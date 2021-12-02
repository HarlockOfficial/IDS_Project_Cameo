package cameo.impianto_balneare.Entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "token")
public class Token {
    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private User user;

    protected Token() {
        id = UUID.randomUUID();
    }

    public Token(User user) {
        this();
        this.user = user;
    }

    public Token(UUID id, User user) {
        this.id = id;
        this.user = user;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
}
