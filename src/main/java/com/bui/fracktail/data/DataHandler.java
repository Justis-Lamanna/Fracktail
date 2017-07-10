/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bui.fracktail.data;

/**
 * An abstraction of a data handler.
 * This allows you to swap between various database schemas, or other data
 * retrieval and storage techniques.
 * @author Justis
 */
public interface DataHandler {
    public void backup();
    public void close();
    public FracktailUser getFracktailUser(String id);
    public void updateFracktailUser(String id, FracktailUser user);
}
