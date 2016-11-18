/****************************************************************************************************************************
Class: URLHandler.java
Generates WebLinks which are used by the application to browse and save URLs.
@Author: Jamison Ball
@Date: November 17th, 2016
@Last Updated: November 17th, 2016
*****************************************************************************************************************************/
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.net.MalformedURLException;
import java.io.IOException;


public class URLHandler {
	private static String testURL(String address) {
		boolean com = false;
		boolean net = false;
		boolean org = false;
		boolean tk = false;
		boolean inv = false;
		
		try {
			String comString = address + ".com";
			URL comURL = new URL(comString);
			URLConnection comConnection = comURL.openConnection();
			comConnection.connect();
			com = true;
		} catch(UnknownHostException e) {
			try {
				String netString = address + ".net";
				URL netURL = new URL(netString);
				URLConnection netConnection = netURL.openConnection();
				netConnection.connect();
				net = true;
			} catch(UnknownHostException ex) {
				try {
					String orgString = address + ".org";
					URL orgURL = new URL(orgString);
					URLConnection orgConnection = orgURL.openConnection();
					orgConnection.connect();
					org = true;
				} catch(UnknownHostException exx) {
					try {
						String tkString = address + ".tk";
						URL tkURL = new URL(tkString);
						URLConnection tkConnection = tkURL.openConnection();
						tkConnection.connect();
						tk = true;
					} catch(UnknownHostException exxx) {
						inv = true;
					} catch(MalformedURLException exxx) {
						System.out.println("The URL is improperly formatted.");
						System.exit(1);
					} catch(IOException exxx) {
						System.out.println("FATAL: A PROBLEM HAS OCCOURED.");
						System.exit(1);
					}
				} catch(MalformedURLException exx) {
					System.out.println("The URL is improperly formatted.");
					System.exit(1);
				} catch(IOException exx) {
					System.out.println("FATAL: A PROBLEM HAS OCCOURED.");
					System.exit(1);
				}
			} catch(MalformedURLException ex) {
				System.out.println("The URL is improperly formatted.");
				System.exit(1);
			} catch(IOException ex) {
				System.out.println("FATAL: A PROBLEM HAS OCCOURED.");
				System.exit(1);
			}
		} catch (MalformedURLException e) {
			
			System.out.println("The URL is improperly formatted.");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("FATAL: A PROBLEM HAS OCCOURED.");
			System.exit(1);
		} finally {
			if(!com && 
			   !net &&
			   !org &&
			   !tk  &&
			   !inv ) {
				   return ".interrupt";
			   } 
			   if(com) {
				   return ".com";
			   } 
			   if(net) {
				   return ".net";
			   } 
			   if(org) {
				   return ".org";
			   } 
			   if(tk) {
				   return ".tk";
			   } 
			   return ".inv";
			   
		}
	}
	
