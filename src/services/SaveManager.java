package services;
import models.Farm;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class SaveManager {

    private static final String SAVE_FILE = "save.dat";

    public static void saveFarm(Farm farm) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(farm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Farm loadFarm() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            Farm f = (Farm) ois.readObject();
            return f;
        } catch (Exception e) {
            return new Farm();
        }
    }
    
}
