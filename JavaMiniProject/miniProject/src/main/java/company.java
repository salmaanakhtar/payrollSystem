import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import java.util.Scanner;

import java.util.Scanner;

public class company {

    public int CompanyID;
    public String CompanyName;
    public String CompanyUsername;
    public String CompanyPassword;

    public Long loggedInCompanyID;


    public void registerCompany() {

        String connectionString = "mongodb+srv://salmaanakhtar:salmaanakhtar@cluster0.wiuk2io.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("payrollManagementSystem"); // Replace with your database name
            MongoCollection<Document> collection = database.getCollection("company"); // Replace with your collection name

            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter Company ID: ");
            CompanyID = scanner.nextInt();
            System.out.println("Enter Company Name: ");
            CompanyName = scanner.next();
            System.out.println("Enter Company Username: ");
            CompanyUsername = scanner.next();
            System.out.println("Enter Company Password: ");
            CompanyPassword = scanner.next();

            Document companyDocument = new Document("CompanyID", CompanyID)
                    .append("CompanyName", CompanyName)
                    .append("CompanyUsername", CompanyUsername)
                    .append("CompanyPassword", CompanyPassword);

            collection.insertOne(companyDocument);
            System.out.println("Company added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean loginCompany() {
        String connectionString = "mongodb+srv://salmaanakhtar:salmaanakhtar@cluster0.wiuk2io.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("payrollManagementSystem");
            MongoCollection<Document> collection = database.getCollection("company");

            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter Company Username: ");
            CompanyUsername = scanner.next();
            System.out.println("Enter Company Password: ");
            CompanyPassword = scanner.next();

            Document companyDocument = new Document("CompanyUsername", CompanyUsername)
                    .append("CompanyPassword", CompanyPassword);

            Document foundCompany = collection.find(companyDocument).first();
            if (foundCompany != null) {
                loggedInCompanyID = foundCompany.getLong("CompanyID");
                return true; // Successful login
            } else {
                return false; // Failed login
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Failed login due to exception
        }
    }

    public void addEmployee() {

            employee employee = new employee();
            main main = new main();

            employee.addEmployee(loggedInCompanyID);
            main.menu(this);


    }

    public void viewEmployee() {

        employee employee = new employee();
        main main = new main();

        employee.viewEmployee(loggedInCompanyID);
        main.menu(this);

    }

    public void updateEmployee() {

        employee employee = new employee();
        main main = new main();

        employee.updateEmployee(loggedInCompanyID);
        main.menu(this);

    }

    public void deleteEmployee() {

        employee employee = new employee();
        main main = new main();

        employee.deleteEmployee(loggedInCompanyID);
        main.menu(this);

    }

    public void addHoursToEmployee() {

        employee employee = new employee();
        main main = new main();

        employee.addHoursToEmployee(loggedInCompanyID);
        main.menu(this);

    }

    public void viewHoursOfEmployee() {

        employee employee = new employee();
        main main = new main();

        employee.viewHoursOfEmployee(loggedInCompanyID);
        main.menu(this);

    }

    public void generatePayroll() {

        employee employee = new employee();
        main main = new main();

//        employee.generatePayroll(loggedInCompanyID);
        main.menu(this);

    }

    public void menu(company company) {
        System.out.println("1. Add Employee");
        System.out.println("2. View Employee");
        System.out.println("3. Update Employee");
        System.out.println("4. Delete Employee");
        System.out.println("5. Add Hours to Employee");
        System.out.println("6. View Hours of Employee");
        System.out.println("7. Generate Payroll");
        System.out.println("8. Exit");

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
                company.addHoursToEmployee();
                break;
            case 6:
                company.viewHoursOfEmployee();
                break;
            case 7:
                company.generatePayroll();
                break;
            case 8:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid option. Please select again.");
        }
    }
}
