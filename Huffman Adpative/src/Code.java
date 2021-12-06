
public class Code {
	
	String data="";
	String code = "";
	public Code(String data, String code) {
		this.data = data;
		this.code = code;
	}
	
	@Override
	public String toString ()
	{
		return data + " " + code ;
	}
	
}
