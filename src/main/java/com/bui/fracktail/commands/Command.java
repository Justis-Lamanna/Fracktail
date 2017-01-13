/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bui.fracktail.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sx.blah.discord.handle.obj.IMessage;

/**
 *
 * @author justislamanna
 */
public class Command
{
    private final Pattern regex;
    private final String desc;
    private final int rank;
    private final Logic logic;
    
    /**
     * Creates a command.
     * Command regexes are automatically case insensitive. Robot, robot, and
     * ROBOT are all the same.
     * @param regex The regex which matches this command.
     * @param description The description of this command.
     * @param rank The minimum rank to use this command.
     * @param logic The logic behind this command.
     */
    public Command(String regex, String description, int rank, Logic logic){
        this.regex = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        this.desc = description;
        this.rank = rank;
        this.logic = logic;
    }
    
    /**
     * Checks if this command would be activated by the given String.
     * Basically, this checks if the String provided would match the regex.
     * @param toMatch The string to check against.
     * @return True if there is a match, false otherwise.
     */
    public boolean matches(String toMatch){
        return toMatch.matches(regex.pattern());
    }
    
    /**
     * Do the command.
     * @param msg The message which incited this command.
     * @param stripped The message, stripped of "@Fracktail"
     */
    public void doCommand(IMessage msg, String stripped){
        //TODO: Check rank one day.
        Matcher m = regex.matcher(stripped);
        if(m.find()){
            String[] keywords = new String[m.groupCount() + 1];
            for(int index = 0; index < keywords.length; index++){
                keywords[index] = m.group(index);
            }
            logic.execute(msg, keywords);
        }
    }
    
    /**
     * Get the description of this command.
     * @return The description.
     */
    public String getDescription(){
        return desc;
    }
}
