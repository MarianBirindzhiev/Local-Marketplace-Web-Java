package bg.sofia.uni.fmi.localmarketplace.domain.order;

import bg.sofia.uni.fmi.localmarketplace.domain.User;
import bg.sofia.uni.fmi.localmarketplace.vo.CurrencyType;
import bg.sofia.uni.fmi.localmarketplace.vo.OrderStatus;

import java.util.Objects;

public class Order {

    private final Long id;
    private final User user;
    private OrderStatus status;
    private int totalAmount;
    private CurrencyType currency;

    public Order(User user, OrderStatus status, int totalAmount, CurrencyType currency) {
        this.user = user;
        this.status = status;
        this.totalAmount = totalAmount;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Order order)) return false;
        return Objects.equals(id, order.id) && Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user);
    }
}
