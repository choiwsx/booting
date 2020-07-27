package org.kitchen.booting.repository.userauth;

import org.kitchen.booting.domain.userauth.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    public void deleteByUserId(String userId);

}
