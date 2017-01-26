/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mk.training.dynamicproxy;

/**
 *
 * @author veenamohitkumar
 */
public class X implements XIntf {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("setName(String name)"+name);
        this.name = name;
    }
}
