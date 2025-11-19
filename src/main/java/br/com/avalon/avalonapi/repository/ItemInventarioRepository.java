package br.com.avalon.avalonapi.repository;

import br.com.avalon.avalonapi.domain.model.ItemInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemInventarioRepository extends JpaRepository<ItemInventario, Long> {
}