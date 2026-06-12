package bg.sofia.uni.fmi.localmarketplace.dto.output.product;

import bg.sofia.uni.fmi.localmarketplace.domain.Product;
import bg.sofia.uni.fmi.localmarketplace.vo.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response body containing detailed information about a product.")
public record ProductDetailsDTO(
    @Schema(description = "Unique identifier of the product", requiredMode = Schema.RequiredMode.REQUIRED)
    Long id,

    @Schema(description = "Type of the product", requiredMode = Schema.RequiredMode.REQUIRED)
    ProductType productType,

    @Schema(description = "Description of the product", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 100)
    String description,

    @Schema(description = "Price of the product", requiredMode = Schema.RequiredMode.REQUIRED)
    long price,

    @Schema(description = "Quantity of the product", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    int quantity,

    @Schema(description = "Maker of the product", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    OutputProductMakerDTO maker
) {

    public static ProductDetailsDTO from(Product product) {

        return new ProductDetailsDTO(product.getId(), product.getProductType(), product.getDescription(),
            product.getPrice(), product.getQuantity(),
            new OutputProductMakerDTO(product.getMaker().getUsername(), product.getMaker().getEmail()));
    }
}
