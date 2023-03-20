package demo.dao;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Entity
@Table(name = "balance_log")
@DynamicUpdate
@DynamicInsert

// 逻辑删除
@SQLDelete(sql = "update balance_log set deleted = true where id = ?")
@Where(clause = "deleted = false")

// lombok
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class BalanceLogDO extends BaseDO {

  @Comment("用户id")
  private Long userId;

  @Enumerated(value = EnumType.STRING)
  @Comment("变更类型")
  private BalanceChangeType type;

  @Comment("变化余额")
  private BigDecimal balanceChange;

  @Comment("变化前余额")
  private BigDecimal balanceBefore;

  @Comment("变化前余额")
  private BigDecimal balanceAfter;
}
