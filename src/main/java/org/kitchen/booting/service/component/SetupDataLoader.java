package org.kitchen.booting.service.component;

import org.kitchen.booting.domain.userauth.Privilege;
import org.kitchen.booting.domain.userauth.Role;
import org.kitchen.booting.domain.userauth.User;
import org.kitchen.booting.repository.userauth.PrivilegeRepository;
import org.kitchen.booting.repository.userauth.RoleRepository;
import org.kitchen.booting.repository.userauth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

//        insert into tbl_privilege values ('100', 'READ_ALL'), ('109','READ_NONE'), ('500', 'WRITE_ALL'), ('501','WRITE_OWN'), ('509','WRITE_NONE'), ('999', 'LOGIN_DENIED');
//        insert into tbl_role values ('999', 'ADMIN_ROLE'), ('100','USER_ROLE'), ('101', 'PENDING_ROLE'), ('400','SUSPENDED_ROLE'), ('500','DELETED_ROLE');

        if (alreadySetup)
            return;
        Privilege readAllPrivilege
                = createPrivilegeIfNotFound("READ_ALL");
        Privilege readNonePrivilege
                = createPrivilegeIfNotFound("READ_NONE");
        Privilege writeAllPrivilege
                = createPrivilegeIfNotFound("WRITE_All");
        Privilege writeOwnPrivilege
                = createPrivilegeIfNotFound("WRITE_OWN");
        Privilege writeNonePrivilege
                = createPrivilegeIfNotFound("WRITE_NONE");
        Privilege loginDeniedPrivilege
                = createPrivilegeIfNotFound("LOGIN_DENIED");

        createRoleIfNotFound("ROLE_ADMIN", Arrays.asList(readAllPrivilege, writeAllPrivilege));
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readAllPrivilege, writeOwnPrivilege));
        createRoleIfNotFound("ROLE_PENDING", Arrays.asList(readAllPrivilege, writeNonePrivilege));
        createRoleIfNotFound("ROLE_SUSPENDED", Arrays.asList(readNonePrivilege, writeNonePrivilege));
        createRoleIfNotFound("ROLE_DELETED", Arrays.asList(loginDeniedPrivilege));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User user = new User();
        user.setUserId("admin");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setRoles(Arrays.asList(adminRole));
        user.setEnabled(true);
        userRepository.save(user);

        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}