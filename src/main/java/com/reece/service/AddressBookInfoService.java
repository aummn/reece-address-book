package com.reece.service;

import com.reece.model.AddressBookInfo;
import java.util.List;
import java.util.Optional;

/**
 * An interface for address book info operations.
 *
 * @author zhimeng
 * @version 1.0 5/8/2018
 * @since 1.0
 */
public interface AddressBookInfoService {

    /**
     * Add an address book info.
     *
     * @param addressBookInfo the address book info
     * @return an AddressBookInfo object
     */
    AddressBookInfo addAddressBookInfo(AddressBookInfo addressBookInfo);

    /**
     * Remove an address book info.
     *
     * @param id the id of the address book info
     * @return an Optional object
     *
     */
    Optional<AddressBookInfo> removeAddressBookInfo(long id);

    /**
     * Find address book info. matching the specified name
     *
     * @param name the name of address books
     * @return an list of AddressBookInfo objects
     *
     */
    List<AddressBookInfo> findAddressBookInfoByName(String name);

    /**
     * Find all address book info.
     *
     * @return a list of AddressBookInfo objects.
     *
     */
    List<AddressBookInfo> findAllAddressBookInfo();

}
