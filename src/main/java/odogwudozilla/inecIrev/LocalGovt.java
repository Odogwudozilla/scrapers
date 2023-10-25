package odogwudozilla.inecIrev;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public class LocalGovt extends State {

    private Integer lgaId;
    private String lgaName;
    private String lgaHashId;

    public static final List<ElectionWard> electionWards = new ArrayList<>();

    public Integer getLgaId() {
        return lgaId;
    }

    public void setLgaId(Integer lgaId) {
        this.lgaId = lgaId;
    }

    public String getLgaName() {
        return lgaName;
    }

    public void setLgaName(String lgaName) {
        this.lgaName = lgaName;
    }

    public String getLgaHashId() {
        return lgaHashId;
    }

    public void setLgaHashId(String lgaHashId) {
        this.lgaHashId = lgaHashId;
    }

    public void fillLocalGovtData(JsonNode lgaData) {

        setLgaId(lgaData.get("lga_id").asInt());
        setLgaName(lgaData.get("name").asText());
        setLgaHashId(lgaData.get("_id").asText());
    }
}
