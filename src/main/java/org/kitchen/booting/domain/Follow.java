package org.kitchen.booting.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.kitchen.booting.domain.id.FollowId;
import org.kitchen.booting.domain.userauth.User;

import javax.persistence.*;
import java.util.Date;

@IdClass(FollowId.class)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "follow")
@Table(name = "tbl_follow")
public class Follow {

    // 팔로우를 받는사람 : 팔로이이이
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "followee_user_id")
    private Profile followee;

    // 팔로우를 하는사람 : 팔로워어어
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "follower_user_id")
    private Profile follower;

    @CreationTimestamp
    private Date regDate;
    @UpdateTimestamp
    private Date upDate;

    private Boolean status;

    public Follow(Profile followee, Profile follower, Boolean status) {
        this.followee = followee;
        this.follower = follower;
        this.status = status;
    }

    public Profile getFollowee() {
        return followee;
    }

    public void setFollowee(Profile followee) {
        this.followee = followee;
    }

    public Profile getFollower() {
        return follower;
    }

    public void setFollower(Profile follower) {
        this.follower = follower;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Date getUpDate() {
        return upDate;
    }

    public void setUpDate(Date upDate) {
        this.upDate = upDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
