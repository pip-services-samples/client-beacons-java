package org.pipservices3.beacons.client.version1;

import jakarta.ws.rs.core.GenericType;
import org.pipservices3.beacons.data.version1.BeaconV1;
import org.pipservices3.commons.data.DataPage;
import org.pipservices3.commons.data.FilterParams;
import org.pipservices3.commons.data.PagingParams;
import org.pipservices3.commons.errors.ApplicationException;
import org.pipservices3.rpc.clients.CommandableHttpClient;

import java.util.List;
import java.util.Map;

public class BeaconsHttpClientV1 extends CommandableHttpClient implements IBeaconsClientV1 {

    public BeaconsHttpClientV1() {
        super("v1/beacons");
    }

    @Override
    public DataPage<BeaconV1> getBeacons(String correlationId, FilterParams filter, PagingParams paging) {
        try {
            return this.callCommand(new GenericType<DataPage<BeaconV1>>() {
                                    },
                    "get_beacons",
                    correlationId,
                    Map.of("filter", filter, "paging", paging)
            );
        } catch (ApplicationException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public BeaconV1 getBeaconById(String correlationId, String beaconId) {
        try {
            return this.callCommand(BeaconV1.class,
                    "get_beacon_by_id",
                    correlationId,
                    Map.of("beacon_id", beaconId)
            );
        } catch (ApplicationException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public BeaconV1 getBeaconByUdi(String correlationId, String udi) {
        try {
            return this.callCommand(BeaconV1.class,
                    "get_beacon_by_udi",
                    correlationId,
                    Map.of("udi", udi)
            );
        } catch (ApplicationException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Map<String, Object> calculatePosition(String correlationId, String siteId, List<String> udis) {
        try {
            return this.callCommand(new GenericType<Map<String, Object>>() {
                                    },
                    "calculate_position",
                    correlationId,
                    Map.of("site_id", siteId, "udis", udis)
            );
        } catch (ApplicationException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public BeaconV1 createBeacon(String correlationId, BeaconV1 beacon) {
        try {
            return this.callCommand(BeaconV1.class,
                    "create_beacon",
                    correlationId,
                    Map.of("beacon", beacon)
            );
        } catch (ApplicationException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public BeaconV1 updateBeacon(String correlationId, BeaconV1 beacon) {
        try {
            return this.callCommand(BeaconV1.class,
                    "update_beacon",
                    correlationId,
                    Map.of("beacon", beacon)
            );
        } catch (ApplicationException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public BeaconV1 deleteBeaconById(String correlationId, String beaconId) {
        try {
            return this.callCommand(BeaconV1.class,
                    "delete_beacon_by_id",
                    correlationId,
                    Map.of("beacon_id", beaconId)
            );
        } catch (ApplicationException ex) {
            throw new RuntimeException(ex);
        }
    }
}
