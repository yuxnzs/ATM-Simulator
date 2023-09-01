import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class ATMSimulator {
    private final int accountNumber; // Immutable account number
    private int passcode;
    private double balance = 0.0;

    // Implementing encapsulation on private member balance
    private void setBalance(double balance) {
        this.balance = balance;
    }

    private void addBalance(double amount) {
        this.balance += amount;
    }

    private void subtractBalance(double amount) {
        this.balance -= amount;
    }

    // Constructor to initialize account with account number and passcode
    public ATMSimulator(int accountNumber, int passcode) {
        this.accountNumber = accountNumber;
        this.passcode = passcode;
    }

    // Getter methods for account details
    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    // Validates passcode without exposing it for security reasons. (Without using getPasscode())
    public boolean validatePasscode(int inputPasscode) {
        return this.passcode == inputPasscode;
    }

    // Method to display account balance
    public void checkBalance() {
        System.out.println("Your balance is: $" + this.getBalance() + '\n');
    }

    // Method to deposit money into the account
    public void deposit(double amount) {
        addBalance(amount);
        System.out.println("You have deposited $" + amount + " to your account.");
        System.out.println("Your balance is: $" + this.balance + '\n');
    }

    // Method to withdraw money from the account
    public void withdraw(double amount) {
        if (amount <= this.balance) {
            subtractBalance(amount);
            System.out.println("You have withdrawn $" + amount + " from your account.");
            System.out.println("Your balance is: $" + this.balance + '\n');
        } else {
            System.out.println("Invalid amount. Please try again.");
            System.out.println("Your balance is: $" + this.balance + '\n');
        }
    }

    // Method to transfer money to another account
    public void transfer(double amount, ATMSimulator recipient) {
        if (amount <= this.balance) {
            subtractBalance(amount);
            recipient.addBalance(amount); // Update the recipient's balance
            System.out.println("You have transferred $" + amount + " to account " + recipient.getAccountNumber());
            System.out.println("Your balance is: $" + this.balance + '\n');
        } else {
            System.out.println("Invalid amount. Please try again.");
        }
    }

    // Method to change the account's passcode
    public void changePasscode(int newPasscode) {
        this.passcode = newPasscode;
        System.out.println("Passcode changed successfully!\n");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<ATMSimulator> accounts = new ArrayList<>();
        int invalidInputCount = 0;
        final int MAX_INVALID_INPUTS = 3;

        // Initializing default accounts
        accounts.add(new ATMSimulator(123, 123));
        accounts.add(new ATMSimulator(456, 456));

        int mainMenuOption = 0;
        do {
            System.out.println("**********************************");
            System.out.println("   Please select an option:");
            System.out.println("   1. Create a new account");
            System.out.println("   2. Access existing account");
            System.out.println("   3. Exit");
            System.out.println("**********************************");

            try {
                mainMenuOption = scanner.nextInt(); // Get user choice for main menu
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid numeric value.");
                scanner.nextLine(); // Clear the input buffer to discard invalid input
                invalidInputCount++; // Increment the count for invalid inputs
                if (invalidInputCount >= MAX_INVALID_INPUTS) {
                    System.out.println("Too many invalid inputs. Please wait for 10 seconds before trying again.\n");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException innerException) {
                        System.out.println("Something went wrong. Program terminating.\n");
                        System.exit(0);  // Terminate the program
                    }
                    invalidInputCount = 0;  // Reset the count for invalid inputs
                }
                continue;
            }

            switch (mainMenuOption) {
                case 1 -> {  // Create a new account
                    int newAccNumber = 0;
                    int newPasscode = 0;
                    try {
                        System.out.print("Enter a new account number: ");
                        newAccNumber = scanner.nextInt(); // Get new account number from the user
                        System.out.print("Enter a new passcode: ");
                        newPasscode = scanner.nextInt(); // Get new passcode from the user
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter valid numeric values.");
                        scanner.nextLine(); // Clear the input buffer to discard invalid input
                        invalidInputCount++; // Increment the count for invalid inputs
                        if (invalidInputCount >= MAX_INVALID_INPUTS) {
                            System.out.println("Too many invalid inputs. Please wait for 10 seconds before trying again.\n");
                            try {
                                Thread.sleep(10000); // Pause the execution for 10 seconds
                            } catch (InterruptedException innerException) {
                                System.out.println("Something went wrong. Program terminating.\n");
                                System.exit(0);  // Terminate the program if an exception occurs
                            }
                            invalidInputCount = 0;  // Reset the count for invalid inputs
                        }
                        continue;
                    }
                    // Create a new ATM account instance with given account number and passcode
                    ATMSimulator newAccount = new ATMSimulator(newAccNumber, newPasscode);

                    // Check if the new account number is already in use
                    boolean isDuplicate = false;
                    for (ATMSimulator account : accounts) { // Iterate the ArrayList and see if there are the same account number
                        if (account.getAccountNumber() == newAccNumber) {
                            isDuplicate = true; // Mark as duplicate if the account number is found
                            break;
                        }
                    }
                    if (isDuplicate) {
                        System.out.println("The account number you entered is already in use. Please try a different number.\n");
                    } else {
                        // If the account number is unique, add the new account to the list
                        accounts.add(newAccount);
                        System.out.println("New account created successfully!\n");
                    }
                }
                case 2 -> {  // Access an existing account
                    int accountNumber = 0;
                    int passcode = 0;
                    try {
                        System.out.print("Enter your account number: ");
                        accountNumber = scanner.nextInt(); // Get account number from the user
                        System.out.print("Enter your passcode: ");
                        passcode = scanner.nextInt(); // Get passcode from the user
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter valid numeric values.");
                        scanner.nextLine(); // Clear the input buffer to discard invalid input
                        invalidInputCount++; // Increment the count for invalid inputs
                        if (invalidInputCount >= MAX_INVALID_INPUTS) {
                            System.out.println("Too many invalid inputs. Please wait for 10 seconds before trying again.\n");
                            try {
                                Thread.sleep(10000); // Pause the execution for 10 seconds
                            } catch (InterruptedException innerException) {
                                System.out.println("Something went wrong. Program terminating.\n");
                                System.exit(0);  // Terminate the program if an exception occurs
                            }
                            invalidInputCount = 0;  // Reset the count for invalid inputs
                        }
                        continue;
                    }
                    ATMSimulator currentAccount = null;
                    for (ATMSimulator account : accounts) {
                        if (account.getAccountNumber() == accountNumber && account.validatePasscode(passcode)) {
                            currentAccount = account;
                            break;
                        }
                    }
                    if (currentAccount == null) {
                        System.out.println("The account number or passcode is incorrect. Please try again.\n");
                    } else {
                        System.out.println("You have successfully logged in!\n");
                        int accountMenuOption = 0;
                        do {
                            System.out.println("**********************************");
                            System.out.println("     Please select an option:");
                            System.out.println("     1. Check balance");
                            System.out.println("     2. Deposit");
                            System.out.println("     3. Withdraw");
                            System.out.println("     4. Transfer");
                            System.out.println("     5. Change passcode");
                            System.out.println("     6. Logout");
                            System.out.println("**********************************");

                            try {
                                accountMenuOption = scanner.nextInt(); // Get account menu choice from the user
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid numeric value.");
                                scanner.nextLine(); // Clear the input buffer to discard invalid input
                                invalidInputCount++; // Increment the count for invalid inputs
                                if (invalidInputCount >= MAX_INVALID_INPUTS) {
                                    System.out.println("Too many invalid inputs. Please wait for 10 seconds before trying again.\n");
                                    try {
                                        Thread.sleep(10000); // Pause the execution for 10 seconds
                                    } catch (InterruptedException innerException) {
                                        System.out.println("Something went wrong. Program terminating.\n");
                                        System.exit(0);  // Terminate the program if an exception occurs
                                    }
                                    invalidInputCount = 0;  // Reset the count for invalid inputs
                                }
                                continue;
                            }

                            switch (accountMenuOption) {
                                case 1 -> currentAccount.checkBalance();  // Check balance
                                case 2 -> {  // Deposit
                                    System.out.print("Enter deposit amount: ");
                                    double depositAmount = 0;
                                    try {
                                        depositAmount = scanner.nextDouble(); // Get deposit amount from the user
                                        if (depositAmount <= 0) {
                                            System.out.println("Invalid deposit amount. Please enter a positive value.");
                                            continue;
                                        }
                                    } catch (InputMismatchException e) {
                                        System.out.println("Invalid input. Please enter valid numeric values.");
                                        scanner.nextLine(); // Clear the input buffer to discard invalid input
                                        invalidInputCount++; // Increment the count for invalid inputs
                                        if (invalidInputCount >= MAX_INVALID_INPUTS) {
                                            System.out.println("Too many invalid inputs. Please wait for 10 seconds before trying again.\n");
                                            try {
                                                Thread.sleep(10000); // Pause the execution for 10 seconds
                                            } catch (InterruptedException innerException) {
                                                System.out.println("Something went wrong. Program terminating.\n");
                                                System.exit(0);  // Terminate the program if an exception occurs
                                            }
                                            invalidInputCount = 0;  // Reset the count for invalid inputs
                                        }
                                        continue;
                                    }
                                    currentAccount.deposit(depositAmount);
                                }
                                case 3 -> {  // Withdraw
                                    System.out.print("Enter withdrawal amount: ");
                                    double withdrawAmount = 0;
                                    try {
                                        withdrawAmount = scanner.nextDouble(); // Get withdrawal amount from the user
                                        if (withdrawAmount <= 0) {
                                            System.out.println("Invalid withdrawal amount. Please enter a positive value.");
                                            continue;
                                        }
                                    } catch (InputMismatchException e) {
                                        System.out.println("Invalid input. Please enter valid numeric values.");
                                        scanner.nextLine(); // Clear the input buffer to discard invalid input
                                        invalidInputCount++; // Increment the count for invalid inputs
                                        if (invalidInputCount >= MAX_INVALID_INPUTS) {
                                            System.out.println("Too many invalid inputs. Please wait for 10 seconds before trying again.\n");
                                            try {
                                                Thread.sleep(10000); // Pause the execution for 10 seconds
                                            } catch (InterruptedException innerException) {
                                                System.out.println("Something went wrong. Program terminating.\n");
                                                System.exit(0);  // Terminate the program if an exception occurs
                                            }
                                            invalidInputCount = 0;  // Reset the count for invalid inputs
                                        }
                                        continue;
                                    }
                                    currentAccount.withdraw(withdrawAmount);
                                }
                                case 4 -> {  // Transfer
                                    System.out.print("Enter recipient's account number: ");
                                    int recipientAccountNumber = 0;
                                    try {
                                        recipientAccountNumber = scanner.nextInt(); // Get recipient's account number from the user
                                    } catch (InputMismatchException e) {
                                        System.out.println("Invalid input. Please enter valid numeric values.");
                                        scanner.nextLine(); // Clear the input buffer to discard invalid input
                                        invalidInputCount++; // Increment the count for invalid inputs
                                        if (invalidInputCount >= MAX_INVALID_INPUTS) {
                                            System.out.println("Too many invalid inputs. Please wait for 10 seconds before trying again.\n");
                                            try {
                                                Thread.sleep(10000); // Pause the execution for 10 seconds
                                            } catch (InterruptedException innerException) {
                                                System.out.println("Something went wrong. Program terminating.\n");
                                                System.exit(0);  // Terminate the program if an exception occurs
                                            }
                                            invalidInputCount = 0;  // Reset the count for invalid inputs
                                        }
                                        continue;
                                    }

                                    ATMSimulator recipient = null;
                                    for (ATMSimulator account : accounts) {
                                        if (account.getAccountNumber() == recipientAccountNumber) {
                                            recipient = account;
                                            break;
                                        }
                                    }
                                    if (recipient == null) {
                                        System.out.println("Invalid recipient account number. Please try again.");
                                    } else if (currentAccount == recipient) { // Check if the user is transferring to themselves
                                        System.out.println("You cannot transfer money to yourself. Please select a different recipient.\n");
                                    } else {
                                        System.out.print("Enter transfer amount: ");
                                        double transferAmount = 0;
                                        try {
                                            transferAmount = scanner.nextDouble(); // Get transfer amount from the user
                                        } catch (InputMismatchException e) {
                                            System.out.println("Invalid input. Please enter valid numeric values.");
                                            scanner.nextLine(); // Clear the input buffer to discard invalid input
                                            invalidInputCount++; // Increment the count for invalid inputs
                                            if (invalidInputCount >= MAX_INVALID_INPUTS) {
                                                System.out.println("Too many invalid inputs. Please wait for 10 seconds before trying again.\n");
                                                try {
                                                    Thread.sleep(10000); // Pause the execution for 10 seconds
                                                } catch (InterruptedException innerException) {
                                                    System.out.println("Something went wrong. Program terminating.\n");
                                                    System.exit(0);  // Terminate the program if an exception occurs
                                                }
                                                invalidInputCount = 0;  // Reset the count for invalid inputs
                                            }
                                            continue;
                                        }
                                        currentAccount.transfer(transferAmount, recipient); // Calling transfer method
                                    }
                                }
                                case 5 -> {  // Change passcode
                                    System.out.print("Enter new passcode: ");
                                    int newPass = 0;
                                    try {
                                        newPass = scanner.nextInt(); // Get new passcode from the user
                                    } catch (InputMismatchException e) {
                                        System.out.println("Invalid input. Please enter valid numeric values.");
                                        scanner.nextLine(); // Clear the input buffer to discard invalid input
                                        invalidInputCount++; // Increment the count for invalid inputs
                                        if (invalidInputCount >= MAX_INVALID_INPUTS) {
                                            System.out.println("Too many invalid inputs. Please wait for 10 seconds before trying again.\n");
                                            try {
                                                Thread.sleep(10000); // Pause the execution for 10 seconds
                                            } catch (InterruptedException innerException) {
                                                System.out.println("Something went wrong. Program terminating.\n");
                                                System.exit(0);  // Terminate the program if an exception occurs
                                            }
                                            invalidInputCount = 0;  // Reset the count for invalid inputs
                                        }
                                        continue;
                                    }

                                    currentAccount.changePasscode(newPass);
                                }
                                case 6 -> System.out.println("Logging out...\n");  // Log out
                                default -> System.out.println("Invalid option. Please try again.");
                            }
                        } while (accountMenuOption != 6);
                    }
                }
                case 3 -> System.out.println("Exiting the program...\n");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (mainMenuOption != 3);
    }
}