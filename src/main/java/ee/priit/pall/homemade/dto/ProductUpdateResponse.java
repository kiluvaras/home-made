package ee.priit.pall.homemade.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductUpdateResponse {
    private Long id;
    private String name;
    private int quantity;
    private String quantityType;
    private BigDecimal price;
    private String seller;
}
