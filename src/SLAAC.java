public class SLAAC {

	public SLAAC(String MAC) {
		String x = Integer.toBinaryString(Integer.parseInt(MAC.substring(0,2), 16)); 
		while(x.length() < 8)
			x = "0" + x;
		char c = (char) ((1+x.charAt(6))%2);
		x = x.substring(0,6) + c + x.charAt(7);
		x = Integer.toHexString(binaryToInteger(x));
		String result = "fe80::" + x + MAC.substring(3,5) 
		+ ":" + MAC.substring(6,8) + "ff:fe" + MAC.substring(9,11) + ":" + MAC.substring(12,14) + MAC.substring(15,17);
		System.out.println("SLAAC: " + result);
	}
	
	public static void main(String[] args) {
		SLAAC slaac = new SLAAC("02 00 12 34 56 78");
	}
	

public Integer binaryToInteger(String binary){
    char[] numbers = binary.toCharArray();
    Integer result = 0;
    int count = 0;
    for(int i=numbers.length-1;i>=0;i--){
         if(numbers[i]=='1')result+=(int)Math.pow(2, count);
         count++;
    }
    return result;
}
}
