package model;

import java.util.List;

public class GameModel {

    private CityModel city;
    private List<TripModel> trips;

    public CityModel getCity() {
        return city;
    }

    public void setCity(CityModel city) {
        this.city = city;
    }

    public List<TripModel> getTrips() {
        return trips;
    }

    public void setTrips(List<TripModel> trips) {
        this.trips = trips;
    }
}
