package br.com.avalon.avalonapi.domain.model;

import br.com.avalon.avalonapi.domain.enums.*;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Personagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @Enumerated(EnumType.STRING)
    private Raca raca;

    @Enumerated(EnumType.STRING)
    private Classe classe;

    private int nivel;
    private long experiencia;
    private int pontosVidaAtual;
    private int pontosVidaMax;
    private int pontosManaAtual;
    private int pontosManaMax;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "inventario_id", referencedColumnName = "id")
    private Inventario inventario;

    @ElementCollection
    @CollectionTable(name = "personagem_itens_equipados", joinColumns = @JoinColumn(name = "personagem_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "slot_equipamento")
    @Column(name = "item_id")
    private Map<SlotEquipamento, Itens> itensEquipados = new HashMap<>(); // Inicialize para evitar NullPointerException

    private Long guildaId; // Referência a guilda que o personagem pertence
    private Long guildaLiderancaPendenteId; // Para Abordagem 2 de notificação de liderança

    // Construtor padrão
    public Personagem() {
        this.itensEquipados = new HashMap<>();
    }

    // SUBISTITUIR ESSA PELO LOMBOK QUANDO TUDO TIVER RODANDO DIREITO
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Raca getRaca() { return raca; }
    public void setRaca(Raca raca) { this.raca = raca; }
    public Classe getClasse() { return classe; }
    public void setClasse(Classe classe) { this.classe = classe; }
    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }
    public long getExperiencia() { return experiencia; }
    public void setExperiencia(long experiencia) { this.experiencia = experiencia; }
    public int getPontosVidaAtual() { return pontosVidaAtual; }
    public void setPontosVidaAtual(int pontosVidaAtual) { this.pontosVidaAtual = pontosVidaAtual; }
    public int getPontosVidaMax() { return pontosVidaMax; }
    public void setPontosVidaMax(int pontosVidaMax) { this.pontosVidaMax = pontosVidaMax; }
    public int getPontosManaAtual() { return pontosManaAtual; }
    public void setPontosManaAtual(int pontosManaAtual) { this.pontosManaAtual = pontosManaAtual; }
    public int getPontosManaMax() { return pontosManaMax; }
    public void setPontosManaMax(int pontosManaMax) { this.pontosManaMax = pontosManaMax; }
    public Inventario getInventario() { return inventario; }
    public void setInventario(Inventario inventario) { this.inventario = inventario; }
    public Map<SlotEquipamento, Itens> getItensEquipados() { return itensEquipados; }
    public void setItensEquipados(Map<SlotEquipamento, Itens> itensEquipados) { this.itensEquipados = itensEquipados; }
    public Long getGuildaId() { return guildaId; }
    public void setGuildaId(Long guildaId) { this.guildaId = guildaId; }
    public Long getGuildaLiderancaPendenteId() { return guildaLiderancaPendenteId; }
    public void setGuildaLiderancaPendenteId(Long guildaLiderancaPendenteId) { this.guildaLiderancaPendenteId = guildaLiderancaPendenteId; }


    public void ganharExperiencia(long xp) {
        this.experiencia += xp;
        // Lógica para subir de nível
        // if (this.experiencia >= getXpParaProximoNivel()) {
        //     this.nivel++;
        //     // Atualizar vida/mana máxima, recalcular stats, etc.
        // }
    }

    public void receberDano(int dano) {
        this.pontosVidaAtual -= dano;
        if (this.pontosVidaAtual < 0) {
            this.pontosVidaAtual = 0;
            // Lógica para morte do personagem
        }
    }

    public void usarMana(int mana) {
        this.pontosManaAtual -= mana;
        if (this.pontosManaAtual < 0) {
            this.pontosManaAtual = 0;
        }
    }

    // Métodos para cálculo de status
    public int getAtaqueMagicoTotal() {
        return calcularAtaqueMagicoBase() + calcularBonusAtaqueMagicoItens();
    }

    public int getAtaqueFisicoTotal() {
        return calcularAtaqueFisicoBase() + calcularBonusAtaqueFisicoItens();
    }

    public int getDefesaMagicaTotal() {
        return calcularDefesaMagicaBase() + calcularBonusDefesaMagicaItens();
    }

    public int getDefesaFisicaTotal() {
        return calcularDefesaFisicaBase() + calcularBonusDefesaFisicaItens();
    }

    public int getPrecisaoTotal() {
        return calcularPrecisaoBase() + calcularBonusPrecisaoItens();
    }

    public int getDestrezaTotal() {
        return calcularDestrezaBase() + calcularBonusDestrezaItens();
    }

    // Métodos privados para cálculo base (Raça/Classe)
    private int calcularAtaqueMagicoBase() {
        return this.raca.getEstrategiaStatus().getAtaqueMagicoBase(this.nivel) +
                this.classe.getEstrategiaStatus().getAtaqueMagicoBase(this.nivel);
    }

    private int calcularAtaqueFisicoBase() {
        return this.raca.getEstrategiaStatus().getAtaqueFisicoBase(this.nivel) +
                this.classe.getEstrategiaStatus().getAtaqueFisicoBase(this.nivel);
    }

    private int calcularDefesaMagicaBase() {
        return this.raca.getEstrategiaStatus().getDefesaMagicaBase(this.nivel) +
                this.classe.getEstrategiaStatus().getDefesaMagicaBase(this.nivel);
    }

    private int calcularDefesaFisicaBase() {
        return this.raca.getEstrategiaStatus().getDefesaFisicaBase(this.nivel) +
                this.classe.getEstrategiaStatus().getDefesaFisicaBase(this.nivel);
    }

    private int calcularPrecisaoBase() {
        return this.raca.getEstrategiaStatus().getPrecisaoBase(this.nivel) +
                this.classe.getEstrategiaStatus().getPrecisaoBase(this.nivel);
    }

    private int calcularDestrezaBase() {
        return this.raca.getEstrategiaStatus().getDestrezaBase(this.nivel) +
                this.classe.getEstrategiaStatus().getDestrezaBase(this.nivel);
    }

    // Métodos privados para cálculo de bônus de itens
    private int calcularBonusAtaqueMagicoItens() {
        return itensEquipados.values().stream()
                .mapToInt(item ->
                        item.getAtributos().getOrDefault(Atributo.ATAQUE_MAGICO, 0))
                .sum();
    }

    private int calcularBonusAtaqueFisicoItens() {
        return itensEquipados.values().stream()
                .mapToInt(item ->
                        item.getAtributos().getOrDefault(Atributo.ATAQUE_FISICO, 0))
                .sum();
    }

    private int calcularBonusDefesaMagicaItens() {
        return itensEquipados.values().stream()
                .mapToInt(item ->
                        item.getAtributos().getOrDefault(Atributo.DEFESA_MAGICA, 0))
                .sum();
    }

    private int calcularBonusDefesaFisicaItens() {
        return itensEquipados.values().stream()
                .mapToInt(item ->
                        item.getAtributos().getOrDefault(Atributo.DEFESA_FISICA, 0))
                .sum();
    }

    private int calcularBonusPrecisaoItens() {
        return itensEquipados.values().stream()
                .mapToInt(item ->
                        item.getAtributos().getOrDefault(Atributo.PRECISAO, 0))
                .sum();
    }

    private int calcularBonusDestrezaItens() {
        return itensEquipados.values().stream()
                .mapToInt(item ->
                        item.getAtributos().getOrDefault(Atributo.DESTREZA, 0))
                .sum();
    }

    // Métodos para equipar/desequipar itens
    public boolean equiparItem(Itens item) {
        if (item.getTipo() == TipoItem.EQUIPAMENTO &&
                item.getSlotEquipamento() != null) {

            // Verificar se a classe pode equipar este tipo de item
            if (this.classe.podeEquipar(item.getTipo(), item.getSlotEquipamento())) {
                // Desequipar item existente no slot, se houver
                if (itensEquipados.containsKey(item.getSlotEquipamento())) {
                    Itens itemDesequipado = itensEquipados.remove(item.getSlotEquipamento());
                    if (this.inventario != null) {
                        this.inventario.adicionarItem(itemDesequipado, 1); // Retorna para o inventário
                    }
                }
                itensEquipados.put(item.getSlotEquipamento(), item);
                if (this.inventario != null) {
                    this.inventario.removerItem(item, 1); // Remove do inventário
                }
                return true;
            }
        }
        return false;
    }

    public boolean desequiparItem(SlotEquipamento slot) {
        if (itensEquipados.containsKey(slot)) {
            Itens itemDesequipado = itensEquipados.remove(slot);
            if (this.inventario != null) {
                this.inventario.adicionarItem(itemDesequipado, 1); // Retorna para o inventário
            }
            return true;
        }
        return false;
    }
}