package demo.model.req;

import demo.Desc;
import demo.dao.BalanceChangeType;
import demo.dao.BalanceDO;
import demo.listener.BalanceLogEvent;
import demo.service.IBalanceService;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @author jensen_deng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class BalanceConsumeReq {

  @Desc("消费金额")
  @DecimalMin("0.00")
  private BigDecimal amount;

  @NotNull(message = "user id should not be null!")
  private Long userId;

  public void consume(IBalanceService service, ApplicationEventPublisher eventPublisher) {
    BalanceDO balance =
        service
            .getOne(BalanceDO.builder().userId(userId).build())
            .orElseThrow(
                () -> {
                  throw new IllegalArgumentException("no exist balance record!");
                });

    // 若使用DDD充血模型 应该封装在Balance类中

    BigDecimal oldBalance = balance.getBalance();
    if (amount.compareTo(oldBalance) > 0) {
      throw new IllegalArgumentException("not sufficient funds!");
    }
    BigDecimal newBalance = oldBalance.subtract(amount);
    balance.setBalance(newBalance);
    service.save(balance);

    eventPublisher.publishEvent(
        new BalanceLogEvent(this, balance, oldBalance, amount, BalanceChangeType.CONSUMED));
  }
}
