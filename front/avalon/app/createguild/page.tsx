"use client";

import { useEffect, useState } from "react";
import { User, Users } from "react-feather";

export default function CreateGuildPage() {
  interface Personagem {
    id: number;
    nome: string;
  }

  const [personagens, setPersonagens] = useState<Personagem[]>([]);
  const [liderId, setLiderId] = useState<number | "">("");

  const [nome, setNome] = useState("");
  const [descricao, setDescricao] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [created, setCreated] = useState(false);

  // === GET PERSONAGENS DO USUÁRIO ===
  useEffect(() => {
    fetch("http://localhost:8080/api/personagens")
      .then((res) => res.json())
      .then((data) => setPersonagens(data))
      .catch((err) => console.error("Erro ao buscar personagens:", err));
  }, []);

  // === SUBMIT CRIAÇÃO DE GUILDA ===
  async function onSubmit(e: React.FormEvent) {
    e.preventDefault();
    setLoading(true);

    try {
      const response = await fetch("http://localhost:8080/api/guildas", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          nome,
          descricao,
          liderId: Number(liderId),
        }),
      });

      if (!response.ok) {
        throw new Error("Falha ao criar guilda. Verifique o líder e tente novamente.");
      }

      setCreated(true);
      setMessage("Guilda criada com sucesso!");
    } catch (error: any) {
      setMessage(error.message);
      console.error(error);
    } finally {
      setLoading(false);
    }
  }

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
            <a href="/character" className="dash-link">
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

      {/* MAIN CONTENT */}
      <main className="dashboard-content">
        <div className="content-wrapper max-width-800">
          <h1 className="page-title">
            <i data-feather="users"></i> Criar Nova Guilda
          </h1>

          <p className="page-subtitle">
            Escolha um dos seus personagens para ser o líder da sua nova <span className="highlight-text">Guilda</span>.
          </p>

          <form onSubmit={onSubmit} className="create-form">
            <div className="form-group">
              <label>Nome da Guilda</label>
              <input
                type="text"
                placeholder="Ex: Aliança Arcana"
                value={nome}
                onChange={(e) => setNome(e.target.value)}
                required
              />
            </div>

            <div className="form-group">
              <label>Descrição</label>
              <textarea
                placeholder="Ex: Magos de elite!"
                value={descricao}
                onChange={(e) => setDescricao(e.target.value)}
                required
              ></textarea>
            </div>

            {/* SELECT DO LÍDER */}
            <div className="form-group">
              <label>Escolha o Líder (seus personagens)</label>

              {personagens.length === 0 ? (
                <p>Você precisa criar um personagem antes de criar uma guilda.</p>
              ) : (
                <select
                  value={liderId}
                  onChange={(e) => setLiderId(Number(e.target.value))}
                  required
                >
                  <option value="">Selecione um líder</option>
                  {personagens.map((p) => (
                    <option key={p.id} value={p.id}>
                      {p.nome}
                    </option>
                  ))}
                </select>
              )}
            </div>

            <button type="submit" className="btn-dashboard-primary" disabled={loading}>
              {loading ? "Criando..." : "Criar Guilda"}
            </button>

            {message && <p className="status-message">{message}</p>}

            {created && (
              <a href="/guild" className="btn-dashboard-primary">
                Ver Guildas
              </a>
            )}
          </form>
        </div>
      </main>
    </div>
  );
}
