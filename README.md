# URL-Library
Opens and saves supported URLs

This program supports keyboard controls:
ENTER- Navigate to the URL in the URL text field and close the application
CNTRL + S- Save the URL in the text field

Additionally you can name saved links by entering the URL in the url textfield and typing it's name next to the 
URL in parenthesis. Named URLs will appear on the GUI. To select an already saved URL click on the name of the desired URL. The
address of the URL will populate in the text field, and the URL's label will turn red. From there, press the user can press ENTER
as normal to navigate to the URL. If the URL does not have a name, then the web address is used in place of a name. 

If a saved URL is not supported by this application, the resulting generated panel will have the name "UNSUPPORTED URL", 
and the destination URL of "http://google.com".

Supported domains include:
.com
.net
.org
.tk

Notes
It is within the capabilities of this application to enter a URL which does not contain a protocol(e.g. http://) or any domain.
Doing this, however, is not recommended as the program will generate the first valid URL (e.g. entering "youtube" into the url field
will generate a link to http://www.youtube.com). It is suggested therefore that users enter the desired website's domain in 
addition to it's name (e.g. replace "youtube" by "youtube.com") to ensure that the applcation opens the desired URL.

IMPORTANT USAGE INFORMATION
This application is currently for use on Windows Operating systems running Java 7 or newer.
