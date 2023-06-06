/*
 * @file: 
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief
 */

/**
 *
 * @author Luis Oliveira <https://github.com/LuisCarlosOliveira>
 */
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import ma02_resources.project.Task;

public class Main {

    public static void main(String[] args) {
        String jsonFilePath = "caminho/do/arquivo.json";

        try {
            String jsonString = readJsonFile(jsonFilePath);
            JsonParser jsonParser = new JsonParser();
            JsonObject json = jsonParser.parse(jsonString).getAsJsonObject();

            int numberOfFacilitors = 0;
            if (json.has("number_of_facilitors")) {
                numberOfFacilitors = json.get("number_of_facilitors").getAsInt();
            }

            int numberOfStudents = 0;
            if (json.has("number_of_students")) {
                numberOfStudents = json.get("number_of_students").getAsInt();
            }

            int numberOfPartners = 0;
            if (json.has("number_of_partners")) {
                numberOfPartners = json.get("number_of_partners").getAsInt();
            }

            JsonArray tasksJsonArray = json.getAsJsonArray("tasks");

            Task[] tasks = new Task[tasksJsonArray.size()];
            for (int i = 0; i < tasksJsonArray.size(); i++) {
                JsonObject taskJson = tasksJsonArray.get(i).getAsJsonObject();

                String title = "";
                if (taskJson.has("title")) {
                    title = taskJson.get("title").getAsString();
                }

                String description = "";
                if (taskJson.has("description")) {
                    description = taskJson.get("description").getAsString();
                }

                int startAt = 0;
                if (taskJson.has("start_at")) {
                    startAt = taskJson.get("start_at").getAsInt();
                }

                int duration = 0;
                if (taskJson.has("duration")) {
                    duration = taskJson.get("duration").getAsInt();
                }

                Task task = new Task(title, description, startAt, duration) {};
                tasks[i] = task;
            }

            MyProject myProject = new MyProject(numberOfFacilitors, numberOfStudents, numberOfPartners, tasks);
            // Faça o que for necessário com o objeto MyProject criado

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo JSON: " + e.getMessage());
        }
    }

    private static String readJsonFile(String filePath) throws IOException {
        StringBuilder jsonString = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String line;
        while ((line = reader.readLine()) != null) {
            jsonString.append(line);
        }

        reader.close();
        return jsonString.toString();
    }
}

