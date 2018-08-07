package com.reece.ui;

import com.reece.manager.AddressBookInfoManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class shows the address book info UI window.
 *
 *
 * @author zhimeng
 * @version 1.0 5/8/2018
 * @since 1.0
 */
public class AddressBookInfoFrame extends JFrame {
    
    /**
     *  managing the communicate with all user interface elements on address book info frame
     */
    private AddressBookInfoManager addressBookInfoManager;
    
    
    /**
     * Creates a <code>AddressBookInfoFrame</code> object
     *
     * @param title the window title
     *
     */
    public AddressBookInfoFrame(String title) {
        super(title);
        this.addressBookInfoManager = new AddressBookInfoManager();
        addWindowListener(new WindowCloser());
        setupGUI();
    }
    
    /**
     * Lays out the address book info UI.
     */
    public void setupGUI() {

        /* sets up display elements */
        JTable addressBookInfoDataTable = new JTable();
        addressBookInfoDataTable.setModel(this.addressBookInfoManager.getDataTableModel());
        addressBookInfoManager.registerAddressBookInfoDataTable(addressBookInfoDataTable);

        JTextField nameTextField = new JTextField(20);
        addressBookInfoManager.registerNameTextField(nameTextField);
        nameTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                addressBookInfoManager.searchAddressBook();
            }
        });

        JLabel addressBookNameLabel = new JLabel("Address Book Name: ");
        JButton addressBookSearchButton = new JButton("Search");
        addressBookSearchButton.addActionListener(e -> addressBookInfoManager.searchAddressBook());

        JButton addressBookContactManagementButton = new JButton("Contact Management");
        addressBookContactManagementButton.addActionListener(e-> addressBookInfoManager.showContactDialog());
        
        JButton addressBookAddButton = new JButton("Add Address Book");
        addressBookAddButton.addActionListener(e -> addressBookInfoManager.addAddressBook());

        JButton addressBookRemoveButton = new JButton("Remove Address Book");
        addressBookRemoveButton.addActionListener(e -> addressBookInfoManager.removeAddressBook());

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> addressBookInfoManager.clearDisplayedData());

        JButton showContactButton = new JButton("Show Contacts");
        showContactButton.addActionListener(e -> addressBookInfoManager.showContact());

        JButton showUniqueContactButton = new JButton("Show Unique Contacts");
        showUniqueContactButton.addActionListener(e -> addressBookInfoManager.showUniqueContact());

        /* sets up the overall layout of user interface */
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        JPanel contentPanel = new JPanel();
        contentPane.add(contentPanel, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel();
        JPanel statusPanel = new JPanel();
        
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(mainPanel, BorderLayout.CENTER);
        contentPanel.add(statusPanel, BorderLayout.SOUTH);
        
        /* fills in the main panel displayed on screen center area */
        JPanel northPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel southPanel = new JPanel();
        
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        
        /* fills in the north panel in main panel */
        GridBagLayout gridBagForNorthPanel = new GridBagLayout();
        GridBagConstraints gridConstrainsForNorthPanel = new GridBagConstraints();
        
        gridBagForNorthPanel.columnWidths = new int [] {1, 1, 1, 1, 1};
        gridBagForNorthPanel.rowHeights = new int [] {1};
        gridBagForNorthPanel.columnWeights = new double [] {20, 20, 20, 20, 20};
        gridBagForNorthPanel.rowWeights = new double [] {100};
        
        northPanel.setLayout(gridBagForNorthPanel);
        
        gridConstrainsForNorthPanel.gridwidth = 1;
        gridConstrainsForNorthPanel.anchor = GridBagConstraints.EAST;
        northPanel.add(addressBookNameLabel, gridConstrainsForNorthPanel);
        gridConstrainsForNorthPanel.anchor = GridBagConstraints.CENTER;
        
        gridConstrainsForNorthPanel.fill = GridBagConstraints.HORIZONTAL;
        northPanel.add(nameTextField, gridConstrainsForNorthPanel);
        gridConstrainsForNorthPanel.fill = GridBagConstraints.NONE;

        gridConstrainsForNorthPanel.anchor = GridBagConstraints.CENTER;
        northPanel.add(addressBookSearchButton, gridConstrainsForNorthPanel);

        gridConstrainsForNorthPanel.anchor = GridBagConstraints.CENTER;
        northPanel.add(addressBookContactManagementButton, gridConstrainsForNorthPanel);

        northPanel.setBorder(new TitledBorder(" Search "));
        
        /* fills in the center panel in main panel */
        JScrollPane tableScrollPane = new JScrollPane(addressBookInfoDataTable);
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);
        
        /* fills in the south panel in main panel */
        JPanel centerInSouth = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.add(centerInSouth, BorderLayout.CENTER);
        southPanel.setBorder(new TitledBorder("Function"));
        
        /* fills in the center panel in south panel */
        GridBagLayout gridBagForCenterInSouthPanel = new GridBagLayout();
        GridBagConstraints gridConstrainsForCenterInSouthPanel = new GridBagConstraints();
        
        gridBagForCenterInSouthPanel.columnWidths = new int [] {1, 1, 1, 1, 1};
        gridBagForCenterInSouthPanel.rowHeights = new int [] {1};
        gridBagForCenterInSouthPanel.columnWeights = new double [] {20, 20, 20, 20, 20};
        gridBagForCenterInSouthPanel.rowWeights = new double [] {100};
        
        centerInSouth.setLayout(gridBagForCenterInSouthPanel);
        
        gridConstrainsForCenterInSouthPanel.gridwidth = 1;
        gridConstrainsForCenterInSouthPanel.gridx = 1;
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.EAST;
        centerInSouth.add(addressBookAddButton, gridConstrainsForCenterInSouthPanel);
        addressBookAddButton.setIcon(null);

        gridConstrainsForCenterInSouthPanel.gridx = 2;
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.CENTER;
        centerInSouth.add(addressBookRemoveButton, gridConstrainsForCenterInSouthPanel);
        addressBookRemoveButton.setIcon(null);

        gridConstrainsForCenterInSouthPanel.gridx = 3;
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.WEST;
        centerInSouth.add(showContactButton, gridConstrainsForCenterInSouthPanel);
        showContactButton.setIcon(null);
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.CENTER;

        gridConstrainsForCenterInSouthPanel.gridx = 4;
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.WEST;
        centerInSouth.add(showUniqueContactButton, gridConstrainsForCenterInSouthPanel);
        showUniqueContactButton.setIcon(null);
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.CENTER;

        gridConstrainsForCenterInSouthPanel.gridx = 5;
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.WEST;
        centerInSouth.add(clearButton, gridConstrainsForCenterInSouthPanel);
        clearButton.setIcon(null);
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.CENTER;
        
        pack();
        nameTextField.requestFocusInWindow();
        getRootPane().setDefaultButton(addressBookSearchButton);
        this.setSize(new Dimension(750, 400));
        this.setLocationRelativeTo(null);
    }
    
}