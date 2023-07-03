import java.util.ArrayList;
import java.util.List;

public class Telephone {

	private String brand;
	private String model;
	private String serialNumber;
	private int storageCapacity;
	private String operatingSystem;
	private List<Application> applications;
	

	public Telephone(String brand, String model, String serialNumber, int storageCapacity, String operatingSystem) {
		this.brand = brand;
		this.model = model;
		this.serialNumber = serialNumber;
		this.storageCapacity = storageCapacity;
		this.operatingSystem = operatingSystem;
		this.applications = new ArrayList<>();
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public int getStorageCapacity() {
		return storageCapacity;
	}

	public void setStorageCapacity(int storageCapacity) {
		this.storageCapacity = storageCapacity;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public void showTelephoneInfo() {
		System.out.println("Marka: " + brand);
		System.out.println("Model: " + model);
		System.out.println("Seri numarası: " + serialNumber);
		System.out.println("Depolama alanı: " + storageCapacity);
		System.out.println("İşletim sistemi: " + operatingSystem);
	}

	public String getPhoneNumber() {
		return null;
	}

}