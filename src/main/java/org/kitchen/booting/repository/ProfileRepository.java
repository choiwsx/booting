package org.kitchen.booting.repository;

import org.kitchen.booting.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, String> {

    public void deleteByUserId(String userId);
    public Profile findByUserId(String userId);

}
