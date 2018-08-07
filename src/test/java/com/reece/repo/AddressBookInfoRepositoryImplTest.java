package com.reece.repo;

import com.reece.model.AddressBookInfoRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import static org.assertj.core.api.Assertions.*;

public class AddressBookInfoRepositoryImplTest {
    private AtomicLong keyGenerator;
    private AddressBookInfoRepository repo;

    @Before
    public void runBeforeEveryTest() {
        repo = new AddressBookInfoRepositoryImpl();
        keyGenerator = new AtomicLong(1);
    }

    @After
    public void runAfterEveryTest() {
        repo = null;
    }


    @Test
    public void addAddressBook()
    {
        AddressBookInfoRecord record = new AddressBookInfoRecord(1, "vip");
        AddressBookInfoRecord inputRecord = new AddressBookInfoRecord(6, "vip");
        AddressBookInfoRecord savedRecord = repo.addAddressBookInfo(inputRecord);
        assertThat(savedRecord).isEqualTo(record);
    }

    @Test
    public void addAddressBook_MissingRecord() {

        assertThatThrownBy(() ->
                repo.addAddressBookInfo(null)).hasMessage("address book is required");
    }

    @Test
    public void removeAddressBook() {
        Long key = keyGenerator.get();
        AddressBookInfoRecord record1 = new AddressBookInfoRecord(key, "vip");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record2 = new AddressBookInfoRecord(key, "melbourne");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record3 = new AddressBookInfoRecord(key, "sydney");

        AddressBookInfoRecord savedRecord1 = repo.addAddressBookInfo(record1);
        repo.addAddressBookInfo(record2);
        repo.addAddressBookInfo(record3);

        Optional<AddressBookInfoRecord> recordOptional = repo.removeAddressBookInfo(1L);
        assertThat(recordOptional).isNotEmpty().hasValue(savedRecord1);
    }

    @Test
    public void removeAddressBook_NonExistingAddressBook() {
        Optional<AddressBookInfoRecord> record = repo.removeAddressBookInfo(6L);
        assertThat(record).isEmpty();
    }

    @Test
    public void findAllAddressBook() {
        Long key = keyGenerator.get();
        AddressBookInfoRecord record1 = new AddressBookInfoRecord(key, "vip");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record2 = new AddressBookInfoRecord(key, "melbourne");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record3 = new AddressBookInfoRecord(key, "sydney");
        repo.addAddressBookInfo(record1);
        repo.addAddressBookInfo(record2);
        repo.addAddressBookInfo(record3);

        List<AddressBookInfoRecord> records = repo.findAllAddressBookInfo();
        assertThat(records).isNotEmpty().hasSize(3).extracting("id", "name")
                .contains(tuple(1L, "vip"),
                        tuple(2L, "melbourne"),
                        tuple(3L, "sydney"));
    }

    @Test
    public void findAllAddressBook_EmptyAddressBook() {
        List<AddressBookInfoRecord> records = repo.findAllAddressBookInfo();
        assertThat(records).isEmpty();
    }


    @Test
    public void findAddressBookByName_PartialMatch() {
        Long key = keyGenerator.get();
        AddressBookInfoRecord record1 = new AddressBookInfoRecord(key, "vip");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record2 = new AddressBookInfoRecord(key, "melbourne");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record3 = new AddressBookInfoRecord(key, "sydney");
        repo.addAddressBookInfo(record1);
        repo.addAddressBookInfo(record2);
        repo.addAddressBookInfo(record3);

        List<AddressBookInfoRecord> records = repo.findAddressBookInfoByName("ne");
        assertThat(records).isNotEmpty().hasSize(2).extracting("id", "name")
                .contains(tuple(2L, "melbourne"),
                        tuple(3L, "sydney"));
    }

    @Test
    public void findAddressBookByName_ExactMatch() {
        Long key = keyGenerator.get();
        AddressBookInfoRecord record1 = new AddressBookInfoRecord(key, "vip");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record2 = new AddressBookInfoRecord(key, "melbourne");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record3 = new AddressBookInfoRecord(key, "sydney");
        repo.addAddressBookInfo(record1);
        repo.addAddressBookInfo(record2);
        repo.addAddressBookInfo(record3);

        List<AddressBookInfoRecord> records = repo.findAddressBookInfoByName("vip");
        assertThat(records).isNotEmpty().hasSize(1).extracting("id", "name")
                .contains(tuple(1L, "vip"));
    }

    @Test
    public void findAddressBookByName_AllMatch() {
        Long key = keyGenerator.get();
        AddressBookInfoRecord record1 = new AddressBookInfoRecord(key, "vip");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record2 = new AddressBookInfoRecord(key, "melbourne");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record3 = new AddressBookInfoRecord(key, "sydney");
        repo.addAddressBookInfo(record1);
        repo.addAddressBookInfo(record2);
        repo.addAddressBookInfo(record3);

        List<AddressBookInfoRecord> records = repo.findAddressBookInfoByName("");
        assertThat(records).isNotEmpty().hasSize(3).extracting("id", "name")
                .contains(tuple(1L, "vip"),
                        tuple(2L, "melbourne"),
                        tuple(3L, "sydney"));
    }

    @Test
    public void findAddressBookByName_NoMatchingRecords() {
        Long key = keyGenerator.get();
        AddressBookInfoRecord record1 = new AddressBookInfoRecord(key, "vip");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record2 = new AddressBookInfoRecord(key, "melbourne");
        key = keyGenerator.incrementAndGet();
        AddressBookInfoRecord record3 = new AddressBookInfoRecord(key, "sydney");
        repo.addAddressBookInfo(record1);
        repo.addAddressBookInfo(record2);
        repo.addAddressBookInfo(record3);

        List<AddressBookInfoRecord> records = repo.findAddressBookInfoByName("tom");
        assertThat(records).isEmpty();
    }

    @Test
    public void findAddressBookByName_NullName() {

        assertThatThrownBy(() ->
                repo.findAddressBookInfoByName(null)).hasMessage("name is required");
    }

}
