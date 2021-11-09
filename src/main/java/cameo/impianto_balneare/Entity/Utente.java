package cameo.impianto_balneare.Entity;

import javax.persistence.*;

@Entity
@Table(name="utente")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column
    private String username;

    @Column
    private String password;

    public Utente(){}

    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
