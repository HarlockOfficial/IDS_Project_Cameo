package cameo.impianto_balneare.entity;

import cameo.impianto_balneare.view.GlobalExceptionHandler;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "date_time_wrapper")
@Getter
@Setter
@JsonIgnoreProperties(value = {"newsletter"}, allowSetters = true)
public class DateTimeWrapper implements GlobalExceptionHandler {
    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private ZonedDateTime dateTime;

    @ManyToOne(targetEntity = Newsletter.class)
    private Newsletter newsletter;

    protected DateTimeWrapper() {
        id = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DateTimeWrapper that = (DateTimeWrapper) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
