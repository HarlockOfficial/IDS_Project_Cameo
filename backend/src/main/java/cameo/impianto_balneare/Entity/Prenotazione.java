package cameo.impianto_balneare.Entity;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "prenotazione")
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUtente() {
        return utente;
    }

    public void setUtente(User utente) {
        this.utente = utente;
    }

    public StatoPrenotazione getStatoPrenotazione() {
        return statoPrenotazione;
    }

    public void setStatoPrenotazione(StatoPrenotazione statoPrenotazione) {
        this.statoPrenotazione = statoPrenotazione;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public List<Event> getPrenotazioniEventi() {
        return prenotazioniEventi;
    }

    public void setPrenotazioniEventi(List<Event> prenotazioniEventi) {
        this.prenotazioniEventi = prenotazioniEventi;
    }

    public List<PrenotazioneSpiaggia> getPrenotazioneSpiaggia() {
        return prenotazioneSpiaggia;
    }

    public void setPrenotazioneSpiaggia(List<PrenotazioneSpiaggia> prenotazioneSpiaggia) {
        this.prenotazioneSpiaggia = prenotazioneSpiaggia;
    }
}
