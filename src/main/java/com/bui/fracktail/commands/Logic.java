/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bui.fracktail.commands;

import sx.blah.discord.handle.obj.IMessage;

/**
 *
 * @author justislamanna
 */
public interface Logic {
    void execute(IMessage msg, String... params);
}
