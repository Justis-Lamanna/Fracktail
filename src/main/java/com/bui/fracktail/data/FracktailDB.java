/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bui.fracktail.data;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author Justis
 */
@Component
public class FracktailDB implements DataHandler{
    
    UserRepository userRepository;
    
    private static final Logger LOG = Logger.getLogger(FracktailDB.class.getName());
    
    public FracktailDB(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    
    @Override
    public void backup() {
        //No backup here.
        LOG.warning("Attempted to backup, but there is no backup method.");
    }

    @Override
    public FracktailUser getFracktailUser(String id) {
        LOG.log(Level.INFO, "Found user with User ID {0}", id);
        return userRepository.findByUserId(id);
    }

    @Override
    public void updateFracktailUser(String id, FracktailUser user) {
        LOG.log(Level.INFO, "Updating user with User ID {0}", id);
        userRepository.save(user);
    }

    @Override
    public void close() {
        
    }
}
