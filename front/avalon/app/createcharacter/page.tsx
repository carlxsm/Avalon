"use client";

import { useState } from "react";
import { User } from "react-feather";

export default function CreateCharacterPage() {
  const [nome, setNome] = useState("");
  const [raca, setRaca] = useState("HUMANO");
  const [classe, setClasse] = useState("GUERREIRO");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  async function handleCreate(e: React.FormEvent) {
    e.preventDefault();
    setLoading(true);
    setMessage("");

    try {
      const response = await fetch("http://localhost:8080/personagens", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ nome, raca, classe }),
      });

      if (!response.ok) throw new Error("Erro ao criar personagem");

      setMessage("✅ Personagem criado com sucesso!");
      setNome("");
      setRaca("HUMANO");
      setClasse("GUERREIRO");
    } catch (err: any) {
      setMessage("❌ Erro ao criar personagem.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="dashboard-body">
      {/* NAVBAR – Mantida exatamente igual */}
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
          <a className="dash-link" href="/character">
            <User />
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
            <User />
          </div>

          <a className="logout-link" href="/">
            <i data-feather="log-out"></i>
          </a>
        </div>
      </nav>

      {/* MAIN CONTENT */}
      <main className="dashboard-content">
        <div className="particles"></div>

        <div className="content-wrapper max-width-800">
          <h1 className="page-title">
            <i data-feather="edit"></i> Criar Novo Personagem
          </h1>

          <p className="page-subtitle">
            Preencha as informações abaixo para criar o seu herói em <span className="highlight-text">Avalon</span>.
          </p>

          <form onSubmit={handleCreate} className="create-form">

            <div className="form-group">
              <label>Nome do Personagem</label>
              <input
                type="text"
                placeholder="Ex: Kratos_God"
                value={nome}
                onChange={(e) => setNome(e.target.value)}
                required
              />
            </div>

            <div className="form-group">
              <label>Raça</label>
              <select value={raca} onChange={(e) => setRaca(e.target.value)}>
                <option value="HUMANO">Humano</option>
                <option value="ELFO">Elfo</option>
                <option value="ANÃO">Anão</option>
                <option value="ORC">Orc</option>
              </select>
            </div>

            <div className="form-group">
              <label>Classe</label>
              <select value={classe} onChange={(e) => setClasse(e.target.value)}>
                <option value="GUERREIRO">Guerreiro</option>
                <option value="MAGO">Mago</option>
                <option value="ARQUEIRO">Arqueiro</option>
                <option value="PALADINO">Paladino</option>
              </select>
            </div>

            <button className="btn-dashboard-primary" disabled={loading}>
              {loading ? "Criando..." : "Criar Personagem"}
            </button>

            {message && <p className="status-message">{message}</p>}
          </form>
        </div>
      </main>
    </div>
  );
}
