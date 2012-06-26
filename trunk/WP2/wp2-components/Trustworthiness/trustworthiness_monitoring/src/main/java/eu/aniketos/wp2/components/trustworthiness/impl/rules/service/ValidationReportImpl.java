/**
 * 
 */
package eu.aniketos.wp2.components.trustworthiness.impl.rules.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import eu.aniketos.wp2.components.trustworthiness.rules.service.Message;
import eu.aniketos.wp2.components.trustworthiness.rules.service.ValidationReport;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public class ValidationReportImpl implements ValidationReport, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6397725368292346892L;
	protected Map<Message.Type, Set<Message>> messagesMap = new HashMap<Message.Type, Set<Message>>();

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.rules.service.ValidationReport#getMessages()
	 */
	public Set<Message> getMessages() {
		Set<Message> messagesAll = new HashSet<Message>();
		for (Collection<Message> messages : messagesMap.values()) {
			messagesAll.addAll(messages);
		}
		return messagesAll;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.rules.service.ValidationReport#getMessagesByType(eu.aniketos.wp2.components.trustworthiness.rules.service.Message.Type)
	 */
	public Set<Message> getMessagesByType(Message.Type type) {
		if (type == null)
			return Collections.emptySet();
		Set<Message> messages = messagesMap.get(type);
		if (messages == null)
			return Collections.emptySet();
		else
			return messages;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.rules.service.ValidationReport#contains(java.lang.String)
	 */
	public boolean contains(String messageKey) {
		for (Message message : getMessages()) {
			if (messageKey.equals(message.getMessageKey())) {
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.rules.service.ValidationReport#addMessage(eu.aniketos.wp2.components.trustworthiness.rules.service.Message)
	 */
	public boolean addMessage(Message message) {
		if (message == null)
			return false;
		Set<Message> messages = messagesMap.get(message.getType());
		if (messages == null) {
			messages = new HashSet<Message>();
			messagesMap.put(message.getType(), messages);
		}
		return messages.add(message);
	}
}
// @extract-end
