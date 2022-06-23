package org.pipservices3.beacons.client.version1;

import org.pipservices3.beacons.data.version1.BeaconV1;
import org.pipservices3.commons.data.DataPage;
import org.pipservices3.commons.data.FilterParams;
import org.pipservices3.commons.data.PagingParams;
import org.pipservices3.commons.random.RandomInteger;

import java.util.*;
import java.util.function.Predicate;

public class BeaconsMockClientV1 implements IBeaconsClientV1 {
    private long _maxPageSize = 100;
    private List<BeaconV1> _items = new ArrayList<>();

    public BeaconsMockClientV1(List<BeaconV1> items) {
        this._items = items;
    }

    public BeaconsMockClientV1() {
    }

    private Predicate<BeaconV1> composeFilter(FilterParams filter) {
        filter = filter != null ? filter : new FilterParams();

        var id = filter.getAsNullableString("id");
        var siteId = filter.getAsNullableString("site_id");
        var label = filter.getAsNullableString("label");
        var udi = filter.getAsNullableString("udi");
        var udis = filter.getAsObject("udis");

        if (udis != null)
            udis = Arrays.stream(((String) udis).split(",")).toList();

        if (udis != null && ((List<?>) udis).isEmpty())
            udis = null;

        List<String> finalUdis = (List<String>) udis;
        return (BeaconV1 item) -> {
            if (id != null && !Objects.equals(item.getId(), id))
                return false;
            if (siteId != null && !Objects.equals(item.siteId, siteId))
                return false;
            if (label != null && !Objects.equals(item.label, label))
                return false;
            if (udi != null && !Objects.equals(item.udi, udi))
                return false;
            if (finalUdis != null && !finalUdis.contains(item.udi))
                return false;
            return true;
        };
    }

    @Override
    public DataPage<BeaconV1> getBeacons(String correlationId, FilterParams filter, PagingParams paging) {
        var filterBeacons = this.composeFilter(filter);
        var beacons = this._items.stream().filter(filterBeacons);

        // Extract a page
        paging = paging != null ? paging : new PagingParams();
        var skip = paging.getSkip(-1);
        var take = paging.getTake(this._maxPageSize);

        Long total = null;
        if (paging.hasTotal())
            total = beacons.count();

        if (skip > 0)
            beacons = beacons.skip(skip);
        beacons = beacons.limit(take);

        return new DataPage<>(beacons.toList(), total);
    }

    @Override
    public BeaconV1 getBeaconById(String correlationId, String beaconId) {
        var beacons = this._items.stream().filter((x) -> Objects.equals(x.getId(), beaconId)).toList();

        return beacons.size() > 0 ? beacons.get(0) : null;
    }

    @Override
    public BeaconV1 getBeaconByUdi(String correlationId, String udi) {
        var beacons = this._items.stream().filter((x) -> Objects.equals(x.udi, udi)).toList();

        return beacons.size() > 0 ? beacons.get(0) : null;
    }

    @Override
    public Map<String, Object> calculatePosition(String correlationId, String siteId, List<String> udis) {
        List<BeaconV1> beacons;
        Map<String, Object> position = null;

        if (udis == null || udis.isEmpty())
            return null;

        var page = this.getBeacons(
                correlationId,
                FilterParams.fromTuples(
                        "site_id", siteId,
                        "udis", udis
                ),
                null
        );

        beacons = page != null ? page.getData() : new ArrayList<>();

        var lat = 0;
        var lng = 0;
        var count = 0;

        for (var beacon : beacons) {
            if (beacon.center != null
                    && beacon.center.get("type").equals("Point")
                    && beacon.center.get("coordinates") instanceof List<?>) {
                lng += ((List<Integer>) beacon.center.get("coordinates")).get(0);
                lat += ((List<Integer>) beacon.center.get("coordinates")).get(1);
                count += 1;
            }
        }

        if (count > 0) {
            position = Map.of(
                    "type", "Point",
                    "coordinates", List.of(lng / count, lat / count)
            );
        }

        return position;

    }

    @Override
    public BeaconV1 createBeacon(String correlationId, BeaconV1 beacon) {
        if (beacon == null)
            return null;

        if (beacon.getId() == null)
            beacon.withGeneratedId();

        this._items.add(beacon);

        return beacon;
    }

    @Override
    public BeaconV1 updateBeacon(String correlationId, BeaconV1 beacon) {
        var index = this._items.stream().map(BeaconV1::getId).toList().indexOf(beacon.getId());

        if (index < 0)
            return null;

        this._items.set(index, beacon);

        return beacon;
    }

    @Override
    public BeaconV1 deleteBeaconById(String correlationId, String beaconId) {
        var index = this._items.stream().map(BeaconV1::getId).toList().indexOf(beaconId);

        if (index < 0)
            return null;

        return this._items.remove(index);
    }
}
