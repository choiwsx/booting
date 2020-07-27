//package org.kitchen.booting.domain.userauth;
//
//import javax.persistence.*;
//import java.util.Collection;
//
//@Entity(name = "role")
//@Table(name = "tbl_role")
//public class Role {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id", updatable = false, nullable = false)
//    private Long id;
//
//    private String name;
//
//    @ManyToMany(mappedBy = "roles")
//    private Collection<User> users;
//
//    @ManyToMany
//    @JoinTable(
//            name = "roles_privileges",
//            joinColumns = @JoinColumn(
//                    name = "role_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "privilege_id", referencedColumnName = "id"))
//    private Collection<Privilege> privileges;
//}