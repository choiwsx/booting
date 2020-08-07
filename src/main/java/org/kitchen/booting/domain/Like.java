package org.kitchen.booting.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.kitchen.booting.domain.id.LikeId;
import org.kitchen.booting.domain.id.ScrapId;
import org.kitchen.booting.domain.userauth.User;

import javax.persistence.*;
import java.util.Date;

@Data
@IdClass(LikeId.class)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "like")
@Table(name = "tbl_like")
public class Like {
//    @Id
//    private String userId;

    @ManyToOne
    @Id
    @JoinColumn(name = "user_id")
    private User user;
//    @Id
//    @Column(name="user_id", nullable = false)
//    private User user;
//    @Id
//    @Column(name="recipe_no", nullable = false)
//    private Long recipeNo;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_no", updatable = false, insertable = false)
    @JsonBackReference
    private Recipe recipe;

    @CreationTimestamp
    private Date regDate;

}
