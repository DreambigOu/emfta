package analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.uiuc.traceability.defaults.TraceabilityConfigs;
import edu.uiuc.traceability.io.TraceabilityGraphIO;
import edu.uiuc.traceability.models.RootEventToRequirementTraceLink;
import edu.uiuc.traceability.models.TraceabilityGraph;
import edu.uiuc.traceability.reqif.parser.ReqifParser;

public class RequirementChangeAnalysis {

	private TraceabilityGraph tg;
	private Map<String, RootEventToRequirementTraceLink> mapRootEventToRequirement = null;
	private TraceabilityGraphIO tgIO;

	public RequirementChangeAnalysis() {

		tg = TraceabilityGraph.getInstance();
		tgIO = tg.getTraceabilityGraphReader(TraceabilityConfigs.DEFAULT_TRACEABILITY_GRAPH_PATH);
		tgIO.read();
		mapRootEventToRequirement = TraceabilityGraph.getMapRootEventToRequirement();

	}

	public RequirementChangeAnalysis(String traceFilePath) {

		tg = TraceabilityGraph.getInstance();
		tgIO = tg.getTraceabilityGraphReader(traceFilePath);
		tgIO.read();
		mapRootEventToRequirement = TraceabilityGraph.getMapRootEventToRequirement();

	}

	/**
	 * Perform requirement change impact analysis
	 * @param reqId - The "ReqId" field in an event in a trace link
	 * @return     - The list of all affected emfta file paths
	 */
	public List<String> getImpactedFaultTreeFilePaths(String reqId) {

		List<String> result = new ArrayList<>();

		for (Entry<String, RootEventToRequirementTraceLink> entry : mapRootEventToRequirement.entrySet()) {

			if (reqId == entry.getValue().getDst().getOriginIdentifier()) {

				result.add(entry.getValue().getSrc().getFilePath());
			}
		}
		return result;
	}

	/**
	 * Check if a requirement is updated in a reqif file
	 * @param reqId - The "ReqId" field in an event in a trace link
	 * @return Boolean
	 */
	public Boolean isRequirementUpdated(String reqId) {

		String reqifFilePath = "";
		String reqifLastChange = "";

		for (Entry<String, RootEventToRequirementTraceLink> entry : mapRootEventToRequirement.entrySet()) {

			if (reqId.equals(entry.getValue().getDst().getOriginIdentifier())) {

				reqifFilePath = entry.getValue().getDst().getFilePath();
				reqifLastChange = entry.getValue().getDst().getOriginLastChange();
				break;
			}
		}

		// read reqif file again to get the lastest LastChange
		ReqifParser reqParser = new ReqifParser(reqifFilePath);
		reqParser.parse();

		String newLastChange = reqParser.getLastChangeByID(reqId);

		return !reqifLastChange.equals(newLastChange);
	}



}
