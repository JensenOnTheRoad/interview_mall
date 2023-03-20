package demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaBalanceLogRepository
    extends JpaRepository<BalanceLogDO, Long>, JpaSpecificationExecutor<BalanceLogDO> {}
