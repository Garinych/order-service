package md.codefactory.orderservice.mapping;

import md.codefactory.orderservice.domain.Order;
import md.codefactory.orderservice.mapping.dto.NewOrderDto;
import md.codefactory.orderservice.mapping.dto.UpdateOrderDto;
import md.codefactory.orderservice.mapping.dto.ViewOrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order updateOrderDtoToOrder(UpdateOrderDto updateOrderDto);

    UpdateOrderDto orderToUpdateOrderDto(Order order);

    Order newOrderDtoToOrder(NewOrderDto newOrderDto);

    NewOrderDto orderToNewOrderDto(Order order);

    Order viewOrderDtoToOrder(ViewOrderDto viewOrderDto);

    ViewOrderDto orderToViewOrderDto(Order order);

}
