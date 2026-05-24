package bg.sofia.uni.fmi.localmarketplace.repository;

import bg.sofia.uni.fmi.localmarketplace.domain.Product;
import bg.sofia.uni.fmi.localmarketplace.vo.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByProductTypeAndMaker_Username(ProductType productType, String makerUsername, Pageable pageable);

    Page<Product> findByProductType(ProductType productType, Pageable pageable);

    Page<Product> findByMakerUsername(String makerUsername, Pageable pageable);

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
