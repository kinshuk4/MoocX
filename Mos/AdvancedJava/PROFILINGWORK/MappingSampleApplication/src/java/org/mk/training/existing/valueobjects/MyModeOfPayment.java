/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mk.training.existing.valueobjects;

/**
 *
 * @author veena mohit kumar
 */
public enum MyModeOfPayment {
    CREDITCARD,
    CASH;
    public String value() {
        return name();
    }

    public static MyModeOfPayment fromValue(String v) {
        return valueOf(v);
    }
}
