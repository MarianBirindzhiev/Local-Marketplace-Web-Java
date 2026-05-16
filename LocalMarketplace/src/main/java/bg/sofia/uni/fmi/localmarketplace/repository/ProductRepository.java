package bg.sofia.uni.fmi.localmarketplace.repository;

import bg.sofia.uni.fmi.localmarketplace.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

//    Product save(Product product);
//    Optional<Product> findById(Long id);
//    List<Product> findAll();
//    void deleteById(Long id);
//    boolean existsById(Long id);
//
//    List<Product> findByType(ProductType type);
//    List<Product> findByLocation(String location);
//    List<Product> findByPriceBetween(double min, double max);
//    List<Product> findByVendorId(Long vendorId);
//    List<Product> findBySearchText(String text);
}
