package org.kitchen.booting.service;

import org.kitchen.booting.domain.Comment;
import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.repository.CommentRepository;
import org.kitchen.booting.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    RecipeRepository recipeRepository;

    @Transactional
    public Long delete(Long commentNo) {
       return commentRepository.deleteByCommentNo(commentNo);
    }

    public void update(Comment comment,Long commentNo){
        Comment updateComment = commentRepository.findByCommentNo(commentNo);
        updateComment.setReply(comment.getReply());
//        comment.setUpDate(comment.getUpDate());
        commentRepository.save(updateComment);
    }

    public Comment save(Comment comment) {
        commentRepository.save(comment);
        return comment;
    }

    public List<Comment> commentList(Long recipeNo){
        List<Comment> list = commentRepository.findByRecipeNo(recipeNo);
        return list;
    }

    public List<Comment> findAll() {
        List<Comment> list = commentRepository.findAll();
        return list;
    }

    public Comment findByCommentNo(Long commentNo){
        return commentRepository.findByCommentNo(commentNo);
    }

}
