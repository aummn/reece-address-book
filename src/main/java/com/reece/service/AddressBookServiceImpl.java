package com.reece.service;

import com.reece.model.AddressBookRecord;
import com.reece.model.Contact;
import com.reece.repo.AddressBookRepository;
import com.reece.repo.AddressBookRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This class is the implementation of the interface AddressBookService.
 *
 * @author zhimeng
 * @version 1.0 5/8/2018
 * @since 1.0
 */
public class AddressBookServiceImpl implements AddressBookService {

    private AddressBookRepository repo;

    public AddressBookServiceImpl() {
        this.repo = new AddressBookRepositoryImpl();
    }

    public Contact addContact(Contact c, long addressBookId) {
        if(c == null) throw new IllegalArgumentException("contact is required");
        AddressBookRecord record = new AddressBookRecord(c.getName(),c.getPhone(), addressBookId);
        AddressBookRecord savedRecord = repo.addRecord(record);
        return new Contact(savedRecord.getId(),savedRecord.getName(), savedRecord.getPhone());
    }

    public Optional<Contact> removeContact(long id) {
        return repo.removeRecord(id).map(r -> new Contact(r.getId(), r.getName(), r.getPhone()));
    }

    public Optional<Contact> findContact(long id) {
        return repo.findRecordById(id).map(r -> new Contact(r.getId(), r.getName(), r.getPhone()));
    }

    public List<Contact> findAllContacts(List<Long> addressBookIds) {
        if(addressBookIds == null) throw new IllegalArgumentException("address book ids is required");
        return repo.findAllRecordsByAbids(addressBookIds).stream()
                .map(record -> new Contact(record.getId(), record.getName(), record.getPhone()))
                .collect(Collectors.toList());
    }

    public List<Contact> findAllUniqueContacts(List<Long> addressBookIds) {
        if(addressBookIds == null) throw new IllegalArgumentException("address book ids is required");
        return repo.findAllRecordsByAbids(addressBookIds).stream()
                .map(record -> new Contact(record.getId(), record.getName(), record.getPhone()))
                .filter(distinctByKey(Contact::getContents))
                .collect(Collectors.toList());
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> uniqueContactSet = ConcurrentHashMap.newKeySet();
        return t -> uniqueContactSet.add(keyExtractor.apply(t));
    }
}
