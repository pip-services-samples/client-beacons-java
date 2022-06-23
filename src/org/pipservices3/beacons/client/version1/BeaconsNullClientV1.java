package org.pipservices3.beacons.client.version1;

import org.pipservices3.commons.data.DataPage;
import org.pipservices3.commons.data.FilterParams;
import org.pipservices3.commons.data.PagingParams;

import org.pipservices3.beacons.data.version1.BeaconV1;

import java.util.List;
import java.util.Map;

public class BeaconsNullClientV1 implements IBeaconsClientV1{
    @Override
    public DataPage<BeaconV1> getBeacons(String correlationId, FilterParams filter, PagingParams paging) {
        return new DataPage<BeaconV1>(List.of(), 0L);
    }

    @Override
    public BeaconV1 getBeaconById(String correlationId, String beaconId) {
        return null;
    }

    @Override
    public BeaconV1 getBeaconByUdi(String correlationId, String udi) {
        return null;
    }

    @Override
    public Map<String, Object> calculatePosition(String correlationId, String siteId, List<String> udis) {
        return null;
    }

    @Override
    public BeaconV1 createBeacon(String correlationId, BeaconV1 beacon) {
        return null;
    }

    @Override
    public BeaconV1 updateBeacon(String correlationId, BeaconV1 beacon) {
        return null;
    }

    @Override
    public BeaconV1 deleteBeaconById(String correlationId, String beaconId) {
        return null;
    }
}
