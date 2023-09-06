package kg.kelso.kelsobackend.entities.user;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String username;

    String email;

    String password;

    String phone_num;

    Timestamp cdt;

    Timestamp mdt;

    Timestamp rdt;

    Boolean is_block;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String username, String email, String password, String phoneNum, Timestamp cdt, Boolean is_block) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone_num = phoneNum;
        this.cdt = cdt;
        this.is_block = is_block;
    }

}
