package bg.sofia.uni.fmi.localmarketplace.domain.order;

import bg.sofia.uni.fmi.localmarketplace.domain.Product;

import java.util.Objects;

public class OrderItem {

    private final Long id;
    private final Order order;
    private final Product product;
    private int quantity;
    private double price;

    public OrderItem(Order order, Product product, int quantity, double price) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof OrderItem orderItem)) return false;
        return Objects.equals(id, orderItem.id) && Objects.equals(order, orderItem.order) &&
            Objects.equals(product, orderItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, product);
    }
}
