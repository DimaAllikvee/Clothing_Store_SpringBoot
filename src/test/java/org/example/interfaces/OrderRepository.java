package org.example.interfaces;

import org.example.model.Customer;
import org.example.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomer(Customer customer);

    @Query("SELECT new map(o.id as orderId, o.description as description, o.totalPrice as totalPrice, " +
            "o.orderDate as orderDate, c.firstName as customerFirstName, c.lastName as customerLastName) " +
            "FROM Order o JOIN o.customer c WHERE c.id = :customerId")
    List<Map<String, Object>> findOrdersWithCustomerDetails(@Param("customerId") Long customerId);
}
