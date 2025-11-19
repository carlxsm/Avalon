package br.com.avalon.avalonapi.repository;

import br.com.avalon.avalonapi.domain.model.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    List<Mensagem> findByDestinatarioIdAndLidaFalse(Long id);
}