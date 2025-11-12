package br.com.avalon.avalonapi.domain.strategy;

public class EstrategiaStatusGuerreiro implements EstrategiaStatus {
    @Override
    public int getAtaqueMagicoBase(int nivel) { return 2 + nivel * 1; }
    @Override
    public int getAtaqueFisicoBase(int nivel) { return 15 + nivel * 4; }
    @Override
    public int getDefesaMagicaBase(int nivel) { return 5 + nivel * 2; }
    @Override
    public int getDefesaFisicaBase(int nivel) { return 12 + nivel * 3; }
    @Override
    public int getPrecisaoBase(int nivel) { return 8 + nivel * 2; }
    @Override
    public int getDestrezaBase(int nivel) { return 6 + nivel * 1; }
}