package it.sara.demo.service.user.impl;

import it.sara.demo.dto.UserDTO;
import it.sara.demo.exception.GenericException;
import it.sara.demo.service.database.UserRepository;
import it.sara.demo.service.database.model.User;
import it.sara.demo.service.user.UserService;
import it.sara.demo.service.user.criteria.CriteriaAddUser;
import it.sara.demo.service.user.criteria.CriteriaGetUsers;
import it.sara.demo.service.user.result.AddUserResult;
import it.sara.demo.service.user.result.GetUsersResult;
import it.sara.demo.service.util.MapperUtil;
import it.sara.demo.service.util.StringUtil;
import it.sara.demo.service.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public AddUserResult addUser(CriteriaAddUser criteria) throws GenericException {
        User user = new User();
        user.setFirstName(criteria.getFirstName());
        user.setLastName(criteria.getLastName());
        user.setEmail(criteria.getEmail());
        user.setPhoneNumber(criteria.getPhoneNumber());

        validateUser(user);

        return AddUserResult.builder()
                .guid(userRepository.save(user))
                .build();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.getAll().stream().map(
                user -> (MapperUtil.map(UserDTO.class, user))).toList();
    }

    @Override
    public UserDTO getUser(String id) throws GenericException {
        UserDTO user = MapperUtil.map(UserDTO.class, userRepository.getByGuid(id));

        if (Objects.isNull(user)) {
            throw new GenericException(404, "User not found");
        }
        return user;
    }

    @Override
    public GetUsersResult getUsers(CriteriaGetUsers criteriaGetUsers) {
        return userRepository.getPaginated(criteriaGetUsers);
    }

    private void validateUser(User user) throws  GenericException {
        if (StringUtil.isNullOrEmpty(user.getFirstName())) {
            throw new GenericException(400, "First name is required");
        }
        if (StringUtil.isNullOrEmpty(user.getLastName())) {
            throw new GenericException(400, "Last name is required");
        }
        if (StringUtil.isNullOrEmpty(user.getEmail())) {
            throw new GenericException(400, "Email is required");
        }
        if (StringUtil.isNullOrEmpty(user.getPhoneNumber())) {
            throw new GenericException(400, "Phone is required");
        }
        if (!ValidationUtil.isValidEmail(user.getEmail())) {
            throw new GenericException(500, "Invalid email address");
        }
        if (!ValidationUtil.isValidItalianPhone(user.getPhoneNumber())) {
            throw new GenericException(500, "Invalid Italian phone number");
        }
    }
}
