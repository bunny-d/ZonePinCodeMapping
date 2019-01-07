package SpringBase.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ZonesModel {
    @JsonProperty("polygon")
    public List<Test> polygon;

    @JsonProperty("zone")
    public List<Test> zone;

    public int getZoneDataSize() {
        return zone.size();
    }
    public String getSublistIds(List subList) {
        String zoneIds = "";
        for(Object t : subList) {
            zoneIds+=  ((Test)t).getId() + ",";
        }
        return zoneIds;
    }
}

class Test {
    @JsonProperty("name") String name;
    @JsonProperty("id") String id;

    public String getId() {
        return id;
    }
}