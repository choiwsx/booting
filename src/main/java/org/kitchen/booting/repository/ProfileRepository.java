package org.kitchen.booting.repository;

import org.kitchen.booting.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, String> {

    public void deleteByUserId(String userId);
    public Profile findByUserId(String userId);
    public List<Profile> findByUserIdContaining(String keyword);

}
