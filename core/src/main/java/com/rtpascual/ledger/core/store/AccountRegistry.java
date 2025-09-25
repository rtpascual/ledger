package com.rtpascual.ledger.core.store;

import com.rtpascual.ledger.core.model.Account;

import java.util.Map;
import java.util.Optional;

/**
 * Acts as an in-memory repository for Account objects, populated from the ledger file.
 */
public class AccountRegistry {

    private final Map<String, Account> accounts;

    /**
     * The registry is typically created once with all known accounts.
     * @param accounts A map of account names to account objects.
     */
    public AccountRegistry(Map<String, Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * Finds an account by its fully qualified name.
     * @param name The name of the account (e.g., "Assets:Checking").
     * @return An Optional containing the Account if found, otherwise empty.
     */
    public Optional<Account> findByName(String name) {
        return Optional.ofNullable(accounts.get(name));
    }
}
