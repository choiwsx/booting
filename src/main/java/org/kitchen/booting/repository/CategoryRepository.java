package org.kitchen.booting.repository;

import org.kitchen.booting.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public List<Category> findByMainCategory(Category mainCategory);


}
