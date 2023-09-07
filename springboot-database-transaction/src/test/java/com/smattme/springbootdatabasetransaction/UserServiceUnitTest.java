package com.smattme.springbootdatabasetransaction;

import com.github.javafaker.Faker;
import com.smattme.springbootdatabasetransaction.models.User;
import com.smattme.springbootdatabasetransaction.repositories.UserNotificationPreferenceRepo;
import com.smattme.springbootdatabasetransaction.repositories.UserRepository;
import com.smattme.springbootdatabasetransaction.services.EmailService;
import com.smattme.springbootdatabasetransaction.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {SpringbootDatabaseTransactionApplication.class})
@Testcontainers
public class UserServiceUnitTest {

    @Autowired
    private UserService userService;

    @SpyBean
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserNotificationPreferenceRepo userNotificationPreferenceRepo;


    @Test
    void givenRequiredPrams_whenCreateTransactional_thenReturnCreatedUser() {

        var faker = Faker.instance();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();

        User user = userService.createTransactional(firstName, lastName, email);
        assertNotNull(user.getId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());

        var preference = userNotificationPreferenceRepo.getByUser(user);
        assertEquals(user, preference.getUser());
        assertTrue(preference.isEmailNotificationEnabled());

    }

    @Test
    void givenRequiredPrams_whenCreateTransactionTemplate_thenReturnCreatedUser() {

        var faker = Faker.instance();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();

        User user = userService.createTransactionTemplate(firstName, lastName, email);
        assertNotNull(user.getId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());

        var preference = userNotificationPreferenceRepo.getByUser(user);
        assertEquals(user, preference.getUser());
        assertTrue(preference.isEmailNotificationEnabled());

    }

    @Test
    void givenDuplicateEmail_whenCreateTransactional_thenSendEmailTwiceAndThrowException() {

        var faker = Faker.instance();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();

        //create first user record
        userService.createTransactional(firstName, lastName, email);

        //attempting to create another user with the same email
        Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> userService.createTransactional(firstName, lastName, email));

        //email is sent twice, despite the second attempt failed
        Mockito.verify(emailService, Mockito.times(2))
                .sendWelcomeEmail(Mockito.any(User.class));
    }


    @Test
    void givenDuplicateEmail_whenCreateTransactionTemplate_thenSendEmailOnceAndThrowException() {

        var faker = Faker.instance();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();

        //create first user record
        userService.createTransactionTemplate(firstName, lastName, email);

        //attempting to create another user with the same email
        Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> userService.createTransactionTemplate(firstName, lastName, email));

        //email is only sent for the first attempt
        Mockito.verify(emailService, Mockito.times(1))
                .sendWelcomeEmail(Mockito.any(User.class));
    }

    @Test
    void givenEmailServiceThrowsException_whenCreateTransactional_thenRollbackCreatedUser() {

        var faker = Faker.instance();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();

        //mock the emailService to throw runtime exception
        Mockito.doThrow(new RuntimeException("EmailService failed"))
                .when(emailService).sendWelcomeEmail(Mockito.any(User.class));

        assertThrows(RuntimeException.class,
                () -> userService.createTransactional(firstName, lastName, email));

        boolean recordCreated = userRepository.existsByEmail(email);
        assertFalse(recordCreated);

    }


    @Test
    void givenEmailServiceThrowsException_whenCreateTransactionTemplate_thenSaveUser() {

        var faker = Faker.instance();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();

        //mock the emailService to throw runtime exception
        Mockito.doThrow(new RuntimeException("EmailService failed"))
                .when(emailService).sendWelcomeEmail(Mockito.any(User.class));

        assertThrows(RuntimeException.class,
                () -> userService.createTransactionTemplate(firstName, lastName, email));

        boolean recordCreated = userRepository.existsByEmail(email);
        assertTrue(recordCreated);

    }


}
