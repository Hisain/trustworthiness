package eu.aniketos.trustworthiness.impl.rules.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.drools.ObjectFilter;
import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.command.runtime.rule.FireAllRulesCommand;
import org.drools.command.runtime.rule.GetObjectsCommand;
import org.drools.runtime.ExecutionResults;
import org.drools.runtime.StatefulKnowledgeSession;

import eu.aniketos.trustworthiness.impl.rules.persistence.KnowledgeSessionLookup;
import eu.aniketos.trustworthiness.impl.rules.util.RuleNameEqualsAgendaFilter;
import eu.aniketos.trustworthiness.rules.service.ReportFactory;
import eu.aniketos.trustworthiness.rules.service.RuleExecuter;

/**
 * Session Bean implementation class RuleExecuterImpl
 * 
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class RuleExecuterImpl implements RuleExecuter {

	private static Logger logger = Logger.getLogger(RuleExecuterImpl.class);

	private ReportFactory reportFactory;

	private KnowledgeSessionLookup sessionLookup;

	/**
	 * Default constructor.
	 */
	public RuleExecuterImpl() {

	}

	/**
	 * creates multiple commands, calls session.execute and returns results back
	 */
	public Collection<?> execute(Collection<Object> objects,
			String ruleNamePattern, final String filterType, String filterOut) {

		StatefulKnowledgeSession session = sessionLookup.newSession();

		// ValidationReport validationReport = reportFactory
		// .createValidationReport();
		List<Command<?>> commands = new ArrayList<Command<?>>();
		// commands.add(CommandFactory.newSetGlobal("validationReport",
		// validationReport, true));
		commands.add(CommandFactory.newInsertElements(objects));
		// TODO: may use RuleNameMatchesAgendaFilter for rule selection
		commands.add(new FireAllRulesCommand(new RuleNameEqualsAgendaFilter(
				ruleNamePattern, true)));
		if (filterType != null && filterOut != null) {
			GetObjectsCommand getObjectsCommand = new GetObjectsCommand(
					new ObjectFilter() {
						@SuppressWarnings("rawtypes")
						public boolean accept(Object object) {
							return object instanceof Map
									&& ((Map) object).get("_type_").equals(
											filterType);
						}
					});
			getObjectsCommand.setOutIdentifier(filterOut);
			commands.add(getObjectsCommand);
		}

		ExecutionResults results = session.execute(CommandFactory
				.newBatchExecution(commands));

		if (logger.isDebugEnabled()) {
			logger.debug("attempted fire rule " + ruleNamePattern);
		}

		return (List<?>) results.getValue("score");
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return
	 */
	public ReportFactory getReportFactory() {
		return reportFactory;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param reportFactory
	 */
	public void setReportFactory(ReportFactory reportFactory) {
		this.reportFactory = reportFactory;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return KnowledgeSessionLookup object for new or existing session lookup
	 */
	public KnowledgeSessionLookup getSessionLookup() {
		return sessionLookup;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param sessionLookup
	 */
	public void setSessionLookup(KnowledgeSessionLookup sessionLookup) {
		this.sessionLookup = sessionLookup;
	}
}
