package com.reece.manager;

import com.reece.model.AddressBookDataTableModel;
import com.reece.model.AddressBookInfoItem;
import com.reece.model.Contact;
import com.reece.service.AddressBookRecordService;
import com.reece.service.AddressBookRecordServiceImpl;
import com.reece.ui.AddressBookDialog;
import com.reece.ui.ContactAddDialog;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * This class controls the communication among all elements on the address book dialog.
 *
 * @author zhimeng
 * @version 1.0 5/8/2018
 * @since 1.0
 */
public class AddressBookRecordManager {

    private JTable addressBookDataTable;
    private JTextField contactNameTextField;
    private AddressBookDataTableModel addressBookDataTableModel;
    private AddressBookRecordService addressBookRecordService;
    private AddressBookInfoManager addressBookInfoManager;
    private AddressBookDialog addressBookDialog;
    private JList addressBookDataList;

    /**
     * Creates a <code>AddressBookRecordManager</code> object.
     *
     */
    public AddressBookRecordManager(AddressBookInfoManager addressBookInfoManager) {
        this.addressBookRecordService = new AddressBookRecordServiceImpl();
        this.addressBookInfoManager = addressBookInfoManager;
        addressBookDataTableModel = new AddressBookDataTableModel(null, AddressBookDataTableModel.ADDRESS_BOOK_RECORD_FIELD_NAMES,
                AddressBookDataTableModel.ROW_COUNT, AddressBookDataTableModel.ADDRESS_BOOK_RECORD_FIELD_NAMES.length);
    }

    /**
     * Search contacts in address books.
     *
     */
    public void searchContact() {

        String contactName = contactNameTextField.getText().trim();
        List<Contact> contactList = addressBookRecordService.findContact(contactName);
        int rowCount = contactList.size();
        int columnCount = AddressBookDataTableModel.ADDRESS_BOOK_RECORD_FIELD_NAMES.length;

        addressBookDataTableModel = new AddressBookDataTableModel(contactList,
                AddressBookDataTableModel.ADDRESS_BOOK_RECORD_FIELD_NAMES,
                rowCount, columnCount);

        /* display returned results to the table in user interface */
        addressBookDataTable.setModel(addressBookDataTableModel);
        contactNameTextField.requestFocusInWindow();
    }

    /**
     * Show contacts in selected address books.
     *
     */
    public void showContact() {
        List<AddressBookInfoItem> selectedAddressBookInfoList = (List<AddressBookInfoItem>)addressBookDataList.getSelectedValuesList();
        int[] rowNumbers = addressBookDataList.getSelectedIndices();
        if (selectedAddressBookInfoList == null || selectedAddressBookInfoList.size() < 1) {
            JOptionPane.showMessageDialog(null, "Please select an address book!");
        } else {
            List<Long> selectedAddressBookInfoIdList = new ArrayList<>();
            selectedAddressBookInfoList
                    .forEach(item -> selectedAddressBookInfoIdList.add(item.getId()));

            this.showContact(selectedAddressBookInfoIdList, rowNumbers);
        }
    }

    /**
     * Search contacts and populate address book table.
     *
     */
    public void showContact(List<Long> addressBookIds, int[] rowNumbers) {

        List<Contact> contactList = addressBookRecordService.findAllContacts(addressBookIds);

        /* constructs a <code>AddressBookInfoDataTableModel</code> object */
        int rowCount = (contactList == null)? 0 : contactList.size();
        int columnCount = AddressBookDataTableModel.ADDRESS_BOOK_RECORD_FIELD_NAMES.length;

        addressBookDataTableModel = new AddressBookDataTableModel(contactList,
                AddressBookDataTableModel.ADDRESS_BOOK_RECORD_FIELD_NAMES,
                rowCount, columnCount);

        /* display returned results to the table in user interface */
        addressBookDataTable.setModel(addressBookDataTableModel);
        addressBookDataList.setSelectedIndices(rowNumbers);
        contactNameTextField.setText("");
        contactNameTextField.requestFocusInWindow();
    }

    /**
     * Handle searching unique contacts in selected address books.
     *
     */
    public void showUniqueContact() {
        List<AddressBookInfoItem> selectedAddressBookInfoList = (List<AddressBookInfoItem>)addressBookDataList.getSelectedValuesList();
        int[] rowNumbers = addressBookDataList.getSelectedIndices();
        if (selectedAddressBookInfoList == null || selectedAddressBookInfoList.size() < 1) {
            JOptionPane.showMessageDialog(null, "Please select an address book!");
        } else {
            List<Long> selectedAddressBookInfoIdList = new ArrayList<>();
            selectedAddressBookInfoList
                    .forEach(item -> selectedAddressBookInfoIdList.add(item.getId()));

            this.showUniqueContact(selectedAddressBookInfoIdList, rowNumbers);
        }
    }

    /**
     * Search unique contacts in selected address books.
     *
     */
    public void showUniqueContact(List<Long> addressBookIds, int[] rowNumbers) {

        List<Contact> contactList = addressBookRecordService.findAllUniqueContacts(addressBookIds);

        /* constructs a <code>AddressBookInfoDataTableModel</code> object */
        int rowCount = (contactList == null)? 0 : contactList.size();
        int columnCount = AddressBookDataTableModel.ADDRESS_BOOK_RECORD_FIELD_NAMES.length;

        addressBookDataTableModel = new AddressBookDataTableModel(contactList,
                AddressBookDataTableModel.ADDRESS_BOOK_RECORD_FIELD_NAMES,
                rowCount, columnCount);

        /* display returned results to the table in user interface */
        addressBookDataTable.setModel(addressBookDataTableModel);
        addressBookDataList.setSelectedIndices(rowNumbers);
        contactNameTextField.setText("");
        contactNameTextField.requestFocusInWindow();
    }

