package edu.uiuc.traceability.io;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.uiuc.traceability.artifacts.AutomataArtifact;
import edu.uiuc.traceability.artifacts.EventArtifact;
import edu.uiuc.traceability.artifacts.EventType;
import edu.uiuc.traceability.artifacts.RequirementArtifact;
import edu.uiuc.traceability.artifacts.RequirementType;
import edu.uiuc.traceability.defaults.AutomataFields;
import edu.uiuc.traceability.defaults.EventFields;
import edu.uiuc.traceability.defaults.RequirementFields;
import edu.uiuc.traceability.defaults.TraceabilityConfigs;
import edu.uiuc.traceability.defaults.TraceabilityConst;
import edu.uiuc.traceability.defaults.TraceabilityGraphFields;
import edu.uiuc.traceability.models.BasicEventToAutomataTraceLink;
import edu.uiuc.traceability.models.RootEventToRequirementTraceLink;
import edu.uiuc.traceability.models.TraceabilityGraph;

public class TraceabilityGraphIO {

	private String traceFilePath = TraceabilityConfigs.DEFAULT_TRACEABILITY_GRAPH_PATH;
	private TraceabilityGraph tg = null;
	private boolean isRead;

	public TraceabilityGraphIO(String traceFilePath) {

		this.traceFilePath = traceFilePath;
		tg = new TraceabilityGraph();

	}

	public TraceabilityGraphIO() {
		tg = new TraceabilityGraph();
	}

