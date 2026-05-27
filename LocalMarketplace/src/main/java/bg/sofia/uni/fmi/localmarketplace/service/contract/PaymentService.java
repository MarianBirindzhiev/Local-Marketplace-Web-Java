package bg.sofia.uni.fmi.localmarketplace.service.contract;

import bg.sofia.uni.fmi.localmarketplace.dto.input.payment.CreatePaymentDTO;
import bg.sofia.uni.fmi.localmarketplace.dto.output.payment.PaymentDetailsDTO;

import bg.sofia.uni.fmi.localmarketplace.exception.user.OwnershipMismatchException;
import bg.sofia.uni.fmi.localmarketplace.exception.order.OrderDoesNotExistException;
import bg.sofia.uni.fmi.localmarketplace.exception.payment.InvalidPaymentException;
import bg.sofia.uni.fmi.localmarketplace.exception.payment.PaymentDoesNotExistException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {

    /**
     * Returns information about a payment
     *
     * @param id the id of the payment
     * @return {@link PaymentDetailsDTO}
     * @throws PaymentDoesNotExistException if the payment does not exist
     */
    PaymentDetailsDTO getPayment(Long id);

    /**
     * Retrieves a paginated and optionally sorted page of all payments made due a single order converted to DTOs.
     *
     * @param pageable pagination and sorting details for the payments
     * @return a page of payment responses
     * @throws OrderDoesNotExistException if the order does not exist
     */
    Page<PaymentDetailsDTO> getAllPaymentsOfAnOrder(Long orderId, Pageable pageable);

    /**
     * Creates a new payment
     *
     * @param dto      the data for the new payment
     * @param username the username of the currently logged user
     * @return {@link PaymentDetailsDTO}
     * @throws PaymentDoesNotExistException if the payment does not exist
     * @throws OwnershipMismatchException   if order maker is not the trying to make the payment
     */
    PaymentDetailsDTO createPayment(CreatePaymentDTO dto, String username);

    /**
     * Cancels a pending payment
     *
     * @param id       the id of the payment
     * @param username the username of the currently logged user
     * @throws PaymentDoesNotExistException if the payment does not exist
     * @throws OwnershipMismatchException   if a user different from the owner tries cancelling the payment
     * @throws InvalidPaymentException      if the payment is not in a cancellable state
     */
    void cancelPayment(Long id, String username);

    /**
     * Refunds a successful payment
     *
     * @param id       the id of the payment
     * @param username the username of the currently logged user
     * @throws PaymentDoesNotExistException if the payment does not exist
     * @throws OwnershipMismatchException   if a user different from the owner tries refunding the payment
     * @throws InvalidPaymentException      if the payment is not eligible for refund
     */
    void refundPayment(Long id, String username);
}
