public class IPv6 {
	Hilfe help;
	int baseOffset = 0;
	
	/*
	 * 
	 * Payload Length: gibt Länge der auf den Header folgenden Daten in Byte an. (inkl. Extension Headers)
	 * 
	 * 
	 */
	public IPv6(String values) {
		this.help = new Hilfe(values);
	}
	
	public void setBaseOffset(int baseOffset) {
		this.baseOffset = baseOffset;
	}
	
	public Hilfe getHelp() {
		return help;
	}

	public String getVersion() {
		return help.getBitsDec(0,4) + "";
	}

	public String getTrafficClass() {
		return help.getBitsDec(4, 8) + " (0x" + help.getBitsHex(4, 8) + ")";
	}

	public String getFlowLabel() {
		return "0x" + help.getBitsHex(12, 20);
	}

	public String getPayloadLength() {
		return "0x" + help.getBytesHex(4, 2) + " -- Offset: 0x" + Integer.toHexString(baseOffset + 4);
	}

	public String getNextHeader() {
		return "0x" + help.getBytesHex(6, 1) + " -- Offset: 0x" + Integer.toHexString(baseOffset + 6);
	}

	public String getHopLimit() {
		return "0x" + help.getBytesHex(7, 1) + " -- Offset: 0x" + Integer.toHexString(baseOffset + 7);
	}

	public String getSourceAddress() {
		String result = "";
		int startBit = 8;
		for (int byt = startBit; byt < startBit + 16; byt += 2) {
			result += help.getBytesHex(byt, 2) + ":";
		}
		return result + " -- Offset: 0x" + Integer.toHexString(baseOffset + startBit);
	}

	public String getDestinationAddress() {
		String result = "";
		int startBit = 24;
		for (int byt = startBit; byt < startBit + 16; byt += 2) {
			result += help.getBytesHex(byt, 2) + ":";
		}
		return result + " -- Offset: 0x" + Integer.toHexString(baseOffset + startBit);
	}

	public void setHelp(Hilfe help) {
		this.help = help;
	}

	public void printAll() {
		System.out.println("-----------------------IPv6-----------------------");
		System.out.println("---------------------" + Integer.valueOf(help.getBytesHex(4, 2), 16) + " Bytes----------------------");
		System.out.println("Version: " + getVersion());
		System.out.println("Traffic Class: " + getTrafficClass());
		System.out.println("Flow Label: " + getFlowLabel());
		System.out.println("Payload length: " + getPayloadLength());
		System.out.println("Next Header: " + getNextHeader());
		System.out.println("Hop Limit: " + getHopLimit());
		System.out.println("Source Address: " + getSourceAddress());
		System.out.println("Destination Address: " + getDestinationAddress());
	}
	
	
	public static void main(String[] args) {
		IPv6 paket = new IPv6("45 c0 "
				+ "00 53 20 dc 00 00 40 01 d2 5b c0 a8 02 fe c0 a8 "
				+ "02 64 03 00 82 42 00 00 00 00 45 00 00 37 59 84 "
				+ "00 00 40 11 9c 24 c0 a8 02 64 c0 00 02 01 cc 1a "
				+ "00 35 00 23 b2 4b 86 b2 01 20 00 01 00 00 00 00 "
				+ "00 00 05 67 72 6e 76 73 03 6e 65 74 00 00 1c 00 01");
		paket.printAll();
	}
}