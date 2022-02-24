import java.util.Scanner;

public class ConsoleApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("hello");

        Bee bee =  new Bee();
        System.out.println("Give the x of the bee");
        int numberX = scanner.nextInt();
        bee.setX(numberX);
        scanner.nextLine();

        System.out.println("Give the y of the bee");
        int numberY = scanner.nextInt();
        bee.setY(numberY);
        scanner.nextLine();
        System.out.println("Give the message of the bee");
        String message = scanner.nextLine();
        bee.setMessage(message);

        System.out.println(bee.getMessage());
        System.out.println("Bee is at " + bee.getX() + " " + bee.getY());
        System.out.println("End of program");
    }
}
