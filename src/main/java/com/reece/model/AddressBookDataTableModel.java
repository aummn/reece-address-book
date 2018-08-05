package com.reece.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;


/**
 * This class provides a table model to store contact data.
 *
 * @author zhimeng
 * @version 1.0 5/8/2018
 * @since 1.0
 */
public class AddressBookDataTableModel extends AbstractTableModel {

    /**
     * the table column names
     */
    public static String[] ADDRESS_BOOK_RECORD_FIELD_NAMES = {"id", "name", "phone"};

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
     * a <code>List</code> object containing contact data
     */
    private List<Contact> list;

    /**
     * a <code>String</code> array containing the field names of a record
     */
    private String[] recordFieldNames;


    /**
     * Creates a new <code>AddressBookDataTableModel</code> object based on
     * a <code>List</code> object.
     *
     * @param list a <code>List</code> containing the contact data
     * @param recordFieldNames the field names of a record
     * @param rowCount the number of row in a table
     * @param columnCount the number of columns in a table
     */
    public AddressBookDataTableModel(List<Contact> list, String[] recordFieldNames,
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
        if (list != null) {

            Contact contact = list.get(row);

            switch(column) {
                case 0:
                    value = contact.getId();
                    break;
                case 1:
                    value = contact.getName();
                    break;
                case 2:
                    value = contact.getPhone();
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
            
            return AddressBookDataTableModel.ADDRESS_BOOK_RECORD_FIELD_NAMES[column];
            
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
    public List getList() {
        
        return list;
        
    }
    
}