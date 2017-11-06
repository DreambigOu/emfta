package edu.uiuc.traceability.models;

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
	}

}
