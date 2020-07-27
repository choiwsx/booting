//package org.kitchen.booting.domain.userauth;
//
//import javax.persistence.*;
//import java.util.Collection;
//
//@Entity(name = "privilege")
//@Table(name = "tbl_privilege")
//public class Privilege {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", updatable = false, nullable = false)
//    private Long id;
//
//    private String name;
//
//    @ManyToMany(mappedBy = "privileges")
//    private Collection<Role> roles;
//}