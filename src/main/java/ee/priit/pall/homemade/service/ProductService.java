package ee.priit.pall.homemade.service;

import ee.priit.pall.homemade.dto.*;

import java.util.List;

public interface ProductService {
    List<ProductListResponse> getProducts();
    ProductDetailResponse getProduct(Long id);
    ProductCreateResponse createProduct(ProductCreateRequest request);
    ProductUpdateResponse updateProduct(Long id, ProductUpdateRequest request);
}
