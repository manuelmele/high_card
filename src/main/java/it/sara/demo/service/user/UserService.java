package it.sara.demo.service.user;

import it.sara.demo.dto.UserDTO;
import it.sara.demo.exception.GenericException;
import it.sara.demo.service.user.criteria.CriteriaAddUser;
import it.sara.demo.service.user.criteria.CriteriaGetUsers;
import it.sara.demo.service.user.result.AddUserResult;
import it.sara.demo.service.user.result.GetUsersResult;

import java.util.List;

public interface UserService {

    /**
     * Adds a new user based on the provided criteria.
     *
     * @param addUserRequest the criteria containing the user details such as first name, last name, email, and phone number
     * @return the result of the add operation, including the unique identifier (GUID) of the newly added user
     * @throws GenericException if an error occurs during the process, such as validation issues or system errors
     */
    AddUserResult addUser(CriteriaAddUser addUserRequest) throws GenericException;

    /**
     * Retrieves a list of users based on the specified search criteria.
     *
     * @param criteriaGetUsers the criteria defining the parameters for fetching users,
     *                         including query string, offset, limit, and order type
     * @return a result object containing the list of users, total count, offset, and limit
     * @throws GenericException if an error occurs during the retrieval process
     */
    GetUsersResult getUsers(CriteriaGetUsers criteriaGetUsers) throws GenericException;

    /**
     * Retrieves a list of all existing users.
     *
     * @return a list of UserDTO objects representing all users
     * @throws GenericException if an error occurs during the retrieval process
     */
    List<UserDTO> getAllUsers() throws GenericException;

    /**
     * Retrieves a user based on the given unique identifier.
     *
     * @param id the unique identifier of the user to be retrieved
     * @return a UserDTO object containing the details of the corresponding user
     * @throws GenericException if an error occurs during the retrieval process,
     *                          such as when the user is not found or a system error occurs
     */
    UserDTO getUser(String id) throws GenericException;
}
