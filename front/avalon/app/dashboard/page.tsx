"use client";

import {
  User,
  Users,
  Rss,
  MessageSquare,
  Box,
  LogOut,
  PlusCircle,
  Shield
} from "react-feather";

export default function DashboardPage() {
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
                <stop offset="0%" stopColor="#c376ff" stopOpacity="1" />
                <stop offset="100%" stopColor="#e5baff" stopOpacity="1" />
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

          <a href="/guild" className="dash-link">
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

      {/* MAIN */}
      <main className="dashboard-content">
        <div className="particles"></div>

        <div className="welcome-container">
          <div className="welcome-card">
            <div className="card-glow"></div>

            <h1>Boas-vindas à Avalon, aventureiro!</h1>
            <p className="subtitle">Sua lenda começa agora.</p>

            {/* Criar personagem */}
            <div className="action-section">
              <p>Para começarmos, que tal criarmos seu personagem?</p>

              <a href="/characters" className="btn-dashboard-primary">
                <PlusCircle />
                Crie seu Personagem
              </a>
            </div>

            <div className="divider">
              <span>OU</span>
            </div>

            {/* Ver guildas */}
            <div className="action-section secondary">
              <p>Interaja com outros personagens!</p>

              <a href="/guilds" className="btn-dashboard-guild">
                <Shield />
                Veja as Guildas
              </a>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}
