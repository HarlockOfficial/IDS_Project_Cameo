package cameo.impianto_balneare.service;

import cameo.impianto_balneare.entity.MenuOrder;
import cameo.impianto_balneare.entity.OrderStatus;
import cameo.impianto_balneare.entity.Role;
import cameo.impianto_balneare.repository.MenuOrderRepository;
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
                .filter(e -> e.getDateTime().isAfter(origDateTime) && e.getDateTime().isBefore(nextDay)).collect(Collectors.toList());
    }

    public List<MenuOrder> getAllTodayOrders(String token) {
        if(checkToken(token)) return null;
        return getAllOrderOfDay(ZonedDateTime.now(), token);
    }

    public MenuOrder updateOrderStatus(UUID order_id, String token) {
        if(checkToken(token)) return null;
        var orderToUpdate = menuOrderRepository.findAll().stream().filter(e -> e.getId().equals(order_id)).findFirst();
        if (orderToUpdate.isPresent()) {
            var order =orderToUpdate.get();
            order.setOrderStatus(order.getOrderStatus().next());
            return menuOrderRepository.save(order);
        }
        return null;
    }

    public MenuOrder createOrder(MenuOrder menuOrder, String token) {
        if(!tokenService.checkToken(token, Role.USER)) return null;
        menuOrder.setUser(tokenService.getUserFromUUID(token));
        return menuOrderRepository.save(menuOrder);
    }

    public MenuOrder getUserOrder(UUID id, String token) {
        if(!tokenService.checkToken(token, Role.USER)) return null;
        var orderToGet = menuOrderRepository.findAll().stream().filter(e->e.getId().equals(id)).findFirst();
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
        var orderToDelete = menuOrderRepository.findAll().stream().filter(e-> e.getId().equals(id)).findFirst();
        if(orderToDelete.isPresent()) {
            var order = orderToDelete.get();
            menuOrderRepository.delete(order);
            return order;
        }
        return null;
    }
}
