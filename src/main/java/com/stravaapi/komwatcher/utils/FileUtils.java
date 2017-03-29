package com.stravaapi.komwatcher.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.stravaapi.komwatcher.bugfix.JsonUtilImplBugFix;

import javastrava.json.JsonUtil;
import javastrava.json.exception.JsonSerialisationException;

public class FileUtils {
	
	public static <T> boolean writeJsonListToFile(String file, List<T> list) {
		
		JsonUtil jsonUtil = new JsonUtilImplBugFix();
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter(file);
			for(T item : list) {
				String serializedStr = jsonUtil.serialise(item);
				writer.println(serializedStr);
			}
			
			return true;
					
		} catch (FileNotFoundException e) {
			// TODO Debug logs!
			e.printStackTrace();
		} catch (JsonSerialisationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(writer != null) {
				writer.close();
			}
		}

		return false;
	}
	
	public static <T> List<T> readJsonListFromFile(String file, Class<T> type) {
		
		List<T> objList = new ArrayList<T>();
		JsonUtil jsonUtil = new JsonUtilImplBugFix();
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			
		    String line;
		    while ((line = br.readLine()) != null) {
		    	objList.add(jsonUtil.deserialise(line, type));
		    }
		    br.close();
		    
		    return objList;
		    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonSerialisationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return objList;
	}
}
