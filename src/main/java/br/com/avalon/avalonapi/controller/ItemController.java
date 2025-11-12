package br.com.avalon.avalonapi.controller;

import br.com.avalon.avalonapi.domain.enums.Atributo;
import br.com.avalon.avalonapi.domain.enums.SlotEquipamento;
import br.com.avalon.avalonapi.domain.enums.TipoItem;
import br.com.avalon.avalonapi.domain.model.Item;
import br.com.avalon.avalonapi.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/itens")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<Item> criarItem(@RequestParam String nome,
                                          @RequestParam TipoItem tipo,
                                          @RequestBody(required = false) Map<Atributo, Integer> atributos,
                                          @RequestParam(required = false) SlotEquipamento slotEquipamento) {
        Item novoItem = itemService.criarItem(nome, tipo, atributos, slotEquipamento);
        return new ResponseEntity<>(novoItem, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Item>> listarItens() {
        List<Item> itens = itemService.buscarTodosItens();
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> buscarItemPorId(@PathVariable Long id) {
        return itemService.buscarItemPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> atualizarItem(@PathVariable Long id, @RequestBody Item itemAtualizado) {
        try {
            Item atualizado = itemService.atualizarItem(id, itemAtualizado);
            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        itemService.deletarItem(id);
        return ResponseEntity.noContent().build();
    }
}