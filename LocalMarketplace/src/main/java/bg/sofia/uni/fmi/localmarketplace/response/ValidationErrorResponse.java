package bg.sofia.uni.fmi.localmarketplace.response;

import java.util.Map;

public record ValidationErrorResponse(Map<String, String> errors) {
}