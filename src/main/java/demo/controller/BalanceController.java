package demo.controller;

import demo.dao.BalanceDO;
import demo.model.req.BalanceConsumeReq;
import demo.model.req.BalanceRefundReq;
import demo.model.res.BalanceGetPageRes;
import demo.service.IBalanceService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jensen_deng
 */
@RestController
@RequestMapping("/balances")
@Transactional(rollbackFor = Exception.class)
public class BalanceController {

  private final IBalanceService balanceService;
  private final ApplicationEventPublisher eventPublisher;

  public BalanceController(
      IBalanceService balanceService, ApplicationEventPublisher eventPublisher) {
    this.balanceService = balanceService;
    this.eventPublisher = eventPublisher;
  }

  /*
   实际环境下，用户的id不应在前端传递过来，应从已登陆账户的上下文对象中获取
  */

  // region 查询余额

  @GetMapping("/{userId}")
  public BalanceGetPageRes getBalance(@PathVariable Long userId) {
    return balanceService
        .getOne(BalanceDO.builder().userId(userId).build())
        .map(BalanceGetPageRes::from)
        .orElse(null);
  }
  // endregion

  // region 消费

  @PostMapping("/consumptions")
  public void consume(@RequestBody @Validated BalanceConsumeReq req) {
    req.consume(balanceService, eventPublisher);
  }
  // endregion

  // region 退款

  @PostMapping("/refunds")
  public void refund(@RequestBody @Validated BalanceRefundReq req) {
    req.refund(balanceService, eventPublisher);
  }
  // endregion

}
