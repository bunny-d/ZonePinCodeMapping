package SpringBase.ConstantsConfig;

public class ConstantsConfig {
    public static final String ZONE_POLYGON_URL = "http://serviceability.mkt.paytm/v2/serviceability/zones";
    public static final String COORDINATES_URL =  "http://serviceability.mkt.paytm/v2/serviceability/zone/pincode";
    public static final String ES_INDEX_URL = "http://localhost:9200/{indexname}";
    public static final String ES_INDEX_HOST = "http://localhost:9200";
    public static final Integer PINCODE_CHUNK = 5;
    public static final String ES_INDEX_NAME = "catalog_zones";
    public static final String ES_INDEX_TYPE = "info";
}
