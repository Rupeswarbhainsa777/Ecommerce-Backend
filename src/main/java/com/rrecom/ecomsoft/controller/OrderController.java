package com.rrecom.ecomsoft.controller;

import com.rrecom.ecomsoft.io.OrderRequest;
import com.rrecom.ecomsoft.io.OrderResponse;
import com.rrecom.ecomsoft.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {


    private  final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody OrderRequest request){
      return   orderService.createOrder(request);

    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{orderId}")
    public  void  deleteOrder(@PathVariable String orderId)
    {
        orderService.deleteOrder(orderId);

    }

   @GetMapping("/latest")
    public List<OrderResponse> getLatestOrders()
    {
             return orderService.getLatestOrder();
    }
}
