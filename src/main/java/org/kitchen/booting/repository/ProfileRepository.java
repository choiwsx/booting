package org.kitchen.booting.repository;

import org.kitchen.booting.domain.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, String> {

    public void deleteByUserId(String userId);
    public Profile findByUserId(String userId);
    public List<Profile> findByUserIdContaining(String keyword);
    public Page<Profile> findByUserIdContaining(String keyword, Pageable pageable);
    public void deleteById(String userId);
    @Query(value="SELECT nickname FROM tbl_profile where nickname like %:keyword%", nativeQuery = true)
    public List<String> search(@Param("keyword") String keyword);

}
