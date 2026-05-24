package bg.sofia.uni.fmi.localmarketplace.dto.output.review;

import bg.sofia.uni.fmi.localmarketplace.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response body containing detailed information about a review of a product.")
public record ReviewDetailsDTO(
    @Schema(description = "Id of the review", requiredMode = Schema.RequiredMode.REQUIRED)
    Long id,

    @Schema(description = "The text of the review", requiredMode = Schema.RequiredMode.REQUIRED)
    String text,

    @Schema(description = "The rating of the product", requiredMode = Schema.RequiredMode.REQUIRED)
    int rating,

    @Schema(description = "The writer of the review", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    OutputReviewWriterDTO reviewWriterDTO
) {

    public static ReviewDetailsDTO from(Review review) {
        return new ReviewDetailsDTO(review.getId(), review.getText(), review.getRating(),
            new OutputReviewWriterDTO(review.getUser().getUsername()));
    }
}
