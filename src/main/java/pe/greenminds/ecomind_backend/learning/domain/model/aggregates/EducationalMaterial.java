package pe.greenminds.ecomind_backend.learning.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.learning.domain.model.events.EducationalMaterialCreatedEvent;
import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialCategory;
import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialType;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.util.Objects;

public class EducationalMaterial extends AbstractDomainAggregateRoot<EducationalMaterial> {

    @Getter
    @Setter
    private Long id;

    private String title;
    private String description;
    private String content;
    private MaterialType materialType;
    private MaterialCategory category;
    private String imageUrl;
    private Integer durationMinutes;

    public EducationalMaterial(
            Long id,
            String title,
            String description,
            String content,
            MaterialType materialType,
            MaterialCategory category,
            String imageUrl,
            Integer durationMinutes
    ) {
        this.id = id;
        this.title = Objects.requireNonNull(title, "title must not be null");
        this.description = description;
        this.content = Objects.requireNonNull(content, "content must not be null");
        this.materialType = Objects.requireNonNull(materialType, "materialType must not be null");
        this.category = Objects.requireNonNull(category, "category must not be null");
        this.imageUrl = imageUrl;
        this.durationMinutes = durationMinutes;
    }

    public EducationalMaterial(
            String title,
            String description,
            String content,
            MaterialType materialType,
            MaterialCategory category,
            String imageUrl,
            Integer durationMinutes
    ) {
        this(null, title, description, content, materialType, category, imageUrl, durationMinutes);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public MaterialCategory getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void onCreated() {
        registerDomainEvent(EducationalMaterialCreatedEvent.from(this));
    }
}
