package mpks.jabia.client;

public class Main {
    public static void main(String[] args) {
        try {
            var game = new Game();
            game.run(args);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}