package kg.kelso.kelsobackend.service.subscribe;

import kg.kelso.kelsobackend.dao.SubscribeDao;
import kg.kelso.kelsobackend.dao.UserDao;
import kg.kelso.kelsobackend.entities.subscription.Subscribe;
import kg.kelso.kelsobackend.entities.user.User;
import kg.kelso.kelsobackend.enums.SubscribeStatus;
import kg.kelso.kelsobackend.model.subscription.SubscribeModelRequest;
import kg.kelso.kelsobackend.model.subscription.SubscribeModelResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class SubscribeServiceImpl implements SubscribeService{

    @Autowired
    UserDao userDao;

    @Autowired
    SubscribeDao subscribeDao;

    @Override
    public void saveSubscribe(SubscribeModelRequest model) {
        Optional<User> optionalUser = userDao.findById(model.getUserId());

        optionalUser.ifPresent(user -> {
            if (!user.getIs_block()){
                subscribeDao.save(new Subscribe(
                        null,
                        user,
                        SubscribeStatus.WAITING,
                        new BigDecimal(String.valueOf(model.getDeposit())),
                        new BigDecimal(String.valueOf(model.getPrice())),
                        new Timestamp(System.currentTimeMillis()),
                        getNextMothDate(), null, null
                ));
            }
        });
    }

    @Override
    public List<SubscribeModelResponse> getAll() {
        return subscribeDao.findAll().stream().map(this::map).collect(Collectors.toList());
    }

    @Override
    public List<SubscribeModelResponse> getByStatus(SubscribeStatus status){
        return subscribeDao.getByStatus(status).stream().map(this::map).collect(Collectors.toList());
    }


    @Override
    public SubscribeModelResponse getActiveSubscribeByUserId(Long id) {
        return subscribeDao.getActiveSubscribeByUserId(id).map(this::map).orElse(null);
    }

    @Override
    public SubscribeModelResponse getWaitingSubscribeByUserId(Long id) {
        return subscribeDao.getWaitingSubscribeByUserId(id).map(this::map).orElse(null);
    }

    public SubscribeModelResponse map(Subscribe subscribe) {
        return new SubscribeModelResponse(
                subscribe.getSubscribe_id(),
                subscribe.getUser().getId(),
                subscribe.getStatus().getTitle(),
                subscribe.getDeposit(),
                subscribe.getPrice(),
                subscribe.getCdt(),
                subscribe.getEdt(),
                subscribe.getMdt(),
                subscribe.getRdt()
        );
    }

    private Timestamp getNextMothDate(){
        return Timestamp.valueOf(LocalDateTime.now().plusMonths(1));
    }
}
