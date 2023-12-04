import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.Scanner;

public class main {

    public static void main(String[] args) {

        String connectionString = "mongodb+srv://salmaanakhtar:salmaanakhtar@cluster0.wiuk2io.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("payrollManagementSystem");
            MongoCollection<Document> collection = database.getCollection("company");

            Scanner scanner = new Scanner(System.in);

            int option;
            company company = new company();
            boolean exit = false;

            while (!exit) {
                System.out.println("Select an option:");
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("3. Exit");

                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        if (company.loginCompany()) {
                            // Successful login
                            System.out.println("Logged in successfully!");

                            System.out.println("1. Add Employee");
                            System.out.println("2. View Employees");
                            System.out.println("3. Update Employee");
                            System.out.println("4. Delete Employee");
                            System.out.println("5. Add Hours To Employee");
                            System.out.println("6. View Hours Of Employee");
                            System.out.println("7. Generate Payroll");
                            System.out.println("8. Exit");

                            option = scanner.nextInt();

                            switch (option) {
                                case 1:
                                    company.addEmployee();
                                    break;
                                case 2:
                                    company.viewEmployee();
                                    break;
                                case 3:
                                    company.updateEmployee();
                                    break;
                                case 4:
                                    company.deleteEmployee();
                                    break;
                                case 5:
                                    company.addHoursToEmployee();
                                    break;
                                case 6:
                                    company.viewHoursOfEmployee();
                                    break;
                                case 7:
                                    company.generatePayroll();
                                    break;
                                case 8:
                                    exit = true;
                                    System.out.println("Exiting...");
                                    break;
                                default:
                                    System.out.println("Invalid option. Please select again.");
                            }

                        } else {
                            System.out.println("Invalid username/password. Please try again.");
                        }
                        break;
                    case 2:
                        company.registerCompany();
                        break;
                    case 3:
                        exit = true; // Exit the loop
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid option. Please select again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menu(company company) {
        System.out.println("1. Add Employee");
        System.out.println("2. View Employee");
        System.out.println("3. Update Employee");
        System.out.println("4. Delete Employee");
        System.out.println("5. Exit");

        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();

        switch (option) {
            case 1:
                company.addEmployee();
                break;
            case 2:
                company.viewEmployee();
                break;
            case 3:
                company.updateEmployee();
                break;
            case 4:
                company.deleteEmployee();
                break;
            case 5:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid option. Please select again.");
        }
    }
}
