package bg.sofia.uni.fmi.localmarketplace.domain;

import bg.sofia.uni.fmi.localmarketplace.domain.order.Order;
import bg.sofia.uni.fmi.localmarketplace.vo.CurrencyType;

import java.util.Objects;

public class Payment {

    private final Long id;
    private final Order order;
    private double amount;
    private CurrencyType currency;

    public Payment(Order order, double amount, CurrencyType currency) {
        this.order = order;
        this.amount = amount;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Payment payment)) return false;
        return Objects.equals(id, payment.id) && Objects.equals(order, payment.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order);
    }
}
