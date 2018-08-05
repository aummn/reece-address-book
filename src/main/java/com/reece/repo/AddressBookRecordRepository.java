package com.reece.repo;

import com.reece.model.AddressBookRecord;

import java.util.List;
import java.util.Optional;

/**
 * This interface defines methods for address book operations.
 *
 * @author zhimeng
 * @version 1.0 4/8/2018
 * @since 1.0
 */
public interface AddressBookRecordRepository {

    /**
     * Add a record to an address book.
     *
     * @param record the address book record
     * @return the added address book record
     */
    AddressBookRecord addRecord(AddressBookRecord record);

    /**
     * Remove a record.
     *
     * @param id the id of an address book record
     * @return the removed address book record
     *
     */
    Optional<AddressBookRecord> removeRecord(long id);

    /**
     * Find an address book record by id.
     *
     * @param id the id of an address book record
     * @return the matching address book record
     *
     */
    Optional<AddressBookRecord> findRecordById(Long id);

    /**
     * Find a list of address book records by name, or phone
     *
     * @param searchString the string containing name, or phone
     *
     */
    List<AddressBookRecord> findRecord(String searchString);

    /**
     * Find all records from multiple address books.
     *
     * @param addressBookIds the ids of address books
     * @return a list of address book records
     */
    List<AddressBookRecord> findAllRecordsByAbids(List<Long> addressBookIds);

}
