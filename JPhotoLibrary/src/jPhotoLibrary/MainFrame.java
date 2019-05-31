package jPhotoLibrary;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTree;
import net.miginfocom.swing.MigLayout;

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
		contentPane.setLayout(new MigLayout("", "[117px][204px][117px]", "[29px][236px]"));
		contentPane.add(btnPreferences, "cell 2 0,alignx left,aligny top");
		
		JButton btnScan = new JButton(UI_Constants.SCAN_BTN_TITLE.toString());
		btnScan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jPhotoLibrary.scanDirectories();
			}
		});
		contentPane.add(btnScan, "cell 0 0,growx,aligny top");		
		
		JTree tree = new JTree();
		tree.setRootVisible(false);
		tree.setModel(treeModel);
		contentPane.add(tree, "cell 0 1 3 1,grow");		
		
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
