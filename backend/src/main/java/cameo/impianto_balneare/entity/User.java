package cameo.impianto_balneare.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@JsonIgnoreProperties(value = {"menuOrders", "prenotazioni", "password"}, allowSetters = true)
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

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(targetEntity = MenuOrder.class, mappedBy = "user", cascade = CascadeType.ALL)
    private Set<MenuOrder> menuOrders;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(targetEntity = Prenotazione.class, mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Prenotazione> prenotazioni;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(targetEntity = Token.class, mappedBy = "id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Token> token;

    protected User() {
        id = UUID.randomUUID();
        role = Role.USER;
        menuOrders = new HashSet<>();
        prenotazioni = new HashSet<>();
        token = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
