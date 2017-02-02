/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bui.fracktail.commands;

import com.bui.fracktail.Fracktail;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageList;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 *
 * @author justislamanna
 */
public class Commands
{
    private static final Logger LOG = Logger.getLogger(Commands.class.getName());
    
    private static final ArrayList<Command> CMDS = new ArrayList<>();
    
    private Commands(){}
    
    private static final Command NULL_COMMAND = 
            new Command("", null, 0, (msg, params) -> reply(msg, "I don't understand."));
    
    /**
     * Reply to a person.
     * @param msg The message they sent.
     * @param reply The reply to give.
     */
    public static void reply(IMessage msg, String reply){
        try{
            msg.reply(reply);
        } catch (MissingPermissionsException ex) {
            LOG.log(Level.SEVERE, "Missing permissions.", ex);
        } catch (RateLimitException ex) {
            LOG.log(Level.SEVERE, "Rate limit exceeded.", ex);
        } catch (DiscordException ex) {
            LOG.log(Level.SEVERE, "General error.", ex);
        }
    }
    
    /**
     * Say something.
     * @param msg The message they sent.
     * @param reply The reply to give.s
     */
    public static void say(IMessage msg, String reply){
        try{
            msg.getChannel().sendMessage(reply);
        } catch (MissingPermissionsException ex) {
            LOG.log(Level.SEVERE, "Missing permissions.", ex);
        } catch (RateLimitException ex) {
            LOG.log(Level.SEVERE, "Rate limit exceeded.", ex);
        } catch (DiscordException ex) {
            LOG.log(Level.SEVERE, "General error.", ex);
        }
    }
    
    /**
     * Send a direct message.
     * @param channel The private channel.
     * @param message The message.
     */
    public static void dm(IPrivateChannel channel, String message){
        try{
            channel.sendMessage(message);
        } catch (MissingPermissionsException ex) {
            LOG.log(Level.SEVERE, "Missing permissions.", ex);
        } catch (RateLimitException ex) {
            LOG.log(Level.SEVERE, "Rate limit exceeded.", ex);
        } catch (DiscordException ex) {
            LOG.log(Level.SEVERE, "General error.", ex);
        }
    }
    
    /**
     * Get the command to execute.
     * @param message The message. This must match the regex contained in the Command.
     * @return The command matched, or the null-command if none.
     */
    public static Command getCommand(String message){
        for(Command c : CMDS){
            if(c.matches(message)){
                return c;
            }
        }
        return NULL_COMMAND;
    }
    
    /**
     * Loads all the commands into the maps.
     */
    public static void initialize(){
        CMDS.add(new Command("log\\s*(out|off)", "I log off.", 2, 
            (IMessage msg, String... params) -> {
                if(msg.getAuthor() == Fracktail.BOT.getMaster()){
                    reply(msg, "Goodbye.");
                    Fracktail.BOT.logout();
                }
                else{
                    reply(msg, "No fuck you.");
                }
        }));
        CMDS.add(new Command("clean.*up!*", "I remove the last 100 messages from the chat.", 2, 
            (IMessage msg, String... params) -> {
                IChannel channel = Fracktail.BOT.getChannel(msg.getChannel().getID());
                MessageList messages = channel.getMessages();
                try{
                    messages.deleteAfter(0);
                    reply(msg, "I did my best, sir.");
                } catch (RateLimitException ex) {
                    LOG.log(Level.SEVERE, "Rate limit exceeded.", ex);
                } catch (DiscordException ex) {
                    LOG.log(Level.SEVERE, "General error occured.", ex);
                } catch (MissingPermissionsException ex) {
                    reply(msg, "I don't have that ability here.");
                }
        }));
        CMDS.add(new Command("clean\\s+up\\s+and\\s+log\\s*(out|off)", "I remove the last 100 messages in the chat and log off.", 2, 
                (IMessage msg, String... params) -> {
                IChannel channel = Fracktail.BOT.getChannel(msg.getChannel().getID());
                MessageList messages = channel.getMessages();
                try{
                    messages.deleteAfter(0);
                    Fracktail.BOT.logout();
                } catch (RateLimitException ex) {
                    LOG.log(Level.SEVERE, "Rate limit exceeded.", ex);
                } catch (DiscordException ex) {
                    LOG.log(Level.SEVERE, "General error occured.", ex);
                } catch (MissingPermissionsException ex) {
                    reply(msg, "I don't have that ability here.");
                }
        }));
        Logic msgLogic = (msg, params) -> {
            List<IUser> mentions = msg.getMentions();
            mentions.remove(Fracktail.BOT.getSelf());
            if(mentions.isEmpty()){
                reply(msg, "I can't message myself.");
            }
            for(IUser i : mentions){
                IPrivateChannel channel = Fracktail.BOT.getPrivateChannel(i.getID());
                dm(channel, params[2]);
            }
            reply(msg, "They've been messaged.");
        };
        CMDS.add(new Command("message\\s+((?:<@\\S+>\\s*,?\\s*)+)\\s+saying\\s+(.+)", "I send a message to someone.", 0, msgLogic));
    }
}
