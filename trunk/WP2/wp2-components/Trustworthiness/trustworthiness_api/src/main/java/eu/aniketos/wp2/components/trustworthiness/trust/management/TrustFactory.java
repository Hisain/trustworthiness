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

	public abstract Atomic createService(String serviceName);
	
	public abstract Composite createComposite(String serviceName);

	public abstract Score createScore(Service service);

}
