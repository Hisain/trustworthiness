package eu.aniketos.wp2.components.trustworthiness.trust.management;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Composite;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Service;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public interface TrustFactory {

	/**
	 * @param serviceId
	 *            String service id
	 * @return
	 */
	public abstract Atomic createService(String serviceId);

	/**
	 * @param serviceId
	 *            String service id
	 * @return
	 */
	public abstract Composite createComposite(String serviceId);

	/**
	 * @param service
	 *            new service object
	 * @return new Score object
	 */
	public abstract Score createScore(Service service);

}
