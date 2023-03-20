package demo.model.req;

import demo.dao.BalanceLogDO;
import demo.model.res.BalanceLogGetPageRes;
import demo.model.res.PageResult;
import demo.service.IBalanceLogService;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

/**
 * @author jensen_deng
 */
@Data
@Builder
@Slf4j
public class BalanceLogGetPageReq {

  @NotNull(message = "user id should not be null!")
  private Long userId;

  private Integer page;
  private Integer size;

  public PageResult<BalanceLogGetPageRes> getAll(IBalanceLogService service) {
    Page<BalanceLogDO> pages = service.findAll(PageVO.builder().page(page).size(size).build());
    List<BalanceLogGetPageRes> list =
        pages.getContent().stream().map(BalanceLogGetPageRes::from).toList();

    return PageResult.of(page, pages.getSize(), pages.getTotalElements(), list);
  }
}
