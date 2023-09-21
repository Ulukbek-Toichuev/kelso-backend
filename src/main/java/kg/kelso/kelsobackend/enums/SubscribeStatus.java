package kg.kelso.kelsobackend.enums;

public enum SubscribeStatus {
    
    ACTIVE("Подписка активна"),
    INACTIVE("Подписка неактивна"),
    EXPIRED("Подписка истекла"),
    WAITING("В процессе обработки");
    
    private final String title;
    
    SubscribeStatus(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return this.title;
    }
}
