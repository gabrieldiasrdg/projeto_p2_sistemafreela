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
        s.instrumentos = new Instrumentos();

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

        sc.nextLine(); //Jogar o enter fora

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
        sc.nextLine(); //Jogar o enter fora
        System.out.println("Complemento(Características do local): ");
        s.enderecoEvento.compemento = sc.nextLine();

        //INSTRUMENTOS
        do {
        System.out.println("Insira a quantidade de instrumentistas a serem contratados(Limite de 8 intrumentos): ");
        s.instrumentos.quantidadeInstrumentos = sc.nextInt();
        } while (s.instrumentos.quantidadeInstrumentos<1 || s.instrumentos.quantidadeInstrumentos>8);
        int op = 0;
        for (int i = 0; i < s.instrumentos.quantidadeInstrumentos; i++) {
            System.out.printf("Insira %dº instrumento requerido na lista: \n", i+1);
            do {
                menuInstrumentos();
                op = sc.nextInt();
            } while (op < 1 || op > 8);
            do{
                System.out.printf("Instira o valor do cachê do instrumentista que tocará o(a) %s: R$\n", s.instrumentos.instrumentosMusicais[op-1]);
                s.instrumentos.valorCache[i] = sc.nextDouble();
                if(s.instrumentos.valorCache[i]<200) {
                    System.out.println("A escravidão foi abolida em 1888, insira um valor acima de R$200,00!!");
                }
            } while(s.instrumentos.valorCache[i] < 200);
            s.instrumentos.numeroDeInstrumentos[op-1]++;
        }
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

    private static void menuInstrumentos() {
        System.out.println(	"\n-----------------------"
                + "\n1) Guitarra"
                + "\n2) Violão"
                + "\n3) Baixo"
                + "\n4) Bateria"
                + "\n5) Cajon"
                + "\n6) Teclado"
                + "\n7) Percussão"
                + "\n8) Sanfona"
                + "\n-----------------------");
        System.out.println("Opção: ");

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