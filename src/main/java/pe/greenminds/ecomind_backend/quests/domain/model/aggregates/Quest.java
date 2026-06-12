package pe.greenminds.ecomind_backend.quests.domain.model.aggregates;
import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.quests.domain.model.events.QuestCreatedEvent;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Reward;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Category;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestType;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.util.Objects;

public class Quest extends AbstractDomainAggregateRoot<Quest>{

    @Getter
    @Setter
    private Long id;

    @Getter
    private Reward reward;
    private Long minigameId;
    private String title;
    private String description;
    private Category category;
    private QuestType type;
    private Integer age;
    private Integer time;
    private String image;
    private String theme;


    public Quest(Long id, Long minigame_id, String title, Category category,
                 String description, QuestType type, Integer age,Reward reward, Integer time, String image, String theme) {
        this.id = id;
        this.minigameId = minigame_id;
        this.title = Objects.requireNonNull(title, "title must not be null");
        this.description = Objects.requireNonNull(description, "description must not be null");
        this.category = Objects.requireNonNull(category, "category must not be null");
        this.type = Objects.requireNonNull(type, "type must not be null");
        this.reward = Objects.requireNonNull(reward, "reward must not be null");
        this.time = time;
        this.image = image;
        this.age = age;
        this.theme = theme;
    }

    public Quest(Long minigame_id, String title, Category category,
                 String description, QuestType type, Integer age,Reward reward, Integer time, String image,  String theme) {

        this(null, minigame_id, title, category, description, type, age, reward, time, image, theme);
    }

    public Quest(Long minigame_id, String title, Category category,
                 String description, QuestType type, Integer age, Integer gems, Integer ecopoints, Integer time, String image, String theme) {

        this(null, minigame_id, title, category, description, type, age, new Reward(gems, ecopoints), time, image, theme);
    }

    public Integer getGems() {return reward.gems();}
    public Integer getEcopoints() {return reward.ecopoints();}

    public Integer getTime() {return time;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public Category getCategory() {return category;}
    public QuestType getType() {return type;}
    public Integer getAge() {return age;}
    public Long getMinigameId() {return minigameId;}
    public String getImage() {return image;}
    public String getTheme() {return theme;}

    public Reward getRewardValue() {
        return reward;
    }

    public void onCreated() {
        registerDomainEvent(QuestCreatedEvent.from(this));
    }
}
