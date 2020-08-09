package org.kitchen.booting.repository;

import org.kitchen.booting.domain.Like;
import org.kitchen.booting.domain.Profile;
import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.domain.id.LikeId;
import org.kitchen.booting.domain.userauth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, LikeId> {
    // 좋아요도 저장, 삭제
    // 그리고 유저아이디로 내가 좋아요 한것들 레시피넘버 나오게 하기
    // 레시피 넘버로 또 레시피테이블에서 레시피 제목 나오면 좋겠당
    // 그리고 게시물 아이디로 날 좋아요한 유저아이디들 리스트로

    public List<Like> findAllByUser(User user);
    public List<Like> findAllByUser(String userId);
    public List<Like> findAllByRecipe(Recipe recipe);

//    public List<Like> findAllByRecipe();
    public Like findByUserAndRecipe(User user, Recipe recipe);
    public void delete(Like like);
}