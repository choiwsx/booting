package org.kitchen.booting.repository;

import org.kitchen.booting.domain.Profile;
import org.kitchen.booting.domain.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, String> {

    public void deleteByUserId(String userId);
    public Profile findByUserId(String userId);
//    public Boolean findByUserIdAndIsPrivate(String userId);
    public List<Profile> findByUserIdContaining(String keyword);
    public Page<Profile> findByUserIdContaining(String keyword, Pageable pageable);
    public void deleteById(String userId);
    @Query(value="SELECT user_id FROM tbl_profile where user_id like %:keyword%", nativeQuery = true)
    public List<String> search(@Param("keyword") String keyword);

    @Query(value="SELECT followee_user_id \n" +
            "from (\n" +
            "SELECT followee_user_id, COUNT(*) as c FROM tbl_follow  GROUP BY followee_user_id ORDER BY c DESC LIMIT 5) tf;", nativeQuery = true)
    public List<String> getPopularProfile();
}
