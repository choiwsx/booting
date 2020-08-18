package org.kitchen.booting.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity(name = "category")
@Table(name = "tbl_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_no", updatable = false, nullable = false)
    private Long categoryNo;

    private String title;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "prev_category_no", referencedColumnName = "category_no")
    private Category mainCategory;


//    @JsonIgnore
    @OneToMany(mappedBy = "mainCategory")
    private List<Category> subCategories;


    @OneToMany(mappedBy = "category")
    private Set<Recipe> recipes = new LinkedHashSet<>();


    public String toString() {
        return categoryNo.toString();
    }

}