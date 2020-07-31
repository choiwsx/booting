package org.kitchen.booting.repository;

import org.kitchen.booting.domain.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, String> {

    // 스크랩테이블에서 유저꺼만 찾아서 리스트로 보여줌
    public List<Scrap> findAllByUserId(String userId);
    public Scrap findByUserIdAndRecipeNo(String userId, Long recipeNo);
    public void delete(Scrap scrap);


}