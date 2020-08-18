package org.kitchen.booting.domain;

import lombok.Data;

@Data
public class ProfileDTO {
    private String userId;
    private String nickname;

    public ProfileDTO(String userId, String nickname)
    {
        this.userId = userId;
        this.nickname = nickname;
    }
}
