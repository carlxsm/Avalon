package br.com.avalon.avalonapi.dto;

public class GuildaCriacaoDTO {
    private String nome;
    private String descricao;
    private Long liderId;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Long getLiderId() { return liderId; }
    public void setLiderId(Long liderId) { this.liderId = liderId; }
}