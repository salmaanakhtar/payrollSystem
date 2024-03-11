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

            // Find the maximum Employee ID value currently in the database
            Document maxEmployeeId = collection.find()
                    .sort(Sorts.descending("EmployeeID"))
                    .limit(1)
                    .first();

            //Adds 1 to the maxEmployeeId to get the next Employee ID
            int nextEmployeeID = 1;
            if (maxEmployeeId != null) {
                nextEmployeeID = maxEmployeeId.getInteger("EmployeeID") + 1;
            }

            //takes input from user
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

            //creates a new document with the input from the user
            Document employeeDocument = new Document("EmployeeID", employeeID)
                    .append("CompanyID", companyID)
                    .append("EmployeeFirstName", employeeFirstName)
                    .append("EmployeeLastName", employeeLastName)
                    .append("SalaryType", salaryType)
                    .append("SalaryAmount", salaryAmount)
                    .append("HoursWorked", hoursWorked);

            //inserts the document into the database
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

            //displays all employees with the same companyID as the logged in company
            while (cursor.hasNext()) {
                Document employeeDocument = cursor.next();
                if (employeeDocument.getLong("CompanyID") == loggedInCompanyID) {
                    displayEmployee(employeeDocument);
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

            // Enter the Employee ID to update
            System.out.println("Enter Employee ID: ");
            int employeeID = scanner.nextInt();

            // Find the employee details based on Employee ID
            Document query = new Document("EmployeeID", employeeID)
                    .append("CompanyID", loggedInCompanyID);
            Document employeeDocument = collection.find(query).first();

            // If the employee is found, display the details and ask for new details
            if (employeeDocument != null) {

                System.out.println("Existing Details:");
                displayEmployee(employeeDocument);


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

            // Enter the Employee ID to delete
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Employee ID: ");
            employeeID = scanner.nextInt();


                // Find the employee details based on Employee ID
               Document employeeDocument = new Document("EmployeeID", employeeID)
                        .append("CompanyID", loggedInCompanyID);

               // Delete the employee details
                collection.deleteOne(employeeDocument);
                System.out.println("Employee deleted successfully!");


        }
    }

    public void addHoursToEmployee(Long loggedInCompanyID) {

        String connectionString = "mongodb+srv://salmaanakhtar:salmaanakhtar@cluster0.wiuk2io.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("payrollManagementSystem");
            MongoCollection<Document> collection = database.getCollection("employee");

            // Enter the Employee ID to update
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Employee ID: ");
            int employeeID = scanner.nextInt();

            // Find the employee details based on Employee ID
            Document query = new Document("EmployeeID", employeeID)
                    .append("CompanyID", loggedInCompanyID);

            Document employeeDocument = collection.find(query).first();

            // If the employee is found, get hours worked and add to current amount
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

        String connectionString = "mongodb+srv://salmaanakhtar:salmaanakhtar@cluster0.wiuk2io.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("payrollManagementSystem");
            MongoCollection<Document> collection = database.getCollection("employee");

            // Enter the Employee ID to get details
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Employee ID: ");

            int employeeID = scanner.nextInt();

            Document query = new Document("EmployeeID", employeeID)
                    .append("CompanyID", loggedInCompanyID);

            Document employeeDocument = collection.find(query).first();

            // If the employee is found, display the hours worked
            if (employeeDocument != null) {
                System.out.println("Hours Worked: " + employeeDocument.getInteger("HoursWorked"));
            } else {
                System.out.println("Employee not found.");
            }



        }
    }

    public void generatePayroll(Long loggedInCompanyID) {

        String connectionString = "mongodb+srv://salmaanakhtar:salmaanakhtar@cluster0.wiuk2io.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("payrollManagementSystem");
            MongoCollection<Document> collection = database.getCollection("employee");

            //display number of employees and number of hours worked and total salary
            MongoCursor<Document> cursor = collection.find().iterator();
            int totalSalary = 0;
            int totalHours = 0;
            int totalEmployees = 0;

            //Iterates through all employees and adds the hours and salary to the total
            while (cursor.hasNext()) {
                Document employeeDocument = cursor.next();
                if (employeeDocument.getLong("CompanyID") == loggedInCompanyID) {
                    totalEmployees++;
                    totalHours += employeeDocument.getInteger("HoursWorked");
                    totalSalary += employeeDocument.getInteger("SalaryAmount");
                }
            }

            //displays the total number of employees, hours and salary
            System.out.println("Total Employees: " + totalEmployees);
            System.out.println("Total Hours: " + totalHours);
            System.out.println("Total Salary: " + totalSalary);


        }
    }
}


