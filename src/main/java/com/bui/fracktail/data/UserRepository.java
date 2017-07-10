/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bui.fracktail.data;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Justis
 */
public interface UserRepository extends MongoRepository<FracktailUser, String>{
    public FracktailUser findByUserId(String userId);
}
