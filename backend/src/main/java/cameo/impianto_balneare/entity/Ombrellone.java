package cameo.impianto_balneare.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ombrellone")
@Getter
@Setter
@JsonIgnoreProperties(value = {"listaOrdini", "listaPrenotazioni"}, allowSetters = true)
public class Ombrellone {

    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private int row;

    @Column
    private int column;

    @Column
    private float price;

    @Column
    private ZonedDateTime startDate;

    @Column
    private ZonedDateTime endDate;

    @OneToMany(mappedBy = "ombrellone", cascade = CascadeType.ALL)
    private List<PrenotazioneSpiaggia> listaPrenotazioni;

    @OneToMany(targetEntity = MenuOrder.class, mappedBy = "ombrellone")
    private List<MenuOrder> listaOrdini;

    protected Ombrellone() {
        id = UUID.randomUUID();
        listaPrenotazioni = new ArrayList<>();
        listaOrdini = new ArrayList<>();
    }
}
