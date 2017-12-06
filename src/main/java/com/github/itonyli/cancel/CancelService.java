package com.github.itonyli.cancel;

import com.github.itonyli.cancel.event.InsuranceSubscribe;
import com.github.itonyli.cancel.event.LogSubscribe;
import com.github.itonyli.cancel.event.SmallNumberSubscribe;
import com.google.common.eventbus.AsyncEventBus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CancelService {
    private static ExecutorService executor = Executors.newFixedThreadPool(8);
    private AsyncEventBus asyncEventBus = new AsyncEventBus("cancel-eventbus", executor);
    private InsuranceSubscribe insuranceSubscribe = new InsuranceSubscribe();
    private LogSubscribe logSubscribe = new LogSubscribe();
    private SmallNumberSubscribe smallNumberSubscribe = new SmallNumberSubscribe();


    /**
     * @transaction
     * @param
     * @return
     */
    public boolean cancel() {
        // TODO do main within sync and transaction

        // ready data for cancelEventModel
        CancelEventModel event = new CancelEventModel.Builder(1L, "system", "job")
                .setInsuranceId(54188L)
                .build();

        // no servlet env can`t use @PostConstruct, so ...
        asyncEventBus.register(insuranceSubscribe);
        asyncEventBus.register(logSubscribe);
        asyncEventBus.register(smallNumberSubscribe);

        // do post event
        asyncEventBus.post(event);

        // return main result;
        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        CancelService cancelService = new CancelService();
        boolean result = cancelService.cancel();
        executor.shutdown();
        System.out.println(result);

        //TimeUnit.SECONDS.sleep(1);
    }

}
