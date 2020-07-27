package org.kitchen.booting.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "category")
@Table(name = "tbl_category")
public class Category {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_no")
    private Long categoryNo;

    private String title;

    @ManyToOne
    @JoinColumn(name = "prev_category_no", updatable = false, insertable = false)
    @JsonBackReference
    private Category prevCategory;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Recipe> recipes = new ArrayList<>();

    @Override
    public String toString() {
        return categoryNo+"";
    }



}