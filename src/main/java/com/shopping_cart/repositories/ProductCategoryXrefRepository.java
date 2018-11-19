package com.shopping_cart.repositories;

import com.shopping_cart.domain.ProductCategoryXref;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryXrefRepository extends JpaRepository<ProductCategoryXref,Long> {
}
