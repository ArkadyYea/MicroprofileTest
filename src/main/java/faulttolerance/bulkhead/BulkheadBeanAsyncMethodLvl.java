package faulttolerance.bulkhead;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.Fallback;

@ApplicationScoped
public class BulkheadBeanAsyncMethodLvl {
    
	private long SLEEP = 3000;
    
    @Asynchronous
    @Bulkhead(value = 2, waitingTaskQueue = 2)
    public Future<String> testMmethod() {
        try {
            Thread.sleep(SLEEP);
        } catch (InterruptedException ex) {
            System.out.println("Exception!: " + ex.getMessage());
        }
        
        return CompletableFuture.completedFuture("Future Finished, thread: " + Thread.currentThread().getName());
    }
    
    
    @Asynchronous
    @Bulkhead(value = 2, waitingTaskQueue = 2)
    @Fallback(fallbackMethod="failed")
    public Future<String> testMmethodFallback() {
        try {
            Thread.sleep(SLEEP);
        } catch (InterruptedException ex) {
            System.out.println("Exception!: " + ex.getMessage());
        }
        
        return CompletableFuture.completedFuture("Future Finished, thread: " + Thread.currentThread().getName());
    }
	
	public Future<String> failed() {
		//System.out.println("--- Future Error,  Fallback Method Called, thread: " + Thread.currentThread().getName());
		return CompletableFuture.completedFuture("--- Future Error,  Fallback Method Called, thread: " + Thread.currentThread().getName());
	}
}