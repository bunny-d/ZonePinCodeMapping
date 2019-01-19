package SpringBase;

import SpringBase.ConstantsConfig.ConstantsConfig;
import com.google.gson.*;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

@Component
public class ESIndex {
    private RestTemplate rest = new RestTemplate();

    //Input is :: [{index={_index=catalog_zones, _type=info, _id=305}}, {pincode=160071, delivery_id=591}]
    public boolean indexDataOnES(List<String> combined) {
        JsonParser parser = new JsonParser();
        JSONObject data = new JSONObject(); //This is important in order to create JSON to index. Can't help it.
        String  indexName,type,url;
        Integer id;
        String indexStructure = combined.get(0);
        String dataStructure = combined.get(1);

        //Index details
        JsonObject objStructure = parser.parse(indexStructure).getAsJsonObject();
        JsonObject obj = (JsonObject) objStructure.get("index");
        indexName = obj.get("_index").toString();
        type = obj.get("_type").toString();
        id = obj.get("_id").getAsInt();

        //Data details
        JsonObject dataObj = parser.parse(dataStructure).getAsJsonObject();
        Set keys = dataObj.keySet();

        //keys are pincode and delivery_id
        for(Object key : keys) {
            String key1 = key.toString();
            data.put(key1, dataObj.get(key1).getAsInt());
        }
        url = ConstantsConfig.ES_INDEX_HOST;
        url = url.concat("/").concat(indexName).concat("/").concat(type).concat("/");
        url = url.replace("\"","");

        rest.put(url + "{id}", data.toMap(),id);
        //System.out.println("indexing " + id);
        return true;
    }
}