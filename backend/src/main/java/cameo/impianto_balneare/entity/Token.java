package cameo.impianto_balneare.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "token")
@Getter
@Setter
public class Token {
    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "token")
    private User user;

    protected Token() {
        id = UUID.randomUUID();
    }

    public Token(User user) {
        this();
        this.user = user;
    }
}
