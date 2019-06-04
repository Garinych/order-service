package md.codefactory.orderservice.mapping.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderDto {

    private Long menuId;
    private String orderStatus;
}
