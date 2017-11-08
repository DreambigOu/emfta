package edu.uiuc.traceability.models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import edu.uiuc.traceability.artifacts.AutomataArtifact;
import edu.uiuc.traceability.artifacts.EventArtifact;
import edu.uiuc.traceability.artifacts.RequirementArtifact;

// Use singleton design pattern
/** Exposes the public interface of the assignment. This uses the <i>Singleton</i> design pattern.
 * @see <a href="http://en.wikipedia.org/wiki/Singleton_pattern">Singleton</a> **/
public class TraceabilityGraph {

	private static Map<String, RootEventToRequirementTraceLink> mapRootEventToRequirement = null;
	private static Map<String, BasicEventToAutomataTraceLink> mapBasicEventToAutomata = null;

	private static TraceabilityGraph instance = null;

	public static synchronized TraceabilityGraph getInstance() {
		if (instance == null) {
			instance = new TraceabilityGraph();
			TraceabilityGraph.mapBasicEventToAutomata = new HashMap<String, BasicEventToAutomataTraceLink>();
			TraceabilityGraph.mapRootEventToRequirement = new HashMap<String, RootEventToRequirementTraceLink>();
		}
		return instance;
	}

	/**
	 * @return the mapRootEventToRequirement
	 */
	public static Map<String, RootEventToRequirementTraceLink> getMapRootEventToRequirement() {
		return mapRootEventToRequirement;
	}

	/**
	 * @param mapRootEventToRequirement the mapRootEventToRequirement to set
	 */
	public static void setMapRootEventToRequirement(
			Map<String, RootEventToRequirementTraceLink> mapRootEventToRequirement) {
		TraceabilityGraph.mapRootEventToRequirement = mapRootEventToRequirement;
	}

	/**
	 * @return the mapBasicEventToAutomata
	 */
	public static Map<String, BasicEventToAutomataTraceLink> getMapBasicEventToAutomata() {
		return mapBasicEventToAutomata;
	}

	/**
	 * @param mapBasicEventToAutomata the mapBasicEventToAutomata to set
	 */
	public static void setMapBasicEventToAutomata(Map<String, BasicEventToAutomataTraceLink> mapBasicEventToAutomata) {
		TraceabilityGraph.mapBasicEventToAutomata = mapBasicEventToAutomata;
	}

	public TraceabilityGraphReader getTraceabilityGraphReader(String traceFilPath) {

		return new TraceabilityGraphReader(traceFilPath);
	}

	public String addRootEventToRequirementTraceLink(RootEventToRequirementTraceLink traceLink) {

		String uuid = UUID.randomUUID().toString();
		TraceabilityGraph.mapRootEventToRequirement.put(uuid, traceLink);
		return uuid;
	}

	public String addRootEventToRequirementTraceLink(EventArtifact src, RequirementArtifact dst) {

		String uuid = UUID.randomUUID().toString();
		RootEventToRequirementTraceLink traceLink = new RootEventToRequirementTraceLink();
		traceLink.setDst(dst);
		traceLink.setSrc(src);
		traceLink.setId(uuid);
		TraceabilityGraph.mapRootEventToRequirement.put(uuid, traceLink);
		return uuid;
	}

	public RootEventToRequirementTraceLink getRootEventToRequirementTraceLink(String uuid) {

		if (TraceabilityGraph.mapRootEventToRequirement.containsKey(uuid)) {
			return TraceabilityGraph.mapRootEventToRequirement.get(uuid);
		}

		return null;
	}

	public String addBasicEventToAutomataTraceLink(BasicEventToAutomataTraceLink traceLink) {

		String uuid = UUID.randomUUID().toString();
		TraceabilityGraph.mapBasicEventToAutomata.put(uuid, traceLink);
		return uuid;
	}

	public String addBasicEventToAutomataTraceLink(EventArtifact src, AutomataArtifact dst) {

		String uuid = UUID.randomUUID().toString();
		BasicEventToAutomataTraceLink traceLink = new BasicEventToAutomataTraceLink();
		traceLink.setDst(dst);
		traceLink.setSrc(src);
		traceLink.setId(uuid);
		TraceabilityGraph.mapBasicEventToAutomata.put(uuid, traceLink);
		return uuid;
	}

	public BasicEventToAutomataTraceLink getBasicEventToAutomataTraceLink(String uuid) {

		if (TraceabilityGraph.mapBasicEventToAutomata.containsKey(uuid)) {
			return TraceabilityGraph.mapBasicEventToAutomata.get(uuid);
		}

		return null;
	}
}