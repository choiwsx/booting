package org.kitchen.booting.service;

import org.kitchen.booting.domain.userauth.User;
//import org.kitchen.booting.repository.userauth.PrivilegeRepository;
//import org.kitchen.booting.repository.userauth.RoleRepository;
import org.kitchen.booting.repository.userauth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
//    @Autowired
//    RoleRepository roleRepository;
//    @Autowired
//    PrivilegeRepository privilegeRepository;
    @Autowired
    ProfileService profileService;

    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void delete(String userId) {
        userRepository.deleteByUserId(userId);
    }


}
