package kg.kelso.kelsobackend.service.subscribe;

import kg.kelso.kelsobackend.enums.SubscribeStatus;
import kg.kelso.kelsobackend.model.message.MessageResponse;
import kg.kelso.kelsobackend.model.subscription.SubscribeDetailsModel;
import kg.kelso.kelsobackend.model.subscription.SubscribeModelRequest;
import kg.kelso.kelsobackend.model.subscription.SubscribeModelResponse;
import kg.kelso.kelsobackend.util.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SubscribeService {

    void saveSubscribe(SubscribeModelRequest model);

    List<SubscribeModelResponse> getHistoryByUserId(Long userId);

    List<SubscribeModelResponse> getAll();

    List<SubscribeModelResponse> getByStatus(String status);

    String updateStatus(String status, Long userId) throws NotFoundException;

    SubscribeModelResponse getActiveSubscribeByUserId(Long id);

    ResponseEntity<MessageResponse> saveBooking(List<SubscribeDetailsModel> models, Long subscriber_id);

}
