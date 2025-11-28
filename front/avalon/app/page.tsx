import Image from "next/image";

export default function Home() {
  return (
    <div>
      <header className="navbar">
        <div className="logo">
<svg
  className="logo-icon"
  width="24"
  height="24"
  viewBox="0 0 24 24"
  fill="none"
  xmlns="http://www.w3.org/2000/svg"
>
  <path
    d="M12 2L3 7V17L12 22L21 17V7L12 2Z"
    stroke="url(#gradient1)"
    strokeWidth="2"
    strokeLinecap="round"
    strokeLinejoin="round"
  />
  <path
    d="M12 2V22"
    stroke="url(#gradient1)"
    strokeWidth="2"
    strokeLinecap="round"
  />
  <defs>
    <linearGradient id="gradient1" x1="0%" y1="0%" x2="100%" y2="100%">
      <stop offset="0%" stopColor="#c376ff" stopOpacity="1" />
      <stop offset="100%" stopColor="#e5baff" stopOpacity="1" />
    </linearGradient>
  </defs>
</svg>

            <span>Avalon RPG System</span>
        </div>

        <nav>
            <a href="#recursos">Recursos</a>
            <a href="#como-comecar">Como Começar</a>
            <a href="#comunidade">Comunidade</a>
        </nav>

        <div className="auth">
            <a className="login" href="/auth?mode=login">Login</a>
            <a className="cadastro" href="/auth?mode=register">Cadastrar</a>
        </div>
    </header>

    
    <section className="hero">
        <div className="hero-background">
            <div className="particles"></div>
        </div>
        
        <div className="hero-image-wrapper">
            <Image src="/images/guerreiro.jpg" className="hero-character" alt="Personagem RPG"
             width={500}
            height={500}
            />
            <div className="hero-image-overlay"></div>
        </div>
        <div className="floating-particles">
            <div className="particle"></div>
            <div className="particle"></div>
            <div className="particle"></div>
            <div className="particle"></div>
            <div className="particle"></div>
            <div className="particle"></div>
            <div className="particle"></div>
            <div className="particle"></div>
        </div>
        <div className="hero-glow"></div>

        <div className="hero-content">
            <h1>Forje Sua Lenda</h1>
            <p>
                Crie personagens épicos, forme guildas poderosas e domine o reino
                em uma experiência RPG incomparável.
            </p>

            <div className="hero-buttons">
                <a className="btn-primary" href="#">
                    <svg className="btn-icon" width="20" height="20" viewBox="0 0 20 20" fill="none">
                        <path d="M10 2L3 7V17L10 22L17 17V7L10 2Z" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round"/>
                    </svg>
                    <span>Começar Aventura</span>
                </a>
                <a className="btn-secondary" href="#">
                    <svg className="btn-icon" width="20" height="20" viewBox="0 0 20 20" fill="none">
                        <circle cx="7" cy="7" r="2.5" stroke="currentColor" strokeWidth="1.5"/>
                        <circle cx="13" cy="7" r="2.5" stroke="currentColor" strokeWidth="1.5"/>
                        <path d="M5 14C5 12 6.5 10.5 9 10.5C11.5 10.5 13 12 13 14" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round"/>
                    </svg>
                    <span>Explorar Sistema</span>
                </a>
            </div>
        </div>
    </section>
    <section id="recursos" className="recursos">
        <h2>Recursos do Sistema</h2>
        <p className="subtitle">
            Explore um mundo completo com sistemas interconectados e experiências épicas
        </p>

        <div className="recursos-grid">

            <div className="recurso-card">
                <Image src="/images/characters_feature.jpg" alt="Gerenciamento de Personagens"
                 width={500}
                height={500}
                />
                <div className="recurso-info">
                    <h3>Gerenciamento de Personagens</h3>
                    <p>Crie, customize e gerencie múltiplos personagens únicos. Visualize estatísticas, edite atributos e acompanhe o progresso de cada herói.</p>
                </div>
            </div>

            <div className="recurso-card">
                <Image src="/images/guild_feature.jpg" alt="Sistema de Guildas"
                width={500}
                height={500}
                />
                <div className="recurso-info">
                    <h3>Sistema de Guildas</h3>
                    <p>Forme ou junte-se a guildas poderosas. Gerencie membros, defina cargos, comunique-se via chat e construa sua reputação no reino.</p>
                </div>
            </div>

            <div className="recurso-card">
                <Image src="/images/item_feature.jpg" alt="Coleta de Itens Épicos"
                width={500}
                height={500}
                />
                <div className="recurso-info">
                    <h3>Coleta de Itens Épicos</h3>
                    <p>Colete, equipe e gerencie equipamentos lendários. Descarte itens desnecessários e construa o arsenal perfeito para sua jornada.</p>
                </div>
            </div>

            <div className="recurso-card">
              
                <Image src="/images/progression_feature.jpg" alt=""
                width={500}
                height={500}
                />
                <div className="recurso-info">
                    <h3>Sistema de Progressão</h3>
                    <p>Ganhe experiência através de suas conquistas, suba de nível e desbloqueie novas habilidades para se tornar cada vez mais poderoso.</p>
                </div>
            </div>

        </div>
    </section>


    <section id="como-comecar" className="como-comecar">
        <h2>Como Começar</h2>
        <p className="subtitle">Quatro passos simples para iniciar sua jornada épica</p>

        <div className="steps">

            <div className="step-card">
                <span className="step-number">01</span>
                <div className="step-icon">
                    <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                        <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"></path>
                        <circle cx="9" cy="7" r="4"></circle>
                        <line x1="19" y1="8" x2="19" y2="14"></line>
                        <line x1="22" y1="11" x2="16" y2="11"></line>
                    </svg>
                </div>
                <h3>Crie Sua Conta</h3>
                <p>Registre-se rapidamente e tenha acesso completo ao sistema. Processo simples e seguro.</p>
            </div>

            <div className="step-card">
                <span className="step-number">02</span>
                <div className="step-icon">
                    <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                        <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                        <circle cx="12" cy="7" r="4"></circle>
                        <circle cx="12" cy="7" r="2" fill="currentColor"></circle>
                    </svg>
                </div>
                <h3>Forje Seu Personagem</h3>
                <p>Crie seu primeiro herói, customize atributos e escolha seu caminho no mundo épico.</p>
            </div>

            <div className="step-card">
                <span className="step-number">03</span>
                <div className="step-icon">
                    <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                        <path d="M12 2L10 6h4L12 2z"/>
                        <line x1="12" y1="6" x2="12" y2="18"/>
                        <path d="M8 18L6 20h12l-2-2"/>
                        <path d="M10 18h4v2h-4z"/>
                        <circle cx="12" cy="12" r="1" fill="currentColor"/>
                    </svg>
                </div>
                <h3>Entre em Ação</h3>
                <p>Comece sua jornada coletando itens, ganhando experiência e conquistando territórios.</p>
            </div>

            <div className="step-card">
                <span className="step-number">04</span>
                <div className="step-icon">
                    <svg width="40" height="40" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M5 16h14v2H5z"/>
                        <path d="M8 16h8v1H8z"/>
                        <path d="M12 2L9 8h6L12 2z"/>
                        <path d="M8 8L6 12h4L8 8z"/>
                        <path d="M16 8l2 4h-4l2-4z"/>
                        <path d="M10 8h4v4h-4z"/>
                        <circle cx="12" cy="10" r="1" fill="white"/>
                        <circle cx="9" cy="12" r="0.7" fill="white"/>
                        <circle cx="15" cy="12" r="0.7" fill="white"/>
                    </svg>
                </div>
                <h3>Construa Seu Legado</h3>
                <p>Forme ou junte-se a uma guilda, lidere aliados e torne-se uma lenda viva.</p>
            </div>

        </div>

        <div className="cta-final">
            <h2>Pronto Para Começar?</h2>
            <p>Junte-se a milhares de jogadores e forje sua lenda. Crie seu personagem agora e comece sua jornada épica gratuitamente.</p>

            <div className="cta-buttons">
                <a className="btn-primary-cta" href="/auth?mode=register">
                    <span>Criar Conta Grátis</span>
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                        <line x1="5" y1="12" x2="19" y2="12"></line>
                        <polyline points="12 5 19 12 12 19"></polyline>
                    </svg>
                </a>
                <a className="btn-secondary-cta" href="/auth?mode=login">Fazer Login</a>
            </div>
        </div>
    </section>

    </div>
  );
}
