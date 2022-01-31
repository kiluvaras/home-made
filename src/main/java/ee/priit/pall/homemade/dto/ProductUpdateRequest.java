package ee.priit.pall.homemade.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductUpdateRequest {
    private Long id;
    private String name;
    private int quantity;
    private String quantityType;
    private BigDecimal price;
    private String seller;
}
