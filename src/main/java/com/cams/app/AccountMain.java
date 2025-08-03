package com.cams.app;

import com.cams.AppConfig;
import com.cams.dto.AccountDto;
import com.cams.exception.AccountNotFoundException;
import com.cams.exception.InsufficientFundsException;
import com.cams.exception.InsufficientOpeningBalanceException;
import com.cams.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
@Component
public class AccountMain {
    AccountService accountService;

    @Autowired
    public AccountMain(AccountService accountService) {
        this.accountService = accountService;
    }

    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        AccountMain accountMain = applicationContext.getBean(AccountMain.class);

        Scanner sc = new Scanner(System.in);
        int choice = 0;

        while(choice != 7) {
            System.out.println("================= Welcome to MyBankApp ====================");
            System.out.println("\n 1.Create a account\n " +
                                "2. Deposit Funds\n " +
                                "3. Withdraw Funds\n " +
                                "4. Display Account Details\n " +
                                "5. Sort Accounts\n " +
                                "6. List all accounts\n " +
                                "7. Exit\n");
            System.out.print("Enter your Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: accountMain.createAccount(); break;
                case 2: accountMain.deposit(); break;
                case 3: accountMain.withdraw(); break;
                case 4: accountMain.displayAccountDetail(); break;
                case 5: accountMain.sortAccounts(); break;
                case 6: accountMain.listAllAccounts(); break;
                case 7: break;
                default: System.out.println("Invalid choice");   break;
            }
        }
    }

    private void createAccount() {
        int result = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name");
        String name = sc.nextLine();

        System.out.println("Enter your opening Balance(Min: 3000)");
        Double amount = sc.nextDouble();

        AccountDto accountDto = new AccountDto(name, amount);
        try {
            result = accountService.createAccount(accountDto);
        } catch (InsufficientOpeningBalanceException e) {
            System.err.println(e);
            System.out.println("Minimum Opening balance is 3000");
        }

        if(result > 0) {
            System.out.println(result + " row inserted");
            try {
                System.out.println(accountService.displayAccount(accountDto));
            } catch (AccountNotFoundException e) {
                System.err.println(e);
            }
        }
    }

    private void deposit() {
        int result = 0;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter account id: ");
        Integer id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter account name: ");
        String name = sc.nextLine();


        System.out.print("Enter amount to be Deposit: ");
        Double amount = sc.nextDouble();

        AccountDto accountDto = new AccountDto(id, name);
        try {
            result = accountService.deposit(accountDto, amount);
        } catch (AccountNotFoundException e) {
            System.err.println(e);
        }

        if(result > 0) {
            System.out.println("Balance Updated");
            try {
                System.out.println(accountService.displayAccount(accountDto));
            } catch (AccountNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void withdraw() {
        int result = 0;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter account id: ");
        Integer id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter account name: ");
        String name = sc.nextLine();

        System.out.print("Enter amount to be Withdraw: ");
        Double amount = sc.nextDouble();

        AccountDto accountDto = new AccountDto(id, name);
        try {
            result = accountService.withdraw(accountDto, amount);
        } catch (AccountNotFoundException e) {
            System.err.println(e);
        } catch (InsufficientFundsException e) {
            System.err.println(e);
        }

        if(result > 0) {
            System.out.println("Balance Updated");
            try {
                System.out.println(accountService.displayAccount(accountDto));
            } catch (AccountNotFoundException e) {
                System.err.println(e);
            }
        }
    }

    void displayAccountDetail() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter account id: ");
        Integer id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter account name: ");
        String name = sc.nextLine();

        AccountDto accountDto = new AccountDto(id, name);
        try {
            System.out.println(accountService.displayAccount(accountDto));
        } catch (AccountNotFoundException e) {
            System.err.println(e);
        }
    }

    void sortAccounts() {
        int choice;
        System.out.println("1. Sort account by Id");
        System.out.println("2. Sort account by Name");
        System.out.println("3. Sort account by Balance");
        System.out.print("\nEnter your choice: ");
        Scanner sc = new Scanner(System.in);
        choice = sc.nextInt();
        List<AccountDto> accountDtoList = accountService.sortAccounts(choice);
        for(AccountDto account: accountDtoList) {
            System.out.println(account);
        }
    }

    void listAllAccounts() {
        List<AccountDto> accountDtoList = accountService.sortAccounts(1);
        for(AccountDto account: accountDtoList) {
            System.out.println(account);
        }
    }

}
