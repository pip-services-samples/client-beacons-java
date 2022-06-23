package org.pipservices3.beacons.client.version1;

import org.junit.Before;
import org.junit.Test;

public class BeaconsMockClientV1Test {

    BeaconsMockClientV1 client;
    BeaconsClientV1Fixture fixture;

    @Before
    public void setup() {
        client = new BeaconsMockClientV1();
        fixture = new BeaconsClientV1Fixture(client);
    }

    @Test
    public void testCrudOperations() {
        fixture.testCrudOperations();
    }

    @Test
    public void testCalculatePosition() {
        fixture.testCalculatePosition();
    }
}
