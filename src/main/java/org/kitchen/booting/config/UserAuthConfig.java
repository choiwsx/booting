//package org.kitchen.booting.config;
//
//import org.kitchen.booting.domain.userauth.Role;
//import org.kitchen.booting.domain.userauth.User;
//import org.kitchen.booting.repository.userauth.RoleRepository;
//import org.kitchen.booting.repository.userauth.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import javax.transaction.Transactional;
//
//@Component
//public class UserAuthConfig implements ApplicationListener<ContextRefreshedEvent> {
//
//    boolean alreadySetup = false;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    @Transactional
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        if (alreadySetup)
//            return;
//        createRoleIfNotFound(1L,"ROLE_ADMIN");
//        createRoleIfNotFound(2L,"ROLE_USER");
//        createRoleIfNotFound(3L,"ROLE_PENDING");
//        createRoleIfNotFound(4L,"ROLE_SUSPENDED");
//        createRoleIfNotFound(5L,"ROLE_DELETED");
//
//        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
//        User user = new User();
//        user.setUserId("admin");
//        user.setPassword(passwordEncoder.encode("1234"));
//        user.setEnabled(true);
//        user.setRole(adminRole);
//        userRepository.save(user);
//
//        alreadySetup = true;
//    }
//
//    @Transactional
//    Role createRoleIfNotFound(Long roleNo, String name) {
//        Role role = roleRepository.findByName(name);
//        if (role == null) {
//            role = new Role(roleNo, name);
//            roleRepository.save(role);
//        }
//        return role;
//    }
//}
