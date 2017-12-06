package com.github.itonyli.cancel.event;

import com.github.itonyli.cancel.CancelEventModel;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

public class InsuranceSubscribe implements ICancelSubscribe {

    @Override
    @Subscribe
    @AllowConcurrentEvents
    public void onEvent(CancelEventModel event) {
        System.out.println(Thread.currentThread().getName() + "->" + "Trigger InsuranceSubscribe!");
    }
}
