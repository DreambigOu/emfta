package edu.uiuc.traceability.models;

import java.io.File;
import java.io.IOException;

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
}