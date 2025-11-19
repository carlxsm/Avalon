package br.com.avalon.avalonapi.controller;

import br.com.avalon.avalonapi.domain.enums.SlotEquipamento;
import br.com.avalon.avalonapi.domain.model.ItemInventario;
import br.com.avalon.avalonapi.domain.model.Personagem;
import br.com.avalon.avalonapi.dto.ItemAdicionarDTO;
import br.com.avalon.avalonapi.dto.PersonagemCriacaoDTO;
import br.com.avalon.avalonapi.service.PersonagemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personagens")
public class PersonagemController {

    private final PersonagemService personagemService;

    public PersonagemController(PersonagemService personagemService) {
        this.personagemService = personagemService;
    }

    @PostMapping
    public ResponseEntity<Personagem> criarPersonagem(@RequestBody PersonagemCriacaoDTO dto) {
        try {
            Personagem novoPersonagem = personagemService.criarPersonagem(dto.getNome(), dto.getRaca(), dto.getClasse());
            return new ResponseEntity<>(novoPersonagem, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Personagem>> listarPersonagens() {
        List<Personagem> personagens = personagemService.buscarTodosPersonagens();
        return ResponseEntity.ok(personagens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personagem> buscarPersonagemPorId(@PathVariable Long id) {
        return personagemService.buscarPersonagemPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Personagem> atualizarPersonagem(@PathVariable Long id, @RequestBody Personagem personagemAtualizado) {
        try {
            Personagem atualizado = personagemService.atualizarPersonagem(id, personagemAtualizado);
            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPersonagem(@PathVariable Long id) {
        personagemService.deletarPersonagem(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{personagemId}/ganhar-xp")
    public ResponseEntity<Personagem> ganharExperiencia(@PathVariable Long personagemId, @RequestParam long xp) {
        try {
            Personagem personagem = personagemService.ganharExperiencia(personagemId, xp);
            return ResponseEntity.ok(personagem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{personagemId}/receber-dano")
    public ResponseEntity<Personagem> receberDano(@PathVariable Long personagemId, @RequestParam int dano) {
        try {
            Personagem personagem = personagemService.receberDano(personagemId, dano);
            return ResponseEntity.ok(personagem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{personagemId}/usar-mana")
    public ResponseEntity<Personagem> usarMana(@PathVariable Long personagemId, @RequestParam int mana) {
        try {
            Personagem personagem = personagemService.usarMana(personagemId, mana);
            return ResponseEntity.ok(personagem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{personagemId}/inventario/adicionar")
    public ResponseEntity<ItemInventario> adicionarItem(@PathVariable Long personagemId, @RequestBody ItemAdicionarDTO dto) {
        try {
            ItemInventario itemAdicionado = personagemService.adicionarItemAoInventario(personagemId, dto.getItemId(), dto.getQuantidade());
            return new ResponseEntity<>(itemAdicionado, HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{personagemId}/inventario/remover")
    public ResponseEntity<Void> removerItem(@PathVariable Long personagemId, @RequestBody ItemAdicionarDTO dto) {
        try {
            boolean removido = personagemService.removerItemDoInventario(personagemId, dto.getItemId(), dto.getQuantidade());
            return removido ? ResponseEntity.ok().build() : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{personagemId}/equipar/{itemId}")
    public ResponseEntity<Void> equiparItem(@PathVariable Long personagemId, @PathVariable Long itemId) {
        try {
            boolean equipado = personagemService.equiparItem(personagemId, itemId);
            return equipado ? ResponseEntity.ok().build() : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{personagemId}/desequipar/{slot}")
    public ResponseEntity<Void> desequiparItem(@PathVariable Long personagemId, @PathVariable SlotEquipamento slot) {
        try {
            boolean desequipado = personagemService.desequiparItem(personagemId, slot);
            return desequipado ? ResponseEntity.ok().build() : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{personagemId}/logar")
    public ResponseEntity<String> aoLogar(@PathVariable Long personagemId) {
        try {
            personagemService.aoLogar(personagemId);
            return ResponseEntity.ok("Verificação de login e mensagens pendentes concluída.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}