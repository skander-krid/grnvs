public class Hilfe {
	int[] bytes;
	
	public Hilfe (String values) {
		String[] stringBytesStrings = values.split(" ");
		bytes = new int[stringBytesStrings.length];
		for (int i = 0; i < stringBytesStrings.length; i++) {
			bytes[i] = Integer.decode("0x" + stringBytesStrings[i]);
		}
	}
	
	public String getBytesHex (int from, int bytesCount) {
		String result = "";
		
		for (int i = from; i < from + bytesCount; i++) {
			if(Integer.toHexString(bytes[i]).length() == 1)
				result += "0" + Integer.toHexString(bytes[i]);
			else
				result += Integer.toHexString(bytes[i]);

		}
		return result;
	}
	
	public String getBytesDec (int from, int bytesCount) {
		String result = "";
		
		for (int i = from; i < from + bytesCount; i++) {
			result += bytes[i] + " ";
		}
		
		result = result.substring(0, result.length() - 1);
		
		return result;
	}
	
	public int getBitsDec (int from, int bitsCount) {
		int startByte = from / 8;
		int startBit = from % 8;
		int endByte = startByte + (startBit - 1 + bitsCount) / 8;
		int endBit = (from - 1 + bitsCount) % 8;
		
		int result = 0;
		int i = 0;
		for (int bytePos = endByte; bytePos >= startByte; bytePos--) {
			result += bytes[bytePos] * Math.pow(256,i);
			i++;
		}
		
		int up = (endByte - startByte) * 8 + (8 - startBit);
		result = result % (int)Math.pow(2, up);
		int down = 7 - endBit;
		result = result / (int) Math.pow(2, down);
		
		return result;
	}
	
	public String getBitsHex (int from, int bitsCount) {
		return Integer.toHexString(getBitsDec(from, bitsCount));
	}
	
	public static void main(String[] args) {
		Hilfe h = new Hilfe("aa b2 2c");
		System.out.println(h.getBytesDec(0,1));
		System.out.println(h.getBitsHex(8, 12));
	}
}
