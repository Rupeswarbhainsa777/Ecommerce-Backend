package com.rrecom.ecomsoft.controller;

import com.rrecom.ecomsoft.io.DashBoardResponse;
import com.rrecom.ecomsoft.io.OrderResponse;
import com.rrecom.ecomsoft.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashBoardController {

    private  final OrderService orderService;

    @GetMapping
    public DashBoardResponse getDashboardData() {
        LocalDate today = LocalDate.now();
        Double todaySales = orderService.sumSalesByDate(today);
        Long todayOrderCount = orderService.countByOrderDate(today);
        List<OrderResponse> recentOrder = orderService.findRecentOrders();

        return new DashBoardResponse(
                todaySales != null ? todaySales : 0.0,
                todayOrderCount != null ? todayOrderCount : 0,
                recentOrder
        );
    }
}
