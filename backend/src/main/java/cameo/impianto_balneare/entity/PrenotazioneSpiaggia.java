package cameo.impianto_balneare.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "prenotazione_spiaggia")
@Getter
@Setter
public class PrenotazioneSpiaggia {
    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(targetEntity = Ombrellone.class)
    private Ombrellone ombrellone;

    @Column
    private int lettini;

    @Column
    private int sdraio;

    @Column
    private ZonedDateTime dataInizio;

    @Column
    private ZonedDateTime dataFine;

    @ManyToOne(targetEntity = Prenotazione.class)
    private Prenotazione prenotazione;

    protected PrenotazioneSpiaggia() {
        this.id = UUID.randomUUID();
    }
}
