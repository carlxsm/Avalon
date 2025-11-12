package br.com.avalon.avalonapi.domain.enums;

import br.com.avalon.avalonapi.domain.strategy.EstrategiaStatus;
import br.com.avalon.avalonapi.domain.strategy.EstrategiaStatusArqueiro;
import br.com.avalon.avalonapi.domain.strategy.EstrategiaStatusGuerreiro;
import br.com.avalon.avalonapi.domain.strategy.EstrategiaStatusMago;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public enum Classe {
    GUERREIRO("Guerreiro", new EstrategiaStatusGuerreiro(),
            Set.of(TipoItem.ARMA, TipoItem.ARMADURA_PESADA),
            Map.of(SlotEquipamento.MAO_PRINCIPAL, Set.of(TipoItem.ARMA),
                    SlotEquipamento.PEITO, Set.of(TipoItem.ARMADURA_PESADA))),
    MAGO("Mago", new EstrategiaStatusMago(),
            Set.of(TipoItem.ARMA, TipoItem.ARMADURA_LEVE),
            Map.of(SlotEquipamento.MAO_PRINCIPAL, Set.of(TipoItem.ARMA),
                    SlotEquipamento.PEITO, Set.of(TipoItem.ARMADURA_LEVE))),
    ARQUEIRO("Arqueiro", new EstrategiaStatusArqueiro(),
            Set.of(TipoItem.ARMA, TipoItem.ARMADURA_MEDIA),
            Map.of(SlotEquipamento.MAO_PRINCIPAL, Set.of(TipoItem.ARMA),
                    SlotEquipamento.PEITO, Set.of(TipoItem.ARMADURA_MEDIA)));

    private final String nome;
    private final EstrategiaStatus estrategiaStatus;
    private final Set<TipoItem> tiposItensPermitidos;
    private final Map<SlotEquipamento, Set<TipoItem>> slotsPermitidosPorTipo;

    Classe(String nome, EstrategiaStatus estrategiaStatus, Set<TipoItem> tiposItensPermitidos, Map<SlotEquipamento, Set<TipoItem>> slotsPermitidosPorTipo) {
        this.nome = nome;
        this.estrategiaStatus = estrategiaStatus;
        this.tiposItensPermitidos = Collections.unmodifiableSet(tiposItensPermitidos); // Torna imutável
        this.slotsPermitidosPorTipo = Collections.unmodifiableMap(slotsPermitidosPorTipo); // Torna imutável
    }

    public String getNome() {
        return nome;
    }

    public EstrategiaStatus getEstrategiaStatus() {
        return estrategiaStatus;
    }

    public boolean podeEquipar(TipoItem tipoItem, SlotEquipamento slotEquipamento) {
        return tiposItensPermitidos.contains(tipoItem) &&
                slotsPermitidosPorTipo.getOrDefault(slotEquipamento, Collections.emptySet()).contains(tipoItem);
    }
}