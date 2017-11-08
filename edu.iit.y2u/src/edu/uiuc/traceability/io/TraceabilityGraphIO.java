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
import edu.uiuc.traceability.defaults.TraceabilityConfigs;
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

			NodeList rootEventToRequiremenTraceLinksList = rootEle
					.getElementsByTagName("RootEventToRequiremenTraceLinks");

			if (rootEventToRequiremenTraceLinksList != null) {

				Map<String, RootEventToRequirementTraceLink> mapRootEventToRequirement = new HashMap<>();

				TraceabilityGraph.setMapRootEventToRequirement(mapRootEventToRequirement);

				for(int i = 0; i < rootEventToRequiremenTraceLinksList.getLength(); i++) {

					RootEventToRequirementTraceLink traceToBeInserted = new RootEventToRequirementTraceLink();
					EventArtifact src = new EventArtifact();
					RequirementArtifact dst = new RequirementArtifact();

					Element traceElement = (Element) rootEventToRequiremenTraceLinksList.item(i);

					traceToBeInserted.setId(traceElement.getAttribute("TraceId"));

					mapRootEventToRequirement.put(traceToBeInserted.getId(), traceToBeInserted);

					NodeList eventElements = traceElement.getElementsByTagName("RootEvent");

					if (eventElements != null) {
						Element eventElement = (Element) eventElements.item(0);

						src.setUuid(eventElement.getAttribute("EventId"));
						src.setEventType(EventType.valueOf(eventElement.getAttribute("EventType")));
						src.setEventUri(eventElement.getAttribute("EventUri"));
						src.setFilePath(eventElement.getAttribute("FilePath"));
						src.setName(eventElement.getAttribute("Name"));
						src.setSafeGuard(Boolean.valueOf(eventElement.getAttribute("SafeGuard")));
						traceToBeInserted.setSrc(src);
					}

					NodeList reqElements = traceElement.getElementsByTagName("Requirement");

					if (reqElements != null) {
						Element reqElement = (Element) reqElements.item(0);

						dst.setFilePath(reqElement.getAttribute("FilePath"));
						dst.setOriginDescription(reqElement.getAttribute("OriginDescription"));
						dst.setOriginIdentifier(reqElement.getAttribute("OriginIdentifier"));
						dst.setOriginLastChange(reqElement.getAttribute("OriginLastChange"));
						dst.setReqType(RequirementType.valueOf(reqElement.getAttribute("ReqType")));
						traceToBeInserted.setDst(dst);
					}
				}
			}

			NodeList basicEventToAutomataTraceLinksList = rootEle
					.getElementsByTagName("BasicEventToAutomataTraceLinks");

			if (basicEventToAutomataTraceLinksList != null) {

				Map<String, BasicEventToAutomataTraceLink> mapBasicEventToAutomata = new HashMap<>();

				TraceabilityGraph.setMapBasicEventToAutomata(mapBasicEventToAutomata);

				for (int i = 0; i < basicEventToAutomataTraceLinksList.getLength(); i++) {

					BasicEventToAutomataTraceLink traceToBeInserted = new BasicEventToAutomataTraceLink();
					EventArtifact src = new EventArtifact();
					AutomataArtifact dst = new AutomataArtifact();

					Element traceElement = (Element) basicEventToAutomataTraceLinksList.item(i);

					traceToBeInserted.setId(traceElement.getAttribute("TraceId"));

					mapBasicEventToAutomata.put(traceToBeInserted.getId(), traceToBeInserted);

					NodeList eventElements = traceElement.getElementsByTagName("BasicEvent");

					if (eventElements != null) {
						Element eventElement = (Element) eventElements.item(0);

						src.setUuid(eventElement.getAttribute("EventId"));
						src.setEventType(EventType.valueOf(eventElement.getAttribute("EventType")));
						src.setEventUri(eventElement.getAttribute("EventUri"));
						src.setFilePath(eventElement.getAttribute("FilePath"));
						src.setName(eventElement.getAttribute("Name"));
						src.setSafeGuard(Boolean.valueOf(eventElement.getAttribute("SafeGuard")));
						traceToBeInserted.setSrc(src);
					}

					NodeList reqElements = traceElement.getElementsByTagName("Automata");

					if (reqElements != null) {
						Element reqElement = (Element) reqElements.item(0);

						dst.setFilePath(reqElement.getAttribute("FilePath"));
						dst.setOriginIdentifier(reqElement.getAttribute("OriginIdentifier"));
						dst.setOriginLastChange(reqElement.getAttribute("OriginLastChange"));
						dst.setOriginName(reqElement.getAttribute("OriginName"));
						traceToBeInserted.setDst(dst);
					}
				}
			}


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