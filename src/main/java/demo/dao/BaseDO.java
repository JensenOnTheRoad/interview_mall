package demo.dao;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@FieldNameConstants
@MappedSuperclass
@Generated
public abstract class BaseDO {

  @javax.persistence.Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "deleted")
  private Boolean deleted;

  @Column(nullable = false, updatable = false)
  private Long createdBy;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdTime;

  private Long updatedBy;
  private LocalDateTime updatedTime;

  @PrePersist
  public void prePersist() {
    if (createdTime == null) {
      createdTime = LocalDateTime.now();
    }
    if (deleted == null) {
      deleted = false;
    }
    if (null == createdBy) {
      createdBy = 1L;
    }
  }

  @PreUpdate
  public void preUpdate() {
    updatedTime = LocalDateTime.now();
    updatedBy = 1L;
    if (deleted == null) {
      deleted = false;
    }
  }
}
