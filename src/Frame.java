
public class Frame {
Hilfe hilfe;
public Frame(String values) {
	hilfe = new Hilfe(values.substring(0, 42));
	printAll();
	if(getType().equals("0800")) {
		IPv4 ip = new IPv4(values.substring(42, (values.length() - 12)));
		ip.setBaseOffset(14);
		ip.printAll();
	}else if(getType().equals("86dd")) {
		IPv6 ip = new IPv6(values.substring(42, (values.length() - 12)));
	}else if(getType().equals("0806")) {
		//ARP
	}else if(getType().equals("8035")) {
		//RARP
	}else if(getType().equals("814c")) {
		//SNMP
	}else if(getType().equals("0842")) {
		//WoL
	}
		
	
		
}

public String getDst() {
	String result = "";
	int startBit = 0;
	for (int byt = startBit; byt < startBit + 6; byt += 1) {
		result += hilfe.getBytesHex(byt, 1) + ":";
	}
	return result.substring(0, result.length()-1);
}

public String getSrc() {
	String result = "";
	int startBit = 6;
	for (int byt = startBit; byt < startBit + 6; byt += 1) {
		result += hilfe.getBytesHex(byt, 1) + ":";
	}
	return result.substring(0, result.length()-1);
}

public String getType() {
	return hilfe.getBytesHex(12, 2);
}

public void printAll() {
	System.out.println("Destination MAC: " + getDst());
	System.out.println("Source MAC: " + getSrc());
	System.out.println("Type L3: 0x" + getType());
}

public static void main(String[] args) {
	String values = "90 e2 ba 2a 8d 97 90 e2 ba 86 dd 60 08 00 45 c0" + 
			" 00 53 20 dc 00 00 40 01 d2 5b c0 a8 02 fe c0 a8" + 
			" 02 64 03 00 82 42 00 00 00 00 45 00 00 37 59 84" + 
			" 00 00 40 11 9c 24 c0 a8 02 64 c0 00 02 01 cc 1a" + 
			" 00 35 00 23 b2 4b 86 b2 01 20 00 01 00 00 00 00" + 
			" 00 00 05 67 72 6e 76 73 03 6e 65 74 00 00 1c 00" + 
			" 01";
	Frame x = new Frame(values);
}
	
}
