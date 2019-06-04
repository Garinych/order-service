package md.codefactory.orderservice.controller;

import md.codefactory.orderservice.domain.Order;
import md.codefactory.orderservice.exceptions.NotEnoughRightsException;
import md.codefactory.orderservice.exceptions.OrderNotFoundException;
import md.codefactory.orderservice.mapping.OrderMapper;
import md.codefactory.orderservice.mapping.dto.NewOrderDto;
import md.codefactory.orderservice.mapping.dto.UpdateOrderDto;
import md.codefactory.orderservice.mapping.dto.ViewOrderDto;
import md.codefactory.orderservice.repository.OrderRepository;
import md.codefactory.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/orders")
public class UserOrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewOrderDto saveOrder(@RequestBody NewOrderDto newOrderDto) throws NotEnoughRightsException {

        Order order = orderMapper.newOrderDtoToOrder(newOrderDto);
        orderService.saveOrder(order);

        return orderMapper.orderToNewOrderDto(order);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.FOUND)
    public List<ViewOrderDto> findAllUserOrders(@PathVariable Long userId) {

        List<Order> orders = orderRepository.findAllByUserId(userId);

        return orders.stream().map(orderMapper::orderToViewOrderDto).collect(Collectors.toList());
    }

    @PutMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public UpdateOrderDto updateOrder(@PathVariable Long orderId, @RequestBody UpdateOrderDto updateOrderDto) throws OrderNotFoundException, NotEnoughRightsException {

        Order updatedOrder = orderMapper.updateOrderDtoToOrder(updateOrderDto);
                orderService.updateOrder(orderId, updatedOrder);

        return orderMapper.orderToUpdateOrderDto(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteDraftOrder(@PathVariable Long orderId) throws OrderNotFoundException, NotEnoughRightsException {

        orderService.deleteDraftOrder(orderId);

        return new ResponseEntity<>("Order with id = " + orderId + " was deleted successful !", HttpStatus.OK);
    }


}
