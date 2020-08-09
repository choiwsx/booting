package org.kitchen.booting.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
//    @Column(name="id")
    private String userId;
    private String nickname;
    private String thumbnail;
    private String bio;
    @Column(name = "private")
    private Boolean isPrivate;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
//
//    @JoinColumn(name = "user_id", referencedColumnName = "user_id", updatable = false, nullable = false)
//    @JsonManagedReference
//    private User user;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private Set<Recipe> recipes = new LinkedHashSet<>();

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
}