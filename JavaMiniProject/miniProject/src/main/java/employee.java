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

    private void displayEmployee(Document employeeDocument) {
        System.out.println("Employee ID: " + employeeDocument.getInteger("EmployeeID"));
        System.out.println("Employee First Name: " + employeeDocument.getString("EmployeeFirstName"));
        System.out.println("Employee Last Name: " + employeeDocument.getString("EmployeeLastName"));
        System.out.println("Salary Type: " + employeeDocument.getString("SalaryType"));
        System.out.println("Salary Amount: " + employeeDocument.getInteger("SalaryAmount"));
        System.out.println("Hours Worked: " + employeeDocument.getInteger("HoursWorked"));
        System.out.println();
    }

    public void addEmployee(Long loggedInCompanyID) {
        String connectionString = "mongodb+srv://salmaanakhtar:salmaanakhtar@cluster0.wiuk2io.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("payrollManagementSystem");
            MongoCollection<Document> collection = database.getCollection("employee");

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
                    displayEmployee(employeeDocument);
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
            MongoDatabase database = mongoClient.getDatabase("payrollManagementSystem");
            MongoCollection<Document> collection = database.getCollection("employee");
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter Employee ID: ");
            int employeeID = scanner.nextInt();

            // Find the employee details based on Employee ID
            Document query = new Document("EmployeeID", employeeID)
                    .append("CompanyID", loggedInCompanyID);
            Document employeeDocument = collection.find(query).first();

            if (employeeDocument != null) {
                // Display existing details
                System.out.println("Existing Details:");
                System.out.println("Employee ID: " + employeeDocument.getInteger("EmployeeID"));
                System.out.println("Employee First Name: " + employeeDocument.getString("EmployeeFirstName"));
                System.out.println("Employee Last Name: " + employeeDocument.getString("EmployeeLastName"));
                System.out.println("Salary Type: " + employeeDocument.getString("SalaryType"));
                System.out.println("Salary Amount: " + employeeDocument.getInteger("SalaryAmount"));
                System.out.println("Hours Worked: " + employeeDocument.getInteger("HoursWorked"));

                // Allow user to edit details
                System.out.println("Enter new Employee First Name (or press Enter to keep existing): ");
                String newFirstName = scanner.nextLine().trim();
                if (!newFirstName.isEmpty()) {
                    employeeDocument.put("EmployeeFirstName", newFirstName);
                }

                System.out.println("Enter new Employee Last Name (or press Enter to keep existing): ");
                String newLastName = scanner.nextLine().trim();
                if (!newLastName.isEmpty()) {
                    employeeDocument.put("EmployeeLastName", newLastName);
                }

                System.out.println("Enter new Salary Type (or press Enter to keep existing): ");
                String newSalaryType = scanner.nextLine().trim();
                if (!newSalaryType.isEmpty()) {
                    employeeDocument.put("SalaryType", newSalaryType);
                }

                System.out.println("Enter new Salary Amount (or enter 0 to keep existing): ");
                int newSalaryAmount = scanner.nextInt();
                if (newSalaryAmount != 0) {
                    employeeDocument.put("SalaryAmount", newSalaryAmount);
                }

                System.out.println("Enter new Hours Worked (or enter 0 to keep existing): ");
                int newHoursWorked = scanner.nextInt();
                if (newHoursWorked != 0) {
                    employeeDocument.put("HoursWorked", newHoursWorked);
                }

                // Update the employee details
                collection.replaceOne(query, employeeDocument);
                System.out.println("Employee updated successfully!");
            } else {
                System.out.println("Employee not found!");
            }
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
            MongoDatabase database = mongoClient.getDatabase("payrollManagementSystem");
            MongoCollection<Document> collection = database.getCollection("employee");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Employee ID: ");
            int employeeID = scanner.nextInt();

            Document query = new Document("EmployeeID", employeeID)
                    .append("CompanyID", loggedInCompanyID);

            Document employeeDocument = collection.find(query).first();

            if (employeeDocument != null) {
                System.out.println("Enter Hours Worked: ");
                int hoursWorked = scanner.nextInt();

                int currentHours = employeeDocument.getInteger("HoursWorked", 0);
                int updatedHours = currentHours + hoursWorked;

                collection.updateOne(query, new Document("$set", new Document("HoursWorked", updatedHours)));

                System.out.println("Hours updated successfully!");
            } else {
                System.out.println("Employee not found.");
            }
        }
    }

    public void viewHoursOfEmployee(Long loggedInCompanyID) {

    }
}


