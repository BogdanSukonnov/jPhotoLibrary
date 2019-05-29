package jPhotoLibrary;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTree;

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
		contentPane.setLayout(null);
		
		JButton btnPreferences = new JButton(UI_Constants.PREFS_BTN_TITLE.toString());
		btnPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				openPrefsDialog();				
			}
		});
		btnPreferences.setBounds(327, 6, 117, 29);
		contentPane.add(btnPreferences);
		
		JButton btnScan = new JButton(UI_Constants.SCAN_BTN_TITLE.toString());
		btnScan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jPhotoLibrary.scanDirectories();
			}
		});
		btnScan.setBounds(6, 6, 117, 29);
		contentPane.add(btnScan);		
		
		JTree tree = new JTree();
		tree.setRootVisible(false);
		tree.setModel(treeModel);
		tree.setBounds(6, 36, 242, 236);
		contentPane.add(tree);		
		
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