	public void read() {

		if (this.traceFilePath != "") {

			Document doc = null;
			try {
				// DOM parser instance
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				// parse an XML file into a DOM tree
				System.out.println("[traceFilePath]: " + traceFilePath);
				doc = docBuilder.parse(new File(traceFilePath));
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (doc == null || doc.getTextContent() == "") {
				return;
			}

			Element rootEle = doc.getDocumentElement();

			// --- rootEventToRequiremenTraceLinks----
			NodeList rootEventToRequiremenTraceLinksList = rootEle
					.getElementsByTagName(TraceabilityGraphFields.RootEventToRequirementTraceLinks.name());

			System.out.println(
					"[rootEventToRequiremenTraceLinksList]: " + rootEventToRequiremenTraceLinksList.getLength());

			if (rootEventToRequiremenTraceLinksList != null && rootEventToRequiremenTraceLinksList.getLength() > 0) {

				Map<String, RootEventToRequirementTraceLink> mapRootEventToRequirement = new HashMap<>();

				TraceabilityGraph.setMapRootEventToRequirement(mapRootEventToRequirement);

				Element elementRootEventToRequiremenTraceLinks = (Element) rootEventToRequiremenTraceLinksList.item(0);
				NodeList traceList = elementRootEventToRequiremenTraceLinks
						.getElementsByTagName(TraceabilityConst.Trace);

				System.out.println("[traceList]: " + traceList.getLength());

				for (int i = 0; i < traceList.getLength(); i++) {

					RootEventToRequirementTraceLink traceToBeInserted = new RootEventToRequirementTraceLink();
					EventArtifact src = new EventArtifact();
					RequirementArtifact dst = new RequirementArtifact();

					Element traceElement = (Element) traceList.item(i);

					traceToBeInserted.setId(traceElement.getAttribute(TraceabilityConst.TraceId));

					mapRootEventToRequirement.put(traceToBeInserted.getId(), traceToBeInserted);

					NodeList eventElements = traceElement.getElementsByTagName(TraceabilityConst.RootEvent);
					NodeList reqElements = traceElement.getElementsByTagName(TraceabilityConst.Requirement);

					if (eventElements != null && reqElements != null) {

						// assume there is only one RootEvent and one Requirement
						Element eventElement = (Element) eventElements.item(0);

						src.setUuid(eventElement.getAttribute(EventFields.EventId.name()));
						src.setEventType(EventType.valueOf(eventElement.getAttribute(EventFields.EventType.name())));
						src.setEventUri(eventElement.getAttribute(EventFields.EventUri.name()));
						src.setFilePath(eventElement.getAttribute(EventFields.FilePath.name()));
						src.setName(eventElement.getAttribute(EventFields.Name.name()));
						src.setSafeGuard(Boolean.valueOf(eventElement.getAttribute(EventFields.SafeGuard.name())));
						traceToBeInserted.setSrc(src);

						Element reqElement = (Element) reqElements.item(0);

						dst.setFilePath(reqElement.getAttribute(RequirementFields.FilePath.name()));
						dst.setOriginDescription(reqElement.getAttribute(RequirementFields.OriginDescription.name()));
						dst.setOriginIdentifier(reqElement.getAttribute(RequirementFields.OriginIdentifier.name()));
						dst.setOriginLastChange(reqElement.getAttribute(RequirementFields.OriginLastChange.name()));
						dst.setReqType(
								RequirementType.valueOf(reqElement.getAttribute(RequirementFields.ReqType.name())));
						traceToBeInserted.setDst(dst);
					}
				}
			}

			// --- End rootEventToRequiremenTraceLinks----

			// basicEventToAutomataTraceLinks

			NodeList basicEventToAutomataTraceLinksList = rootEle
					.getElementsByTagName(TraceabilityGraphFields.BasicEventToAutomataTraceLinks.name());

			System.out
			.println("[basicEventToAutomataTraceLinksList]: " + basicEventToAutomataTraceLinksList.getLength());

			if (basicEventToAutomataTraceLinksList != null && basicEventToAutomataTraceLinksList.getLength() > 0) {

				Map<String, BasicEventToAutomataTraceLink> mapBasicEventToAutomata = new HashMap<>();

				TraceabilityGraph.setMapBasicEventToAutomata(mapBasicEventToAutomata);

				Element elementBasicEventToAutomataTraceLinks = (Element) basicEventToAutomataTraceLinksList.item(0);
				NodeList traceList = elementBasicEventToAutomataTraceLinks
						.getElementsByTagName(TraceabilityConst.Trace);

				System.out.println("[traceList]: " + traceList.getLength());

				for (int i = 0; i < traceList.getLength(); i++) {

					BasicEventToAutomataTraceLink traceToBeInserted = new BasicEventToAutomataTraceLink();
					EventArtifact src = new EventArtifact();
					AutomataArtifact dst = new AutomataArtifact();

					Element traceElement = (Element) traceList.item(i);

					traceToBeInserted.setId(traceElement.getAttribute(TraceabilityConst.TraceId));

					mapBasicEventToAutomata.put(traceToBeInserted.getId(), traceToBeInserted);

					NodeList eventElements = traceElement.getElementsByTagName(TraceabilityConst.BasicEvent);
					NodeList reqElements = traceElement.getElementsByTagName(TraceabilityConst.Automata);

					if (eventElements != null && reqElements != null) {

						Element eventElement = (Element) eventElements.item(0);

						src.setUuid(eventElement.getAttribute(EventFields.EventId.name()));
						src.setEventType(EventType.valueOf(eventElement.getAttribute(EventFields.EventType.name())));
						src.setEventUri(eventElement.getAttribute(EventFields.EventUri.name()));
						src.setFilePath(eventElement.getAttribute(EventFields.FilePath.name()));
						src.setName(eventElement.getAttribute(EventFields.Name.name()));
						src.setSafeGuard(Boolean.valueOf(eventElement.getAttribute(EventFields.SafeGuard.name())));
						traceToBeInserted.setSrc(src);

						Element reqElement = (Element) reqElements.item(0);

						dst.setFilePath(reqElement.getAttribute(AutomataFields.FilePath.name()));
						dst.setOriginIdentifier(reqElement.getAttribute(AutomataFields.OriginIdentifier.name()));
						dst.setOriginLastChange(reqElement.getAttribute(AutomataFields.OriginLastChange.name()));
						dst.setOriginName(reqElement.getAttribute(AutomataFields.OriginName.name()));
						traceToBeInserted.setDst(dst);
					}
				}
			}

			isRead = true;

			// --- read the traceability graph file (*.xml) ---
//			File xmlFile = new File(traceFilePath);
//
//			XStreamTranslator xStreamInstance = XStreamTranslator.getInstance();
//
//			try {
//				tg = (TraceabilityGraph) xStreamInstance.toObject(xmlFile);
//				// --- end read ---
//				isRead = true;
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

		}
	}

	public boolean isRead() {
		return isRead;
	}

	public TraceabilityGraph getTraceabilityGraph() {
		return tg;
	}

	public void write() {

//		XStreamTranslator xStreamInstance = XStreamTranslator.getInstance();
//
//		String xml = xStreamInstance.toXMLString(tg);
//		System.out.println("XML");
//		System.out.println(xml);
//
//		try {
//			System.out.println(System.getProperty("user.dir") + Traceability.DEFAULT_TRACEABILITY_GRAPH_PATH);
//			System.out.println(xStreamInstance.toXMLString(tg));
//			xStreamInstance.toXMLFile(tg,
//					System.getProperty("user.dir") + Traceability.DEFAULT_TRACEABILITY_GRAPH_PATH);
//
//			xStreamInstance.toXMLFile(tg, Traceability.DEFAULT_TRACEABILITY_GRAPH_PATH);
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		try {
			TraceabilityWriter.toXmlFile(tg, TraceabilityConfigs.DEFAULT_TRACEABILITY_GRAPH_PATH);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}