package org.kitchen.booting.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kitchen.booting.domain.id.ScrapId;
import org.kitchen.booting.domain.userauth.User;

import javax.persistence.*;

@Data
@IdClass(ScrapId.class)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "scrap")
@Table(name = "tbl_scrap")
public class Scrap {
//    @Id
//    private String userId;

    @ManyToOne(optional = false)
    @Id
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_no", updatable = false, insertable = false)
    @JsonBackReference
    private Recipe recipe;


}