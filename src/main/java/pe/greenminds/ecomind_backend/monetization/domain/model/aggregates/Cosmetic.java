package pe.greenminds.ecomind_backend.monetization.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.monetization.domain.model.events.CosmeticCreatedEvent;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.CosmeticType;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.util.Objects;

public class Cosmetic extends AbstractDomainAggregateRoot<Cosmetic> {

    @Getter
    @Setter
    private Long id;

    private String name;
    private String description;
    private Integer price;
    private CosmeticType type;
    private String imageUrl;

    public Cosmetic(Long id, String name, String description, Integer price, CosmeticType type, String imageUrl) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.description = description;
        this.price = Objects.requireNonNull(price, "price must not be null");
        this.type = Objects.requireNonNull(type, "type must not be null");
        this.imageUrl = imageUrl;
    }

    public Cosmetic(String name, String description, Integer price, CosmeticType type, String imageUrl) {
        this(null, name, description, price, type, imageUrl);
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public Integer getPrice() { return price; }
    public CosmeticType getType() { return type; }
    public String getImageUrl() { return imageUrl; }

    public void onCreated() {
        registerDomainEvent(CosmeticCreatedEvent.from(this));
    }
}
