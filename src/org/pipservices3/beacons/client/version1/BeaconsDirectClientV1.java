package org.pipservices3.beacons.client.version1;

import org.pipservices3.beacons.data.version1.BeaconV1;
import org.pipservices3.beacons.service.logic.IBeaconsController;
import org.pipservices3.commons.data.DataPage;
import org.pipservices3.commons.data.FilterParams;
import org.pipservices3.commons.data.PagingParams;
import org.pipservices3.commons.refer.Descriptor;
import org.pipservices3.rpc.clients.DirectClient;

import java.util.List;
import java.util.Map;

public class BeaconsDirectClientV1 extends DirectClient<IBeaconsController> implements IBeaconsClientV1 {

    public BeaconsDirectClientV1() {
        super();
        this._dependencyResolver.put("controller", new Descriptor("beacons", "controller", "*", "*", "1.0"));
    }

    @Override
    public DataPage<BeaconV1> getBeacons(String correlationId, FilterParams filter, PagingParams paging) {
        var timing = this.instrument(correlationId, "beacons.get_beacons");

        try {
            return this._controller.getBeacons(correlationId, filter, paging);
        } catch (Exception err) {
            timing.endFailure(err);
            throw new RuntimeException(err);
        } finally {
            timing.endSuccess();
        }
    }

    @Override
    public BeaconV1 getBeaconById(String correlationId, String beaconId) {
        var timing = this.instrument(correlationId, "beacons.get_beacon_by_id");

        try {
            return this._controller.getBeaconById(correlationId, beaconId);
        } catch (Exception err) {
            timing.endFailure(err);
            throw new RuntimeException(err);
        } finally {
            timing.endSuccess();
        }
    }

    @Override
    public BeaconV1 getBeaconByUdi(String correlationId, String udi) {
        var timing = this.instrument(correlationId, "beacons.get_beacon_by_udi");

        try {
            return this._controller.getBeaconByUdi(correlationId, udi);
        } catch (Exception err) {
            timing.endFailure(err);
            throw new RuntimeException(err);
        } finally {
            timing.endSuccess();
        }
    }

    @Override
    public Map<String, ?> calculatePosition(String correlationId, String siteId, List<String> udis) {
        var timing = this.instrument(correlationId, "beacons.calculate_position");

        try {
            return this._controller.calculatePosition(correlationId, siteId, udis);
        } catch (Exception err) {
            timing.endFailure(err);
            throw new RuntimeException(err);
        } finally {
            timing.endSuccess();
        }
    }

    @Override
    public BeaconV1 createBeacon(String correlationId, BeaconV1 beacon) {
        var timing = this.instrument(correlationId, "beacons.create_beacon");

        try {
            return this._controller.createBeacon(correlationId, beacon);
        } catch (Exception err) {
            timing.endFailure(err);
            throw new RuntimeException(err);
        } finally {
            timing.endSuccess();
        }
    }

    @Override
    public BeaconV1 updateBeacon(String correlationId, BeaconV1 beacon) {
        var timing = this.instrument(correlationId, "beacons.update_beacon");

        try {
            return this._controller.updateBeacon(correlationId, beacon);
        } catch (Exception err) {
            timing.endFailure(err);
            throw new RuntimeException(err);
        } finally {
            timing.endSuccess();
        }
    }

    @Override
    public BeaconV1 deleteBeaconById(String correlationId, String beaconId) {
        var timing = this.instrument(correlationId, "beacons.delete_beacon_by_id");

        try {
            return this._controller.deleteBeaconById(correlationId, beaconId);
        } catch (Exception err) {
            timing.endFailure(err);
            throw new RuntimeException(err);
        } finally {
            timing.endSuccess();
        }
    }
}
