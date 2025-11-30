import java.io.File;

public class Musico {
    String id;
    String nome;
    int nInstrumentosToca;
    int registro;
    String[] instrumentoDoMusico;

    public static boolean existeRegistro(String registro, String raizMusico ) {
        File f = new File(raizMusico);
        File[] arquivos = f.listFiles();
        for(int i = 0 ; i<arquivos.length; i++){
            String nome = arquivos[i].getName(); //Pega o nome doa rquivo todo
            nome = nome.replace(".txt", ""); //retira o txt
            String[] partes = nome.split("_"); //Divide em duas partes, uma antes do '_' e outra depois;
            String registroNoArquivo = partes[1]; //pega a parte do nnúmero
            if (registroNoArquivo.equals(registro)) {
                return true;
            }
        }
        return false;
    }

    public static String gerarIDMusico(String nome, int registro) {
        String id = String.format("%s_%d", nome, registro);
        return id;
    }
}
