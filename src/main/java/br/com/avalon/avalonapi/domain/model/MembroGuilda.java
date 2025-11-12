package br.com.avalon.avalonapi.domain.model;

import br.com.avalon.avalonapi.domain.enums.CargoGuilda;
import br.com.avalon.avalonapi.domain.model.Guilda;
import jakarta.persistence.*;

import javax.persistence.*;

@Entity
public class MembroGuilda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID próprio para a entidade MembroGuilda

    private Long personagemId;
    @Enumerated(EnumType.STRING)
    private CargoGuilda cargo;

    @ManyToOne // Relacionamento com Guilda
    @JoinColumn(name = "guilda_id", insertable = false, updatable = false)
    private Guilda guilda; // Para mapeamento reverso, se necessário

    public MembroGuilda() {} // Construtor padrão para JPA

    public MembroGuilda(Long personagemId, CargoGuilda cargo) {
        this.personagemId = personagemId;
        this.cargo = cargo;
    }

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPersonagemId() { return personagemId; }
    public void setPersonagemId(Long personagemId) { this.personagemId = personagemId; }
    public CargoGuilda getCargo() { return cargo; }
    public void setCargo(CargoGuilda cargo) { this.cargo = cargo; }
    public Guilda getGuilda() { return guilda; }
    public void setGuilda(Guilda guilda) { this.guilda = guilda; }
}