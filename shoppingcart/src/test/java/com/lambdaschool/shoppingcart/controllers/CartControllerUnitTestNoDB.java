package com.lambdaschool.shoppingcart.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.shoppingcart.ShoppingCartApplicationTests;
import com.lambdaschool.shoppingcart.models.*;
import com.lambdaschool.shoppingcart.services.CartItemService;
import com.lambdaschool.shoppingcart.services.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = ShoppingCartApplicationTests.class)
@AutoConfigureMockMvc
@WithMockUser(username = "admin",
    roles = {"USER", "ADMIN"})
public class CartControllerUnitTestNoDB {

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @MockBean
  private CartItemService cartItemService;

  @MockBean
  private UserService userService;

  private List<User> userList = new ArrayList<>();
  private List<Product> prodList = new ArrayList<>();

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

    RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void listCartItemsByUserId() throws Exception {
    String apiUrl = "/carts/user";
    Mockito.when(userService.findByName(any(String.class)))
        .thenReturn(userList.get(0));

    RequestBuilder rb = get(apiUrl)
        .accept(MediaType.APPLICATION_JSON);
    MvcResult r = mockMvc.perform(rb)
        .andReturn();
    String tr = r.getResponse()
        .getContentAsString();

    ObjectMapper mapper = new ObjectMapper();
    String er = mapper.writeValueAsString(userList.get(0));

    assertEquals(er, tr);
  }

  @Test
  public void addToCart() throws Exception {
    String apiUrl = "/carts/add/product/1";

    CartItem cart3 = new CartItem();
    cart3.setUser(userList.get(0));
    cart3.setProduct(prodList.get(0));
    cart3.setComments("");
    cart3.setQuantity(2);

    Mockito.when(userService.findByName(any(String.class))).thenReturn(userList.get(0));
    Mockito.when(cartItemService.addToCart(any(Long.class), any(Long.class), any(String.class)))
        .thenReturn(cart3);

    RequestBuilder rb = put(apiUrl)
        .accept(MediaType.APPLICATION_JSON);
    MvcResult r = mockMvc.perform(rb)
        .andReturn();
    String tr = r.getResponse()
        .getContentAsString();

    ObjectMapper mapper = new ObjectMapper();
    String er = mapper.writeValueAsString(cart3);

    Assert.assertEquals(er,
        tr);
  }

  @Test
  public void removeFromCart() {
  }
}