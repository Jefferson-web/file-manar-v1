package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class frmFileManager extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JFrame parent = null;
	
	public frmFileManager() {
		super("File Manager");
		setSize(800, 550);
		setLocationRelativeTo(null);
		initComponents();
		frmFileManager self = this;
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				self.setVisible(false);
				parent.setVisible(true);
			}
		});
	}
	
	void initComponents() {
		
	}
	
	public void setParent(JFrame parent) {
		this.parent = parent;
		this.parent.setVisible(false);
	}
	
}
