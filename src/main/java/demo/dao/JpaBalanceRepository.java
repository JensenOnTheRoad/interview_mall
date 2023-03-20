package demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaBalanceRepository
    extends JpaRepository<BalanceDO, Long>, JpaSpecificationExecutor<BalanceDO> {}
