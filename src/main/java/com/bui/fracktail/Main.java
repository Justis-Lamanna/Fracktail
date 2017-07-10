/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bui.fracktail;

import com.bui.fracktail.data.DataHandler;
import com.bui.fracktail.data.FracktailDB;
import com.bui.fracktail.data.UserRepository;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author justislamanna
 */
@SpringBootApplication
public class Main implements CommandLineRunner
{
    private static final Logger LOG = Logger.getLogger(Main.class.getName());
    
    @Autowired
    UserRepository userRepository;
    
    @Bean
    FracktailDB fracktailDB(){
        LOG.info("Creating FracktailDB Bean");
        return new FracktailDB(userRepository);
    }
    
    @Bean
    Fracktail fracktail(){
        String readToken;
        try(Scanner in = new Scanner(new File("token.txt"))){
            readToken = in.nextLine();
            LOG.log(Level.INFO, "Token found: {0}", readToken);
        } catch (FileNotFoundException ex) {
            LOG.warning("Token.txt was not found.");
            readToken = "";
        }
        return new Fracktail(readToken);
    }
    
    public static void main(String[] args)
    {
        LOG.info("Starting Main");
        SpringApplication.run(Main.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        LOG.info("Starting Fracktail...");
        fracktail().connect();
        fracktail().login();
    }
}
