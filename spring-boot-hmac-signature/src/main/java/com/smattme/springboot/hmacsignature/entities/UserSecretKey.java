package com.smattme.springboot.hmacsignature.entities;

import com.smattme.springboot.hmacsignature.config.HmacSecretKeyAttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Table(name = "user_secret_keys")
@Entity
@SQLRestriction("abolished_at IS NULL")
public class UserSecretKey {

    @Id
    @SequenceGenerator(name = "user_secret_keys_id_seq", sequenceName = "user_secret_keys_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_secret_keys_id_seq")
    private long id;

    @OneToOne
    private User user;

    @Convert(converter = HmacSecretKeyAttributeConverter.class)
    private String hmacSecretKey;

    private String clientId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime abolishedAt;

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getHmacSecretKey() {
        return hmacSecretKey;
    }

    public void setHmacSecretKey(String hmacSecretKey) {
        this.hmacSecretKey = hmacSecretKey;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public LocalDateTime getAbolishedAt() {
        return abolishedAt;
    }

    public void setAbolishedAt(LocalDateTime abolished) {
        this.abolishedAt = abolished;
    }
}
