import java.util.Arrays;

public class Testing {
    public static void main(String[] args) {
        Object[] objects = new Object[2];
        objects[0] = 1;
        objects[1] = "Denis";

        System.out.println(Arrays.toString(objects[0].toString().getBytes()));
        System.out.println(Arrays.toString(objects[1].toString().getBytes()));
        System.out.println(Arrays.toString(Arrays.toString(objects).getBytes()));

        byte[] converted = Arrays.toString(objects).getBytes();


        String newMessage = 1 + "";


    }
}
