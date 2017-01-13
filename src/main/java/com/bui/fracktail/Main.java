/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bui.fracktail;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 *
 * @author justislamanna
 */
public class Main
{
    private static final Logger LOG = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args)
    {
        String readToken;
        try(Scanner in = new Scanner(new File("token.txt"))){
            readToken = in.nextLine();
        } catch (FileNotFoundException ex) {
            readToken = "";
        }
        System.setProperty("Token", readToken);
        LOG.info("Starting Fracktail...");
        Fracktail.BOT.login();
    }
}
