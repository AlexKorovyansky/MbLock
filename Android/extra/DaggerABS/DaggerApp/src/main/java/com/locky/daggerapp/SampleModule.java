package com.locky.daggerapp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by akorovyansky on 7/19/13.
 */
@Module(
        injects = MainActivity.class
)
public class SampleModule {

    @Provides String provideString(){
        return "HELLO";
    }
}
