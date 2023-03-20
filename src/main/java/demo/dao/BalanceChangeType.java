package demo.dao;
/**
 * @author jensen_deng
 */
public enum BalanceChangeType {
  CONSUMED("consumed", "消费"),
  REFUNDED("refunded", "退款");

  private final String value;
  private final String desc;

  BalanceChangeType(String value, String desc) {
    this.value = value;
    this.desc = desc;
  }
}
