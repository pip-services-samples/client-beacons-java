package org.pipservices3.beacons.client.version1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pipservices3.beacons.service.logic.BeaconsController;
import org.pipservices3.beacons.service.persistence.BeaconsMemoryPersistence;
import org.pipservices3.commons.config.ConfigParams;
import org.pipservices3.commons.errors.ApplicationException;
import org.pipservices3.commons.refer.Descriptor;
import org.pipservices3.commons.refer.References;

public class BeaconsDirectClientV1Test {

    BeaconsMemoryPersistence persistence;
    BeaconsController controller;
    BeaconsDirectClientV1 client;
    BeaconsClientV1Fixture fixture;

    @Before
    public void setup() throws ApplicationException {
        persistence = new BeaconsMemoryPersistence();
        persistence.configure(new ConfigParams());

        controller = new BeaconsController();
        controller.configure(new ConfigParams());

        client = new BeaconsDirectClientV1();

        var references = References.fromTuples(
                new Descriptor("beacons", "persistence", "memory", "default", "1.0"), persistence,
                new Descriptor("beacons", "controller", "default", "default", "1.0"), controller,
                new Descriptor("beacons", "client", "direct", "default", "1.0"), client
        );

        controller.setReferences(references);
        client.setReferences(references);

        fixture = new BeaconsClientV1Fixture(client);

        persistence.open(null);
    }

    @After
    public void teardown() throws ApplicationException {
        persistence.close(null);
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
