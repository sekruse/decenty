package com.github.sekruse.decenty_spike;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Sebastian
 * @since 26.07.2015.
 */
public class CommandLineHandler implements Runnable {

    private BufferedReader reader;

    @Override
    public void run() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
//        Thread.
    }
}
