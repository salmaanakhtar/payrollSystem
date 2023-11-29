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

    //create register function for company and connect to db again
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
}
