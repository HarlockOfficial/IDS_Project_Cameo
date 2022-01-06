package cameo.impianto_balneare.Entity;

import cameo.impianto_balneare.View.DateTimeWrapper;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="newsletter")
public class Newsletter {
    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private String mailContent;

    @OneToOne(targetEntity = Event.class, mappedBy = "newsletter")
    private Event event;

    @OneToMany(targetEntity = DateTimeWrapper.class, mappedBy = "newsletter", cascade = CascadeType.ALL)
    private List<DateTimeWrapper> times;

    protected Newsletter() {
        this.id = UUID.randomUUID();
        times = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<DateTimeWrapper> getTimes() {
        return times;
    }

    public void setTimes(List<DateTimeWrapper> times) {
        this.times = times;
    }
}
