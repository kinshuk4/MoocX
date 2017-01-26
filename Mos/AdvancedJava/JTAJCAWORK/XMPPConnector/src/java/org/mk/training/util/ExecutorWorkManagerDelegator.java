/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mk.training.util;

import java.util.concurrent.Executor;
import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkManager;

/**
 *
 * @author veenamohitkumar
 */
public class ExecutorWorkManagerDelegator implements Executor {

    WorkManager workManager;
    
    public ExecutorWorkManagerDelegator(WorkManager workManager) {
        System.out.println("WorkManagerDelegatingExecutor:before::");
        this.workManager = workManager;
        System.out.println("WorkManagerDelegatingExecutor:after::");
    }

    public void execute(final Runnable command) {
        System.out.println("Delegating to Work Manager::::");
        try {
            workManager.scheduleWork((Work) command,WorkManager.INDEFINITE,null,null);
        } catch (WorkException ex) {
            ex.printStackTrace();
        }
    }
}
