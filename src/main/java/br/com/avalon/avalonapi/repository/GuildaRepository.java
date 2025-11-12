package br.com.avalon.avalonapi.repository;

import br.com.avalon.avalonapi.domain.model.Guilda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuildaRepository extends JpaRepository<Guilda, Long> {
}