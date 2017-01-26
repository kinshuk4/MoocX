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
public class WorkManagerDelegatingExecutor implements Executor {

    WorkManager workManager;
    Work work;

    public WorkManagerDelegatingExecutor(WorkManager workManager, Work work) {
        this.workManager = workManager;
        this.work = work;
    }

    public void execute(final Runnable command) {
        try {
            workManager.scheduleWork(new Work() {
                public void release() {
                    work.release();
                }
                public void run() {
                    System.out.println("TIMEOUT PROVIDED.................");
                    command.run();
                }
            },WorkManager.INDEFINITE,null,null);
        } catch (WorkException ex) {
            ex.printStackTrace();
        }
    }
}
