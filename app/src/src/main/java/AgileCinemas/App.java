/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package AgileCinemas;

public class App {
    public String getGreeting() {
        return "Welcome to the AGILE Cinemas interface!";
    }

    public static void main(String[] args) {


        System.out.println("This was a test for Paul");


        System.out.println(new App().getGreeting());

        Cinema cinema = new Cinema();
        cinema.runCinema();
    }
}