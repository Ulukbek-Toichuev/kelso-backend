package kg.kelso.kelsobackend.dao;

import kg.kelso.kelsobackend.entities.subscription.Subscribe;
import kg.kelso.kelsobackend.enums.SubscribeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscribeDao extends JpaRepository<Subscribe, Long> {

    @Query("select s from Subscribe s where s.rdt is null and s.status = :status")
    List<Subscribe> getByStatus(@Param("status") SubscribeStatus status);

    @Modifying
    @Query("update Subscribe s set s.status = :status where s.user.id = :id")
    void updateStatus(@Param("status") SubscribeStatus status, @Param("id") Long id);

    @Query("select s from Subscribe s where s.user.id = :id and s.rdt is null")
    Optional<Subscribe> getByUserId(@Param("id") Long id);

    @Query("select s from Subscribe s where s.user.id = :id and s.rdt is null and s.status = 'ACTIVE'")
    Optional<Subscribe> getActiveSubscribeByUserId(@Param("id") Long id);

    @Query("select s from Subscribe s where s.user.id = :id")
    List<Subscribe> getHistoryByUserId(@Param("id") Long id);

}
