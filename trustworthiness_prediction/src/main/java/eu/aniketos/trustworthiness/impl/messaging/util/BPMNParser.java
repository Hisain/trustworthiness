package eu.aniketos.trustworthiness.impl.messaging.util;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Vector;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

/**
 * @author Francesco Malmignati, Selex Elsag
 * 
 */
public class BPMNParser {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BPMNParser.class);

	public static Document getDocument(String file) {

		SAXBuilder builder = new SAXBuilder();

		Document doc = null;

		try {
			InputStream is = new ByteArrayInputStream(file.getBytes("UTF-8"));
			doc = (Document) builder.build(is);

		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		} catch (JDOMException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}

		return doc;
	}

	public static void addLocationField(Document document, String taskId,
			String location) {

		Element definitions = document.getRootElement();

		Namespace n = definitions.getNamespace();

		Element process = definitions.getChild("process", n);

		@SuppressWarnings("unchecked")
		List<Element> listServiceTask = process.getChildren("serviceTask", n);

		for (Element serviceTask : listServiceTask) {
			if (serviceTask.getAttribute("id").getValue().equals(taskId)) {
				Element extensionElements = serviceTask.getChild(
						"extensionElements", n);

				Namespace ns = Namespace.getNamespace("activiti",
						"http://activiti.org/bpmn");
				Element field = new Element("field", ns);
				field.setAttribute("name", "location");
				Element string = new Element("string", ns);
				string.setText(location);
				field.addContent(string);

				if (extensionElements != null) {
					boolean newfield = true;
					@SuppressWarnings("unchecked")
					List<Element> listField = extensionElements.getChildren(
							"field", ns);
					for (Element testfield : listField) {
						if (testfield.getAttributeValue("name").equals(
								"location")) {
							testfield.getChild("string", ns).setText(location);
							newfield = false;
						}
					}
					if (newfield)
						extensionElements.addContent(field);
				}

				logger.debug("Location " + location + " added in ServiceTask "
						+ taskId);

			}
		}
	}

	public static void addProviderField(Document document, String taskId,
			String provider) {

		Element definitions = document.getRootElement();

		Namespace n = definitions.getNamespace();

		Element process = definitions.getChild("process", n);

		@SuppressWarnings("unchecked")
		List<Element> listServiceTask = process.getChildren("serviceTask", n);

		for (Element serviceTask : listServiceTask) {
			if (serviceTask.getAttribute("id").getValue().equals(taskId)) {
				Element extensionElements = serviceTask.getChild(
						"extensionElements", n);

				Namespace ns = Namespace.getNamespace("activiti",
						"http://activiti.org/bpmn");
				Element field = new Element("field", ns);
				field.setAttribute("name", "serviceProvider");
				Element string = new Element("string", ns);
				string.setText(provider);
				field.addContent(string);

				if (extensionElements != null) {
					extensionElements.addContent(field);
				}

				logger.debug("ServiceProvider " + provider
						+ " added in ServiceTask " + taskId);

			}
		}
	}

	public static void addProcessId(Document document, String processId) {

		Element definitions = document.getRootElement();

		Namespace n = definitions.getNamespace();
		Element process = definitions.getChild("process", n);
		process.getAttribute("id").setValue(processId);
		process.getAttribute("name").setValue(processId);

		Namespace n1 = definitions.getNamespace("bpmndi");
		Element BPMNDiagram = definitions.getChild("BPMNDiagram", n1);
		Element BPMNPlane = BPMNDiagram.getChild("BPMNPlane", n1);

		BPMNPlane.getAttribute("bpmnElement").setValue(processId);

	}

	public static String getProcessId(Document document) {

		Element definitions = document.getRootElement();

		Namespace n = definitions.getNamespace();
		Element process = definitions.getChild("process", n);
		String compositionPlanId = process.getAttribute("id").getValue();

		return compositionPlanId;
	}

	public static List<String> getServicesList(String file) {

		SAXBuilder builder = new SAXBuilder();

		Document doc = null;

		try {
			InputStream is = new ByteArrayInputStream(file.getBytes("UTF-8"));
			doc = (Document) builder.build(is);

		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		} catch (JDOMException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}

		Element definitions = doc.getRootElement();

		Namespace n = definitions.getNamespace();
		Element process = definitions.getChild("process", n);

		@SuppressWarnings("unchecked")
		List<Element> list = process.getChildren("serviceTask", n);

		List<String> serviceList = new Vector<String>();

		for (Element serviceTask : list) {
			Element extensionElements = serviceTask.getChild(
					"extensionElements", n);

			Namespace ns = Namespace.getNamespace("activiti",
					"http://activiti.org/bpmn");

			List<Element> listField = extensionElements
					.getChildren("field", ns);
			for (Element field : listField) {
				if (field.getAttributeValue("name").equals("id")) {
					String serviceId = field.getChild("string", ns).getValue();
					serviceList.add(serviceId);
				}

			}

		}

		return serviceList;
	}

	public static void addServiceId(Document document, String taskId,
			String serviceId) {

		Element definitions = document.getRootElement();

		Namespace n = definitions.getNamespace();

		Element process = definitions.getChild("process", n);

		@SuppressWarnings("unchecked")
		List<Element> listServiceTask = process.getChildren("serviceTask", n);

		for (Element serviceTask : listServiceTask) {
			if (serviceTask.getAttribute("id").getValue().equals(taskId)) {
				Element extensionElements = serviceTask.getChild(
						"extensionElements", n);

				Namespace ns = Namespace.getNamespace("activiti",
						"http://activiti.org/bpmn");
				Element field = new Element("field", ns);
				field.setAttribute("name", "id");
				Element string = new Element("string", ns);
				string.setText(serviceId);
				field.addContent(string);

				if (extensionElements != null) {
					boolean newfield = true;
					@SuppressWarnings("unchecked")
					List<Element> listField = extensionElements.getChildren(
							"field", ns);
					for (Element testfield : listField) {
						if (testfield.getAttributeValue("name").equals("id")) {
							testfield.getChild("string", ns).setText(serviceId);
							newfield = false;
						}
					}
					if (newfield)
						extensionElements.addContent(field);
				}

				logger.debug("ServiceId " + serviceId
						+ " added in ServiceTask " + taskId);

			}
		}
	}

	public static Element addPortTypeName(Document document, String portTypeName) {

		Element definitions = document.getRootElement();

		Namespace n1 = Namespace.getNamespace(portTypeName.toLowerCase(),
				"http://webservice.activiti.org/");
		definitions.addNamespaceDeclaration(n1);

		Namespace n = definitions.getNamespace();

		Element interfaceElement = new Element("interface", n);
		interfaceElement.setAttribute(new Attribute("name", portTypeName
				+ " Interface"));
		interfaceElement.setAttribute(new Attribute("implementationRef",
				portTypeName.toLowerCase() + ":" + portTypeName));
		definitions.addContent(interfaceElement);

		return interfaceElement;

	}

	public static void addOperation(Document document,
			Element interfaceElement, String portTypeName, String operation,
			List<String> listInputOutput) {

		Element definitions = document.getRootElement();
		Namespace n = definitions.getNamespace();

		Element operationElement = new Element("operation", n);
		operationElement.setAttribute(new Attribute("id", operation
				+ "Operation"));
		operationElement.setAttribute(new Attribute("name", operation
				+ " Operation"));
		operationElement.setAttribute(new Attribute("implementationRef",
				portTypeName.toLowerCase() + ":" + operation));
		interfaceElement.addContent(operationElement);

		Element inMessageRef = new Element("inMessageRef", n);
		inMessageRef.setText("tns:" + operation + "RequestMessage");
		Element outMessageRef = new Element("outMessageRef", n);
		outMessageRef.setText("tns:" + operation + "ResponseMessage");
		operationElement.addContent(inMessageRef);
		operationElement.addContent(outMessageRef);

		Element messageRequest = new Element("message");
		messageRequest.setAttribute(new Attribute("id", operation
				+ "RequestMessage"));
		messageRequest.setAttribute(new Attribute("itemRef", "tns:" + operation
				+ "RequestItem"));
		definitions.addContent(messageRequest);

		Element messageResponse = new Element("message");
		messageResponse.setAttribute(new Attribute("id", operation
				+ "ResponseMessage"));
		messageResponse.setAttribute(new Attribute("itemRef", "tns:"
				+ operation + "ResponseItem"));
		definitions.addContent(messageResponse);

		Element itemDefinitionRequest = new Element("itemDefinition");
		itemDefinitionRequest.setAttribute(new Attribute("id", operation
				+ "RequestItem"));
		itemDefinitionRequest.setAttribute(new Attribute("structureRef",
				portTypeName.toLowerCase() + ":" + listInputOutput.get(0)));
		definitions.addContent(itemDefinitionRequest);

		Element itemDefinitionResponse = new Element("itemDefinition");
		itemDefinitionResponse.setAttribute(new Attribute("id", operation
				+ "ResponseItem"));
		itemDefinitionResponse.setAttribute(new Attribute("structureRef",
				portTypeName.toLowerCase() + ":" + listInputOutput.get(1)));
		definitions.addContent(itemDefinitionResponse);
	}

	// extract service task IDs from the BPMN
	public static List<String> getServiceTaskList(String file) {

		SAXBuilder builder = new SAXBuilder();

		Document doc = null;

		try {
			InputStream is = new ByteArrayInputStream(file.getBytes("UTF-8"));
			doc = (Document) builder.build(is);

		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		} catch (JDOMException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}

		Element definitions = doc.getRootElement();

		Namespace n = definitions.getNamespace();
		Element process = definitions.getChild("process", n);

		@SuppressWarnings("unchecked")
		List<Element> list = process.getChildren("serviceTask", n);

		List<String> serviceTaskList = new Vector<String>();

		for (Element serviceTask : list) {
			String serviceTaskID = serviceTask.getAttribute("id").getValue();

			serviceTaskList.add(serviceTaskID);

		}

		return serviceTaskList;

	}

	public static String getServiceType(String file, String serviceTaskID) {
		SAXBuilder builder = new SAXBuilder();

		Document doc = null;
		String serviceType = null;

		try {
			InputStream is = new ByteArrayInputStream(file.getBytes("UTF-8"));
			doc = (Document) builder.build(is);

		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		} catch (JDOMException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}

		Element definitions = doc.getRootElement();

		Namespace n = definitions.getNamespace();
		Element process = definitions.getChild("process", n);

		@SuppressWarnings("unchecked")
		List<Element> list = process.getChildren("serviceTask", n);

		List<String> serviceList = new Vector<String>();

		for (Element serviceTask : list) {

			String taskID = serviceTask.getAttribute("id").getValue();
			if (taskID.equals(serviceTaskID)) {
				Element extensionElements = serviceTask.getChild(
						"extensionElements", n);

				Namespace ns = Namespace.getNamespace("activiti",
						"http://activiti.org/bpmn");

				List<Element> listField = extensionElements.getChildren(
						"field", ns);
				for (Element field : listField) {
					if (field.getAttributeValue("name").equals("type")) {
						serviceType = field.getChild("string", ns).getValue();

					}

				}
			}
		}

		return serviceType;
	}

	// extract Services from the BPMN
	public static List<String> getServices(String file) {

		SAXBuilder builder = new SAXBuilder();

		Document doc = null;

		try {
			InputStream is = new ByteArrayInputStream(file.getBytes("UTF-8"));
			doc = (Document) builder.build(is);

		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		} catch (JDOMException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}

		Element definitions = doc.getRootElement();

		Namespace n = definitions.getNamespace();
		Element process = definitions.getChild("process", n);

		@SuppressWarnings("unchecked")
		List<Element> list = process.getChildren("serviceTask", n);

		List<String> serviceList = new Vector<String>();

		for (Element serviceTask : list) {
			String serviceId = serviceTask.getAttributeValue("id");
			serviceList.add(serviceId);
		}

		return serviceList;
	}

	// extract Service from the BPMN based on serviceTask
	public static String getService(String file, String serviceTaskID) {

		SAXBuilder builder = new SAXBuilder();

		Document doc = null;

		try {
			InputStream is = new ByteArrayInputStream(file.getBytes("UTF-8"));
			doc = (Document) builder.build(is);

		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		} catch (JDOMException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}

		Element definitions = doc.getRootElement();

		Namespace n = definitions.getNamespace();
		Element process = definitions.getChild("process", n);

		@SuppressWarnings("unchecked")
		List<Element> list = process.getChildren("serviceTask", n);

		String serviceId = null;
		String serviceLocation = null;

		for (Element serviceTask : list) {

			String taskID = serviceTask.getAttribute("id").getValue();
			if (taskID.equals(serviceTaskID)) {
				Element extensionElements = serviceTask.getChild(
						"extensionElements", n);

				Namespace ns = Namespace.getNamespace("activiti",
						"http://activiti.org/bpmn");

				List<Element> listField = extensionElements.getChildren(
						"field", ns);
				for (Element field : listField) {
					if (field.getAttributeValue("name").equals("id")) {
						serviceId = field.getChild("string", ns).getValue();

					} else if (field.getAttributeValue("name").equals(
							"location")) {
						serviceLocation = field.getChild("string", ns)
								.getValue();

					}

				}
			}

		}

		return serviceId;
	}

}
