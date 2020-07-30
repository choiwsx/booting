package org.kitchen.booting.repository;

import org.kitchen.booting.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, String> {
    // 좋아요도 저장, 삭제
    // 그리고 유저아이디로 내가 좋아요 한것들 레시피넘버 나오게 하기
    // 레시피 넘버로 또 레시피테이블에서 레시피 제목 나오면 좋겠당
    // 그리고 게시물 아이디로 날 좋아요한 유저아이디들 리스트로

        
}
