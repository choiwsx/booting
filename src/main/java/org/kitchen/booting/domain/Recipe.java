package org.kitchen.booting.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "recipe")
@Table(name = "tbl_recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="recipe_no", updatable = false, nullable = false)
    private Long recipeNo;

    @CreationTimestamp
    private Date regDate;
    @UpdateTimestamp
    private Date upDate;
    private String cookingTime;
    private String difficulty;
    private String serving;
    private String thumbnail;
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Profile profile;

    @ManyToOne
    @JoinColumn(name="category_no", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Step> steps = new LinkedHashSet<>();

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Ingredient> ingredients = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "tbl_recipe_tag",
            joinColumns = @JoinColumn(name = "recipe_no",referencedColumnName = "recipe_no"),
            inverseJoinColumns = @JoinColumn(name = "tag_no",referencedColumnName = "tag_no"))
    private Set<Tag> tags;

    public boolean addTags(Tag tag){
        if(tags==null){
            tags = new LinkedHashSet<>();
        }
        return tags.add(tag);
    }

//    @GeneratedValue(generator = "hibernate-uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
//    @Column(name="recipe_uuid", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID recipeUuid;


    public void addToSteps(Step step) {
        step.setRecipe(this);
        this.steps.add(step);
    }

    public void addToIngredients(Ingredient ingredient) {
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
    }

    public Long getRecipeNo() {
        return recipeNo;
    }

    public void setRecipeNo(Long recipeNo) {
        this.recipeNo = recipeNo;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Date getUpDate() {
        return upDate;
    }

    public void setUpDate(Date upDate) {
        this.upDate = upDate;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getServing() {
        return serving;
    }

    public void setServing(String serving) {
        this.serving = serving;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getRecipeUuid() {
        return recipeUuid;
    }

    public void setRecipeUuid(UUID recipeUuid) {
        this.recipeUuid = recipeUuid;
    }

    public Set<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        steps.forEach(s->this.addToSteps(s));
    }

    public Set<Ingredient> getIngredients() { return ingredients; }

    public void setIngredients(List<Ingredient> Ingredients) { Ingredients.forEach(m->this.addToIngredients(m)); }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeNo=" + recipeNo +
                ", user=" + profile +
                ", category=" + category +
                ", regDate=" + regDate +
                ", upDate=" + upDate +
                ", cookingTime='" + cookingTime + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", serving='" + serving + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", recipeUuid='" + recipeUuid + '\'' +
//                ", steps=" + steps +
//                ", materials=" + materials +
                '}';
    }
}