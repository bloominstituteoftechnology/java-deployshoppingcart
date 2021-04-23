package com.lambdaschool.shoppingcart.services;

import com.lambdaschool.shoppingcart.ShoppingCartApplicationTest;
import com.lambdaschool.shoppingcart.models.*;
import com.lambdaschool.shoppingcart.repository.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShoppingCartApplicationTest.class,
    properties = {
        "command.line.runner.enabled=false"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CartItemServiceImplUnitTestNoDB {

  @Autowired
  private CartItemService cartItemService;

  @MockBean
  private UserRepository userrepos;

  @MockBean
  private ProductRepository productrepos;

  @MockBean
  private CartItemRepository cartrepos;

  @MockBean
  private RoleRepository rolerepos;

  private List<Product> productList;

  private List<User> userList;


  @Before
  public void setUp() throws Exception {
    productList = new ArrayList<>();
    userList = new ArrayList<>();


    Role r1 = new Role("testadmin");
    Role r2 = new Role("testuser");

    r1.setRoleid(1);
    r2.setRoleid(2);

    User u1 = new User("testbarnbarn",
                       "LambdaLlama",
                       "barnbarn@host.local",
                       "added via seed data");
    u1.getRoles()
      .add(new UserRoles(u1,
                         r1));
    u1.getRoles()
      .add(new UserRoles(u1,
                         r2));
    u1.setUserid(1);

    User u2 = new User("testcinnamon",
                       "LambdaLlama",
                       "cinnamon@host.local",
                       "added via seed data");
    u2.getRoles()
      .add(new UserRoles(u2,
                         r2));
    u2.setUserid(2);

    User u3 = new User("teststumps",
                       "LambdaLlama",
                       "stumps@host.local",
                       "added via seed data");
    u3.getRoles()
      .add(new UserRoles(u3,
                         r2));
    u3.setUserid(3);

    // Adding Products

    Product p1 = new Product();
    p1.setName("TESTPEN");
    p1.setDescription("MAKES WORDS");
    p1.setPrice(2.50);
    p1.setComments("added via seed data");

    Product p2 = new Product();
    p2.setName("TESTPENCIL");
    p2.setDescription("DOES MATH");
    p2.setPrice(1.50);
    p2.setComments("added via seed data");

    Product p3 = new Product();
    p3.setName("TESTCOFFEE");
    p3.setDescription("EVERYONE NEEDS COFFEE");
    p3.setPrice(4.00);
    p3.setComments("added via seed data");

    productList.add(p1);
    productList.add(p2);
    productList.add(p3);

    // Creating Carts
    CartItem c1 = new CartItem(u1,
                                p1,
                                4,
                                "added via seed data");
    u1.getCarts()
      .add(c1);

    CartItem c2= new CartItem(u1,
                                p2,
                                3,
                                "added via seed data");
    u1.getCarts()
      .add(c2);

    CartItem c3 = new CartItem(u1,
                                p3,
                                2,
                                "added via seed data");
    u1.getCarts()
      .add(c3);

    CartItem c4 = new CartItem(u2,
                                p3,
                                1,
                                "added via seed data");
    u2.getCarts()
      .add(c3);

    CartItem c5 = new CartItem(u3,
                                p3,
                                17,
                                "added via seed data");
    u3.getCarts()
      .add(c5);

    userList.add(u1);
    userList.add(u2);
    userList.add(u3);

    MockitoAnnotations.initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void addToCart() {

    CartItemId cartItemId = new CartItemId(1, 1);
    CartItem cartItem = new CartItem();
    cartItem.setUser(userList.get(0));
    cartItem.setProduct(productList.get(0));
    cartItem.setComments("");
    cartItem.setQuantity(4);

    Mockito.when(userrepos.findById(1L))
           .thenReturn(Optional.of(userList.get(0)));

    Mockito.when(productrepos.findById(1L))
           .thenReturn(Optional.of(productList.get(0)));

//    Mockito.when(cartrepos.findById(any(CartItemId.class)))
//           .thenReturn(Optional.of(cartItem));                                             )

    Mockito.when(cartrepos.findById(any(CartItemId.class)))
           .thenReturn(Optional.of(cartItem));

    Mockito.when(cartrepos.save(any(CartItem.class)))
           .thenReturn(cartItem);

    assertEquals(3, cartItemService
        .addToCart(1L,
        1L,
        "Hello").getQuantity());
  }

  @Test
  public void removeFromCart() {
    CartItemId cartItemId = new CartItemId(1, 1);
    CartItem cartItem = new CartItem();
    cartItem.setUser(userList.get(0));
    cartItem.setProduct(productList.get(0));
    cartItem.setComments("");
    cartItem.setQuantity(4);

    Mockito.when(userrepos.findById(1L)).thenReturn(Optional.of(userList.get(0)));
    Mockito.when(productrepos.findById(1L)).thenReturn(Optional.of(productList.get(0)));
    Mockito.when(cartrepos.findById(any(CartItemId.class)))
           .thenReturn(Optional.of(cartItem));
    Mockito.when(cartrepos.save(any(CartItem.class))).thenReturn(cartItem);
  }
}