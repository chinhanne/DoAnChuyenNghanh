package com.dacn.WebsiteBanDoCongNghe.service;

import com.dacn.WebsiteBanDoCongNghe.dto.request.CartItemRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.CartItemResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.CartResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.*;
import com.dacn.WebsiteBanDoCongNghe.exception.AppException;
import com.dacn.WebsiteBanDoCongNghe.exception.ErrorCode;
import com.dacn.WebsiteBanDoCongNghe.mapper.CartMapper;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.CartItemRepository;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.CartRepository;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.ProductReponsitory;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.UserReponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartService {
    CartRepository cartRepository;
    CartMapper cartMapper;
    CartItemRepository cartItemRepository;
    UserReponsitory userReponsitory;
    ProductReponsitory productReponsitory;

//    Kiểm tra sự tồn tại của user
    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        String username = authentication.getName();
        return userReponsitory.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

//    Kiểm tra người dùng có thuộc về giỏ hàng của mình không
    private void validateCartOwnerShip(User user, Cart cart){
        if(!cart.getUser().getUsername().equals(user.getUsername())){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

//    tạo giỏ hàng khi user đăng nhập
    protected Cart createdCartForUser(User user){
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalPrice(0.0);
        return cartRepository.save(cart);
    }

    //    Created product to Cart
    public CartResponse addProductToCart( CartItemRequest request){
//        Kiểm tra nguười dùng có phải là chủ giỏ hàng không
        User user = getAuthenticatedUser();
        Cart cart = cartRepository.findByUser_Username(user.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));
        validateCartOwnerShip(user,cart);

//         Thiết lập sản phẩm vào cartItem
        Product product = productReponsitory.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        CartItem cartItem = cartMapper.toCartItem(request);
        cartItem.setProduct(product);
        cartItem.setCart(cart);

        Optional<CartItem> filterCartItem = cart.getCartItems().stream().filter(item ->
                item.getProduct().getId().equals(cartItem.getProduct().getId())).findFirst();

        if(filterCartItem.isPresent()){
            CartItem existingCartItem = filterCartItem.get();
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItem.getQuantity());
            existingCartItem.setTotalPrice(existingCartItem.getQuantity() * existingCartItem.getProduct().getPrice());
            cart.setTotalPrice(cart.getTotalPrice() + (cartItem.getQuantity() * existingCartItem.getProduct().getPrice()));
        }else{
            cartItem.setTotalPrice(cartItem.getQuantity() * cartItem.getProduct().getPrice());
            cartItemRepository.save(cartItem);
            cart.getCartItems().add(cartItem);
            cart.setTotalPrice(cart.getTotalPrice() + cartItem.getTotalPrice());
        }

        cartRepository.save(cart);

        CartResponse cartResponse = cartMapper.toResponseCart(cart);
        cartResponse.setCartItems(cart.getCartItems().stream()
                .map(cartMapper::toResponseCartItem)
                .collect(Collectors.toList()));

        return cartResponse;
    }

//    Delete product to Cart
    public CartResponse removeProductFromCart(Long cartItemId){
        User user = getAuthenticatedUser();

        Cart cart = cartRepository.findByUser_Username(user.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));

        validateCartOwnerShip(user,cart);

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_EXISTED));

        cartItemRepository.delete(cartItem);
        cart.setTotalPrice(cart.getTotalPrice() - cartItem.getTotalPrice());
        cartRepository.save(cart);

        return cartMapper.toResponseCart(cart);
    }

//    Get CartItem by userId
    public List<CartItemResponse> getCartItems() {
        User user = getAuthenticatedUser();
        Cart cart = cartRepository.findByUser_Username(user.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));

        return cart.getCartItems().stream()
                .map(cartMapper::toResponseCartItem)
                .toList();
    }

//    Button increase quantity cartItem
    public CartResponse incrementCartItemQuantity(Long cartItemId){
        User user = getAuthenticatedUser();
        Cart cart = cartRepository.findByUser_Username(user.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));

        // Kiểm tra quyền sở hữu giỏ hàng
        validateCartOwnerShip(user, cart);

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_EXISTED));

//        Kiểm tra sản phẩm có thuộc về giỏ hàng không
        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new AppException(ErrorCode.INVALID_CART_ITEM);
        }

        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItem.setTotalPrice(cartItem.getQuantity() * cartItem.getProduct().getPrice());
        cart.setTotalPrice(cart.getTotalPrice() + cartItem.getProduct().getPrice());

        cartRepository.save(cart);
        cartItemRepository.save(cartItem);

        return cartMapper.toResponseCart(cart);
    }

    //    Button decrease quantity cartItem
    public CartResponse decrementCartItemQuantity(Long cartItemId){
        User user = getAuthenticatedUser();
        Cart cart = cartRepository.findByUser_Username(user.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));

        // Kiểm tra quyền sở hữu giỏ hàng
        validateCartOwnerShip(user, cart);

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_EXISTED));

//        Kiểm tra sản phẩm có thuộc về giỏ hàng không
        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new AppException(ErrorCode.INVALID_CART_ITEM);
        }

        if(cartItem.getQuantity() > 1){
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItem.setTotalPrice(cartItem.getQuantity() * cartItem.getProduct().getPrice());
            cart.setTotalPrice(cart.getTotalPrice() - cartItem.getProduct().getPrice());
            cartRepository.save(cart);
            cartItemRepository.save(cartItem);
        }else{
            cart.getCartItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
            cart.setTotalPrice(cart.getTotalPrice() - cartItem.getTotalPrice());
            cartRepository.save(cart);
        }

        return cartMapper.toResponseCart(cart);
    }
}
