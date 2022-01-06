package cameo.impianto_balneare.View;

import cameo.impianto_balneare.Entity.Newsletter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "DateTimeWrapper")
public class DateTimeWrapper {
    @Id
    private UUID id;

    @Column
    private ZonedDateTime dateTime;

    @ManyToOne(targetEntity = Newsletter.class)
    private Newsletter newsletter;

    protected DateTimeWrapper() {
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Newsletter getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(Newsletter newsletter) {
        this.newsletter = newsletter;
    }

}
