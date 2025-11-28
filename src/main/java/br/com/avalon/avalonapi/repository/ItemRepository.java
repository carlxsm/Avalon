package br.com.avalon.avalonapi.repository;

import br.com.avalon.avalonapi.domain.model.Itens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Itens, Long> {
}