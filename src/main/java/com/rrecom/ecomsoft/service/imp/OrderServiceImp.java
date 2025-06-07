package com.rrecom.ecomsoft.service.imp;

import com.rrecom.ecomsoft.entity.OrderEntity;
import com.rrecom.ecomsoft.entity.OrderItemEntity;
import com.rrecom.ecomsoft.io.OrderRequest;
import com.rrecom.ecomsoft.io.OrderResponse;
import com.rrecom.ecomsoft.io.PaymentDetails;
import com.rrecom.ecomsoft.io.PaymentMethod;
import com.rrecom.ecomsoft.repository.OrderEntityRepository;
import com.rrecom.ecomsoft.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

    private final OrderEntityRepository orderEntityRepository;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
             OrderEntity newOrder =   convertToOrderEntity(request);

        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setStatus(newOrder.getPaymentMethod()==PaymentMethod.CASH ?
                PaymentDetails.PaymentStatus.COMPLETED: PaymentDetails.PaymentStatus.PENDING
            );

     List<OrderItemEntity> orderItems=   request.getCartItems().stream()
                .map(this::convertToOrderItemEntity)
                .collect(Collectors.toList());
                newOrder.setItems(orderItems);

     newOrder     = orderEntityRepository.save(newOrder);

     return  convertToResponse(newOrder);




    }

    private OrderItemEntity convertToOrderItemEntity(OrderRequest.OrderItemRequest orderItemRequest) {


    return   OrderItemEntity.builder()
              .itemId(orderItemRequest.getItemId())
              .name(orderItemRequest.getName())
              .price(orderItemRequest.getPrice())
              .quantity(orderItemRequest.getQuantity())
              .build();


    }

    private OrderResponse convertToResponse(OrderEntity newOrder) {
     return    OrderResponse.builder()
                .orderId(newOrder.getOrderId())
                .customerName(newOrder.getCustomerName())
                .phoneNumber(newOrder.getPhoneNumber())
                .subtotal(newOrder.getSubtotal())
                .tax(newOrder.getTax())
                .grandTotal(newOrder.getGrandTotal())
                .paymentMethod(newOrder.getPaymentMethod())
                .items(newOrder.getItems().stream()
                        .map(this::convertToItemResponse)
                        .collect(Collectors.toList()))
                .paymentDetails(newOrder.getPaymentDetails())
                .createAt(newOrder.getCreatedAt())
                .build();

    }

    private OrderResponse.OrderItemResponse convertToItemResponse(OrderItemEntity orderItemEntity) {


     return    OrderResponse.OrderItemResponse.builder()
                .itemId(orderItemEntity.getItemId())
                .name(orderItemEntity.getName())
                .price(orderItemEntity.getPrice())
                .quantity(orderItemEntity.getQuantity())
                .build();
    }

    private OrderEntity convertToOrderEntity(OrderRequest request) {

     return    OrderEntity.builder()
                .customerName(request.getCustomerName())
                .phoneNumber(request.getPhoneNumber())
                .subtotal(request.getSubtotal())
                .tax(request.getTax())
                .grandTotal(request.getGrandTotal())
                .paymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()))
                .build();

    }

    @Override
    public void deleteOrder(String orderId) {

     OrderEntity entityOrder   = orderEntityRepository.findByOrderId(orderId)
                .orElseThrow(()-> new RuntimeException("Order Not found"));
        orderEntityRepository.delete(entityOrder);

    }

    @Override
    public List<OrderResponse> getLatestOrder() {
   return     orderEntityRepository.findAllByOrderByCreatedAtDesc()
               .stream()
               .map(this::convertToResponse)
               .collect(Collectors.toList());


    }

    @Override
    public Double sumSalesByDate(LocalDate date) {
     return   orderEntityRepository.sumSalesByDate(date);
    }

    @Override
    public Long countByOrderDate(LocalDate date) {
       return orderEntityRepository.countByOrderDate(date);
    }

    @Override
    public List<OrderResponse> findRecentOrders() {
      return   orderEntityRepository.findRecentOrders(PageRequest.of(0,5))
              .stream()
              .map(orderEntity -> convertToResponse(orderEntity))
              .collect(Collectors.toList());
    }
}
