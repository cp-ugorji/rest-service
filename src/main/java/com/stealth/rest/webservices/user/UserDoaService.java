/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stealth.rest.webservices.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Chux
 */
@Component
public class UserDoaService {
    private static List<User> users = new ArrayList<>();
    private static int userCount = 6;
    
    static
    {
        users.add(new User(1, "Olabisi", new Date()));
        users.add(new User(2, "Olakunori", new Date()));
        users.add(new User(3, "Victor", new Date()));
        users.add(new User(4, "Philip", new Date()));
        users.add(new User(5, "Alex", new Date()));
        users.add(new User(6, "Efe", new Date()));
    }
    
    public List<User> getAllUser()
    {
        return users;
    }
    
    public User addUser(User user)
    {
        if(user.getId() == null)
            user.setId(++userCount);
        
        users.add(user);        
        return user;
    }
    
    public User findUser(int id)
    {
        for(User user : users)
        {
            if(user.getId() == id)
            {
                return user;
            }
        }
        return null;
    }
    
    public User deleteUser(int id)
    {
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext())
        {
            User user = iterator.next();
            if(user.getId() == id)
            {
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
