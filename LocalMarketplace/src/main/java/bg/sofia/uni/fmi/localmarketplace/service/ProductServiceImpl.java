package bg.sofia.uni.fmi.localmarketplace.service;

import bg.sofia.uni.fmi.localmarketplace.domain.Product;
import bg.sofia.uni.fmi.localmarketplace.domain.User;
import bg.sofia.uni.fmi.localmarketplace.dto.input.product.CreateProductDTO;
import bg.sofia.uni.fmi.localmarketplace.dto.input.product.UpdateProductDTO;
import bg.sofia.uni.fmi.localmarketplace.dto.output.product.ProductDetailsDTO;
import bg.sofia.uni.fmi.localmarketplace.exception.product.ProductDoesNotBelongToUserExeption;
import bg.sofia.uni.fmi.localmarketplace.exception.product.ProductDoesNotExistException;
import bg.sofia.uni.fmi.localmarketplace.exception.user.UserNotFoundException;
import bg.sofia.uni.fmi.localmarketplace.repository.ProductRepository;
import bg.sofia.uni.fmi.localmarketplace.repository.UserRepository;
import bg.sofia.uni.fmi.localmarketplace.service.contract.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProductDetailsDTO getProduct(Long id) {
        return ProductDetailsDTO.from(getProductById(id));
    }

    @Override
    public Page<ProductDetailsDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(ProductDetailsDTO::from);
    }

    @Override
    public ProductDetailsDTO createProduct(CreateProductDTO dto, String username) {
        User user = getUser(username);
        Product product = new Product(dto.productType(), dto.description(), dto.price(), user, dto.quantity());

        productRepository.save(product);
        return ProductDetailsDTO.from(product);
    }

    @Override
    public ProductDetailsDTO updateProduct(Long id, String username, UpdateProductDTO dto) {
        User user = getUser(username);
        Product toUpdate = getProductById(id);

        checkIfTheMakerOfTheProductIsTheSame(toUpdate.getMaker(), user, id);

        toUpdate.setDescription(dto.description());
        toUpdate.setPrice(dto.price());
        toUpdate.setQuantity(dto.quantity());

        return ProductDetailsDTO.from(toUpdate);
    }

    @Override
    public void deleteProduct(Long id, String username) {
        Product toDelete = getProductById(id);
        User user = getUser(username);

        checkIfTheMakerOfTheProductIsTheSame(toDelete.getMaker(), user, id);

        productRepository.delete(toDelete);
    }

    private Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new ProductDoesNotExistException("Product with id " + id + " does not exist");
        }
    }

    private User getUser(String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("User with name " + username + " does not exist");
        }
    }

    private void checkIfTheMakerOfTheProductIsTheSame(User first,  User second, Long productId) {
        if (first != second) {
            throw new ProductDoesNotBelongToUserExeption("Product with id " + productId + " does not belong to user " +
                "with username " + second.getUsername());
        }
    }
}
