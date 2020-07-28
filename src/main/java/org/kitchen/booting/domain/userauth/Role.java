package org.kitchen.booting.domain.userauth;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity(name = "role")
@Table(name = "tbl_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_no", updatable = false, nullable = false)
    private Long roleNo;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany
    @JoinTable(
            name = "tbl_roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_no", referencedColumnName = "role_no"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_no", referencedColumnName = "privilege_no"))
    private Collection<Privilege> privileges;

    public Role(String name) {
        this.name = name;
    }

    public Long getRoleNo() {
        return roleNo;
    }

    public void setRoleNo(Long roleNo) {
        this.roleNo = roleNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }

    public Role() {
        users = new ArrayList<>();
        privileges = new ArrayList<>();
    }

    public void addUser(User user) {
        if(users==null) {
            System.out.println("null");
            users = new ArrayList<>();
        }
        users.add(user);
    }
}