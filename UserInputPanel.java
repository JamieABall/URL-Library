/********************************************************************************************************************
Class: UserInputPanel.java
Custom UI element for URL text field
@Author: Jamison Ball
@Date Written: November 17th, 2016
@Last Updated: November 17th, 2016
*********************************************************************************************************************/
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class UserInputPanel extends JPanel {
	private JLabel urlLabel;
	private JTextField urlField;
	private AppUI master;
	
	public UserInputPanel(AppUI masterComponent) {
		super();
		urlLabel = new JLabel("URL:");
		urlField = new JTextField(25);
		master = masterComponent;
		
		setLayout(new FlowLayout());
		setSize(urlField.getSize().width + urlLabel.getSize().width,
				urlField.getSize().height + urlLabel.getSize().height);
		add(urlLabel);
		add(urlField);
		urlField.addKeyListener(new KeyboardEventManager());
		urlField.requestFocus();
	}
	
	public JTextField getInputField() {
		return urlField;
	}
	
	public String getInputFieldText() {
		return getInputField().getText();
	}
	
	public AppUI getMasterComponent() {
		return master;
	}
}
