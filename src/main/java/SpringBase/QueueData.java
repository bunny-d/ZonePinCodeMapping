package SpringBase;

import SpringBase.ConstantsConfig.ConstantsConfig;
import SpringBase.Model.PincodesHelperModel;
import SpringBase.Model.PincodesModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class QueueData {
    public static Integer COUNTS = 1;
    /*
    1 model per Integer and that model consists list of 5 helper models
    {0=SpringBase.Model.PincodesModel@24569dba, 1=SpringBase.Model.PincodesModel@5ddeb7cb}
     */
    public HashMap<Integer, List<String>> prepareQueueData(HashMap<Integer,PincodesModel> zonePincodeData) {
        List<PincodesHelperModel> listHelperModel;
        HashMap<Integer, List<String>> zoneWiseQueueData = new HashMap<Integer, List<String>>();
        List<String> indexDataString;
        List<String> indexSemantic;
        List<String> indexDataSemanticMerged;

        for(PincodesModel pincodeModel : zonePincodeData.values()) {
            //list of 5 helper models
            listHelperModel = pincodeModel.getPincodesHelperModel();

            for(PincodesHelperModel helperModel : listHelperModel) {
                //Each helperModel represent data for a zone and all its pincodes array.
                indexDataString = helperModel.getIndexData();
                indexSemantic = this.getIndexSemantic(helperModel);
                indexDataSemanticMerged = this.mergedSemanticData(indexDataString, indexSemantic, helperModel.getCountPinCodes());
                zoneWiseQueueData.put(helperModel.getId(), indexDataSemanticMerged);
            }
        }
        /*
        OUTPUT
        584=[{index={_index=catalog_zones, _type=info, _id=1}}, {pincode=121000, delivery_id=584},
             {index={_index=catalog_zones, _type=info, _id=2}}, {pincode=121001, delivery_id=584}]
         */
        return zoneWiseQueueData;
    }

    /*
        [{index={_index=catalog_zones, _type=info, _id=1}}, {index={_index=catalog_zones, _type=info, _id=2}}]
     */
    private List<String> getIndexSemantic(PincodesHelperModel helperModel) {
        List<String> indexSemanticString = new ArrayList<String>();
        HashMap<String, String> indexSemanticMap = new HashMap<String, String>();
        HashMap<String, HashMap<String, String>> indexSemanticMapParent = new HashMap<String, HashMap<String, String>>();
        int i,pincodesCount;

        indexSemanticMap.put("_index", ConstantsConfig.ES_INDEX_NAME);
        indexSemanticMap.put("_type", ConstantsConfig.ES_INDEX_TYPE);
        indexSemanticMapParent.put("index", indexSemanticMap);

        pincodesCount = helperModel.getCountPinCodes();
        for(i = 0;i < pincodesCount;i++) {
            indexSemanticMap.put("_id", COUNTS.toString());
            COUNTS++;
            indexSemanticString.add(indexSemanticMapParent.toString());
        }
        return indexSemanticString;
    }

    /*
    [{index={_index=catalog_zones, _type=info, _id=1}}, {pincode=121000, delivery_id=584}]
     */
    private List<String> mergedSemanticData(List<String> indexDataString, List<String> indexSemantic, int pincodesCount) {
        int i;
        List<String> data = new ArrayList<String> ();

        for(i = 0; i< pincodesCount; i++) {
            data.add(indexSemantic.get(i));
            data.add(indexDataString.get(i));
        }
        return data;
    }
}
