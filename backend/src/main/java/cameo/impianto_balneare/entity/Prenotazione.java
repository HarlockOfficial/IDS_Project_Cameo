package cameo.impianto_balneare.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "prenotazione")
@Getter
@Setter
public class Prenotazione {

    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(targetEntity = User.class)
    private User utente;

    @Enumerated(EnumType.ORDINAL)
    private StatoPrenotazione statoPrenotazione;

    @Column(nullable = false)
    private ZonedDateTime data;

    @ManyToMany(targetEntity = Event.class, mappedBy = "prenotazione", cascade = CascadeType.ALL)
    private List<Event> prenotazioniEventi;

    @OneToMany(targetEntity = PrenotazioneSpiaggia.class, mappedBy = "prenotazione")
    private List<PrenotazioneSpiaggia> prenotazioneSpiaggia;

    protected Prenotazione() {
        this.id = UUID.randomUUID();
        prenotazioniEventi = new ArrayList<>();
        prenotazioneSpiaggia = new ArrayList<>();
    }
}
