/**
 * Design a simple yelp system. Support the following features:
 *
 * Add a restaurant with name and location.
 * Remove a restaurant with id.
 * Search the nearby restaurants by given location.
 * A location is represented by latitude and longitude, both in double. A Location class is given in code.
 *
 * Nearby is defined by distance smaller than k Km .
 *
 * Restaurant class is already provided and you can directly call Restaurant.create() to create a new object. Also, a Helper
 * class is provided to calculate the distance between two location, use Helper.get_distance(location1, location2).
 *
 * A GeoHash class is provided to convert a location to a string. Try GeoHash.encode(location) to convert location to a
 * geohash string and GeoHash.decode(string) to convert a string to location.
 */

import java.util.*;

 // Definition of Location:
 class Location {
    public double latitude, longitude;
    public static Location create(double lati, double longi) {
        // This will create a new location object
        return null;
     }
};
 //Definition of Restaurant
 class Restaurant {
     public int id;
     public String name;
     public Location location;
     public static Restaurant create(String name, Location location) {
         // This will create a new restaurant object,
         // and auto fill id
         return null;
     }
  }

// Definition of Helper
class Helper2 {
    public static double get_distance(Location location, Location location1) {
        return 0.0;
    }
};

 class GeoHash2 {
     public static String encode(Location location) {
         // return convert location to a GeoHash string
         return null;
     }
     public static Location decode(String hashcode) {
          // return convert a GeoHash string to location
         return null;
     }
 };

public class MiniYelp {
    class Node implements Comparable<Node> {
        public double distance;
        public Restaurant restaurant;
        public Node(double d, Restaurant r) {
            distance = d;
            restaurant = r;
        }
        public int compareTo(Node a) {
            if (this.distance < a.distance) {
                return -1;
            } else if (this.distance > a.distance) {
                return 1;
            } else {
                return 0;
            }
        }
    }
    public TreeMap<String, Restaurant> restaurants;
    public Map<Integer, String> ids;
    public double[] ERROR = {0.0000186, 0.0001492, 0.0005971, 0.00478, 0.01911, 0.076, 0.61, 2.4, 20, 78, 630, 2500};
    public MiniYelp() {
        ids = new HashMap<Integer, String>();
        restaurants = new TreeMap<String, Restaurant>();
    }

    // @param name a string
    // @param location a Location
    // @return an integer, restaurant's id
    public int addRestaurant(String name, Location location) {
        Restaurant restaurant = Restaurant.create(name, location);
        String hashcode = GeoHash2.encode(location);
        ids.put(restaurant.id, hashcode);
        restaurants.put(hashcode, restaurant);
        return restaurant.id;
    }

    // @param restaurant_id an integer
    public void removeRestaurant(int restaurant_id) {
        if (ids.containsKey(restaurant_id)) {
            String hashcode = ids.get(restaurant_id);
            restaurants.remove(hashcode);
            ids.remove(restaurant_id);
        }
    }

    // @param location a Location
    // @param k an integer, distance smaller than k miles
    // @return a list of restaurant's name and sort by 
    // distance from near to far.
    public List<String> neighbors(Location location, double k) {
        String hashcode = GeoHash2.encode(location);
        hashcode = hashcode.substring(0, getLength(k));
        List<Node> rst = new ArrayList<Node>();
        for (Map.Entry<String, Restaurant> entry :
                restaurants.subMap(hashcode, hashcode + Character.MAX_VALUE).entrySet()) {
            String key = entry.getKey();
            Restaurant value = entry.getValue();
            double distance = Helper2.get_distance(location, value.location);
            if (distance <= k)
                rst.add(new Node(distance, value));
        }
        Collections.sort(rst);
        List<String> rt = new ArrayList<String>();
        for (int i = 0; i < rst.size(); ++i) {
            rt.add(rst.get(i).restaurant.name);
        }
        return rt;
    }

    int getLength(double k) {
        int index =  Arrays.binarySearch(ERROR, k);
        if (index < 0) {
            index = -(index + 1);
        }
        return ERROR.length - index;
    }
};