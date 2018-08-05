package com.reece.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;


/**
 * This class provides a table model to store address book info data.
 *
 * @author zhimeng
 * @version 1.0 5/8/2018
 * @since 1.0
 */
public class AddressBookInfoDataTableModel extends AbstractTableModel {

    /**
     * the string array containing table column names
     */
    public static String[] ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES = {"id", "name"};

    /**
     * the number of displayed rows
     */
    public static final int ROW_COUNT = 20;

    /**
     * the number of rows
     */
    private int rowCount;

    /**
     * the number of columns
     */
    private int columnCount;

    /**
     * a list containing address book info data
     */
    private List<AddressBookInfo> list;

    /**
     * an array containing the field names of a record
     */
    private String[] recordFieldNames;


    /**
     * Creates a new <code>AddressBookInfoDataTableModel</code> object.
     *
     * @param list a <code>List</code> containing the address book info data
     * @param recordFieldNames the field names of a record
     * @param rowCount the number of row in a table
     * @param columnCount the number of columns in a table
     */
    public AddressBookInfoDataTableModel(List<AddressBookInfo> list, String[] recordFieldNames,
                                         int rowCount, int columnCount) {
        
        this.list = list;
        this.recordFieldNames = recordFieldNames;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        
    }
    
    /**
     * Returns the number of columns in the table.
     *
     * @return an <code>int</code> representing the count of columns in
     * the table
     */
    public int getColumnCount() {
        
        return columnCount;
        
    }
    
    /**
     * Returns the number of rows in the table.
     *
     * @return an <code>int</code> representing the count of records in a table
     */
    public int getRowCount() {
        
        return rowCount;
        
    }
    
    /**
     * Gets the value at the given cell as an <code>Object</code>.
     *
     * @param row the row number of this cell
     * @param column the column number of this cell
     *
     * @return An <code>Object</code> stored in this cell.
     * Returns an empty string if the <code>List</code> is <code>null</code>.
     */
    public Object getValueAt(int row, int column) {

        Object value = "";
        if (list != null && list.size() != 0) {
            
            AddressBookInfo addressBookInfo = list.get(row);

            switch(column) {
                case 0:
                    value = addressBookInfo.getId();
                    break;
                case 1:
                    value = addressBookInfo.getName();
                    break;
                default:
                    value = "";
            }
        }
        return value;
    }
    
    /**
     * Gets the column name of a give column.
     *
     * @param column the index of a given column
     *
     * @return a <code>String</code> object to be the name of this column
     */
    public String getColumnName(int column) {
        
        if (recordFieldNames == null) {
            return AddressBookInfoDataTableModel.ADDRESS_BOOK_INFO_RECORD_FIELD_NAMES[column];
        } else {
            return recordFieldNames[column];
        }
    }
    
    /**
     * Gets the column class of a give column.
     *
     * @param column the index of the given column
     *
     * @return A <code>Class</code> object to be the class of this column.
     * It is used to increase the likelihood of being matched up with
     * more appropriate renderers and editors.
     */
    public Class getColumnClass(int column) {
        
        return String.class;
        
    }
    
    /**
     * Returns a <code>List</code> object containing records.
     */
    public List<AddressBookInfo> getList() {
        
        return list;
        
    }
    
}