import java.io.*;
import java.util.*;

public class TelephoneManagementApp {
	private Map<String, Telephone> telephones;
	private List<Application> applications;
	private Map<String, Person> persons;
	private int totalStorage;
	private int availableStorage;
	private int userStorageLimit;
	private boolean storageChanged;

	public TelephoneManagementApp() {
		this.telephones = new HashMap<>();
		this.applications = new ArrayList<>();
		this.persons = new HashMap<>();
		this.totalStorage = 256;
		this.availableStorage = 256;
		this.userStorageLimit = 256;
		this.storageChanged = false;
	}

	public void showMenu() {
		Scanner scanner = new Scanner(System.in);
		int choice;

		do {
			System.out.println("--------- Akıllı Telefon Yönetim Uygulaması ---------");
			System.out.println("1. Uygulama Ekle");
			System.out.println("2. Uygulama Sil");
			System.out.println("3. Uygulama Güncelle");
			System.out.println("4. Kişi Ekle");
			System.out.println("5. Kişi Güncelle");
			System.out.println("6. Kişi Ara");
			System.out.println("7. Kişi Düzenle");
			System.out.println("8. Depolamayı Kontrol Et");
			System.out.println("9. Verileri Yedekle");
			System.out.println("10. Verileri Geri Yükle");
			System.out.println("11. Çıkış Yap");
			System.out.print("Seçiminizi girin: ");

			choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				addApplication();
				break;
			case 2:
				try {
					removeApplication();
				} catch (ApplicationNotFoundException e1) {
					e1.printStackTrace();
				}
				break;
			case 3:
				try {
					updateApplication();
				} catch (ApplicationNotFoundException e) {
					e.printStackTrace();
				}
				break;
			case 4:
				addPerson();
				break;
			case 5:
				try {
					updatePerson();
				} catch (PersonNotFoundException e) {
					e.printStackTrace();
				}
				break;
			case 6:
				callPerson();
				break;
			case 7:
				editPerson();
				break;
			case 8:
				displayStorageInfo();
				break;
			case 9:
				System.out.print("Yedeklenecek dosya adını girin(.txt/.dat ekleyerek): ");
				String backupFilename = scanner.nextLine();
				try {
					backupData(backupFilename);
				} catch (FileOperationException e) {
					e.printStackTrace();
				}
				break;
			case 10:
				System.out.print("Geri yüklenecek dosya adını girin(.txt/.dat ekleyerek): ");
				String restoreFilename = scanner.nextLine();
				try {
					restoreData(restoreFilename);
				} catch (FileOperationException e) {
					e.printStackTrace();
				}
				break;
			case 11:
				System.out.println("Uygulamadan çıkış yapılıyor...");
				break;
			default:
				System.out.println("Hatalı seçim! Lütfen tekrar deneyin.");
				break;
			}

			if (storageChanged) {
				displayStorageInfo();
				storageChanged = false;
			}

			System.out.println();
		} while (choice != 11);
		scanner.close();
	}

	public void addTelephone(String brand, String model, String serialNumber, int storageCapacity,
			String operatingSystem) {
		Telephone telephone = new Telephone(brand, model, serialNumber, storageCapacity, operatingSystem);
		telephones.put(serialNumber, telephone);
		totalStorage += storageCapacity;
		availableStorage += storageCapacity;
	}

	public void addApplication() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Uygulama adını girin: ");
		String name = scanner.nextLine();
		System.out.print("Uygulama versiyonunu girin: ");
		String version = scanner.nextLine();
		System.out.print("Uygulama boyutunu girin: ");
		int size = scanner.nextInt();
		scanner.nextLine(); // Boş satırı oku

		if (size > availableStorage) {
			System.out.println("Yeterli depolama alanı yok!");
			return;
		}

		Application application = new Application(name, version, size);
		applications.add(application);
		availableStorage -= size;
		storageChanged = true;

		System.out.println("Uygulama başarıyla eklendi");
	}

	public void removeApplication() throws ApplicationNotFoundException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Silmek istediğiniz uygulamanın adını girin: ");
		String name = scanner.nextLine();

		Application applicationToRemove = null;
		for (Application application : applications) {
			if (application.getName().equals(name)) {
				applicationToRemove = application;
				break;
			}
		}

		if (applicationToRemove != null) {
			applications.remove(applicationToRemove);
			availableStorage += applicationToRemove.getSize();
			storageChanged = true;

			System.out.println("Uygulama başarıyla silindi");
		} else {
			throw new ApplicationNotFoundException("Uygulama bulunamadı: " + name);
		}
	}

	public void updateApplication() throws ApplicationNotFoundException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Güncellemek istediğiniz uygulamanın adını girin: ");
		String name = scanner.nextLine();

		Application applicationToUpdate = null;
		for (Application application : applications) {
			if (application.getName().equals(name)) {
				applicationToUpdate = application;
				break;
			}
		}

		if (applicationToUpdate != null) {
			System.out.print("Yeni uygulama sürümünü girin: ");
			String newVersion = scanner.nextLine();
			System.out.print("Yeni uygulama boyutunu girin: ");
			int newSize = scanner.nextInt();

			int sizeDifference = newSize - applicationToUpdate.getSize();

			if (sizeDifference > availableStorage) {
				System.out.println("Yeterli depolama alanı yok!");
				return;
			}

			applicationToUpdate.setVersion(newVersion);
			applicationToUpdate.setSize(newSize);
			availableStorage -= sizeDifference;
			storageChanged = true;

			System.out.println("Uygulama başarıyla güncellendi");
		} else {
			throw new ApplicationNotFoundException("Uygulama bulunamadı: " + name);
		}
	}

	public void addPerson() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Kişi adı girin: ");
		String name = scanner.nextLine();
		System.out.print("Kişi telefon numarası girin: ");
		String phoneNumber = scanner.nextLine();

		Person person = new Person(name, phoneNumber);
		persons.put(phoneNumber, person);
	}

	public void removePerson(String phoneNumber) throws PersonNotFoundException {
		if (persons.containsKey(phoneNumber)) {
			persons.remove(phoneNumber);
		} else {
			throw new PersonNotFoundException("Kişi bulunamadı " + phoneNumber);
		}
	}

	public void updatePerson() throws PersonNotFoundException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Güncellenecek kişinin telefon numarasını girin: ");
		String phoneNumber = scanner.nextLine();

		if (persons.containsKey(phoneNumber)) {
			Person person = persons.get(phoneNumber);

			System.out.print("Yeni telefon numarası girin: ");
			String newPhoneNumber = scanner.nextLine();

			if (phoneNumber.equals(newPhoneNumber)) {
				System.out.println("Girilen telefon numarası zaten güncel telefon numarasıdır.");
			} else {
				person.setPhoneNumber(newPhoneNumber);
				persons.remove(phoneNumber);
				persons.put(newPhoneNumber, person);
			}
		} else {
			throw new PersonNotFoundException("Kişi bulunamadı: " + phoneNumber);
		}
	}

	public void callPerson() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Aranacak telefon numarasını girin: ");
		String phoneNumber = scanner.nextLine();

		if (persons.containsKey(phoneNumber)) {
			Person person = persons.get(phoneNumber);
			System.out.println("Aranıyor...");
		} else {
			System.out.println("Verilen telefon numarasına sahip kişi bulunamadı.");
		}
	}

	private void editPerson() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Düzenlenecek kişinin telefon numarasını girin: ");
		String phoneNumber = scanner.nextLine();

		if (persons.containsKey(phoneNumber)) {
			Person person = persons.get(phoneNumber);

			System.out.println("1. İsim");
			System.out.println("2. Soyisim");
			System.out.println("3. Telefon Numarası");
			System.out.println("4. Geri dön");

			System.out.print("Düzenlemek istediğiniz özelliği seçin: ");
			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				System.out.print("Yeni isim: ");
				String newName = scanner.nextLine();
				person.setName(newName);
				System.out.println("İsim başarıyla güncellendi.");
				break;
			case 2:
				System.out.print("Yeni soyisim: ");
				String newLastName = scanner.nextLine();
				person.setLastName(newLastName);
				System.out.println("Soyisim başarıyla güncellendi.");
				break;
			case 3:
				System.out.print("Yeni telefon numarası: ");
				String newPhoneNumber = scanner.nextLine();
				if (!persons.containsKey(newPhoneNumber)) {
					person.setPhoneNumber(newPhoneNumber);
					persons.put(newPhoneNumber, person);
					persons.remove(phoneNumber);
					System.out.println("Telefon numarası başarıyla güncellendi.");
				} else {
					System.out.println("Bu telefon numarası zaten mevcut. Lütfen farklı bir numara girin.");
				}
				break;
			case 4:
				System.out.println("Düzenleme işleminden vazgeçildi.");
				break;
			default:
				System.out.println("Hatalı seçim! Lütfen tekrar deneyin.");
				break;
			}
		} else {
			System.out.println("Belirtilen telefon numarasına sahip bir kişi bulunamadı.");
		}
	}

	public void displayStorageInfo() {
		System.out.println("Toplam Depolama Kapasitesi: " + totalStorage + " GB");
		System.out.println("Kullanılabilir depolama alanı: " + availableStorage + " GB");
		storageChanged = false;
	}

	public void backupData(String filename) throws FileOperationException {
		FileOperations fileOperations = new FileOperations();

		// Dosya tipini kontrol et
		String fileExtension = getFileExtension(filename);
		boolean saved = false;

		if (fileExtension.equalsIgnoreCase("txt")) {
			try {
				fileOperations.savePersons(persons, filename);
				saved = true;
			} catch (FileOperationException e) {
				e.printStackTrace();
			}
		} else if (fileExtension.equalsIgnoreCase("dat")) {
			try {
				fileOperations.saveTelephones(telephones, filename);
				saved = true;
			} catch (FileOperationException e) {
				e.printStackTrace();
			}
		} else {
			throw new FileOperationException("Desteklenmeyen dosya uzantısı: " + fileExtension);
		}

		if (saved) {
			System.out.println("Veriler başarıyla yedeklendi.");
		}
	}

	// Dosya uzantısını almak için yardımcı bir metod
	private String getFileExtension(String filename) {
		int dotIndex = filename.lastIndexOf(".");
		if (dotIndex == -1 || dotIndex == filename.length() - 1) {
			return "";
		}
		return filename.substring(dotIndex + 1);
	}

	public void restoreData(String filename) throws FileOperationException {
		FileOperations fileOperations = new FileOperations();

		// Dosya tipini kontrol et
		String fileExtension = getFileExtension(filename);
		if (fileExtension.equalsIgnoreCase("txt")) {
			Map<String, Person> restoredPersons = fileOperations.loadPersons(filename);
			System.out.println("Dosyadan geri yüklenen kişiler: " + filename);
			// ...
		} else if (fileExtension.equalsIgnoreCase("dat")) {
			Map<String, Telephone> restoredTelephones = fileOperations.loadTelephones(filename);
			System.out.println("Dosyadan geri yüklenen telefonlar: " + filename);
			// ...
		} else {
			throw new FileOperationException("Desteklenmeyen dosya uzantısı: " + fileExtension);
		}
	}

	// Dosya uzantısını almak için yardımcı bir metod
	private String getFileExtension2(String filename) {
		int dotIndex = filename.lastIndexOf(".");
		if (dotIndex == -1 || dotIndex == filename.length() - 1) {
			return "";
		}
		return filename.substring(dotIndex + 1);
	}

	// Getter methodları
	public Map<String, Telephone> getTelephones() {
		return telephones;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public Map<String, Person> getPersons() {
		return persons;
	}

	public int getTotalStorage() {
		return totalStorage;
	}

	public int getAvailableStorage() {
		return availableStorage;
	}
}