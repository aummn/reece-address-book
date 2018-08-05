package com.reece.repo;

import com.reece.model.AddressBookInfoRecord;

import java.util.List;
import java.util.Optional;

/**
 * This interface defining methods for saving, removing and retrieving address book info.
 *
 * @author zhimeng
 * @version 1.0 5/8/2018
 * @since 1.0
 */
public interface AddressBookInfoRepository {

    /**
     * Add an address book.
     *
     * @param record the address book info record
     * @return the AddressBookInfoRecord object
     */
    AddressBookInfoRecord addAddressBookInfo(AddressBookInfoRecord record);

    /**
     * Remove a record.
     *
     * @param id the id of an address book
     * @return an Optional<AddressBookInfoRecord> object
     */
    Optional<AddressBookInfoRecord> removeAddressBookInfo(long id);

    /**
     * Find address books matching the specified name
     *
     * @param name the name of address book
     * @return an list of AddressBookInfoRecord objects
     *
     */
    List<AddressBookInfoRecord> findAddressBookInfoByName(String name);

    /**
     * Find all address books.
     *
     * @return a list of AddressBookInfoRecord objects
     */
    List<AddressBookInfoRecord> findAllAddressBookInfo();

}
