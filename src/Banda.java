import java.util.Locale;

public class Banda {
    String id;
    String nome;
    String cnpj;

    public static String formatarCnpj(String cnpj) {
        return cnpj.substring(0, 2) + "." +
                cnpj.substring(2, 5) + "." +
                cnpj.substring(5, 8) + "-" +
                cnpj.substring(8, 12) + "-" +
                cnpj.substring(12, 14);
    }

    public static String gerarIDBanda(String nome, String cnpj) {
        nome = nome.replace(" ", "").toUpperCase();
        String id = String.format("%s_%s", nome, cnpj);
        return id;
    }
}
