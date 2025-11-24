import java.util.Scanner;

public class Banda {
    String id;
    private static String nome;
    private static String cnpj;

    public static void cadastrarBanda(String raizBanda, Scanner sc) {
        System.out.println("Insira o nome da banda: ");
        nome=sc.nextLine();
        System.out.println("Insira o cnpj da banda: ");
        cnpj = sc.nextLine();

    }
}
