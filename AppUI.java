/***************************************************************************************************************************
Class: AppUI.java
The base UI of the application.
@Author: Jamison Ball
@Date Written: November 17th, 2016
@Last Updated: November 17th, 2016
*****************************************************************************************************************************/
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class AppUI extends JFrame {
	private UserInputPanel uiPanel;
	private File libFile;
	private int urlCount;
	private ArrayList<WebLink> urls;
	private WebLinkPanel counterPanel;
	private ArrayList<WebLinkPanel> panels;
	
	public AppUI() {
		try {
			uiPanel = new UserInputPanel(this);
			libFile = new File("vid lib.txt");
		
			libFile.createNewFile();
			setTitle("Video Library II");
			setLayout(new GridLayout(0, 1));
			add(uiPanel);
			AppUtil.initUrlCount(this);
			
			if(urlCount > 0) {
				urls = new ArrayList<WebLink>(urlCount);
				panels = new ArrayList<WebLinkPanel>(urlCount);
			} else {
				urls = new ArrayList<WebLink>();
				panels = new ArrayList<WebLinkPanel>();
			}
			AppUtil.readUrls(this);
			AppUtil.generateWebLinkPanels(this);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			pack();
			setVisible(true);
		} catch(IOException e) {
			System.out.println("FATAL: UNABLE TO BUILD UI.") ;
				System.exit(1);
			
		}
	}
	
	public ArrayList<WebLink> getUrlStorage() {
		return urls;
	}
	
	public void incrementUrlCount() {
		++urlCount;
	}
	
	public void setUrlCount(int count) {
		urlCount = count;
	}
	
	public int getUrlCount() {
		return urlCount;
	}
	
	public ArrayList<WebLinkPanel> getUrlPanels() {
		return panels;
	}
  //src:http://stackoverflow.com/questions/19005410/check-if-some-exe-program-is-running-on-the-windows
	private String toString(InputStream stream) {
		Scanner scanner = new Scanner(stream, "UTF-8").useDelimiter("\\A");
		String task = scanner.hasNext() ? scanner.next():"";
		scanner.close();
		return task;
	}
	//based on code found at http://www.stackoverflow.com
	private boolean cmdln_active() {
		try {
			ProcessBuilder builder = new ProcessBuilder("tasklist.exe");
			Process tasklist_process = builder.start();
			String tasklist_actual = toString(tasklist_process.getInputStream());
			return tasklist_actual.contains("cmd.exe");
		} catch(Exception e) {
			System.out.println("Error: Unable to do lookup.");
			return false;
		}
	}
	
	public UserInputPanel getUserInputComponent() {
		return uiPanel; //you can get the input field text with the UserInputPanel that this function returns
	}
	
	public void close() {
		try {
			if(cmdln_active()) {
				Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
				setVisible(false);
				dispose();
			} else {
				setVisible(false);
				dispose();
			}
		} catch(Exception e) {
			setVisible(false);
			dispose();
		}
	}
	
}
