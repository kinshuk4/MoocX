/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mk.training.existing.service;

import javax.ejb.Local;
import org.mk.training.existing.valueobjects.MyOrderRequest;
import org.mk.training.existing.valueobjects.MyOrderResponse;
import org.mk.training.existing.valueobjects.MyPaymentException;

/**
 *
 * @author veena mohit kumar
 */
@Local
public interface MyEShoppingLocal {
    public MyOrderResponse processOrder(MyOrderRequest ordererequest)throws MyPaymentException;
}
