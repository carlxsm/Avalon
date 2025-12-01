"use client";

import { useEffect, useState } from "react";
import { User } from "react-feather";
import Image from "next/image";


import Link from "next/link";
export default function CharacterPage() {
  interface Personagem{
    id: number;
    nome: string;
    raca: string;
    classe: string;
    nivel: number;
    experiencia: number;
    pontosVidaAtual: number;
    pontosVidaMax: number;
    pontosManaAtual: number;
    pontosManaMax: number;
    inventario: any;
    guilda: number;
  }
  const [personagens, setPersonagens] = useState<Personagem[]>([]);
  const [loading, setLoading] = useState(true);
  useEffect(() => {
    fetch("http://localhost:8080/api/personagens")
      .then((res) => {
        if (!res.ok) throw new Error("Erro ao buscar personagens");
        return res.json();
      })
      .then((data) => {
        setPersonagens(data);
      })
      .catch((err) => {
        console.error("Erro no GET:", err);
      })
      .finally(() => setLoading(false));
  }, []);


  return (
    <div className="dashboard-body">
      {/* NAVBAR */}
      <nav className="dash-navbar">
        <div className="dash-logo">
          <svg
            width="24"
            height="24"
            viewBox="0 0 24 24"
            fill="none"
            stroke="url(#grad1)"
            strokeWidth="2"
          >
            <path d="M12 2L3 7V17L12 22L21 17V7L12 2Z" />
            <defs>
              <linearGradient id="grad1" x1="0%" y1="0%" x2="100%" y2="100%">
                <stop offset="0%" style={{ stopColor: "#c376ff", stopOpacity: 1 }} />
                <stop offset="100%" style={{ stopColor: "#e5baff", stopOpacity: 1 }} />
              </linearGradient>
            </defs>
          </svg>

          <a className="logo-text dash-link" href="">Avalon</a>
        </div>

        <div className="dash-menu">
          <a className="dash-link active" href="/character">
            <User/>
            <span>Meu Personagem</span>
          </a>

          <a className="dash-link" href="/guild">
            <i data-feather="users"></i>
            <span>Guilda</span>
          </a>

          <a className="dash-link" href="#">
            <i data-feather="rss"></i>
            <span>Feed</span>
          </a>

          <a className="dash-link" href="#">
            <i data-feather="message-square"></i>
            <span>Taverna</span>
          </a>

          <a className="dash-link" href="#">
            <i data-feather="box"></i>
            <span>Inventário</span>
          </a>
        </div>

        <div className="dash-profile">
          <div className="avatar-circle">
            <User/>
          </div>

          <a className="logout-link" href="/">
            <i data-feather="log-out"></i>
          </a>
        </div>
      </nav>
      {/* MAIN CONTENT */}
          <div id="criarPerso">
            <Link className="btn-dashboard-primary" href="/createcharacter">
              Criar personagem!
              </Link>
          </div>
      <main className="dashboard-content">

        <div className="content-wrapper max-width-1000">
          <h1 className="page-title">
            <i data-feather="shield"></i> A Ficha do seu Herói
          </h1>
          {loading ? (
            <p>Carregando personagens...</p>
          ) : personagens.length === 0 ? (
            <p>Nenhum personagem encontrado.</p>
          ) : (
            <>
                          <p className="page-subtitle">
                Estatísticas, equipamentos e a história de{" "}
                <span className="highlight-text">
                  {personagens[0].nome}
                </span>.
              </p>

              <div className="character-sheet-container">
                {/* HEADER dinâmico */}
                <div className="character-sheet-header">
                  
                  <Image 
      
                  src="/teste.png" 
                  alt="Imagem do Personagem" 
                  className="character-avatar"
                  width={500}
                  height={500}
                  />

                  <div className="header-details">
                    <h2>{personagens[0].nome}</h2>
                    <span className="char-level">
                      Nível {personagens[0].nivel}
                    </span>
                    <p className="char-class">Classe: {personagens[0].classe}</p>
                  </div>
                </div>

                {/* STATUS dinamicamente */}
                <div className="character-stats-grid">
                  <div className="stat-block stat-hp">
                    <i data-feather="heart"></i>
                    <h4>HP (Vida)</h4>
                    <span className="stat-value">
                      {personagens[0].pontosVidaAtual} / {personagens[0].pontosVidaMax}
                    </span>
                    <div className="stat-bar">
                      <div
                        style={{
                          width:
                            (personagens[0].pontosVidaAtual /
                              personagens[0].pontosVidaMax) *
                              100 +
                            "%",
                        }}
                      ></div>
                    </div>
                  </div>

                  <div className="stat-block stat-mana">
                    <i data-feather="droplet"></i>
                    <h4>MP (Mana)</h4>
                    <span className="stat-value">
                      {personagens[0].pontosManaAtual} / {personagens[0].pontosManaMax}
                    </span>
                    <div className="stat-bar">
                      <div
                        style={{
                          width:
                            (personagens[0].pontosManaAtual /
                              personagens[0].pontosManaMax) *
                              100 +
                            "%",
                        }}
                      ></div>
                    </div>
                  </div>
                </div>
              </div>    
            </>
          )}
            

        </div>
      </main>
    </div>
  );
}
