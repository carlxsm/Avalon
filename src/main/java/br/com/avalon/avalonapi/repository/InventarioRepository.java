package br.com.avalon.avalonapi.repository;

import br.com.avalon.avalonapi.domain.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    Optional<Inventario> findByPersonagemId(Long personagemId);
}