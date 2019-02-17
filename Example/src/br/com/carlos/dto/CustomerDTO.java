package br.com.carlos.dto;

public class CustomerDTO {

	private String otherName;
	private String otherEmail;

	public String getOtherName() {
		return otherName;
	}
	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
	public String getOtherEmail() {
		return otherEmail;
	}
	public void setOtherEmail(String otherEmail) {
		this.otherEmail = otherEmail;
	}
	@Override
	public String toString() {
		return "CustomerDTO [otherName=" + otherName + ", otherEmail=" + otherEmail + "]";
	}
}
