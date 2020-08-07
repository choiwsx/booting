package org.kitchen.booting.service;

import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.domain.Tag;
import org.kitchen.booting.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    public List<Tag> findAll(){
        return tagRepository.findAll();
    }

    public void insert (Recipe recipe) {
        List<Tag> tagList = new ArrayList<Tag>();
        String content = recipe.getContent();
        String tags[] = content.split("\\s*#\\s*");
        String[] tagContent = new String[tags.length];
        for (int i = 1; i < tags.length; i++) {
            Tag tag = new Tag();
            if (tags[i].indexOf(" ") != -1) {
                tagContent[i] = tags[i].substring(0, (tags[i].indexOf(" ")));
            } else {
                tagContent[i] = tags[i];
            }
            tag.setContent(tagContent[i]);
            if (check(tag)) {
                Tag oldTag = tagRepository.findByContent(tag.getContent());
                oldTag.addRecipes(recipe);
                tagRepository.saveAndFlush(oldTag);
            } else {
                tag.addRecipes(recipe);
                tagRepository.saveAndFlush(tag);
            }
        }
    }


    @Transactional
    public void delete(String content) {
        tagRepository.deleteByContent(content);
    }

    @Transactional
    public void delete(Long tagNo) {
        tagRepository.deleteByTagNo(tagNo);
    }

    public boolean check(Tag tag){
       Tag nullCheck = tagRepository.findByContent(tag.getContent());
            return nullCheck != null ? true: false;
    }

    public List<Tag> randomTagList(){
        List<Tag> randomTagList = tagRepository.findTag();
        return randomTagList;
    }

    public List<String> search(String keyword){
        return tagRepository.search(keyword);
    }

}
