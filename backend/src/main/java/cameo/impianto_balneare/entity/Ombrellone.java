package cameo.impianto_balneare.entity;

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
public class Ombrellone {

    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private int ombrelloneRowNumber;

    @Column
    private int ombrelloneColumnNumber;

    @Column
    private float prezzo;

    @Column
    private ZonedDateTime dataInizio;

    @Column
    private ZonedDateTime dataFine;

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
