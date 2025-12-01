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
  Feather,
  Anchor,
  PlusCircle
} from "react-feather";

export default function GuildRanking() {
  interface Guilda{
    id: number;
    nome: string;
    descricao: string;
    liderId: number;
    membros: any;

  }
const [guildas, setGuildas] = useState<Guilda[]>([]);
  const [loading, setLoading] = useState(true);
  useEffect(() => {
    fetch("http://localhost:8080/api/guildas")
      .then((res) => {
        if (!res.ok) throw new Error("Erro ao buscar personagens");
        return res.json();
      })
      .then((data) => {
        console.log(data)
        setGuildas(data);
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
                <stop offset="0%" stopColor="#c376ff" stopOpacity="1" />
                <stop offset="100%" stopColor="#e5baff" stopOpacity="1" />
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

          <a href="/guild" className="dash-link active">
            <Users />
            <span>Guilda</span>
          </a>

          <a href="#" className="dash-link">
            <Rss />
            <span>Feed</span>
          </a>

          <a href="#" className="dash-link">
            <MessageSquare />
            <span>Taverna</span>
          </a>

          <a href="#" className="dash-link">
            <Box />
            <span>Inventário</span>
          </a>
        </div>

        <div className="dash-profile">
          <div className="avatar-circle">
            <User />
          </div>
          <a href="/" className="logout-link">
            <LogOut />
          </a>
        </div>
      </nav>

      <main className="dashboard-content">
        <div className="particles"></div>

        <div className="content-wrapper">
          <h1 className="page-title">
            <Zap /> Ranking Global de Guildas
          </h1>

          <p className="page-subtitle">
            As facções mais poderosas de Avalon em tempo real.
          </p>
        </div>

          {/* LISTA DE GUILDAS */}
          <div className="guild-list">
            {loading ? (
              <p>Carregando guildas...</p>
            ) : guildas.length === 0 ? (
              <p>Nenhuma guilda encontrada!</p>
            ) : (
            <>
            {guildas.map((g, index) => {
              return (
              <div className="guild-card">
              <span className="guild-rank rank-1">{index + 1}</span>

              <div className="guild-info">
                <div className="guild-icon">
                  <Aperture color="gold" />
                </div>

                <div className="guild-details">
                  <h3>{g.nome}</h3>
                  <p>Líder: {g.liderId}</p>
                  <span className="guild-motto">{g.descricao}</span>
                </div>
              </div>

              

              <button className="btn-view-guild">Ver Detalhes</button>
          </div>
            )})}
            </>
            )}
            </div>
            {/* 1° */}

          {/* PAGINAÇÃO */}
          <div className="pagination-controls">
            <button className="btn-secondary-page disabled">Anterior</button>
            <span className="page-number">Página 1 de 10</span>
            <button className="btn-secondary-page">Próxima</button>
          </div>

          {/* BOTÃO CRIAR GUILDA */}
          <a href="#" className="btn-dashboard-guild create-guild">
            <PlusCircle /> Crie Sua Própria Guilda
          </a>
      </main>
        </div>
    </div>
  );
}
