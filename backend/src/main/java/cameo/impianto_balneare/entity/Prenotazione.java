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
@Table(name = "prenotazione")
@Getter
@Setter
@JsonIgnoreProperties(value = {"user"}, allowSetters = true)
public class Prenotazione {

    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @Enumerated(EnumType.ORDINAL)
    private StatoPrenotazione statoPrenotazione;

    @Column(nullable = false)
    private ZonedDateTime date;

    @ManyToMany(targetEntity = Event.class, mappedBy = "prenotazione", cascade = CascadeType.ALL)
    private List<Event> eventiPrenotatiList;

    @OneToMany(targetEntity = PrenotazioneSpiaggia.class, mappedBy = "prenotazione")
    private List<PrenotazioneSpiaggia> spiaggiaPrenotazioniList;

    protected Prenotazione() {
        this.id = UUID.randomUUID();
        eventiPrenotatiList = new ArrayList<>();
        spiaggiaPrenotazioniList = new ArrayList<>();
    }
}
