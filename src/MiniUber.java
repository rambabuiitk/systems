import java.util.*;

/**
 * Support two basic uber features:
 *
 * Drivers report their locations.
 * Rider request a uber, return a matched driver.
 * When rider request a uber, match a closest available driver with him, then mark the driver not available.
 *
 * When next time this matched driver report his location, return with the rider's information.
 *
 * You can implement it with the following instructions:
 *
 * report(driver_id, lat, lng)
 * If a matching trip is not found, return null.
 * return matched trip information.
 * request(rider_id, lat, lng)
 * create a trip with rider's information.
 * find a closest driver, mark this driver not available.
 * fill driver_id into this trip.
 * return trip
 * This is the definition of Trip in Java:
 */
// Definition of Trip:
class Trip {
    public int id; // trip's id, primary key
    public int driver_id, rider_id; // foreign key
    public double lat, lng; // pick up location

    public Trip(int rider_id, double lat, double lng) {

    }
}

// Definition of Helper
class Helper {
    public static double get_distance(double lat1, double lng1,
                                      double lat2, double lng2) {
        // return distance between (lat1, lng1) and (lat2, lng2)
        return 0.0;
    }
};

public class MiniUber {

    class Location {
        public double lat, lng;

        public Location(double _lat, double _lng) {
            lat = _lat;
            lng = _lng;
        }
    }

    Map<Integer, Trip> driver2Trip = null;
    Map<Integer, Location> driver2Location = null;

    public MiniUber() {
        driver2Trip = new HashMap<Integer, Trip>();
        driver2Location = new HashMap<>();
    }

    // @param driver_id an integer
    // @param lat, lng driver's location
    // return matched trip information if there have matched rider or null
    public Trip report(int driver_id, double lat, double lng) {
        if (driver2Trip.containsKey(driver_id))
            return driver2Trip.get(driver_id);

        if (driver2Location.containsKey(driver_id)) {
            driver2Location.get(driver_id).lat = lat;
            driver2Location.get(driver_id).lng = lng;
        } else {
            driver2Location.put(driver_id, new Location(lat, lng));
        }

        return null;
    }

    // @param rider_id an integer
    // @param lat, lng rider's location
    // return a trip
    public Trip request(int rider_id, double lat, double lng) {
        Trip trip = new Trip(rider_id, lat, lng);
        double distance = -1;
        int driver_id = -1;
        for (Map.Entry<Integer, Location> entry : driver2Location.entrySet()) {
            Location location = entry.getValue();
            double dis = Helper.get_distance(location.lat, location.lng, lat, lng);
            if (distance < 0 || distance > dis) {
                driver_id = entry.getKey();
                distance = dis;
            }
        }

        if (driver_id != -1)
            driver2Location.remove(driver_id);
        trip.driver_id = driver_id;
        driver2Trip.put(driver_id, trip);
        return trip;
    }
}