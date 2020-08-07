package org.kitchen.booting.repository;

import org.kitchen.booting.domain.Recipe;
import org.kitchen.booting.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {


    public List<Tag> findAll();
    public Tag findByContent(String content);
    public void deleteByContent(String content);
    public void deleteByTagNo(Long tagNo);

    @Query(value="SELECT * FROM tbl_tag ORDER BY RAND() LIMIT 8", nativeQuery = true)
    public List<Tag> findTag();

    public List<Tag> findByContentContaining(String keyword);
    public Page<Tag> findByContentContaining(String keyword, Pageable pageable);

    @Query(value="select * from tbl_recipe_tag where tag_no = :tagNo", nativeQuery = true)
    public List<Long> findRecipeNoByTagNo(@Param("tagNo") Long tagNo);

    @Query(value="SELECT content FROM tbl_tag where content like %:keyword%", nativeQuery = true)
    public List<String> search(@Param("keyword") String keyword);
}

