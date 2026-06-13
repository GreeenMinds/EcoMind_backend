package pe.greenminds.ecomind_backend.quests.domain.model.aggregates;

import jakarta.persistence.*;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Category;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestType;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Reward;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Theme;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.LocalDate;

@Entity
@Table(name = "quests")
public class Quest extends AuditableAbstractAggregateRoot<Quest> {

    @Column(name = "minigame_id")
    private Long minigameId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private QuestType type;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "gems", column = @Column(name = "gem_reward")),
            @AttributeOverride(name = "ecopoints", column = @Column(name = "points_reward"))
    })
    private Reward reward;

    private Integer age;

    private Integer time;

    @Column(name = "image_url", length = 255)
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Theme theme;

    @Column(name = "assigned_date")
    private LocalDate assignedDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    protected Quest() {}

    public Quest(Long minigameId, String title, Category category,
                 String description, QuestType type, Integer age,
                 Reward reward, Integer time, String image,
                 Theme theme, LocalDate assignedDate, LocalDate expirationDate) {
        this.minigameId = minigameId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.type = type;
        this.age = age;
        this.reward = reward;
        this.time = time;
        this.image = image;
        this.theme = theme;
        this.assignedDate = assignedDate;
        this.expirationDate = expirationDate;
    }

    public Quest(String title, Category category, String description,
                 QuestType type, Integer age, Reward reward,
                 Integer time, String image, Theme theme,
                 LocalDate assignedDate, LocalDate expirationDate) {
        this(null, title, category, description, type, age, reward, time, image, theme, assignedDate, expirationDate);
    }

    public Long getMinigameId() { return minigameId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Category getCategory() { return category; }
    public QuestType getType() { return type; }
    public Integer getAge() { return age; }
    public Integer getTime() { return time; }
    public String getImage() { return image; }
    public Theme getTheme() { return theme; }
    public LocalDate getAssignedDate() { return assignedDate; }
    public LocalDate getExpirationDate() { return expirationDate; }
    public Integer getGems() { return reward != null ? reward.gems() : 0; }
    public Integer getEcopoints() { return reward != null ? reward.ecopoints() : 0; }
    public Reward getReward() { return reward; }

    public void updateInformation(String title, String description, Category category,
                                  QuestType type, Integer age, Reward reward,
                                  Integer time, String image, Theme theme,
                                  LocalDate assignedDate, LocalDate expirationDate) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.type = type;
        this.age = age;
        this.reward = reward;
        this.time = time;
        this.image = image;
        this.theme = theme;
        this.assignedDate = assignedDate;
        this.expirationDate = expirationDate;
    }
}
