package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.github.javafaker.Faker;

public class GenerateData
{
    public static String getUserRegistrationPayload(String password) throws IOException {
        String path = "./data/api/userRegistration.json";
        String jsonData = Files.readString(Paths.get(path));
        Faker faker = new Faker();
        String finalPassword = (password != null) ? password:"helloWorld01@";
        return jsonData
                .replace("PlaceHolder_Fname", faker.name().firstName())
                .replace("PlaceHolder_Lname", faker.name().lastName())
                .replace("PlaceHolder_Email", faker.internet().emailAddress())
                .replace("PlaceHolder_Password", finalPassword);
    }
}
