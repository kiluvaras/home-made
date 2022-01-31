package ee.priit.pall.homemade.service.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import ee.priit.pall.homemade.dto.ProductCreateRequest;
import ee.priit.pall.homemade.dto.ProductCreateResponse;
import ee.priit.pall.homemade.dto.ProductDetailResponse;
import ee.priit.pall.homemade.dto.ProductListResponse;
import ee.priit.pall.homemade.dto.ProductUpdateRequest;
import ee.priit.pall.homemade.dto.ProductUpdateResponse;
import ee.priit.pall.homemade.mapper.ProductMapper;
import ee.priit.pall.homemade.mapper.ProductMapperImpl;
import ee.priit.pall.homemade.model.Product;
import ee.priit.pall.homemade.model.QuantityType;
import ee.priit.pall.homemade.repostiory.ProductRepository;
import ee.priit.pall.homemade.service.ProductService;
import ee.priit.pall.homemade.service.ProductServiceImpl;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

  @MockBean
  ProductRepository repository;
  ProductMapper mapper;
  ProductService service;


  @BeforeEach
  void setup() {
    mapper = new ProductMapperImpl();
    this.service = new ProductServiceImpl(mapper, repository);
  }

  @Test
  void getProducts_noProducts_returnsEmptyList() {
    when(repository.findAll()).thenReturn(Collections.emptyList());

    List<ProductListResponse> products = service.getProducts();

    assertThat(products)
      .isNotNull()
      .isEmpty();
  }

  @Test
  void getProducts_productsExist_returnsAllProducts() {
    when(repository.findAll()).thenReturn(Arrays.asList(new Product(), new Product()));

    List<ProductListResponse> products = service.getProducts();

    assertThat(products)
      .isNotNull()
      .isNotEmpty()
      .hasSize(2);
  }

  @Test
  void getProduct_notFound_throwsException() {
    when(repository.findById(anyLong())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.getProduct(10L))
      .isExactlyInstanceOf(EntityNotFoundException.class)
      .hasMessage("Product not found with id: 10");
  }

  @Test
  void getProduct_exists_returnsMappedResponse() {
    Product product = Product.builder()
      .id(10L)
      .name("Potato")
      .quantity(200)
      .quantityType(QuantityType.KG)
      .price(BigDecimal.valueOf(4.20))
      .seller("Priidu Talu").build();
    when(repository.findById(anyLong())).thenReturn(Optional.of(product));

    ProductDetailResponse response = service.getProduct(10L);

    assertThat(response)
      .isNotNull()
      .extracting("id", "name", "quantity", "quantityType", "price", "seller")
      .contains(10L, "Potato", 200, "KG", BigDecimal.valueOf(4.20), "Priidu Talu");
  }

  @Test
  void createProduct_validInput_returnsSavedProduct() {
    ProductCreateRequest request = new ProductCreateRequest();
    request.setName("Potato");
    request.setQuantity(200);
    request.setQuantityType(QuantityType.KG.name());
    request.setPrice(BigDecimal.valueOf(4.20));
    request.setSeller("Priidu Talu");
    Product product = Product.builder()
      .id(10L)
      .name("Potato")
      .quantity(200)
      .quantityType(QuantityType.KG)
      .price(BigDecimal.valueOf(4.20))
      .seller("Priidu Talu").build();
    when(repository.save(any(Product.class))).thenReturn(product);

    ProductCreateResponse response = service.createProduct(request);

    assertThat(response).isNotNull();
    assertThat(response.getId()).isNotNull();
  }

  @Test
  void updateProduct_validInput_returnsSavedProduct() {
    ProductUpdateRequest request = new ProductUpdateRequest();
    request.setId(10L);
    request.setName("Potato");
    request.setQuantity(200);
    request.setQuantityType(QuantityType.KG.name());
    request.setPrice(BigDecimal.valueOf(4.20));
    request.setSeller("Priidu Talu");
    Product product = Product.builder()
      .id(10L)
      .name("Potato")
      .quantity(200)
      .quantityType(QuantityType.KG)
      .price(BigDecimal.valueOf(4.20))
      .seller("Priidu Talu").build();
    when(repository.save(any(Product.class))).thenReturn(product);

    ProductUpdateResponse response = service.updateProduct(10L, request);

    assertThat(response)
      .isNotNull()
      .extracting("id", "name", "quantity", "quantityType", "price", "seller")
      .contains(10L, "Potato", 200, "KG", BigDecimal.valueOf(4.20), "Priidu Talu");

  }
}
