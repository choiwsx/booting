package org.kitchen.booting.special;

import org.kitchen.booting.domain.Profile;
import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.domain.enums.CookingTime;
import org.kitchen.booting.domain.userauth.Role;
import org.kitchen.booting.domain.userauth.User;
import org.kitchen.booting.repository.CategoryRepository;
import org.kitchen.booting.repository.ProfileRepository;
import org.kitchen.booting.repository.RecipeRepository;
import org.kitchen.booting.repository.userauth.RoleRepository;
import org.kitchen.booting.repository.userauth.UserRepository;
import org.kitchen.booting.service.RecipeService;
import org.kitchen.booting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class Special {
    private static AtomicLong atomicLong;

    UserRepository userRepository;

    RoleRepository roleRepository;

    ProfileRepository profileRepository;

    RecipeRepository recipeRepository;

    CategoryRepository categoryRepository;

    PasswordEncoder passwordEncoder;

    Role userRole;

    @Autowired
    public Special(UserRepository userRepository, RoleRepository roleRepository, ProfileRepository profileRepository, RecipeRepository recipeRepository,
                   CategoryRepository categoryRepository, PasswordEncoder passwordEncoder) {
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
        this.profileRepository=profileRepository;
        this.recipeRepository=recipeRepository;
        this.categoryRepository=categoryRepository;
        this.passwordEncoder=passwordEncoder;
        atomicLong = new AtomicLong(100);
        userRole = roleRepository.findByName("ROLE_USER");
    }

    public void makeNewUser() {
        User newUser = new User();
        newUser.setUserId("user"+atomicLong.get());
        newUser.setEmail("user"+atomicLong.get()+"@cookstagram.me");
        newUser.setEnabled(true);
        newUser.setPassword(passwordEncoder.encode("1234"));
        newUser.setRole(userRole);
        newUser.setCreatedAt(new Date());
        Profile newProfile = new Profile();
        newProfile.setUser(newUser);
        newUser.setProfile(newProfile);
        newProfile.setIsPrivate(false);
        newProfile.setBio("안녕? 나는 새로운 주방장! 최고의 주방장! 쿡스타그램 평정!");
        newProfile.setNickname("최고주방장"+atomicLong.getAndIncrement());
        newProfile.setThumbnail("profile.png");
        userRepository.save(newUser);
    }

    public void makeNewUser(int count) {
        for(int i = 0; i<count; i++) {
            makeNewUser();
        }

    }


}
