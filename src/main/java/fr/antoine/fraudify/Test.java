package fr.antoine.fraudify;

public class Test {

    public static void main(String[] args) {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            System.out.println("Vous êtes sous Windows.");
        } else if (os.contains("nux")) {
            System.out.println("Vous êtes sous Linux.");
        } else {
            System.out.println("Système d'exploitation non reconnu.");
        }
    }
}
