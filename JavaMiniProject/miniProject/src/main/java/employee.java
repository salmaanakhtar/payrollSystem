import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import java.util.Scanner;

public class employee {

    public int employeeID;
    public Long companyID;
    public String employeeFirstName;
    public String employeeLastName;
    public String salaryType;
    public int salaryAmount;
    public int hoursWorked;

    public void addEmployee(Long loggedInCompanyID) {
        String connectionString = "mongodb+srv://salmaanakhtar:salmaanakhtar@cluster0.wiuk2io.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("payrollManagementSystem"); // Replace with your database name
            MongoCollection<Document> collection = database.getCollection("employee"); // Replace with your collection name

            Document maxEmployeeId = collection.find()
                    .sort(Sorts.descending("EmployeeID"))
                    .limit(1)
                    .first();

            int nextEmployeeID = 1; // Default to 1 if no documents are found
            if (maxEmployeeId != null) {
                nextEmployeeID = maxEmployeeId.getInteger("EmployeeID") + 1;
            }

            Scanner scanner = new Scanner(System.in);
            employeeID = nextEmployeeID;
            companyID = loggedInCompanyID;
            System.out.println("Enter Employee First Name: ");
            employeeFirstName = scanner.next();
            System.out.println("Enter Employee Last Name: ");
            employeeLastName = scanner.next();
            System.out.println("Enter Salary Type: ");
            salaryType = scanner.next();
            System.out.println("Enter Salary Amount: ");
            salaryAmount = scanner.nextInt();
            System.out.println("Enter Hours Worked: ");
            hoursWorked = scanner.nextInt();

            Document employeeDocument = new Document("EmployeeID", employeeID)
                    .append("CompanyID", companyID)
                    .append("EmployeeFirstName", employeeFirstName)
                    .append("EmployeeLastName", employeeLastName)
                    .append("SalaryType", salaryType)
                    .append("SalaryAmount", salaryAmount)
                    .append("HoursWorked", hoursWorked);

            collection.insertOne(employeeDocument);
            System.out.println("Employee added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewEmployee(Long loggedInCompanyID) {

        String connectionString = "mongodb+srv://salmaanakhtar:salmaanakhtar@cluster0.wiuk2io.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("payrollManagementSystem"); // Replace with your database name
            MongoCollection<Document> collection = database.getCollection("employee"); // Replace with your collection name



            MongoCursor<Document> cursor = collection.find().iterator();

            while (cursor.hasNext()) {
                Document employeeDocument = cursor.next();
                if (employeeDocument.getLong("CompanyID") == loggedInCompanyID) {
                    System.out.println("Employee ID: " + employeeDocument.getInteger("EmployeeID"));
                    System.out.println("Employee First Name: " + employeeDocument.getString("EmployeeFirstName"));
                    System.out.println("Employee Last Name: " + employeeDocument.getString("EmployeeLastName"));
                    System.out.println("Salary Type: " + employeeDocument.getString("SalaryType"));
                    System.out.println("Salary Amount: " + employeeDocument.getInteger("SalaryAmount"));
                    System.out.println("Hours Worked: " + employeeDocument.getInteger("HoursWorked"));
                    System.out.println();
                }
            }
        }
    }

    public void updateEmployee(Long loggedInCompanyID) {

        String connectionString = "mongodb+srv://salmaanakhtar:salmaanakhtar@cluster0.wiuk2io.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("payrollManagementSystem"); // Replace with your database name
            MongoCollection<Document> collection = database.getCollection("employee"); // Replace with your collection name
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Employee ID: ");
            employeeID = scanner.nextInt();
            System.out.println("Enter Employee First Name: ");
            employeeFirstName = scanner.next();
            System.out.println("Enter Employee Last Name: ");
            employeeLastName = scanner.next();
            System.out.println("Enter Salary Type: ");
            salaryType = scanner.next();
            System.out.println("Enter Salary Amount: ");
            salaryAmount = scanner.nextInt();
            System.out.println("Enter Hours Worked: ");
            hoursWorked = scanner.nextInt();

            Document employeeDocument = new Document("EmployeeID", employeeID)
                    .append("CompanyID", loggedInCompanyID)
                    .append("EmployeeFirstName", employeeFirstName)
                    .append("EmployeeLastName", employeeLastName)
                    .append("SalaryType", salaryType)
                    .append("SalaryAmount", salaryAmount)
                    .append("HoursWorked", hoursWorked);

            collection.updateOne(employeeDocument, new Document("$set", employeeDocument));
            System.out.println("Employee updated successfully!");

        }
    }

    public void deleteEmployee(Long loggedInCompanyID) {
        String connectionString = "mongodb+srv://salmaanakhtar:salmaanakhtar@cluster0.wiuk2io.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("payrollManagementSystem"); // Replace with your database name
            MongoCollection<Document> collection = database.getCollection("employee"); // Replace with your collection name

            //ask for emplyee id and delete emplyee id where employee id = employee id
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Employee ID: ");
            employeeID = scanner.nextInt();

               Document employeeDocument = new Document("EmployeeID", employeeID)
                        .append("CompanyID", loggedInCompanyID);

                collection.deleteOne(employeeDocument);
                System.out.println("Employee deleted successfully!");


        }
    }

    public void addHoursToEmployee(Long loggedInCompanyID) {

        String connectionString = "mongodb+srv://salmaanakhtar:salmaanakhtar@cluster0.wiuk2io.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("payrollManagementSystem"); // Replace with your database name
            MongoCollection<Document> collection = database.getCollection("employee"); // Replace with your collection name

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Employee ID: ");
            int employeeID = scanner.nextInt();

            Document query = new Document("EmployeeID", employeeID)
                    .append("CompanyID", loggedInCompanyID);

            Document employeeDocument = collection.find(query).first();

            if (employeeDocument != null) {
                System.out.println("Enter Hours Worked: ");
                int hoursWorked = scanner.nextInt();

                int currentHours = employeeDocument.getInteger("HoursWorked", 0); // Get current hours worked or default to 0 if not found
                int updatedHours = currentHours + hoursWorked;

                // Update the document with the new hours worked
                collection.updateOne(query, new Document("$set", new Document("HoursWorked", updatedHours)));

                System.out.println("Hours updated successfully!");
            } else {
                System.out.println("Employee not found.");
            }
        }
    }
}


