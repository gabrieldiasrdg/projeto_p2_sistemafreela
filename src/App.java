import java.io.File;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String raiz="Freela/";
        String raizShow = raiz + "Shows/";

        char opcao;
        Scanner sc=new Scanner(System.in);

        do {
            menuPrincipal();
            opcao=sc.next().charAt(0);
            switch (opcao) {
                case '1':
                    cadastrarShow(raizShow);
                    break;
                case '2':

                    break;
                case '3':

                    break;
                case '4':

                case '5':
                    iniciarResetar(raiz, raizShow);
                    break;
                case '6':
                    System.out.println("Saindo...");
            }
        }while(opcao!='6');


    }
    private static void cadastrarShow(String raizShow) {
        Scanner sc=new Scanner(System.in);
        Show s = new Show();
        s.dataEvento = new Data();
        s.enderecoEvento = new Endereco();
        s.horarioInicial = new Hora();
        s.horarioFinal = new Hora();

        //DATA
        System.out.println("Insira a data do evento no formato **DD MM AAAA** (Ex: 10 11 2025): ");
        s.dataEvento.dia = sc.nextInt();
        s.dataEvento.mes = sc.nextInt();
        s.dataEvento.ano = sc.nextInt();

        //HORÁRIOS EVENTO
        System.out.println("Insira o horário de INÍCIO do evento em **HH MM** (Ex: 14 30)): "); //HorárioInicial
        s.horarioInicial.hora = sc.nextInt();
        s.horarioInicial.minuto = sc.nextInt();
        System.out.println("Insira o horário de TÉRMINO do evento em **HH MM** (Ex: 14 30)): "); //HorárioFinal
        s.horarioFinal.hora = sc.nextInt();
        s.horarioFinal.minuto = sc.nextInt();


        //ENDEREÇO
        System.out.println("- Informações sobre o local onde ocorrerá o evento -");
        System.out.println("Cidade: ");
        s.enderecoEvento.cidade = sc.nextLine();
        System.out.println("Bairro: ");
        s.enderecoEvento.bairro = sc.nextLine();
        System.out.println("Logradouro: ");
        s.enderecoEvento.logradouro = sc.nextLine();
        System.out.println("Número: ");
        s.enderecoEvento.numero = sc.nextInt();
        System.out.println("Complemento(Características do local): ");
        s.enderecoEvento.compemento = sc.nextLine();

        //INSTRUMENTOS
        System.out.println();
    }

    private static void iniciarResetar(String raiz, String raizShow) {
        File dir = new File(raiz);
        if(!dir.exists()){ //cria a pasta Freela, se não existir
            dir.mkdir();
        }
        dir = new File(raizShow);
        if(!dir.exists()) { //cria a pasta Shows, se não existir
            dir.mkdir();
        }
        else {
            apagarArquivos(dir);
        }

    }

    private static void apagarArquivos(File dir) {
        String[] arquivos = dir.list();
        for (String arquivo : arquivos) {
            File f = new File(dir, arquivo);
            f.delete();
        }
    }

    private static void menuPrincipal() {
        System.out.println(	"\n-----------------------"
                + "\n1) Cadastrar novo show"
                + "\n2) Atualizar show"
                + "\n3) Excluir show"
                + "\n4) Listar shows pendentes"
                + "\n5) Iniciar/Resetar"
                + "\n6) Sair"
                + "\n-----------------------");
        System.out.println("Opção: ");

    }
}