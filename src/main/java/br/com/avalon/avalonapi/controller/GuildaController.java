package br.com.avalon.avalonapi.controller;

import br.com.avalon.avalonapi.domain.enums.CargoGuilda;
import br.com.avalon.avalonapi.domain.model.Guilda;
import br.com.avalon.avalonapi.service.GuildaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guildas")
public class GuildaController {

    private final GuildaService guildaService;

    public GuildaController(GuildaService guildaService) {
        this.guildaService = guildaService;
    }

    @PostMapping
    public ResponseEntity<Guilda> criarGuilda(@RequestBody GuildaCriacaoDTO dto) {
        try {
            Guilda novaGuilda = guildaService.criarGuilda(dto.getNome(), dto.getDescricao(), dto.getLiderId());
            return new ResponseEntity<>(novaGuilda, HttpStatus.CREATED);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Guilda> buscarGuildaPorId(@PathVariable Long id) {
        return guildaService.buscarGuildaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/convidar")
    public ResponseEntity<String> convidarPersonagem(@RequestBody ConviteGuildaDTO dto) {
        try {
            boolean convidado = guildaService.convidarPersonagem(dto.getConvidanteId(), dto.getConvidadoId());
            return convidado ? ResponseEntity.ok("Convite enviado com sucesso.") : new ResponseEntity<>("Não foi possível enviar o convite.", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/aceitar-convite")
    public ResponseEntity<String> aceitarConvite(@RequestBody AceitarConviteGuildaDTO dto) {
        try {
            boolean aceito = guildaService.aceitarConvite(dto.getPersonagemId(), dto.getGuildaId());
            return aceito ? ResponseEntity.ok("Convite aceito. Você agora é membro da guilda.") : new ResponseEntity<>("Não foi possível aceitar o convite.", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/recusar-convite")
    public ResponseEntity<String> recusarConvite(@RequestBody AceitarConviteGuildaDTO dto) { // Reutilizando DTO
        try {
            boolean recusado = guildaService.recusarConvite(dto.getPersonagemId(), dto.getGuildaId());
            return recusado ? ResponseEntity.ok("Convite recusado.") : new ResponseEntity<>("Não foi possível recusar o convite.", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/expulsar")
    public ResponseEntity<String> expulsarMembro(@RequestParam Long guildaId,
                                                 @RequestParam Long expulsadorId,
                                                 @RequestParam Long expulsoId) {
        try {
            boolean expulso = guildaService.expulsarMembro(expulsadorId, expulsoId);
            return expulso ? ResponseEntity.ok("Membro expulso com sucesso.") : new ResponseEntity<>("Não foi possível expulsar o membro.", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/transferir-lideranca")
    public ResponseEntity<String> transferirLideranca(@RequestBody TransferenciaLiderancaDTO dto) {
        try {
            boolean transferido = guildaService.transferirLideranca(dto.getGuildaId(), dto.getAntigoLiderId(), dto.getNovoLiderId());
            return transferido ? ResponseEntity.ok("Liderança transferida com sucesso.") : new ResponseEntity<>("Não foi possível transferir a liderança.", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{guildaId}/descricao")
    public ResponseEntity<String> mudarDescricao(@PathVariable Long guildaId,
                                                 @RequestParam Long alteradorId,
                                                 @RequestParam String novaDescricao) {
        try {
            boolean alterado = guildaService.mudarDescricaoGuilda(guildaId, alteradorId, novaDescricao);
            return alterado ? ResponseEntity.ok("Descrição da guilda alterada com sucesso.") : new ResponseEntity<>("Não foi possível alterar a descrição.", HttpStatus.FORBIDDEN);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{guildaId}/promover")
    public ResponseEntity<String> promoverMembro(@PathVariable Long guildaId,
                                                 @RequestParam Long promotorId,
                                                 @RequestParam Long promovidoId,
                                                 @RequestParam CargoGuilda novoCargo) {
        try {
            boolean promovido = guildaService.promoverMembro(guildaId, promotorId, promovidoId, novoCargo);
            return promovido ? ResponseEntity.ok("Membro promovido com sucesso para " + novoCargo.getNome()) : new ResponseEntity<>("Não foi possível promover o membro.", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}