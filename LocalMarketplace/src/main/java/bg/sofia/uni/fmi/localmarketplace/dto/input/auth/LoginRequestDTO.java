package bg.sofia.uni.fmi.localmarketplace.dto.input.auth;

import bg.sofia.uni.fmi.localmarketplace.utils.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Credentials for logging in.")
public record LoginRequestDTO(

    @NotBlank(message = ValidationConstants.User.BLANK_USERNAME)
    @Schema(description = "Username of the account.", requiredMode = Schema.RequiredMode.REQUIRED)
    String username,

    @NotBlank(message = ValidationConstants.User.BLANK_PASSWORD)
    @Schema(description = "Password of the account.", requiredMode = Schema.RequiredMode.REQUIRED)
    String password
) {
}
