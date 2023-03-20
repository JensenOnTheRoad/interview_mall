package demo.service;

import demo.dao.BalanceDO;
import demo.dao.JpaBalanceRepository;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * @author jensen_deng
 */
@Service
public class BalanceServiceImpl implements IBalanceService {

  private final JpaBalanceRepository repository;

  public BalanceServiceImpl(JpaBalanceRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<BalanceDO> getOne(BalanceDO balance) {
    Example<BalanceDO> example = Example.of(balance);
    return repository.findOne(example);
  }

  @Override
  public void save(BalanceDO balance) {
    repository.save(balance);
  }
}
