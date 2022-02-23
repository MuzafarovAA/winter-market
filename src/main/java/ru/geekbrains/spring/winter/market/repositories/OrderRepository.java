package ru.geekbrains.spring.winter.market.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.spring.winter.market.entities.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
