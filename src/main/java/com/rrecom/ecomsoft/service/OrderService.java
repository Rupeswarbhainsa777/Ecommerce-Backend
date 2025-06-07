package com.rrecom.ecomsoft.service;

import com.rrecom.ecomsoft.io.OrderRequest;
import com.rrecom.ecomsoft.io.OrderResponse;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {

       OrderResponse createOrder(OrderRequest request);


       void deleteOrder(String orderId);

        List<OrderResponse> getLatestOrder();

       Double sumSalesByDate(LocalDate date);

      Long countByOrderDate(LocalDate date);

     List<OrderResponse> findRecentOrders();

}
