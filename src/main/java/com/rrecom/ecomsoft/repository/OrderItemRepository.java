package com.rrecom.ecomsoft.repository;

import com.rrecom.ecomsoft.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity , Long> {


}
