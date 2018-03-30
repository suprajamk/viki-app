package com.supram.viki.app.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.supram.viki.app.constants.ClipTypeConstants;

/**
 * Service class to handle all functionalities of clip data
 * 
 * @author supram
 *
 */
public class ClipService {

	/**
	 * Returns the clip types. Can modify if clip types needs to be amended
	 * 
	 * @return a list of string containing the clip types
	 */
	public List<String> getListOfClipTypes() {
		List<String> allowedTypes = new ArrayList<String>();
		allowedTypes.add(ClipTypeConstants.CLIP);
//		allowedTypes.add(ClipTypeConstants.EPISODE);
//		allowedTypes.add(ClipTypeConstants.FILM);
		allowedTypes.add(ClipTypeConstants.NEWS_CLIP);
		return allowedTypes;
	}

	/**
	 * Returns the count of the hd videos for the required types
	 * 
	 * @param startPageNumber
	 *            - can specify which page to start from
	 * @param perPageLimit
	 *            - can change limit to optimize number of api calls. Max allowed
	 *            for this API is 50
	 * @param allowedTypes
	 *            - can give the required type in the form of a list of strings
	 * @return hdcount
	 */
	public int getHDVideoCount(int startPageNumber, int perPageLimit, List<String> allowedTypes) {
		int pageNumber = startPageNumber;
		int hdCount = 0;
		int allowedTypeCount = 0;
		int responseCount = 0;
		boolean more = true;

		try {
			while (more) {
				if (pageNumber % 50 == 0) {
					System.out.println("RESPONSECOUNT: " + responseCount);
					System.out.println("ALLOWEDTYPESCOUNT: " + allowedTypeCount);
					System.out.println("HDCOUNT: " + hdCount);
					System.out.println("________________________________________");
				}
				JsonObject obj = getJsonResponseForVideoAPI(pageNumber, perPageLimit);
				if (obj != null && !obj.isEmpty()) {
					more = obj.getBoolean("more");
					if (more)
						pageNumber++;
					JsonArray results = obj.getJsonArray("response");
					if (results != null && !results.isEmpty()) {
						for (JsonObject result : results.getValuesAs(JsonObject.class)) {
							String type = result.getString("type");
							if (allowedTypes.contains(type)) {
								JsonObject flags = result.getJsonObject("flags");
								boolean hd = flags.getBoolean("hd");
								if (hd)
									hdCount++;
								allowedTypeCount++;
							}
							responseCount++;
						}
					}
				}
			}
			System.out.println("TOTALRESPONSECOUNT: " + responseCount);
			System.out.println("ALLOWEDTYPESCOUNT: " + allowedTypeCount);
			System.out.println("HDCOUNT: " + hdCount);
			System.out.println("________________________________________");
			System.out.println("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hdCount;
	}

	/**
	 * Returns JsonObject response from the API
	 * @param pageNumber
	 * @param perPageLimit
	 * @return
	 * @throws IOException
	 */
	public JsonObject getJsonResponseForVideoAPI(int pageNumber, int perPageLimit) throws IOException {
		String urlString = "https://api.viki.io/v4/videos.json?app=100166a&page=" + pageNumber + "&per_page="
				+ perPageLimit;
		URL url = new URL(urlString);
		InputStream is = url.openStream();
		JsonReader rdr = Json.createReader(is);
		JsonObject obj = rdr.readObject();
		return obj;
	}
}
