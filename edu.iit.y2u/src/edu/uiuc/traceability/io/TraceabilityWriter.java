package edu.uiuc.traceability.io;

import java.io.File;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.uiuc.traceability.artifacts.AutomataArtifact;
import edu.uiuc.traceability.artifacts.EventArtifact;
import edu.uiuc.traceability.models.BasicEventToAutomataTraceLink;
import edu.uiuc.traceability.models.RootEventToRequirementTraceLink;
import edu.uiuc.traceability.models.TraceabilityGraph;

public class TraceabilityWriter {

	public static void toXmlFile(TraceabilityGraph tg, String path) throws ParserConfigurationException, Exception {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("TraceabilityGraph");
		doc.appendChild(rootElement);

		Element rootEventToRequiremenTraceLinksElement = doc.createElement("RootEventToRequiremenTraceLinks");
		rootElement.appendChild(rootEventToRequiremenTraceLinksElement);

		for (Entry<String, RootEventToRequirementTraceLink> trace : TraceabilityGraph.getMapRootEventToRequirement()
				.entrySet()) {

			if (trace != null) {
				Element TraceElement = doc.createElement("Trace");
				rootEventToRequiremenTraceLinksElement.appendChild(TraceElement);

				Attr attrTraceId = doc.createAttribute("TraceId");
				attrTraceId.setValue(trace.getKey());
				TraceElement.setAttributeNode(attrTraceId);

				Element RootEventElement = doc.createElement("RootEvent");
				TraceElement.appendChild(RootEventElement);

				eventToXML(doc, RootEventElement, trace.getValue().getSrc());

				Element ReqElement = doc.createElement("Requirement");
				TraceElement.appendChild(ReqElement);

				requirementToXML(doc, ReqElement, trace);

				rootEventToRequiremenTraceLinksElement.appendChild(TraceElement);

			}

		}



		Element basicEventToStataechartTraceLinkElement = doc.createElement("BasicEventToAutomatatTraceLinks");
		rootElement.appendChild(basicEventToStataechartTraceLinkElement);

		for (Entry<String, BasicEventToAutomataTraceLink> trace : TraceabilityGraph.getMapBasicEventToAutomata()
				.entrySet()) {

			if (trace != null) {
				Element TraceElement = doc.createElement("Trace");
				basicEventToStataechartTraceLinkElement.appendChild(TraceElement);

				Attr attrTraceId = doc.createAttribute("TraceId");
				attrTraceId.setValue(trace.getKey());
				TraceElement.setAttributeNode(attrTraceId);

				Element BasicEventElement = doc.createElement("BasicEvent");
				TraceElement.appendChild(BasicEventElement);

				eventToXML(doc, BasicEventElement, trace.getValue().getSrc());

				Element AutomataElement = doc.createElement("Automata");
				TraceElement.appendChild(AutomataElement);

				automataToXML(doc, AutomataElement, trace.getValue().getDst());

				basicEventToStataechartTraceLinkElement.appendChild(TraceElement);
			}

		}

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(path));
		transformer.transform(source, result);

	}

	private static void automataToXML(Document doc, Element element,
			AutomataArtifact automataArtifact) {

		Attr attrOriginIdentifier = doc.createAttribute("OriginIdentifier");
		attrOriginIdentifier.setValue(automataArtifact.getOriginIdentifier());
		element.setAttributeNode(attrOriginIdentifier);

		Attr attrFilePathReq = doc.createAttribute("FilePath");
		attrFilePathReq.setValue(automataArtifact.getFilePath());
		element.setAttributeNode(attrFilePathReq);

		Attr attrOriginLastChange = doc.createAttribute("OriginLastChange");
		attrOriginLastChange.setValue(automataArtifact.getOriginLastChange());
		element.setAttributeNode(attrOriginLastChange);

		Attr attrReqType = doc.createAttribute("OriginName");
		attrReqType.setValue(automataArtifact.getOriginName());
		element.setAttributeNode(attrReqType);
	}

	public static void eventToXML(Document doc, Element RootEventElement,
			EventArtifact eventArtifact) {


		Attr attrReqId = doc.createAttribute("EventId");
		attrReqId.setValue(eventArtifact.getUuid());
		RootEventElement.setAttributeNode(attrReqId);

		Attr attrEventUri = doc.createAttribute("EventUri");
		attrEventUri.setValue(eventArtifact.getEventUri());
		RootEventElement.setAttributeNode(attrEventUri);

		Attr attrFilePath = doc.createAttribute("FilePath");
		attrFilePath.setValue(eventArtifact.getFilePath());
		RootEventElement.setAttributeNode(attrFilePath);

		Attr attrName = doc.createAttribute("Name");
		attrName.setValue(eventArtifact.getName());
		RootEventElement.setAttributeNode(attrName);

		Attr attrEventType = doc.createAttribute("EventType");
		attrEventType.setValue(eventArtifact.getEventType().name());
		RootEventElement.setAttributeNode(attrEventType);

		Attr attrSafeGuard = doc.createAttribute("SafeGuard");
		attrSafeGuard.setValue(eventArtifact.getSafeGuard().toString());
		RootEventElement.setAttributeNode(attrSafeGuard);
	}

	public static void requirementToXML(Document doc, Element element,
			Entry<String, RootEventToRequirementTraceLink> trace) {

		Attr attrOriginIdentifier = doc.createAttribute("OriginIdentifier");
		attrOriginIdentifier.setValue(trace.getValue().getDst().getOriginIdentifier());
		element.setAttributeNode(attrOriginIdentifier);

		Attr attrOriginDescription = doc.createAttribute("OriginDescription");
		attrOriginDescription.setValue(trace.getValue().getDst().getOriginDescription());
		element.setAttributeNode(attrOriginDescription);

		Attr attrFilePathReq = doc.createAttribute("FilePath");
		attrFilePathReq.setValue(trace.getValue().getDst().getFilePath());
		element.setAttributeNode(attrFilePathReq);

		Attr attrOriginLastChange = doc.createAttribute("OriginLastChange");
		attrOriginLastChange.setValue(trace.getValue().getDst().getOriginLastChange());
		element.setAttributeNode(attrOriginLastChange);

		Attr attrReqType = doc.createAttribute("ReqType");
		attrReqType.setValue(trace.getValue().getDst().getReqType().name());
		element.setAttributeNode(attrReqType);
	}

}
