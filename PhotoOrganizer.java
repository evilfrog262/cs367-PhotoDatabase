///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            PhotoOrganizer
// Files:            Photo.java, PhotoDatabase.java
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
import java.io.*;
import java.util.*;

/**
 * Creates and uses a PhotoDatabase to represent and process information about 
 * photos and slideshows. The photo and slideshow information is read from a 
 * text file and then the program processes user commands. 
 *
 * <p>Bugs: None known.
 *
 * @author Kristin and Will
 */
public class PhotoOrganizer {
	/**
	  * Creates and uses a PhotoDatabase to represent and process information about 
	  * photos and slideshows. The photo and slideshow information is read from a 
	  * text file and then the program processes user commands. 
	  * @param args[] contains command line arguments
	  */
	public static void main(String[] args) {
    	//keep track of number of slideshows
		int numSlideshows = 0;
		//result of adding the number of photos in each slideshow
    	int photosPerSlide = 0;
    	//the number of photos in the slideshow with the most photos
    	int mostPhotos = 0;
    	//the number of photos in the slideshow with the least photos
    	int leastPhotos = 0;
    	//the average number of photos per slideshow
    	int avgP = 0;
    	//keeps track of whether this is the first photo or slideshow being compared
    	boolean first = true;
    	//a list holding the names of slideshows added
    	ArrayList<String> slideshowList = new ArrayList<String>();
    	
        //if the incorrect number of args entered, display correct usage and exit
    	if (args.length != 1) {
    		System.out.println("Usage: java PhotoOrgranizer FileName");
    		System.exit(0);
    	}
    	
    	//create file object to hold text file
    	File inFile = null;
    	//create scanner object to read file
    	Scanner file = null;
    	//create a photodatabase to hold photos read in from file
    	PhotoDatabase database = new PhotoDatabase();
    	
    	//if the filename is not null, get the file
    	if (args[0] != null) {
    		inFile = new File(args[0]);
    	}
    	
    	//try to read the file, print error message if unable and exit
    	try {
    		file = new Scanner(inFile);
    	} catch (FileNotFoundException ex) {
    		System.out.println("Error: Cannot access input file");
    		System.exit(0);
    	}
    	
    	//go through each line in file one by one
    	while (file.hasNextLine()) {
    		String line = file.nextLine();   //separate each token in the line by commas
    		String delims = "[,]";           //put tokens in an array
    		String[] tokens = line.split(delims);  //first token is a slideshow name
    		String slideshow = tokens[0].toLowerCase();	 
    		//convert to lowercase and add to list of slideshows
    		slideshowList.add(tokens[0].toLowerCase());
    		numSlideshows++;
    
    		//go through the remaining tokens
    		for (int i = 1; i < tokens.length; i++) {
    			//convert token to lowercase
    			String photo = tokens[i].toLowerCase();
    			//check if the photo is already in the database
    			if (!database.containsPhoto(photo)) {
    				database.addPhoto(photo);
    			}
    			//also add the slideshow to that photo's list of slideshows
    			database.addSlideshow(slideshow,photo);
    		}
    	}
    	
        Scanner stdin = new Scanner(System.in);  // for reading console input
        printOptions();
        boolean done = false;
        while (!done) {
            System.out.print("Enter option ( dfhilqr ): ");
            String input = stdin.nextLine();
            input = input.toLowerCase();  // convert input to lower case

            // only do something if the user enters at least one character
            if (input.length() > 0) {
                char choice = input.charAt(0);  // strip off option character
                String remainder = "";  // used to hold the remainder of input
                if (input.length() > 1) {
                    // trim off any leading or trailing spaces
                    remainder = input.substring(1).trim(); 
                }

                switch (choice) {
                
                case 'd':
                	//check if slideshow is in the database
                    if(!slideshowList.contains(remainder)){
                    	//print error message if not found
                    	System.out.println("slideshow not found");
                    }
                    else {
                    	//if it exists, remove it
                    	if(database.removeSlideshow(remainder)){
                    		System.out.println("slideshow deleted");
                    		//also remove from slideshow list
                    		slideshowList.remove(slideshowList.indexOf(remainder));
                    	}
                    }
                    break;


                case 'f':
                	//check if photo is in database
                    if (!database.containsPhoto(remainder)) {
                    	//print error if not found
                    	System.out.println("photo not found");
                    }
                    else {
                    	//get list of slideshows for the given photo
                    	List<String> slideshows= database.getSlideshows(remainder);
                    	//print out list
                    	System.out.print(remainder + ": ");
                    	for (int i = 0; i < slideshows.size() - 1; i++) {	
                    		System.out.print(slideshows.get(i) + ",");
                    	}
                    	//to eliminate comma after last slideshow name
                    	System.out.println(slideshows.get(slideshows.size() - 1));
                    }
                    break;

                case 'h': 
                    printOptions();
                    break;

                case 'i':
                	//vars explained above
                	photosPerSlide = 0;
                	mostPhotos = 0;
                	leastPhotos = 0;
                	avgP = 0;
                	
                	//Part 1: count of slideshows and photos
                	System.out.println("Slideshows: " + slideshowList.size() + ", Photos: "
                			+ database.size());
                	
                	
                	//part 2: # slideshows/photo
                	//true if photo is first to be compared
                	first = true;
                	//# slideshows photo with most has
                	int mostS = 0;
                	//# slideshows photo with least has
                	int leastS = 0;
                	//average # slideshows/photo
                	int avgS = 0;
                	
                	//a list of slideshow names
                	List<String> slideshows = new ArrayList<String>();
                	//an iterator to traverse database
                	Iterator<Photo> slideCheck = database.iterator();
                	
                	//check all photos in database
                	while(slideCheck.hasNext()){
                		//get list of slideshows for given photo
                		slideshows = slideCheck.next().getSlideshows();
                		//if first to be compared, set least value equal to this
                		if (first) {
                			leastS = slideshows.size();
                			first = false;
                		}
                		//if bigger than most, update
                		if (slideshows.size() > mostS) {
                			mostS = slideshows.size();
                		}
                		//if smaller than least, update
                		if (slideshows.size() < leastS) {
                			leastS = slideshows.size();
                		}
                		//sum up # slideshows of all photos
                		avgS += slideshows.size();
                	}
                	
                	//divide total # slideshows for all photos by numPhotos
                	avgS = avgS/database.size();
                	//display results
                	System.out.println("# of slideshows/photo: most " + mostS 
                			+ ", least " + leastS + ", average " + avgS);

                	
                	//part 3: # photos/slideshow
                	first = true;
                	photosPerSlide = 0;
                	
                	//go through all slideshows
                	for (int i = 0; i < slideshowList.size(); i++) {
                		//get the number of photos they contain and add to total
                		photosPerSlide += database.getPhotos(slideshowList.get(i)).size();
                	}
                	
                	//make sure the list isn't empty
                	if (slideshowList.size() != 0){
                		//calculate average by dividing by numSlideshows
                		avgP = photosPerSlide/slideshowList.size();
                	}
                	
                	//go through all slideshows again
                	for (int i = 0; i < slideshowList.size(); i++) {
                		//if first to be compared, set least equal to this numPhotos
                		if (first) {
                			leastPhotos = database.getPhotos(slideshowList.get(i)).size();
                			first = false;
                		}
                		//if less than least, update
                		if (leastPhotos > database.getPhotos(slideshowList.get(i)).size()) {
                			leastPhotos = database.getPhotos(slideshowList.get(i)).size();
                		}
                	}
                	//if more than most, update
                	for (int i = 0; i < slideshowList.size(); i++) {
                		if (mostPhotos < database.getPhotos(slideshowList.get(i)).size()) {
                			mostPhotos = database.getPhotos(slideshowList.get(i)).size();
                		}	/*for (int i = 0; i < largestSlideshows.size() - 1; i++) {
                		System.out.print(largestSlideshows.get(i) + ", "); 
                    	}*/
                	}
                	
                	//display results
                	System.out.println("# of photos/slideshow: most " 
                			+ mostPhotos + ", least " + leastPhotos + ", average " 
                			+ avgP);

                	
                	//part 4: favorite photo
                	slideCheck = database.iterator();
                	//create array to store favorites
                	ArrayList<Photo> mostUsedP = new ArrayList<Photo>();
                	Photo photo2;
                	//num of slideshows favorite has
                	int mostFrequent = 0;

                	//go through all photos
                	while(slideCheck.hasNext()){
                		photo2 = slideCheck.next();
                		//if greater than most frequent, update
                		if(mostFrequent < photo2.getSlideshows().size()){
                			mostFrequent = photo2.getSlideshows().size();
                			mostUsedP = new ArrayList<Photo>();
                			mostUsedP.add(photo2);
                		}
                		//if equal to most frequent, add to list
                		else if(mostFrequent == photo2.getSlideshows().size()){
                			mostUsedP.add(photo2);
                		}
                	}
                	
                	//print results in order they appear in database
                	System.out.print("Favorite Photo: ");
                	for(int i = 0; i < mostUsedP.size() - 1; i++){
                		System.out.print(mostUsedP.get(i).getName() + ",");
                	}
                	System.out.println(mostUsedP.get(mostUsedP.size() - 1).getName() + 
                			"[" + mostFrequent + "]");
                	
                	
                	//part 5: largest slideshow
                	//array to store largest slideshows
                	ArrayList<String> largestSlideshows = new ArrayList<String>();
                	//# photos in largest slideshow
                	int numPhotos = 0;
                	
                	//go through all slideshows
                	for (int i = 0; i < slideshowList.size(); i++) {
                		//if larger than largest, update
                		if (database.getPhotos(slideshowList.get(i)).size() > numPhotos) {
                			numPhotos = database.getPhotos(slideshowList.get(i)).size();
                			largestSlideshows = new ArrayList<String>();
                			largestSlideshows.add(slideshowList.get(i));
                		}
                		//if same size, add to list
                		else if (database.getPhotos(slideshowList.get(i)).size() == numPhotos) {
                			largestSlideshows.add(slideshowList.get(i));
                		}
     
                	}
                	
                	//sort into alphabetical order
                	boolean swapped = true;
                    int j = 0;
                    String tmp;
                    while (swapped) {
                          swapped = false;
                          j++;
                          for (int i = 0; i < largestSlideshows.size() - j; i++) {                                       
                                if (largestSlideshows.get(i).compareTo(largestSlideshows.get(i + 1)) > 0) {                          
                                      tmp = largestSlideshows.get(i);
                                      largestSlideshows.set(i,largestSlideshows.get(i + 1));
                                      largestSlideshows.set(i + 1, tmp);
                                      swapped = true;
                                }
                          }                
                    }
                	
                	//print results
                	System.out.print("Largest slideshow: ");
                	//print out list of slideshows
                	for (int i = 0; i < largestSlideshows.size() - 1; i++) {
                		System.out.print(largestSlideshows.get(i) + ", "); 
                	}
                	//make sure there are slideshows in the list
                	if (largestSlideshows.size() != 0 && largestSlideshows.get(largestSlideshows.size() - 1) != null) {
                		System.out.println(largestSlideshows.get(largestSlideshows.size() - 1) +
                    			"[" + mostPhotos + "]");
                	}
                	//if not, display error
                	else {
                		System.out.println("No slideshows");
                	}
                	break;
                	
                case 'l':
                	//if slideshow not in database, display error
                	if (!database.containsSlideshow(remainder)) {
                		System.out.println("slideshow not found");
                	}
                	else {
                		//get list of photos for given slideshow
                		List<String> photosInS = database.getPhotos(remainder);
                		//display
                		System.out.print(remainder + ":");
                		for (int i = 0; i < photosInS.size() - 1; i++) {
                			System.out.print(photosInS.get(i) + ",");
                		}
                		System.out.println(photosInS.get(photosInS.size() - 1));
                	}
                    break;
                    
                case 'q':
                    done = true;
                    System.out.println("quit");
                    break;

                case 'r':
                	//if photo not in database, display error
                   if(!database.containsPhoto(remainder)) {
                	   System.out.println("photo not found");
                   }
                   else {
                	   //remove photo from database
                	   database.removePhoto(remainder);
                	   //check all slideshows in list
                	   for (int i = 0; i < slideshowList.size(); i++) {
                		   //if they are now empty, remove them
                		   if (database.getPhotos(slideshowList.get(i)) == null) {
                			   database.removeSlideshow(slideshowList.get(i));
                			   slideshowList.remove(i);
                		   }
                	   }
                	   System.out.println("photo removed");
                   }
                   break;

                default:  // ignore any unknown commands
                    break;
                }
            }
        }

    }

    /**
     * Prints the list of command options along with a short description of
     * one.  This method should not be modified.
     */
    private static void printOptions() {
        System.out.println("d <slideshow> - delete the given <slideshow>");
        System.out.println("f <photo> - find the given <photo>");
        System.out.println("h - display this help menu");
        System.out.println("i - display information about this photo database");
        System.out.println("l <slideshow> - lookup the given <slideshow>");
        System.out.println("q - quit");
        System.out.println("r <photo> - remove the given <photo>");
    }      
    
}
