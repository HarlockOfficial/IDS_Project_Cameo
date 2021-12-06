package cameo.impianto_balneare.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "menu_section")
public class MenuSection {
    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private String sectionName;

    @OneToMany(targetEntity = MenuElement.class, mappedBy = "section", cascade = CascadeType.ALL)
    private final List<MenuElement> elements;

    @Column
    private boolean isSectionVisible;

    protected MenuSection() {
        this.id = UUID.randomUUID();
        elements = new ArrayList<>();
        isSectionVisible = true;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<MenuElement> getElements() {
        return elements;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<MenuElement> getMenuElements() {
        return elements;
    }

    public boolean isSectionVisible() {
        return isSectionVisible;
    }

    public void setSectionVisible(boolean sectionVisible) {
        isSectionVisible = sectionVisible;
    }

    public void toggleVisibility(){
        isSectionVisible = !isSectionVisible;
    }
}
