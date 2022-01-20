package cameo.impianto_balneare.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "componente_spiaggia")
@Getter
@Setter
public class ComponenteSpiaggia {
    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;
    @Column
    private String nome;
    @Column
    private int quantity;
    protected ComponenteSpiaggia() {
        this.id = UUID.randomUUID();
    }
}
