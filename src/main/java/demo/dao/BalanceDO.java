package demo.dao;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * @author jensen_deng
 */
@Table(name = "balance")
@Entity
@DynamicUpdate
@DynamicInsert
@SuperBuilder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "update balance set deleted = true where id = ?")
@Where(clause = "deleted = false")
@FieldNameConstants
public class BalanceDO extends BaseDO {

  @Comment("用户id")
  private Long userId;

  @Comment("余额")
  @Column(nullable = false, columnDefinition = "decimal(19,2) default '0.00'")
  private BigDecimal balance;
}
