package com.supram.viki.app;

import java.util.List;

import com.supram.viki.app.service.ClipService;

/**
 * Entry-point to run the method to count the HD Clips
 * 
 * @author supram
 *
 */
public class App {

	public static void main(String[] args) {
		ClipService service = new ClipService();
		List<String> clipTypes = service.getListOfClipTypes();
		System.out.println("Program to count the HD clips ");
		System.out.println("________________________________________ ");
		/**
		 * Start page number = 1 -> start from first page 
		 * Per Page limit = 50 -> max limit possible for this API
		 * Allowed ClipTypes = {clip, episode, film, news_clip} 
		 */
		int hdCount = service.getHDVideoCount(1, 50, clipTypes);
		System.out.println("Total hd videos for the clips: " + hdCount);
	}
}
