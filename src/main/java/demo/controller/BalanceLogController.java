package demo.controller;

import demo.model.req.BalanceLogGetPageReq;
import demo.model.res.BalanceLogGetPageRes;
import demo.model.res.PageResult;
import demo.service.IBalanceLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jensen_deng
 */
@RestController
@RequestMapping("/balances/logs")
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class BalanceLogController {

  private final IBalanceLogService service;

  /*
   实际环境下，用户的id不应在前端传递过来，应从已登陆账户的上下文对象中获取
  */

  // region 查看余额动态纪录

  @GetMapping("/{userId}")
  public PageResult<BalanceLogGetPageRes> getBalanceLogs(BalanceLogGetPageReq req) {
    return req.getAll(service);
  }
  // endregion
}
