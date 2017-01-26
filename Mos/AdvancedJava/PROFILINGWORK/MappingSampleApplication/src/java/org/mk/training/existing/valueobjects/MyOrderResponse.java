/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mk.training.existing.valueobjects;

import java.util.List;
import org.mk.training.util.ObjectAnalyzer;

/**
 *
 * @author veena mohit kumar
 */
public class MyOrderResponse {
    private  String orderKey;
    private  MyOrderDetails orderHeader;
    private  List<MyProduct> products;
    private  MyCreditCard creditCard;
    private  String description;

    public MyOrderResponse() {
    }

    public MyOrderResponse(String orderKey, MyOrderDetails orderHeader, List<MyProduct> products, MyCreditCard creditCard, String description) {
        this.orderKey = orderKey;
        this.orderHeader = orderHeader;
        this.products = products;
        this.creditCard = creditCard;
        this.description = description;
    }

    public MyCreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(MyCreditCard creditCard) {
        System.out.println("creditcard"+creditCard);
        this.creditCard = creditCard;
        System.out.println("this.creditcard"+this.creditCard);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MyOrderDetails getOrderHeader() {
        return orderHeader;
    }

    public void setOrderHeader(MyOrderDetails orderHeader) {
        this.orderHeader = orderHeader;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public List<MyProduct> getProducts() {
        return products;
    }

    public void setProducts(List<MyProduct> products) {
        this.products = products;
    }
    @Override
    public String toString() {
        return ObjectAnalyzer.genericToString(this);
    }


}
