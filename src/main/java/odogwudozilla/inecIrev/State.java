package odogwudozilla.inecIrev;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public class State {

    public int getStateId() {
        return stateId;
    }

    private Integer stateId;
    private String stateName;
    private String stateHashId;

    public static final List<LocalGovt> localGovts = new ArrayList<>();

    public String getStateHashId() {
        return stateHashId;
    }

    public void setStateHashId(String stateHashId) {
        this.stateHashId = stateHashId;
    }


    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public void fillStateData(JsonNode loadedStateData) {

        JsonNode stateInfo = loadedStateData.path("data").get(0).path("state");
        setStateId(stateInfo.get("state_id").intValue());
        setStateName(stateInfo.get("name").asText());
        setStateHashId(stateInfo.get("_id").asText());
    }
}
