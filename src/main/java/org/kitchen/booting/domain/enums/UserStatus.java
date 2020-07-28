package org.kitchen.booting.domain.enums;

import org.kitchen.booting.domain.userauth.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserStatus {
    @Id
    private Long statusNo;

//    @OneToMany(mappedBy = "status")
//    private List<User> users = new ArrayList<>();
}
