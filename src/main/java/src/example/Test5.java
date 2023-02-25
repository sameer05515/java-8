package example;

public class Test5 {
    public static void main(String[] args) {
        int[] arr=new int[]{1,2,5,7,8};
        int k=2;
        printAllCombination(arr,k);
    }

    private static void printAllCombination(int[] arr, int k) {
        for (int i = 0; i < arr.length ; i++) {
            for (int j = 0; j < arr.length; j++) {
                if(i!=j && ((arr[i]-arr[j])==2)){
                    System.out.println(arr[i]+" "+arr[j]+" "+(arr[i]-arr[j]));
                }
            }
        }
    }


}
