package kg.kelso.kelsobackend.service.subscribe;

import kg.kelso.kelsobackend.enums.SubscribeStatus;
import kg.kelso.kelsobackend.model.subscription.SubscribeModelRequest;
import kg.kelso.kelsobackend.model.subscription.SubscribeModelResponse;

import java.util.List;

public interface SubscribeService {

    void saveSubscribe(SubscribeModelRequest model);

    List<SubscribeModelResponse> getAll();

    List<SubscribeModelResponse> getByStatus(SubscribeStatus status);


    SubscribeModelResponse getActiveSubscribeByUserId(Long id);

    SubscribeModelResponse getWaitingSubscribeByUserId(Long id);

}
