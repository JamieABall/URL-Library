/**********************************************************************************************************************************
Class: KeyboardEventManager
Listens for specific commands from the keyboard and executes the corresponding event
@Author: Jamison Ball
@Date: November 17th, 2016
@Last Updated: November 17th, 2016
**********************************************************************************************************************************/
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Desktop;
import java.awt.Color;
import javax.swing.JTextField;
import java.net.URI;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class KeyboardEventManager implements KeyListener {
	private AppUI appInstance;
	private Color defaultSelectionColor = new Color(184, 207, 229);
	public KeyboardEventManager(){
		
	}
	
	public void keyPressed(KeyEvent e) {
		Object e_src = e.getSource();
		//note to self: ENTER: GO TO WEBSITE CTRL + S: SAVE URL
		if(e_src instanceof JTextField) {
			JTextField instance = (JTextField)e_src;
			
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				if(instance.getText().isEmpty()) {
					return;
				} else {
					try {
						String siteData = instance.getText();
				
						if((siteData.contains("(")) && (siteData.contains(")"))) {
							siteData = siteData.substring(0, siteData.lastIndexOf("("));
							WebLink link = URLHandler.generateWebLink(siteData); //if I'm going to the site I don't care about the alias
							URI siteLocation = new URI(link.getURL());
								
							if(Desktop.isDesktopSupported()) {
								Object fatherComponent = instance.getParent();
								
								if(fatherComponent instanceof UserInputPanel) {
									UserInputPanel fatherPanel = (UserInputPanel)fatherComponent;
									fatherPanel.getMasterComponent().close();
									
								}
								Desktop.getDesktop().browse(siteLocation);
							}
						   	
						} else if(siteData.contains("(") && (!siteData.contains(")"))) {
								instance.setForeground(Color.red);	   
						} else if(!(siteData.contains("(")) && (siteData.contains(")"))) {
								instance.setForeground(Color.red);
						} else {
							WebLink default_link = URLHandler.generateWebLink(siteData);
							URI default_site_location = new URI(default_link.getURL());
								
							if(Desktop.isDesktopSupported()) {
								Object fatherComponent = instance.getParent();
									
								if(fatherComponent instanceof UserInputPanel) {
									UserInputPanel fatherPanel = (UserInputPanel)fatherComponent;
									fatherPanel.getMasterComponent().close();
								}
								Desktop.getDesktop().browse(default_site_location);
							}
						}						
					
					} catch(IOException ex) {
						System.out.println("FATAL: UNABLE TO OPEN URL.");
						System.exit(1);
					} catch(URISyntaxException ex) {
						System.out.println("FATAL: UNABLE TO OPEN URL-BAD SYNTAX.");
						System.exit(1);
					}
				}
			} else if(e.getKeyCode() == KeyEvent.VK_S && e.isControlDown()) {
				if(instance.getText().isEmpty()) {
					return;
				} else {
					Object fatherComponent = instance.getParent();
				
					if(fatherComponent instanceof UserInputPanel) {
						UserInputPanel fatherPanel = (UserInputPanel)fatherComponent;
						AppUI rootComponent = fatherPanel.getMasterComponent();
						
						if(instance.getText().contains("(") && instance.getText().contains(")")) {
							WebLink saved_link = URLHandler.generateWebLink(instance.getText().substring(0, 
													instance.getText().indexOf("(")), 
													instance.getText().substring(instance.getText().indexOf("(") + 1,
																				 instance.getText().indexOf(")")));
							AppUtil.saveUrl(rootComponent);
							rootComponent.incrementUrlCount();
							rootComponent.getUrlStorage().ensureCapacity(rootComponent.getUrlCount());
							rootComponent.getUrlStorage().add(saved_link);
							
							WebLinkPanel link_panel = new WebLinkPanel(rootComponent, saved_link);
							instance.setText("");
							rootComponent.remove(rootComponent.getUserInputComponent());
							ArrayList<WebLink> currentLinks = rootComponent.getUrlStorage();
							ArrayList<WebLinkPanel> linkPanels = rootComponent.getUrlPanels();
							
							for(WebLinkPanel wlp: linkPanels) {
								rootComponent.remove(wlp);
							}
							currentLinks.clear();
							AppUtil.readUrls(rootComponent);
							rootComponent.add(rootComponent.getUserInputComponent());
							rootComponent.getUserInputComponent().getInputField().requestFocus();
							AppUtil.generateWebLinkPanels(rootComponent);
							rootComponent.pack();
						} else if(instance.getText().contains("(") && !instance.getText().contains(")")) {
							instance.setForeground(Color.red);
						} else if(!instance.getText().contains("(") && instance.getText().contains(")")) {
							instance.setForeground(Color.red);
						} else {
							WebLink default_saved_link = URLHandler.generateWebLink(instance.getText());
							AppUtil.saveUrl(rootComponent);
							rootComponent.incrementUrlCount();
							rootComponent.getUrlStorage().ensureCapacity(rootComponent.getUrlCount());
							rootComponent.getUrlStorage().add(default_saved_link);
							
							WebLinkPanel default_link_panel = new WebLinkPanel(rootComponent, default_saved_link);
							instance.setText("");
							rootComponent.remove(rootComponent.getUserInputComponent());
							ArrayList<WebLinkPanel> linkPanels = rootComponent.getUrlPanels();
							ArrayList<WebLink> currentLinks = rootComponent.getUrlStorage();
								
							for(WebLinkPanel wlp: linkPanels) {
								rootComponent.remove(wlp);
							}
							currentLinks.clear();
							AppUtil.readUrls(rootComponent);
							rootComponent.add(rootComponent.getUserInputComponent());
							rootComponent.getUserInputComponent().getInputField().requestFocus();
							AppUtil.generateWebLinkPanels(rootComponent);
							rootComponent.pack();
						}
					}
				}
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		Object e_src = e.getSource();
		
		if(e_src instanceof JTextField) {
			JTextField siteData = (JTextField)e_src;
			
			if((siteData.getText().contains("(")) && (siteData.getText().contains(")"))) {
				if(!siteData.getText().substring(siteData.getText().indexOf("(") + 1, siteData.getText().indexOf(")")).isEmpty()) {
					siteData.select(siteData.getText().indexOf("(") + 1, siteData.getText().indexOf(")"));
					siteData.setSelectionColor(Color.green);
				}
				siteData.setForeground(Color.black);
			} else if(!(siteData.getText().contains("(")) && !(siteData.getText().contains(")"))) {
				siteData.setForeground(Color.black);
			}
			
			if(!(siteData.getText().contains("(") && siteData.getText().contains(")")) ||
				(siteData.getText().contains("(") && !siteData.getText().contains(")")) ||
				(!siteData.getText().contains("(") && siteData.getText().contains(")")) ||
				  siteData.getText().isEmpty()) {
					siteData.setSelectionColor(defaultSelectionColor);
				}
		}
	}
	
	public void keyTyped(KeyEvent e) {
		Object e_src = e.getSource();
		
		if(e_src instanceof JTextField) {
			JTextField instance = (JTextField)e_src;
			instance.setCaretPosition(instance.getCaretPosition());
		}
	}
}
