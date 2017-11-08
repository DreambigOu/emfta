package edu.uiuc.traceability.models;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import edu.uiuc.TraceabilityWriter;

public class TraceabilityGraphReader {

	private String traceFilePath;
	private TraceabilityGraph tg = null;
	private boolean isRead;

	public TraceabilityGraphReader(String traceFilePath) {

		this.traceFilePath = traceFilePath;
		tg = new TraceabilityGraph();

	}

	public void read() {

		if (this.traceFilePath != "") {
			// --- read the traceability graph file (*.xml) ---
			File xmlFile = new File(traceFilePath);

			XStreamTranslator xStreamInstance = XStreamTranslator.getInstance();

			try {
				tg = (TraceabilityGraph) xStreamInstance.toObject(xmlFile);
				// --- end read ---
				isRead = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			TraceabilityWriter.toXmlFile(tg, "/Users/YZOU/Bitbucket/osate2_oxygen/git/emfta/tg.tgx");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}