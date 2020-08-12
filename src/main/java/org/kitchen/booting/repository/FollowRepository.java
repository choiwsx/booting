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
}
