package org.kitchen.booting.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.kitchen.booting.domain.id.StepId;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity(name="step")
@Table(name="tbl_step")
@IdClass(StepId.class)
public class Step {
    @Id
    @Column(name = "step_no", updatable = false, nullable = false)
    private Long stepNo;
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_no", nullable = false, insertable = false)
    @JsonBackReference
    private Recipe recipe;
    private String thumbnail;
    private String content;

    public Long getStepNo() {
        return stepNo;
    }

    public void setStepNo(Long stepNo) {
        this.stepNo = stepNo;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

//    @Override
//    public String toString() {
//        return "Step{" +
//                "stepNo=" + stepNo +
//                ", recipe=" + recipe +
//                ", thumbnail='" + thumbnail + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }
}
