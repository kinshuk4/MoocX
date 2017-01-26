/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mk.training.existing.valueobjects;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

/**
 *
 * @author veena mohit kumar
 */
public class MyOrderDetails {
    
    private  String salesOrganization;
    private  Date dateOfPurchase;
    private  String customerNumber;
    private  MyModeOfPayment paymentMode;
    private  String purchaseOrderNumber;

    public MyOrderDetails() {
        this.init();
    }

    public MyOrderDetails(String salesOrganization, Date dateOfPurchase, String customerNumber, MyModeOfPayment paymentMode, String purchaseOrderNumber) {
        this.init();
        this.salesOrganization = salesOrganization;
        this.dateOfPurchase = dateOfPurchase;
        this.customerNumber = customerNumber;
        this.paymentMode = paymentMode;
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public MyModeOfPayment getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(MyModeOfPayment paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public String getSalesOrganization() {
        return salesOrganization;
    }

    public void setSalesOrganization(String salesOrganization) {
        this.salesOrganization = salesOrganization;
    }
    public void setConvertedDateOfPurchase(String date){
        if(date!=null){
            try {
                this.dateOfPurchase=DatatypeFactory.newInstance().newXMLGregorianCalendar(date).toGregorianCalendar().getTime();
            } catch (DatatypeConfigurationException ex) {
                Logger.getLogger(MyCreditCard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public String getConvertedDateOfPurchase(){
        String xdate=null;
        try {
            GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
            gc.setTime(dateOfPurchase);
            xdate=DatatypeFactory.newInstance().newXMLGregorianCalendar(gc).toXMLFormat().toString();
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(MyCreditCard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return xdate;
    }
    private void init(){
        Calendar defaultdate=Calendar.getInstance();
        defaultdate.set(1900,Calendar.JANUARY,1);
        dateOfPurchase=defaultdate.getTime();
    }
}
