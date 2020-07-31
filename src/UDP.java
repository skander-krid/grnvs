public class UDP {
	Hilfe hilfe;
	int baseOffset = 0;
	
	public UDP(String values) {
		hilfe = new Hilfe(values);	
	}
	
	public void setBaseOffset(int baseOffset) {
		this.baseOffset = baseOffset;
	}
	
	public String getSrc() {
		return hilfe.getBytesHex(0, 2);
	}
	
	public String getDst() {
		return hilfe.getBytesHex(2, 2);
	}
	
	public String getLength() {
		return hilfe.getBytesHex(4, 2);
	}
	
	
	public void printAll() {
		System.out.println("--------------UDP-----------------");
		System.out.println("Source Port: " + Integer.valueOf(getSrc(), 16) + " (" + getSrc() +") Offset: 0x" +(Integer.toHexString(baseOffset)));
		System.out.println("Destination Port: " + Integer.valueOf(getDst(), 16) + " (" + getDst() +") Offset: 0x" + (Integer.toHexString(baseOffset+2)) );
		System.out.println("Length: " + Integer.valueOf(getLength(), 16) + " (" + getLength() +") Offset: 0x" + (Integer.toHexString(baseOffset+2)) );

	}
	
}
