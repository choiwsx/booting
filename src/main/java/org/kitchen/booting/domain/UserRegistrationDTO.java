package org.kitchen.booting.domain;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.Setter;
import org.kitchen.booting.domain.userauth.User;

@Getter
@Setter
public class UserRegistrationDTO {
    private String userId;
    private String password;
    private String email;
    private String nickname;
    private String thumbnail;
    private String bio;
    private Boolean isPrivate;

    public UserRegistrationDTO() {}

    public User getUser() {
        return new User(userId, password, email);
    }

    public void setUser(User user) {
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.email = user.getEmail();
    }

    public Profile getProfile() {
        return new Profile(nickname, thumbnail, bio, isPrivate);
    }

    public void setProfile(Profile profile) {
        this.nickname = profile.getNickname();
        this.thumbnail = profile.getThumbnail();
        this.bio = profile.getBio();
        this.isPrivate = profile.getIsPrivate();
    }

}
