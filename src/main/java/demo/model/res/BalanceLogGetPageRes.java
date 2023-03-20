package demo.model.res;

import demo.Desc;
import demo.dao.BalanceChangeType;
import demo.dao.BalanceLogDO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

/**
 * @author jensen_deng
 */
@Data
@Builder
public class BalanceLogGetPageRes {

  @Desc("id")
  private Long id;

  @Desc("用户id")
  private Long userId;

  @Enumerated(value = EnumType.STRING)
  @Desc("变更类型")
  private BalanceChangeType type;

  @Desc("变化前余额")
  private BigDecimal balanceBefore;

  @Desc("变化前余额")
  private BigDecimal balanceAfter;

  private Long createdBy;

  private Long updatedBy;

  private LocalDateTime createdTime;

  private LocalDateTime updatedTime;

  public static BalanceLogGetPageRes from(BalanceLogDO obj) {
    return BalanceLogGetPageRes.builder()
        .id(obj.getId())
        .userId(obj.getUserId())
        .type(obj.getType())
        .balanceBefore(obj.getBalanceBefore())
        .balanceAfter(obj.getBalanceAfter())
        .createdBy(obj.getCreatedBy())
        .updatedBy(obj.getUpdatedBy())
        .createdTime(obj.getCreatedTime())
        .updatedTime(obj.getUpdatedTime())
        .build();
  }
}
