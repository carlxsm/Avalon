package br.com.avalon.avalonapi.service;

import br.com.avalon.avalonapi.domain.enums.Atributo;
import br.com.avalon.avalonapi.domain.enums.SlotEquipamento;
import br.com.avalon.avalonapi.domain.enums.TipoItem;
import br.com.avalon.avalonapi.domain.model.Itens;
import br.com.avalon.avalonapi.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public Itens criarItem(String nome, TipoItem tipo, Map<Atributo, Integer> atributos, SlotEquipamento slotEquipamento) {
        Itens item = new Itens(nome, tipo, atributos, slotEquipamento);
        return itemRepository.save(item);
    }

    public Optional<Itens> buscarItemPorId(Long id) {
        return itemRepository.findById(id);
    }

    public List<Itens> buscarTodosItens() {
        return itemRepository.findAll();
    }

    @Transactional
    public Itens atualizarItem(Long id, Itens itemAtualizado) {
        return itemRepository.findById(id)
                .map(item -> {
                    item.setNome(itemAtualizado.getNome());
                    item.setTipo(itemAtualizado.getTipo());
                    item.setAtributos(itemAtualizado.getAtributos());
                    item.setSlotEquipamento(itemAtualizado.getSlotEquipamento());
                    return itemRepository.save(item);
                })
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado com ID: " + id));
    }

    @Transactional
    public void deletarItem(Long id) {
        itemRepository.deleteById(id);
    }
}