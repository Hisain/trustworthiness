package eu.aniketos.wp2.components.trustworthiness.rules.service;

import java.util.Collection;


/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface RuleExecuter {

	/**
	 * method to execute JBoss rules.
	 * parameters for rule selection
	 * @param iterator
	 * @param ruleNamePattern
	 * @param filterType
	 * @param filterOut
	 * @return
	 */
	Collection<?> execute(Collection<Object> iterator, String ruleNamePattern,
			final String filterType, String filterOut);

}

