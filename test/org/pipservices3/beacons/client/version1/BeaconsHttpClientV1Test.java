package org.pipservices3.beacons.client.version1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pipservices3.beacons.service.logic.BeaconsController;
import org.pipservices3.beacons.service.persistence.BeaconsMemoryPersistence;
import org.pipservices3.beacons.service.services.BeaconsHttpServiceV1;
import org.pipservices3.commons.config.ConfigParams;
import org.pipservices3.commons.errors.ApplicationException;
import org.pipservices3.commons.refer.Descriptor;
import org.pipservices3.commons.refer.References;

public class BeaconsHttpClientV1Test {

    BeaconsMemoryPersistence persistence;
    BeaconsController controller;
    BeaconsHttpServiceV1 service;
    BeaconsHttpClientV1 client;
    BeaconsClientV1Fixture fixture;

    @Before
    public void setup() throws ApplicationException {
        persistence = new BeaconsMemoryPersistence();
        persistence.configure(new ConfigParams());

        controller = new BeaconsController();
        controller.configure(new ConfigParams());

        var httpConfig = ConfigParams.fromTuples(
                "connection.protocol", "http",
                "connection.port", 3000,
                "connection.host", "localhost"
        );

        service = new BeaconsHttpServiceV1();
        service.configure(httpConfig);

        client = new BeaconsHttpClientV1();
        client.configure(httpConfig);

        var references = References.fromTuples(
                new Descriptor("beacons", "persistence", "memory", "default", "1.0"), persistence,
                new Descriptor("beacons", "controller", "default", "default", "1.0"), controller,
                new Descriptor("beacons", "service", "http", "default", "1.0"), service,
                new Descriptor("beacons", "client", "http", "default", "1.0"), client
        );
        controller.setReferences(references);
        service.setReferences(references);
        client.setReferences(references);

        fixture = new BeaconsClientV1Fixture(client);

        persistence.open(null);
        service.open(null);
        client.open(null);
    }

    @After
    public void teardown() throws ApplicationException {
        client.close(null);
        service.close(null);
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
