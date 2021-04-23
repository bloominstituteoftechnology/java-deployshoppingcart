package com.lambdaschool.shoppingcart.services;

import com.lambdaschool.shoppingcart.controllers.CartControllerIntergrationTest;
import com.lambdaschool.shoppingcart.models.CartItem;
import com.lambdaschool.shoppingcart.models.Product;
import com.lambdaschool.shoppingcart.models.User;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WithMockUser(username = "admin",
    roles = {"USER", "ADMIN"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = CartControllerIntergrationTest.class,
    properties = {"command.line.runner.enabled=false"})
@AutoConfigureMockMvc
public class CartItemServiceImplTestNoDB
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CartItemRepository cartItemRepository;

    private List<CartItem> cartItemList;

    @Before
    public void setUp() throws Exception
    {
        cartItemList = new ArrayList<>();
//        (PRODUCTID, NAME, DESCRIPTION, PRICE, COMMENTS,
//        (1, 'PEN', 'MAKES WORDS', 2.50, '',
//        (2, 'PENCIL', 'DOES MATH', 1.50, '',
//        (3, 'COFFEE', 'EVERYONE NEEDS COFFEE', 4.00, ''

        //        CARTITEMS(USERID, PRODUCTID, QUANTITY, COMMENTS,
        //        VALUES (1, 1, 4, '',
        //            (1, 2, 3, '',
        //        (1, 3, 2, '',
        //        (2, 3, 1, '',
//
//        Product p1 = new Product("Pen",2.50, "makes words", "");
//        Product p2 = new Product("Pencil", 1.50, "does math", "");
//        Product p3 = new Product("Coffee", 4.00, "everyone needs this", "");
//
//        p1.setProductid(1);
//        p2.setProductid(2);
//        p3.setProductid(3);
//
//        User u1 = new User("Bob", "LambdaLlama", "stuff@asdf.com", );
//        User u2 = new User("Pookie", "LambdaLlama", "farts@hotmail.com", );
//        User u3 = new User("Mookie", "LambdaLlama", "mature@email.com", );
//
//        u1.setUserid(1);
//        u2.setUserid(2);
//        u3.setUserid(3);
//
//        CartItem c1 = new CartItem(1,1,4,"");
//        CartItem c2 = new CartItem(1 ,2,3,"GRRRREAT");
//        CartItem c3 = new CartItem(1,3,2,"");
//        CartItem c4 = new CartItem(2,3,1,"");
//
//        cartItemList.add(c1);
//        cartItemList.add(c2);
//        cartItemList.add(c3);
//        cartItemList.add(c4);

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void addToCart()
    {

    }

    @Test
    public void removeFromCart()
    {
    }
}