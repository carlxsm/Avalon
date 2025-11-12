package br.com.avalon.avalonapi.service;

import br.com.avalon.avalonapi.domain.enums.TipoMensagem;
import br.com.avalon.avalonapi.domain.model.Mensagem;
import br.com.avalon.avalonapi.domain.model.Personagem;
import br.com.avalon.avalonapi.repository.MensagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    public void verificarMensagensPendentes(Personagem personagem) {
        List<Mensagem> mensagensNaoLidas =
                mensagemRepository.findByDestinatarioIdAndLidaFalse(personagem.getId());
        for (Mensagem msg : mensagensNaoLidas) {
            // Apresentar a mensagem ao usuário (via websocket se online, ou na próxima requisição)
            if (msg.getTipo() == TipoMensagem.TRANSFERENCIA_LIDERANCA) {
                // Lógica para perguntar se aceita a liderança
                // Se aceitar, chamar
                GuildaService.aceitarLideranca(personagem.getId(), msg.getReferenciaId());
            }
            msg.setLida(true);
            mensagemRepository.save(msg);
        }
    }

}
