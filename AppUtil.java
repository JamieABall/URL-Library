/*****************************************************************************************************************************
Class: AppUtil.java
Utility functions performed on application start up and during UI generation
@Author: Jamison Ball
@Date: November 17th, 2016
@Last Updated: November 17th, 2016
******************************************************************************************************************************/
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class AppUtil {
	public static void initUrlCount(AppUI appInstance) {
		int numUrls = 0;
		try {
			File lib = new File("vid lib.txt");
			lib.createNewFile();
			
			Scanner counter = new Scanner(lib);
			
			while(counter.hasNextLine()) {
				String temp = counter.nextLine();
				++numUrls;
			}
			appInstance.setUrlCount(numUrls);
		} catch(IOException e) {
			System.out.println("No file \"vid lib.txt\" exists. It will be generated.");
			
			try {
				File catch_lib = new File("vid lib.txt");
				catch_lib.createNewFile();
				appInstance.setUrlCount(0);
			} catch(IOException ex) {
				System.out.println("FATAL: UNABLE TO GENERATE FILE \"vid lib.txt\".");
				System.exit(1);
			}
		}
	}
	
	public static void saveUrl(AppUI appInstance) {
			try {
				File outFile = new File("vid lib.txt");
				outFile.createNewFile();
				BufferedWriter writer;
				FileWriter outWriter = new FileWriter(outFile, true);
				writer = new BufferedWriter(outWriter);
				UserInputPanel panel = appInstance.getUserInputComponent();
				String url = panel.getInputFieldText();
			
				if(url.contains("(") && url.contains(")")) {
					WebLink link = URLHandler.generateWebLink(url.substring(0, url.indexOf("(")), url.substring(url.indexOf("(") + 1, url.indexOf(")")));
					writer.write(link.toString() + '\n');
					writer.close();
				} else {
					WebLink default_link = URLHandler.generateWebLink(url);
					writer.write(default_link.toString() + '\n');
					writer.close();
				}
			} catch(IOException e) {
				File catch_file = new File("vid lib.txt");
				try {
					if(!catch_file.createNewFile()) {
						System.out.println("ERROR: A PROBLEM HAS OCCOURED WHILE SAVING.");
						return;
					} else {
						System.out.println("ERROR: THE FILE \" vid lib.txt\" COULD NOT BE CREATED.");
						return;
					}
				} catch(IOException ex) {
					System.out.println("FATAL: CANNOT DETERMINE WHETHER FILE \"vid lib.txt\" EXISTS.");
					System.exit(1);
				}
			}
		 
	}
	
	public static void readUrls(AppUI appInstance) {
		try {
			Scanner fileReader;
			File target = new File("vid lib.txt");
			ArrayList<WebLink> storage = appInstance.getUrlStorage();
			int numUrls = appInstance.getUrlCount();
			target.createNewFile();
			String[] tmpStorage = new String[numUrls];
			fileReader = new Scanner(target);
			int count = 0;
		
			//read the file
			if(!(numUrls == 0)) {
				while(fileReader.hasNextLine()) {
					tmpStorage[count] = fileReader.nextLine();
					++count;
				}
			}
			//parse the strings
			//build WebLinks
			//insert them into the array list
			for(String s: tmpStorage) {
				String[] buildStorage = s.split(",");
			
				if(!(buildStorage[0].equals("none")) && !(appInstance.getUrlCount() == 0)) {
					storage.add(URLHandler.generateWebLink(buildStorage[1], buildStorage[0]));
				} else {
					storage.add(URLHandler.generateWebLink(buildStorage[1]));
				}
			}
		} catch(IOException e) {
			try {
				File catch_file = new File("vid lib.txt");
				boolean file_exists = !catch_file.createNewFile();
				
				if(file_exists) {
					System.out.println("ERROR: CANNOT READ FILE \" vid lib.txt.\"");
					return;
				}
			} catch(IOException ex) {
				System.out.println("FATAL: CANNOT DETERMINE WHETHER FILE \"vid lib.txt\" EXISTS.");
				System.exit(1);
			}
		}
	}
	
	public static void generateWebLinkPanels(AppUI appInstance) {
		ArrayList<WebLink> storedUrls = appInstance.getUrlStorage();
		ArrayList<WebLinkPanel> totalPanels = appInstance.getUrlPanels();
		for(WebLink w: storedUrls) {
			WebLinkPanel panel = new WebLinkPanel(appInstance, w);
			totalPanels.add(panel);
			appInstance.add(panel);
		}
	}
}
