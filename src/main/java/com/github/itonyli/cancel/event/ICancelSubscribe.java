package com.github.itonyli.cancel.event;

import com.github.itonyli.cancel.CancelEventModel;

public interface ICancelSubscribe {

    void onEvent(CancelEventModel event);
}
