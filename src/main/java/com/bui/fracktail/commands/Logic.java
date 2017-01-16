/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bui.fracktail.commands;

import sx.blah.discord.handle.obj.IMessage;

/**
 * The logic of a Command.
 * The actual command code implements this interface.
 * @author justislamanna
 */
public interface Logic {
    /**
     * Execute the logic.
     * @param msg The message which called the command which called this logic.
     * @param params The parameters parsed from the command. Index 0 contains
     * the entire message, with the @Fracktail or F, removed. Subsequent
     * indices return that capture group.
     */
    void execute(IMessage msg, String... params);
}
