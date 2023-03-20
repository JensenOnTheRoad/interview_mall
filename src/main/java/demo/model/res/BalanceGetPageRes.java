package demo.model.res;

import demo.Desc;
import demo.dao.BalanceDO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jensen_deng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BalanceGetPageRes {

  @Desc("id")
  private Long id;

  @Desc("用户id")
  private Long userId;

  @Desc("余额")
  private BigDecimal balance;

  private Long createdBy;

  private Long updatedBy;

  private LocalDateTime createdTime;

  private LocalDateTime updatedTime;

  public static BalanceGetPageRes from(BalanceDO obj) {
    return BalanceGetPageRes.builder()
        .id(obj.getId())
        .userId(obj.getUserId())
        .balance(obj.getBalance())
        .createdBy(obj.getCreatedBy())
        .updatedBy(obj.getUpdatedBy())
        .createdTime(obj.getCreatedTime())
        .updatedTime(obj.getUpdatedTime())
        .build();
  }
}
