package org.kitchen.booting.repository.userauth;


import org.kitchen.booting.domain.userauth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String role_admin);
}