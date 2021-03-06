/**  Programa simulador de um SO.
 *   Trabalho de SO II.
 */

/** Imports necessários para funcionamento do programa:
 *  Scanner: para realizar leitura pelo teclado.
 *  Date: para obter a hora do computador.
 *  SimpleDateFormat: para formatar a hora em hh:mm:ss.
 *  DateFormat: formata a hora, em conjunto com SimpleDateFormat.
 *  FileWriter: para criar o arquivo txt.
 *  PrintWriter: para escrever no arquivo txt.
 *  IOException: Exceção de input/output.
 */
package so;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.lang.Thread;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *  Classe principal (executável).
 * @author JUNIOR
 */
public class So
{
    
    private static final Gerenciador gerenciador = new Gerenciador();   /** gerenciador é criado. Instância da classe Gerenciador. */
    private static String comando = null;       /** comando receberá o comando informado pelo usuário. */    
    private static String separa_string_comando[] = new String[4];  /** separa_string_comando é um array de string, receberá os valores quebrados da string comando. */        
    private static FileWriter arquivo;      /** arquivo ¨"vai ser" o arquivo txt que armazenará informações do programa. */    
    private static PrintWriter gravarArquivo;       /** gravarArquivo irá inserir dados no arquivo. */
    private static boolean controla_thread;     /** Variável que controlará a execução da thread. */
    
    
    /** MAIN
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException
    {
        
        Scanner sc = new Scanner(System.in);        /** instrucao para possibilitar leitura do teclado. */        
        gerenciador.limpaArraylist();       /** Limpa os arraylists antes de utiliza-los. Só por garantia. */
        gerenciador.getHost1().setNome("Host 1");       /** A classe Host tem o atributo 'nome' para facilitar os "prints" nas tabelas, por isso este comando é necessário. */
        gerenciador.getHost2().setNome("Host 2");        
        arquivo = new FileWriter("C:\\So_log.txt");     /** O arquivo externo "So_log.txt" é aberto para operações de saída através do objetoarquivo instanciado e criado a partir da classe FileWriter. */        
        gravarArquivo = new PrintWriter(arquivo);       /**  objeto de gravação gravarArquivo é associado a um fluxo de saída de dados baseado em caracteres através da classe PrinterWriter. */
        
        printOpcoesEscalonador();        
        gerenciador.setEscalonador(sc.nextLine());      
        
        for (;;)
        {
            /** É verificado o escalonador apenas para mostrar na tela qual deles foi selecionado. */
            if ( gerenciador.getEscalonador().equals("1") )
            {    
                System.out.println("Escalonador selecionado: 1 - Sem quantum.");
                System.out.println("_________________________________________________________________________________");        
                break;
            }
            else if ( gerenciador.getEscalonador().equals("2") )
            {
                for (;;)
                {
                    System.out.println("Escalonador selecionado: 2 - Com quantum.");
                    System.out.println("Informe o quantum (o quantum deve ser maior ou igual a 5:");
                    System.out.print("Quantum:");
                    gerenciador.setQuantum(sc.nextLine());
                    if ( gerenciador.getQuantum() >= 5 ) 
                    {
                        System.out.println("Escalonador com quantum de "+gerenciador.getQuantum()+" unidades de tempo.");
                        System.out.println("_________________________________________________________________________________");
                        break;
                    }
                    else
                    {
                        System.out.println("Quantum invlálido.");
                        System.out.println("_________________________________________________________________________________");
                    }
                    break;   
                }
                break;
            }
            else
            {
                
                System.out.println("Tipo de escalonador inválido. Informe novamente.");
                printOpcoesEscalonador();        
                gerenciador.setEscalonador(sc.nextLine());
                
            }
            
        }
        
