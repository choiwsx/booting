package org.kitchen.booting.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.kitchen.booting.domain.userauth.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity(name = "profile")
@Table(name = "tbl_profile")
public class Profile {
    @Id
    @Column(name="user_id")
    private String userId;

    @OneToOne
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private User user;

    private String nickname;
    private String thumbnail;
    private String bio;
    @Column(name = "private")
    private String isPrivate;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<Recipe> recipes = new ArrayList<>();

    //@GenericGenerator(name = "uuid2", strategy = "uuid2")
    //@GeneratedValue(generator = "uuid2")
    //@GeneratedValue(generator = "hibernate-uuid")
    //@GenericGenerator(name = "uuid", strategy = "uuid2")
    //@Column(name = "user_uuid", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", name = "user_uuid")
    private UUID userUuid;

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

    public String getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(String isPrivate) {
        this.isPrivate = isPrivate;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }

    @Override
    public String toString() {
        return userId+"";
    }
}