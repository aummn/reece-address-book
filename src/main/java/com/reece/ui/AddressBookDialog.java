package com.reece.ui;

import com.reece.manager.AddressBookRecordManager;
import com.reece.model.AddressBookDataTableModel;
import com.reece.model.AddressBookInfoItem;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * This class presents the address book UI.
 *
 *
 * @author zhimeng
 * @version 1.0 5/8/2018
 * @since 1.0
 */
public class AddressBookDialog extends JDialog {

    /**
     * An object used to communicate with all user interface elements.
     * Every user interface element registers with it and
     * delegates operations to it.
     */
    private AddressBookRecordManager addressBookRecordManager;

    /**
     * Creates a <code>AddressBookDialog</code> object
     *
     * @param title a string to be the title of this <code>JFrame</code>
     * @param addressBookRecordManager the UI elements communication mediator
     *
     */
    public AddressBookDialog(String title, AddressBookRecordManager addressBookRecordManager) {
        super();
        setModal(false);
        setResizable(true);
        this.setTitle(title);
        this.addressBookRecordManager = addressBookRecordManager;
        setupGUI();
    }

    /**
     * Lays out the user interface.
     */
    public void setupGUI() {
        
        /* sets up actions to generate buttons */
        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false);
        
        /* sets up display elements */
        JTable addressBookDataTable = new JTable();

        AddressBookDataTableModel dataTableModel = new AddressBookDataTableModel(null,
                AddressBookDataTableModel.ADDRESS_BOOK_RECORD_FIELD_NAMES,
                AddressBookDataTableModel.ROW_COUNT,
                AddressBookDataTableModel.ADDRESS_BOOK_RECORD_FIELD_NAMES.length);

        addressBookDataTable.setModel(dataTableModel);
        addressBookRecordManager.registerAddressBookDataTable(addressBookDataTable);

        JTextField contactNameTextField = new JTextField(20);
        contactNameTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                addressBookRecordManager.searchContact();
            }
        });
        addressBookRecordManager.registerContactNameTextField(contactNameTextField);

        JList addressBookDataList = new JList();
        Object[] data = addressBookRecordManager.getAddressBookInfoService().findAddressBookInfoByName("").stream()
                .map(AddressBookInfoItem::new).toArray();
        addressBookDataList.setListData(data);
        addressBookDataList.setVisibleRowCount(3);

        this.addressBookRecordManager.registerAddressBookDataList(addressBookDataList);
        JScrollPane addressBookDataListScrollPane = new JScrollPane(addressBookDataList);

        JLabel contactNameLabel = new JLabel("Contact Name: ");
        JLabel addressBookDataListLabel = new JLabel("Address Book: ");
        
        JButton contactSearchButton = new JButton("Search");
        contactSearchButton.addActionListener(e -> addressBookRecordManager.searchContact());
        
        JButton contactAddButton = new JButton("Add");
        contactAddButton.addActionListener(e -> addressBookRecordManager.addContact());

        JButton contactRemoveButton = new JButton("Remove");
        contactRemoveButton.addActionListener(e -> addressBookRecordManager.removeContact());
        
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> addressBookRecordManager.clearDisplayedData());

        JButton closeButton = new JButton("Close");
        Window window = this;
        closeButton.addActionListener(e -> {
            window.setVisible(false);
            /* releases all of the native screen resources used by this window */
            window.dispose();
        });

        JButton showContactButton = new JButton("Show Contacts");
        showContactButton.addActionListener(e -> addressBookRecordManager.showContact());

        JButton showUniqueContactButton = new JButton("Show Unique Contacts");
        showUniqueContactButton.addActionListener(e -> addressBookRecordManager.showUniqueContact());
        
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

        gridBagForNorthPanel.columnWidths = new int [] {1, 1,1,1};
        gridBagForNorthPanel.rowHeights = new int [] {1, 1};
        gridBagForNorthPanel.columnWeights = new double [] {25, 25, 25, 25};
        gridBagForNorthPanel.rowWeights = new double [] {50, 50};
        
        northPanel.setLayout(gridBagForNorthPanel);

        gridConstrainsForNorthPanel.gridwidth = 1;
        gridConstrainsForNorthPanel.anchor = GridBagConstraints.EAST;
        northPanel.add(contactNameLabel, gridConstrainsForNorthPanel);

        gridConstrainsForNorthPanel.anchor = GridBagConstraints.WEST;
        northPanel.add(contactNameTextField, gridConstrainsForNorthPanel);

        gridConstrainsForNorthPanel.gridwidth = GridBagConstraints.REMAINDER;
        gridConstrainsForNorthPanel.anchor = GridBagConstraints.WEST;
        northPanel.add(contactSearchButton, gridConstrainsForNorthPanel);

        gridConstrainsForNorthPanel.gridwidth = 1;
        gridConstrainsForNorthPanel.anchor = GridBagConstraints.EAST;
        northPanel.add(addressBookDataListLabel, gridConstrainsForNorthPanel);
        gridConstrainsForNorthPanel.anchor = GridBagConstraints.CENTER;

        gridConstrainsForNorthPanel.anchor = GridBagConstraints.WEST;
        northPanel.add(addressBookDataListScrollPane, gridConstrainsForNorthPanel);
        gridConstrainsForNorthPanel.anchor = GridBagConstraints.CENTER;
        gridConstrainsForNorthPanel.fill = GridBagConstraints.NONE;

        gridConstrainsForNorthPanel.gridwidth = 1;
        gridConstrainsForNorthPanel.anchor = GridBagConstraints.WEST;
        northPanel.add(showContactButton, gridConstrainsForNorthPanel);

        gridConstrainsForNorthPanel.anchor = GridBagConstraints.CENTER;
        gridConstrainsForNorthPanel.gridwidth = GridBagConstraints.REMAINDER;
        northPanel.add(showUniqueContactButton, gridConstrainsForNorthPanel);
        gridConstrainsForNorthPanel.fill = GridBagConstraints.NONE;


        northPanel.setBorder(new TitledBorder(" Search "));
        
        /* fills in the center panel in main panel */
        JScrollPane tableScrollPane = new JScrollPane(addressBookDataTable);
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
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.CENTER;
        centerInSouth.add(contactAddButton, gridConstrainsForCenterInSouthPanel);
        contactAddButton.setIcon(null);

        gridConstrainsForCenterInSouthPanel.gridx = 2;
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.CENTER;
        centerInSouth.add(contactRemoveButton, gridConstrainsForCenterInSouthPanel);
        contactRemoveButton.setIcon(null);

        gridConstrainsForCenterInSouthPanel.gridx = 3;
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.CENTER;
        centerInSouth.add(clearButton, gridConstrainsForCenterInSouthPanel);
        clearButton.setIcon(null);

        gridConstrainsForCenterInSouthPanel.gridx = 4;
        gridConstrainsForCenterInSouthPanel.anchor = GridBagConstraints.CENTER;
        centerInSouth.add(closeButton, gridConstrainsForCenterInSouthPanel);
        clearButton.setIcon(null);

        pack();
        contactNameTextField.requestFocusInWindow();
        getRootPane().setDefaultButton(contactSearchButton);
        this.setSize(new Dimension(700, 400));
        this.setLocationRelativeTo(null);
    }
    
}