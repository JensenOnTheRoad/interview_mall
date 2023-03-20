package demo.service;

import demo.dao.BalanceLogDO;
import demo.model.req.PageVO;
import java.util.Optional;
import org.springframework.data.domain.Page;

/**
 * @author jensen_deng
 */
public interface IBalanceLogService {

  Page<BalanceLogDO> findAll(PageVO pageVO);

  Optional<BalanceLogDO> findOne(BalanceLogDO balanceLogDO);

  void save(BalanceLogDO balanceLogDO);
}
