package org.uiuc.reqif.parser;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.uiuc.reqif.models.SpecObjects;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


//import Y2U.DataStructure.Model;
//import Y2U.ReadWrite.YakinduReader;
//import Y2U.Transformer.ElementTransformer;
//import Y2U.Transformer.PositionSetter;
//import Y2U.Transformer.Synchronizer;

public class ReqifReader {

	private String reqifFilePath;

//	private String yakinduFilePath;
//	private Model model;
//	private YakinduReader reader;
//	private ElementTransformer elementTransformer;
//	private PositionSetter positionSetter;
	// private Synchronizer synchronizer;


	private Document reqifDoc;
//	private Element rootEle;

	private SpecObjects specObjects;

	public ReqifReader(String filePath) {
		super();
		this.reqifFilePath = filePath;
		reqifDoc = null;
	}

	public Document read() {

		specObjects = new SpecObjects();
		reqifDoc = parse();
		return reqifDoc;
	}

	private Document parse() {

		Document document = null;
		try {
			// DOM parser instance
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			// parse an XML file into a DOM tree
			System.out.println("[reqifFilePath]: " + reqifFilePath);
			document = docBuilder.parse(new File(reqifFilePath));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return document;
	}

	public String getReqifFilePath() {
		return reqifFilePath;
	}

	public void setReqifFilePath(String reqifFilePath) {
		this.reqifFilePath = reqifFilePath;
	}

	public Document getReqifDoc() {
		return reqifDoc;
	}

	public void setReqifDoc(Document reqifDoc) {
		this.reqifDoc = reqifDoc;
	}

	public SpecObjects getSpecObjects() {


		return specObjects;
	}

	// @return String : the requirement description
	public String getRequirmentDescriptionByID(String id) {

		return null;
	}

}