package org.kitchen.booting.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.kitchen.booting.domain.id.FollowId;

import javax.persistence.*;
import java.util.Date;

@IdClass(FollowId.class)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "follow")
@Table(name = "tbl_follow")
public class Follow {

    @Id
    @Column(name="follow_user_id", nullable = false)
    private String followUserId;
    @Id
    @Column(name="user_id", nullable = false)
    private String userId;

    @CreationTimestamp
    private Date regDate;
    @UpdateTimestamp
    private Date upDate;
    private Boolean status;

    public String getUserId() { return userId;}

    public void setUserId(String userId) { this.userId = userId; }

    public String getFollowUserId() { return followUserId;}

    public void setFollowUserId(String followUserId) { this.followUserId = followUserId; }

    public Date getRegDate() { return regDate; }

    public void setRegDate(Date regDate) {this.regDate = regDate; }

    public Date getUpDate() { return upDate; }

    public void setUpDate(Date upDate) {this.upDate = upDate; }

    public Boolean getStatus() { return status; }

    public void setStatus(Boolean status) {this.status = status; }


}
