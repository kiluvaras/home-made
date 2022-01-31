package ee.priit.pall.homemade.service;

import ee.priit.pall.homemade.dto.*;
import ee.priit.pall.homemade.mapper.ProductMapper;
import ee.priit.pall.homemade.model.Product;
import ee.priit.pall.homemade.repostiory.ProductRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductMapper mapper;
    private final ProductRepository repository;

    public ProductServiceImpl(ProductMapper mapper, ProductRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public List<ProductListResponse> getProducts() {
        List<Product> products = repository.findAll();
        return products.stream().map(mapper::modelToListResponse).toList();
    }

    @Override
    public ProductDetailResponse getProduct(Long id) {
        return mapper.modelToDetailResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id)));
    }

    @Override
    public ProductCreateResponse createProduct(ProductCreateRequest request) {
        Product product = mapper.createRequestToModel(request);
        Product savedProduct = repository.save(product);
        return mapper.modelToCreateResponse(savedProduct);
    }

    @Override
    public ProductUpdateResponse updateProduct(Long id, ProductUpdateRequest request) {
        Product product = mapper.updateRequestToModel(request);
        product.setId(id);
        product = repository.save(product);
        return mapper.modelToUpdateResponse(product);
    }
}
