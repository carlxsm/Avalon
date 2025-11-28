package br.com.avalon.avalonapi.domain.strategy;

public class EstrategiaStatusElfo implements EstrategiaStatus {
    @Override
    public int getAtaqueMagicoBase(int nivel) { return 8 + nivel * 3; }
    @Override
    public int getAtaqueFisicoBase(int nivel) { return 6 + nivel * 2; }
    @Override
    public int getDefesaMagicaBase(int nivel) { return 4 + nivel * 2; }
    @Override
    public int getDefesaFisicaBase(int nivel) { return 5 + nivel * 1; }
    @Override
    public int getPrecisaoBase(int nivel) { return 7 + nivel * 2; }
    @Override
    public int getDestrezaBase(int nivel) { return 9 + nivel * 3; }

    @Override public int getPontosVidaBase(int nivel) { return 80 + (nivel * 8); }
    @Override public int getPontosManaBase(int nivel) { return 100 + (nivel * 10); }
}