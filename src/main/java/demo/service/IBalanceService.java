package demo.service;

import demo.dao.BalanceDO;
import java.util.Optional;

/**
 * @author jensen_deng
 */
public interface IBalanceService {

  Optional<BalanceDO> getOne(BalanceDO user);
void save(BalanceDO balance);}
