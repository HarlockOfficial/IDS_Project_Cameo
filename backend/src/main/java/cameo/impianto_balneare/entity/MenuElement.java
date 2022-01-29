package cameo.impianto_balneare.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "menu_element")
@Getter
@Setter
@JsonIgnoreProperties(value = {"orders"}, allowSetters = true)
public class MenuElement {
    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private float price;

    @ManyToOne (fetch = FetchType.EAGER)
    private MenuSection section;

    @ManyToMany(targetEntity = MenuOrder.class, mappedBy = "menuElements")
    private Set<MenuOrder> orders;

    @Column
    private boolean isElementVisible;

    protected MenuElement() {
        this.id = UUID.randomUUID();
        orders = new HashSet<>();
        isElementVisible = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MenuElement that = (MenuElement) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
