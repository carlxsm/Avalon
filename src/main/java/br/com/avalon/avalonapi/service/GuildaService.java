package br.com.avalon.avalonapi.service;

import br.com.avalon.avalonapi.domain.enums.CargoGuilda;
import br.com.avalon.avalonapi.domain.enums.TipoMensagem;
import br.com.avalon.avalonapi.domain.model.Guilda;
import br.com.avalon.avalonapi.domain.model.MembroGuilda;
import br.com.avalon.avalonapi.domain.model.Mensagem;
import br.com.avalon.avalonapi.domain.model.Personagem;
import br.com.avalon.avalonapi.repository.GuildaRepository;
import br.com.avalon.avalonapi.repository.MensagemRepository;
import br.com.avalon.avalonapi.repository.PersonagemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GuildaService {

    private final GuildaRepository guildaRepository;
    private final PersonagemRepository personagemRepository;
    private final MensagemRepository mensagemRepository; // Para o sistema de mensagens

    public GuildaService(GuildaRepository guildaRepository, PersonagemRepository personagemRepository, MensagemRepository mensagemRepository) {
        this.guildaRepository = guildaRepository;
        this.personagemRepository = personagemRepository;
        this.mensagemRepository = mensagemRepository;
    }

    public Optional<Guilda> buscarGuildaPorId(Long id) {
        return guildaRepository.findById(id);
    }

    public Guilda salvarGuilda(Guilda guilda) {
        return guildaRepository.save(guilda);
    }

    @Transactional
    public Guilda criarGuilda(String nome, String descricao, Long liderId) {
        Personagem lider = personagemRepository.findById(liderId)
                .orElseThrow(() -> new IllegalArgumentException("Líder não encontrado."));

        if (lider.getGuildaId() != null) {
            throw new IllegalStateException("Personagem já pertence a uma guilda.");
        }

        Guilda guilda = new Guilda(nome, descricao, liderId);
        guilda.adicionarMembro(lider, CargoGuilda.LIDER);
        guilda = guildaRepository.save(guilda);

        lider.setGuildaId(guilda.getId());
        personagemRepository.save(lider);

        return guilda;
    }

    @Transactional
    public boolean convidarPersonagem(Long convidanteId, Long convidadoId) {
        Personagem convidante = personagemRepository.findById(convidanteId)
                .orElseThrow(() -> new IllegalArgumentException("Convidante não encontrado."));
        Personagem convidado = personagemRepository.findById(convidadoId)
                .orElseThrow(() -> new IllegalArgumentException("Convidado não encontrado."));

        if (convidante.getGuildaId() == null) {
            throw new IllegalStateException("Convidante não pertence a nenhuma guilda.");
        }
        if (convidado.getGuildaId() != null) {
            throw new IllegalStateException("Convidado já pertence a uma guilda.");
        }

        Guilda guilda = guildaRepository.findById(convidante.getGuildaId())
                .orElseThrow(() -> new IllegalStateException("Guilda não encontrada."));

        if (guilda.convidarPersonagem(convidanteId, convidadoId)) {
            guildaRepository.save(guilda);

            // Notificar o convidado sobre o convite (sistema de mensagens interno)
            Mensagem mensagem = new Mensagem();
            mensagem.setDestinatarioId(convidadoId);
            mensagem.setTitulo("Convite para Guilda: " + guilda.getNome());
            mensagem.setConteudo(convidante.getNome() + " convidou você para a guilda " + guilda.getNome() + ".");
            mensagem.setTipo(TipoMensagem.CONVITE_GUILDA);
            mensagem.setReferenciaId(guilda.getId()); // Adicionar o ID da guilda como referência
            mensagem.setLida(false);
            mensagemRepository.save(mensagem);

            return true;
        }
        return false;
    }

    @Transactional
    public boolean aceitarConvite(Long personagemId, Long guildaId) {
        Personagem personagem = personagemRepository.findById(personagemId)
                .orElseThrow(() -> new IllegalArgumentException("Personagem não encontrado."));
        Guilda guilda = guildaRepository.findById(guildaId)
                .orElseThrow(() -> new IllegalArgumentException("Guilda não encontrada."));

        if (personagem.getGuildaId() != null) {
            throw new IllegalStateException("Personagem já pertence a uma guilda.");
        }

        if (guilda.aceitarConvite(personagemId)) {
            // Adicionar personagem como MEMBRO na guilda
            guilda.adicionarMembro(personagem, CargoGuilda.MEMBRO);
            guildaRepository.save(guilda);

            personagem.setGuildaId(guilda.getId());
            personagemRepository.save(personagem);

            return true;
        }
        return false;
    }

    @Transactional
    public boolean recusarConvite(Long personagemId, Long guildaId) {
        // Obter a guilda e o personagem
        Guilda guilda = guildaRepository.findById(guildaId)
                .orElseThrow(() -> new IllegalArgumentException("Guilda não encontrada."));

        if (guilda.recusarConvite(personagemId)) {
            guildaRepository.save(guilda);
            return true;
        }
        return false;
    }


    @Transactional
    public boolean expulsarMembro(Long expulsadorId, Long expulsoId) {
        Personagem expulsador = personagemRepository.findById(expulsadorId)
                .orElseThrow(() -> new IllegalArgumentException("Expulsador não encontrado."));
        Personagem expulso = personagemRepository.findById(expulsoId)
                .orElseThrow(() -> new IllegalArgumentException("Expulso não encontrado."));

        if (!expulsador.getGuildaId().equals(expulso.getGuildaId())) {
            throw new IllegalStateException("Membros não pertencem à mesma guilda.");
        }

        Guilda guilda = guildaRepository.findById(expulsador.getGuildaId())
                .orElseThrow(() -> new IllegalStateException("Guilda não encontrada."));

        Optional<MembroGuilda> membroExpulsador = guilda.getMembro(expulsadorId);
        Optional<MembroGuilda> membroExpulso = guilda.getMembro(expulsoId);

        if (membroExpulsador.isPresent() && membroExpulso.isPresent()) {
            if (membroExpulsador.get().getCargo().podeExpulsar() &&
                    membroExpulsador.get().getCargo().ordinal() <
                            membroExpulso.get().getCargo().ordinal()) { // Não pode expulsar alguém de cargo superior ou igual
                if (guilda.removerMembro(expulsoId)) {
                    guildaRepository.save(guilda);
                    expulso.setGuildaId(null); // Remove a referência da guilda do personagem
                    personagemRepository.save(expulso);
                    return true;
                }
            }
        }
        return false;
    }

    @Transactional
    public boolean transferirLideranca(Long guildaId, Long antigoLiderId, Long novoLiderId) {
        Guilda guilda = guildaRepository.findById(guildaId)
                .orElseThrow(() -> new IllegalStateException("Guilda não encontrada."));

        if (!guilda.getLiderId().equals(antigoLiderId)) {
            throw new IllegalStateException("Apenas o líder atual pode transferir a liderança.");
        }

        Optional<MembroGuilda> novoLiderMembro = guilda.getMembro(novoLiderId);
        if (novoLiderMembro.isEmpty()) {
            throw new IllegalArgumentException("Novo líder não é membro da guilda.");
        }

        guilda.transferirLideranca(novoLiderId);
        guildaRepository.save(guilda);

        // Notificar o novo líder (se offline, na próxima vez que logar)
        Personagem novoLider = personagemRepository.findById(novoLiderId)
                .orElseThrow(() -> new IllegalArgumentException("Novo líder não encontrado para notificação."));

        // ensagem Interna
        Mensagem mensagem = new Mensagem();
        mensagem.setDestinatarioId(novoLiderId);
        mensagem.setTitulo("Você é o novo Líder da Guilda: " + guilda.getNome());
        mensagem.setConteudo("Parabéns! Você foi promovido a líder da guilda.");
        mensagem.setTipo(TipoMensagem.TRANSFERENCIA_LIDERANCA);
        mensagem.setReferenciaId(guildaId);
        mensagem.setLida(false);
        mensagemRepository.save(mensagem);

        // Campo no Personagem (se o novo líder estiver offline)
        if (true /* Lógica para verificar se o novoLider está offline */) {
            novoLider.setGuildaLiderancaPendenteId(guildaId);
            personagemRepository.save(novoLider);
        }

        return true;
    }

    // Outros métodos para gerenciar a guilda (mudar descrição, promover/rebaixar)
    @Transactional
    public boolean mudarDescricaoGuilda(Long guildaId, Long alteradorId, String novaDescricao) {
        Guilda guilda = guildaRepository.findById(guildaId)
                .orElseThrow(() -> new IllegalArgumentException("Guilda não encontrada."));
        Optional<MembroGuilda> alterador = guilda.getMembro(alteradorId);

        if (alterador.isPresent() && alterador.get().getCargo().podeMudarDescricao()) {
            guilda.setDescricao(novaDescricao);
            guildaRepository.save(guilda);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean promoverMembro(Long guildaId, Long promotorId, Long promovidoId, CargoGuilda novoCargo) {
        Guilda guilda = guildaRepository.findById(guildaId)
                .orElseThrow(() -> new IllegalArgumentException("Guilda não encontrada."));

        if (guilda.promoverMembro(promotorId, promovidoId, novoCargo)) {
            guildaRepository.save(guilda);
            // Notificar o membro promovido
            Mensagem mensagem = new Mensagem();
            mensagem.setDestinatarioId(promovidoId);
            mensagem.setTitulo("Você foi promovido na Guilda " + guilda.getNome());
            mensagem.setConteudo("Seu novo cargo é: " + novoCargo.getNome());
            mensagem.setTipo(TipoMensagem.PROMOCAO_GUILDA);
            mensagem.setReferenciaId(guildaId);
            mensagem.setLida(false);
            mensagemRepository.save(mensagem);

            return true;
        }
        return false;
    }

    @Transactional
    public void aceitarLideranca(Long personagemId, Long guildaId) {
        Guilda guilda = guildaRepository.findById(guildaId)
                .orElseThrow(() -> new IllegalArgumentException("Guilda não encontrada."));

        Personagem novoLider = personagemRepository.findById(personagemId)
                .orElseThrow(() -> new IllegalArgumentException("Personagem não encontrado."));

        // Atualiza a guilda
        guilda.transferirLideranca(personagemId);
        guildaRepository.save(guilda);

        // Limpa pendência do personagem se houver
        novoLider.setGuildaLiderancaPendenteId(null);
        personagemRepository.save(novoLider);
    }

}