        controla_thread = true;
        new Thread(verifica).start(); /** Início da thread que verificará os tempos de execução dos processos. */       
        sc.reset();
        /** Iteração para ficar pedindo o comando do usuário. Só terá fim caso o usuário digite EXIT */
        for (;;)
        {            
            /** Mostra o menu de opções. */
            printOpcoesComando();
            System.out.print("Comando:");
            /** Instrução para ler o valor inserido no teclado, até houver a quebra de linha (ENTER) */
            comando = sc.nextLine();
                                  
            /** o comando é quebrado e atribuído para um array de string */
            separa_string_comando = comando.split(" ");
                        
            /** Verifica a primeira posição do array de string. Se for CREATE o gerenciador já cria o processo */
            if ( ( separa_string_comando[0].equals("CREATE") ) || ( separa_string_comando[0].equals("create") ) )
            {
                /** gerenciador cria um processo, já enviando as informações das próximas posições do array de string. */
                gerenciador.criarProcesso(separa_string_comando[1], separa_string_comando[2], separa_string_comando[3], getTempoAtual());               
            } 
            /** Verifica a primeira posição do array de string. Se for PS o gerenciador irá mostrar a tabela de processos */
            else if ( (separa_string_comando[0].equals("PS") ) || ( separa_string_comando[0].equals("ps") ) )
            {
                gerenciador.mostraTabelaPS();
            } 
            /** Verifica a primeira posição do array de string. Se for MEM o gerenciador irá verificar se há algum parâmetro a mais */
            else if ( ( separa_string_comando[0].equals("MEM") ) || ( separa_string_comando[0].equals("mem") ) )
            {
                /** Se não há parâmetros, entra aqui */ 
                if ( separa_string_comando[1] == null)
                {
                    /** o gerenciador mostrará a configuração da memória. */
                    gerenciador.mostraMem("nd");
                }
                /** Se o parâmetro for F ou V, entra aqui */
                else if ( ( ( separa_string_comando[1].equals("F") ) || ( separa_string_comando[1].equals("f") ) ) || ( ( separa_string_comando[1].equals("V") ) || ( separa_string_comando[1].equals("v") ) ) )
                {       
                    /** O gerenciador mostrará o escopo da memória FISICA ou VIRTUAL, dependendo da informação contida na próxima posição do array. */
                    gerenciador.mostraMem(separa_string_comando[1]);
                    /** Se for alguma coisa diferente de F ou V, entra aqui para informar parâmetro invalido. */
                }
                
                else 
                {    
                    System.out.println("Parametro inválido!!");
                }
            }        
               else if ( ( comando.equals("EXIT") ) || ( comando.equals("exit") ))
            {            
                arquivo.close();
                controla_thread = false;
                break;
            } 
            /** Se for alguma coisa diferente das opções do menu, entra aqui para informar comando invalido. */
            else
            {
                System.out.println("Comando inválido!!");
            }

        }  
        System.out.println("Arquivo txt gerado em \"C:\\So_log.txt\\ \" ");
        arquivo.close();
    }
    
    /**
     * Printa as opções de escalonador para o usuário.
     */
    public static void printOpcoesEscalonador()
    {
        System.out.println("-> \tSelecione o tipo de escalonador:");
        System.out.println("-> \t     1 - FIFO sem quantum");
        System.out.println("-> \t     2 - FIFO com quantum");
        System.out.println("\n_________________________________________________________________________________");
        System.out.print("Escalonador:");
    }
    
    /**
     *  Printa as opções de comando para usuário.
     */
    public static void printOpcoesComando()
    {
        
            System.out.println("\n\n-> \t                     Exemplos de Comados:");
            System.out.println("-> \tCREATE (Ex: CREATE A 10 15) = Cria um processo com Nome:A, Tamanho:10, Execução:15");
            System.out.println("-> \tPS = Mostra tabela.");
            System.out.println("-> \tMEM = Mostra Configuração da memória.");
            System.out.println("-> \tMEM F ou V = Mostra escopo da memória Fisica ou escopo da memória Virtual.");
            System.out.println("-> \tEXIT = Sai do programa.");
            System.out.println("\n\n_________________________________________________________________________________");
    }
    
    /**
     * Método que retorna a hora atual do sistema.
     * @return hora - hora atual do sistema
     */

    /**
     * Método que retorna o tempo atual do sistema.
     * @return calendar - Tempo atual do sistema.
     */
    public static Date getTempoAtual() 
    {
	Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());        
        return calendar.getTime();     
    }
        
    /**
     * Cabeçalho do arquivo txt.
     */
    public void inicializaArquivo()
    {
        gravarArquivo.printf("=============================================================================================================%n"); 
        gravarArquivo.printf("======================================== LOG de comandos e processos ========================================%n");
        gravarArquivo.printf("=============================================================================================================%n%n"); 
    }
    
    /**
     * Método que insere dados no arquivo txt.
     * @param dados - string que será colocado no arquivo.
     */
    public static void insereDadosArquivo(String dados)
    {
        gravarArquivo.printf(dados);
    }
    
    /**
     * Thread responsável por monitorar o tempo de execução doo processos.
     */
    public static Runnable verifica = new Runnable()
    {
        public void run()
        {
            while (controla_thread)
            {
                if ( gerenciador.getHost1().getCore1() != null)
                    gerenciador.verificaExecucaoThread( gerenciador.getHost1().getCore1());
                if ( gerenciador.getHost1().getCore2() != null)
                    gerenciador.verificaExecucaoThread( gerenciador.getHost1().getCore2());
                if ( gerenciador.getHost2().getCore1() != null)
                    gerenciador.verificaExecucaoThread( gerenciador.getHost2().getCore1());
                if ( gerenciador.getHost2().getCore2() != null)
                    gerenciador.verificaExecucaoThread( gerenciador.getHost2().getCore2());
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(So.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        
    };
   
}

   