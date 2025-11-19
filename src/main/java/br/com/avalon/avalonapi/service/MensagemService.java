package br.com.avalon.avalonapi.service;

import br.com.avalon.avalonapi.domain.model.Mensagem;
import br.com.avalon.avalonapi.repository.MensagemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MensagemService {

    private final MensagemRepository mensagemRepository;

    public MensagemService(MensagemRepository mensagemRepository) {
        this.mensagemRepository = mensagemRepository;
    }

    public List<Mensagem> buscarMensagensNaoLidas(Long personagemId) {
        return mensagemRepository.findByDestinatarioIdAndLidaFalse(personagemId);
    }

    public void marcarComoLida(Mensagem mensagem) {
        mensagem.setLida(true);
        mensagemRepository.save(mensagem);
    }
}