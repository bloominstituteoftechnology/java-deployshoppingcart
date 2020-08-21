package com.lambdaschool.shoppingcart.services;

import com.lambdaschool.shoppingcart.ShoppingcartApplication;
import com.lambdaschool.shoppingcart.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShoppingcartApplication.class)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest
{
    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        List<User> myList = userService.findAll();
        for (User u : myList)
        {
            System.out.println(u.getUserid() + "" + u.getUsername());
        }
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void findAll()
    {
        assertEquals(3, userService.findAll().size());
    }

    @Test
    public void findUserById()
    {
        assertEquals("test barnbarn", userService.findUserById(7).getUsername());
    }

    @Test
    public void findByName()
    {
    }

    @Test
    public void delete()
    {
    }

    @Test
    public void save()
    {
    }
}