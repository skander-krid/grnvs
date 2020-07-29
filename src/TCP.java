public class TCP {
	
	String sourcePort, destPort, seqNumber, ackNumber, offset, urg, ack, psh, rst, syn, fin, window, checksum, urgPointer;
	
	Hilfe hilfe;
	int baseOffset = 0;
	
	/*
	 * Offset: Länge des TCP-Headers in 4 Bytes
	 */
	public TCP(String values) {
		hilfe = new Hilfe(values);
	}
	
	public String getSrc() {
		return hilfe.getBytesHex(0, 2);
	}
	
	public String getDst() {
		return hilfe.getBytesHex(2, 2);
	}
	
	public void setBaseOffset(int baseOffset) {
		this.baseOffset = baseOffset;
	}

	public String getSourcePort() {
		return sourcePort;
	}

	public String getDestPort() {
		return destPort;
	}

	public String getSeqNumber() {
		return seqNumber;
	}

	public String getAckNumber() {
		return ackNumber;
	}

	public String getOffset() {
		return offset;
	}

	public String getUrg() {
		return urg;
	}

	public String getAck() {
		return ack;
	}

	public String getPsh() {
		return psh;
	}

	public String getRst() {
		return rst;
	}

	public String getSyn() {
		return syn;
	}

	public String getFin() {
		return fin;
	}

	public String getWindow() {
		return window;
	}

	public String getChecksum() {
		return checksum;
	}

	public String getUrgPointer() {
		return urgPointer;
	}
	
	void initialize() {
		this.sourcePort = Integer.valueOf(getSrc(), 16) + " (0x" + getSrc() +") Offset: 0x" +(Integer.toHexString(baseOffset));
		this.destPort = Integer.valueOf(getDst(), 16) + " (0x" + getDst() +") Offset: 0x" + (Integer.toHexString(baseOffset+2));
		this.seqNumber = "0x" + hilfe.getBytesHex(4, 4) + " -- Offset: 0x" + Integer.toHexString(baseOffset + 4);
		this.ackNumber = "0x" + hilfe.getBytesHex(8, 4) + " -- Offset: 0x" + Integer.toHexString(baseOffset + 8);
		this.offset = "0x" + hilfe.getBitsHex(8*12, 4);
		this.urg = hilfe.getBitsHex(8*13 + 2, 1);
		this.ack = hilfe.getBitsHex(8*13 + 3, 1);
		this.psh = hilfe.getBitsHex(8*13 + 4, 1);
		this.rst = hilfe.getBitsHex(8*13 + 5, 1);
		this.syn = hilfe.getBitsHex(8*13 + 6, 1);
		this.fin = hilfe.getBitsHex(8*13 + 7, 1);
		this.window = "0x" + hilfe.getBytesHex(14, 2) + " -- Offset: 0x" + Integer.toHexString(baseOffset + 14);
		this.checksum = "0x" + hilfe.getBytesHex(16, 2) + " -- Offset: 0x" + Integer.toHexString(baseOffset + 16);
		this.urgPointer = "0x" + hilfe.getBytesHex(18, 2) + " -- Offset: 0x" + Integer.toHexString(baseOffset + 18);
	
	}
	
	public void printAll() {
		initialize();
		System.out.println("-------------------TCP--------------------");
		System.out.println("------------------" + hilfe.getBitsDec(8*12, 4) * 4 + " Bytes-------------------");
		System.out.println("Source Port: " + getSourcePort());
		System.out.println("Destination Port: " + getDestPort());
		System.out.println("Sequence Number: " + getSeqNumber());
		System.out.println("Acknowledgement Number: " + getAckNumber());
		System.out.println("Offset (Length TCP header 4B): " + getOffset());
		System.out.println("urg, ack, psh, rst, syn, fin: " + getUrg() + getAck() + getPsh() + getRst() + getSyn() + getFin());
		System.out.println("Window: " + getWindow());
		System.out.println("Chescksum: " + getChecksum());
		System.out.println("Urgent Pointer: " + getUrgPointer());
	}
	
	public static void main(String[] args) {
		TCP t = new TCP("9a a0 00 35 6d 30 93 19 cc d8 5c 44 80 18" + 
				" 00 e5 73 cb 00 00 01 01 08 0a c3 fd 52 11 01 27" + 
				" 4f 28 00 2a 78 cb 01 10 00 01 00 00 00 00 00 00" + 
				" 01 31 01 31 03 31 36 38 03 31 39 32 07 69 6e 2d" + 
				" 61 64 64 72 04 61 72 70 61 00 00 0c 00 01 1a ee" + 
				" 1d 02");
		t.setBaseOffset(34);
		t.printAll();
	} 
}