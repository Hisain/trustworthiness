package eu.aniketos.wp5.components.trustworthiness.impl;


import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import eu.aniketos.wp5.components.trustworthiness.TrustworthinessService;

public class Activator implements BundleActivator {
    private ServiceRegistration registration;

    public void start(BundleContext bc) throws Exception {
        Dictionary props = new Hashtable();

        props.put("service.exported.interfaces", "*");
        props.put("service.exported.configs", "org.apache.cxf.ws");
        props.put("org.apache.cxf.ws.address", "http://localhost:9090/trust");

        registration = bc.registerService(TrustworthinessService.class.getName(),
                                          new TrustworthinessServiceImpl(), props);
    }

    public void stop(BundleContext bc) throws Exception {
        registration.unregister();
    }
}
