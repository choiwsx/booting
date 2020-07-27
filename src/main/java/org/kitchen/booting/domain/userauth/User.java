package org.kitchen.booting.domain.userauth;

import lombok.Getter;
import org.kitchen.booting.domain.Profile;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Entity(name = "user")
@Table(name = "tbl_user")
public class User {
    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    private String userId;

    private String password;
    private Boolean enabled;
    private Boolean tokenExpired;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Profile profile;

//    @ManyToMany
//    @JoinTable(
//            name = "users_roles",
//            joinColumns = @JoinColumn(
//                    name = "user_id", referencedColumnName = "user_id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "role_id", referencedColumnName = "id"))
//    private Collection<Role> roles;

    public void setUserId(String userId) {
        this.userId = userId;
        this.profile.setUserId(userId);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setTokenExpired(boolean tokenExpired) {
        this.tokenExpired = tokenExpired;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
        this.profile.setUser(this);
    }

//    public void setRoles(Collection<Role> roles) {
//        this.roles = roles;
//    }

    public User() {
        profile = new Profile();
        profile.setUser(this);
        enabled = false;
    }

}

