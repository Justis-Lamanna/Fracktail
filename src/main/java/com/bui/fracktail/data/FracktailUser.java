/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bui.fracktail.data;

import org.springframework.data.annotation.Id;

/**
 *
 * @author Justis
 */
public class FracktailUser {

    @Id
    String id;
    
    String userId;
    
    public FracktailUser(String userId){
        this.userId = userId;
    }
    
    public String getUserID(){
        return id;
    }    
}
