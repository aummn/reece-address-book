package com.reece.repo;

import com.reece.model.AddressBook;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * This is the implementation class of the interface AddressBookRepository.
 *
 * @author zhimeng
 * @version 1.0 5/8/2018
 * @since 1.0
 */
public class AddressBookRepositoryImpl implements AddressBookRepository {

    // the address book data store
    private Map<Long, AddressBook> addressBookInfoMap = new HashMap<>();

    // the data store lock
    private Lock lock = new ReentrantLock();

    // the ID generator for address books
    private AtomicLong keyGenerator = new AtomicLong(0);

    public AddressBookRepositoryImpl() {}

    public AddressBook addAddressBook(AddressBook addressBook) {
        if(addressBook == null) throw new IllegalArgumentException("address book is required");
        lock.lock();
        try {
            Long key = keyGenerator.incrementAndGet();
            addressBook.setId(key);
            addressBookInfoMap.put(key, addressBook);
            return addressBook;
        } finally {
            lock.unlock();
        }
    }

    public Optional<AddressBook> removeAddressBook(long id) {
        lock.lock();
        try {
            return Optional.ofNullable(addressBookInfoMap.remove(id));
        } finally {
            lock.unlock();
        }
    }

    public List<AddressBook> findAddressBookByName(String name) {
        if(name == null) throw new IllegalArgumentException("name is required");
        lock.lock();
        try {
            return addressBookInfoMap.entrySet().stream()
                    .filter(entry -> entry.getValue().getName().contains(name))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        } finally {
            lock.unlock();
        }
    }

    public List<AddressBook> findAllAddressBook() {
        lock.lock();
        try {
            return addressBookInfoMap.entrySet().stream()
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        } finally {
            lock.unlock();
        }
    }
}
