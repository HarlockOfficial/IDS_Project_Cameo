package cameo.impianto_balneare.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.*;

@Entity
@Table(name="menu_order")
@Getter
@Setter
public class MenuOrder {
    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private ZonedDateTime dateTime;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    @ManyToMany(targetEntity = MenuElement.class)
    private Set<MenuElement> menuElements;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @ManyToOne(targetEntity = Ombrellone.class)
    private Ombrellone ombrellone;

    protected MenuOrder() {
        id = UUID.randomUUID();
        dateTime = ZonedDateTime.now();
        orderStatus = OrderStatus.ORDERED;
        menuElements = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MenuOrder menuOrder = (MenuOrder) o;
        return id != null && Objects.equals(id, menuOrder.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
