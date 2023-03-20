package demo.service;

import demo.dao.BalanceLogDO;
import demo.dao.JpaBalanceLogRepository;
import demo.model.req.PageVO;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author jensen_deng
 */
@Service
@RequiredArgsConstructor
public class BalanceLogServiceImpl implements IBalanceLogService {

  private final JpaBalanceLogRepository repository;

  public Page<BalanceLogDO> findAll(PageVO pageVO) {
    PageRequest pageable = PageRequest.of(pageVO.getPage(), pageVO.getSize());
    return repository.findAll(pageable);
  }

  @Override
  public Optional<BalanceLogDO> findOne(BalanceLogDO balanceLogDO) {
    Example<BalanceLogDO> of = Example.of(balanceLogDO);
    return repository.findOne(of);
  }

  @Override
  public void save(BalanceLogDO balanceLogDO) {
    repository.save(balanceLogDO);
  }
}
