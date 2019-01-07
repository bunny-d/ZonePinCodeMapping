package SpringBase.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PincodesHelperModel {
    @JsonProperty("id") Integer id;
    @JsonProperty("pincodes")
    List<Integer> pincodes;

    public Integer getId() {
        return this.id;
    }

    public List<Integer> getPincodes() {
        return this.pincodes;
    }
    public int getCountPinCodes() {
        return pincodes.size();
    }
    /*
    [{pincode=121000, delivery_id=584}, {pincode=121001, delivery_id=584}]
     */
    public List<String> getIndexData() {
        List<String> indexDataString = new ArrayList<String>();
        HashMap<String, String> indexDataMap = new HashMap<String, String>();

        for(Integer pincode : this.pincodes) {
            indexDataMap.put("delivery_id", this.id.toString());
            indexDataMap.put("pincode", pincode.toString());
            indexDataString.add(indexDataMap.toString());
        }
        return indexDataString;
    }
}