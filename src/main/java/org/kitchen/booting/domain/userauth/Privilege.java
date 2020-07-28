package org.kitchen.booting.domain.userauth;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@Entity(name = "privilege")
@Table(name = "tbl_privilege")
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "privilege_no", updatable = false, nullable = false)
    private Long privilegeNo;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public Privilege() {}

    public Privilege(String name) {
        this.name = name;
    }

}