package pe.greenminds.ecomind_backend.quests.infrastructure.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;

import java.util.Map;

@Entity
@Table(name = "minigames")
public class MinigamePersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "url", nullable = false)
    private String url;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "completion_rules", columnDefinition = "jsonb", nullable = false)
    private Map<String, Object> completionRules;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public Map<String, Object> getCompletionRules() { return completionRules; }
    public void setCompletionRules(Map<String, Object> completionRules) {
        this.completionRules = completionRules;
    }
}
