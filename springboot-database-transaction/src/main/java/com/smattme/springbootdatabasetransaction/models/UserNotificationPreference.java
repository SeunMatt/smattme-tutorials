package com.smattme.springbootdatabasetransaction.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user_notification_preference")
public class UserNotificationPreference {

    @Id
    @SequenceGenerator(name = "user_notification_preference_id_seq", sequenceName = "user_notification_preference_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_notification_preference_id_seq")
    private long id;

    @ManyToOne
    private User user;

    private boolean emailNotificationEnabled;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isEmailNotificationEnabled() {
        return emailNotificationEnabled;
    }

    public void setEmailNotificationEnabled(boolean emailNotificationEnabled) {
        this.emailNotificationEnabled = emailNotificationEnabled;
    }
}
