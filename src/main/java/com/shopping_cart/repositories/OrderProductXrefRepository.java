package com.shopping_cart.repositories;

import com.shopping_cart.domain.OrderProductXref;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductXrefRepository extends JpaRepository<OrderProductXref,Long> {
}
