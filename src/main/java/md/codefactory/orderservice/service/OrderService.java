package md.codefactory.orderservice.service;

import md.codefactory.orderservice.domain.Order;
import md.codefactory.orderservice.domain.enums.OrderStatus;
import md.codefactory.orderservice.exceptions.NotEnoughRightsException;
import md.codefactory.orderservice.exceptions.OrderNotFoundException;
import md.codefactory.orderservice.repository.OrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order saveOrder(Order order) throws NotEnoughRightsException {

        order.setCreatedAt(LocalDateTime.now());

        if (order.getOrderStatus().equals(OrderStatus.DRAFT) || order.getOrderStatus().equals(OrderStatus.AWAITING)) {
            return orderRepository.save(order);
        } else {
            throw new NotEnoughRightsException("Not enough rights!");
        }
    }

    public Order updateOrder(Long orderId, Order order) throws NotEnoughRightsException, OrderNotFoundException {

        Order updatedOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with id = " + orderId + " not found !"));

        if (order.getOrderStatus().equals(OrderStatus.DRAFT) || order.getOrderStatus().equals(OrderStatus.AWAITING)) {
            if (updatedOrder.getOrderStatus().equals(OrderStatus.AWAITING)) {
                throw new NotEnoughRightsException("Not enough rights!");
            } else {
                BeanUtils.copyProperties(order, updatedOrder, "id","userId","createdAt");
                return orderRepository.save(updatedOrder);
            }
        } else {
            throw new NotEnoughRightsException("Not enough rights!");
        }
    }

    public void deleteDraftOrder(Long orderId) throws OrderNotFoundException, NotEnoughRightsException {

        Order deleteOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with id = " + orderId + " not found !"));
        if (deleteOrder.getOrderStatus().equals(OrderStatus.DRAFT)) {
            orderRepository.delete(deleteOrder);
        } else {
            throw new NotEnoughRightsException("Not enough rights!");
        }
    }
}
