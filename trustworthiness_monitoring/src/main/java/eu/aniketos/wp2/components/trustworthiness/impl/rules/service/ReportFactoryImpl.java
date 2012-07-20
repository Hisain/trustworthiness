/**
 * 
 */
package eu.aniketos.wp2.components.trustworthiness.impl.rules.service;

import java.util.Arrays;

import eu.aniketos.wp2.components.trustworthiness.rules.service.Message;
import eu.aniketos.wp2.components.trustworthiness.rules.service.ReportFactory;
import eu.aniketos.wp2.components.trustworthiness.rules.service.ValidationReport;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class ReportFactoryImpl implements ReportFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.wp2.components.trustworthiness.rules.service.ReportFactory
	 * #createMessage
	 * (eu.aniketos.wp2.components.trustworthiness.rules.service.Message.Type,
	 * java.lang.String, java.lang.Object[])
	 */
	public Message createMessage(Message.Type type, String messageKey,
			Object... context) {
		return new MessageImpl(type, messageKey, Arrays.asList(context));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.wp2.components.trustworthiness.rules.service.ReportFactory
	 * #createValidationReport()
	 */
	public ValidationReport createValidationReport() {
		return (ValidationReport) new ValidationReportImpl();
	}
}
