package cameo.impianto_balneare.entity;

//import cameo.impianto_balneare.Quartz.Entity.MailQueue;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="newsletter")
@Getter
@Setter
public class Newsletter {
    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private String mailContent;

    @OneToOne(targetEntity = Event.class, mappedBy = "newsletter")
    private Event event;

    @OneToMany(targetEntity = DateTimeWrapper.class, mappedBy = "newsletter", cascade = CascadeType.ALL)
    private Set<DateTimeWrapper> times;

    protected Newsletter() {
        this.id = UUID.randomUUID();
        times = new HashSet<>();
    }
}
