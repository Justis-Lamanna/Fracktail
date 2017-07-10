/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bui.fracktail;

import com.bui.fracktail.commands.Commands;
import com.bui.fracktail.data.FracktailDB;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RateLimitException;

/**
 * The bot itself.
 * This is an enum, which guarantees a singleton.
 * Using Fracktail:
 * Fracktail is trained to respond to regex matches, rather than through
 * the use of commands. The goal here is to allow more natural input to
 * Fracktail, at the cost of a slightly more confusing backend.
 * 
 * To call him, you simply say "@Fracktail [command]".
 * If you are particularly lazy, you may also say "F, [command]".
 * 
 * To modify the commands he knows, check out the Commands.initialize()
 * method. To view the actual commands, I'll place them in the readme.
 * @author justislamanna
 */
@Component
public class Fracktail{
    
    private final Logger LOG = Logger.getLogger(Fracktail.class.getName());
    
    private IDiscordClient client;
    
    @Autowired
    private FracktailDB db;
    private String token;
    
    public Fracktail(String token){
        this.token = token;
    }
    
    public void connect(){
        LOG.log(Level.INFO, "Building Fracktail...{0}", token);
        ClientBuilder builder = new ClientBuilder();
        builder.withToken(token);
        try{
            client = builder.build();
            client.getDispatcher().registerListener(new Handlers());
            Commands.initialize();
        } catch (DiscordException ex) {
            LOG.log(Level.SEVERE, "Error initializing Fracktail.", ex);
        }
    }
    
    /**
     * Logs Fracktail in.
     */
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
    
    /**
     * Logs Fracktail out, and stops program execution.
     */
    public void logout(){
        try{
            client.logout();
            db.close();
            System.exit(0);
        }
        catch(DiscordException ex){
            LOG.log(Level.SEVERE, "Error logging off.", ex);
        }
    }
    
    /**
     * Get a channel Fracktail is connected to.
     * @param id The ID of the channel.
     * @return The channel object.
     */
    public IChannel getChannel(String id){
        return client.getChannelByID(id);
    }
    
    /**
     * Get a private channel.
     * @param id The user's ID.
     * @return The private channel.
     */
    public IPrivateChannel getPrivateChannel(String id){
        try {
            return client.getOrCreatePMChannel(client.getUserByID(id));
        } catch (DiscordException ex) {
            LOG.log(Level.SEVERE, "General error getting Private Channel.", ex);
        } catch (RateLimitException ex) {
            LOG.log(Level.SEVERE, "Rate limit exceeded.", ex);
        }
        return null;
    }
    
    /**
     * Get a user by their ID.
     * @param id The ID.
     * @return The user.
     */
    public IUser getUser(String id){
        return client.getUserByID(id);
    }
    
    /**
     * Get myself.
     * @return Me.
     */
    public IUser getSelf(){
        return client.getOurUser();
    }
    
    /**
     * Get my master.
     * @return Master lucbui.
     */
    public IUser getMaster(){
        try{
            return client.getApplicationOwner();
        } catch (DiscordException ex) {
            LOG.log(Level.SEVERE, "Error getting app owner.", ex);
            return null;
        }
    }
}
