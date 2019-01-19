package SpringBase;

import SpringBase.BeanConfig.ObjectsConfig;
import SpringBase.ConstantsConfig.ConstantsConfig;
import SpringBase.Model.PincodesModel;
import SpringBase.Model.ZonesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class ZonePincode {
    @Autowired
    private Zones zones;
    @Autowired
    private Pincodes pincodes;
    @Autowired
    private ESMapping esMapping;
    @Autowired
    private QueueData queueData;
    @Autowired
    private Publisher publisher;

    public static void main(String [] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ObjectsConfig.class);
        ZonePincode zp = ctx.getBean(ZonePincode.class);
        zp.start();
    }

    private void start() {
        ZonesModel zp;
        HashMap<Integer,PincodesModel> zonePincodeData;
        HashMap<Integer, List<String>> zoneWiseQueueData;
        ResponseEntity re = this.zones.getZones(ConstantsConfig.ZONE_POLYGON_URL);

        if(re.getStatusCode() == HttpStatus.OK) {
            zp = (ZonesModel) re.getBody();
        } else {
            System.out.println("Error in zonesAPI");
            return;
        }

        zonePincodeData = this.pincodes.getPincodes(ConstantsConfig.COORDINATES_URL, zp);
        if(zonePincodeData.isEmpty()) {
            System.out.println("Error in pincodeAPI");
            return;
        }

        this.esMapping.createMapping(ConstantsConfig.ES_INDEX_URL);
        zoneWiseQueueData = this.queueData.prepareQueueData(zonePincodeData);
        if(zoneWiseQueueData.isEmpty()) {
            System.out.println("Error in preparing queue Data");
            return;
        }

        this.publisher.sendToQueue(zoneWiseQueueData);
    }
}