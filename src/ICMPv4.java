
public class ICMPv4 {
Hilfe hilfe;
int baseOffset = 0;
String values;

public ICMPv4(String values) {
	hilfe = new Hilfe(values);
	this.values = values;
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
	System.out.println("--------------ICMPv4-----------------");
	System.out.println("Type: 0x" + getType() + " Offset: 0x" + (Integer.toHexString(baseOffset)));
	System.out.println("Code: 0x" + getCode() + " Offset: 0x" + (Integer.toHexString(baseOffset+1)));
	if((getType().equals("03") || getType().equals("0b")) && hilfe.bytes.length >= 28) {
		IPv4 ip = new IPv4(values.substring(24));
		ip.setBaseOffset(8+baseOffset);
		ip.printAll();
	}
}
}
