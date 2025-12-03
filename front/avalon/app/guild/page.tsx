"use client";

import { useEffect, useState } from "react";
import {
  User,
  Users,
  Rss,
  MessageSquare,
  Box,
  LogOut,
  Zap,
  Aperture,
  PlusCircle
} from "react-feather";

export default function GuildRanking() {
  interface Guilda {
    id: number;
    nome: string;
    descricao: string;
    liderId: number;
    membros: any;
  }

  interface Personagem {
    id: number;
    nome: string;
  }

  const [guildas, setGuildas] = useState<Guilda[]>([]);
  const [lideres, setLideres] = useState<{ [key: number]: string }>({});
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch("http://localhost:8080/api/guildas")
      .then((res) => {
        if (!res.ok) throw new Error("Erro ao buscar guildas");
        return res.json();
      })
      .then(async (data) => {
        setGuildas(data);

        // === CAPTURAR LÍDERES POR ID ===
        const ids = [...new Set(data.map((g: Guilda) => g.liderId))];

        const lideresTemp: { [key: number]: string } = {};

        for (const id of ids) {
          try {
            const res = await fetch(`http://localhost:8080/api/personagens/${id}`);
            if (res.ok) {
              const personagem: Personagem = await res.json();
              lideresTemp[id] = personagem.nome;
            } else {
              lideresTemp[id] = "Desconhecido";
            }
          } catch {
            lideresTemp[id] = "Erro";
          }
        }

        setLideres(lideresTemp);
      })
      .catch((err) => {
        console.error("Erro no GET:", err);
      })
      .finally(() => setLoading(false));
  }, []);

  return (
    <div className="guild-geral">
      <div className="dashboard-body">
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
            <a href="/character" className="dash-link">
              <User />
              <span>Meu Personagem</span>
            </a>
            <a href="/dashboard" className="dash-link">
                <i data-feather="rss"></i>
                <span>Dashboard</span>
            </a>
            <a href="/guild" className="dash-link active">
              <Users />
              <span>Guilda</span>
            </a>
          </div>
        </nav>

        <main className="dashboard-content">
          <div className="content-wrapper">
            <h1 className="page-title">
              <Zap /> Ranking Global de Guildas
            </h1>
            <p className="page-subtitle">As facções mais poderosas de Avalon em tempo real.</p>
          </div>

          {/* LISTA DE GUILDAS */}
          <div className="guild-list">
            {loading ? (
              <p>Carregando guildas...</p>
            ) : guildas.length === 0 ? (
              <p>Nenhuma guilda encontrada!</p>
            ) : (
              guildas.map((g, index) => (
                <div key={g.id} className="guild-card">
                  <span className="guild-rank rank-1">{index + 1}</span>

                  <div className="guild-info">
                    <div className="guild-icon">
                      <Aperture color="gold" />
                    </div>

                    <div className="guild-details">
                      <h3>{g.nome}</h3>

                      {/* ⬇️ AGORA AQUI MOSTRA O NOME DO LÍDER */}
                      <p>
                        Líder:{" "}
                        <strong>
                          {lideres[g.liderId] ? lideres[g.liderId] : "Carregando..."}
                        </strong>
                      </p>

                      <span className="guild-motto">{g.descricao}</span>
                    </div>
                  </div>

                  <button className="btn-view-guild">Ver Detalhes</button>
                </div>
              ))
            )}
          </div>

          <a href="createguild" className="btn-dashboard-guild create-guild">
            <PlusCircle /> Crie Sua Própria Guilda
          </a>
        </main>
      </div>
    </div>
  );
}
