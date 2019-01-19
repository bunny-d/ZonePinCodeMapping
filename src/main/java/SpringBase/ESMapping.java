package SpringBase;

import SpringBase.ConstantsConfig.ConstantsConfig;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ESMapping {
    private RestTemplate rest = new RestTemplate();

    public void createMapping(String url) {
        JSONObject mapping = this.getMapping1();

        try {
            rest.delete(url, ConstantsConfig.ES_INDEX_NAME);
        } catch(Exception e) {

        }
        System.out.println(mapping.toMap());
        rest.put(url, mapping.toMap(), ConstantsConfig.ES_INDEX_NAME);
    }

    /*
    mappings": {
        "info": {
            "properties": {
                "delivery_id": {
                    "type": "integer"
                },
                "pincode": {
                    "type": "integer"
                }
            }
        }
    }
     */
    private JSONObject getMapping1() {
        JSONObject mappings1 = new JSONObject();
        JSONObject mappings = new JSONObject();
        JSONObject info = new JSONObject();
        JSONObject properties = new JSONObject();
        JSONObject delivery_id = new JSONObject();
        JSONObject pincode = new JSONObject();

        pincode.put("type", "Integer");
        delivery_id.put("type", "Integer");
        properties.put("delivery_id", delivery_id);
        properties.put("pincode", pincode);
        info.put("properties", properties);
        mappings.put("info", info);
        mappings1.put("mappings", mappings);

        return mappings1;
    }
}