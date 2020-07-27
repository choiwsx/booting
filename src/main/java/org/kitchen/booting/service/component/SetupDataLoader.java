//package org.kitchen.booting.service.component;
//
//import org.kitchen.booting.domain.userauth.Privilege;
//import org.kitchen.booting.domain.userauth.Role;
//import org.kitchen.booting.domain.userauth.UserAccount;
//import org.kitchen.booting.repository.userauth.PrivilegeRepository;
//import org.kitchen.booting.repository.userauth.RoleRepository;
//import org.kitchen.booting.repository.userauth.UserAccountRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import javax.transaction.Transactional;
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
//
//    boolean alreadySetup = false;
//
//    @Autowired
//    private UserAccountRepository userAccountRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private PrivilegeRepository privilegeRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    @Transactional
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//
//        if (alreadySetup)
//            return;
//        Privilege readPrivilege
//                = createPrivilegeIfNotFound("READ_PRIVILEGE");
//        Privilege writePrivilege
//                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
//
//        List<Privilege> adminPrivileges = Arrays.asList(
//                readPrivilege, writePrivilege);
//        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
//        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));
//
//        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
//        UserAccount userAccount = new UserAccount();
//        userAccount.setUserID("user");
//        userAccount.setPassword(passwordEncoder.encode("1234"));
//        userAccount.setEnabled(true);
//        userAccountRepository.save(userAccount);
//
//        alreadySetup = true;
//    }
//
//    @Transactional
//    Privilege createPrivilegeIfNotFound(String name) {
//
//        Privilege privilege = privilegeRepository.findByName(name);
//        if (privilege == null) {
//            privilege = new Privilege(name);
//            privilegeRepository.save(privilege);
//        }
//        return privilege;
//    }
//
//    @Transactional
//    Role createRoleIfNotFound(
//            String name, Collection<Privilege> privileges) {
//
//        Role role = r?oleRepository.findByName(name);
//        if (role == null) {
//            role = new Role(name);
//            role.setPrivileges(privileges);
//            roleRepository.save(role);
//        }
//        return role;
//    }
//}
