import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
                    String funcaoAtualizar = "atualizar";
                    listarShows(raizShow, funcaoAtualizar);
                    break;
                case '3':
                    String funcaoExcluir = "excluir";
                    listarShows(raizShow,  funcaoExcluir);
                    break;
                case '4':
                    String funcaoVisualizar = "visualizar";
                    listarShows(raizShow,  funcaoVisualizar);
                    break;
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
        System.out.println("Insira o horário de INÍCIO do evento em **HH MM** (Ex: 14 00)): "); //HorárioInicial
        s.horarioInicial.hora = sc.nextInt();
        s.horarioInicial.minuto = sc.nextInt();
        System.out.println("Insira o horário de TÉRMINO do evento em **HH MM** (Ex: 17 00)): "); //HorárioFinal
        s.horarioFinal.hora = sc.nextInt();
        s.horarioFinal.minuto = sc.nextInt();

        sc.nextLine(); //limpar o buffer

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
        sc.nextLine(); //limpar o buffer
        System.out.println("Complemento(Características do local): ");
        s.enderecoEvento.compemento = sc.nextLine();

        //INSTRUMENTOS
        int salvaUltimaOp = -1;
        double salvaUltimoCache = 0.0;
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
                if (salvaUltimaOp == op) {
                    s.instrumentos.valorCache[i] = salvaUltimoCache;
                } else {
                    System.out.printf("Instira o valor do cachê do instrumentista que tocará o(a) %s: R$\n", s.instrumentos.instrumentosMusicais[op - 1]);
                    s.instrumentos.valorCache[i] = sc.nextDouble();
                    if (s.instrumentos.valorCache[i] < 150) {
                        System.out.println("A escravidão foi abolida em 1888, insira um valor acima de R$150,00!!\n");
                    }
                }
            } while(s.instrumentos.valorCache[i] < 150);
            salvaUltimaOp = op;
            salvaUltimoCache = s.instrumentos.valorCache[i];
            s.instrumentos.numeroDeInstrumentos[op-1]++;
        }
        //INFORMAÇÕES ADICIONAIS
        System.out.println("INSIRA AS INFORMAÇÕES ADICIONAIS SOBRE O SHOW(OPCIONAL): ");
        sc.nextLine(); // limpar o buffer
        s.infoAdicionais = sc.nextLine();

        //CRIANDO ARQUIVO
        s.id = gerarID(s.dataEvento.ano, s.dataEvento.mes, s.dataEvento.dia, s.horarioInicial.hora, s.horarioInicial.minuto);
        if (criarArquivo(raizShow, s)) {
            System.out.println("Show cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao gravar o arquivo do show.");
        }
    }

    private static void atualizarShow(String raizShow, File[] arquivos) {
        Scanner sc = new Scanner(System.in);
        int opShow = -1;

        // Escolha do show
        do {
            System.out.print("Digite o número do show que deseja atualizar: ");
            opShow = sc.nextInt() - 1;
            sc.nextLine(); // limpar buffer
            if (opShow < 0 || opShow >= arquivos.length) {
                System.out.println("Número inválido! Tente novamente.\n");
            }
        } while (opShow < 0 || opShow >= arquivos.length);

        // Mostra o conteúdo atual
        imprimirArquivo(raizShow, arquivos[opShow].getName());

        // Confirma atualização
        String op;
        do {
            System.out.print("Deseja realmente atualizar este show? (S/N): ");
            op = sc.nextLine().toUpperCase();
        } while (!op.equals("S") && !op.equals("N"));

        if (op.equals("N")) {
            System.out.println("Atualização cancelada.\n");
            return;
        }

        // Apaga o arquivo antigo
        if (arquivos[opShow].delete()) {
            System.out.println("Arquivo antigo removido com sucesso!");
        } else {
            System.out.println("Erro ao remover o arquivo antigo.\n");
            return;
        }

        // Cadastra novamente (gera novo ID e arquivo atualizado)
        System.out.println("\n--- INSIRA OS NOVOS DADOS DO SHOW ---\n");
        cadastrarShow(raizShow);

        System.out.println("Show atualizado com sucesso!\n");
    }


    private static void excluirShow(File[] arquivos){
        Scanner sc = new Scanner(System.in);
        String op = "";
        int opShow = 0;

        System.out.println("Digite o número do show que você deseja excluir: ");
        do {
            opShow = sc.nextInt() - 1;
            sc.nextLine(); // limpar o buffer
        }while (opShow < 0 || opShow >= arquivos.length);

        do {
            System.out.printf("Tem certeza que deseja excluir o show '%s'? (S/N)\nR= \n", arquivos[opShow].getName());
            op = sc.nextLine().toUpperCase();
        } while (!op.equals("N")&&!op.equals("S"));
        if (op.equals("S")) {
            if (arquivos[opShow].delete()) {
                System.out.println("Show excluído com sucesso!\n");
            } else {
                System.out.println("Erro ao tentar excluir o show.\n");
            }
        } else {
            System.out.println("Exclusão cancelada.\n");
        }
    }

    private static void listarShows(String raizShow, String funcao) {
        Scanner sc = new Scanner(System.in);
        boolean existe;
        File dir = new File(raizShow);
        String op = "";

        existe = existeArquivo(dir);

        if (!existe) {
            System.out.println("Nenhum show cadastrado ainda!");
        } else {
            File[] arquivos = dir.listFiles();
            listarArquivos(arquivos);
            do {
                if (funcao.equals("visualizar")) {
                    op = visualisarShows(raizShow, arquivos);
                } else if (funcao.equals("excluir")) {
                    excluirShow(arquivos);
                    do {
                        System.out.println("Deseja excluir outro show? (S/N)\nR= ");
                        op = sc.nextLine().toUpperCase();
                    } while (!op.equals("N")&&!op.equals("S"));
                    if (op.equals("S")) {
                        listarShows(raizShow, funcao);
                    }
                } else if (funcao.equals("atualizar")) {
                    atualizarShow(raizShow, arquivos);
                    do {
                        System.out.print("Deseja atualizar outro show? (S/N)\nR= ");
                        op = sc.nextLine().toUpperCase();
                    } while (!op.equals("N") && !op.equals("S"));
                    if (op.equals("S")) {
                        listarShows(raizShow, funcao);
                    }
                }
            } while (!op.equals("N"));

            System.out.println("Voltando ao menu principal...\n");

        }
    }

    private static String visualisarShows(String raizShow, File[] arquivos) {
        Scanner sc = new Scanner(System.in);
        String op = "";
        int opShow = 0;
        do {
            System.out.println("Deseja visualizar algum show? (S/N)\nR= ");
            op = sc.nextLine().toUpperCase();
        } while (!op.equals("N")&&!op.equals("S"));
        if (op.equals("S")) {
            System.out.println("Digite o número do show que deseja visualizar: ");
            do {
                opShow = sc.nextInt() - 1;
                sc.nextLine(); // limpar o buffer
            }while (opShow < 0 || opShow >= arquivos.length);
            imprimirArquivo(raizShow, arquivos[opShow].getName());
            return op;
        } else {
            return op;
        }
    }

    private static void listarArquivos(File[] arquivos) {
        System.out.println("Shows cadastrados: ");
        for (int i = 0; i < arquivos.length; i++) {
            System.out.printf("%d) %s\n", i + 1, arquivos[i].getName());
        }
    }

    private static boolean existeArquivo(File dir) {
        return dir.exists() && dir.listFiles().length > 0;
    }

    private static void imprimirArquivo(String raizShow, String nomeArquivo) {
        try {
            System.out.println("\n-------------------------");
            System.out.println("Show: " + nomeArquivo);
            Scanner leitor = new Scanner(new File(raizShow+nomeArquivo));
            while (leitor.hasNextLine()) {
                System.out.println(leitor.nextLine());
            }
            leitor.close();
            System.out.println("\n-------------------------");
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir o arquivo: " + nomeArquivo);
        }
    }

    private static boolean criarArquivo(String raizShow, Show s) {
        File f = new File(raizShow + s.id + ".txt");
        if (f.exists()) {
            System.out.println("Já existe um show cadastrado nesse horário!");
            return false;
        }
        try {
            PrintWriter pw = new PrintWriter(raizShow + s.id + ".txt");
            pw.append("Id: " + s.id + "\n");
            pw.append("Data do evento: "+s.dataEvento.dia+"/"+s.dataEvento.mes+"/"+s.dataEvento.ano+"\n");
            pw.append("Carga horária de show (Início/Fim): "+s.horarioInicial.hora+"h"+s.horarioInicial.minuto+"min"+" - "+s.horarioFinal.hora+"h"+s.horarioFinal.minuto+"min"+"\n");
            pw.append("Informações do endereço onde ocorrerá o evento: \n");
            pw.append("- Cidade: "+s.enderecoEvento.cidade+"\n");
            pw.append("- Endereço: "+s.enderecoEvento.bairro+", "+s.enderecoEvento.logradouro+", Nº "+s.enderecoEvento.numero+"\n");
            pw.append("- Complemento: "+s.enderecoEvento.compemento+"\n");
            pw.append("Instrumentos requeridos: "+"\n");
            for(int i = 0; i < s.instrumentos.quantidadeInstrumentos; i++) {
                if(s.instrumentos.numeroDeInstrumentos[i]>0) {
                    if (s.instrumentos.numeroDeInstrumentos[i]>1){
                        for (int j = 0; j < s.instrumentos.numeroDeInstrumentos[i] ; j++) {
                            pw.append(String.format("- %s %d: Pendente | R$%.2f%n", s.instrumentos.instrumentosMusicais[i], j+1, s.instrumentos.valorCache[i]));
                        }
                    } else {
                        pw.append(String.format("- %s: Pendente | R$%.2f%n", s.instrumentos.instrumentosMusicais[i], s.instrumentos.valorCache[i]));
                    }
                }
            }
            pw.append("Informações adicionais: "+ s.infoAdicionais+"\n");
            pw.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String gerarID(int ano, int mes, int dia, int horaInicio, int minutoInicio) {
        String id = String.format("%04d%02d%02d_%02d%02d", ano, mes, dia, horaInicio, minutoInicio);
        return id;
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
                + "\n4) Visualizar shows pendentes"
                + "\n5) Iniciar/Resetar"
                + "\n6) Sair"
                + "\n-----------------------");
        System.out.println("Opção: ");

    }
}