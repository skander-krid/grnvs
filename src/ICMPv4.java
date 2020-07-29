
public class ICMPv4 {
Hilfe hilfe;
int baseOffset = 0;
public ICMPv4(String values) {
	hilfe = new Hilfe(values);
	if((getType().equals("03") || getType().equals("0b")) && hilfe.bytes.length >= 28) {
		IPv4 ip = new IPv4(values.substring(8));
	}
}

public String getType() {
	return hilfe.getBytesHex(0, 1);
}

public String getCode() {
	return hilfe.getBytesHex(1, 1);
}

public void setBaseOffset(int baseOffset) {
	this.baseOffset = baseOffset;
}

public void printAll() {
	System.out.println("Type: " + getType() + " Offset: 0x" + (Integer.toHexString(baseOffset)));
	System.out.println("Code: " + getCode() + " Offset: 0x" + (Integer.toHexString(baseOffset+1)));
}
}
