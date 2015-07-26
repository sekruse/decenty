package com.github.sekruse.decenty_spike;


import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sebastian
 * @since 26.07.2015.
 */
public class PeerDiscovery {

    public static String REMOTE_TYPE = "_simple-chat._tcp.local.";

    public static int PORT = 9093;

    private JmDNS jmDNS;

    private ServiceInfo localService;

    public PeerDiscovery() throws IOException {
        this.jmDNS = JmDNS.create();
        this.jmDNS.addServiceListener(REMOTE_TYPE, new ServiceEventPrinter());
    }

    public void publish() throws IOException {
        if (this.localService != null) {
            System.out.println("Cannot publish service, service already published.");
            return;
        }

        final Map<String, String> values = new HashMap<String, String>();
        values.put("status", "hello from " + jmDNS.getHostName());
        values.put("port", String.valueOf(PORT));
        this.localService = ServiceInfo.create(PeerDiscovery.REMOTE_TYPE, jmDNS.getHostName(), 1025, 0, 0, values);
        this.jmDNS.registerService(this.localService);

        System.out.println("\nRegistered Service as " + this.localService);
    }

    public void unpublish() throws IOException {
        if (this.localService == null) {
            System.out.println("No service to unpublish.");
            return;
        }

        this.jmDNS.unregisterService(this.localService);
    }

    public ServiceInfo[] listServices() {
        ServiceInfo[] serviceInfos = this.jmDNS.list(REMOTE_TYPE);
        return serviceInfos;
    }

    public void shutdown() {
        try {
            unpublish();
            this.jmDNS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ServiceEventPrinter implements ServiceListener {

        @Override
        public void serviceAdded(ServiceEvent event) {
            System.out.format("[%s] Service added (name: %s, type: %s)\n", this.getClass().getSimpleName(), event.getName(), event.getType());
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            System.out.format("[%s] Service added (name: %s, type: %s)\n", this.getClass().getSimpleName(), event.getName(), event.getType());
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            System.out.format("[%s] Service added (name: %s, type: %s)\n", this.getClass().getSimpleName(), event.getName(), event.getType());
        }
    }

}
