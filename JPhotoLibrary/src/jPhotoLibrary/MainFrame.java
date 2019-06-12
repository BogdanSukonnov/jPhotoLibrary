package jPhotoLibrary;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTree;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import java.awt.Rectangle;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import java.awt.Color;

class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPhotoLibrary jPhotoLibrary;
	
	MainFrame(JPL_TreeModel treeModel, JPhotoLibrary jPhotoLibrary) {
		super(UI_Constants.MAIN_FRAME_TITLE.toString());
		this.jPhotoLibrary = jPhotoLibrary;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnPreferences = new JButton(UI_Constants.PREFS_BTN_TITLE.toString());
		btnPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				openPrefsDialog();				
			}
		});
		contentPane.setLayout(new MigLayout("", "[117px][313.00px]", "[29px][244.00px]"));
		contentPane.add(btnPreferences, "cell 1 0,alignx left,aligny top");
		
		JButton btnScan = new JButton(UI_Constants.SCAN_BTN_TITLE.toString());
		btnScan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jPhotoLibrary.scanDirectories();
			}
		});
		contentPane.add(btnScan, "cell 0 0,growx,aligny top");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setMaximumSize(new Dimension(2000, 2000));
		scrollPane.setPreferredSize(new Dimension(2000, 2000));
		contentPane.add(scrollPane, "south");
		
		JTree tree = new JTree();
		tree.setVisibleRowCount(120);
		tree.setMaximumSize(new Dimension(2000, 2000));
		tree.setMinimumSize(new Dimension(30, 30));
		scrollPane.setViewportView(tree);
		tree.setRootVisible(false);
		tree.setModel(treeModel);
		
	}
		
	void openPrefsDialog() {
		try {
			PrefsDialog prefsDialog = new PrefsDialog(MainFrame.this, jPhotoLibrary);
			prefsDialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
