package br.com.avalon.avalonapi.domain.strategy;

// Interface para a estratégia de status
public interface EstrategiaStatus {
    int getAtaqueMagicoBase(int nivel);
    int getAtaqueFisicoBase(int nivel);
    int getDefesaMagicaBase(int nivel);
    int getDefesaFisicaBase(int nivel);
    int getPrecisaoBase(int nivel);
    int getDestrezaBase(int nivel);

    int getPontosVidaBase(int nivel);
    int getPontosManaBase(int nivel);
}


// Enum Raca
