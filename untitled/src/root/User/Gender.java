package root.User;


public enum Gender {
	MALE("Masculino"), FEMALE("Femenino"), OTHER("Otro");
	private String gender;

	Gender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}
}

