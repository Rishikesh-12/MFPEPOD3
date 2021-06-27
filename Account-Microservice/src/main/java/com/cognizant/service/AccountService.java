package com.cognizant.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.dto.Statement;
import com.cognizant.dto.TransactionStatus;
import com.cognizant.model.Account;
import com.cognizant.model.Customer;
import com.cognizant.model.Transaction;
import com.cognizant.repository.AccountRepository;
import com.cognizant.repository.CustomerRepository;
import com.cognizant.repository.TransactionRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository repo;
	// private static int temp=1000;
	@Autowired
	private TransactionRepository transactionRepo;

	@Autowired
	private CustomerRepository customerRepo;

	public static String generateAccountId() {
		Random rand = new Random();
		String temp = String.valueOf(rand.nextInt(8000) + 1000);
		String temp1 = String.valueOf(rand.nextInt(6000) + 1000);
		String temp2 = String.valueOf(rand.nextInt(1000) + 1000);
		String id = temp1 + temp2 + String.valueOf(temp);
		temp = temp + 1;
		return id;
	}

	public String createAccount(int id, String type) {
		Customer customer = customerRepo.findById(id).get();
		Account account = new Account(generateAccountId(), customer.getName(), 3000, id, type);
		repo.save(account);
		return account.getId();
	}

	public List<Account> getCustomerAccount(int id) {
		return repo.findByCustId(id);
	}

	public Optional<Account> getAccount(String id) {
		return repo.findById(id);
	}

	public List<Statement> getStatement(long id, String from_date, String to_date) {
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		if (from_date.equals("") && to_date.equals("")) {
			from_date = formatter1.format(new Date());
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.MONTH, -1);
			to_date = formatter1.format(cal.getTime());
		}
		System.out.println(from_date + "     " + to_date);
		List<Transaction> list = transactionRepo.getStatement(from_date, to_date, id);
		List<Statement> statement = new ArrayList<Statement>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		for (Transaction item : list) {
			Statement st = new Statement();
			st.setDate(formatter.format(item.getTransactionDate()));
			st.setNarration("The" + item.getType() + "was successfull");
			st.setReferenceNo(item.getReference());
			st.setAmount(item.getAmount());
			if (item.getType().equalsIgnoreCase("Withdrawal")) {
				st.setWithdrawl(item.getAmount());
				st.setDeposit(0);
			} else {
				st.setWithdrawl(0);
				st.setDeposit(item.getAmount());
			}
			st.setBalance(item.getBalance());
			statement.add(st);
		}
		return statement;
	}

	public TransactionStatus deposit(String id, double amount, String reference) {
		repo.updateAccountDeposit(id, amount);
		Account account = repo.findById(id).get();
		Transaction transaction = new Transaction(0, Long.parseLong(id), account.getOwnerName(), Long.parseLong(id),
				account.getOwnerName(), amount, new Date(), reference, "Deposit", account.getBalance());
		transactionRepo.save(transaction);
		return new TransactionStatus("Deposit Successfull", account.getBalance());
	}

	public TransactionStatus withdrawal(String id, double amount, String reference) {
		repo.updateAccountWithdrawal(id, amount);
		Account account = repo.findById(id).get();
		Transaction transaction = new Transaction(0, Long.parseLong(id), account.getOwnerName(), Long.parseLong(id),
				account.getOwnerName(), amount, new Date(), reference, "Withdrawal", account.getBalance());
		transactionRepo.save(transaction);
		return new TransactionStatus("Withdrawal Successfull", account.getBalance());
	}
}
