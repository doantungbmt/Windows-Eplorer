package QLyFileWindowsExplorer_XoaThuMuc;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;
import java.io.File;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.DropMode;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class QuanLyFileWindowsExplorer {

	private JFrame frame;
	private JTextField textField;
	private JTable table;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	
	public static String CURRENT_FOLDER = "D:\\";
	public static String PREVIOUS_FOLDER = "D:\\";
	
	
	public DefaultTableModel TABLE_MODEL = new DefaultTableModel(0, 0);
	public String FOLDER_HEADER[] = new String[] { "Name", "Date modified", "Type","Size"};
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuanLyFileWindowsExplorer window = new QuanLyFileWindowsExplorer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public QuanLyFileWindowsExplorer() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 504, 407);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btUndo = new JButton("");
		btUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CURRENT_FOLDER = PREVIOUS_FOLDER;
				TABLE_MODEL = loadFileInFolder(CURRENT_FOLDER, TABLE_MODEL);
			}
		});
		btUndo.setIcon(new ImageIcon(QuanLyFileWindowsExplorer.class.getResource("/com/sun/javafx/scene/web/skin/DecreaseIndent_16x16_JFX.png")));
		btUndo.setBounds(0, 0, 49, 23);
		frame.getContentPane().add(btUndo);
		
		JButton btRedo = new JButton("");
		btRedo.setIcon(new ImageIcon(QuanLyFileWindowsExplorer.class.getResource("/com/sun/javafx/scene/web/skin/IncreaseIndent_16x16_JFX.png")));
		btRedo.setBounds(49, 0, 49, 23);
		frame.getContentPane().add(btRedo);
		
		textField = new JTextField();
		textField.setBounds(385, 0, 107, 23);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(106, 34, 386, 270);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowVerticalLines(false);
		scrollPane.setViewportView(table);
		
		
		table.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				JTable table =(JTable) e.getSource();
		        Point point = e.getPoint();
		        //int row = table.rowAtPoint(point);
		        int row = table.getSelectedRow();
		        String name = table.getModel().getValueAt(row, 0).toString();
		        if (e.getClickCount() == 2) {
		            //txtDetail.setText(""+name);
		        	PREVIOUS_FOLDER = CURRENT_FOLDER;
		        	CURRENT_FOLDER = CURRENT_FOLDER + name + "\\";
		        	File folder = new File(CURRENT_FOLDER);
		        	if (folder.isDirectory()) {
		        		TABLE_MODEL = loadFileInFolder(CURRENT_FOLDER, TABLE_MODEL);
		        		
		        	}
		        }

			}
		});
		
		
		JTree tree = new JTree();
		tree.setBounds(0, 34, 98, 89);
		frame.getContentPane().add(tree);
		
		JTree tree_1 = new JTree();
		tree_1.setBounds(0, 128, 98, 89);
		frame.getContentPane().add(tree_1);
		
		JTree tree_2 = new JTree();
		tree_2.setBounds(0, 215, 98, 89);
		frame.getContentPane().add(tree_2);
		
		JTextArea txtDetail = new JTextArea();
		txtDetail.setBounds(10, 315, 472, 47);
		frame.getContentPane().add(txtDetail);
		
		
		scrollPane.setViewportView(table);
		table.setModel(TABLE_MODEL);
		TABLE_MODEL.setColumnIdentifiers(FOLDER_HEADER);
		TABLE_MODEL = loadFileInFolder(CURRENT_FOLDER, TABLE_MODEL);
		table.setAutoCreateRowSorter(true);
		
		textField_1 = new JTextField();
		textField_1.setBounds(110, 0, 270, 23);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText("C:/");
	}
	
	
	public DefaultTableModel loadFileInFolder(String path, DefaultTableModel myTableModel) {
		if (myTableModel.getRowCount() > 0) {
		    for (int i = myTableModel.getRowCount() - 1; i > -1; i--) {
		        myTableModel.removeRow(i);
		    }
		}
		
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

	    for (int i = 0; i < listOfFiles.length; i++) {
	    	
	    	//{ "Name", "Date modified", "Type","Size"};
	    	File currentFile = listOfFiles[i];
            String name = currentFile.getName();
            String type = "";
            String size = "";
	    	if (listOfFiles[i].isFile()) {
	    		type = "File";
	    		// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
	    		long fileSizeInBytes = currentFile.length();
	    		long fileSizeInKB = fileSizeInBytes / 1024;
	    		size = "" + fileSizeInKB + " kb";
    		} else if (listOfFiles[i].isDirectory()) {
    			type = "Folder";
    		}
	        	
	    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	    	String dateModified = sdf.format(currentFile.lastModified());
	    	
	    	myTableModel.addRow(new Object[] { name, dateModified, type, size});
	    }
		return myTableModel;
	}
}
