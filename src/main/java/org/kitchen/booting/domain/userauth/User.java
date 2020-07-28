package org.kitchen.booting.domain.userauth;

import lombok.Getter;
import lombok.Setter;
import org.kitchen.booting.domain.Profile;
import org.kitchen.booting.domain.enums.UserStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@Getter
@Setter
@Entity(name = "user")
@Table(name = "tbl_user")
public class User {
    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    private String userId;

    private String password;
    private boolean enabled;
    private boolean tokenExpired;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Profile profile;

//    @ManyToOne
//    @JoinColumn(name = "status")
//    private UserStatus status;

    @ManyToMany
    @JoinTable(
            name = "tbl_users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_no", referencedColumnName = "role_no"))
    private Collection<Role> roles = new ArrayList<>();

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

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
        Iterator<Role> iterator = roles.iterator();
        while(iterator.hasNext()) {
            iterator.next().addUser(this);
        }
    }

    public void addRole(Role role) {
        roles.add(role);
        role.addUser(this);
    }

    public User() {
        profile = new Profile();
        profile.setUser(this);
        enabled = false;
    }

}

