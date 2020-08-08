package org.kitchen.booting.repository;

import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.domain.Scrap;
import org.kitchen.booting.domain.id.ScrapId;
import org.kitchen.booting.domain.userauth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, ScrapId> {

    // 스크랩테이블에서 유저꺼만 찾아서 리스트로 보여줌
    public List<Scrap> findAllByUser(User user);
    public Scrap findByUserAndRecipe(User user, Recipe recipe);
//    public Scrap findByre
    public void delete(Scrap scrap);
    public Scrap save(Scrap scrap);


}