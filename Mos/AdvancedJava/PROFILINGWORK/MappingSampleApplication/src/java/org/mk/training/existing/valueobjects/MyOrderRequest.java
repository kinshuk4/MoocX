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
public class MyOrderRequest {
    private String customerId;
    private String purchaseOrderNumber;
    private MyCreditCard card;
    private List<MyProduct> products;

    public MyOrderRequest() {
    }

    public MyOrderRequest(String customerId, String purchaseOrderNumber, MyCreditCard card, List<MyProduct> products) {
        this.customerId = customerId;
        this.purchaseOrderNumber = purchaseOrderNumber;
        this.card = card;
        this.products = products;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public MyCreditCard getCard() {
        return card;
    }

    public void setCard(MyCreditCard card) {
        this.card = card;
    }

    public List<MyProduct> getProducts() {
        return products;
    }

    public void setProducts(List<MyProduct> products) {
        this.products = products;
    }

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    @Override
    public String toString() {
        return ObjectAnalyzer.genericToString(this);
    }
    
}   
