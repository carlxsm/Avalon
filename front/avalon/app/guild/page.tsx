"use client";

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
  return (
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

          {/* LISTA DE GUILDAS */}
          <div className="guild-list">

            {/* 1° */}
            <div className="guild-card">
              <span className="guild-rank rank-1">#1</span>

              <div className="guild-info">
                <div className="guild-icon">
                  <Aperture color="gold" />
                </div>

                <div className="guild-details">
                  <h3>Os Filhos do Dragão</h3>
                  <p>Líder: Lord Kael</p>
                  <span className="guild-motto">"O fogo nos guia."</span>
                </div>
              </div>

              <div className="guild-stats">
                <p>
                  Membros: <span>98/100</span>
                </p>
                <p>
                  Poder: <span>1,200,500</span>
                </p>
              </div>

              <button className="btn-view-guild">Ver Detalhes</button>
            </div>

            {/* 2° */}
            <div className="guild-card">
              <span className="guild-rank rank-2">#2</span>

              <div className="guild-info">
                <div className="guild-icon">
                  <Feather color="silver" />
                </div>

                <div className="guild-details">
                  <h3>Fênix Negra</h3>
                  <p>Líder: Lady Elara</p>
                  <span className="guild-motto">"Renasceremos das cinzas."</span>
                </div>
              </div>

              <div className="guild-stats">
                <p>
                  Membros: <span>85/100</span>
                </p>
                <p>
                  Poder: <span>980,320</span>
                </p>
              </div>

              <button className="btn-view-guild">Ver Detalhes</button>
            </div>

            {/* 3° */}
            <div className="guild-card">
              <span className="guild-rank rank-3">#3</span>

              <div className="guild-info">
                <div className="guild-icon">
                  <Anchor color="#cd7f32" />
                </div>

                <div className="guild-details">
                  <h3>Legião de Ferro</h3>
                  <p>Líder: Comandante Vex</p>
                  <span className="guild-motto">"Nossa força é inquebrável."</span>
                </div>
              </div>

              <div className="guild-stats">
                <p>
                  Membros: <span>90/100</span>
                </p>
                <p>
                  Poder: <span>750,110</span>
                </p>
              </div>

              <button className="btn-view-guild">Ver Detalhes</button>
            </div>

          </div>

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
        </div>
      </main>
    </div>
  );
}
