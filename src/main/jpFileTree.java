package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class jpFileTree extends JPanel implements TreeSelectionListener {

	private JTree tree;
	
	private static final long serialVersionUID = 1L;
	
	DefaultMutableTreeNode selectedNode = null;
	jpFileDescription fileDescription;

	public jpFileTree() {
		DefaultMutableTreeNode root = walk(null, FileConstants.ROOT_DIR);
		this.tree = new JTree(root);
		initComponentes();
	}
	
	void initComponentes() {
		this.tree.addTreeSelectionListener(this);
		this.tree.addMouseListener(new TreeMouseHandler());
		add(new JScrollPane(this.tree));
	}
	
	DefaultMutableTreeNode walk(DefaultMutableTreeNode parent, String path) {
		DefaultMutableTreeNode currentParent = null;
		if(parent == null) {
			currentParent = new DefaultMutableTreeNode(getFileName(path));
		} else {
			currentParent = parent;
		}
		List<File> files = getFilesFromPath(path);
		DefaultMutableTreeNode node = null;
		for (File file : files) {
			node = new DefaultMutableTreeNode(file.getName());
			if(file.isDirectory()) {
				DefaultMutableTreeNode newParent = walk(node, file.getPath());
				currentParent.add(newParent);
			} else {
				currentParent.add(node);
			}
		}
		return currentParent;
	}
	
	public String getFileName(String path) {
		File file = new File(path);
		return file.getName();
	}
	
	public List<File> getFilesFromPath(String path) {
		File file = new File(path);
		List<File> files = Arrays.asList(file.listFiles());
		return files;
	}

	public JTree getTree() {
		return tree;
	}

	public void setTree(JTree tree) {
		this.tree = tree;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		try {
			selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			Object[] paths = selectedNode.getPath();
			String selectedPath = transformPath(paths);
			File file = new File(FileConstants.ROOT_DIR + selectedPath);
			this.fileDescription.sendFile(file);
		} catch (Exception e2) {}
	}
	
	public String transformPath(Object[] paths) {
		String selectedPath = "";
		for (int i = 0; i < paths.length; i++) {
			if(i == 0) continue;
			selectedPath += File.separator + paths[i];
		}
		return selectedPath;
	}
	
	public void setFileDescriptionForm(jpFileDescription frm) {
		this.fileDescription = frm;
	}
	
	class TreeMouseHandler extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON3 && selectedNode != null) {
				showMenu(e.getX(), e.getY());
			}
		}
		
		void showMenu(int x, int y) {
			try {
				JPopupMenu popup = new JPopupMenu();
				JMenuItem menu = new JMenuItem("Delete");
				TreePath treePath = tree.getPathForLocation(x, y);
				Object node = treePath.getLastPathComponent();
				if(node == getTree().getModel().getRoot() || selectedNode != (DefaultMutableTreeNode) node) {
					menu.setEnabled(false);
				}
				popup.add(menu);
				menu.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						deleteSelectedItem();
					}
				});
				popup.show(tree, x, y);
			} catch (Exception e) {}
		}
		
		void deleteSelectedItem() {
			DefaultTreeModel model = (DefaultTreeModel) (tree.getModel());
			model.removeNodeFromParent(selectedNode);
			selectedNode = null;
			fileDescription.clearProperties();
		}
		
	}
	
}
