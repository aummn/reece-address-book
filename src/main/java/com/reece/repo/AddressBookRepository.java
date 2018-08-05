package com.reece.repo;

import com.reece.model.AddressBook;

import java.util.List;
import java.util.Optional;

/**
 * This interface defining methods for saving, removing and retrieving address book info.
 *
 * @author zhimeng
 * @version 1.0 5/8/2018
 * @since 1.0
 */
public interface AddressBookRepository {

    /**
     * Add an address book.
     *
     * @param addressBook the address book
     * @return the AddressBook object
     */
    AddressBook addAddressBook(AddressBook addressBook);

    /**
     * Remove a record.
     *
     * @param id the id of an address book
     * @return an Optional<AddressBook> object
     */
    Optional<AddressBook> removeAddressBook(long id);

    /**
     * Find address books matching the specified name
     *
     * @param name the name of address book
     * @return an list of AddressBook objects
     *
     */
    List<AddressBook> findAddressBookByName(String name);

    /**
     * Find all address books.
     *
     * @return a list of AddressBook objects
     */
    List<AddressBook> findAllAddressBook();

}
