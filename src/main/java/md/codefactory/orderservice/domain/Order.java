package md.codefactory.orderservice.domain;

import lombok.Data;
import md.codefactory.orderservice.domain.enums.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "orders_id_seq", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "orders_id_seq")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}
