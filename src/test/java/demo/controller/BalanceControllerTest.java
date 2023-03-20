package demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.reflect.TypeToken;
import demo.SpringBootBaseTest;
import demo.dao.BalanceDO;
import demo.dao.BalanceLogDO;
import demo.model.req.BalanceConsumeReq;
import demo.model.req.BalanceRefundReq;
import demo.model.res.BalanceGetPageRes;
import demo.service.IBalanceLogService;
import demo.service.IBalanceService;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

/**
 * @author jensen_deng
 */
class BalanceControllerTest extends SpringBootBaseTest {

  @Autowired IBalanceService balanceService;
  @Autowired IBalanceLogService balanceLogService;

  BalanceDO preData;

  @BeforeEach
  void setUp() {
    preData = BalanceDO.builder().balance(new BigDecimal("200.00")).userId(2L).build();
    balanceService.save(preData);
  }

  @Test
  @DisplayName("查询余额")
  void test() throws Exception {
    MvcResult mvcResult =
        getMockMvc().perform(get("/balances/{userId}", 2L)).andExpect(status().isOk()).andReturn();
    String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    Type type = TypeToken.getParameterized(BalanceGetPageRes.class).getType();
    BalanceGetPageRes balanceGetPageRes = getGson().fromJson(contentAsString, type);
    Assertions.assertEquals(new BigDecimal("200.00"), balanceGetPageRes.getBalance());
  }

  @Test
  @DisplayName("消费")
  void test_consume() throws Exception {
    long userId = 2L;

    BalanceConsumeReq req =
        BalanceConsumeReq.builder().userId(userId).amount(new BigDecimal("10")).build();

    getMockMvc()
        .perform(
            post("/balances/consumptions", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getGson().toJson(req)))
        .andExpect(status().isOk());

    BigDecimal balance =
        balanceService
            .getOne(BalanceDO.builder().userId(userId).build())
            .map(BalanceDO::getBalance)
            .orElse(null);

    BigDecimal balanceAfter = new BigDecimal("190.00");
    Assertions.assertEquals(balanceAfter, balance);

    BalanceLogDO result =
        balanceLogService.findOne(BalanceLogDO.builder().userId(userId).build()).orElse(null);
    Assertions.assertEquals(result.getUserId(), userId);
    Assertions.assertEquals(result.getBalanceBefore(), new BigDecimal("200.00"));
    Assertions.assertEquals(result.getBalanceAfter(), balanceAfter);
    Assertions.assertEquals(result.getBalanceChange(), req.getAmount());
  }

  @Test
  @DisplayName("退款")
  void test_refund() throws Exception {
    long userId = 2L;

    BalanceRefundReq req =
        BalanceRefundReq.builder().userId(userId).amount(new BigDecimal("10")).build();

    getMockMvc()
        .perform(
            post("/balances/refunds", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getGson().toJson(req)))
        .andExpect(status().isOk());

    BigDecimal balance =
        balanceService
            .getOne(BalanceDO.builder().userId(userId).build())
            .map(BalanceDO::getBalance)
            .orElse(null);

    BigDecimal balanceAfter = new BigDecimal("210.00");
    Assertions.assertEquals(balanceAfter, balance);

    BalanceLogDO result =
        balanceLogService.findOne(BalanceLogDO.builder().userId(userId).build()).orElse(null);
    Assertions.assertEquals(result.getUserId(), userId);
    Assertions.assertEquals(result.getBalanceBefore(), new BigDecimal("200.00"));
    Assertions.assertEquals(result.getBalanceAfter(), balanceAfter);
    Assertions.assertEquals(result.getBalanceChange(), req.getAmount());
  }
}
