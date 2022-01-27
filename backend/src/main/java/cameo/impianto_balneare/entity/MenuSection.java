package cameo.impianto_balneare.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

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

    @OneToMany(targetEntity = MenuElement.class, mappedBy = "section", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<MenuElement> menuElementList;

    protected MenuSection() {
        this.id = UUID.randomUUID();
        menuElementList = new HashSet<>();
        isSectionVisible = true;
    }
}
