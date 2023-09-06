package kg.kelso.kelsobackend.dao.users;

import jakarta.transaction.Transactional;
import kg.kelso.kelsobackend.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password = :newPassword, u.mdt = (SELECT NOW()) WHERE u.id = :id")
    void updateEmailByUsername(@Param("id") Long userId, @Param("newPassword") String newPassword);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.is_block = true, u.mdt = (SELECT NOW()) WHERE u.id = :id")
    void blockUser(@Param("id") Long userId);

}
