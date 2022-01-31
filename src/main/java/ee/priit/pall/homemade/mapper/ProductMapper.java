package ee.priit.pall.homemade.mapper;

import ee.priit.pall.homemade.dto.*;
import ee.priit.pall.homemade.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductListResponse modelToListResponse(Product product);
    ProductDetailResponse modelToDetailResponse(Product product);
    Product createRequestToModel(ProductCreateRequest request);
    ProductCreateResponse modelToCreateResponse(Product product);
    Product updateRequestToModel(ProductUpdateRequest request);
    ProductUpdateResponse modelToUpdateResponse(Product product);
}
