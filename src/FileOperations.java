import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileOperations {
    public void saveTelephones(Map<String, Telephone> telephones, String fileName) throws FileOperationException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(telephones);
            System.out.println("Dosyaya kaydedilen telefonlar: " + fileName);
        } catch (IOException e) {
            throw new FileOperationException("Telefonlar dosyaya kaydedilirken hata oluştu: " + e.getMessage());
        }
    }

    public Map<String, Telephone> loadTelephones(String fileName) throws FileOperationException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Object object = ois.readObject();
            if (object instanceof Map) {
                return (Map<String, Telephone>) object;
            } else {
                throw new FileOperationException("Hatalı dosya formatı: " + fileName);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new FileOperationException("Dosyadan telefonlar yüklenirken hata oluştu: " + e.getMessage());
        }
    }

    public void savePersons(Map<String, Person> persons, String fileName) throws FileOperationException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(persons);
            System.out.println("Dosyaya kaydedilen kişiler: " + fileName);
        } catch (IOException e) {
            throw new FileOperationException("Kişileri dosyaya kaydederken hata oluştu: " + e.getMessage());
        }
    }

    public Map<String, Person> loadPersons(String fileName) throws FileOperationException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Object object = ois.readObject();
            if (object instanceof Map) {
                return (Map<String, Person>) object;
            } else {
                throw new FileOperationException("Hatalı dosya formatı: " + fileName);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new FileOperationException("Dosyadan kişiler yüklenirken hata oluştu: " + e.getMessage());
        }
    }
}