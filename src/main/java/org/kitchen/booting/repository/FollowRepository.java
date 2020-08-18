package org.kitchen.booting.repository;

import org.kitchen.booting.domain.Follow;
import org.kitchen.booting.domain.Profile;
import org.kitchen.booting.domain.id.FollowId;
import org.kitchen.booting.domain.userauth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {

    public void delete(Follow follow);
    public Follow save(Follow follow);
    public Follow findByFollowerAndFollowee(Profile follower, Profile followee);
    // 비공개 계정한테 친구신청한 팔로우 찾는당
    public List<Follow> findByFolloweeAndStatusIsTrue(Profile followee);
    // 공개 계정한테 친구신청한 팔로우 찾는당 --> 이미 친구임
    public List<Follow> findByFolloweeAndStatusIsFalse(Profile followee);
    public List<Follow> findByFollowerAndStatusIsFalse(Profile followee);
}
