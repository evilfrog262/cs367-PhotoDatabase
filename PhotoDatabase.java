///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  PhotoOrganizer.java
// File:             PhotoDatabase.java
// Semester:         Spring 2012
//
// Author:           Will Kraus
// CS Login:         kraus	
// Lecturer's Name:  Beck Hasti
// Lab Section:      Lecture 2
//
//                   PAIR PROGRAMMERS COMPLETE THIS SECTION
// Pair Partner:     Kristin Cox
// CS Login:         kcox
// Lecturer's Name:  Beck Hasti
// Lab Section:      Lecture 1
//
//                   STUDENTS WHO GET HELP FROM ANYONE OTHER THAN THEIR PARTNER
// Credits:          n/a
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.ArrayList;
import java.util.*;

/**
 * Stores Photo objects in a list and allows a user to manipulate them.
 *
 * @author Kristin and Will
 */
public class PhotoDatabase {
	//list to hold photo objects
	private ArrayList<Photo> database;

	/**
	  * Constructs a photo database.
	  */
	public PhotoDatabase () {
		this.database = new ArrayList<Photo>();
	}
	
	/**
	  * Returns the photo database.
	  * 
	  * @return PhotoDatabase that you want that contains an ArrayList of Photos
	  */
	public ArrayList<Photo> getDatabase () {
		return this.database;
	}
	
	/**
	  * Adds a Photo object to the database
	  *
	  * @param String p -- the name of the photo to be added
	  */
	void addPhoto(String p){
		if(p == null){
			throw new IllegalArgumentException();
		}
		Photo temp = new Photo (p);
		database.add(temp);
	}
	
	/**
	  * Add the given slideshow s to photo p in the database. If photo p is not 
	  * in the database throw a java.lang.IllegalArgumentException. If s is 
	  * already in the list of slideshows associated with photo p, return. 
	  *
	  * @param String s -- name of slideshow to be added
	  * @param String p -- name of photo to be added
	  */
	void addSlideshow(String s, String p) {
		if (s == null || p == null) {
			throw new IllegalArgumentException();
		}
		//var to hold photo object when retrieved
		Photo temp;
		//count current index of database
		int counter = 0;
		Iterator<Photo> itr = iterator();
		//go through all photos
		while (itr.hasNext()) {
			//check if name equals given string
			if (itr.next().getName().equals(p)) {
				//if it does get photo at that index
				temp = database.get(counter);
				//if it already has the slideshow, do nothing
				if (temp.getSlideshows().contains(s)) {
					return;
				}
				//otherwise add
				temp.getSlideshows().add(s);
				return;
			}
			counter++;
		}
	}
	
	/**
	  * Return true if photo p exists anywhere in the database.
	  *
	  * @param String p -- name of photo to be checked
	  * @return boolean -- true if photo exists in database
	  */
	boolean containsPhoto(String p){
		if(p == null){
			throw new IllegalArgumentException();
		}
		Iterator<Photo> itr = iterator();
		//look through photos
		while(itr.hasNext()){
			//if there is a match return true
			if(itr.next().getName().equals(p)){
				return true;
			}
		}
		return false;
	}
	
	/**
	  * Return true if slideshow s exists anywhere in the database.
	  *
	  * @param String s -- name of slideshow to be checked
	  * @return boolean -- true if slideshow exists in database
	  */
	boolean containsSlideshow(String s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		//look through all photos
		Iterator<Photo> itr = iterator();
		while(itr.hasNext()){
		//if any photo contains given slideshow return true
			if(itr.next().getSlideshows().contains(s)){
			return true;
			}
		}
		return false;
	}
	
	/**
	  * Return true if slideshow s is in the list of slideshows for photo p.
	  *
	  * @param String p -- name of photo to be checked
	  * @param String s -- name of slideshow to be checked
	  * @return boolean -- true if photo p has slideshow s
	  */
	boolean hasSlideshow(String s, String p){
		if (s == null || p == null) {
			throw new IllegalArgumentException();
		}
		//count current index
		int counter = 0;
		//holder for wanted photo
		Photo temp;
		Iterator<Photo> itr = iterator();
		//check all photos
		while (itr.hasNext()) {
			//if match, check if it contains slideshow
			if (itr.next().getName().equals(p)) {
				temp = database.get(counter);
				if (temp.getSlideshows().contains(s)) {
					return true;
				}
			}
			counter++;
		}
		return false;
	}
	
	/**
	  * Return the list of photos for slideshow s. If slideshow s is not in the 
	  * database, return null. 
	  *
	  * @param String s -- name of slideshow to be checked
	  * @return List<String> -- list of photos for slideshow s
	  */
	public List<String> getPhotos(String s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		//list to hold photo names
		List<String> list = new ArrayList<String>();
		Iterator<Photo> itr = iterator();
		//check if slideshow is in database
		if (this.containsSlideshow(s)) {
			//go through all photos
			while(itr.hasNext()) {
				//if the photo has the slideshow, add to list
				String p = itr.next().getName();
				if (hasSlideshow(s, p)) {
					list.add(p);
				}
			}
		}
		if (list.size() > 0) {
			return list;
		}
		else {
			return null;
		}
	}
	
	/**
	  * Return the list of slideshows for the photo p. If a photo p is not in 
	  * the database, return null. 
	  *
	  * @param String p -- name of photo to be checked
	  * @return List<String> -- list of slideshows for photo p
	  */
	public List<String> getSlideshows(String p){
		if(p == null){
			throw new IllegalArgumentException();
		}
		Photo temp;
		int counter = 0;
		Iterator<Photo> itr = iterator();
		//go through all photos
		while (itr.hasNext()) {
			//check if match
			if (itr.next().getName().equals(p)) {
				//get list of slideshows for that photo
				temp = database.get(counter);
				return temp.getSlideshows();
			}
			counter++;
		}
		return null;
	}
	
	/**
	  * Returns an iterator over the photo objects in the database
	  *
	  * @return Iterator<Photo> over photos in database
	  */
	Iterator<Photo> iterator() {
		return database.iterator();
	}
	
	/**
	  * Remove photo p from the database. If photo p is not in the database, 
	  * return false; otherwise return true. 
	  *
	  * @param String p -- name of photo to be removed
	  * @return boolean -- false if photo is not in database, otherwise true
	  */
	boolean removePhoto(String p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		int counter = 0;
		Iterator<Photo> itr = iterator();
		//go through all photos
		while (itr.hasNext()) {
			//check if match
			if (itr.next().getName().equals(p)) {
				//remove photo at that index
				database.remove(counter);
				return true;
			}
			counter++;
		}
		return false;
	}
	
	/**
	  * Remove slideshow s from the database. If slideshow s is not in the 
	  * database, return false; otherwise return true. 
	  * 
	  * @param String s -- name of slideshow to be removed
	  * @return boolean -- false if slideshow is not in database, otherwise true
	  */
	boolean removeSlideshow(String s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		//true if slideshow has been removed
		boolean exists = false;
		int counter = 0;
		Iterator<Photo> itr = iterator();
		//go through all photos
		while (itr.hasNext()) {
			//check if they contain slideshow
			if (itr.next().getSlideshows().contains(s)) {
				//if they do, remove it
				database.get(counter).getSlideshows().remove(s);
				exists = true;
			}
			counter++;
		}
		return exists;
	}
	
	/**
	  * Return the number of photos in the database.
	  * 
	  * @return int -- number of photos in the database
	  */
	int size(){
		return database.size();
	}
}
