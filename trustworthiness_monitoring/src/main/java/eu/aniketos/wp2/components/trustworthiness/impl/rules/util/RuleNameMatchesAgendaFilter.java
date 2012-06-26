package eu.aniketos.wp2.components.trustworthiness.impl.rules.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.drools.runtime.rule.Activation;
import org.drools.runtime.rule.AgendaFilter;

/**
 * filter for rule selection through regular expression
 * 
 * @author Hisain Elshaafi (TSSG)
 *
 */
public class RuleNameMatchesAgendaFilter implements AgendaFilter {
	private final Pattern pattern;

	private final boolean accept;

	/**
	 * @param regexp
	 */
	public RuleNameMatchesAgendaFilter(final String regexp) {

		this(regexp, true);
	}

	/**
	 * @param regexp
	 * @param accept
	 */
	public RuleNameMatchesAgendaFilter(final String regexp,

	final boolean accept) {

		this.pattern = Pattern.compile(regexp);

		this.accept = accept;
	}

	/* (non-Javadoc)
	 * @see org.drools.runtime.rule.AgendaFilter#accept(org.drools.runtime.rule.Activation)
	 */
	public boolean accept(final Activation activation) {

		Matcher matcher = pattern.matcher(activation.getRule().getName());

		if (matcher.matches()) {
			return this.accept;
		} else {
			return !this.accept;
		}
	}

}
