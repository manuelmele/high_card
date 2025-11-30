package it.sara.demo.service.database;

import it.sara.demo.service.database.model.User;
import it.sara.demo.service.user.criteria.CriteriaGetUsers;
import it.sara.demo.service.user.result.GetUsersResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    /**
     * Test class to validate the behavior of UserRepository's getPaginated method.
     * The method retrieves a filtered, ordered, and paginated list of users based on given criteria.
     */

    @Test
    void testGetPaginated_NoFilteringNoSorting() {
        // Arrange
        UserRepository userRepository = new UserRepository();
        populateDatabase();

        CriteriaGetUsers criteria = new CriteriaGetUsers();
        criteria.setQuery(""); // No filtering
        criteria.setOrder(CriteriaGetUsers.OrderType.BY_FIRSTNAME);
        criteria.setLimit(10);
        criteria.setOffset(0);

        // Act
        GetUsersResult result = userRepository.getPaginated(criteria);

        // Assert
        assertEquals(3, result.getTotal());
        assertEquals(3, result.getUsers().size());
        assertEquals("Alice", result.getUsers().get(0).getFirstName());
    }

    @Test
    void testGetPaginated_FilteringByFirstName() {
        // Arrange
        UserRepository userRepository = new UserRepository();
        populateDatabase();

        CriteriaGetUsers criteria = new CriteriaGetUsers();
        criteria.setQuery("bob"); // Filter by first name "Bob"
        criteria.setOrder(CriteriaGetUsers.OrderType.BY_FIRSTNAME);
        criteria.setLimit(10);
        criteria.setOffset(0);

        // Act
        GetUsersResult result = userRepository.getPaginated(criteria);

        // Assert
        assertEquals(1, result.getTotal());
        assertEquals(1, result.getUsers().size());
        assertEquals("Bob", result.getUsers().get(0).getFirstName());
    }

    @Test
    void testGetPaginated_SortingByLastNameDescending() {
        // Arrange
        UserRepository userRepository = new UserRepository();
        populateDatabase();

        CriteriaGetUsers criteria = new CriteriaGetUsers();
        criteria.setQuery(""); // No filtering
        criteria.setOrder(CriteriaGetUsers.OrderType.BY_LASTNAME_DESC); // Sort by last name descending
        criteria.setLimit(10);
        criteria.setOffset(0);

        // Act
        GetUsersResult result = userRepository.getPaginated(criteria);

        // Assert
        assertEquals(3, result.getTotal());
        assertEquals(3, result.getUsers().size());
        assertEquals("Charlie", result.getUsers().get(0).getFirstName()); // Last name "Williams"
    }

    @Test
    void testGetPaginated_Pagination() {
        // Arrange
        UserRepository userRepository = new UserRepository();
        populateDatabase();

        CriteriaGetUsers criteria = new CriteriaGetUsers();
        criteria.setQuery(""); // No filtering
        criteria.setOrder(CriteriaGetUsers.OrderType.BY_FIRSTNAME);
        criteria.setLimit(2); // Limit to 2 users per page
        criteria.setOffset(1); // Start from the second user

        // Act
        GetUsersResult result = userRepository.getPaginated(criteria);

        // Assert
        assertEquals(3, result.getTotal()); // Total users in DB
        assertEquals(2, result.getUsers().size()); // Limited to 2 users for this page
        assertEquals("Bob", result.getUsers().get(0).getFirstName()); // Bob should be the first user on this page
    }

    @Test
    void testGetPaginated_EmptyDatabase() {
        // Arrange
        UserRepository userRepository = new UserRepository();
        emptyDatabase();

        CriteriaGetUsers criteria = new CriteriaGetUsers();
        criteria.setQuery("");
        criteria.setOrder(CriteriaGetUsers.OrderType.BY_FIRSTNAME);
        criteria.setLimit(10);
        criteria.setOffset(0);

        // Act
        GetUsersResult result = userRepository.getPaginated(criteria);

        // Assert
        assertEquals(0, result.getTotal());
        assertEquals(0, result.getUsers().size());
    }

    private void emptyDatabase() {
        FakeDatabase.TABLE_USER.clear();
    }

    private void populateDatabase() {
        FakeDatabase.TABLE_USER.clear();

        User user1 = new User();
        user1.setFirstName("Alice");
        user1.setLastName("Smith");
        user1.setEmail("alice@example.com");
        user1.setPhoneNumber("+123456789");

        User user2 = new User();
        user2.setFirstName("Bob");
        user2.setLastName("Johnson");
        user2.setEmail("bob@example.com");
        user2.setPhoneNumber("+987654321");

        User user3 = new User();
        user3.setFirstName("Charlie");
        user3.setLastName("Williams");
        user3.setEmail("charlie@example.com");
        user3.setPhoneNumber("+192837465");

        FakeDatabase.TABLE_USER.add(user1);
        FakeDatabase.TABLE_USER.add(user2);
        FakeDatabase.TABLE_USER.add(user3);
    }
}