    /**
     * Clear displayed data on screen.
     *
     */
    public void clearDisplayedData() {
        int columnCount = AddressBookDataTableModel.ADDRESS_BOOK_RECORD_FIELD_NAMES.length;
        addressBookDataTableModel = new AddressBookDataTableModel(null,
                AddressBookDataTableModel.ADDRESS_BOOK_RECORD_FIELD_NAMES,
                AddressBookDataTableModel.ROW_COUNT, columnCount);

        /* clears the displayed records in a table */
        addressBookDataTable.setModel(addressBookDataTableModel);
        addressBookDataList.clearSelection();
        /* clears the content of text fields */
        contactNameTextField.setText("");
        contactNameTextField.requestFocusInWindow();
    }

    /**
     * Present the address book dialog.
     *
     */
    public void showDialog(AddressBookDialog addressBookDialog) {
        this.setAddressBookDialog(addressBookDialog);
        this.addressBookDialog.setVisible(true);
    }

    /**
     * Present the adding contact dialog.
     *
     */
    public void addContact() {
        ContactAddDialog dialog = new ContactAddDialog(this, this.addressBookInfoManager);
        dialog.setVisible(true);
    }

    /**
     * Add the contact into an address book.
     *
     */
    public void addContact(Contact contact, long addressBookId) {
        Contact addedContact = addressBookRecordService.addContact(contact, addressBookId);
        List dataList =  (addressBookDataTableModel.getList() == null) ? new ArrayList() : addressBookDataTableModel.getList();
        dataList.add(addedContact);

        int rowCount = (dataList == null)? 0 : dataList.size();
        int columnCount = AddressBookDataTableModel.ADDRESS_BOOK_RECORD_FIELD_NAMES.length;
        addressBookDataTableModel = new AddressBookDataTableModel(dataList, AddressBookDataTableModel.ADDRESS_BOOK_RECORD_FIELD_NAMES,
                rowCount, columnCount);
        addressBookDataTable.setModel(addressBookDataTableModel);
    }

    /**
     * Remove contacts in address books.
     *
     */
    public void removeContact() {
        List<Contact> selectedContactList = new ArrayList<>();
        List<Contact> successfullyRemovedContactList = new ArrayList<>();
        int[] rowNumbers = addressBookDataTable.getSelectedRows();
        List<Contact> dataModelList = (List<Contact>) addressBookDataTableModel.getList();

        if (rowNumbers.length == 0) {
            JOptionPane.showMessageDialog(null, "Please select a row in a table!");
        } else {
            // gets the selected contacts
            Arrays.stream(rowNumbers)
                    .forEach(rowNumber -> selectedContactList.add(dataModelList.get(rowNumber)));

            // remove contacts from the ui and data store
            selectedContactList
                    .forEach(contact -> {
                        dataModelList.remove(contact);
                        Optional<Contact> removedContact = addressBookRecordService.removeContact(contact.getId());
                        removedContact.ifPresent(successfullyRemovedContactList::add);
                    });

            // refresh the data table to reflect changes
            int rowCount = (dataModelList == null)? 0 : dataModelList.size();
            int columnCount = AddressBookDataTableModel.ADDRESS_BOOK_RECORD_FIELD_NAMES.length;
            addressBookDataTableModel = new AddressBookDataTableModel(dataModelList, AddressBookDataTableModel.ADDRESS_BOOK_RECORD_FIELD_NAMES,
                    rowCount, columnCount);
            addressBookDataTable.setModel(addressBookDataTableModel);

            if(selectedContactList.size() == successfullyRemovedContactList.size())
                JOptionPane.showMessageDialog(null,"Selected contact has been removed!");

        }
        contactNameTextField.requestFocusInWindow();
    }

    /**
     * Update the address book list data.
     *
     */
    public void updateAddressBookDataList() {
        if(this.addressBookDialog != null) {
            Object[] data = this.getAddressBookInfoManager().searchAddressBook(null).stream()
                    .map(AddressBookInfoItem::new).toArray();
            this.getAddressBookDataList().setListData(data);
        }
    }

    /**
     * Registers the <code>DataTable</code> object with this Manager.
     *
     * @param addressBookDataTable a <code>DataTable</code> object used to display contacts
     */
    public void registerAddressBookDataTable(JTable addressBookDataTable) {
        
        this.addressBookDataTable = addressBookDataTable;
        
    }

    /**
     * Registers the <code>ContactNameTextField</code> object with this Manager.
     *
     * @param contactNameTextField a <code>ContactNameTextField</code> object used to accept the name of a contact
     */
    public void registerContactNameTextField(JTextField contactNameTextField) {
        
        this.contactNameTextField = contactNameTextField;
        
    }

    /**
     * Registers the address book list with this Manager.
     *
     * @param addressBookDataList a list containing address books
     */
    public void registerAddressBookDataList(JList addressBookDataList) {
        this.addressBookDataList = addressBookDataList;
    }

    public void setAddressBookDialog(AddressBookDialog addressBookDialog) {
        this.addressBookDialog = addressBookDialog;
    }

    public AddressBookInfoManager getAddressBookInfoManager() {
        return addressBookInfoManager;
    }

    public void setAddressBookInfoManager(AddressBookInfoManager addressBookInfoManager) {
        this.addressBookInfoManager = addressBookInfoManager;
    }

    public JList getAddressBookDataList() {
        return addressBookDataList;
    }

    public void setAddressBookDataList(JList addressBookDataList) {
        this.addressBookDataList = addressBookDataList;
    }
}