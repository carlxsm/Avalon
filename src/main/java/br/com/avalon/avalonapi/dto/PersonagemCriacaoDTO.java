package br.com.avalon.avalonapi.dto;

import br.com.avalon.avalonapi.domain.enums.Classe;
import br.com.avalon.avalonapi.domain.enums.Raca;

public class PersonagemCriacaoDTO {
    private String nome;
    private Raca raca;
    private Classe classe;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Raca getRaca() { return raca; }
    public void setRaca(Raca raca) { this.raca = raca; }
    public Classe getClasse() { return classe; }
    public void setClasse(Classe classe) { this.classe = classe; }
}