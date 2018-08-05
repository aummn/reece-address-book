package com.reece.repo;

import com.reece.model.AddressBookInfoRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * This is the implementation class of the interface AddressBookInfoRepository.
 *
 * @author zhimeng
 * @version 1.0 5/8/2018
 * @since 1.0
 */
public class AddressBookInfoRepositoryImpl implements AddressBookInfoRepository {

    // the address book data store
    private Map<Long, AddressBookInfoRecord> addressBookInfoMap = new HashMap<>();

    // the data store lock
    private Lock lock = new ReentrantLock();

    // the ID generator for address books
    private AtomicLong keyGenerator = new AtomicLong(0);

    public AddressBookInfoRepositoryImpl() {}

    public AddressBookInfoRecord addAddressBookInfo(AddressBookInfoRecord record) {
        if(record == null) throw new IllegalArgumentException("address book is required");
        lock.lock();
        try {
            Long key = keyGenerator.incrementAndGet();
            record.setId(key);
            addressBookInfoMap.put(key, record);
            return record;
        } finally {
            lock.unlock();
        }
    }

    public Optional<AddressBookInfoRecord> removeAddressBookInfo(long id) {
        lock.lock();
        try {
            return Optional.ofNullable(addressBookInfoMap.remove(id));
        } finally {
            lock.unlock();
        }
    }

    public List<AddressBookInfoRecord> findAddressBookInfoByName(String name) {
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

    public List<AddressBookInfoRecord> findAllAddressBookInfo() {
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