	public static String findDomExtension(String addr) {
		boolean hasExtension = (addr.contains(".com") || 
								addr.contains(".net") ||
								addr.contains(".org") ||
								addr.contains(".tk")  ||
								addr.contains(".io")  ||
								addr.contains(".tv")  ||
								addr.contains(".uk"));
		boolean extensionSupported = (!(addr.contains(".int")) &&
									  !(addr.contains(".edu")) &&
									  !(addr.contains(".gov")) &&
									  !(addr.contains(".mil")) &&
									  !(addr.contains(".arpa"))&& 
									  !(addr.contains(".io"))  &&
									  !(addr.contains(".tv"))  &&
									  !(addr.contains(".uk")));
								
		if(!hasExtension) {
			return testURL(addr);
		} else {
			if(extensionSupported) {
				try {
					URL verifyWithEx = new URL(addr);
					URLConnection addrConnection = verifyWithEx.openConnection();
					addrConnection.connect();
					return addr.substring(addr.lastIndexOf("."));
				} catch(UnknownHostException e) {
					return ".inv";
				} catch(MalformedURLException e) {
					return ".inv";
				} catch(IOException e) {
					System.out.println("ERROR: A PROBLEM HAS OCCOURED.");
					return ".err";
				}
			} else {
				return ".unsupported";
			}
		}
	}
	private static String evaluateProtocol(String address) {
		boolean http = address.startsWith("http");
		boolean httpcol = address.startsWith("http:");
		boolean httpcolbs = address.startsWith("http:/");
		boolean httpcolbsbs = address.startsWith("http://");
		boolean leadingWWW = address.startsWith("www");
		boolean leadingWWWDot = address.startsWith("www.");
		boolean hasWWW = address.contains("www");
		boolean hasWWWDot = address.contains("www.");
		
		if(httpcol || httpcolbs || httpcolbsbs) {
			http = false;
		}
		if(httpcolbs) {
			httpcol = false;
		}
		if(httpcolbsbs) {
			httpcolbs = false;
		}
		if(leadingWWW || leadingWWWDot) {
			http = httpcol = httpcolbs = httpcolbsbs = false;
		}
		
		if(leadingWWWDot) {
			leadingWWW = false;
		}
		
		if(leadingWWW) {
			leadingWWWDot = false;
		}
		
		if(hasWWWDot) {
			hasWWW = false;
		}
		
		if(hasWWW) {
			hasWWWDot = false;
		}
		if(leadingWWW) {
			address = address.replace("www", "http://www.");
		} else if(leadingWWWDot) {
			address = address.replace("www.", "http://www.");
		} else if(http && !hasWWWDot && !hasWWW) {
			address = address.replace("http", "http://www.");
		} else if(http && hasWWW) {
			address = address.replace("httpwww", "http://www.");
		} else if(http && hasWWWDot) {
			address = address.replace("httpwww.", "http://www.");
		} else if(httpcol && !hasWWW && !hasWWWDot) {
			address = address.replace("http:", "http://www.");
		} else if(httpcol && hasWWW) {
			address = address.replace("http:www", "http://www.");
		} else if(httpcol && hasWWWDot) {
			address = address.replace("http:www.", "http://www.");
		} else if(httpcolbs && !hasWWW && !hasWWWDot) {
			address = address.replace("http:/", "http://www.");
		} else if(httpcolbs && hasWWW) {
			address = address.replace("http:/www", "http://www.");
		} else if(httpcolbs && hasWWWDot) {
			address = address.replace("http:/www.", "http://www.");
		} else if(httpcolbsbs && !hasWWW && !hasWWWDot) {
			address = address.replace("http://", "http://www.");
		} else if(httpcolbsbs && hasWWW) {
			address = address.replace("http://www", "http://www.");
		} else if(httpcolbsbs && hasWWWDot){
			return address;
		} else if(!http && 
				  !httpcol && 
				  !httpcolbs && 
				  !httpcolbsbs && 
				  !leadingWWW && 
				  !leadingWWWDot && 
				  !hasWWW &&  
				  !hasWWWDot) {
					  address = "http://www." + address;
				  }
		return address;
	}
	public static boolean WebLinkSupported(WebLink target) {
		String ext = target.getDomExtension();
		
		return(!(ext.contains(".inv")) && !(ext.contains(".err")) && !(ext.contains(".unsupported")) && !(ext.contains(".interrupt")));
	}
	public static WebLink generateWebLink(String address) {
		address = evaluateProtocol(address);
		WebLink webAddr = new WebLink(address);
		webAddr.setDomExtention(findDomExtension(address));
		
		if(!webAddr.getURL().contains(findDomExtension(address))) {
			webAddr.setURL(webAddr.getURL() + findDomExtension(address));
		}
		return webAddr;
	}
	
	public static WebLink generateWebLink(String address, String alias) {
		WebLink namedAddr = generateWebLink(address);
		
		if(!alias.isEmpty()) {
			namedAddr.setName(alias);
		}
		return namedAddr;
	}
}
