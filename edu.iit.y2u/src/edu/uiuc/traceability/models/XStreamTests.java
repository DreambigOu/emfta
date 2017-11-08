package edu.uiuc.traceability.models;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import edu.uiuc.traceability.artifacts.AutomataArtifact;

public class XStreamTests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AutomataArtifact regionArtifact;
		XStreamTranslator xStreamTranslator;

		xStreamTranslator = XStreamTranslator.getInstance();
		regionArtifact = new AutomataArtifact();

		regionArtifact.setFilePath("/test/file/path");
		regionArtifact.setOriginIdentifier("001");
		regionArtifact.setOriginName("region1");
		regionArtifact.setOriginLastChange("2017-10-27T23:07:09.320-05:00");
//		assertEquals("/test/file/path", regionArtifact.getFilePath());

		String xml = xStreamTranslator.toXMLString(regionArtifact);
		System.out.println(xml);

		TraceabilityGraph tg = TraceabilityGraph.getInstance();

		tg.addBasicEventToAutomataTraceLink(new BasicEventToAutomataTraceLink());
		tg.addRootEventToRequirementTraceLink(new RootEventToRequirementTraceLink());

		System.out.println(xStreamTranslator.toXMLString(tg));
		System.out.println(xStreamTranslator.toXMLString(TraceabilityGraph.getMapBasicEventToAutomata()));
		System.out.println(xStreamTranslator.toXMLString(TraceabilityGraph.getMapRootEventToRequirement()));

		XMLEncoder encoder=null;
		try{
			encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("tg.tgx")));
		}catch(FileNotFoundException fileNotFound){
			System.out.println("ERROR: While Creating or Opening the File dvd.xml");
		}
		encoder.writeObject(TraceabilityGraph.getMapBasicEventToAutomata());
		encoder.close();

	}

}
