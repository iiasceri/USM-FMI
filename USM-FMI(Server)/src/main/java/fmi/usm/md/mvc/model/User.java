package fmi.usm.md.mvc.model;

import lombok.*;

import static fmi.usm.md.mvc.model.Status.ACTIVE;
import static fmi.usm.md.mvc.model.Privilege.USER;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString

@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "mail")
    private String mail;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = ACTIVE;

    @Column(name = "privilege")
    @Enumerated(EnumType.STRING)
    private Privilege privilege = USER;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
