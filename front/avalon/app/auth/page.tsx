'use client'

import { useEffect, useState } from "react";
import { Mail, Lock, User, ArrowLeft } from "react-feather"
import Script from "next/script"
import Link from "next/link";



export default function Auth() {
    const [mode, setMode] = useState<"login" | "register">("login");
    useEffect(() => {
        const params = new URLSearchParams(window.location.search);
        const initialMode = (params.get("mode") as "login" | "register") || "login";
        setMode(initialMode);
    }, []);


    return (
            <>
            <Script src="https://unpkg.com/feather-icons" strategy="afterInteractive" />
        <div className="auth-page">

            <a href="../" className="back-link">
                <ArrowLeft width={20} height={20} />
                Voltar
            </a>

            <div id="authContainer" className="auth-box">
                
                <svg className="logo-icon" width="40" height="40" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M12 2L3 7V17L12 22L21 17V7L12 2Z" stroke="url(#gradient-sword)" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                    <path d="M12 2V22" stroke="url(#gradient-sword)" strokeWidth="2" strokeLinecap="round"/>
                    <defs>
                        <linearGradient id="gradient-sword" x1="0%" y1="0%" x2="100%" y2="100%">
                            <stop offset="0%" style={{ stopColor: "#c376ff", stopOpacity: 1 }} />
                            <stop offset="100%" style={{ stopColor: "#e5baff", stopOpacity: 1 }} />
                        </linearGradient>
                    </defs>
                </svg>

                <h2 id="authTitle">
                    {mode === "login" ? "Bem-vindo de Volta" : "Junte-se ao Reino"}
                </h2>
                <p>{mode === "login" ? "Entre no reino épico" : "Crie seu herói"}</p>


                <div className="form-tabs">
                    <button 
                    id="loginTab"
                    className={mode === "login" ? "active" : ""}
                    onClick={() => setMode("login")}
                    >
                        Login
                    </button>
                    <button id="registerTab"
                    className={mode === "register" ? "active" : ""}
                    onClick={() => setMode("register")}
                    >Cadastrar</button>
                </div>
                {mode === "login" && (
                    <form id="loginForm" className="auth-form active">
                    <div className="form-group">
                        <label htmlFor="loginEmail">
                            <Mail />
                            Email
                        </label>
                        <input type="email" id="loginEmail" placeholder="seu@email.com" required />
                    </div>
                    <div className="form-group">
                        <label htmlFor="loginPassword">
                            <Lock />
                            Senha
                        </label>
                        <input type="password" id="loginPassword" placeholder="********" required />
                    </div>

                    <button type="submit" className="btn-auth-primary">Entrar no Reino</button>
                </form>
                )} 
                
                {mode === "register" && (
                 
                <form id="registerForm" className="auth-form">
                    <div className="form-group">
                        <label htmlFor="heroName">
                            <User />
                            Nome do Herói
                        </label>
                        <input type="text" id="heroName" placeholder="Digite seu nome" required />
                    </div>

                    <div className="form-group">
                        <label htmlFor="registerEmail">
                            <Mail />
                            Email
                        </label>
                        <input type="email" id="registerEmail" placeholder="seu@email.com" required />
                    </div>

                    <div className="form-group">
                        <label htmlFor="registerPassword">
                            <Lock />
                            Senha
                        </label>
                        <input type="password" id="registerPassword" placeholder="********" required />
                    </div>

                    <div className="form-group">
                        <label htmlFor="confirmPassword">
                            <Lock />
                            Confirmar Senha
                        </label>
                        <input type="password" id="confirmPassword" placeholder="********" required />
                    </div>

                    <button type="submit" className="btn-auth-primary">Começar Aventura</button>
                </form>
                )}
                
                {mode === "login" ? (
                    <a id="forgotPasswordLink" href="#" className="footer-link">Esqueceu a senha? Recuperar</a>
                ) : ( 
                <p id="termsText" className="terms-text">
                    Ao criar uma conta, você concorda com nossos <a href="#">Termos de Uso</a>
                </p>

                )}


            </div>
        </div>
        </>
    )
}
