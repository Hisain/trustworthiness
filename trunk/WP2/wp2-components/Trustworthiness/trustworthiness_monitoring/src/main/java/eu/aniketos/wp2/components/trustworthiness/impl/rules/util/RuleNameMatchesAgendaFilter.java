package eu.aniketos.wp2.components.trustworthiness.impl.rules.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.drools.runtime.rule.Activation;
import org.drools.runtime.rule.AgendaFilter;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public class RuleNameMatchesAgendaFilter implements AgendaFilter {
	private final Pattern pattern;

	private final boolean accept;

	public RuleNameMatchesAgendaFilter(final String regexp) {

		this(regexp, true);
	}

	public RuleNameMatchesAgendaFilter(final String regexp,

	final boolean accept) {

		this.pattern = Pattern.compile(regexp);

		this.accept = accept;
	}

	public boolean accept(final Activation activation) {

		Matcher matcher = pattern.matcher(activation.getRule().getName());

		if (matcher.matches()) {
			return this.accept;
		} else {
			return !this.accept;
		}
	}

}
