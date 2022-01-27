package cameo.impianto_balneare.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.*;

@Entity
@Table(name = "prenotazione")
@Getter
@Setter
@JsonIgnoreProperties(value = {"user"}, allowSetters = true)
public class Prenotazione {

    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(targetEntity = User.class)
    private User user;

    @Enumerated(EnumType.ORDINAL)
    private StatoPrenotazione statoPrenotazione;

    @Column(nullable = false)
    private ZonedDateTime date;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(targetEntity = Event.class, mappedBy = "prenotazione", cascade = CascadeType.ALL)
    private Set<Event> eventiPrenotatiList;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(targetEntity = PrenotazioneSpiaggia.class, mappedBy = "prenotazione")
    private Set<PrenotazioneSpiaggia> spiaggiaPrenotazioniList;

    protected Prenotazione() {
        this.id = UUID.randomUUID();
        eventiPrenotatiList = new HashSet<>();
        spiaggiaPrenotazioniList = new HashSet<>();
    }
}
