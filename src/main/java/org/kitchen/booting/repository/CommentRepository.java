package org.kitchen.booting.repository;

import org.kitchen.booting.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    public Long deleteByCommentNo(Long commentNo);

    public List<Comment> findByRecipeNo(Long recipeNo);

    public Comment findByCommentNo(Long commentNo);


}
