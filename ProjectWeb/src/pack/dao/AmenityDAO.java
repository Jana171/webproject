package pack.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import pack.model.Amenity;

public class AmenityDAO {

	private String path;
	private List<Amenity> amenities = new ArrayList<Amenity>();
	
	public AmenityDAO(String path) {
		this.path = path;
		this.loadAmenities();
	}
	
	
	public List<Amenity> getAllAmenities() {
		return amenities;
	}
	
	public Amenity getAmenity(int id) {
		for(Amenity a : this.amenities)
			if(a.getId() == id)
				return a;
		
		return null;
	}
	
	public boolean addAmenity(Amenity amenity) {
		Random random = new Random();
		int id = random.nextInt();
		amenity.setId(id);
		this.amenities.add(amenity);
		this.saveAmenity(amenity);
		return true;
	}
	

	
	@SuppressWarnings("unchecked")
	public Amenity updateAmenity(Amenity amenity) {
		
		Amenity a = this.getAmenity(amenity.getId());
		a.setTitle(amenity.getTitle());
			
		
		JSONParser jsonParser = new JSONParser();
		String fullPath = path + "/res/db/amenities.json";
		
		try {
			JSONArray amenitiesArray = (JSONArray) jsonParser.parse(new FileReader(fullPath));
			for(Object o : amenitiesArray) {
				JSONObject amenityJSON = (JSONObject) o;
				if(((Long) amenityJSON.get("id")).intValue() == amenity.getId()) {
					amenityJSON.put("title", amenity.getTitle());
				}
			}
			
			FileWriter file = new FileWriter(fullPath);
            file.write(amenitiesArray.toJSONString());
            file.close();
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		
		return a;
		
	}
	
	@SuppressWarnings("unchecked")
	public boolean deleteAmenity(int id) {
		boolean flag = false;
		for(int i = 0 ; i < this.amenities.size();i++) {
			if(this.amenities.get(i).getId() == id) {
				this.amenities.remove(i);
				flag = true;
				break;
			}
		}
		
		JSONParser jsonParser = new JSONParser();
		String fullPath = path + "/res/db/amenities.json";
		
		try {
			JSONArray amenitiesArray = (JSONArray) jsonParser.parse(new FileReader(fullPath));
			for(Object o : amenitiesArray) {
				JSONObject amenityJSON = (JSONObject) o;
				if(((Long) amenityJSON.get("id")).intValue() == id) {
					amenityJSON.put("deleted", true);
				}
			}
			
			FileWriter file = new FileWriter(fullPath);
            file.write(amenitiesArray.toJSONString());
            file.close();
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		
		return true;
	}
	
	public void loadAmenities() {
		JSONParser jsonParser = new JSONParser();
		String fullPath = path + "/res/db/amenities.json";
		System.out.println("Prava putanja ucitavanja: " + fullPath);
		try {
			
			JSONArray amenities = (JSONArray) jsonParser.parse(new FileReader(fullPath));	

			for(Object o : amenities) {
				JSONObject amenityJSON = (JSONObject) o;
				
				String title = (String) amenityJSON.get("title");
				int id = ((Long) amenityJSON.get("id")).intValue();
				boolean deleted = (Boolean) amenityJSON.get("deleted");
				
				if(deleted)
					continue;
				
				Amenity amenity = new Amenity(id,title);
				amenity.setDeleted(deleted);
				
				this.amenities.add(amenity);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Nije nasao fajl");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Vrv pars exception");
			e.printStackTrace();
		}
		
		System.out.println("Ucitana vozila");
	}
	
	@SuppressWarnings("unchecked")
	public void saveAmenity(Amenity amenity) {
		JSONParser jsonParser = new JSONParser();
		String fullPath = path + "/res/db/amenities.json";
		try {
			
			JSONArray amenitiesArray = (JSONArray) jsonParser.parse(new FileReader(fullPath));	
			
			JSONObject amenityJSON = new JSONObject();
			amenityJSON.put("id", amenity.getId());
			amenityJSON.put("title",amenity.getTitle());
			amenityJSON.put("deleted", amenity.isDeleted());
			
			
			amenitiesArray.add(amenityJSON);

			FileWriter file = new FileWriter(fullPath);
            file.write(amenitiesArray.toJSONString());
            file.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Nije nasao fajl");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Vrv pars exception");
			e.printStackTrace();
		}
		
	}
}
