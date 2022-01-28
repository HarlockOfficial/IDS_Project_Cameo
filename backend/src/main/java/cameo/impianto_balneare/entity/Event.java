package cameo.impianto_balneare.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

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

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(targetEntity = Prenotazione.class, mappedBy = "eventiPrenotatiList")
    private Set<Prenotazione> prenotazione;

    @OneToOne
    private Newsletter newsletter;

    protected Event() {
        id = UUID.randomUUID();
        prenotazione = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Event event = (Event) o;
        return id != null && Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
