package edu.uiuc.traceability.statecharts;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import Y2U.DataStructure.Model;
import Y2U.DataStructure.State;
import Y2U.ReadWrite.YakinduReader;
import edu.uiuc.traceability.artifacts.AutomataArtifact;


public class StatechartReader {

	private String filePath = "";
	private Model model;
	private YakinduReader reader;

	private Document yakinduDoc;
	private Element rootEle;

	public StatechartReader(String filePath) {
		this.filePath = filePath;

		model = new Model();
		reader = new YakinduReader(filePath, model);

	}

	public void setStatechartFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void read() {

		yakinduDoc = reader.read();
		rootEle = yakinduDoc.getDocumentElement();
	}

	public List<Entry<String, AutomataArtifact>> getAutomataArtifacts() {

		List<Entry<String, AutomataArtifact>> result = new ArrayList<>();

		// transform automata
		NodeList automataEleList = rootEle.getElementsByTagName("regions");
		if (automataEleList != null) {

			for (int i = 0; i < automataEleList.getLength(); i++) {

				AutomataArtifact automata = new AutomataArtifact();

				Element automataEle = (Element) automataEleList.item(i);

				automata.setOriginIdentifier(automataEle.getAttribute("xmi:id"));
				automata.setOriginName(automataEle.getAttribute("name"));
				automata.setFilePath(this.filePath);

				// Same time format as is in reqif format
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
				automata.setOriginLastChange(sdf.format(new File(this.filePath).lastModified()));

				System.out.println(automata.toString());

				Entry<String, AutomataArtifact> entry = new SimpleEntry<String, AutomataArtifact>(
						automata.getOriginIdentifier(), automata);
				result.add(entry);
			}
		}

		return result;
	}

	public List<Entry<String, State>> getStates() {

		List<Entry<String, State>> result = new ArrayList<>();


		//transform automata
		NodeList automataEleList = rootEle.getElementsByTagName("regions");
		if(automataEleList != null) {

			for(int i = 0; i < automataEleList.getLength(); i++) {

				Element automataEle = (Element)automataEleList.item(i);
//						System.out.println(automataEle.getAttribute("name").trim());
				NodeList stateEleList = automataEle.getElementsByTagName("vertices");

				if(stateEleList != null) {

					for(int j = 0; j < stateEleList.getLength(); j++) {
						Element stateEle = null;
						String stateName = "";
						String stateID = "";
						String stateType = "";

						// transform states
						stateEle = (Element)stateEleList.item(j);
						stateType = stateEle.getAttribute("xsi:type").trim();
						stateID = stateEle.getAttribute("xmi:id").trim();
						// stateName = stateEle.getAttribute("name").trim();

						State state = new State();

						// initial state
						if(stateType.equals("sgraph:Entry"))
						{
//							// stateName = "init_" + automata.getName();
//							stateName = "";
//							stateName = stateName.replace(' ', '_');
//							stateName = stateName.replace('.', '_');
//							stateName = stateName.replace('-', '_');
//							state.setName(stateName);

							// Disable for UPPAAL
							// set initial state
							// automata.setInitialStateID(stateID);
							continue;
						}
						//simple state
						else if(stateType.equals("sgraph:State"))
						{
							//stateName = stateEle.getAttribute("name").trim() + "_" + automata.getName();
							stateName = stateEle.getAttribute("name").trim();
							state.setName(stateName);
							state.setId(stateID);

							Entry<String, State> entry = new SimpleEntry<String, State>(
									automataEle.getAttribute("name"), state);
							result.add(entry);
						}
					}

				}
			}
		}

		return result;
	}

	// @return State : the state with associated with the id
	public State getStateByID(String id) {

		return null;
	}
}
