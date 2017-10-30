package org.uiuc.reqif.parser;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ReqifParser {

	private String reqifFilePath;
	private ReqifReader reader;
	private Document reqifDoc;
	private Element rootEle;

	public ReqifParser(String reqifFilePath) {

		this.reqifFilePath = reqifFilePath;
		reader = new ReqifReader(this.reqifFilePath);
	}

	public String getReqifFilePath() {
		return reqifFilePath;
	}

	public void setReqifFilePath(String reqifFilePath) {
		this.reqifFilePath = reqifFilePath;
	}

	public void parse() {

		reqifDoc = reader.read();
		rootEle = reqifDoc.getDocumentElement();
	}

	// ------ Requirement Example with the first value is the description of a requirement -------
//	<SPEC-OBJECTS>
//	  <SPEC-OBJECT IDENTIFIER="_d9fEyLuVEeeZCIaPue7aVg" LAST-CHANGE="2017-10-27T23:53:45.624-05:00">
//	    <VALUES>
//	      <ATTRIBUTE-VALUE-STRING THE-VALUE="The system shall have no hypoxia">
//	        <DEFINITION>
//	          <ATTRIBUTE-DEFINITION-STRING-REF>_d9fExbuVEeeZCIaPue7aVg</ATTRIBUTE-DEFINITION-STRING-REF>
//	        </DEFINITION>
//	      </ATTRIBUTE-VALUE-STRING>
//	    </VALUES>
//	    <TYPE>
//	      <SPEC-OBJECT-TYPE-REF>_d9fExLuVEeeZCIaPue7aVg</SPEC-OBJECT-TYPE-REF>
//	    </TYPE>
//	  </SPEC-OBJECT>
//	</SPEC-OBJECTS>
	// ------ End Example ---------

	public List<Entry<String, String>> getIdentifierAndRequirementList() {

		List<Entry<String, String>> result = new ArrayList<Entry<String, String>>();

		// transform automata
		NodeList specObjectsElementList = rootEle.getElementsByTagName("SPEC-OBJECTS");

		System.out.println(
				"[getIdentifierAndRequirementList().specObjectsElementList]" + specObjectsElementList.getLength());
		System.out.println(
				"[getIdentifierAndRequirementList().specObjectsElementList]" + specObjectsElementList.toString());

		if (specObjectsElementList != null) {

			for (int i = 0; i < specObjectsElementList.getLength(); i++) {

				Element specObjectsElement = (Element) specObjectsElementList.item(i);
//						System.out.println(automataEle.getAttribute("name").trim());
				NodeList specObjectElementList = specObjectsElement.getElementsByTagName("SPEC-OBJECT");
				System.out.println("[getIdentifierAndRequirementList().specObjectElementList.getLength()]"
						+ specObjectElementList.getLength());
				System.out.println("[getIdentifierAndRequirementList().specObjectElementList]" + specObjectElementList.toString());

				if (specObjectElementList != null) {

					for (int j = 0; j < specObjectElementList.getLength(); j++) {
						Element specObjectElement = null;
						String specObjectIdentifier = "";

						// transform a SpecObject
						specObjectElement = (Element) specObjectElementList.item(j);
						specObjectIdentifier = specObjectElement.getAttribute("IDENTIFIER").trim();
						System.out.println("[getIdentifierAndRequirementList().specObjectIdentifier]" + specObjectIdentifier);

						NodeList valuesElementList = specObjectsElement.getElementsByTagName("VALUES");

						if (valuesElementList != null) {

							NodeList attributeValueStringElementList = ((Element) valuesElementList.item(0))
									.getElementsByTagName("ATTRIBUTE-VALUE-STRING");

							Element attributeValueStringElementReqDescription = (Element) attributeValueStringElementList
									.item(0);

							String reqDescription = attributeValueStringElementReqDescription.getAttribute("THE-VALUE")
									.trim();

							System.out.println("[getIdentifierAndRequirementList().reqDescription]" + reqDescription);

							Entry<String, String> entry = new SimpleEntry(specObjectIdentifier, reqDescription);
							System.out.println("[getIdentifierAndRequirementList().entry]" + entry.toString());

							result.add(entry);
						}
					}
				}
			}
		}

		return result;
	}
}
