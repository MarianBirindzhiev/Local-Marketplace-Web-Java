package bg.sofia.uni.fmi.localmarketplace.domain;

import bg.sofia.uni.fmi.localmarketplace.vo.ProductType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="product_type", nullable = false)
    private ProductType productType;

    @Column(length = 100, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User maker;

    protected Product() {

    }

    public Product(ProductType productType, String description, double price, User maker, int quantity) {
        this.productType = productType;
        this.description = description;
        this.price = price;
        this.maker = maker;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getMaker() {
        return maker;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {}

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Product product)) return false;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
