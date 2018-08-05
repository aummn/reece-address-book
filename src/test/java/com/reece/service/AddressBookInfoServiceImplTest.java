package com.reece.service;

import com.reece.model.AddressBookInfo;
import com.reece.model.AddressBookInfoRecord;
import com.reece.repo.AddressBookInfoRepositoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddressBookInfoServiceImplTest {

    @Mock
    private AddressBookInfoRepositoryImpl repository;

    @InjectMocks
    private AddressBookInfoServiceImpl service;


    @Test
    public void addAddressBookInfo() {
        AddressBookInfo info = new AddressBookInfo();
        info.setName("vip");

        AddressBookInfoRecord record = new AddressBookInfoRecord();
        record.setName("vip");

        AddressBookInfoRecord outputRecord = new AddressBookInfoRecord();
        outputRecord.setId(1L);
        outputRecord.setName("vip");

        when(repository.addAddressBookInfo(eq(record))).thenReturn(outputRecord);
        AddressBookInfo savedInfo = service.addAddressBookInfo(info);
        verify(repository).addAddressBookInfo(eq(record));

        assertThat(savedInfo.getId()).isEqualTo(1L);
        assertThat(savedInfo.getName()).isEqualTo("vip");
    }

    @Test
    public void addAddressBookInfo_MissingAddressBookInfo() {

        assertThatThrownBy(() ->
                service.addAddressBookInfo(null)).hasMessage("address book info is required");
    }

    @Test
    public void removeAddressBookInfo() {
        AddressBookInfo info = new AddressBookInfo();
        info.setId(1L);
        info.setName("vip");

        AddressBookInfoRecord record = new AddressBookInfoRecord();
        record.setId(1L);
        record.setName("vip");

        when(repository.removeAddressBookInfo(1L)).thenReturn(Optional.of(record));
        Optional<AddressBookInfo> infoOptional = service.removeAddressBookInfo(1L);
        verify(repository, times(1)).removeAddressBookInfo(1L);
        assertThat(infoOptional).isNotEmpty().hasValue(info);
    }


    @Test
    public void removeAddressBookInfo_EmptyAddressBookInfo() {
        when(repository.removeAddressBookInfo(1L)).thenReturn(Optional.empty());
        Optional<AddressBookInfo> infoOptional = service.removeAddressBookInfo(1L);
        verify(repository, times(1)).removeAddressBookInfo(1L);
        assertThat(infoOptional).isEmpty();
    }


    @Test
    public void findAllAddressBookInfo() {
        AddressBookInfoRecord outputRecord1 = new AddressBookInfoRecord();
        outputRecord1.setId(1L);
        outputRecord1.setName("vip");

        AddressBookInfoRecord outputRecord2 = new AddressBookInfoRecord();
        outputRecord2.setId(2L);
        outputRecord2.setName("melbourne");

        AddressBookInfoRecord outputRecord3 = new AddressBookInfoRecord();
        outputRecord3.setId(3L);
        outputRecord3.setName("sydney");

        when(repository.findAllAddressBookInfo())
                .thenReturn(Arrays.asList(outputRecord1, outputRecord2, outputRecord3));
        List<AddressBookInfo> savedInfo = service.findAllAddressBookInfo();
        verify(repository).findAllAddressBookInfo();

        assertThat(savedInfo).isNotEmpty().hasSize(3).extracting("id", "name")
                .contains(tuple(1L, "vip"),
                        tuple(2L, "melbourne"),
                        tuple(3L, "sydney"));
    }

    @Test
    public void findAllAddressBookInfo_EmptyAddressBookInfo() {
        when(repository.findAllAddressBookInfo()).thenReturn(new ArrayList<>());
        List<AddressBookInfo> savedInfo = service.findAllAddressBookInfo();
        verify(repository).findAllAddressBookInfo();
        assertThat(savedInfo).isEmpty();
    }



    @Test
    public void findAddressBookInfoByName() {
        AddressBookInfoRecord outputRecord1 = new AddressBookInfoRecord();
        outputRecord1.setId(1L);
        outputRecord1.setName("vip");

        AddressBookInfoRecord outputRecord2 = new AddressBookInfoRecord();
        outputRecord2.setId(2L);
        outputRecord2.setName("melbourne");

        AddressBookInfoRecord outputRecord3 = new AddressBookInfoRecord();
        outputRecord3.setId(3L);
        outputRecord3.setName("sydney");

        when(repository.findAddressBookInfoByName("ne"))
                .thenReturn(Arrays.asList(outputRecord2, outputRecord3));
        List<AddressBookInfo> savedInfo = service.findAddressBookInfoByName("ne");
        verify(repository).findAddressBookInfoByName("ne");

        assertThat(savedInfo).isNotEmpty().hasSize(2).extracting("id", "name")
                .contains(tuple(2L, "melbourne"),
                        tuple(3L, "sydney"));
    }

    @Test
    public void findAddressBookInfoByName_NoMatchingRecord() {
        AddressBookInfoRecord outputRecord1 = new AddressBookInfoRecord();
        outputRecord1.setId(1L);
        outputRecord1.setName("vip");

        AddressBookInfoRecord outputRecord2 = new AddressBookInfoRecord();
        outputRecord2.setId(2L);
        outputRecord2.setName("melbourne");

        AddressBookInfoRecord outputRecord3 = new AddressBookInfoRecord();
        outputRecord3.setId(3L);
        outputRecord3.setName("sydney");

        when(repository.findAddressBookInfoByName("ne")).thenReturn(new ArrayList<>());
        List<AddressBookInfo> savedInfo = service.findAddressBookInfoByName("ne");
        verify(repository).findAddressBookInfoByName("ne");

        assertThat(savedInfo).isEmpty();
    }

    @Test
    public void findAddressBookInfoByName_NameIsNull() {
        AddressBookInfoRecord outputRecord1 = new AddressBookInfoRecord();
        outputRecord1.setId(1L);
        outputRecord1.setName("vip");

        AddressBookInfoRecord outputRecord2 = new AddressBookInfoRecord();
        outputRecord2.setId(2L);
        outputRecord2.setName("melbourne");

        AddressBookInfoRecord outputRecord3 = new AddressBookInfoRecord();
        outputRecord3.setId(3L);
        outputRecord3.setName("sydney");

        when(repository.findAddressBookInfoByName(""))
                .thenReturn(Arrays.asList(outputRecord1, outputRecord2, outputRecord3));
        List<AddressBookInfo> savedInfo = service.findAddressBookInfoByName(null);
        verify(repository).findAddressBookInfoByName("");

        assertThat(savedInfo).isNotEmpty().hasSize(3).extracting("id", "name")
                .contains(tuple(1L, "vip"),
                        tuple(2L, "melbourne"),
                        tuple(3L, "sydney"));
    }

}
