package kg.kelso.kelsobackend.dao;

import kg.kelso.kelsobackend.entities.user.Role;
import kg.kelso.kelsobackend.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(ERole name);
}
