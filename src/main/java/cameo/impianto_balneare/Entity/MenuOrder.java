package cameo.impianto_balneare.Entity;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="menu_order")
public class MenuOrder {
    @Id
    private UUID id;

    @Column
    private ZonedDateTime orderDateTime;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    @ManyToMany(targetEntity = MenuElement.class, mappedBy = "orders")
    private List<MenuElement> menuElements;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @ManyToOne(targetEntity = Ombrellone.class)
    private Ombrellone ombrellone;

    protected MenuOrder() {
        id = UUID.randomUUID();
        orderDateTime = ZonedDateTime.now();
        orderStatus = OrderStatus.ORDERED;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ZonedDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(ZonedDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<MenuElement> getMenuElements() {
        return menuElements;
    }

    public void setMenuElements(List<MenuElement> menuElements) {
        this.menuElements = menuElements;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ombrellone getOmbrellone() {
        return ombrellone;
    }

    public void setOmbrellone(Ombrellone ombrellone) {
        this.ombrellone = ombrellone;
    }
}
