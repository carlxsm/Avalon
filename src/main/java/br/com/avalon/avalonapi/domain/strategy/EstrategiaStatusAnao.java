package br.com.avalon.avalonapi.domain.strategy;

public class EstrategiaStatusAnao implements EstrategiaStatus {
    @Override
    public int getAtaqueMagicoBase(int nivel) { return 3 + nivel * 1; }
    @Override
    public int getAtaqueFisicoBase(int nivel) { return 12 + nivel * 4; }
    @Override
    public int getDefesaMagicaBase(int nivel) { return 2 + nivel * 1; }
    @Override
    public int getDefesaFisicaBase(int nivel) { return 10 + nivel * 3; }
    @Override
    public int getPrecisaoBase(int nivel) { return 6 + nivel * 2; }
    @Override
    public int getDestrezaBase(int nivel) { return 4 + nivel * 1; }
}