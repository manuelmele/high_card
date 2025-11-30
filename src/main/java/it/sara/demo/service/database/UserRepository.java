package it.sara.demo.service.database;

import it.sara.demo.dto.UserDTO;
import it.sara.demo.exception.GenericException;
import it.sara.demo.service.database.model.User;
import it.sara.demo.service.user.criteria.CriteriaGetUsers;
import it.sara.demo.service.user.result.GetUsersResult;
import it.sara.demo.service.util.MapperUtil;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRepository {

    public String save(User user) throws GenericException {
        try {
            String randomID = java.util.UUID.randomUUID().toString();
            user.setGuid(randomID);
            FakeDatabase.TABLE_USER.add(user);
            return randomID;
        } catch (Exception e) {
            throw new GenericException(500, "Error saving user");
        }
    }

    public User getByGuid(String guid) {
        return FakeDatabase.TABLE_USER.stream().filter(
                u -> u.getGuid().equals(guid)).findFirst().orElse(null);
    }

    public List<User> getAll() {
        return FakeDatabase.TABLE_USER;
    }

    /**
     * Retrieves a paginated and optionally filtered list of users based on the specified criteria.
     *
     * @param criteria the filtering and pagination criteria, including search query, pagination offsets,
     *                 and sorting options
     * @return a {@code GetUsersResult} containing the paginated list of users, the total user count,
     *         and the provided pagination details
     */
    public GetUsersResult getPaginated(CriteriaGetUsers criteria) {
        List<User> allUsers = FakeDatabase.TABLE_USER;

        String query = criteria.getQuery();
        CriteriaGetUsers.OrderType orderType = criteria.getOrder();
        int limit = criteria.getLimit();
        int offset = criteria.getOffset();

        // Filter by query (case-insensitive)
        List<User> filtered = allUsers.stream()
                .filter(u ->
                        u.getFirstName().toLowerCase().contains(query.toLowerCase()) ||
                        u.getLastName().toLowerCase().contains(query.toLowerCase()) ||
                        u.getEmail().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());

        // Sort based on orderType
        Comparator<User> comparator = switch (orderType) {
            case BY_FIRSTNAME -> Comparator.comparing(User::getFirstName, String.CASE_INSENSITIVE_ORDER);
            case BY_FIRSTNAME_DESC -> Comparator.comparing(User::getFirstName, String.CASE_INSENSITIVE_ORDER).reversed();
            case BY_LASTNAME -> Comparator.comparing(User::getLastName, String.CASE_INSENSITIVE_ORDER);
            case BY_LASTNAME_DESC -> Comparator.comparing(User::getLastName, String.CASE_INSENSITIVE_ORDER).reversed();
        };
        filtered.sort(comparator);

        // Pagination
        int total = filtered.size();
        List<User> pagedUsers = filtered.subList(Math.min(offset, total), Math.min(offset + limit, total));

        return GetUsersResult.builder()
                .users(pagedUsers.stream().map(user -> (MapperUtil.map(UserDTO.class, user))).toList())
                .total(total)
                .offset(offset)
                .limit(limit)
                .build();
    }
}
