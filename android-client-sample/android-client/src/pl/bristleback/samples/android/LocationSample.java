package pl.bristleback.samples.android;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * <p/>
 * Created on: 16.06.13 11:53 <br/>
 *
 * @author Pawel Machowski
 */
public class LocationSample extends Activity {

  /*----------Listener class to get coordinates ------------- */
  private class MyLocationListener implements LocationListener {

    @Override
    public void onLocationChanged(Location loc) {

/*	        Toast.makeText(
	                getBaseContext(),
	                "Location changed: Lat: " + loc.getLatitude() + " Lng: "
	                    + loc.getLongitude(), Toast.LENGTH_SHORT).show();*/
      String longitude = "Longitude: " + loc.getLongitude();
      String latitude = "Latitude: " + loc.getLatitude();
	        /*-------to get City-Name from coordinates -------- */
      String cityName = null;
      Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
      List<Address> addresses;
      try {
        addresses = gcd.getFromLocation(loc.getLatitude(),
          loc.getLongitude(), 1);
        if (addresses.size() > 0)
          System.out.println(addresses.get(0).getLocality());
        cityName = addresses.get(0).getLocality();
      } catch (IOException e) {
        e.printStackTrace();
      }
      String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
        + cityName;
      System.out.println(s);
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
  }
}
