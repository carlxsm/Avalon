package br.com.avalon.avalonapi.service;

import br.com.avalon.avalonapi.domain.enums.Classe;
import br.com.avalon.avalonapi.domain.enums.Raca;
import br.com.avalon.avalonapi.domain.enums.SlotEquipamento;
import br.com.avalon.avalonapi.domain.enums.TipoMensagem;
import br.com.avalon.avalonapi.domain.model.*;
import br.com.avalon.avalonapi.repository.InventarioRepository;
import br.com.avalon.avalonapi.repository.ItemRepository;
import br.com.avalon.avalonapi.repository.MensagemRepository;
import br.com.avalon.avalonapi.repository.PersonagemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonagemService {

    private final PersonagemRepository personagemRepository;
    private final InventarioRepository inventarioRepository;
    private final ItemRepository itemRepository;
    private final MensagemRepository mensagemRepository;
    private final GuildaService guildaService; // Injetar GuildaService

    public PersonagemService(PersonagemRepository personagemRepository,
                             InventarioRepository inventarioRepository,
                             ItemRepository itemRepository,
                             MensagemRepository mensagemRepository,
                             GuildaService guildaService) {
        this.personagemRepository = personagemRepository;
        this.inventarioRepository = inventarioRepository;
        this.itemRepository = itemRepository;
        this.mensagemRepository = mensagemRepository;
        this.guildaService = guildaService; // Injetar
    }

    @Transactional
    public Personagem criarPersonagem(String nome, Raca raca, Classe classe) {
        Personagem personagem = new Personagem();
        personagem.setNome(nome);
        personagem.setRaca(raca);
        personagem.setClasse(classe);
        personagem.setNivel(1);
        personagem.setExperiencia(0);
        personagem.setPontosVidaMax(raca.getEstrategiaStatus().getPontosVidaBase(1) + classe.getEstrategiaStatus().getPontosVidaBase(1)); // Exemplo de cálculo inicial
        personagem.setPontosVidaAtual(personagem.getPontosVidaMax());
        personagem.setPontosManaMax(raca.getEstrategiaStatus().getPontosManaBase(1) + classe.getEstrategiaStatus().getPontosManaBase(1)); // Exemplo de cálculo inicial
        personagem.setPontosManaAtual(personagem.getPontosManaMax());

        personagem = personagemRepository.save(personagem);

        // Criar inventário para o novo personagem
        Inventario inventario = new Inventario();
        inventario.setPersonagem(personagem);
        inventarioRepository.save(inventario);
        personagem.setInventario(inventario);

        return personagemRepository.save(personagem); // Salvar novamente para associar o inventário
    }

    public Optional<Personagem> buscarPersonagemPorId(Long id) {
        return personagemRepository.findById(id);
    }

    public List<Personagem> buscarTodosPersonagens() {
        return personagemRepository.findAll();
    }

    @Transactional
    public Personagem atualizarPersonagem(Long id, Personagem personagemAtualizado) {
        return personagemRepository.findById(id)
                .map(personagem -> {
                    personagem.setNome(personagemAtualizado.getNome());
                    personagem.setNivel(personagemAtualizado.getNivel());
                    personagem.setExperiencia(personagemAtualizado.getExperiencia());
                    // ... atualizar outros atributos conforme necessário
                    return personagemRepository.save(personagem);
                })
                .orElseThrow(() -> new IllegalArgumentException("Personagem não encontrado com ID: " + id));
    }

    @Transactional
    public void deletarPersonagem(Long id) {
        personagemRepository.deleteById(id);
    }

    @Transactional
    public Personagem ganharExperiencia(Long personagemId, long xp) {
        Personagem personagem = personagemRepository.findById(personagemId)
                .orElseThrow(() -> new IllegalArgumentException("Personagem não encontrado."));
        personagem.ganharExperiencia(xp);
        // Lógica de subir de nível aqui, se for o caso
        return personagemRepository.save(personagem);
    }

    @Transactional
    public Personagem receberDano(Long personagemId, int dano) {
        Personagem personagem = personagemRepository.findById(personagemId)
                .orElseThrow(() -> new IllegalArgumentException("Personagem não encontrado."));
        personagem.receberDano(dano);
        return personagemRepository.save(personagem);
    }

    @Transactional
    public Personagem usarMana(Long personagemId, int mana) {
        Personagem personagem = personagemRepository.findById(personagemId)
                .orElseThrow(() -> new IllegalArgumentException("Personagem não encontrado."));
        personagem.usarMana(mana);
        return personagemRepository.save(personagem);
    }

    @Transactional
    public ItemInventario adicionarItemAoInventario(Long personagemId, Long itemId, int quantidade) {
        Personagem personagem = personagemRepository.findById(personagemId)
                .orElseThrow(() -> new IllegalArgumentException("Personagem não encontrado."));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado."));

        Inventario inventario = personagem.getInventario();
        if (inventario == null) {
            inventario = new Inventario();
            inventario.setPersonagem(personagem);
            inventarioRepository.save(inventario);
            personagem.setInventario(inventario);
            personagemRepository.save(personagem);
        }

        if (inventario.adicionarItem(item, quantidade)) {
            inventarioRepository.save(inventario);
            // Retorna o ItemInventario adicionado/atualizado
            return inventario.getItens().stream()
                    .filter(ii -> ii.getItem().getId().equals(itemId))
                    .findFirst()
                    .orElse(null);
        }
        throw new IllegalStateException("Não foi possível adicionar o item ao inventário. Talvez esteja cheio.");
    }

    @Transactional
    public boolean removerItemDoInventario(Long personagemId, Long itemId, int quantidade) {
        Personagem personagem = personagemRepository.findById(personagemId)
                .orElseThrow(() -> new IllegalArgumentException("Personagem não encontrado."));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado."));

        Inventario inventario = personagem.getInventario();
        if (inventario == null) {
            throw new IllegalStateException("Inventário não encontrado para o personagem.");
        }

        if (inventario.removerItem(item, quantidade)) {
            inventarioRepository.save(inventario);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean equiparItem(Long personagemId, Long itemId) {
        Personagem personagem = personagemRepository.findById(personagemId)
                .orElseThrow(() -> new IllegalArgumentException("Personagem não encontrado."));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado."));

        // A lógica de equipar item já está na classe Personagem
        // Mas o serviço orquestra a persistência
        if (personagem.equiparItem(item)) {
            personagemRepository.save(personagem);
            if (personagem.getInventario() != null) {
                inventarioRepository.save(personagem.getInventario()); // Persistir o inventário atualizado
            }
            return true;
        }
        return false;
    }

    @Transactional
    public boolean desequiparItem(Long personagemId, SlotEquipamento slot) {
        Personagem personagem = personagemRepository.findById(personagemId)
                .orElseThrow(() -> new IllegalArgumentException("Personagem não encontrado."));

        if (personagem.desequiparItem(slot)) {
            personagemRepository.save(personagem);
            if (personagem.getInventario() != null) {
                inventarioRepository.save(personagem.getInventario()); // Persistir o inventário atualizado
            }
            return true;
        }
        return false;
    }

    // Métodos para Mensagens Internas
    @Transactional
    public void verificarMensagensPendentes(Long personagemId) {
        Personagem personagem = personagemRepository.findById(personagemId)
                .orElseThrow(() -> new IllegalArgumentException("Personagem não encontrado."));

        List<Mensagem> mensagensNaoLidas = mensagemRepository.findByDestinatarioIdAndLidaFalse(personagem.getId());

        for (Mensagem msg : mensagensNaoLidas) {
            if (msg.getTipo() == TipoMensagem.TRANSFERENCIA_LIDERANCA) {
                // Lógica para perguntar se aceita a liderança
                // Por enquanto, vamos assumir que aceita automaticamente para teste
                // Em um cenário real, você teria um endpoint ou lógica para o jogador aceitar/recusar.
                guildaService.aceitarLideranca(personagem.getId(), msg.getReferenciaId());
            }
            msg.setLida(true);
            mensagemRepository.save(msg);
            System.out.println("Mensagem para " + personagem.getNome() + ": " + msg.getTitulo() + " - " + msg.getConteudo());
        }
    }

    // Abordagem 2: aoLogar com guildaLiderancaPendenteId
    @Transactional
    public void aoLogar(Long personagemId) {
        Personagem personagem = personagemRepository.findById(personagemId)
                .orElseThrow(() -> new IllegalArgumentException("Personagem não encontrado."));

        if (personagem.getGuildaLiderancaPendenteId() != null) {
            Long guildaId = personagem.getGuildaLiderancaPendenteId();
            Guilda guilda = guildaService.buscarGuildaPorId(guildaId)
                    .orElseThrow(() -> new IllegalStateException("Guilda de liderança pendente não encontrada."));

            // Aqui você apresentaria a opção para o jogador aceitar a liderança
            // Por enquanto, vamos fazer aceitar automaticamente para fins de teste
            guilda.setLiderId(personagem.getId());
            guildaService.salvarGuilda(guilda); // Salvar a guilda no service
            personagem.setGuildaLiderancaPendenteId(null); // Limpar o campo
            personagemRepository.save(personagem);
            System.out.println("Personagem " + personagem.getNome() + " aceitou a liderança da guilda " + guilda.getNome());
        }
        verificarMensagensPendentes(personagemId); // Verifica também mensagens internas
    }
}