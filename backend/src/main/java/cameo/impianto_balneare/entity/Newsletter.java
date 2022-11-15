package cameo.impianto_balneare.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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

    @OneToOne(targetEntity = Event.class, cascade = CascadeType.ALL)
    private Event event;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(targetEntity = DateTimeWrapper.class, cascade = CascadeType.ALL)
    private Set<DateTimeWrapper> times;

    protected Newsletter() {
        this.id = UUID.randomUUID();
        times = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Newsletter that = (Newsletter) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
