package pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;

@Entity
@Table(name = "favorite_materials")
public class FavoriteMaterialEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "material_id", nullable = false)
    private Long materialId;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getMaterialId() { return materialId; }
    public void setMaterialId(Long materialId) { this.materialId = materialId; }
}
