package org.kitchen.booting.service;

import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.domain.Tag;
import org.kitchen.booting.domain.userauth.User;
import org.kitchen.booting.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    private static final int BLOCK_PAGE_NUM_COUNT = 5;
    private static final int PAGE_POST_COUNT = 5;


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

    @Transactional
    public List<Tag> getTagList(Integer pageNum)
    {
        Page<Tag> page = tagRepository.findAll(PageRequest.of(pageNum-1, PAGE_POST_COUNT));

        List<Tag> tags = page.getContent();
        List<Tag> tagList = new ArrayList<>();

        for(Tag tag : tags)
        {
            tagList.add(tag);
        }
        return tagList;
    }

    public Integer[] getPageList(Integer curPageNum)
    {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // 총 게시글 수
        Double postsTotalCount = Double.valueOf(this.getTagCount());

        // 총 게시글 수를 기준으로 계산한 마지막 페이지 번호 계산
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        // 페이지 시작 번호 조정
        curPageNum = (curPageNum<=3) ? 1 : curPageNum-2;

        // 페이지 번호 할당
        for(int val=curPageNum, i=0; val<=blockLastPageNum; val++, i++) { pageList[i] = val; }

        return pageList;
    }

    @Transactional
    public Long getTagCount(){
        return tagRepository.count();
    }

}
