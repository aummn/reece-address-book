package com.reece.repo;

import com.reece.model.AddressBookRecord;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * This class is the implementation of the interface AddressBookRepository.
 *
 * @author zhimeng
 * @version 1.0 4/8/2018
 * @since 1.0
 */
public class AddressBookRepositoryImpl implements AddressBookRepository {

    // the data store for address book records
    private Map<Long, AddressBookRecord> addressBookMap = new HashMap<>();

    // the id generator for the address book record
    private AtomicLong keyGenerator = new AtomicLong(0);

    // the data access lock
    private Lock lock = new ReentrantLock();


    public AddressBookRepositoryImpl() {}

    public AddressBookRecord addRecord(AddressBookRecord record) {
        lock.lock();
        try {
            Long key = keyGenerator.incrementAndGet();
            record.setId(key);
            addressBookMap.put(key, record);
            return record;
        } finally {
            lock.unlock();
        }
    }

    public Optional<AddressBookRecord> removeRecord(long id) {
        lock.lock();
        try {
            return Optional.ofNullable(addressBookMap.remove(id));
        } finally {
            lock.unlock();
        }
    }

    public Optional<AddressBookRecord> findRecordById(Long id) {
        lock.lock();
        try {
            return Optional.ofNullable(addressBookMap.get(id));
        } finally {
            lock.unlock();
        }
    }

    public List<AddressBookRecord> findAllRecordsByAbids(List<Long> addressBookIds) {
        if(addressBookIds == null) throw new IllegalArgumentException("address book Ids is required");

        lock.lock();
        try {
            return addressBookIds.stream()
                    .map(this::findAllRecordsByAbid)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        } finally {
            lock.unlock();
        }
    }

    private List<AddressBookRecord> findAllRecordsByAbid(long addressBookId) {
        lock.lock();
        try {
            return addressBookMap.entrySet().stream()
                    .filter(entry -> entry.getValue().getAbid() == addressBookId)
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        } finally {
            lock.unlock();
        }
    }
}
