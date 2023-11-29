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
            MongoDatabase database = mongoClient.getDatabase("payrollManagementSystem"); // Replace with your database name
            MongoCollection<Document> collection = database.getCollection("company"); // Replace with your collection name


//            MongoCursor<Document> cursor = collection.find().iterator();
//            while (cursor.hasNext()) {
//                Document companyDocument = cursor.next();
//                // Print entire document
//                System.out.println("Company Details:");
//                System.out.println(companyDocument.toJson());
//                System.out.println("---------------------");
//            }

            Scanner scanner = new Scanner(System.in);

            System.out.println("Select an option:");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            int option = scanner.nextInt();

            company company = new company();

            switch (option) {
                case 1:
                    break;
                case 2:
                    company.registerCompany();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
