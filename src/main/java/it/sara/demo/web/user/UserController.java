package it.sara.demo.web.user;

import it.sara.demo.dto.UserDTO;
import it.sara.demo.exception.GenericException;
import it.sara.demo.service.user.UserService;
import it.sara.demo.service.user.criteria.CriteriaAddUser;
import it.sara.demo.service.user.criteria.CriteriaGetUsers;
import it.sara.demo.service.user.result.GetUsersResult;
import it.sara.demo.web.assembler.AddUserAssembler;
import it.sara.demo.web.assembler.GetUsersAssembler;
import it.sara.demo.web.response.GenericResponse;
import it.sara.demo.web.user.request.AddUserRequest;
import it.sara.demo.web.user.request.GetUsersRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddUserAssembler addUserAssembler;

    @Autowired
    private GetUsersAssembler getUsersAssembler;

    @RequestMapping(value = {"/v1/insert-user"}, method = RequestMethod.PUT)
    public ResponseEntity<GenericResponse> addUser(
            @Valid @RequestBody AddUserRequest request) throws GenericException {
        CriteriaAddUser criteria = addUserAssembler.toCriteria(request);
        String userId = userService.addUser(criteria).getGuid();
        return ResponseEntity.ok(GenericResponse.success("User added with id : " + userId));
    }

    @RequestMapping(value = {"/v1/search-users"}, method = RequestMethod.POST)
    public GetUsersResult getUsers(@Valid @RequestBody GetUsersRequest request) throws GenericException {
        CriteriaGetUsers criteria = getUsersAssembler.toCriteria(request);
        return userService.getUsers(criteria);
    }

    @RequestMapping(value = {"/v1/get-users"}, method = RequestMethod.GET)
    public List<UserDTO> getAllUsers() throws GenericException {
        return userService.getAllUsers();
    }

    @RequestMapping(value = {"/v1/get-user/"}, method = RequestMethod.GET)
    public UserDTO getUser(@RequestParam String id) throws GenericException {
        return userService.getUser(id);
    }

    // -------------------- SECURE ENDPOINT (ONLY ADMIN ROLE USERS)--------------------
    @RequestMapping(value = {"/v2/insert-user"}, method = RequestMethod.PUT)
    public ResponseEntity<GenericResponse> addUserSafe(
            @Valid @RequestBody AddUserRequest request) throws GenericException {
        CriteriaAddUser criteria = addUserAssembler.toCriteria(request);
        String userId = userService.addUser(criteria).getGuid();
        return ResponseEntity.ok(GenericResponse.success("User safely added with id : " + userId));
    }
}
