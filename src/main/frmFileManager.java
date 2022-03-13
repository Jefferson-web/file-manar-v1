package main;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class frmFileManager extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JFrame parent = null;
	
	public frmFileManager() {
		super("File Manager");
		setSize(800, 550);
		setLocationRelativeTo(null);
		initComponents();
		
	}
	
	void initComponents() {
		GridLayout gridLayout = new GridLayout(2, 2, 5, 5);
		setLayout(gridLayout);
		
		frmFileManager self = this;
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				self.setVisible(false);
				parent.setVisible(true);
			}
		});
		
		jpFileTree fileTree = new jpFileTree();
		jpFileDescription fileDescription = new jpFileDescription();
		fileTree.setFileDescriptionForm(fileDescription);
		add(fileTree);
		add(fileDescription);
		
	}
	
	public void setParent(JFrame parent) {
		this.parent = parent;
		this.parent.setVisible(false);
	}
	
}
