package demo.listener;

import demo.dao.BalanceChangeType;
import demo.dao.BalanceDO;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Getter
@Slf4j
public class BalanceLogEvent extends ApplicationEvent {
  private final BalanceDO balanceDO;

  private final BigDecimal oldBalance;
  private final BigDecimal amount;
  private final BalanceChangeType type;

  public BalanceLogEvent(
      Object source,
      BalanceDO balanceDO,
      BigDecimal oldBalance,
      BigDecimal amount,
      BalanceChangeType type) {
    super(source);
    this.balanceDO = balanceDO;
    this.oldBalance = oldBalance;
    this.amount = amount;
    this.type = type;
  }
}
