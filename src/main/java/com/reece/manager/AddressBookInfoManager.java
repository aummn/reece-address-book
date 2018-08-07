package com.reece.manager;

import com.reece.model.AddressBookInfo;
import com.reece.model.AddressBookInfoDataTableModel;
import com.reece.model.AddressBookInfoItem;
import com.reece.service.AddressBookInfoService;
import com.reece.service.AddressBookInfoServiceImpl;
import com.reece.ui.AddressBookAddDialog;
import com.reece.ui.AddressBookDialog;

import javax.swing.*;
import java.util.*;

/**
 * This class manages the communication among all elements on the address book info frame.
 *
 * @author zhimeng
 * @version 1.0 5/8/2018
 * @since 1.0
 */
public class AddressBookInfoManager extends Observable {

    private JTable addressBookInfoDataTable;
    private AddressBookInfoDataTableModel dataTableModel;
    private JTextField nameTextField;
    private AddressBookInfoService addressBookInfoService;
    private AddressBookRecordManager addressBookRecordManager;
    private AddressBookDialog addressBookDialog;

    /**
     * Creates a <code>AddressBookInfoMediator</code> object.
     *
     */
    public AddressBookInfoManager() {
        this.addressBookInfoService = new AddressBookInfoServiceImpl();
        this.addressBookRecordManager = new AddressBookRecordManager(addressBookInfoService);
        this.addObserver(addressBookRecordManager);
        this.dataTableModel = new AddressBookInfoDataTableModel(
                new ArrayList<>(),
                AddressBookInfoDataTableModel.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES,
                AddressBookInfoDataTableModel.ROW_COUNT,
                AddressBookInfoDataTableModel.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES.length);
    }

    /**
     *  Handles the address book searching triggered by key events or button clicking.
     *
     */
    public void searchAddressBook() {

        String addressBookName = nameTextField.getText();
        List<AddressBookInfo> addressBookInfoList = addressBookInfoService.findAddressBookInfoByName(addressBookName);

        /* constructs a <code>AddressBookInfoDataTableModel</code> object */
        int rowCount = addressBookInfoList.size();
        int columnCount = AddressBookInfoDataTableModel.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES.length;
        dataTableModel = new AddressBookInfoDataTableModel(
                addressBookInfoList,
                AddressBookInfoDataTableModel.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES,
                rowCount,
                columnCount);

        /* display returned results to the table in user interface */
        addressBookInfoDataTable.setModel(dataTableModel);
        nameTextField.requestFocusInWindow();
    }

    /**
     * Search the address books by name.
     *
     * @param  addressBookName  the name of an address book
     */
    public List<AddressBookInfo> searchAddressBook(String addressBookName) {
        if (addressBookName == null) addressBookName = "";
        return addressBookInfoService.findAddressBookInfoByName(addressBookName);
    }

    /**
     * clear the shown data on screen.
     *
     */
    public void clearDisplayedData() {
        int columnCount = AddressBookInfoDataTableModel.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES.length;
        dataTableModel = new AddressBookInfoDataTableModel(new ArrayList<>(),
                AddressBookInfoDataTableModel.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES,
                AddressBookInfoDataTableModel.ROW_COUNT, columnCount);

        /* clears the displayed records in a table */
        addressBookInfoDataTable.setModel(dataTableModel);

        /* clears the content of text fields */
        nameTextField.setText("");
        nameTextField.requestFocusInWindow();
    }

    /**
     * Present the contact management dialog.
     *
     */
    public void showContactDialog() {
        if(this.addressBookDialog == null) {
            this.addressBookDialog = new AddressBookDialog("Contacts", addressBookRecordManager);
        }
        addressBookRecordManager.clearDisplayedData();
        addressBookRecordManager.showDialog(this.addressBookDialog);
    }

    /**
     * Show contacts in selected address books.
     *
     */
    public void showContact() {
        List<Long> selectedAddressBookInfoIdList = new ArrayList<>();
        int[] rowNumbers = addressBookInfoDataTable.getSelectedRows();

        if (rowNumbers.length == 0) {
            JOptionPane.showMessageDialog(null, "Please select a row in a table!");
        } else {
            List<AddressBookInfo> dataModelList = (List<AddressBookInfo>)dataTableModel.getList();
            Arrays.stream(rowNumbers)
                    .forEach(rowNumber -> selectedAddressBookInfoIdList.add(dataModelList.get(rowNumber).getId()));

            if(this.addressBookDialog == null) {
                this.addressBookDialog = new AddressBookDialog("Address Book", addressBookRecordManager);
            }

            // show contacts in address books
            addressBookRecordManager.showContact(selectedAddressBookInfoIdList, rowNumbers);
            addressBookRecordManager.showDialog(this.addressBookDialog);
        }
    }

