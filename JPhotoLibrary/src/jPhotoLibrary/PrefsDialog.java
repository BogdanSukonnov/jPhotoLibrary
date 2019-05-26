package jPhotoLibrary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class PrefsDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private DefaultListModel<String> directoriesListModel = new DefaultListModel<String>();	
	private JButton btnRemoveDirectory;
	private JList<String> listDirectories;	
	private JButton okButton;

	/**
	 * Create the dialog.
	 * @param mainFrame 
	 */
	public PrefsDialog(MainFrame mainFrame) {
		super(mainFrame, UI_Constants.PREFS_DIALOG_TITLE.toString(), true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setEnabled(false);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						savePrefsAndClosePrefsDialog();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		contentPanel.setLayout(null);
		//scroll pane needs to scroll directories list
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 24, 353, 200);
		contentPanel.add(scrollPane);

		listDirectories = new JList<String>(directoriesListModel);
		listDirectories.addListSelectionListener(new ListSelectionListener() {
			//remove button enabled only when directory is selected
			public void valueChanged(ListSelectionEvent e) {
				setRemoveDirectoryButtonAccessibility();
			}
		});
		scrollPane.setViewportView(listDirectories);
		listDirectories.setVisibleRowCount(3);
		listDirectories.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JButton btnAddDirectory = new JButton("+");
		btnAddDirectory.setBounds(369, 21, 75, 29);
		btnAddDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//open choose file dialogue
				JFileChooser directoryChooser = new JFileChooser();
				directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int directoryChooserReturn = directoryChooser.showOpenDialog(contentPanel);
				if (directoryChooserReturn == JFileChooser.APPROVE_OPTION) {
					//directory is chosen
					File chosenDirectory = directoryChooser.getSelectedFile();
					if (chosenDirectory.exists()) {
						String chosenDirectoryPath = chosenDirectory.toString();						 
						int chosenDirectoryIndex;
						//check if we have this directory in the list already						
						if (directoriesListModel.contains(chosenDirectoryPath)) {
							//get directory index
							chosenDirectoryIndex = directoriesListModel.indexOf(chosenDirectoryPath);
						} else {
							//add directory at the end of the list
							chosenDirectoryIndex = directoriesListModel.size();
							directoriesListModel.insertElementAt(chosenDirectoryPath, chosenDirectoryIndex);
						}						
						//select chosen directory at the list and make sure user sees it on a screen
						listDirectories.setSelectedIndex(chosenDirectoryIndex);
						listDirectories.ensureIndexIsVisible(chosenDirectoryIndex);
					}

				}				
			}
		});
		contentPanel.add(btnAddDirectory);

		btnRemoveDirectory = new JButton("-");
		btnRemoveDirectory.setBounds(369, 50, 75, 29);
		btnRemoveDirectory.setEnabled(false);
		btnRemoveDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedDirectoryListIndex = listDirectories.getSelectedIndex();
				if (selectedDirectoryListIndex != -1) {
					directoriesListModel.remove(selectedDirectoryListIndex);															
				}
			}
		});
		contentPanel.add(btnRemoveDirectory);

		JLabel lblFoldersToScan = new JLabel("folders to scan:");
		lblFoldersToScan.setBounds(6, 6, 98, 16);
		contentPanel.add(lblFoldersToScan);		

		//restore saved preferences
		restoreSavedPrefsToDialog();

	}

	/**
	 * 
	 */
	private void setRemoveDirectoryButtonAccessibility() {		
		btnRemoveDirectory.setEnabled(listDirectories.getSelectedIndex() != -1);		
	}

	private void savePrefsAndClosePrefsDialog() {
		String stringDirectoriesToScan = directoriesListModel.toString();
		Boolean prefsSaved = ProgramPreferences.putPref(ProgramPreferences.Prefs.ControlledPaths, stringDirectoriesToScan);
		if (prefsSaved) {
			JPhotoLibrary.setDirectoriesToScan();
			dispose();
		} else {
			//TODO can't save preferences, what will we do?
		}
	}

	private void restoreSavedPrefsToDialog() {
		String[] directoriesToScanArr = ProgramPreferences.getPrefAsArray(ProgramPreferences.Prefs.ControlledPaths);		
		for (String directoryToScan : directoriesToScanArr) {			
			directoriesListModel.insertElementAt(directoryToScan, directoriesListModel.getSize());
		}
		directoriesListModel.addListDataListener(new DirectoryListDataListener());		
	}

	//listen for Directories List Data Model Changes, enable Save Button if user change something
	class DirectoryListDataListener implements ListDataListener {

		@Override
		public void intervalAdded(ListDataEvent e) {
			preferencesChanged();
		}

		@Override
		public void intervalRemoved(ListDataEvent e) {
			preferencesChanged();
		}

		@Override
		public void contentsChanged(ListDataEvent e) {
			preferencesChanged();
		}		

	}

	private void preferencesChanged() {
		okButton.setEnabled(true);			
		setTitle(UI_Constants.PREFS_DIALOG_TITLE.toString() + "*");		
	}
	
}
