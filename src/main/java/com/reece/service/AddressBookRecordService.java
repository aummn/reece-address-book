package com.reece.service;

import com.reece.model.Contact;

import java.util.List;
import java.util.Optional;

/**
 * An interface for contact operations.
 *
 * @author zhimeng
 * @version 1.0 4/8/2018
 * @since 1.0
 */
public interface AddressBookRecordService {

    /**
     * Add a contact to an address book.
     *
     * @param contact a contact with name and phone
     * @param addressBookId the id of an address book
     * @return a Contact object
     */
    Contact addContact(Contact contact, long addressBookId);

    /**
     * Remove a contact.
     *
     * @param id the id of a contact
     * @return an Optional object
     *
     */
    Optional<Contact> removeContact(long id);

    /**
     * Find a contact.
     *
     * @param id the id of a contact
     * @return an Optional object
     *
     */
    Optional<Contact> findContact(long id);

    /**
     * Find all contacts from multiple address books.
     *
     * @param addressBookIds the ids of address books
     * @return a list of Contact objects
     *
     */
    List<Contact> findAllContacts(List<Long> addressBookIds);

    /**
     * Find all unique contacts from multiple address books.
     *
     * @param addressBookIds the ids of address books
     * @return a list of Contact objects
     *
     */
    List<Contact> findAllUniqueContacts(List<Long> addressBookIds);


}
