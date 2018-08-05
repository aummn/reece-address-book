package com.reece.service;

import com.reece.model.AddressBookInfo;
import com.reece.model.AddressBookInfoRecord;
import com.reece.repo.AddressBookInfoRepository;
import com.reece.repo.AddressBookInfoRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This is the implementation of the interface AddressBookInfoService.
 *
 * @author zhimeng
 * @version 1.0 5/8/2018
 * @since 1.0
 */
public class AddressBookInfoServiceImpl implements AddressBookInfoService {

    private AddressBookInfoRepository repo;

    public AddressBookInfoServiceImpl() {
        this.repo = new AddressBookInfoRepositoryImpl();
    }

    public AddressBookInfo addAddressBookInfo(AddressBookInfo info) {
        if(info == null) throw new IllegalArgumentException("address book info is required");
        AddressBookInfoRecord record = new AddressBookInfoRecord(info.getName());
        AddressBookInfoRecord savedRecord = repo.addAddressBookInfo(record);
        return new AddressBookInfo(savedRecord.getId(),savedRecord.getName());
    }

    public Optional<AddressBookInfo> removeAddressBookInfo(long id) {
        return repo.removeAddressBookInfo(id).map(r -> new AddressBookInfo(r.getId(), r.getName()));
    }

    public List<AddressBookInfo> findAddressBookInfoByName(String name) {
        if (name == null) name = "";
        return repo.findAddressBookInfoByName(name).stream()
                .map(r -> new AddressBookInfo(r.getId(), r.getName()))
                .collect(Collectors.toList());
    }

    public List<AddressBookInfo> findAllAddressBookInfo() {
        return repo.findAllAddressBookInfo().stream()
                .map(r -> new AddressBookInfo(r.getId(), r.getName()))
                .collect(Collectors.toList());
    }

}
