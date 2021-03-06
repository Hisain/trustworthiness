/**
 * Copyright (c) 2013, Waterford Institute of Technology
 * All rights reserved.

 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met
 *    - Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *    - Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
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

import java.util.Arrays;

import eu.aniketos.trustworthiness.rules.service.Message;
import eu.aniketos.trustworthiness.rules.service.ReportFactory;
import eu.aniketos.trustworthiness.rules.service.ValidationReport;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class ReportFactoryImpl implements ReportFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.trustworthiness.rules.service.ReportFactory
	 * #createMessage (eu.aniketos.trustworthiness.rules.service.Message.Type,
	 * java.lang.String, java.lang.Object[])
	 */
	public Message createMessage(Message.Type type, String messageKey,
			Object... context) {
		return new MessageImpl(type, messageKey, Arrays.asList(context));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.trustworthiness.rules.service.ReportFactory
	 * #createValidationReport()
	 */
	public ValidationReport createValidationReport() {
		return (ValidationReport) new ValidationReportImpl();
	}
}
