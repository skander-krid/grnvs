public class Submask {
	public static String getNetworkAddress(String ipAddress, int prefix) {
		long ipValue = getLongFromIp(ipAddress);
		int toDiv = 32 - prefix;
		ipValue = (long)(ipValue / Math.pow(2, toDiv));
		ipValue = (long)(ipValue * Math.pow(2, toDiv));
		return getIpFromLong(ipValue);
	}
	
	private static long getLongFromIp(String ip) {
		String[] values = ip.split("\\.");
		int i = 3;
		long result = 0;
		for (String value : values) {
			result += Long.valueOf(value) * Math.pow(256, i--);
		}
		return result;
	}
	
	private static String getIpFromLong(long ip) {
		String result = "";
		result += (long) (ip / Math.pow(256, 3)) + ".";
		result += (long)((ip / Math.pow(256, 2)) % Math.pow(256, 1)) + ".";
		result += (long)((ip / Math.pow(256, 1)) % Math.pow(256, 1)) + ".";
		result += (long)(ip % Math.pow(256, 1));
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(getNetworkAddress("172.16.1.23", 12));
	}
}
