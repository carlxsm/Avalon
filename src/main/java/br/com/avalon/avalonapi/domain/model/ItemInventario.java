package br.com.avalon.avalonapi.domain.model;

import jakarta.persistence.*;

@Entity
public class ItemInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Itens item;
    private int quantidade;

    @ManyToOne // Relacionamento com Inventário
    @JoinColumn(name = "inventario_id", insertable = false, updatable = false)
    private Inventario inventario;

    public ItemInventario() {} // Construtor padrão para JPA

    public ItemInventario(Itens item, int quantidade) {
        this.item = item;
        this.quantidade = quantidade;
    }

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Itens getItem() { return item; }
    public void setItem(Itens item) { this.item = item; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public Inventario getInventario() { return inventario; }
    public void setInventario(Inventario inventario) { this.inventario = inventario; }

    public void adicionarQuantidade(int qtd) {
        this.quantidade += qtd;
    }

    public void removerQuantidade(int qtd) {
        this.quantidade -= qtd;
    }
}