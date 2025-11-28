"use client";

import { useEffect } from "react";
import { User } from "react-feather";

export default function CharacterPage() {
  

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

          <span className="logo-text">Avalon</span>
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
      <main className="dashboard-content">
        <div className="particles"></div>

        <div className="content-wrapper max-width-1000">
          <h1 className="page-title">
            <i data-feather="shield"></i> A Ficha do seu Herói
          </h1>

          <p className="page-subtitle">
            Estatísticas, equipamentos e a história de{" "}
            <span className="highlight-text">Sir Galahad</span>.
          </p>

          <div className="character-sheet-container">
            {/* HEADER */}
            <div className="character-sheet-header">
              <img
                src="/guerreiro.jpg"
                alt="Imagem do Personagem"
                className="character-avatar"
              />

              <div className="header-details">
                <h2>Sir Galahad, O Inabalável</h2>
                <span className="char-level">Nível 42</span>
                <p className="char-class">Classe: Paladino Sagrado</p>
                <p className="char-guild">Guilda: Os Filhos do Dragão</p>
              </div>

              <div className="header-actions">
                <button className="btn-dashboard-primary">
                  <i data-feather="edit-3"></i> Editar Ficha
                </button>

                <button className="btn-dashboard-guild">
                  <i data-feather="aperture"></i> Ver Inventário
                </button>
              </div>
            </div>

            {/* GRID DE STATUS */}
            <div className="character-stats-grid">
              <div className="stat-block stat-hp">
                <i data-feather="heart"></i>
                <h4>HP (Vida)</h4>
                <span className="stat-value">4500 / 4500</span>
                <div className="stat-bar">
                  <div style={{ width: "100%" }}></div>
                </div>
              </div>

              <div className="stat-block stat-mana">
                <i data-feather="droplet"></i>
                <h4>MP (Mana)</h4>
                <span className="stat-value">1200 / 1500</span>
                <div className="stat-bar">
                  <div style={{ width: "80%", background: "#007bff" }}></div>
                </div>
              </div>

              <div className="stat-block stat-attack">
                <i data-feather="activity"></i>
                <h4>Ataque</h4>
                <span className="stat-value">345</span>
              </div>

              <div className="stat-block stat-defense">
                <i data-feather="lock"></i>
                <h4>Defesa</h4>
                <span className="stat-value">280</span>
              </div>

              <div className="stat-block stat-crit">
                <i data-feather="crosshair"></i>
                <h4>Chance Crítica</h4>
                <span className="stat-value">25%</span>
              </div>

              <div className="stat-block stat-speed">
                <i data-feather="zap"></i>
                <h4>Velocidade</h4>
                <span className="stat-value">12.5</span>
              </div>
            </div>

            {/* EQUIPAMENTOS */}
            <div className="char-equipment">
              <h3>Equipamento Atual</h3>

              <div className="equipment-slots">
                <div className="slot">
                  <i data-feather="pocket"></i>
                  <span className="slot-name">Arma Principal</span>
                  <span className="slot-item item-legendary">
                    Espada do Rei Arthur +5
                  </span>
                </div>

                <div className="slot">
                  <i data-feather="shield"></i>
                  <span className="slot-name">Armadura</span>
                  <span className="slot-item item-epic">Placa da Luz Sagrada</span>
                </div>

                <div className="slot">
                  <i data-feather="award"></i>
                  <span className="slot-name">Colar</span>
                  <span className="slot-item item-rare">Amuleto do Poder</span>
                </div>

                <div className="slot">
                  <i data-feather="maximize"></i>
                  <span className="slot-name">Anel 1</span>
                  <span className="slot-item item-common">Anel de Ferro</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}
