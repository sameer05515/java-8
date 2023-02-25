package example;

public class Test3 {

    public static void main(String[] args) {
        String largest = getLargestPalindrome("MalayalamAt123");
        System.out.println(largest);
    }

    private static String getLargestPalindrome(String givenString) {
        String result = "";

        for (int i = 2; i < givenString.length(); i++) {
            for (int j = 0; (j+i)<givenString.length(); j++) {
                String testStr = givenString.substring(j, j + i);
                if (checkPalindrome(testStr) && result.length()<testStr.length()) {
                    result=testStr;
                }

//                if (testStr.equalsIgnoreCase(new StringBuilder(testStr).reverse().toString()) && result.length()<testStr.length()) {
//                    result=testStr;
//                }
            }
        }
        return result;
    }

    private static boolean checkPalindrome(String testStr) {
        String s = "";
        for (char ch : testStr.toCharArray()) {
            s = ch + s;
        }
        return testStr.equalsIgnoreCase(s);
    }
}
