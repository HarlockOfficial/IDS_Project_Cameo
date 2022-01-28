package cameo.impianto_balneare.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.*;

@Entity
@Table(name = "ombrellone")
@Getter
@Setter
@JsonIgnoreProperties(value = {"listaOrdini", "listaPrenotazioni"}, allowSetters = true)
public class Ombrellone {

    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private int numberRow;

    @Column
    private int numberColumn;

    @Column
    private float price;

    @Column
    private ZonedDateTime startDate;

    @Column
    private ZonedDateTime endDate;

    @OneToMany(mappedBy = "ombrellone", cascade = CascadeType.ALL)
    private Set<PrenotazioneSpiaggia> listaPrenotazioni;

    @OneToMany(targetEntity = MenuOrder.class, mappedBy = "ombrellone")
    private Set<MenuOrder> listaOrdini;

    protected Ombrellone() {
        id = UUID.randomUUID();
        listaPrenotazioni = new HashSet<>();
        listaOrdini = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ombrellone that = (Ombrellone) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
