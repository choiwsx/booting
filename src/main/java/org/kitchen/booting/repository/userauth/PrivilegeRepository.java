package org.kitchen.booting.repository.userauth;

import org.kitchen.booting.domain.userauth.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    public Privilege findByName(String name);

}
