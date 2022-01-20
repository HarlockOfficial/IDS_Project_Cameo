package cameo.impianto_balneare.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="menu_order")
@Getter
@Setter
public class MenuOrder {
    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private ZonedDateTime orderDateTime;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    @ManyToMany(targetEntity = MenuElement.class)
    private List<MenuElement> menuElements;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @ManyToOne(targetEntity = Ombrellone.class)
    private Ombrellone ombrellone;

    protected MenuOrder() {
        id = UUID.randomUUID();
        orderDateTime = ZonedDateTime.now();
        orderStatus = OrderStatus.ORDERED;
        menuElements = new ArrayList<>();
    }
}
