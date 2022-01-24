package cameo.impianto_balneare.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "menu_section")
@Getter
@Setter
@JsonIgnoreProperties(value = {"menuElementsList"}, allowSetters = true)
public class MenuSection {
    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private String sectionName;

    @OneToMany(targetEntity = MenuElement.class, mappedBy = "section", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MenuElement> menuElementsList;

    @Column
    private boolean isSectionVisible;

    protected MenuSection() {
        this.id = UUID.randomUUID();
        menuElementsList = new ArrayList<>();
        isSectionVisible = true;
    }
}
