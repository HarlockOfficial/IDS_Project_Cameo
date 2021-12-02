package cameo.impianto_balneare.Entity;

import javax.persistence.*;
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
    @JoinColumn(name = "id")
    private MenuSection section;

    protected MenuElement() {
        this.id = UUID.randomUUID();
    }

    public MenuElement(String name, String description, float price) {
        this();
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public MenuElement(UUID id, String name, String description, float price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public UUID getId() {
        return id;
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
}
