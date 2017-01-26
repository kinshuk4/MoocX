/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mk.training.existing.service;

import java.util.Date;
import java.util.Iterator;
import javax.ejb.Stateless;
import javax.jws.WebService;
import org.mk.training.existing.valueobjects.MyOrderDetails;
import org.mk.training.existing.valueobjects.MyOrderRequest;
import org.mk.training.existing.valueobjects.MyOrderResponse;
import org.mk.training.existing.valueobjects.MyPaymentException;
import org.mk.training.existing.valueobjects.MyModeOfPayment;
import org.mk.training.existing.valueobjects.MyProduct;
/**
 *
 * @author veena mohit kumar
 */
@Stateless
public class MyEShoppingBean implements MyEShoppingLocal {
    
    public MyOrderResponse processOrder(MyOrderRequest ordererequest)throws MyPaymentException{
        MyOrderResponse response = new MyOrderResponse();
        System.out.println("MyEShoppingBean.processOrder()");
        String ordId = Long.toString((new Date()).getTime());
        ordId = ordId.substring(ordId.length() - 10);
        response.setOrderKey(ordId);
        MyOrderDetails hdr = new MyOrderDetails();
        response.setOrderHeader(hdr);
        hdr.setCustomerNumber(ordererequest.getCustomerId());
        hdr.setDateOfPurchase(new Date());
        hdr.setSalesOrganization("MyEShoppingSite");
        if (ordererequest.getPurchaseOrderNumber() != null) {
            hdr.setPaymentMode(MyModeOfPayment.CREDITCARD);
            hdr.setPurchaseOrderNumber(ordererequest.getPurchaseOrderNumber());
        }
        if (ordererequest.getCard() == null) {
            throw new MyPaymentException("No payment info.");
        }
        if (ordererequest.getCard().getExpiryDate().compareTo(new Date()) < 0) {
            throw new MyPaymentException("Expired ccard.");
        }
        float billAmt = 0.0F;
        for (Iterator i$ = ordererequest.getProducts().iterator(); i$.hasNext();) {
            MyProduct item = (MyProduct) i$.next();
            billAmt += item.getPricePerUnit().floatValue() * (float) item.getQuantity().longValue();
        }

        ordererequest.getCard().setAmount(billAmt);
        response.setCreditCard(ordererequest.getCard());
        response.setProducts(ordererequest.getProducts());
        return response;
    }
 
}
