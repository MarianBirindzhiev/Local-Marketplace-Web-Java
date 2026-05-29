//package bg.sofia.uni.fmi.localmarketplace.domain;
//
//import bg.sofia.uni.fmi.localmarketplace.domain.order.Order;
//import bg.sofia.uni.fmi.localmarketplace.vo.CurrencyType;
//import bg.sofia.uni.fmi.localmarketplace.vo.PaymentMethod;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//
//import java.util.Objects;
//
//@Entity
//@Table(name = "payments")
//public class Payment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id", nullable = false)
//    private Order order;
//
//    @Column(nullable = false)
//    private long amount;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private CurrencyType currency;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "payment_method", nullable = false)
//    private PaymentMethod paymentMethod;
//
//    protected Payment() {
//
//    }
//
//    public Payment(Order order, long amount, CurrencyType currency, PaymentMethod paymentMethod) {
//        this.order = order;
//        this.amount = amount;
//        this.currency = currency;
//        this.paymentMethod = paymentMethod;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public Order getOrder() {
//        return order;
//    }
//
//    public long getAmount() {
//        return amount;
//    }
//
//    public void setAmount(long amount) {
//        this.amount = amount;
//    }
//
//    public CurrencyType getCurrency() {
//        return currency;
//    }
//
//    public void setCurrency(CurrencyType currency) {
//        this.currency = currency;
//    }
//
//    public PaymentMethod getPaymentMethod() {
//        return paymentMethod;
//    }
//
//    public void setPaymentMethod(PaymentMethod paymentMethod) {
//        this.paymentMethod = paymentMethod;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        if (!(object instanceof Payment payment)) return false;
//        return Objects.equals(id, payment.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hashCode(id);
//    }
//}
