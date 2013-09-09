package eu.aniketos.trustworthiness.runtime.test.monitoring;

import eu.aniketos.notification.IAlert;
import eu.aniketos.notification.Notification;

import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * This Activator simply registers a service tracker to indicate its interest in
 * the AdderService which causes the service to get registered by the Listener
 * Hook. It is a workaround for the problem that the current ListenerHook is
 * incompatible with the Equinox DS implementation which doesn't specify a
 * filter when looking up a service. See also DOSGI-73.
 */
public class Activator implements BundleActivator {

	private static Logger logger = Logger.getLogger(Activator.class);

	private ServiceTracker tracker;

	public void start(BundleContext context) throws Exception {

		tracker = new ServiceTracker(context, IAlert.class.getName(), null);
		tracker.open();

		Thread.sleep(5000);
		
		IAlert alertService = (IAlert) tracker.getService();

		if (alertService != null) {
			logger.debug("retrieved alertService " + alertService
					+ "...sending alerts.. ");

			alertService.alert("https://www.chrispay.com/api/pay",
					Notification.CONTRACT_VIOLATION, "0.10");
			alertService.alert("https://www.chrispay.com/api/pay",
					Notification.CONTRACT_VIOLATION, "0.46");
			alertService.alert("https://www.chrispay.com/api/pay",
					Notification.CONTRACT_VIOLATION, "0.94");

			logger.debug("sent alerts.. ");
		} else {
			logger.debug("alertService is null");
		}
	}

	public void stop(BundleContext context) throws Exception {
		tracker.close();
	}
}
