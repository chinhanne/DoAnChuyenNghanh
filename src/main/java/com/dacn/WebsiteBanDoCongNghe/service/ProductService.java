package com.dacn.WebsiteBanDoCongNghe.service;

import com.dacn.WebsiteBanDoCongNghe.dto.request.ProductRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.ProductUpdatedRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.ProductUpdatedStatusAndQuantityRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.SearchRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.ProductResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.Brand;
import com.dacn.WebsiteBanDoCongNghe.entity.Category;
import com.dacn.WebsiteBanDoCongNghe.entity.Image;
import com.dacn.WebsiteBanDoCongNghe.entity.Product;
import com.dacn.WebsiteBanDoCongNghe.exception.AppException;
import com.dacn.WebsiteBanDoCongNghe.exception.ErrorCode;
import com.dacn.WebsiteBanDoCongNghe.mapper.ProductMapper;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.BrandRepository;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.CategoryReponsitory;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.ProductReponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ProductService {
    ProductReponsitory productReponsitory;
    FileStorageService fileStorageService;
    ProductMapper productMapper;
    CategoryReponsitory categoryReponsitory;
    BrandRepository brandRepository;

//    Created Product
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse createdProduct(ProductRequest request) throws IOException {
        var category = categoryReponsitory.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

        var brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));

        Product product = productMapper.toProduct(request);
        product.setCategory(category);
        product.setBrand(brand);

        List<Image> images = new ArrayList<>();
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            for (MultipartFile file : request.getImages()) {
                if (file != null && !file.isEmpty()) {
                    String imagePath = fileStorageService.saveFile(file);
                    Image image = new Image();
                    image.setImagePath(imagePath);
                    image.setProduct(product);
                    images.add(image);
                }
            }
        }
        product.setImages(images);

        productReponsitory.save(product);

        return productMapper.toProductResponse(product);
    }

//     Get All Product
    public List<ProductResponse> getAllProduct(){
        return productReponsitory.findAll().stream().map(productMapper::toProductResponse).toList();
    }

//    Get Product by Id
    public ProductResponse getProductById(Long id){
        return productMapper.toProductResponse(productReponsitory.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse updateQuantityAndStatus(Long id, ProductUpdatedStatusAndQuantityRequest request){
        Product product = productReponsitory.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        productMapper.toUpdateQuantityAndStatus(product,request);
        return productMapper.toProductResponse(productReponsitory.save(product));
    }

//    Updated Product
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse updateProduct(Long id, ProductUpdatedRequest request) throws IOException {
        Product product = productReponsitory.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        productMapper.toUpdateProduct(product, request);

        List<Image> images = new ArrayList<>();

        if(request.getImages() != null && !request.getImages().isEmpty()){
            for(MultipartFile file : request.getImages()){
                if (file != null && !file.isEmpty()) {
                    String imageUrl = fileStorageService.saveFile(file);
                    Image image = new Image();
                    image.setImagePath(imageUrl);
                    image.setProduct(product);
                    images.add(image);
                }
            }
            product.setImages(images);
        }

        if(request.getCategoryId() != null){
            Category category = categoryReponsitory.findById(request.getCategoryId())
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
            product.setCategory(category);
        }

        if(request.getBrandId() != null){
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
            product.setBrand(brand);
        }

        return productMapper.toProductResponse(productReponsitory.save(product));
    }

//    Filter by product name and categoryId and price
    public List<ProductResponse> getProductFilter(SearchRequest request) {
        List<Product> allProducts = productReponsitory.findAll();

        List<Product> filteredProducts = allProducts.stream()
                .filter(product -> (request.getName() == null || (product.getName().toLowerCase().contains(request.getName().toLowerCase()))))
                .filter(product -> (request.getCategoryName() == null ||
                        (product.getCategory() != null && product.getCategory().getName().toLowerCase().contains(request.getCategoryName().toLowerCase()))))
                .filter(product -> (request.getBrandName() == null ||
                        (product.getBrand() != null && product.getBrand().getName().toLowerCase().contains(request.getBrandName().toLowerCase()))))
                .filter(product -> (request.getPrice() == null || product.getPrice() <= request.getPrice()))
                .collect(Collectors.toList());

        return filteredProducts.stream()
                .map(product -> productMapper.toProductResponse(product))
                .collect(Collectors.toList());
    }

//    Search
    public List<ProductResponse> searchByKeyword(String keyword){
        List<Product> products = productReponsitory.findByNameContainingIgnoreCase(keyword);
        if(!products.isEmpty()){
            return products.stream().map(productMapper::toProductResponse).collect(Collectors.toList());
        }

        List<Category> categories = categoryReponsitory.findByNameContainingIgnoreCase(keyword);
        if(!categories.isEmpty()){
            List<Product> productByCategory = productReponsitory.findByCategoryIn(categories);
            return productByCategory.stream().map(productMapper::toProductResponse).collect(Collectors.toList());
        }

        List<Brand> brands = brandRepository.findByNameContainingIgnoreCase(keyword);
        if(!brands.isEmpty()){
            List<Product> productByBrand = productReponsitory.findByBrandIn(brands);
            return productByBrand.stream().map(productMapper::toProductResponse).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    //    Delete Product
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(Long id){
        if(!productReponsitory.existsById(id)){
            throw new AppException(ErrorCode.PRODUCT_NOT_EXISTED);
        }
        productReponsitory.deleteById(id);
    }
}
