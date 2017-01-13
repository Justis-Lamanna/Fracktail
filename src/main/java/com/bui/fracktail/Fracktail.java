/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bui.fracktail;

import com.bui.fracktail.commands.Commands;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RateLimitException;

/**
 * The bot itself.
 * This is an enum, which guarantees a singleton.
 * @author justislamanna
 */
public enum Fracktail
{
    BOT;
    
    private final String TOKEN = System.getProperty("Token");
    private final Logger LOG = Logger.getLogger(Fracktail.class.getName());
    
    private IDiscordClient client;
    
    private Fracktail(){
        ClientBuilder builder = new ClientBuilder();
        builder.withToken(TOKEN);
        try{
            client = builder.build();
            client.getDispatcher().registerListener(new Handlers());
            Commands.initialize();
        } catch (DiscordException ex) {
            LOG.log(Level.SEVERE, "Error initializing Fracktail.", ex);
        }
    }
    
    public void login(){
        try{
            client.login();
        }
        catch(DiscordException ex){
            LOG.log(Level.SEVERE, "Error logging on.", ex);
        }
        catch(RateLimitException ex){
            LOG.log(Level.SEVERE, "Error: Rate limit exceeded.", ex);
        }
    }
    
    public void logout(){
        try{
            client.logout();
            System.exit(0);
        }
        catch(DiscordException ex){
            LOG.log(Level.SEVERE, "Error logging off.", ex);
        }
    }
    
    public IDiscordClient getClient(){
        return client;
    }
}
