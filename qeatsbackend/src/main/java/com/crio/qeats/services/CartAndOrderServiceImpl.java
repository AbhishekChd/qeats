package com.crio.qeats.services;

import com.crio.qeats.dto.Cart;
import com.crio.qeats.dto.Item;
import com.crio.qeats.dto.Order;
import com.crio.qeats.exceptions.*;
import com.crio.qeats.exchanges.CartModifiedResponse;
import com.crio.qeats.repositoryservices.CartRepositoryService;
import com.crio.qeats.repositoryservices.OrderRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartAndOrderServiceImpl implements CartAndOrderService {

  @Autowired
  private CartRepositoryService cartRepositoryService;

  @Autowired
  private OrderRepositoryService orderRepositoryService;

  @Autowired
  private MenuService menuService;

  @Override
  public Cart findOrCreateCart(String userId) throws UserNotFoundException {
    Optional<Cart> cartByUserId = cartRepositoryService.findCartByUserId(userId);

    if (cartByUserId.isPresent()) {
      return cartByUserId.get();
    } else {
      Cart cart = new Cart();
      cart.setUserId(userId);
      cart.setRestaurantId("");
      String cartId = cartRepositoryService.createCart(cart);
      return cartRepositoryService.findCartByCartId(cartId);
    }
  }

  @Override
  public CartModifiedResponse addItemToCart(String itemId, String cartId, String restaurantId) throws ItemNotFromSameRestaurantException {
    CartModifiedResponse cartModifiedResponse = null;
    try {
      Item item = menuService.findItem(itemId, restaurantId);
      cartModifiedResponse = new CartModifiedResponse(cartRepositoryService.addItem(item, cartId,
          restaurantId), 0);
    } catch (ItemNotFoundInRestaurantMenuException e) {
      try {
        cartModifiedResponse =
            new CartModifiedResponse(cartRepositoryService.findCartByCartId(cartId), 0);
      } catch (CartNotFoundException e2) {
        cartModifiedResponse = new CartModifiedResponse(new Cart(), 0);
      }
    } catch (CartNotFoundException e) {
      cartModifiedResponse = new CartModifiedResponse(new Cart(), 0);
    }
    return cartModifiedResponse;
  }

  @Override
  public CartModifiedResponse removeItemFromCart(String itemId, String cartId,
                                                 String restaurantId) {
    CartModifiedResponse cartModifiedResponse;
    try {
      Item item = menuService.findItem(itemId, restaurantId);
      cartModifiedResponse = new CartModifiedResponse(cartRepositoryService.removeItem(item, cartId,
          restaurantId), 0);
    } catch (ItemNotFoundInRestaurantMenuException e) {
      try {
        cartModifiedResponse =
            new CartModifiedResponse(cartRepositoryService.findCartByCartId(cartId), 0);
      } catch (CartNotFoundException e2) {
        cartModifiedResponse = new CartModifiedResponse(new Cart(), 0);
      }
    } catch (CartNotFoundException e) {
      cartModifiedResponse = new CartModifiedResponse(new Cart(), 0);
    }
    return cartModifiedResponse;
  }

  @Override
  public Order postOrder(String cartId) throws EmptyCartException {
    try {
      Cart cart = cartRepositoryService.findCartByCartId(cartId);
      if (cart.getItems().isEmpty()) {
        throw new EmptyCartException("Cart is empty");
      }
      return orderRepositoryService.placeOrder(cart);
    } catch (CartNotFoundException e) {
      throw new EmptyCartException("Cart doesn't exist");
    }
  }
}
