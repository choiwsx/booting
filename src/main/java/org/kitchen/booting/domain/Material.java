package org.kitchen.booting.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.kitchen.booting.domain.id.MaterialId;

import javax.persistence.*;

@IdClass(MaterialId.class)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity(name="material")
@Table(name="tbl_material")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_no", updatable = false, nullable = false)
    private Long materialNo;
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_no", updatable = false, insertable = false)
    @JsonBackReference
    private Recipe recipe;

    private String content;

    public Long getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(Long materialNo) {
        this.materialNo = materialNo;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Material{" +
                "materialNo=" + materialNo +
                ", recipe=" + recipe +
                ", content='" + content + '\'' +
                '}';
    }
}
