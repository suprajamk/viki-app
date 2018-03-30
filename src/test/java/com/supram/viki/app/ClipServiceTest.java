package com.supram.viki.app;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.supram.viki.app.service.ClipService;

/**
 * Unit test for Clip Service
 * 
 * @author supram
 *
 */
public class ClipServiceTest {
	private static ClipService clipService;
	private static JsonObject response;

	@BeforeClass
	public static void initClipService() {
		clipService = new ClipService();

	}

	/**
	 * Tests the count is zero when the clip found is not HD
	 */
	@Test
	public void clipIsNotHDTest() {
		ClipService mockService = Mockito.spy(clipService);
		InputStream fis;
		try {
			fis = ClipServiceTest.class.getResourceAsStream("/response-withnonhdclip.json");
			JsonReader reader = Json.createReader(fis);
			response = reader.readObject();
			Mockito.doReturn(response).when(mockService).getJsonResponseForVideoAPI(1, 50);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int hdCount = mockService.getHDVideoCount(1, 50, clipService.getListOfClipTypes());
		assertEquals(0, hdCount);
	}

	/**
	 * Tests the count is one when the clip found is HD
	 */
	@Test
	public void hdClipCountPositiveTest() {
		ClipService mockService = Mockito.spy(clipService);
		InputStream fis;
		try {
			fis = ClipServiceTest.class.getResourceAsStream("/response-withclip.json");
			JsonReader reader = Json.createReader(fis);
			response = reader.readObject();
			Mockito.doReturn(response).when(mockService).getJsonResponseForVideoAPI(1, 50);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int hdCount = mockService.getHDVideoCount(1, 50, clipService.getListOfClipTypes());
		assertEquals(1, hdCount);
	}

	/**
	 * Tests the count is zero when the clip is not found
	 */
	@Test
	public void hdClipNotFoundTest() {
		ClipService mockService = Mockito.spy(clipService);
		InputStream fis;
		try {
			fis = ClipServiceTest.class.getResourceAsStream("/response-withoutclip.json");
			JsonReader reader = Json.createReader(fis);
			response = reader.readObject();
			Mockito.doReturn(response).when(mockService).getJsonResponseForVideoAPI(1, 50);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int hdCount = mockService.getHDVideoCount(1, 50, clipService.getListOfClipTypes());
		assertEquals(0, hdCount);
	}

}
