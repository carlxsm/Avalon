"use client";

import { useEffect, useState } from "react";
import { User, Users } from "react-feather";
import Image from "next/image";
import Link from "next/link";

export default function CharacterPage() {
  interface Personagem {
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
            <svg width="24" height="24" fill="none" stroke="url(#grad1)" strokeWidth="2">
              <path d="M12 2L3 7V17L12 22L21 17V7L12 2Z" />
              <defs>
                <linearGradient id="grad1" x1="0%" y1="0%" x2="100%" y2="100%">
                  <stop offset="0%" stopColor="#c376ff" />
                  <stop offset="100%" stopColor="#e5baff" />
                </linearGradient>
              </defs>
            </svg>
            <span className="logo-text">Avalon</span>
          </div>

          <div className="dash-menu">
            <a href="/character" className="dash-link active">
              <User />
              <span>Meu Personagem</span>
            </a>
            <a href="/dashboard" className="dash-link">
                <i data-feather="rss"></i>
                <span>Dashboard</span>
            </a>
            <a href="/guild" className="dash-link">
              <Users />
              <span>Guilda</span>
            </a>
          </div>
        </nav>

      {/* BOTÃO CRIAR */}
      <div id="criarPerso">
        <Link className="btn-dashboard-primary" href="/createcharacter">
          Criar personagem!
        </Link>
      </div>

      {/* MAIN */}
      <main className="dashboard-content">
        <div className="content-wrapper max-width-1000">
          <h1 className="page-title">A Ficha do seu Herói</h1>

          {loading ? (
            <p>Carregando personagens...</p>
          ) : personagens.length === 0 ? (
            <p>Nenhum personagem encontrado.</p>
          ) : (
            personagens.map((p) => (
              <div key={p.id} className="character-sheet-container">
                {/* CABEÇALHO */}
                <div className="character-sheet-header">
                  <Image
                    src="/ezreal.jpeg"
                    alt="Imagem do Personagem"
                    className="character-avatar"
                    width={500}
                    height={500}
                  />

                  <div className="header-details">
                    <h2>{p.nome}</h2>
                    <span className="char-level">Nível {p.nivel}</span>
                    <p className="char-class">Classe: {p.classe}</p>
                    <p className="char-class">Raça: {p.raca}</p>
                  </div>
                </div>

                {/* STATUS */}
                <div className="character-stats-grid">
                  <div className="stat-block stat-hp">
                    <h4>HP (Vida)</h4>
                    <span className="stat-value">
                      {p.pontosVidaAtual} / {p.pontosVidaMax}
                    </span>
                    <div className="stat-bar">
                      <div
                        style={{
                          width:
                            (p.pontosVidaAtual / p.pontosVidaMax) * 100 + "%",
                        }}
                      ></div>
                    </div>
                  </div>

                  <div className="stat-block stat-mana">
                    <h4>MP (Mana)</h4>
                    <span className="stat-value">
                      {p.pontosManaAtual} / {p.pontosManaMax}
                    </span>
                    <div className="stat-bar">
                      <div
                        style={{
                          width:
                            (p.pontosManaAtual / p.pontosManaMax) * 100 + "%",
                        }}
                      ></div>
                    </div>
                  </div>
                </div>
              </div>
            ))
          )}
        </div>
      </main>
    </div>
  );
}
