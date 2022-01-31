package ee.priit.pall.homemade.mapper.product;

import ee.priit.pall.homemade.dto.ProductDetailResponse;
import ee.priit.pall.homemade.dto.ProductListResponse;
import ee.priit.pall.homemade.dto.ProductUpdateRequest;
import ee.priit.pall.homemade.mapper.ProductMapper;
import ee.priit.pall.homemade.mapper.ProductMapperImpl;
import ee.priit.pall.homemade.model.Product;
import ee.priit.pall.homemade.model.QuantityType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ProductMapperTest {
    private ProductMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new ProductMapperImpl();
    }

    @Test
    void modelToProductListResponse_validInput_mapsAllFields() {
        Product model = Product.builder()
                        .id(10L)
                        .name("Potato")
                        .quantity(200)
                        .quantityType(QuantityType.KG)
                        .price(BigDecimal.valueOf(4.20))
                        .seller("Priidu Talu").build();

        ProductListResponse dto = mapper.modelToListResponse(model);

        Assertions.assertThat(dto)
                .isNotNull()
                .extracting("id", "name", "quantity", "quantityType", "price", "seller")
                .contains(10L, "Potato", 200, "KG", BigDecimal.valueOf(4.20), "Priidu Talu");
    }

    @Test
    void modelToDetailResponse_validInput_mapsAllFields() {
        Product product = Product.builder()
                .id(10L)
                .name("Potato")
                .quantity(200)
                .quantityType(QuantityType.KG)
                .price(BigDecimal.valueOf(4.20))
                .seller("Priidu Talu").build();

        ProductDetailResponse dto = mapper.modelToDetailResponse(product);

        Assertions.assertThat(dto)
                .isNotNull()
                .extracting("id", "name", "quantity", "quantityType", "price", "seller")
                .contains(10L, "Potato", 200, "KG", BigDecimal.valueOf(4.20), "Priidu Talu");
    }

    @Test
    void updateRequestToModel_validInput_mapsAllFields() {
        ProductUpdateRequest dto = new ProductUpdateRequest();
        dto.setId(1L);
        dto.setName("Ahven");
        dto.setQuantity(600);
        dto.setQuantityType(QuantityType.G.name());
        dto.setPrice(BigDecimal.valueOf(3.00));
        dto.setSeller("Tasuja talu");

        Product model = mapper.updateRequestToModel(dto);

        Assertions.assertThat(model)
                .isNotNull()
                .extracting("id", "name", "quantity", "quantityType", "price", "seller")
                .contains(1L, "Ahven", 600, QuantityType.G, BigDecimal.valueOf(3.00), "Tasuja talu");
    }
}
