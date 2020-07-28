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
    private String userId;

    @OneToOne
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private User user;

    private String nickname;
    private String email;
    private String thumbnail;
    private String bio;
    @Column(name = "private")
    private String isPrivate;

    @OneToMany(mappedBy = "profile", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Recipe> recipes = new ArrayList<>();

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setBio(String bio) { this.bio = bio;  }

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

    public void addRecipe(Recipe recipe) {
        recipe.setProfile(this);
        recipes.add(recipe);
    }
}