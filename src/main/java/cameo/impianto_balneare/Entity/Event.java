package cameo.impianto_balneare.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private String name;

    @Column
    private String location;

    @Column
    private Date date;

    @Column
    private String description;

    @Column
    private float price;

    @ManyToMany(targetEntity = Prenotazione.class, mappedBy = "prenotazioni")
    public List<Prenotazione> prenotazione;

    @OneToOne(targetEntity = Newsletter.class, mappedBy = "event")
    private Newsletter newsletter;

    protected Event() {
        id = UUID.randomUUID();
        prenotazione = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<Prenotazione> getPrenotazione() {
        return prenotazione;
    }

    public void setPrenotazione(List<Prenotazione> prenotazione) {
        this.prenotazione = prenotazione;
    }

    public Newsletter getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(Newsletter newsletter) {
        this.newsletter = newsletter;
    }
}
