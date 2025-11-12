package br.com.avalon.avalonapi.domain.model;

import br.com.avalon.avalonapi.domain.enums.CargoGuilda;
import jakarta.persistence.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collections; // Importar Collections

@Entity
public class Guilda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Long liderId; // ID do Personagem que é o líder

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "guilda_id")
    private List<MembroGuilda> membros; // Lista de membros da guilda

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "guilda_convites_pendentes", joinColumns = @JoinColumn(name = "guilda_id"))
    @MapKeyColumn(name = "personagem_id")
    @Column(name = "convite_enviado")
    private Map<Long, Boolean> convitesPendentes; // PersonagemId -> true (convite enviado)

    public Guilda() {
        this.membros = new ArrayList<>();
        this.convitesPendentes = new ConcurrentHashMap<>();
    }

    public Guilda(String nome, String descricao, Long liderId) {
        this(); // Chama o construtor padrão para inicializar as listas
        this.nome = nome;
        this.descricao = descricao;
        this.liderId = liderId;
    }

    // Construtor, getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Long getLiderId() { return liderId; }
    public void setLiderId(Long liderId) { this.liderId = liderId; }
    public List<MembroGuilda> getMembros() { return Collections.unmodifiableList(membros); } // Retorna uma cópia para evitar modificações externas diretas
    // public void setMembros(List<MembroGuilda> membros) { this.membros = membros; } // Geralmente não é necessário um setter para coleções gerenciadas
    public Map<Long, Boolean> getConvitesPendentes() { return Collections.unmodifiableMap(convitesPendentes); } // Retorna uma cópia
    // public void setConvitesPendentes(Map<Long, Boolean> convitesPendentes) { this.convitesPendentes = convitesPendentes; }


    public Optional<MembroGuilda> getMembro(Long personagemId) {
        return membros.stream()
                .filter(m -> m.getPersonagemId().equals(personagemId))
                .findFirst();
    }

    public boolean adicionarMembro(Personagem personagem, CargoGuilda cargo) {
        if (getMembro(personagem.getId()).isEmpty()) {
            membros.add(new MembroGuilda(personagem.getId(), cargo));
            convitesPendentes.remove(personagem.getId()); // Remove o convite pendente
            return true;
        }
        return false; // Já é membro
    }

    public boolean removerMembro(Long personagemId) {
        return membros.removeIf(m ->
                m.getPersonagemId().equals(personagemId));
    }

    public boolean promoverMembro(Long promotorId, Long promovidoId, CargoGuilda novoCargo) {
        Optional<MembroGuilda> promotor = getMembro(promotorId);
        Optional<MembroGuilda> promovido = getMembro(promovidoId);

        if (promotor.isPresent() && promovido.isPresent()) {
            // Lógica de permissão: o promotor deve ter permissão para promover para o novo cargo
            if (promotor.get().getCargo().podePromover(novoCargo)) {
                promovido.get().setCargo(novoCargo);
                return true;
            }
        }
        return false;
    }

    public boolean convidarPersonagem(Long convidanteId, Long convidadoId) {
        Optional<MembroGuilda> convidante = getMembro(convidanteId);
        if (convidante.isPresent() &&
                convidante.get().getCargo().podeConvidar()) {
            if (!convitesPendentes.containsKey(convidadoId) &&
                    getMembro(convidadoId).isEmpty()) {
                convitesPendentes.put(convidadoId, true);
                return true;
            }
        }
        return false;
    }

    public boolean aceitarConvite(Long personagemId) {
        if (convitesPendentes.containsKey(personagemId)) {
            // A lógica de adicionar o personagem como membro e atualizar o personagem
            // será feita pelo serviço (GuildaService) para gerenciar a transação.
            // apenas indicamos que o convite foi aceito.
            convitesPendentes.remove(personagemId);
            return true;
        }
        return false;
    }

    public boolean recusarConvite(Long personagemId) {
        if (convitesPendentes.containsKey(personagemId)) {
            convitesPendentes.remove(personagemId);
            return true;
        }
        return false;
    }

    public void transferirLideranca(Long novoLiderId) {
        // Remover o cargo de LIDER do antigo líder
        getMembro(this.liderId).ifPresent(m ->
                m.setCargo(CargoGuilda.MEMBRO)); // Ou outro cargo padrão

        // Definir o novo líder
        getMembro(novoLiderId).ifPresent(m -> m.setCargo(CargoGuilda.LIDER));
        this.liderId = novoLiderId;
    }
}