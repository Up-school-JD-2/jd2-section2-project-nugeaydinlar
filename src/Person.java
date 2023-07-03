import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Person implements Serializable {

	private String name;
	private String lastName;
	private String phoneNumber;
	private String email;
	private List<Telephone> telephones;

	public Person(String name, String lastName, String phoneNumber, String email, List<Telephone> telephones) {
		this.name = name;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.telephones = telephones;
	}

	public Person(String name2, String phoneNumber2) {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void showUserTelephones() {
		System.out.println("Kullanıcının Telefonları:");
		for (Telephone telephone : telephones) {
			System.out.println("------------------------");
			telephone.showTelephoneInfo();
			System.out.println("------------------------");
		}
	}

	public void addPhoneNumber(String newPhoneNumber) {

	}

	public boolean removePhoneNumber(String phoneNumberToRemove) {

		return false;
	}
}
