/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mk.training.existing.valueobjects;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.mk.training.util.ObjectAnalyzer;

/**
 *
 * @author veena mohit kumar
 */
public class MyProduct {
    private  String name;
    private  String itemNumber;
    private  BigInteger quantity;
    private  BigDecimal pricePerUnit;
    private  String description;

    public MyProduct() {
    }

    public MyProduct(String name, String itemNumber, BigInteger quantity, BigDecimal pricePerUnit, String description) {
        this.name = name;
        this.itemNumber = itemNumber;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        return ObjectAnalyzer.genericToString(this);
    }

}
