package Objects;

public class User {
	private int ID;
	private String Name,Password,FIN,Type;
	public User(int iD,String name, String password, String fIN, String type) {
		super();
		Name = name;
		ID = iD;
		Password = password;
		FIN = fIN;
		Type = type;
		
	}
	public User() {}	
	
	
	public void setInfos(User user)
	{
		
	}

	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getFIN() {
		return FIN;
	}
	public void setFIN(String fIN) {
		FIN = fIN;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	
	
}
