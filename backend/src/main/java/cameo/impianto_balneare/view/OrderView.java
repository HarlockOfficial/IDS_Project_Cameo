package cameo.impianto_balneare.view;

import cameo.impianto_balneare.entity.MenuOrder;
import cameo.impianto_balneare.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class OrderView implements GlobalExceptionHandler{
    private final OrderService orderService;

    @Autowired
    public OrderView(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/order/ordered", method = RequestMethod.GET)
    public ResponseEntity<List<MenuOrder>> getFutureOrders(@RequestHeader("token") String token){
        var futureOrders = orderService.getFutureOrders(token);
        if(futureOrders.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(futureOrders);
    }

    @RequestMapping(value = "/order/in_progress", method = RequestMethod.GET)
    public ResponseEntity<List<MenuOrder>> getInProgressOrders(@RequestHeader("token") String token){
        var inProgressOrders = orderService.getInProgrssOrders(token);
        if(inProgressOrders.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(inProgressOrders);
    }

    @RequestMapping(value = "/order/finished", method = RequestMethod.GET)
    public ResponseEntity<List<MenuOrder>> getFinishedOrders(@RequestHeader("token") String token){
        var finishedOrders = orderService.getCompletedOrders(token);
        if(finishedOrders.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(finishedOrders);
    }

    @RequestMapping(value = "/order/delivered", method = RequestMethod.GET)
    public ResponseEntity<List<MenuOrder>> getDeliveredOrders(@RequestHeader("token") String token){
        var deliveredOrders = orderService.getDeliveredOrders(token);
        if(deliveredOrders.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(deliveredOrders);
    }

    @RequestMapping(value = "/order/paid", method = RequestMethod.GET)
    public ResponseEntity<List<MenuOrder>> getPaidOrders(@RequestHeader("token") String token){
        var paidOrders = orderService.getPaidOrders(token);
        if(paidOrders.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(paidOrders);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public ResponseEntity<MenuOrder> getOrder(@PathVariable("id") UUID id, @RequestHeader("token") String token){
        var order = orderService.getUserOrder(id, token);
        if(order == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @RequestMapping(value = "/order/day/{dateTime}", method = RequestMethod.GET)
    public ResponseEntity<List<MenuOrder>> getOrdersByDateTime(@PathVariable String dateTime, @RequestHeader("token") String token){
        var date = ZonedDateTime.parse(dateTime);
        var orders = orderService.getAllOrderOfDay(date, token);
        if(orders.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }

    @RequestMapping(value = "/order/day/today", method = RequestMethod.GET)
    public ResponseEntity<List<MenuOrder>> getTodayOrders(@RequestHeader("token") String token){
        var orders = orderService.getAllTodayOrders(token);
        if(orders.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public ResponseEntity<MenuOrder> createOrder(@RequestBody MenuOrder menuOrder, @RequestHeader("token") String token){
        var createdOrder = orderService.createOrder(menuOrder, token);
        if(createdOrder == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(createdOrder);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MenuOrder> updateOrderStatus(@PathVariable UUID id, @RequestHeader("token") String token){
        var updatedOrder = orderService.updateOrderStatus(id, token);
        if(updatedOrder == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(updatedOrder);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<MenuOrder> deleteOrder(@PathVariable UUID id, @RequestHeader("token") String token){
        var deletedOrder = orderService.deleteOrder(id, token);
        if(deletedOrder == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(deletedOrder);
    }
}
