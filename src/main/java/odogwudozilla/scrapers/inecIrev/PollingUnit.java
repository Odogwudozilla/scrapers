package odogwudozilla.scrapers.inecIrev;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.databind.JsonNode;
import odogwudozilla.scrapers.inecIrev.ElectionWard;

public class PollingUnit extends ElectionWard {

    private Integer pollingUnitId;
    private String pollingUnitName;
    private String pollingUnitCode;
    private String pollingUnitHashId;

    public List<Pair<String, String>> puDocumentList = new ArrayList<>();


    public Integer getPollingUnitId() {
        return pollingUnitId;
    }

    public void setPollingUnitId(Integer pollingUnitId) {
        this.pollingUnitId = pollingUnitId;
    }

    public String getPollingUnitName() {
        return pollingUnitName;
    }

    public void setPollingUnitName(String pollingUniName) {
        this.pollingUnitName = pollingUniName;
    }

    public String getPollingUnitCode() {
        return pollingUnitCode;
    }

    public void setPollingUnitCode(String pollingUnitCode) {
        this.pollingUnitCode = pollingUnitCode;
    }

    public String getPollingUnitHashId() {
        return pollingUnitHashId;
    }

    public void setPollingUnitHashId(String pollingUnitHashId) {
        this.pollingUnitHashId = pollingUnitHashId;
    }

    public void fillPollingUnitData(JsonNode eachPuData) throws ParseException {
        setPollingUnitId(eachPuData.get("polling_unit").get("polling_unit_id").intValue());
        setPollingUnitName(eachPuData.get("polling_unit").get("name").asText());
        setPollingUnitCode(eachPuData.get("polling_unit").get("pu_code").asText());
        setPollingUnitHashId(eachPuData.get("polling_unit").get("_id").asText());

        JsonNode mainDoc = eachPuData.get("document");
        JsonNode old_documents = eachPuData.get("old_documents");

        if (mainDoc != null) {
            puDocumentList.add(new MutablePair<>(mainDoc.get("updated_at").asText().substring(0, 10), mainDoc.get("url").asText()));
        }

        if (old_documents != null && old_documents.size() > 0) {
            for (JsonNode oldDoc : old_documents) {
                puDocumentList.add(new MutablePair<>(oldDoc.get("updated_at").asText().substring(0, 10), oldDoc.get("url").asText()));
            }

        }


    }

}
