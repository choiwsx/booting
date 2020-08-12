package org.kitchen.booting.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.kitchen.booting.domain.userauth.User;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class FollowDTO {
    private String followerId;
    private String followeeId;

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
