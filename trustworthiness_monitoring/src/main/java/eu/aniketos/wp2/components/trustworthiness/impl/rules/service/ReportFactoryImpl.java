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
  public Message createMessage(Message.Type type, 
      String messageKey, Object... context) {
    return new MessageImpl(type, messageKey, Arrays
        .asList(context));
  }
  
  public ValidationReport createValidationReport() {
    return (ValidationReport) new ValidationReportImpl();
  }
}

