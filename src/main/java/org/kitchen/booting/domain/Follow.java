//package org.kitchen.booting.domain;
//
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//import org.kitchen.booting.domain.id.FollowId;
//import org.kitchen.booting.domain.userauth.User;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@IdClass(FollowId.class)
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity(name = "follow")
//@Table(name = "tbl_follow")
//public class Follow {
//
//    @ManyToOne(optional = false)
//    @Id
//    @JoinColumn(name = "follow_user_id")
//    @JsonManagedReference
//    private User followUser;
//
//    @ManyToOne(optional = false)
//    @Id
//    @JoinColumn(name = "user_id")
//    @JsonManagedReference
//    private User user;
//
//    @CreationTimestamp
//    private Date regDate;
//    @UpdateTimestamp
//    private Date upDate;
//    private Boolean status;
//
//    public User getUser() { return user; }
//
//    public void setUser(User user) { this.user = user; }
//
//    public User getFollowUser() { return followUser;}
//
//    public void setFollowUser(User followUser) { this.followUser = followUser; }
//
//    public Date getRegDate() { return regDate; }
//
//    public void setRegDate(Date regDate) {this.regDate = regDate; }
//
//    public Date getUpDate() { return upDate; }
//
//    public void setUpDate(Date upDate) {this.upDate = upDate; }
//
//    public Boolean getStatus() { return status; }
//
//    public void setStatus(Boolean status) {this.status = status; }
//
//}
