package cameo.impianto_balneare.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "event")
@Getter
@Setter
@JsonIgnoreProperties(value = {"prenotazione"}, allowSetters = true)
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

    @ManyToMany(targetEntity = Prenotazione.class, mappedBy = "eventiPrenotatiList")
    private Set<Prenotazione> prenotazione;

    @OneToOne
    private Newsletter newsletter;

    protected Event() {
        id = UUID.randomUUID();
        prenotazione = new HashSet<>();
    }
}
