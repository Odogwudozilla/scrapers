package odogwudozilla.inecIrev;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public class ElectionWard extends LocalGovt {

    private Integer electionWardId;
    private String electionWardName;
    private String electionWardHashId;

    public static final List<PollingUnit> pollingUnits = new ArrayList<>();

    public Integer getElectionWardId() {
        return electionWardId;
    }

    public void setElectionWardId(Integer electionWardId) {
        this.electionWardId = electionWardId;
    }

    public String getElectionWardName() {
        return electionWardName;
    }

    public void setElectionWardName(String electionWardName) {
        this.electionWardName = electionWardName;
    }

    public String getElectionWardHashId() {
        return electionWardHashId;
    }

    public void setElectionWardHashId(String electionWardHashId) {
        this.electionWardHashId = electionWardHashId;
    }

    public void fillElectionWardData(JsonNode wardData) {

        setElectionWardId(wardData.get("ward_id").asInt());
        setElectionWardName(wardData.get("name").asText());
        setElectionWardHashId(wardData.get("_id").asText());

    }
}
