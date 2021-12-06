package cameo.impianto_balneare.Entity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ombrellone")
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

    @ManyToMany(mappedBy = "ombrellone")
    private List<Prenotazione> listaPrenotazioni;

    @OneToMany(targetEntity = MenuOrder.class, mappedBy = "ombrellone")
    private List<MenuOrder> listaOrdini;

    protected Ombrellone() {
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public List<Prenotazione> getListaPrenotazioni() {
        return listaPrenotazioni;
    }

    public void setListaPrenotazioni(List<Prenotazione> listaPrenotazioni) {
        this.listaPrenotazioni = listaPrenotazioni;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getOmbrelloneRowNumber() {
        return ombrelloneRowNumber;
    }

    public void setOmbrelloneRowNumber(int row) {
        this.ombrelloneRowNumber = row;
    }

    public int getOmbrelloneColumnNumber() {
        return ombrelloneColumnNumber;
    }

    public void setOmbrelloneColumnNumber(int number) {
        this.ombrelloneColumnNumber = number;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }
}
