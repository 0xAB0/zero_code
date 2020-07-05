public class App {
    public static void main(String[] args) {

        float a,b,c;

        a = 0.000000000000001f;
        b = 0.000000000000002f;

        c = 1000000000000000000000f;

        System.out.println( (a*b)*c );
        System.out.println( a*(b*c) );
    }
}
