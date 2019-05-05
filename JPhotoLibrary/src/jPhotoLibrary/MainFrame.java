package jPhotoLibrary;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	MainFrame() {
		super(Constants.mainFrameTitle);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnPreferences = new JButton(Constants.prefsButtonTitle);
		btnPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				openPrefsDialog();				
			}
		});
		btnPreferences.setBounds(327, 6, 117, 29);
		contentPane.add(btnPreferences);
		
		JButton btnScan = new JButton(Constants.scanButtonTitle);
		btnScan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPhotoLibrary.scanDirectories();
			}
		});
		btnScan.setBounds(6, 6, 117, 29);
		contentPane.add(btnScan);		
		
	}
		
	void openPrefsDialog() {
		try {
			PrefsDialog prefsDialog = new PrefsDialog(MainFrame.this);
			prefsDialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
}
