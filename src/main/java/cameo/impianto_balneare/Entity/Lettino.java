package cameo.impianto_balneare.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "sdraio")
public class Lettino {
    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private float price;

    protected Lettino() {
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float prezzo) {
        this.price = prezzo;
    }
}
