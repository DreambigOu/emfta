package edu.uiuc.traceability.models;

import java.io.IOException;

public class TraceabilityGraphWriter {

	private TraceabilityGraph tg = null;

	public TraceabilityGraphWriter() {
		super();
		tg = new TraceabilityGraph();
	}

	public void write() {

		XStreamTranslator xStreamInstance = XStreamTranslator.getInstance();

		// createFile
		try {
			xStreamInstance.toXMLFile(tg, "traceabilityGraph" + ".xml");

			System.out.println("[Export traceability graph to file] : Done");
		} catch (IOException e) {
			System.out.println("[Export traceability graph to file] : Fail");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// @todo to existing file
	}
}
