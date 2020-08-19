package org.kitchen.booting.domain.userauth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Getter
@Setter
@Entity(name = "role")
@Table(name = "tbl_role")
public class Role {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_no", updatable = false, nullable = false)
    private Long roleNo;

    private String name;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private Collection<User> users;

    public void addUser(User user) {
        users.add(user);
    }

    public Role() {
        users = new HashSet<>();
    }

    public Role(String name) {
        this();
        this.name = name;
    }

    public Role(Long roleNo, String name) {
        this();
        this. roleNo= roleNo;
        this.name = name;
    }

    public Long getRoleNo() {
        return roleNo;
    }

    public void setRoleNo(Long roleNo) {
        this.roleNo = roleNo;
    }
    //    @ManyToMany
//    @JoinTable(
//            name = "roles_privileges",
//            joinColumns = @JoinColumn(
//                    name = "role_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "privilege_id", referencedColumnName = "id"))
//    private Collection<Privilege> privileges;
}