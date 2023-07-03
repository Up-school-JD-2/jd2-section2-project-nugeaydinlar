
public class Application {
	private String name;
	private String version;
	private int size;

	public Application(String name, String version, int size) {
		this.name = name;
		this.version = version;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void showApplicationInfo() {
		System.out.println("Uygulama adi: " + name);
		System.out.println("Uygulama versiyonu: " + version);
		System.out.println("Uygulama boyutu: " + size);
	}

}
