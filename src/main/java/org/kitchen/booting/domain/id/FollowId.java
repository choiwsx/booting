package org.kitchen.booting.domain.id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.kitchen.booting.domain.userauth.User;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class FollowId implements Serializable {
    private User followUser;
    private User user;
}
