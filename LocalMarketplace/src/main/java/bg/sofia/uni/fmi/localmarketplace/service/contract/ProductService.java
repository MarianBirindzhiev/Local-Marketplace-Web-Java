package bg.sofia.uni.fmi.localmarketplace.service.contract;

import bg.sofia.uni.fmi.localmarketplace.dto.input.product.CreateProductDTO;
import bg.sofia.uni.fmi.localmarketplace.dto.input.product.UpdateProductDTO;
import bg.sofia.uni.fmi.localmarketplace.dto.output.product.ProductDetailsDTO;

import bg.sofia.uni.fmi.localmarketplace.exception.product.ProductDoesNotExistException;
import bg.sofia.uni.fmi.localmarketplace.exception.user.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    /**
     * Retrieves the product details for the given product
     *
     * @param id the id of the product
     * @return {@link ProductDetailsDTO}
     * @throws ProductDoesNotExistException if the product with given id does not exist
     */
    ProductDetailsDTO getProduct(Long id);

    /**
     * Retrieves a paginated and optionally sorted page of all products converted to DTOs.
     *
     * @param pageable pagination and sorting details for the products
     * @return a page of product responses
     */
    Page<ProductDetailsDTO> getAllProducts(Pageable pageable);

    /**
     * Creates a new artisan product and assigns it to the current logged-in user.
     *
     * @param dto      the data for the new product
     * @param username username of the authenticated creator (maker)
     * @return {@link ProductDetailsDTO}
     * @throws UserNotFoundException if the user does not exist
     */
    ProductDetailsDTO createProduct(CreateProductDTO dto, String username);

    /**
     * Updates the product's details.
     *
     * @param id       the ID of the product to update
     * @param username username of the user making the request
     * @param dto      the new product data
     * @return {@link ProductDetailsDTO}
     * @throws ProductDoesNotExistException if the product does not exist
     * @throws UserNotFoundException        if the user does not exist
     */
    ProductDetailsDTO updateProduct(Long id, String username, UpdateProductDTO dto);

    /**
     * Removes a product from the marketplace.
     *
     * @param id       the ID of the product to delete
     * @param username username of the user making the request
     * @throws ProductDoesNotExistException if the product does not exist
     * @throws UserNotFoundException        if the user does not exist
     */
    void deleteProduct(Long id, String username);
}
