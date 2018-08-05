package com.reece.repo;

import com.reece.model.AddressBook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import static org.assertj.core.api.Assertions.*;

public class AddressBookRepositoryImplTest {
    private AtomicLong keyGenerator;
    private AddressBookRepository repo;

    @Before
    public void runBeforeEveryTest() {
        repo = new AddressBookRepositoryImpl();
        keyGenerator = new AtomicLong(1);
    }

    @After
    public void runAfterEveryTest() {
        repo = null;
    }


    @Test
    public void addAddressBook()
    {
        AddressBook record = new AddressBook(1, "vip");
        AddressBook inputRecord = new AddressBook(6, "vip");
        AddressBook savedRecord = repo.addAddressBook(inputRecord);
        assertThat(savedRecord).isEqualTo(record);
    }

    @Test
    public void addAddressBook_MissingRecord() {

        assertThatThrownBy(() ->
                repo.addAddressBook(null)).hasMessage("address book is required");
    }

    @Test
    public void removeAddressBook() {
        Long key = keyGenerator.get();
        AddressBook record1 = new AddressBook(key, "vip");
        key = keyGenerator.incrementAndGet();
        AddressBook record2 = new AddressBook(key, "melbourne");
        key = keyGenerator.incrementAndGet();
        AddressBook record3 = new AddressBook(key, "sydney");

        AddressBook savedRecord1 = repo.addAddressBook(record1);
        AddressBook savedRecord2 = repo.addAddressBook(record2);
        AddressBook savedRecord3 = repo.addAddressBook(record3);

        Optional<AddressBook> recordOptional = repo.removeAddressBook(1L);
        assertThat(recordOptional).isNotEmpty().hasValue(savedRecord1);
    }

    @Test
    public void removeAddressBook_NonExistingAddressBook() {
        Optional<AddressBook> record = repo.removeAddressBook(6L);
        assertThat(record).isEmpty();
    }

    @Test
    public void findAllAddressBook() {
        Long key = keyGenerator.get();
        AddressBook record1 = new AddressBook(key, "vip");
        key = keyGenerator.incrementAndGet();
        AddressBook record2 = new AddressBook(key, "melbourne");
        key = keyGenerator.incrementAndGet();
        AddressBook record3 = new AddressBook(key, "sydney");
        repo.addAddressBook(record1);
        repo.addAddressBook(record2);
        repo.addAddressBook(record3);

        List<AddressBook> records = repo.findAllAddressBook();
        assertThat(records).isNotEmpty().hasSize(3).extracting("id", "name")
                .contains(tuple(1L, "vip"),
                        tuple(2L, "melbourne"),
                        tuple(3L, "sydney"));
    }

    @Test
    public void findAllAddressBook_EmptyAddressBook() {
        List<AddressBook> records = repo.findAllAddressBook();
        assertThat(records).isEmpty();
    }


    @Test
    public void findAddressBookByName_PartialMatch() {
        Long key = keyGenerator.get();
        AddressBook record1 = new AddressBook(key, "vip");
        key = keyGenerator.incrementAndGet();
        AddressBook record2 = new AddressBook(key, "melbourne");
        key = keyGenerator.incrementAndGet();
        AddressBook record3 = new AddressBook(key, "sydney");
        repo.addAddressBook(record1);
        repo.addAddressBook(record2);
        repo.addAddressBook(record3);

        List<AddressBook> records = repo.findAddressBookByName("ne");
        assertThat(records).isNotEmpty().hasSize(2).extracting("id", "name")
                .contains(tuple(2L, "melbourne"),
                        tuple(3L, "sydney"));
    }

    @Test
    public void findAddressBookByName_ExactMatch() {
        Long key = keyGenerator.get();
        AddressBook record1 = new AddressBook(key, "vip");
        key = keyGenerator.incrementAndGet();
        AddressBook record2 = new AddressBook(key, "melbourne");
        key = keyGenerator.incrementAndGet();
        AddressBook record3 = new AddressBook(key, "sydney");
        repo.addAddressBook(record1);
        repo.addAddressBook(record2);
        repo.addAddressBook(record3);

        List<AddressBook> records = repo.findAddressBookByName("vip");
        assertThat(records).isNotEmpty().hasSize(1).extracting("id", "name")
                .contains(tuple(1L, "vip"));
    }

    @Test
    public void findAddressBookByName_AllMatch() {
        Long key = keyGenerator.get();
        AddressBook record1 = new AddressBook(key, "vip");
        key = keyGenerator.incrementAndGet();
        AddressBook record2 = new AddressBook(key, "melbourne");
        key = keyGenerator.incrementAndGet();
        AddressBook record3 = new AddressBook(key, "sydney");
        repo.addAddressBook(record1);
        repo.addAddressBook(record2);
        repo.addAddressBook(record3);

        List<AddressBook> records = repo.findAddressBookByName("");
        assertThat(records).isNotEmpty().hasSize(3).extracting("id", "name")
                .contains(tuple(1L, "vip"),
                        tuple(2L, "melbourne"),
                        tuple(3L, "sydney"));
    }

    @Test
    public void findAddressBookByName_NoMatchingRecords() {
        Long key = keyGenerator.get();
        AddressBook record1 = new AddressBook(key, "vip");
        key = keyGenerator.incrementAndGet();
        AddressBook record2 = new AddressBook(key, "melbourne");
        key = keyGenerator.incrementAndGet();
        AddressBook record3 = new AddressBook(key, "sydney");
        repo.addAddressBook(record1);
        repo.addAddressBook(record2);
        repo.addAddressBook(record3);

        List<AddressBook> records = repo.findAddressBookByName("tom");
        assertThat(records).isEmpty();
    }

    @Test
    public void findAddressBookByName_NullName() {

        assertThatThrownBy(() ->
                repo.findAddressBookByName(null)).hasMessage("name is required");
    }


}
