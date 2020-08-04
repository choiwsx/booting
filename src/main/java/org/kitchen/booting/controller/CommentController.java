package org.kitchen.booting.controller;

import org.kitchen.booting.domain.Comment;
import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.domain.Scrap;
import org.kitchen.booting.service.CommentService;
import org.kitchen.booting.service.RecipeService;
import org.kitchen.booting.service.ScrapService;
import org.kitchen.booting.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {


    RecipeService recipeService;
    ScrapService scrapService;

    @Autowired
    CommentService commentService;


    @Autowired
    public void setScrapService(ScrapService scrapService) { this.scrapService = scrapService; }
    @Autowired
    public void setRecipeService(RecipeService recipeService)
    {
        this.recipeService = recipeService;
    }

    @PostMapping("/comment/insert")
    public void writeComment(@RequestBody Comment comment){
        commentService.save(comment);
    }
//
//    @GetMapping(value = "/comment/{recipeNo}")
//    public ResponseEntity<List<Comment>> getComment(@PathVariable Long recipeNo) {
//        return new ResponseEntity<>(commentService.commentList(recipeNo),HttpStatus.OK);
//    }

    @RequestMapping(value = "/commentList", method = RequestMethod.POST)
    public List<Comment> commentList(@RequestParam("recipeNo") Long recipeNo) {
        return commentService.commentList(recipeNo);
    }

    @RequestMapping(value = "/comment/delete/{commentNo}",method = RequestMethod.DELETE)
    public Long deleteComment(@PathVariable Long commentNo){
        return commentService.delete(commentNo);
    }

    @RequestMapping(value = "/comment/update/{commentNo}",method = RequestMethod.PUT)
    public void updateComment(@RequestBody Comment comment ,@PathVariable Long commentNo){
     commentService.update(comment,commentNo);
    }

    @GetMapping(value = "/comment/{commentNo}")
    public Comment getComment(@PathVariable Long commentNo) {
        return commentService.findByCommentNo(commentNo);
    }

}