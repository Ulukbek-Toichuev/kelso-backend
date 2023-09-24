package kg.kelso.kelsobackend.dao;

import kg.kelso.kelsobackend.entities.subscription.SubscribeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeDetailsDao extends JpaRepository<SubscribeDetails, Long> {
}
