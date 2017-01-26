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
public class MyCreditCard {
    private  String type;
    private  String number;
    private  Date expiryDate;
    private  String name;
    private  float amount;
    private  Date dateOfPurchase;

    public MyCreditCard() {
        this.init();
    }

    public MyCreditCard(String type, String number, Date expiryDate, String name, float amount, Date dateOfPurchase) {
        this.init();
        this.type = type;
        this.number = number;
        this.expiryDate = expiryDate;
        this.name = name;
        this.amount = amount;
        this.dateOfPurchase = dateOfPurchase;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void setConvertedExpirydate(String date){
        if(date!=null){
            try {
                this.expiryDate=DatatypeFactory.newInstance().newXMLGregorianCalendar(date).toGregorianCalendar().getTime();
            } catch (DatatypeConfigurationException ex) {
                Logger.getLogger(MyCreditCard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
    public String getConvertedExpirydate(){
        String xdate=null;
        try {
            GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
            gc.setTime(expiryDate);
            xdate=DatatypeFactory.newInstance().newXMLGregorianCalendar(gc).toXMLFormat().toString();
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(MyCreditCard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return xdate;
    }
    /*@Override
    public String toString() {
        return ObjectAnalyzer.genericToString(this);
    }*/
    private void init(){
        Calendar defaultdate=Calendar.getInstance();
        defaultdate.set(1900,Calendar.JANUARY,1);
        expiryDate=defaultdate.getTime();
        dateOfPurchase=defaultdate.getTime();
    }
}














