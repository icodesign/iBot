package info.leewong.ibot;

public class DetailEntity {

	public DetailEntity(String text, int direction)
	{
		super();
		this.text = text;
		this.diretion=direction;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getDiretion() {
		return diretion;
	}
	public void setDiretion(int diretion) {
		this.diretion = diretion;
	}
	public String text;
	public int diretion;
}
