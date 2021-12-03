package cameo.impianto_balneare.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "sdraio")
public class Sdraio {

    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private float prezzo;

    protected Sdraio() {
        id = UUID.randomUUID();
    }

    public Sdraio(UUID id, float prezzo) {
        this.id = id;
        this.prezzo = prezzo;
    }

    public Sdraio(float prezzo) {
        this.id = id;
        this.prezzo = prezzo;
    }

    public UUID getId() {
        return id;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }
}
