package com.jy.small.training;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/*
 * created by taofu on 2019/5/13
 **/
public class MyIntentService  extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
