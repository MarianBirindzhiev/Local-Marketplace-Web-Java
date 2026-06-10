package bg.sofia.uni.fmi.localmarketplace.dto.output.event;

import bg.sofia.uni.fmi.localmarketplace.domain.Event;
import bg.sofia.uni.fmi.localmarketplace.vo.DiscountType;
import bg.sofia.uni.fmi.localmarketplace.vo.EventType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Details of a community event")
public record EventDetailsDTO(

    @Schema(description = "Event ID")
    Long id,

    @Schema(description = "Event title")
    String title,

    @Schema(description = "Event description")
    String description,

    @Schema(description = "Event type")
    EventType type,

    @Schema(description = "Username of the event owner")
    String ownerUsername,

    @Schema(description = "Start date")
    LocalDateTime startDate,

    @Schema(description = "End date")
    LocalDateTime endDate,

    @Schema(description = "Whether the event is currently active")
    boolean active,

    @Schema(description = "Story content (STORYTELLING_FEATURES only)")
    String content,

    @Schema(description = "Fair location (CRAFT_FAIRS only)")
    String location,

    @Schema(description = "Discount type (PROMOTIONAL_CAMPAIGNS only)")
    DiscountType discountType,

    @Schema(description = "Discount value (PROMOTIONAL_CAMPAIGNS only)")
    Long discountValue
) {

    public static EventDetailsDTO from(Event event) {
        String location = event.getFairDetails() != null ? event.getFairDetails().getLocation() : null;

        DiscountType discountType = null;
        Long discountValue = null;
        if (event.getPromotionDetails() != null) {
            discountType = event.getPromotionDetails().getDiscountType();
            discountValue = event.getPromotionDetails().getDiscountValue();
        }

        return new EventDetailsDTO(
            event.getId(),
            event.getTitle(),
            event.getDescription(),
            event.getType(),
            event.getUser().getUsername(),
            event.getStartDate(),
            event.getEndDate(),
            event.isActive(),
            event.getContent(),
            location,
            discountType,
            discountValue
        );
    }
}
