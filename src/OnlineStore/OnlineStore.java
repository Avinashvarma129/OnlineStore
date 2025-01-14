package OnlineStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Item {
    private String itemId;
    private String name;
    private double price;

    public Item(String itemId, String name, double price) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
    }

    // Getters
    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class User {
    private String username;
    private String password;
    private double balance;
    private ShoppingCart cart = new ShoppingCart(); // Initialize here for simplicity

    public User(String username, String password, double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    // Authentication
    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    // Getters and Setters
    public ShoppingCart getCart() {
        return cart;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }
}

class ShoppingCart {
    private List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public double calculateTotal() {
        return items.stream().mapToDouble(Item::getPrice).sum();
    }

    public void displayItems() {
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        items.forEach(item -> System.out.println(item.getName() + " - $" + item.getPrice()));
        System.out.println("Total: $" + calculateTotal());
    }

    public void clear() {
        items.clear();
    }
}

public class OnlineStore {
    private static List<Item> inventory = new ArrayList<>();
    private static List<User> users = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    // Method initializations...
    private static void initializeInventory() {
        inventory.add(new Item("1", "Laptop", 1000.0));
        inventory.add(new Item("2", "Smartphone", 500.0));
        inventory.add(new Item("3", "Tablet", 300.0));
        // Add 10 more items
        inventory.add(new Item("4", "Headphones", 200.0));
        inventory.add(new Item("5", "Smartwatch", 350.0));
        inventory.add(new Item("6", "Keyboard", 50.0));
        inventory.add(new Item("7", "Mouse", 30.0));
        inventory.add(new Item("8", "Printer", 150.0));
        inventory.add(new Item("9", "External Hard Drive", 120.0));
        inventory.add(new Item("10", "Wireless Earbuds", 80.0));
        inventory.add(new Item("11", "Monitor", 400.0));
        inventory.add(new Item("12", "Camera", 600.0));
        inventory.add(new Item("13", "Speaker", 100.0));
        inventory.add(new Item("14", "Gaming Console", 300.0));
        inventory.add(new Item("15", "Router", 80.0));
    }

    private static void initializeUsers() {
        users.add(new User("admin", "admin", 0.0));
        // Add 10 more users
        users.add(new User("manasa", "manasa!23", 2000.0));
        users.add(new User("mitra", "mitra@12", 1500.0));
        users.add(new User("ayusha", "ayusha#13", 1200.0));
        users.add(new User("sanjana", "sanjana$34", 1800.0));
        users.add(new User("roopa", "roopa%56", 2200.0));
        users.add(new User("veni", "veni&28", 2500.0));
        users.add(new User("user1", "password1", 2000.0));
        users.add(new User("user2", "password2", 1500.0));
        users.add(new User("user3", "password3", 1200.0));
        users.add(new User("user4", "password4", 1800.0));
    }

    private static void login() {
        System.out.println("Welcome to the Online Store!");
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    loginProcess();
                    break;
                case "2":
                    registerProcess();
                    break;
                case "3":
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void loginProcess() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        User loginUser = authenticateUser(username, password);
        if (loginUser != null) {
            System.out.println("Login successful!\nWelcome, " + loginUser.getUsername() + "!");
            if (loginUser.getUsername().equals("admin")) {
                adminMenu();
            } else {
                displayMainMenu(loginUser);
            }
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private static void registerProcess() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double balance = Double.parseDouble(scanner.nextLine());
        User newUser = new User(username, password, balance);
        users.add(newUser);
        System.out.println("Registration successful!\nWelcome, " + newUser.getUsername() + "!");
        displayMainMenu(newUser);
    }

    private static User authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.authenticate(username, password)) {
                return user;
            }
        }
        return null;
    }

    private static void displayMainMenu(User user) {
        while (true) {
            System.out.println("\n1. Browse items");
            System.out.println("2. View shopping cart");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    browseItems(user);
                    break;
                case "2":
                    viewShoppingCart(user);
                    break;
                case "3":
                    System.out.println("Logging out...");
                    return; // Return to exit the method, effectively logging the user out
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void browseItems(User user) {
        while (true) {
            System.out.println("\nAvailable items:");
            for (int i = 0; i < inventory.size(); i++) {
                Item item = inventory.get(i);
                System.out.println((i + 1) + ". " + item.getName() + " - $" + item.getPrice());
            }
            System.out.print("Enter the item number to add to cart (0 to go back): ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine()) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            if (choice == -1) {
                return; // Go back
            } else if (choice >= 0 && choice < inventory.size()) {
                Item selectedItem = inventory.get(choice);
                user.getCart().addItem(selectedItem);
                System.out.println("Item added to cart.");
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewShoppingCart(User user) {
        System.out.println("\nShopping Cart:");
        user.getCart().displayItems();
        System.out.println("Enter 'c' to checkout or any other key to go back.");
        String choice = scanner.nextLine();
        if ("c".equalsIgnoreCase(choice)) {
            checkout(user);
        }
    }

    private static void checkout(User user) {
        double total = user.getCart().calculateTotal();
        if (user.getBalance() >= total) {
            user.setBalance(user.getBalance() - total);
            System.out.println("Order placed successfully!");
            System.out.println("Remaining balance: $" + user.getBalance());
            user.getCart().clear(); // Clear the cart after successful purchase
        } else {
            System.out.println("Insufficient balance to place the order.");
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add item");
            System.out.println("2. Update item");
            System.out.println("3. Remove item");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addItem();
                    break;
                case "2":
                    updateItem();
                    break;
                case "3":
                    removeItem();
                    break;
                case "4":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void addItem() {
        // Implement logic to add an item to the inventory
        System.out.println("Add item feature is not implemented.");
    }

    private static void updateItem() {
        // Implement logic to update an existing item in the inventory
        System.out.println("Update item feature is not implemented.");
    }

    private static void removeItem() {
        // Implement logic to remove an item from the inventory
        System.out.println("Remove item feature is not implemented.");
    }

    public static void main(String[] args) {
        initializeInventory();
        initializeUsers();
        login();
    }
}
