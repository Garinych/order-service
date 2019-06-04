package md.codefactory.orderservice.mapping.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewOrderDto {

    private Long userId;
    private Long menuId;
    private String orderStatus;
}
