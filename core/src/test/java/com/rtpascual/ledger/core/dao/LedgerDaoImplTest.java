package com.rtpascual.ledger.core.dao;

import com.rtpascual.ledger.core.model.Account;
import com.rtpascual.ledger.core.model.Posting;
import com.rtpascual.ledger.core.model.Transaction;
import com.rtpascual.ledger.core.model.Transaction.TransactionBuilder;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LedgerDaoImplTest {

    private LedgerDao ledgerDao;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() {
        URL resource = getClass().getResource("/com/rtpascual/ledger/core/dao/resources/ledger.txt");
        File ledgerFile = new File(resource.getFile());
        ledgerDao = new LedgerDaoImpl(ledgerFile.getAbsolutePath());
    }

    @Test
    public void testGetAllTransactions() {
        List<Transaction> transactions = ledgerDao.getAllTransactions();

        assertEquals(3, transactions.size());

        Transaction transaction1 = transactions.get(0);
        assertEquals(LocalDate.of(2024, 7, 29), transaction1.getDate());
        assertEquals("Coffee Shop", transaction1.getDescription());
        assertEquals(2, transaction1.getPostings().size());
        assertEquals("Expenses:Coffee", transaction1.getPostings().get(0).getAccount().getName());
        assertEquals(new BigDecimal("5.00"), transaction1.getPostings().get(0).getAmount());

        Transaction transaction2 = transactions.get(1);
        assertEquals(LocalDate.of(2024, 7, 30), transaction2.getDate());
        assertEquals("Supermarket", transaction2.getDescription());
        assertEquals(2, transaction2.getPostings().size());
        assertEquals("Expenses:Groceries", transaction2.getPostings().get(0).getAccount().getName());
        assertEquals(new BigDecimal("150.00"), transaction2.getPostings().get(0).getAmount());

        Transaction transaction3 = transactions.get(2);
        assertEquals(LocalDate.of(2024, 7, 31), transaction3.getDate());
        assertEquals("Gas Station", transaction3.getDescription());
        assertEquals(2, transaction3.getPostings().size());
        assertEquals("Expenses:Gas", transaction3.getPostings().get(0).getAccount().getName());
        assertEquals(new BigDecimal("45.00"), transaction3.getPostings().get(0).getAmount());
    }

    @Test
    public void testSaveTransaction() throws IOException {
        File tempFile = tempFolder.newFile("test_save.ledger");
        LedgerDao tempLedgerDao = new LedgerDaoImpl(tempFile.getAbsolutePath());

        TransactionBuilder transactionBuilder = Transaction.builder()
            .date(LocalDate.of(2025, 1, 1))
            .description("Gas");
        Account expenseAccount = Account.builder()
            .name("Expenses:Gas")
            .build();
        Posting expensePosting = Posting.builder()
            .account(expenseAccount)
            .amount(new BigDecimal("40.00"))
            .build();
        Account assetAccount = Account.builder()
            .name("Assets:Checking")
            .build();
        Posting assetPosting = Posting.builder()
            .account(assetAccount)
            .amount(new BigDecimal("-40.00"))
            .build();
        List<Posting> postings = new ArrayList<>(List.of(expensePosting, assetPosting));
        transactionBuilder.postings(postings);
        Transaction transaction = transactionBuilder.build();

        tempLedgerDao.saveTransaction(transaction);

        String content = new String(Files.readAllBytes(tempFile.toPath()));
        String expectedContent = "\n2025-01-01 Gas\n    Expenses:Gas    $40.00\n    Assets:Checking    $-40.00\n";
        assertEquals(expectedContent, content);
    }
}
