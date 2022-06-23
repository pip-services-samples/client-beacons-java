package org.pipservices3.beacons.client.version1;

import org.pipservices3.beacons.data.version1.BeaconTypeV1;
import org.pipservices3.beacons.data.version1.BeaconV1;
import org.pipservices3.commons.random.RandomArray;
import org.pipservices3.commons.random.RandomInteger;

import java.util.List;
import java.util.Map;

public class RandomBeaconV1 {
    public static String nextBeaconType() {
        return RandomArray.pick(List.of(BeaconTypeV1.AltBeacon, BeaconTypeV1.EddyStoneUdi, BeaconTypeV1.Unknown, BeaconTypeV1.iBeacon));
    }

    public static Map<String, Object> nextBeaconCenter() {
        return Map.of(
                "type", "Point",
                "center", Map.of("coordinates", List.of(
                                RandomInteger.nextInteger(1, 1000), RandomInteger.nextInteger(1, 1000)
                        )
                )
        );
    }

    public static BeaconV1 nextBeacon() {
        var beacon = new BeaconV1();
        beacon.type = RandomBeaconV1.nextBeaconType();
        beacon.radius = RandomInteger.nextInteger(1, 1000);
        beacon.udi = RandomArray.pick(List.of("00001", "00002", "00003", "00004"));
        beacon.center = RandomBeaconV1.nextBeaconCenter();
        return beacon;
    }
}
