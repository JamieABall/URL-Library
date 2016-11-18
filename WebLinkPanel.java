/**************************************************************************************************************
Class: WebLinkPanel.java
Custom UI element for clickable URL panels
@Author: Jamison Ball
@Date Written: November 17th, 2016
@Last Updated: November 18th, 2016
***************************************************************************************************************/
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.Graphics;
import java.awt.Color;

public class WebLinkPanel extends JPanel {
	private JLabel linkLabel;
	private WebLink source;
	private boolean selected = false;
	private AppUI master;
  
	public WebLinkPanel(AppUI root, WebLink link) {
		super();
		
		if(link.hasName() && URLHandler.WebLinkSupported(link)) {
			linkLabel = new JLabel(link.getName());
			source = link;
		} else if(!(link.hasName()) && URLHandler.WebLinkSupported(link)) {
			if(link.getURL().contains(link.getDomExtension())) {
				linkLabel = new JLabel(link.getURL());
			} else {
				linkLabel = new JLabel(link.getURL() + link.getDomExtension());
			}
			source = link;
		} else {
			linkLabel = new JLabel("UNSUPPORTED URL");
			source = new WebLink();
		}
		master = root;
		linkLabel.addMouseListener(new MouseEventManager());
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(Box.createHorizontalGlue());
		add(linkLabel);
		add(Box.createHorizontalGlue());
		setSize(linkLabel.getSize().width, linkLabel.getSize().height);
	}
	
	public JLabel getLabel() {
		return linkLabel;
	}
	
	public void setLabel(String text) {
		linkLabel.setText(text);
	}
	
	public AppUI getRootComponent() {
		return master;
	}
	
	public void setSelected(boolean value) {
		selected = value;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setLabelAndSource(WebLink link) {
		if(link.hasName() && URLHandler.WebLinkSupported(link)) {
			source = link;
			setLabel(source.getName());
		} else if(!(link.hasName()) && URLHandler.WebLinkSupported(link)) {
			source = link;
			setLabel(source.getURL() + source.getDomExtension());
		} else {
			System.out.println("URL is not supported.");
			return;
		}
	}
	
	public void update(Graphics g) {
		super.update(g);
		
		if(selected) {
			linkLabel.setForeground(Color.red);
		} else {
			linkLabel.setForeground(Color.black);
		}
	}
	
	public WebLink getSource() {
		return source;
	}
	
	public String toString() {
		return "Panel: " + linkLabel.getText() + ", source: " + source.getURL();
	}
}
