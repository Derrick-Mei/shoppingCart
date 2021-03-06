package com.lambdaschool.coffeebean.repository;

import com.lambdaschool.coffeebean.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>
{
    @Query(value = "SELECT * FROM orders WHERE shipped_status = 0", nativeQuery = true)
    List<Order> findUnshippedOrders();

    @Query(value = "SELECT * FROM orders WHERE shipped_status = 1", nativeQuery = true)
    List<Order> findShippedOrders();

//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE orders SET ship_date_time = NULL WHERE (orderid = :orderid)", nativeQuery = true)
//    void setShipDateToNull(long orderid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (:orderId, :productId, :quantity);", nativeQuery = true)
    void addToOrderItem(long orderId, long productId, int quantity);

    @Query(value = "SELECT * FROM orders WHERE user_id = :userId AND order_id = :orderId", nativeQuery = true)
    Order findOrderByUserIdAndOrderId(long userId, long orderId);

    @Query(value = "SELECT * FROM orders WHERE user_id = :userId", nativeQuery = true)
    List<Order> findAllUserOrdersByUserId(long userId);

//    @Query(value =
//            "SELECT op.orderid, p.productname, p.description, p.image, p.price, op.quantityinorder, p.productid, o.orderdatetime " +
//            "FROM orderproducts op INNER JOIN products p ON op.productid=p.productid INNER JOIN orders o ON o.orderid=op.orderid " +
//                    "WHERE op.orderid=:orderid", nativeQuery = true)
//    List<OrderItem> getOrderItemsByOrderid(long orderid);
//
//    @Query(value =
//            "SELECT op.orderid, p.productname, p.description, p.image, p.price, op.quantityinorder, p.productid, o.orderdatetime " +
//                    "FROM orderproducts op " +
//                    "INNER JOIN products p ON op.productid=p.productid " +
//                    "INNER JOIN orders o ON o.orderid=op.orderid " +
//                    "WHERE o.userid=:userid", nativeQuery = true)
//    List<OrderItem> getOrderItemsByUserid(long userid);
}
