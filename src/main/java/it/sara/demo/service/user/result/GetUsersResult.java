package it.sara.demo.service.user.result;

import it.sara.demo.dto.UserDTO;
import it.sara.demo.service.result.GenericPagedResult;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class GetUsersResult extends GenericPagedResult {
    private List<UserDTO> users;
    private Integer total;
    private Integer offset;
    private Integer limit;
}
