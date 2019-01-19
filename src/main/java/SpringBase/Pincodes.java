package SpringBase;

import SpringBase.Model.PincodesModel;
import SpringBase.Model.ZonesModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;

import static SpringBase.ConstantsConfig.ConstantsConfig.PINCODE_CHUNK;

@Component
public class Pincodes {
    private RestTemplate rest = new RestTemplate();

    public HashMap<Integer, PincodesModel> getPincodes(String url, ZonesModel zp) {
        double zonesDataSize = (double) zp.getZoneDataSize();
        double pincodeBatch = Math.ceil(zonesDataSize/PINCODE_CHUNK);
        int i,j;
        List batchedZones = null;
        UriComponentsBuilder builder;
        String queryParam;
        PincodesModel pm;
        HashMap<Integer,PincodesModel> zonePincodeData = new HashMap<Integer,PincodesModel>();

        for(i=0,j=0 ;i<1; i++,j+=5) {
            if(i+1 == pincodeBatch) {
                batchedZones = zp.zone.subList(j, (int)zonesDataSize);
            } else {
                batchedZones = zp.zone.subList(j, j + PINCODE_CHUNK);
            }

            queryParam = zp.getSublistIds(batchedZones);
            builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("zone_id", queryParam);
            ResponseEntity re = rest.getForEntity(builder.toUriString(), PincodesModel.class);

            if(re.getStatusCode() == HttpStatus.OK) {
                pm = (PincodesModel) re.getBody();
                zonePincodeData.put(i, pm);
            }
        }
        return zonePincodeData;
    }
}