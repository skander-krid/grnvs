
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
	System.out.println("----------------------Frame-------------------------");
	System.out.println("--------------------14 Bytes------------------------");
	System.out.println("Destination MAC: " + getDst());
	System.out.println("Source MAC: " + getSrc());
	System.out.println("Type L3: 0x" + getType());
}

public static void main(String[] args) {
	String values = "96 d7 9f 52 9d 4b 52 54 00 12 34 56 08 00 45 00" + 
			" 00 60 1b fe 40 00 40 06 4c dd c0 a8 01 05 08 08" + 
			" 04 04 9a a0 00 35 6d 30 93 19 cc d8 5c 44 80 18" + 
			" 00 e5 73 cb 00 00 01 01 08 0a c3 fd 52 11 01 27" + 
			" 4f 28 00 2a 78 cb 01 10 00 01 00 00 00 00 00 00" + 
			" 01 31 01 31 03 31 36 38 03 31 39 32 07 69 6e 2d" + 
			" 61 64 64 72 04 61 72 70 61 00 00 0c 00 01 1a ee" + 
			" 1d 02";
	Frame x = new Frame(values);
}
	
}
