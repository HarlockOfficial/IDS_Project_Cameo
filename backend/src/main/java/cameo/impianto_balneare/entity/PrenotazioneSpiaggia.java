package cameo.impianto_balneare.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "prenotazione_spiaggia")
@Getter
@Setter
@JsonIgnoreProperties(value = {"prenotazione"}, allowSetters = true)
public class PrenotazioneSpiaggia {
    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private int lettini;

    @Column
    private int sdraio;

    @Column
    private ZonedDateTime startDate;

    @Column
    private ZonedDateTime endDate;

    @ManyToOne(targetEntity = Ombrellone.class)
    private Ombrellone ombrellone;

    @ManyToOne(targetEntity = Prenotazione.class)
    private Prenotazione prenotazione;

    protected PrenotazioneSpiaggia() {
        this.id = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PrenotazioneSpiaggia that = (PrenotazioneSpiaggia) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
