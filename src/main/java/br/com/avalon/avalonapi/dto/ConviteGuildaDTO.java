package br.com.avalon.avalonapi.dto;

public class ConviteGuildaDTO {
    private Long convidanteId;
    private Long convidadoId;

    public Long getConvidanteId() { return convidanteId; }
    public void setConvidanteId(Long convidanteId) { this.convidanteId = convidanteId; }
    public Long getConvidadoId() { return convidadoId; }
    public void setConvidadoId(Long convidadoId) { this.convidadoId = convidadoId; }
}