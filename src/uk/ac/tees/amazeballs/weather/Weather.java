package uk.ac.tees.amazeballs.weather;

import java.io.IOException;
import java.net.MalformedURLException;
import org.json.JSONException;

import net.aksingh.java.api.owm.CurrentWeatherData;
import net.aksingh.java.api.owm.OpenWeatherMap;


public class Weather {

	public static CurrentWeatherData getWeatherData(float latitude, float longitude) {
		//Registered AppID from OpenWeatherMap
		OpenWeatherMap owm = new OpenWeatherMap("ebe4cc284208eb70ca4be827307d6967");
		try {
			return owm.currentWeatherByCoordinates(latitude, longitude);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
