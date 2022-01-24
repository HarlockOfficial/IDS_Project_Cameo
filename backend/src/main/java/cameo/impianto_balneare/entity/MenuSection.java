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
@JsonIgnoreProperties(value = {"menuElementList"}, allowSetters = true)
public class MenuSection {
    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private String sectionName;

    @Column
    private boolean isSectionVisible;

    @OneToMany(targetEntity = MenuElement.class, mappedBy = "section", cascade = CascadeType.ALL)
    private List<MenuElement> menuElementList;

    protected MenuSection() {
        this.id = UUID.randomUUID();
        menuElementList = new ArrayList<>();
        isSectionVisible = true;
    }
}
