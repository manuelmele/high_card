package it.sara.demo.web.assembler;

import it.sara.demo.service.user.criteria.CriteriaGetUsers;
import it.sara.demo.web.user.request.GetUsersRequest;
import org.springframework.stereotype.Component;

@Component
public class GetUsersAssembler {

    public CriteriaGetUsers toCriteria(GetUsersRequest getUsersRequest) {
        CriteriaGetUsers returnValue = new CriteriaGetUsers();
        returnValue.setOffset(getUsersRequest.getOffset());
        returnValue.setLimit(getUsersRequest.getLimit());
        returnValue.setQuery(getUsersRequest.getQuery());
        returnValue.setOrder(getUsersRequest.getOrder());
        return returnValue;
    }
}
