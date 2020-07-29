public class IPv4 {
	static final String ICMP = "01";
	
	Hilfe help;
	int baseOffset = 0;
	ICMPv4 icmp;
	
	/*
	 * 
	 * IHL: gibt Länge des IP Headers inkl. Optionen in Vielfachen von 32bit = 4B
	 * 
	 * Total Length: gibt Gesamtlänge des IP-Pakets in Bytes an (Max = 65535)
	 * 
	 */
	public IPv4(String values) {
		this.help = new Hilfe(values);
		if (help.getBytesHex(9, 1).equals(ICMP)) {
			icmp = new ICMPv4(values.substring(help.getBitsDec(4, 4) * 4 * 3));
			icmp.setBaseOffset(baseOffset + help.getBitsDec(4, 4) * 4);
		}
	}
	
	public void setBaseOffset(int baseOffset) {
		this.baseOffset = baseOffset;
	}
	
	public String getVersion() {
		return String.valueOf(help.getBitsDec(0, 4));
	}
	
	public String getIHL() {
		return String.valueOf(help.getBitsDec(4, 4));
	}
	
	public String getTOS() {
		return "0x" + help.getBytesHex(1, 1)+ " -- Offset: 0x" + Integer.toHexString(baseOffset + 1) +  " -- Length: " + 1;
	}
	
	public String getTotalLength() {
		return Long.valueOf(help.getBytesHex(2, 2), 16) + " (0x" + help.getBytesHex(2, 2) + ")" + " -- Offset: 0x" + Integer.toHexString(baseOffset + 2) +  " -- Length: 2";
	}
	
	public String getIdentification() {
		return "0x" + help.getBytesHex(4, 2)+ " -- Offset: 0x" + Integer.toHexString(baseOffset + 4) +  " -- Length: " + 2;
	}
	
	public String getRES() {
		return help.getBitsHex(6*8, 1);
	}
	
	public String getDF() {
		return help.getBitsHex(6*8 + 1, 1);
	}
	
	public String getMF() {
		return help.getBitsHex(6*8 + 2, 1);
	}
	
	public String getFragmentOffset() {
		return "0x" + help.getBitsHex(6*8 + 3, 13);
	}
	
	public String getTTL() {
		return Long.valueOf(help.getBytesHex(8, 1), 16) + " (0x" + help.getBytesHex(8, 1) + ")"+ " -- Offset: 0x" + Integer.toHexString(baseOffset + 8) +  " -- Length: " + 1;
	}
	
	public String getProtocol() {
		return "0x" + help.getBytesHex(9, 1) + " -- Offset: 0x" + Integer.toHexString(baseOffset + 9) +  " -- Length: " + 1;
	}
	
	public String getHeaderChecksum() {
		return "0x" + help.getBytesHex(10, 2) + " -- Offset: 0x" + Integer.toHexString(baseOffset + 10) +  " -- Length: " + 2;
	}
	
	public String getSourceAddress() {
		String result = "";
		int startBit = 12;
		for (int byt = startBit; byt < startBit + 4; byt++) {
			result += help.getBytesDec(byt, 1) + ".";
		}
		return result + " -- Offset: 0x" + Integer.toHexString(baseOffset + startBit) +  " -- Length: " + 4;
	}
	
	public String getDestinationAddress() {
		String result = "";
		int startBit = 16;
		for (int byt = startBit; byt < startBit + 4; byt++) {
			result += help.getBytesDec(byt, 1) + ".";
		}
		return result + " -- Offset: 0x" + Integer.toHexString(baseOffset + startBit) +  " -- Length: " + 4;
	}
	
	public void printAll() {
		System.out.println("-----------------------IPv4-----------------------");
		System.out.println("Version: " + getVersion());
		System.out.println("IHL (4B): " + getIHL());
		System.out.println("TOS: " + getTOS());
		System.out.println("Total length: " + getTotalLength());
		System.out.println("Identification: " + getIdentification());
		System.out.println("RES, DF, MF: " + getRES() + " " + getDF() + " " + getMF());
		System.out.println("Fragment offset (8B): " + getFragmentOffset());
		System.out.println("TTL: " + getTTL());
		System.out.println("Protocol: " + getProtocol());
		System.out.println("Checksum: " + getHeaderChecksum());
		System.out.println("Source Address: " + getSourceAddress());
		System.out.println("Destination Address: " + getDestinationAddress());
		if (icmp != null) {
			icmp.printAll();
		}
	}
	
	public static void main(String[] args) {
		IPv4 paket = new IPv4("45 c0 "
				+ "00 53 20 dc 00 00 40 01 d2 5b c0 a8 02 fe c0 a8 "
				+ "02 64 03 00 82 42 00 00 00 00 45 00 00 37 59 84 "
				+ "00 00 40 11 9c 24 c0 a8 02 64 c0 00 02 01 cc 1a "
				+ "00 35 00 23 b2 4b 86 b2 01 20 00 01 00 00 00 00 "
				+ "00 00 05 67 72 6e 76 73 03 6e 65 74 00 00 1c 00 01");
		paket.setBaseOffset(2);
		paket.printAll();
	}
}
