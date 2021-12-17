package cameo.impianto_balneare.Entity;

import javax.persistence.*;
import java.util.Date;
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

    //todo vedi bene queste cose aggiunte
    @Column
    private int lettiniNumber;

    @Column
    private int sdraioNumber;

    @Column
    private Date dataPrenotazione;

    //aggiungi la correlazione con la tabella ;) manytoone o onetomany nun sacc quale mettere
    @Column
    private User user;


}
