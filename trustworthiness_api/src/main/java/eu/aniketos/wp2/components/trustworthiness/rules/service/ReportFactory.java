/**
 * 
 */
package eu.aniketos.wp2.components.trustworthiness.rules.service;


/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface ReportFactory {
  ValidationReport createValidationReport();

  Message createMessage(Message.Type type, String messageKey, 
      Object... context);
}

