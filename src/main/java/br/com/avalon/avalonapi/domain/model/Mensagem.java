package br.com.avalon.avalonapi.domain.model;

import br.com.avalon.avalonapi.domain.enums.TipoMensagem;
import jakarta.persistence.*;

import javax.persistence.*;

@Entity
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long destinatarioId; // ID do personagem que receberá a mensagem
    private String titulo;
    private String conteudo;
    private boolean lida;

    @Enumerated(EnumType.STRING)
    private TipoMensagem tipo; // Enum: CONVITE_GUILDA, PROMOCAO_GUILDA, TRANSFERENCIA_LIDERANCA.
    private Long referenciaId; // ID da guilda, por exemplo, ou outro ID relevante para o tipo de mensagem

    public Mensagem() {}

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getDestinatarioId() { return destinatarioId; }
    public void setDestinatarioId(Long destinatarioId) { this.destinatarioId = destinatarioId; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
    public boolean isLida() { return lida; }
    public void setLida(boolean lida) { this.lida = lida; }
    public TipoMensagem getTipo() { return tipo; }
    public void setTipo(TipoMensagem tipo) { this.tipo = tipo; }
    public Long getReferenciaId() { return referenciaId; }
    public void setReferenciaId(Long referenciaId) { this.referenciaId = referenciaId; }
}