package kg.kelso.kelsobackend.enums;

public enum CoverType {
    PAPERBACK("Мягкий переплет"),
    HARDCOVER("Твердый переплет");

    private final String title;

    CoverType(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }
}
