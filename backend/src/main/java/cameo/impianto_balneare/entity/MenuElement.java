package cameo.impianto_balneare.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private List<MenuOrder> orders;

    @Column
    private boolean isElementVisible;

    protected MenuElement() {
        this.id = UUID.randomUUID();
        orders = new ArrayList<>();
    }
}
