package bg.sofia.uni.fmi.localmarketplace.service;

import bg.sofia.uni.fmi.localmarketplace.domain.Payment;
import bg.sofia.uni.fmi.localmarketplace.dto.input.payment.CreatePaymentDTO;
import bg.sofia.uni.fmi.localmarketplace.dto.output.payment.PaymentDetailsDTO;
import bg.sofia.uni.fmi.localmarketplace.exception.payment.PaymentDoesNotExistException;
import bg.sofia.uni.fmi.localmarketplace.repository.PaymentRepository;
import bg.sofia.uni.fmi.localmarketplace.repository.UserRepository;
import bg.sofia.uni.fmi.localmarketplace.repository.order.OrderRepository;
import bg.sofia.uni.fmi.localmarketplace.service.contract.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository,
                              UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }


    @Override
    public PaymentDetailsDTO getPayment(Long id) {
        return null;
    }

    @Override
    public Page<PaymentDetailsDTO> getAllPaymentsOfAnOrder(Long orderId, Pageable pageable) {
        return null;
    }

    @Override
    public PaymentDetailsDTO createPayment(CreatePaymentDTO dto, String username) {
        return null;
    }

    @Override
    public void cancelPayment(Long id, String username) {

    }

    @Override
    public void refundPayment(Long id, String username) {

    }

    private Payment getPaymentById(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isEmpty()) {
            throw new PaymentDoesNotExistException("Payment with id " +  id + " does not exist");
        }
        return payment.get();
    }
}
