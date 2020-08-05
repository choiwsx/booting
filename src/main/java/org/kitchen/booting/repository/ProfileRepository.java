package org.kitchen.booting.repository;

import org.kitchen.booting.domain.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, String> {

    public void deleteByUserId(String userId);
    public Profile findByUserId(String userId);
    public List<Profile> findByUserIdContaining(String keyword);
    public Page<Profile> findByUserIdContaining(String keyword, Pageable pageable);
    public void deleteById(String userId);

}
