package cameo.impianto_balneare.Entity;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "prenotazione_spiaggia")
public class PrenotazioneSpiaggia {
    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
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

    public PrenotazioneSpiaggia(Ombrellone ombrellone, int lettini, int sdraio, ZonedDateTime dataInizio, ZonedDateTime dataFine, Prenotazione prenotazione) {
        this();
        this.ombrellone = ombrellone;
        this.lettini = lettini;
        this.sdraio = sdraio;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.prenotazione = prenotazione;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Ombrellone getOmbrellone() {
        return ombrellone;
    }

    public void setOmbrellone(Ombrellone ombrellone) {
        this.ombrellone = ombrellone;
    }

    public int getLettini() {
        return lettini;
    }

    public void setLettini(int lettini) {
        this.lettini = lettini;
    }

    public int getSdraio() {
        return sdraio;
    }

    public void setSdraio(int sdraio) {
        this.sdraio = sdraio;
    }

    public ZonedDateTime getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(ZonedDateTime dataInizio) {
        this.dataInizio = dataInizio;
    }

    public ZonedDateTime getDataFine() {
        return dataFine;
    }

    public void setDataFine(ZonedDateTime dataFine) {
        this.dataFine = dataFine;
    }

    public Prenotazione getPrenotazione() {
        return prenotazione;
    }

    public void setPrenotazione(Prenotazione prenotazione) {
        this.prenotazione = prenotazione;
    }
}
