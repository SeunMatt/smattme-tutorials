package com.smattme.springbootdatabasetransaction.services;

import com.smattme.springbootdatabasetransaction.models.AuditTrail;
import com.smattme.springbootdatabasetransaction.models.User;
import com.smattme.springbootdatabasetransaction.models.UserNotificationPreference;
import com.smattme.springbootdatabasetransaction.repositories.AuditTrailRepository;
import com.smattme.springbootdatabasetransaction.repositories.UserNotificationPreferenceRepo;
import com.smattme.springbootdatabasetransaction.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final UserNotificationPreferenceRepo userNotificationPreferenceRepo;
    private final TransactionTemplate transactionTemplate;
    private final AuditTrailRepository auditTrailRepository;

    public UserService(UserRepository userRepository, EmailService emailService, UserNotificationPreferenceRepo userNotificationPreferenceRepo,
                       TransactionTemplate transactionTemplate, AuditTrailRepository auditTrailRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.userNotificationPreferenceRepo = userNotificationPreferenceRepo;
        this.transactionTemplate = transactionTemplate;
        this.auditTrailRepository = auditTrailRepository;
    }


    @Transactional
    public User createTransactional(String firstName, String lastName, String email) {

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user = userRepository.save(user);

        //save default user notification preference
        UserNotificationPreference preference = new UserNotificationPreference();
        preference.setUser(user);
        preference.setEmailNotificationEnabled(true);
        userNotificationPreferenceRepo.save(preference);

        //save audit trail
        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setAction("USER CREATED");
        auditTrail.setActor("Logged In User");
        auditTrailRepository.save(auditTrail);

        //send welcome email
        emailService.sendWelcomeEmail(user);

        return user;
    }


    public User createTransactionTemplate(String firstName, String lastName, String email) {

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);


        //save default user notification preference
        UserNotificationPreference preference = new UserNotificationPreference();
        preference.setUser(user);
        preference.setEmailNotificationEnabled(true);

        transactionTemplate.execute(status -> {
            userRepository.save(user);
            userNotificationPreferenceRepo.save(preference);
            status.flush();
            return null;

        });

        //this operation is not included in the DB transaction
        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setAction("USER CREATED");
        auditTrail.setActor("Logged In User");
        auditTrailRepository.save(auditTrail);

        //send welcome email
        emailService.sendWelcomeEmail(user);

        return user;
    }



}
