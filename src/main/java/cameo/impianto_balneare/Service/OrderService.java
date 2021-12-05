package cameo.impianto_balneare.Service;

import cameo.impianto_balneare.Entity.MenuOrder;
import cameo.impianto_balneare.Entity.OrderStatus;
import cameo.impianto_balneare.Entity.Role;
import cameo.impianto_balneare.Repository.MenuOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final MenuOrderRepository menuOrderRepository;
    private final TokenService tokenService;

    @Autowired
    public OrderService(MenuOrderRepository menuOrderRepository, TokenService tokenService) {
        this.menuOrderRepository = menuOrderRepository;
        this.tokenService = tokenService;
    }

    private boolean checkToken(String token) {
        return !tokenService.checkToken(token, Role.BAR) && !tokenService.checkToken(token, Role.ADMIN);
    }

    public List<MenuOrder> getFutureOrders(String token) {
        if(checkToken(token)) return null;
        return menuOrderRepository.findAll().stream()
                .filter(e -> e.getOrderStatus().equals(OrderStatus.ORDERED)).collect(Collectors.toList());
    }

    public List<MenuOrder> getInProgrssOrders(String token) {
        if(checkToken(token)) return null;
        return menuOrderRepository.findAll().stream()
                .filter(e -> e.getOrderStatus().equals(OrderStatus.IN_PROGRESS)).collect(Collectors.toList());
    }

    public List<MenuOrder> getCompletedOrders(String token) {
        if(checkToken(token)) return null;
        return menuOrderRepository.findAll().stream()
                .filter(e -> e.getOrderStatus().equals(OrderStatus.COMPLETED)).collect(Collectors.toList());
    }

    public List<MenuOrder> getDeliveredOrders(String token) {
        if(checkToken(token)) return null;
        return menuOrderRepository.findAll().stream()
                .filter(e -> e.getOrderStatus().equals(OrderStatus.DELIVERED)).collect(Collectors.toList());
    }

    public List<MenuOrder> getPaidOrders(String token) {
        if(checkToken(token)) return null;
        return menuOrderRepository.findAll().stream()
                .filter(e -> e.getOrderStatus().equals(OrderStatus.PAID)).collect(Collectors.toList());
    }

    public List<MenuOrder> getAllOrderOfDay(ZonedDateTime dateTime, String token) {
        if(checkToken(token)) return null;
        dateTime = dateTime.minusHours(dateTime.getHour());
        dateTime = dateTime.minusMinutes(dateTime.getMinute());
        dateTime = dateTime.minusSeconds(dateTime.getSecond());
        var origDateTime = dateTime;
        var nextDay = dateTime.plusDays(1);
        return menuOrderRepository.findAll().stream()
                .filter(e -> e.getOrderDateTime().isAfter(origDateTime) && e.getOrderDateTime().isBefore(nextDay)).collect(Collectors.toList());
    }

    public List<MenuOrder> getAllTodayOrders(String token) {
        if(checkToken(token)) return null;
        return getAllOrderOfDay(ZonedDateTime.now(), token);
    }

    public MenuOrder updateOrderStatus(UUID order_id, String token) {
        if(checkToken(token)) return null;
        var orderToUpdate = menuOrderRepository.findById(order_id);
        if (orderToUpdate.isPresent()) {
            var order =orderToUpdate.get();
            order.setOrderStatus(order.getOrderStatus().next());
            return menuOrderRepository.save(order);
        }
        return null;
    }

    public MenuOrder createOrder(MenuOrder menuOrder, String token) {
        if(!tokenService.checkToken(token, Role.USER)) return null;
        //TODO check that user is in the ombrellone
        return menuOrderRepository.save(menuOrder);
    }

    public MenuOrder getUserOrder(UUID id, String token) {
        if(!tokenService.checkToken(token, Role.USER)) return null;
        var orderToGet = menuOrderRepository.findById(id);
        if(orderToGet.isPresent()) {
            var order = orderToGet.get();
            if(order.getUser().getId().equals(tokenService.getUserFromUUID(token).getId())) {
                return order;
            }
        }
        return null;
    }

    public MenuOrder deleteOrder(UUID id, String token) {
        if(checkToken(token)) return null;
        var orderToDelete = menuOrderRepository.findById(id);
        if(orderToDelete.isPresent()) {
            var order = orderToDelete.get();
            menuOrderRepository.delete(order);
        }
        return null;
    }
}
