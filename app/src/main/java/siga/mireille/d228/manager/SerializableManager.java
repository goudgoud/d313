package siga.mireille.d228.manager;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Manage serializable object
 *
 * @author SIGA Mireille
 * @version 1
 * @since 22 /12/2016
 */
public class SerializableManager {

    /**
     * Saves a serializable object.
     *
     * @param <T>          The type of the object.
     * @param context      The application context.
     * @param objectToSave The object to save.
     * @param fileName     The name of the file.
     */
    public static <T extends Serializable> void saveSerializable(Context context, T objectToSave, String fileName) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(objectToSave);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a serializable object.
     *
     * @param <T>      The object type.
     * @param context  The application context.
     * @param fileName The filename.
     * @return the serializable object.
     */
    public static <T extends Serializable> T readSerializable(Context context, String fileName) {
        T objectToReturn = null;

        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            objectToReturn = (T) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return objectToReturn;
    }

    /**
     * Removes a specified file.
     *
     * @param context  The application context.
     * @param filename The name of the file.
     */
    public static void removeSerializable(Context context, String filename) {
        context.deleteFile(filename);
    }

}