package ee.priit.pall.homemade.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductCreateRequest {
    @NotBlank
    private String name;
    @Positive
    private int quantity;

    private String quantityType;
    private BigDecimal price;
    private String seller;
}
