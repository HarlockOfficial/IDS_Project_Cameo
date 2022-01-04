package cameo.impianto_balneare.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "menu_element")
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

    @ManyToOne
    private MenuSection section;

    @ManyToMany(targetEntity = MenuOrder.class, mappedBy = "menuElements")
    private List<MenuOrder> orders;

    @Column
    private boolean isElementVisible;

    protected MenuElement() {
        this.id = UUID.randomUUID();
        orders = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public MenuSection getSection() {
        return section;
    }

    public void setSection(MenuSection section) {
        this.section = section;
    }

    public List<MenuOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<MenuOrder> orders) {
        this.orders = orders;
    }

    public boolean isElementVisible() {
        return isElementVisible;
    }

    public void setElementVisible(boolean elementVisible) {
        isElementVisible = elementVisible;
    }

    public void toggleElementVisibility(){
        isElementVisible = !isElementVisible;
    }
}
