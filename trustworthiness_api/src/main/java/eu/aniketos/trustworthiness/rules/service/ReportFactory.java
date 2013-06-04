/**
 * 
 */
package eu.aniketos.trustworthiness.rules.service;

/**
 * @author Hisain Elshaafi (TSSG)
 * interface for Drools reports
 */
public interface ReportFactory {

	/**
	 * @return Drools validation results report
	 */
	ValidationReport createValidationReport();

	/**
	 * @param type message type
	 * @param messageKey key of the message
	 * @param context
	 * @return
	 */
	Message createMessage(Message.Type type, String messageKey,
			Object... context);

}
