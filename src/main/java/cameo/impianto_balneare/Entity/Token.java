package cameo.impianto_balneare.Entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "token")
public class Token {
    @Id
    private UUID token;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private User user;

    protected Token() {}

    public Token(User user) {
        this.token = UUID.randomUUID();
        this.user = user;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
