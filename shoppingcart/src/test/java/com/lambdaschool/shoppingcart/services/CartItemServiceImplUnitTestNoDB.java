package com.lambdaschool.shoppingcart.services;

import com.lambdaschool.shoppingcart.ShoppingCartApplicationTests;
import com.lambdaschool.shoppingcart.models.*;
import com.lambdaschool.shoppingcart.repository.CartItemRepository;
import com.lambdaschool.shoppingcart.repository.ProductRepository;
import com.lambdaschool.shoppingcart.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShoppingCartApplicationTests.class, properties = {"command.line.runner.enabled=false"})
public class CartItemServiceImplUnitTestNoDB {

  //instantiate userList and prodList so that the lists can be accessed at all levels.

  /** List of users to modify */
  private List<User> userList = new ArrayList<>();
  /** List of products to modify */
  private List<Product> prodList = new ArrayList<>();

  @Autowired
  CartItemService cartItemService;

  @MockBean
  private UserRepository userrepos;

  @MockBean
  private ProductRepository prodrepos;

  @MockBean
  private CartItemRepository cartitemrepos;

  private List<CartItem> cartItemList = new ArrayList<>();

  @Before
  public void setUp() throws Exception {
    Role r1 = new Role("admin");
    r1.setRoleid(1);
    Role r2 = new Role("user");
    r2.setRoleid(2);

    //*********** USERS SET-UP *************
    User u1 = new User("barnbarn", "$2y$12$.PBQBQS87DP/GT.2cBnK7.WvQuUbdMTzPVvT2C47cxnSvPX73kp2C", "barnbarn@host.local", "");
    User u2 = new User("cinnamon", "$2y$12$.PBQBQS87DP/GT.2cBnK7.WvQuUbdMTzPVvT2C47cxnSvPX73kp2C", "cinnamon@host.local", "");
    User u3 = new User("stumps", "$2y$12$.PBQBQS87DP/GT.2cBnK7.WvQuUbdMTzPVvT2C47cxnSvPX73kp2C", "stumps@host.local", "");

    u1.getRoles().add(new UserRoles(u1, r1));
    u1.getRoles().add(new UserRoles(u1, r2));
    u2.getRoles().add(new UserRoles(u2, r2));
    u3.getRoles().add(new UserRoles(u3, r2));

    u1.setUserid(1);
    u2.setUserid(2);
    u3.setUserid(3);

    // do not add users to list yet

    //*********** PRODUCTS SET-UP *************
    Product p1 = new Product();
    p1.setName("PEN");
    p1.setDescription("MAKES WORDS");
    p1.setPrice(2.50);
    p1.setComments("added via seed data");

    Product p2 = new Product();
    p2.setName("PENCIL");
    p2.setDescription("DOES MATH");
    p2.setPrice(1.50);
    p2.setComments("added via seed data");

    Product p3 = new Product();
    p2.setName("COFFEE");
    p2.setDescription("EVERYONE NEEDS COFFEE");
    p2.setPrice(4.00);
    p2.setComments("added via seed data");

    p1.setProductid(1);
    p2.setProductid(2);
    p3.setProductid(3);
    prodList.add(p1);
    prodList.add(p2);
    prodList.add(p3);


    //*********** CART ITEMS SET-UP *************

    //item 1
    CartItem ci1 = new CartItem();
    ci1.setUser(u1);
    ci1.setProduct(p1);
    ci1.setComments("added via seed data");
    ci1.setQuantity(4);
    //add item to associated user
    u1.getCarts().add(ci1);

    //item 2
    CartItem ci2 = new CartItem();
    ci2.setUser(u1);
    ci2.setProduct(p2);
    ci2.setComments("added via seed data");
    ci2.setQuantity(2);
    //add item to associated user
    u1.getCarts().add(ci2);

    //item 3
    CartItem ci3 = new CartItem();
    ci3.setUser(u2);
    ci3.setProduct(p3);
    ci3.setComments("added via seed data");
    ci3.setQuantity(1);
    //add item to associated user
    u2.getCarts().add(ci3);

    //item 4
    CartItem ci4 = new CartItem();
    ci4.setUser(u3);
    ci4.setProduct(p3);
    ci4.setComments("added via seed data");
    ci4.setQuantity(5);
    //add item to associated user
    u3.getCarts().add(ci4);

    //************** ADD USERS TO USERLIST ***********
    userList.add(u1);
    userList.add(u2);
    userList.add(u3);

    MockitoAnnotations.initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    //purposely blank
  }

  @Test
  public void addToCart() {
    CartItemId cartItemId = new CartItemId(1, 1);
    CartItem cart3 = new CartItem();
    cart3.setUser(userList.get(0));
    cart3.setProduct(prodList.get(0));
    cart3.setComments("");
    cart3.setQuantity(2);

    Mockito.when(userrepos.findById(1L)).thenReturn(Optional.of(userList.get(0)));
    Mockito.when(prodrepos.findById(1L)).thenReturn(Optional.of(prodList.get(0)));
    Mockito.when(cartitemrepos.findById(any(CartItemId.class))).thenReturn(Optional.of(cart3));
    Mockito.when(cartitemrepos.save(any(CartItem.class))).thenReturn(cart3);

    assertEquals(3, cartItemService.addToCart(1L,1L,"Hello").getQuantity());
  }

  @Test
  public void removeFromCart() {
    CartItemId cartItemId = new CartItemId(1, 1);
    CartItem cart3 = new CartItem();
    cart3.setUser(userList.get(0));
    cart3.setProduct(prodList.get(0));
    cart3.setComments("");
    cart3.setQuantity(3);

    Mockito.when(userrepos.findById(1L)).thenReturn(Optional.of(userList.get(0)));
    Mockito.when(prodrepos.findById(1L)).thenReturn(Optional.of(prodList.get(0)));
    Mockito.when(cartitemrepos.findById(any(CartItemId.class))).thenReturn(Optional.of(cart3));
    Mockito.when(cartitemrepos.save(any(CartItem.class))).thenReturn(cart3);

    assertEquals(2,
        cartItemService.removeFromCart(1L,
            1L,
            "Bye")
            .getQuantity());
  }
}