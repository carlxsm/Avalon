package br.com.avalon.avalonapi.repository;

import br.com.avalon.avalonapi.domain.model.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonagemRepository extends JpaRepository<Personagem, Long> {
}