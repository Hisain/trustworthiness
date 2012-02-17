package eu.aniketos.wp2.components.trustworthiness.rules.service;

import java.util.Collection;


/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface RuleExecuter {

	Collection<?> execute(Collection<Object> iterator, String ruleNamePattern,
			final String filterType, String filterOut);

}

