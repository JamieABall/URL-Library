/******************************************************************************************************************
Class: WebLink.java
A wrapper class for URLs created for ease of use.
@Author: Jamison Ball
@Date: November 17th, 2016
@Last Updated: November 17th, 2016
********************************************************************************************************************/
import java.net.URL;

public class WebLink {
	private String name;
	private String url;
	private String dom_extension;
	
	public WebLink() {
		name = "Google";
		url = "http://www.google.com";
		dom_extension = ".com";
	}
	
	public WebLink(String reference, String address, String ext) {
		name = reference;
		url = address;
		dom_extension = ext;
	}
	
	public WebLink(String reference, URL address) {
		name = reference;
		url = address.toString();
		dom_extension = "";
	}
	
	public WebLink(String address) {
		name = "";
		url = address;
		dom_extension = "";
	}
	public WebLink(URL address) {
		name = "";
		url = address.toString();
		dom_extension = "";
	}
	
	public String getName() {
		return name;
	}
	
	public String getURL() {
		return url;
	}
	
	public void setURL(String addr) {
		url = addr;
	}
	
	public String getDomExtension() {
		return dom_extension;
	}
	
	public void setName(String ref) {
		name = ref;
	}
	
	public void setDomExtention(String ext) {
		dom_extension = ext;
	}
	
	public boolean hasName() {
		return (!name.isEmpty());
	}
	
	public String toString() {
		if(hasName()) {
			return name + "," + url + "," + dom_extension;
		} else {
			return "none," + url + "," + dom_extension; 
		}
	}
}
