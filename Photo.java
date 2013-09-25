import java.util.ArrayList;
import java.util.List;
import java.util.*;
	/**
	 * The Photo class is used to represent a photo that keeps track of its name
	 * and a list of slideshows in which it appears.
	 * 
	 * @author Beck Hasti, CS 367 instructor, copyright 2012
	 */
	public class Photo {
	    private String name;                // the photo's name      
	    private List<String> slideshows;   // the list of slidesows
	    
	    /**
	     * Constructs a photo with the given name and an empty list of slideshows.
	     * 
	     * @param name the name of this photo
	     */
	    public Photo(String name)     {
	        this.name = name;
	        this.slideshows = new ArrayList<String>();
	    }
	    
	    /**
	     * Return the name of this photo.
	     * 
	     * @return the name of the photo
	     */
	    public String getName() { 
	        return name;
	    }
	    
	    /**
	     * Return the list of slideshows for this photo.
	     * 
	     * @return the list of slideshows
	     */
	    public List<String> getSlideshows() {
	        return slideshows;
	    }
}

