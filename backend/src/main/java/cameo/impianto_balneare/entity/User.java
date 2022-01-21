package cameo.impianto_balneare.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user")
@Getter
@Setter
@JsonIgnoreProperties(value = {"menuOrders", "prenotazioni", "token", "password"})
public class User {
    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private String username;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    @Email
    private String email;

    @Column
    private String password;

    @Column
    private Date birthDate;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @OneToMany(targetEntity = MenuOrder.class, mappedBy = "user")
    private List<MenuOrder> menuOrders;

    @OneToMany(targetEntity = Prenotazione.class, mappedBy = "utente")
    private List<Prenotazione> prenotazioni;

    @OneToMany(targetEntity = Token.class, mappedBy = "id")
    private List<Token> token;

    protected User() {
        id = UUID.randomUUID();
        role = Role.USER;
        menuOrders = new ArrayList<>();
        prenotazioni = new ArrayList<>();
    }
}
