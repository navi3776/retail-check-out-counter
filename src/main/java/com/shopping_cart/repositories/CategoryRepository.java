package com.shopping_cart.repositories;

import com.shopping_cart.domain.TaxCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<TaxCategory,Long> {
}
