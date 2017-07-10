/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bui.fracktail;

import com.bui.fracktail.commands.Command;
import com.bui.fracktail.commands.Commands;
import java.util.logging.Level;
import java.util.logging.Logger;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MentionEvent;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IMessage;

/**
 *
 * @author justislamanna
 */
public class Handlers
{
    private static final Logger LOG = Logger.getLogger(Handlers.class.getName());
    
    @EventSubscriber
    public void onReady(ReadyEvent event){
        LOG.info("OnReady activated.");
    }
    
    @EventSubscriber
    public void onMention(MentionEvent mention){
        IMessage msg = mention.getMessage();
        LOG.log(Level.INFO, "Mentioned by {0}({2}):{1}", new Object[]{msg.getAuthor().getName(), msg.getContent(), msg.getAuthor().getID()});
        String content = msg.getContent();
        String[] split = content.split(" +", 2);
        //split[0] contains @Fracktail. split[1] returns the actual message.
        if(split.length == 1){
            Commands.say(msg, msg.getAuthor().mention() + "?");
        }
        else{
            String stripped = split[1].trim();
            Command ex = Commands.getCommand(stripped);
            ex.doCommand(msg, stripped);
        }
    }
    
    @EventSubscriber
    public void onMessage(MessageReceivedEvent message){
        IMessage msg = message.getMessage();
        String content = msg.getContent();
        String[] split = content.split(" +", 2);
        if(split[0].equalsIgnoreCase(Constants.PREFIX)){
            LOG.log(Level.INFO, "Messaged by {0}({2}):{1}", new Object[]{msg.getAuthor().getName(), msg.getContent(), msg.getAuthor().getID()});
            String stripped = split[1].trim();
            Command ex = Commands.getCommand(stripped);
            ex.doCommand(msg, stripped);
        }
    }
}
