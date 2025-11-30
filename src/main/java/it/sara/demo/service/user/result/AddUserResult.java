package it.sara.demo.service.user.result;

import it.sara.demo.service.result.GenericResult;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddUserResult extends GenericResult {
    private String guid;
}
