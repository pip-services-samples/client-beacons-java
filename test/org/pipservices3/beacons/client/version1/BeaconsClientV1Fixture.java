package org.pipservices3.beacons.client.version1;

import org.pipservices3.beacons.data.version1.BeaconTypeV1;
import org.pipservices3.beacons.data.version1.BeaconV1;
import org.pipservices3.commons.data.FilterParams;
import org.pipservices3.commons.data.PagingParams;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class BeaconsClientV1Fixture {

    BeaconV1 BEACON1 = new BeaconV1(
            "1",
            "00001",
            BeaconTypeV1.AltBeacon,
            "1",
            "TestBeacon1",
            Map.of("type", "Point", "coordinates", List.of(0, 0)),
            50
    );

    BeaconV1 BEACON2 = new BeaconV1(
            "2",
            "00002",
            BeaconTypeV1.iBeacon,
            "1",
            "TestBeacon2",
            Map.of("type", "Point", "coordinates", List.of(2, 2)),
            70
    );

    private final IBeaconsClientV1 _client;

    public BeaconsClientV1Fixture(IBeaconsClientV1 client) {
        assertNotNull(client);
        this._client = client;
    }

    public void testCrudOperations() {
        BeaconV1 beacon1;

        // Create the first beacon
        var beacon = this._client.createBeacon(null, BEACON1);

        assertEquals(BEACON1.udi, beacon.udi);
        assertEquals(BEACON1.siteId, beacon.siteId);
        assertEquals(BEACON1.type, beacon.type);
        assertEquals(BEACON1.label, beacon.label);
        assertNotNull(beacon.center);

        // Create the second beacon
        beacon = this._client.createBeacon(null, BEACON2);

        assertEquals(BEACON2.udi, beacon.udi);
        assertEquals(BEACON2.siteId, beacon.siteId);
        assertEquals(BEACON2.type, beacon.type);
        assertEquals(BEACON2.label, beacon.label);
        assertNotNull(beacon.center);

        // Get all entities
        var page = this._client.getBeacons(null, new FilterParams(), new PagingParams());

        assertEquals(page.getData().size(), 2);

        beacon1 = page.getData().get(0);

        // Update the beacon
        beacon1.label = "ABC";

        beacon = this._client.updateBeacon(null, beacon1);

        assertEquals(beacon1.getId(), beacon.getId());
        assertEquals("ABC", beacon.label);

        // Get beacon by udi
        beacon = this._client.getBeaconByUdi(null, beacon1.udi);

        assertEquals(beacon1.getId(), beacon.getId());

        // Delete the beacon
        beacon = this._client.deleteBeaconById(null, beacon1.getId());

        assertEquals(beacon1.getId(), beacon.getId());

        // Try to get deleted beacon
        beacon = this._client.getBeaconById(null, beacon1.getId());
        assertNull(beacon);
    }

    public void testCalculatePosition() {
        // Create the first beacon
        var beacon = this._client.createBeacon(null, BEACON1);

        assertEquals(BEACON1.udi, beacon.udi);
        assertEquals(BEACON1.siteId, beacon.siteId);
        assertEquals(BEACON1.type, beacon.type);
        assertEquals(BEACON1.label, beacon.label);
        assertNotNull(beacon.center);

        // Create the second beacon
        beacon = this._client.createBeacon(null, BEACON2);

        assertEquals(BEACON2.udi, beacon.udi);
        assertEquals(BEACON2.siteId, beacon.siteId);
        assertEquals(BEACON2.type, beacon.type);
        assertEquals(BEACON2.label, beacon.label);
        assertNotNull(beacon.center);

        // Calculate position for one beacon
        var position = this._client.calculatePosition(
                null, "1", List.of("00001"));

        assertEquals("Point", position.get("type"));
        assertEquals(((List<?>) position.get("coordinates")).size(), 2);
        assertEquals(0, ((List<?>) position.get("coordinates")).get(0));
        assertEquals(0, ((List<?>) position.get("coordinates")).get(1));
    }
}
