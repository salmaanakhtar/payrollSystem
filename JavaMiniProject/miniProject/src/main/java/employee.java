import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
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

            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter Employee ID: ");
            employeeID = scanner.nextInt();
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

}
