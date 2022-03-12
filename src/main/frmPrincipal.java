package main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class frmPrincipal extends JFrame {
	
	private static final long serialVersionUID = 1L;
	JFileChooser jFileChooser;
	
	public frmPrincipal() {
		super("Selecciona el directorio");
		setSize(410, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		initComponentes();
	}
	
	void initComponentes() {
		JPanel jpanel = new JPanel();
		jpanel.setBackground(Color.WHITE);
		jpanel.setLayout(null);
		add(jpanel);
		
		jFileChooser = new JFileChooser();
		jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jFileChooser.setAcceptAllFileFilterUsed(false);
		
		JTextField txtRootPath = new JTextField();
		txtRootPath.setBounds(20, 20, 320, 20);
		txtRootPath.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				FileConstants.ROOT_DIR = txtRootPath.getText();
			}
		});
		jpanel.add(txtRootPath);
		
		
		JButton btnSelectDir = new JButton("...");
		btnSelectDir.setBounds(360, 20, 20, 20);
		frmPrincipal parent = this;
		btnSelectDir.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = jFileChooser.showOpenDialog(parent);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					FileConstants.ROOT_DIR = jFileChooser.getSelectedFile().getPath();
					txtRootPath.setText(FileConstants.ROOT_DIR);
				}
			}
		});
		jpanel.add(btnSelectDir);
		
		JButton btnAccept = new JButton("Aceptar");
		btnAccept.setBounds(20, 60, 360, 35);
		btnAccept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File file = new File(FileConstants.ROOT_DIR);
				if(!FileConstants.ROOT_DIR.isEmpty()) {
					if(file.exists()) {
						frmFileManager frmManager = new frmFileManager();
						frmManager.setVisible(true);
						frmManager.setParent(parent);
					} else {
						JOptionPane.showMessageDialog(parent, "La ruta es inválida", "Ruta no encontrada", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(parent, "Especifique una ruta", "Ruta vacía", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		jpanel.add(btnAccept);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				new frmPrincipal().setVisible(true);				
			}
		});
	}
	
}
