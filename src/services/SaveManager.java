package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Farm;
import java.io.*;

public class SaveManager {
    private static final String SAVE_PATH = "save_ferme.json";
    
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public static void saveFarm(Farm farm) {
        try (Writer writer = new FileWriter(SAVE_PATH)) {
            gson.toJson(farm, writer);
            System.out.println("✅ Sauvegarde effectuée dans " + SAVE_PATH);
        } catch (IOException e) {
            System.err.println("❌ Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    public static Farm loadFarm() {
        File file = new File(SAVE_PATH);
        if (!file.exists()) {
            System.out.println("ℹ️ Aucune sauvegarde trouvée. Création d'une nouvelle ferme.");
            return new Farm();
        }

        try (Reader reader = new FileReader(SAVE_PATH)) {
            Farm loaded = gson.fromJson(reader, Farm.class);
            return (loaded != null) ? loaded : new Farm();
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du chargement : " + e.getMessage());
            return new Farm();
        }
    }
}