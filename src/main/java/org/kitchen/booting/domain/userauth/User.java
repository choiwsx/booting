package org.kitchen.booting.domain.userauth;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.kitchen.booting.domain.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Entity(name = "user")
@Table(name = "tbl_user")
public class User implements UserDetails {
    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    private String userId;

    private String password;
    private String email;

    private Boolean enabled;

//    @CreationTimestamp
//    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
//    private LocalDateTime createdAt;
//    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @OneToOne(mappedBy = "user", optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Profile profile;

    @OneToOne(mappedBy = "user", orphanRemoval = true)
//    @JsonBackReference
    private EmailVerificationToken emailVerificationToken;

    @ManyToOne
    @JoinTable(
            name = "tbl_users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_no", referencedColumnName = "role_no"))
    private Role role;

    public void setUserId(String userId) {
        this.userId = userId;
        this.profile.setUserId(userId);
    }

    public User setUpdatedUser(User userUpdated)
    {
        //profile update
        if(userUpdated.profile.getNickname()!=null
                || !userUpdated.profile.getNickname().equals("")) {
            this.profile.setNickname(userUpdated.profile.getNickname());
        }
        if(userUpdated.profile.getBio()!=null
                || !userUpdated.profile.getBio().equals("")) {
            this.profile.setBio(userUpdated.profile.getBio());
        }
        if(userUpdated.profile.getThumbnail()!=null
                || !userUpdated.profile.getThumbnail().equals("")) {
            this.profile.setThumbnail(userUpdated.profile.getThumbnail());
        }
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public EmailVerificationToken getEmailVerificationToken() {
        return this.emailVerificationToken;
    }

    public void setEmailVerificationToken(EmailVerificationToken emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfile(Profile profile) {
        if(profile == null) {
            if(this.profile != null) {
                this.profile.setUser(null);
            }
        } else {
            profile.setUser(this);
        }
        this.profile = profile;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public User() {
        profile = new Profile();
        profile.setUser(this);
        enabled = false;
    }

    public User(String userId, String password, String email) {
        this();
        this.userId = userId;
        this.password = password;
        this.email = email;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void markVerificationConfirmed() {
        setEmailVerified(true);
    }

    public void setEmailVerified(Boolean emailVerified) {
        enabled = emailVerified;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}

