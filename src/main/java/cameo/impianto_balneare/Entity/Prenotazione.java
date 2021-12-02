package cameo.impianto_balneare.Entity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "prenotazione")
public class Prenotazione {

    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToMany
    @JoinTable(name = "ombrelloni_list",joinColumns = @JoinColumn (name = "ombrellone_id"),
    inverseJoinColumns = @JoinColumn (name = "id"))
    private List<Ombrellone> ombrellone;


}
