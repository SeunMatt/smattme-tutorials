package com.smattme.springbootdatabasetransaction.repositories;

import com.smattme.springbootdatabasetransaction.models.User;
import com.smattme.springbootdatabasetransaction.models.UserNotificationPreference;
import org.springframework.data.repository.CrudRepository;

public interface UserNotificationPreferenceRepo extends CrudRepository<UserNotificationPreference, Long> {

    UserNotificationPreference getByUser(User user);
}
