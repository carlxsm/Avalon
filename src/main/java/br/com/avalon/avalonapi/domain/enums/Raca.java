package br.com.avalon.avalonapi.domain.enums;

import br.com.avalon.avalonapi.domain.strategy.EstrategiaStatus;
import br.com.avalon.avalonapi.domain.strategy.EstrategiaStatusAnao;
import br.com.avalon.avalonapi.domain.strategy.EstrategiaStatusElfo;
import br.com.avalon.avalonapi.domain.strategy.EstrategiaStatusHumano;

import java.util.EnumSet;

public enum Raca {
    HUMANO("Humano", new EstrategiaStatusHumano()),
    ELFO("Elfo", new EstrategiaStatusElfo()),
    ANAO("Anão", new EstrategiaStatusAnao());

    private final String nome;
    private final EstrategiaStatus estrategiaStatus;

    Raca(String nome, EstrategiaStatus estrategiaStatus) {
        this.nome = nome;
        this.estrategiaStatus = estrategiaStatus;
    }

    public String getNome() {
        return nome;
    }

    public EstrategiaStatus getEstrategiaStatus() {
        return estrategiaStatus;
    }
}