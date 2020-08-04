package org.kitchen.booting.service.userauth;


import lombok.AllArgsConstructor;
import org.kitchen.booting.domain.userauth.User;
import org.kitchen.booting.repository.userauth.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String userId) {
      Optional<User> user = userRepository.findById(userId);
      return user.orElseThrow(()->new UsernameNotFoundException(userId));
    }

}