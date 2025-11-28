package br.com.avalon.avalonapi.dto;

public class TransferenciaLiderancaDTO {
    private Long guildaId;
    private Long antigoLiderId;
    private Long novoLiderId;

    public Long getGuildaId() { return guildaId; }
    public void setGuildaId(Long guildaId) { this.guildaId = guildaId; }
    public Long getAntigoLiderId() { return antigoLiderId; }
    public void setAntigoLiderId(Long antigoLiderId) { this.antigoLiderId = antigoLiderId; }
    public Long getNovoLiderId() { return novoLiderId; }
    public void setNovoLiderId(Long novoLiderId) { this.novoLiderId = novoLiderId; }
}