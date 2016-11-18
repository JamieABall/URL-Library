/******************************************************************************************************************************
Class: MouseEventManager.java
Listens for specific input from the mouse and executes the corresponding function
@Author: Jamison Ball
@Date Written: November 17th, 2016
@Last Updated: November 17th, 2016
******************************************************************************************************************************/
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MouseEventManager implements MouseListener {
	public MouseEventManager() {}
	public void mouseClicked(MouseEvent e) {
		Object e_src = e.getSource();
		
		if(e_src instanceof JLabel) {
			JLabel linkLabel = (JLabel)e_src;
			Object fatherComponent = linkLabel.getParent();
			
			if(fatherComponent instanceof WebLinkPanel) {
				WebLinkPanel fatherPanel = (WebLinkPanel)fatherComponent;
				fatherPanel.setSelected(true);
				fatherPanel.update(fatherPanel.getGraphics());
				AppUI masterComponent = fatherPanel.getRootComponent();
				
				for(WebLinkPanel wlp: masterComponent.getUrlPanels()) {
					if(!wlp.equals(fatherPanel) && wlp.isSelected()) {
						wlp.setSelected(false);
						wlp.update(wlp.getGraphics());
					} 
				}
				UserInputPanel masterUIC = masterComponent.getUserInputComponent();
				JTextField uicTextField = masterUIC.getInputField();
				
				if(!uicTextField.getText().isEmpty()) {
					uicTextField.setText("");
					uicTextField.setText(fatherPanel.getSource().getURL());
				} else {
					uicTextField.setText(fatherPanel.getSource().getURL());
				}
			}
		}
	}
	
	public void mouseEntered(MouseEvent e) {
		Object e_src = e.getSource();
		
		if(e_src instanceof JLabel) {
			JLabel linkLabel = (JLabel)e_src;
			Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
			linkLabel.setCursor(cursor);
		}
	}
	
	public void mouseExited(MouseEvent e) {
		Object e_src = e.getSource();
		
		if(e_src instanceof JLabel) {
			JLabel linkLabel = (JLabel)e_src;
			Cursor cursor = Cursor.getDefaultCursor();
			linkLabel.setCursor(cursor);
		}
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
