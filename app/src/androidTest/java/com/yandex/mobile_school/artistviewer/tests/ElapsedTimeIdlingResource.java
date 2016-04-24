package com.yandex.mobile_school.artistviewer.tests;

import android.support.test.espresso.IdlingResource;

/**
 * Created by Sovan on 24.04.2016.
 */
public class ElapsedTimeIdlingResource implements IdlingResource {
    private long startTime;
    private long waitingTime;
    private ResourceCallback resourceCallback;

    public ElapsedTimeIdlingResource(long waitingTime) {
        this.startTime = System.currentTimeMillis();
        this.waitingTime = waitingTime;
    }


    public String getName() {
        return ElapsedTimeIdlingResource.class.getName() + ":" + String.valueOf(waitingTime);
    }


    public boolean isIdleNow() {
        long elapsed = System.currentTimeMillis() - startTime;
        boolean idle = (elapsed >= waitingTime);
        if (idle) {
            resourceCallback.onTransitionToIdle();
        }
        return idle;
    }


    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }
}
