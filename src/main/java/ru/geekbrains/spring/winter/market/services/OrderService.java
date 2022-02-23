package ru.geekbrains.spring.winter.market.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.winter.market.entities.Order;
import ru.geekbrains.spring.winter.market.entities.OrderItem;
import ru.geekbrains.spring.winter.market.entities.Product;
import ru.geekbrains.spring.winter.market.entities.User;
import ru.geekbrains.spring.winter.market.exceptions.ResourceNotFoundException;
import ru.geekbrains.spring.winter.market.model.Cart;
import ru.geekbrains.spring.winter.market.model.CartItem;
import ru.geekbrains.spring.winter.market.repositories.OrderItemRepository;
import ru.geekbrains.spring.winter.market.repositories.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;
    private final CartService cartService;

    public void createOrder(User user) {
        Cart cart = cartService.getCurrentCart();
        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(cart.getTotalPrice());
        orderRepository.save(order);
        List<OrderItem> orderItems = new ArrayList<>();
        List<CartItem> cartItems = cart.getItems();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setOrderId(order.getId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPricePerProduct(cartItem.getPricePerProduct());
            orderItem.setPrice(cartItem.getPrice());
            orderItems.add(orderItem);
            orderItemRepository.save(orderItem);
        }
        cart.clear();
    }
}
