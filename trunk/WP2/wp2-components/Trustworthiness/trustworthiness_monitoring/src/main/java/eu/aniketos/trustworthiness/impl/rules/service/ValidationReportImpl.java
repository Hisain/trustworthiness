/**
 * Copyright (c) 2013, Waterford Institute of Technology
 * All rights reserved.

 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met
 *    - Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *    - Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *    - Neither the name of Waterford Institute of Technology nor the
 *      names of its contributors may be used to endorse or promote products
 *      derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL WATERFORD INSTITUTE OF TECHNOLOGY BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package eu.aniketos.trustworthiness.impl.rules.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import eu.aniketos.trustworthiness.rules.service.Message;
import eu.aniketos.trustworthiness.rules.service.ValidationReport;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.rules.service.ValidationReport#getMessages()
	 */
	public Set<Message> getMessages() {
		Set<Message> messagesAll = new HashSet<Message>();
		for (Collection<Message> messages : messagesMap.values()) {
			messagesAll.addAll(messages);
		}
		return messagesAll;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.rules.service.ValidationReport#getMessagesByType
	 * (eu.aniketos.trustworthiness.rules.service.Message.Type)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.rules.service.ValidationReport#contains(java
	 * .lang.String)
	 */
	public boolean contains(String messageKey) {
		for (Message message : getMessages()) {
			if (messageKey.equals(message.getMessageKey())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.trustworthiness.rules.service.ValidationReport#addMessage
	 * (eu.aniketos.trustworthiness.rules.service.Message)
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
