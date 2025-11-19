package br.com.avalon.avalonapi.domain.strategy;

import br.com.avalon.avalonapi.domain.strategy.EstrategiaStatus;

public class EstrategiaStatusHumano implements EstrategiaStatus {
    @Override
    public int getAtaqueMagicoBase(int nivel) { return 5 + nivel * 2; }
    @Override
    public int getAtaqueFisicoBase(int nivel) { return 10 + nivel * 3; }
    @Override
    public int getDefesaMagicaBase(int nivel) { return 3 + nivel * 1; }
    @Override
    public int getDefesaFisicaBase(int nivel) { return 7 + nivel * 2; }
    @Override
    public int getPrecisaoBase(int nivel) { return 8 + nivel * 2; }
    @Override
    public int getDestrezaBase(int nivel) { return 6 + nivel * 2; }


    @Override public int getPontosVidaBase(int nivel) { return 100 + (nivel * 10); }
    @Override public int getPontosManaBase(int nivel) { return 50 + (nivel * 5); }
}