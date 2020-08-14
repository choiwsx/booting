package org.kitchen.booting.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.kitchen.booting.domain.userauth.User;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class FollowDTO {
    //팔로우를 받는 사람:팔로이이이
    private String followeeId;
    //팔로우를 하는 사람:팔로워
    private String followerId;


    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

    public String getFolloweeId() {
        return followeeId;
    }

    public void setFolloweeId(String followeeId) {
        this.followeeId = followeeId;
    }
}
