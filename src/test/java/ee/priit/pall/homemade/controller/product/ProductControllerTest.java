package ee.priit.pall.homemade.controller.product;

import static org.assertj.core.api.Assertions.assertThat;

import ee.priit.pall.homemade.dto.ProductListResponse;
import ee.priit.pall.homemade.exception.ErrorCode;
import ee.priit.pall.homemade.exception.RestError;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

  @LocalServerPort
  private int port;
  private String url;

  @Autowired
  private TestRestTemplate restTemplate;

  @BeforeEach
  void setup() {
    this.url = "http://localhost:" + port + "/products";
  }

  @Test
  @Sql(value = "/db/test/integration/product/add_product_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(value = "/db/test/integration/product/cleanup_test_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  void getProducts_validInput_returnsDataWithCorrectHttpStatus() {
    ParameterizedTypeReference<List<ProductListResponse>> type = new ParameterizedTypeReference<>() {
    };
    ResponseEntity<List<ProductListResponse>> response = restTemplate.exchange(
        url,
        HttpMethod.GET,
        null,
        type
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody())
        .isNotNull()
        .isNotEmpty()
        .hasSize(3);
  }

  @Test
  @Sql(value = "/db/test/integration/product/add_product_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(value = "/db/test/integration/product/cleanup_test_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  void getProduct_resourceNotFound_returnsResponse() {
    ResponseEntity<RestError> response = restTemplate.getForEntity(url + "/4", RestError.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(Objects.requireNonNull(response.getBody()).getCode()).isNotNull()
        .isEqualTo(ErrorCode.ENTITY_NOT_FOUND);
  }
}
