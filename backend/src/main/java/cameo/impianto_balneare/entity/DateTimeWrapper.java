package cameo.impianto_balneare.entity;

import cameo.impianto_balneare.view.GlobalExceptionHandler;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "date_time_wrapper")
@Getter
@Setter
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
}
