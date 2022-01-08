package cameo.impianto_balneare.entity;

import cameo.impianto_balneare.view.GlobalExceptionHandler;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "DateTimeWrapper")
@Getter
@Setter
public class DateTimeWrapper implements GlobalExceptionHandler {
    @Id
    private UUID id;

    @Column
    private ZonedDateTime dateTime;

    @ManyToOne(targetEntity = Newsletter.class)
    private Newsletter newsletter;

    protected DateTimeWrapper() {
        id = UUID.randomUUID();
    }
}
