import br.com.avalon.avalonapi.domain.enums.Atributo;
import br.com.avalon.avalonapi.domain.enums.SlotEquipamento;
import br.com.avalon.avalonapi.domain.enums.TipoItem;
import jakarta.persistence.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @Enumerated(EnumType.STRING)
    private TipoItem tipo;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "item_atributos", joinColumns = @JoinColumn(name = "item_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "atributo_nome")
    @Column(name = "valor")
    private Map<Atributo, Integer> atributos; // ATAQUE_FISICO -> 10, DEFESA_MAGICA -> 5

    @Enumerated(EnumType.STRING)
    private SlotEquipamento slotEquipamento; // Null para itens não equipáveis

    public Item() {
        this.atributos = new HashMap<>(); // Garante que o mapa não seja nulo
    }

    public Item(String nome, TipoItem tipo, Map<Atributo, Integer> atributos, SlotEquipamento slotEquipamento) {
        this.nome = nome;
        this.tipo = tipo;
        this.atributos = (atributos != null) ? new HashMap<>(atributos) : new HashMap<>();
        this.slotEquipamento = slotEquipamento;
    }

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public TipoItem getTipo() { return tipo; }
    public void setTipo(TipoItem tipo) { this.tipo = tipo; }
    public Map<Atributo, Integer> getAtributos() { return atributos; }
    public void setAtributos(Map<Atributo, Integer> atributos) { this.atributos = atributos; }
    public SlotEquipamento getSlotEquipamento() { return slotEquipamento; }
    public void setSlotEquipamento(SlotEquipamento slotEquipamento) { this.slotEquipamento = slotEquipamento; }


    public boolean isEquipavel() {
        return tipo == TipoItem.EQUIPAMENTO && slotEquipamento != null;
    }
}