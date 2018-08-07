package com.reece.ui;

import com.reece.manager.AddressBookRecordManager;
import com.reece.model.AddressBookInfoItem;
import com.reece.model.Contact;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * This class provides a dialog window to add a contact.
 *
 * @author zhimeng
 * @version 1.0 5/8/2018
 * @since 1.0
 */
public class ContactAddDialog extends JDialog {

    /**
     *  the name of a contact
     */
    private String contactName;

    /**
     *  the phone of a contact
     */
    private String contactPhone;

    /**
     * a text field used to input the contact name
     */
    private JTextField contactNameTextField;

    /**
     * a text field used to input contact phone
     */
    private JTextField contactPhoneTextField;

    /**
     * an combox used to select an address book
     */
    private JComboBox<AddressBookInfoItem> addressBookInfoJComboBox;

    /**
     * An object used to communicate with all user interface elements on address book dialog
     */
    private AddressBookRecordManager addressBookRecordManager;



    /**
     * Creates a <code>ContactAddDialog</code> object
     */
    public ContactAddDialog(AddressBookRecordManager addressBookRecordManager) {

        super();
        setModal(true);
        setResizable(false);
        this.setTitle("Add Contact Dialog");
        this.contactName = "";
        this.contactPhone = "";
        this.addressBookRecordManager = addressBookRecordManager;
        setupGUI();
    }
    
    /**
     * Shows a dialog to add a contact.
     */
    public void setupGUI() {
        
        /* sets up display elements */
        JLabel contactNameLabel = new JLabel("Contact name: ");
        contactNameTextField = new JTextField(contactName);
        contactNameTextField.setColumns(16);

        JLabel contactPhoneLabel = new JLabel("Contact phone: ");
        contactPhoneTextField = new JTextField(contactPhone);
        contactPhoneTextField.setColumns(16);


        JLabel addressBookLabel = new JLabel("Address book: ");
        addressBookInfoJComboBox = new JComboBox<>();

        addressBookRecordManager.getAddressBookInfoService().findAddressBookInfoByName("").stream()
                .forEach(addressBookInfo -> addressBookInfoJComboBox.addItem(new AddressBookInfoItem(addressBookInfo)));

        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false);
        
        JButton contactAddButton = new JButton("  Add  ");
        contactAddButton.setMnemonic(KeyEvent.VK_O);
        ContactAddButtonListener contactAddButtonListener = new ContactAddButtonListener(this);
        contactAddButton.addActionListener(contactAddButtonListener);
        contactAddButton.registerKeyboardAction(contactAddButtonListener, keyStroke, JComponent.WHEN_FOCUSED);
        
        JButton cancelButton = new JButton("Cancel");
        Window window = this;
        cancelButton.addActionListener(e -> {
            window.setVisible(false);
            /* releases all of the native screen resources used by this window */
            window.dispose();
        });

        /* sets up the overall layout of input dialog */
        Container contentPane = getContentPane();
        
        JPanel contentPanel = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(contentPanel,BorderLayout.CENTER);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new TitledBorder(""));
        JPanel buttonPanel = new JPanel();
        
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(mainPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        /* fills in the main panel */
        GridBagLayout gridBagForMainPanel = new GridBagLayout();
        GridBagConstraints gridConstrainsForMainPanel = new GridBagConstraints();
        
        gridBagForMainPanel.columnWidths = new int [] {1, 1};
        gridBagForMainPanel.rowHeights = new int [] {1, 1};
        gridBagForMainPanel.columnWeights = new double [] {40, 60};
        gridBagForMainPanel.rowWeights = new double [] {50, 50};
        
        mainPanel.setLayout(gridBagForMainPanel);

        gridConstrainsForMainPanel.gridwidth = 1;
        gridConstrainsForMainPanel.anchor = GridBagConstraints.EAST;
        mainPanel.add(contactNameLabel, gridConstrainsForMainPanel);
        gridConstrainsForMainPanel.gridwidth = GridBagConstraints.REMAINDER;
        gridConstrainsForMainPanel.anchor = GridBagConstraints.WEST;
        mainPanel.add(contactNameTextField, gridConstrainsForMainPanel);

        gridConstrainsForMainPanel.gridwidth = 1;
        gridConstrainsForMainPanel.anchor = GridBagConstraints.EAST;
        mainPanel.add(contactPhoneLabel, gridConstrainsForMainPanel);
        gridConstrainsForMainPanel.anchor = GridBagConstraints.CENTER;

        gridConstrainsForMainPanel.gridwidth = GridBagConstraints.REMAINDER;
        gridConstrainsForMainPanel.anchor = GridBagConstraints.WEST;
        mainPanel.add(contactPhoneTextField, gridConstrainsForMainPanel);
        gridConstrainsForMainPanel.anchor = GridBagConstraints.CENTER;
        gridConstrainsForMainPanel.fill = GridBagConstraints.NONE;

        gridConstrainsForMainPanel.gridwidth = 1;
        gridConstrainsForMainPanel.anchor = GridBagConstraints.EAST;
        mainPanel.add(addressBookLabel, gridConstrainsForMainPanel);
        gridConstrainsForMainPanel.anchor = GridBagConstraints.CENTER;

        gridConstrainsForMainPanel.gridwidth = GridBagConstraints.REMAINDER;
        gridConstrainsForMainPanel.anchor = GridBagConstraints.WEST;
        mainPanel.add(addressBookInfoJComboBox, gridConstrainsForMainPanel);
        gridConstrainsForMainPanel.anchor = GridBagConstraints.CENTER;
        gridConstrainsForMainPanel.fill = GridBagConstraints.NONE;

        /* fills in the button panel */
        buttonPanel.setBorder(new TitledBorder(""));
        buttonPanel.add(contactAddButton);
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(cancelButton);
        
        pack();
        setLocationRelativeTo(null);
        
    }

    /**
     * This class used to handle contact creation triggered by Add button clicking.
     *
     */
    public class ContactAddButtonListener implements ActionListener {
        
        /* a <code>Window</code> object to be disposed */
        private Window window;
        
        
        /**
         * Creates a new <code>ContactAddButtonListener</code> object
         */
        private ContactAddButtonListener(Window window) {
            
            this.window = window;
            
        }
        
        public void actionPerformed(ActionEvent actionEvent) {
            contactName = contactNameTextField.getText().trim();
            contactPhone = contactPhoneTextField.getText().trim();
            AddressBookInfoItem item = (AddressBookInfoItem)addressBookInfoJComboBox.getSelectedItem();

            /* contact name is empty */
            if (contactName.isEmpty() || item == null || item.getName().isEmpty()) {
                
                JOptionPane.showMessageDialog(null,
                "Contact name or address book can't be empty!");
                
            } else {  // add the contact

                try {
                    addressBookRecordManager.addContact(new Contact(contactName, contactPhone), item.getId());

                    /* closes this dialog */
                    window.setVisible(false);
                    window.dispose();

                } catch(IllegalArgumentException iae) {

                    JOptionPane.showMessageDialog(null,
                    "application runtime error!");
                }
            }
            contactNameTextField.requestFocus();
            contactNameTextField.selectAll();
        }
    }
}