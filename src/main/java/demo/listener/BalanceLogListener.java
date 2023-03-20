package demo.listener;

import demo.dao.BalanceChangeType;
import demo.dao.BalanceLogDO;
import demo.service.IBalanceLogService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class BalanceLogListener implements ApplicationListener<BalanceLogEvent> {

  private final IBalanceLogService balanceLogService;

  @Override
  public void onApplicationEvent(BalanceLogEvent event) {
    Long userId = event.getBalanceDO().getUserId();
    BigDecimal oldBalance = event.getOldBalance();
    BigDecimal newBalance = event.getBalanceDO().getBalance();
    BigDecimal amount = event.getAmount();

    BalanceLogDO balanceLogDO = null;
    if (BalanceChangeType.CONSUMED.equals(event.getType())) {
      balanceLogDO =
          BalanceLogDO.builder()
              .userId(userId)
              .balanceBefore(oldBalance)
              .balanceAfter(newBalance)
              .balanceChange(amount)
              .type(BalanceChangeType.CONSUMED)
              .build();
      log.info(
          "user-{} 's newBalance reduced {},from {} to {}.",
          userId,
          amount,
          oldBalance,
          newBalance);

    } else if (BalanceChangeType.REFUNDED.equals(event.getType())) {
      balanceLogDO =
          BalanceLogDO.builder()
              .userId(userId)
              .balanceBefore(oldBalance)
              .balanceAfter(newBalance)
              .balanceChange(amount)
              .type(BalanceChangeType.REFUNDED)
              .build();
      log.info(
          "user-{} 's newBalance increased {}, from {} to {}.",
          userId,
          amount,
          oldBalance,
          newBalance);
    }

    assert balanceLogDO != null;
    balanceLogService.save(balanceLogDO);
  }
}
