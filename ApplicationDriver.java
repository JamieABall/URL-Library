/******************************************************************************************************************************
Class:ApplicationDriver.java
The inital entry point of the application.
@Author: Jamison Ball
@Date Written: November 17th, 2016
@Last Updated: November 17th, 2016
*******************************************************************************************************************************/
import java.net.URL;
import java.net.URI;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.io.IOException;

public class ApplicationDriver {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AppUI instance = new AppUI();
			}
		});
	}
}