    /**
     * Show unique contacts in selected address books.
     *
     */
    public void showUniqueContact() {
        List<Long> selectedAddressBookInfoIdList = new ArrayList<>();
        int[] rowNumbers = addressBookInfoDataTable.getSelectedRows();

        if (rowNumbers.length == 0) {
            JOptionPane.showMessageDialog(null, "Please select a row in a table!");
        } else {
            List<AddressBookInfo> dataModelList = dataTableModel.getList();
            Arrays.stream(rowNumbers)
                    .forEach(rowNumber -> selectedAddressBookInfoIdList.add(dataModelList.get(rowNumber).getId()));

            if(this.addressBookDialog == null) {
                this.addressBookDialog = new AddressBookDialog("Address Book", addressBookRecordManager);
            }

            // show unique contacts in address books
            addressBookRecordManager.showUniqueContact(selectedAddressBookInfoIdList, rowNumbers);
            addressBookRecordManager.showDialog(this.addressBookDialog);
        }
    }

    /**
     * Presents the adding address dialog.
     *
     */
    public void addAddressBook() {
        AddressBookAddDialog dialog = new AddressBookAddDialog(this);
        dialog.setVisible(true);
    }

    /**
     * Add an address book.
     *
     */
    public void addAddressBookInfo(String addressBookName) {
        AddressBookInfo addressBookInfo = addressBookInfoService.addAddressBookInfo(new AddressBookInfo(addressBookName));
        dataTableModel.getList().add(addressBookInfo);

        int rowCount = (dataTableModel.getList() == null)? 0 : dataTableModel.getList().size();
        int columnCount = AddressBookInfoDataTableModel.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES.length;

        dataTableModel = new AddressBookInfoDataTableModel(dataTableModel.getList(),
                AddressBookInfoDataTableModel.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES,
                rowCount, columnCount);
        addressBookInfoDataTable.setModel(dataTableModel);
        this.updateAddressBookDataList();
    }

    /**
     * Remove selected address books.
     *
     */
    public void removeAddressBook() {
        List<AddressBookInfo> selectedAddressBookInfoList = new ArrayList<>();
        List<AddressBookInfo> successfullyRemovedAddressBookInfoList = new ArrayList();

        int[] rowNumbers = addressBookInfoDataTable.getSelectedRows();
        List<AddressBookInfo> dataModelList = (List<AddressBookInfo>)dataTableModel.getList();

        if (rowNumbers.length == 0) {
            JOptionPane.showMessageDialog(null, "Please select a row in a table!");
        } else {
            Arrays.stream(rowNumbers)
                    .forEach(rowNumber -> selectedAddressBookInfoList.add(dataModelList.get(rowNumber)));

            // remove address book info item from the UI and data store
            selectedAddressBookInfoList
                    .forEach(info -> {
                        dataModelList.remove(info);
                        Optional<AddressBookInfo> removedAddressBookInfo = addressBookInfoService.removeAddressBookInfo(info.getId());
                        removedAddressBookInfo.ifPresent(successfullyRemovedAddressBookInfoList::add);
                    });

            int rowCount = (dataModelList == null)? 0 : dataModelList.size();
            int columnCount = AddressBookInfoDataTableModel.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES.length;
            dataTableModel = new AddressBookInfoDataTableModel(dataModelList,
                    AddressBookInfoDataTableModel.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES,
                    rowCount, columnCount);
            addressBookInfoDataTable.setModel(dataTableModel);
            this.updateAddressBookDataList();
        }
        nameTextField.requestFocusInWindow();
    }

    /**
     * Notify observers registered with this manager to update address book list.
     *
     */
    private void updateAddressBookDataList() {
        Object[] data = this.addressBookInfoService.findAddressBookInfoByName("").stream()
                .map(AddressBookInfoItem::new).toArray();

        setChanged();
        notifyObservers(data);
    }


    /**
     * Registers the <code>DataTable</code> object with this manager.
     *
     * @param addressBookDataTable a <code>DataTable</code> object used to display address books
     *
     */
    public void registerAddressBookInfoDataTable(JTable addressBookDataTable) {
        
        this.addressBookInfoDataTable = addressBookDataTable;
        
    }
    
    /**
     * Registers the <code>NameTextField</code> object with this mediator.
     *
     * @param nameTextField a <code>NameTextField</code> object used to accept an address book name
     */
    public void registerNameTextField(JTextField nameTextField) {
        
        this.nameTextField = nameTextField;
        
    }

    public AddressBookRecordManager getAddressBookRecordManager() {
        return addressBookRecordManager;
    }

    public void setAddressBookRecordManager(AddressBookRecordManager addressBookRecordManager) {
        this.addressBookRecordManager = addressBookRecordManager;
    }

    public AddressBookInfoDataTableModel getDataTableModel() {
        return dataTableModel;
    }


}