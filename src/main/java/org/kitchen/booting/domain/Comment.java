package org.kitchen.booting.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.kitchen.booting.domain.id.CommentId;
import org.kitchen.booting.domain.id.IngredientId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@IdClass(CommentId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "comment")
@Table(name = "tbl_comment")
public class Comment implements Serializable {
    @Id
    @Column(name="comment_no", nullable = false)
    private Long commentNo;

    @Id
    @Column(name="recipe_no", nullable = false)
    private Long recipeNo;

    @Column(name="user_id")
    private String userId;

    private String reply;

    @CreationTimestamp
    private LocalDateTime regDate;

    @UpdateTimestamp
    private LocalDateTime upDate;


}
