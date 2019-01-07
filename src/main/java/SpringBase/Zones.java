package SpringBase;
import SpringBase.Model.ZonesModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Zones {
    private RestTemplate rest = new RestTemplate();

    public ResponseEntity getZones(String url) {
        System.out.println(url);
        ResponseEntity re = rest.getForEntity(url, ZonesModel.class);
        return re;
    }
}