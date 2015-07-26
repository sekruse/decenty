package com.github.sekruse.decenty_spike;

import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Sebastian
 * @since 26.07.2015.
 */
public class ChatApp {

    public static void main(String[] args) throws IOException {
        PeerDiscovery peerDiscovery = null;
        try {
            peerDiscovery = new PeerDiscovery();
            peerDiscovery.publish();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = prompt(reader)) != null) {
                if (line.equals("list")) {
                    ServiceInfo[] serviceInfos = peerDiscovery.listServices();
                    System.out.println("Found " + serviceInfos.length + " services.");
                    for (ServiceInfo serviceInfo : serviceInfos) {
                        System.out.println(serviceInfo);
                    }
                } else if (line.equals("exit")) {
                    break;
                } else {
                    System.out.println("Unknown command.");
                }
            }


        } finally {
            if (peerDiscovery != null) {
                peerDiscovery.shutdown();
            }
        }
    }

    private static String prompt(BufferedReader reader) throws IOException {
        System.out.print("> ");
        return reader.readLine();
    }


}
