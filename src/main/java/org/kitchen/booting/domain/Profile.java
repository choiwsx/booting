package org.kitchen.booting.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.kitchen.booting.domain.userauth.User;

import javax.persistence.*;
import java.util.*;

@AllArgsConstructor
@Entity(name = "profile")
@Table(name = "tbl_profile")
public class Profile {
    @Id
    private String userId;
    private String nickname;
    private String thumbnail;
    private String bio;
    @Column(name = "private")
    private Boolean isPrivate;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private Set<Recipe> recipes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "profile",  fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Like> likes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "profile",  fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Scrap> scraps = new LinkedHashSet<>();

//    setter and getter

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        if(isPrivate!=null) {
            this.isPrivate = isPrivate;
        } else {
            this.isPrivate = false;
        }
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public Set<Scrap> getScraps() {
        return scraps;
    }

    public void setScraps(Set<Scrap> scraps) {
        this.scraps = scraps;
    }

    @Override
    public String toString() {
        return userId+"";
    }

    public Profile() {
        isPrivate = false;
    }

    public Profile(String nickname, String thumbnail, String bio, Boolean isPrivate) {
        this();
        this.nickname = nickname;
        this.thumbnail = thumbnail;
        this.bio = bio;
        setIsPrivate(isPrivate);
    }
    //    }
//        this.followings = followings;
//    public void setFollowings(Set<Profile> followings) {
//
//    }
//        return followings;
//    public Set<Profile> getFollowings() {
//
//    }
//        this.followers = followers;
//    public void setFollowers(Set<Profile> followers) {
//
//    }
//        return followers;
//    public Set<Profile> getFollowers() {
//    }
//        follwing.getFollowers().add(this);
//        this.followings.add(follwing);
//    public void addFollowing(Profile follwing) {
//
//    }
//        follower.getFollowings().add(this);
//        this.followers.add(follower);
//    public void addFollower(Profile follower) {
//    util
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "uuid2")
//    @Column(columnDefinition = "BINARY(16)", name = "user_uuid")

//    private UUID userUuid;
    //    constructor
//    override
//    public UUID getUserUuid() {
//        return userUuid;
//    }
//
//    public void setUserUuid(UUID userUuid) {
//        this.userUuid = userUuid;
//    }

//    @ManyToMany
//    @JoinTable(
//            name = "tbl_follow",
//            joinColumns = @JoinColumn(
//                    name = "follower_user_id", referencedColumnName = "user_id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "followee_user_id", referencedColumnName = "user_id"))
//    private Set<Profile> followings = new HashSet<>();
//
//    @ManyToMany(mappedBy = "followings", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Profile> followers = new HashSet<>();
}