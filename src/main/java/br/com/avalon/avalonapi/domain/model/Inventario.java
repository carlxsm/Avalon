package br.com.avalon.avalonapi.domain.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Collections; // Importar Collections

@Entity
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "inventario")
    private Personagem personagem; // personagem dono do inventário

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "inventario_id")
    private List<ItemInventario> itens; // Lista de itens no inventário

    private final int capacidadeMaxima = 100; // Exemplo de capacidade

    public Inventario() {
        this.itens = new ArrayList<>();
    }

    // Construtor, getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Personagem getPersonagem() { return personagem; }
    public void setPersonagem(Personagem personagem) { this.personagem = personagem; }
    public List<ItemInventario> getItens() { return Collections.unmodifiableList(itens); } // Retorna uma cópia para evitar modificações externas diretas
    // Não precisa de setItens, pois é gerenciado internamente

    public int getCapacidadeMaxima() { return capacidadeMaxima; }


    public boolean adicionarItem(Itens item, int quantidade) {
        if (quantidade <= 0) return false;

        Optional<ItemInventario> existingItem = itens.stream()
                .filter(ii -> ii.getItem().getId().equals(item.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().adicionarQuantidade(quantidade);
            return true;
        } else {
            if (itens.size() < capacidadeMaxima) {
                itens.add(new ItemInventario(item, quantidade));
                return true;
            }
        }
        return false; // Inventário cheio
    }

    public boolean removerItem(Itens item, int quantidade) {
        if (quantidade <= 0) return false;

        Optional<ItemInventario> existingItem = itens.stream()
                .filter(ii -> ii.getItem().getId().equals(item.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            ItemInventario ii = existingItem.get();
            if (ii.getQuantidade() >= quantidade) {
                ii.removerQuantidade(quantidade);
                if (ii.getQuantidade() == 0) {
                    itens.remove(ii);
                }
                return true;
            }
        }
        return false; // Item não encontrado ou quantidade insuficiente
    }

    public Optional<ItemInventario> buscarItem(Long itemId) {
        return itens.stream()
                .filter(ii -> ii.getItem().getId().equals(itemId))
                .findFirst();
    }
}