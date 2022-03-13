package main;

import java.awt.Dimension;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class jpFileDescription extends JPanel {

	private static final long serialVersionUID = 1L;
	private File file = null;
	private JTable jtProperties;
	private DefaultTableModel tableModel;
	
	public jpFileDescription() {
		setPreferredSize(new Dimension(200, 400));
		initComponents();
	}
	
	void initComponents() {
		tableModel = new DefaultTableModel();
		tableModel.addColumn("Property");
		tableModel.addColumn("Value");
		
		jtProperties = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(jtProperties);
		add(scrollPane);
	}
	
	void showProperties() {
		tableModel.setRowCount(0);
		tableModel.addRow(new Object[] { "Name",  getFile().getName() });
		tableModel.addRow(new Object[] { "Last Modified", new Date(getFile().lastModified()).toString() });
		tableModel.addRow(new Object[] { "Directory",  getFile().isDirectory() });
		tableModel.addRow(new Object[] { "Size", getSize(getFile().length()) });
		tableModel.addRow(new Object[] { "Can Execute", getFile().canExecute() });
	}
	
	public void sendFile(File file){
		this.file = file;
		showProperties();
	}

	public File getFile() {
		return file;
	}
	
	private String getSize(long size) {
		DecimalFormat dformat = new DecimalFormat("0.00");
		String unidad = "";
		double newSize = 0.0;
		if(size >= 1000000000) {
			unidad = "GB";
			newSize = size / Math.pow(10, 9);
		} else if(size >= 1000000) {
			unidad = "MB";
			newSize = size / Math.pow(10, 6);
		} else if(size >= 1000) {
			unidad = "KB";
			newSize = size / Math.pow(10, 3);
		} else {
			unidad = "KB";
		}
		return dformat.format(newSize).concat(unidad);
	}
	
}
