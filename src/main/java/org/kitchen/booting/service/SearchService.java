package org.kitchen.booting.service;

import org.kitchen.booting.domain.AutoCompleteDTO;
import org.kitchen.booting.domain.Profile;
import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.domain.Tag;
import org.kitchen.booting.repository.ProfileRepository;
import org.kitchen.booting.repository.RecipeRepository;
import org.kitchen.booting.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SearchService {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private TagRepository tagRepository;

    private static final int BLOCK_PAGE_NUM_COUNT = 5; // 블럭에 존재하는 페이지 수
    private static final int PAGE_POST_COUNT = 5; // 한 페이지에 존재하는 게시글 수

    private final Logger logger = LoggerFactory.getLogger(SearchService.class);

    @Transactional
    public List<Recipe> getRecipeList(String keyword, Integer pageNum)
    {
        Page<Recipe> page = recipeRepository.findByTitleContaining(keyword, PageRequest
                .of(pageNum-1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "regDate")));
        List<Recipe> recipes = page.getContent();
        List<Recipe> recipeList = new ArrayList<>();

        for(Recipe recipe : recipes)
        {
            recipeList.add(recipe);
        }
        return recipeList;
    }

    @Transactional
    public List<Profile> getProfileList(String keyword, Integer pageNum)
    {
        Page<Profile> page = profileRepository.findByNicknameContaining(keyword, PageRequest
                .of(pageNum-1, PAGE_POST_COUNT));
        List<Profile> profiles = page.getContent();
        List<Profile> profileList = new ArrayList<>();

        for(Profile profile : profiles)
        {
            profileList.add(profile);
        }
        return profileList;
    }

    @Transactional
    public List<Recipe> getTagList(String keyword, Integer pageNum)
    {
        Page<Recipe> page = recipeRepository.findByContentContaining("#"+keyword, PageRequest
                .of(pageNum-1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "regDate")));
        List<Recipe> recipes = page.getContent();
        List<Recipe> recipeList = new ArrayList<>();

        for(Recipe recipe : recipes)
        {
            recipeList.add(recipe);
        }
        return recipeList;
    }

    public Integer[] getRecipePageList(String keyword, Integer curPageNum)
    {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // 총 게시글 수
        Double postsTotalCount = Double.valueOf(this.getRecipeCount(keyword));

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

    public Integer[] getProfilePageList(String keyword, Integer curPageNum)
    {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        Double postsTotalCount = Double.valueOf(this.getProfileCount(keyword));

        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        curPageNum = (curPageNum<=3) ? 1 : curPageNum-2;

        for(int val=curPageNum, i=0; val<=blockLastPageNum; val++, i++) { pageList[i] = val; }

        return pageList;
    }

    public Integer[] getTagPageList(String keyword, Integer curPageNum)
    {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        Double postsTotalCount = Double.valueOf(this.getTagCount(keyword));

        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        curPageNum = (curPageNum<=3) ? 1 : curPageNum-2;

        for(int val=curPageNum, i=0; val<=blockLastPageNum; val++, i++) { pageList[i] = val; }

        return pageList;
    }

    @Transactional
    public int getRecipeCount(String keyword) { return this.searchRecipe(keyword).size(); }

    @Transactional
    public int getProfileCount(String keyword) { return this.searchProfile(keyword).size(); }

    @Transactional
    public int getTagCount(String keyword) { return this.searchTag(keyword).size(); }

    @Transactional
    public List<Recipe> searchRecipe(String keyword)
    {
        List<Recipe> recipes = recipeRepository.findByTitleContaining(keyword);
        return recipes;
    }

    @Transactional
    public List<Profile> searchProfile(String keyword)
    {
//        List<Profile> profiles = profileRepository.findByUserIdContaining(keyword);
        List<Profile> profiles = profileRepository.findByNicknameContaining(keyword);
        return profiles;
    }

    @Transactional
    public List<Recipe> searchTag(String keyword)
    {
        List<Tag> tags = tagRepository.findByContentContaining(keyword);
        List<Long> tagList = new ArrayList<>();
        tags.forEach(e->tagList.add(e.getTagNo()));
        Set<Long> recipeList = new HashSet<>();
        List<Long> recipeListtmp = new ArrayList<>();
        for(int i=0; i<tagList.size(); i++)
        {
            recipeListtmp = tagRepository.findRecipeNoByTagNo(tagList.get(i));
            recipeListtmp.forEach(a->recipeList.add(a));
        }

        List<Recipe> tagRecipeList = new ArrayList<>();
        recipeList.forEach(a->tagRecipeList.add(recipeRepository.findByRecipeNo(a)));
        return tagRecipeList;
    }

    @Transactional
    public List<AutoCompleteDTO> searchAuto(String keyword)
    {

        List<Recipe> recipes = recipeRepository.findByTitleContaining(keyword);
        if(recipes.size()>5) {
            recipes = recipes.subList(0,4);
        }
        List<AutoCompleteDTO> autoCompletes = new ArrayList<>();
        recipes.forEach(r->autoCompletes.add(r.getAutocomplete()));

        return autoCompletes;
    }

}
