package org.kitchen.booting.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.kitchen.booting.domain.userauth.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class ProfileDTO {
    private String userId;
    private String nickname;
    private String thumbnail;

    public ProfileDTO(String userId, String nickname, String thumbnail) {
        this.userId = userId;
        this.nickname = nickname;
        this.thumbnail = thumbnail;
    }
//    private String bio;
//    private Boolean isPrivate;
}