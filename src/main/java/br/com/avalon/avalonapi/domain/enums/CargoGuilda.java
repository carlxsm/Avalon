package br.com.avalon.avalonapi.domain.enums;

public enum CargoGuilda {
    LIDER("Líder", true, true, true, true, true) { // Pode convidar, expulsar, mudar descrição, promover, transferir liderança
        @Override
        public boolean podePromover(CargoGuilda novoCargo) {
            return true; // Líder pode promover para qualquer cargo
        }
    },
    OFICIAL("Oficial", true, true, true, false, false) { // Pode convidar, expulsar, mudar descrição
        @Override
        public boolean podePromover(CargoGuilda novoCargo) {
            return novoCargo.ordinal() < this.ordinal(); // Oficial pode promover para cargos de menor hierarquia
        }
    },
    MEMBRO("Membro", false, false, false, false, false) {
        @Override
        public boolean podePromover(CargoGuilda novoCargo) {
            return false;
        }
    };

    private final String nome;
    private final boolean podeConvidar;
    private final boolean podeExpulsar;
    private final boolean podeMudarDescricao;
    private final boolean podePromoverMembros;
    private final boolean podeTransferirLideranca;

    CargoGuilda(String nome, boolean podeConvidar, boolean podeExpulsar,
                boolean podeMudarDescricao, boolean podePromoverMembros,
                boolean podeTransferirLideranca) {
        this.nome = nome;
        this.podeConvidar = podeConvidar;
        this.podeExpulsar = podeExpulsar;
        this.podeMudarDescricao = podeMudarDescricao;
        this.podePromoverMembros = podePromoverMembros;
        this.podeTransferirLideranca = podeTransferirLideranca;
    }

    public String getNome() { return nome; }
    public boolean podeConvidar() { return podeConvidar; }
    public boolean podeExpulsar() { return podeExpulsar; }
    public boolean podeMudarDescricao() { return podeMudarDescricao; }
    public boolean podePromoverMembros() { return podePromoverMembros; }
    public boolean podeTransferirLideranca() { return podeTransferirLideranca; }

    // Método abstrato para ser implementado por cada cargo
    public abstract boolean podePromover(CargoGuilda novoCargo);
}