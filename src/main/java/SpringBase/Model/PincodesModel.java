package SpringBase.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class PincodesModel {
    @JsonProperty("result")
    private List<PincodesHelperModel> result;

    public List<Integer> getData() {
        List<Integer> lIds = new ArrayList<Integer>();
        for(PincodesHelperModel t : result) {
            lIds.add(t.getId());
        }
        return lIds;
    }
    public List<PincodesHelperModel> getPincodesHelperModel() {
        return result;
    }
    public void arrangeDataForQueue() {

    }
}
