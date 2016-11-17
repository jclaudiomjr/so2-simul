package so;

/** 
 * Import necessário para funcionamento da classe.
 * ArrayList: para utilizar Arraylist de processos criados e fila de prontos.
 */
import java.util.ArrayList;
import java.lang.Thread;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe Gerenciador - Responsável pela gerencia dos processos.
 * @author JUNIOR
 */
public class Gerenciador 
{   
    private Integer gera_pid;      /** seq_proc é usada no método geraPID() para gerar um novo PID de numero sequencial. */
    private Memoria memoria;       /** Instância que controla-rá futuramente os processos na memória. */    
    private Host host1, host2;     /** Instâncias para simular duas máquinas. */
    private ArrayList<Processo> fila_aptos;        /** Armazena processos prontos para executar. */
    private ArrayList<Processo> processos_criados;     /** Armazena todos os processos criados. */
    private String escalonador;     /** Tipo do escalonador */
    private int quantum;        /** Quantum de tempo */
    
    /** Construtor da classe Gerenciador, o contrutor já irá instanciar também uma memória e dois Hosts. */
    public Gerenciador()  
    {
        this.gera_pid = 0;
        this.memoria = new Memoria();
        this.host1 = new Host();
        this.host2 = new Host();
        this.processos_criados = new ArrayList<>();
        this.fila_aptos = new ArrayList<>();
    }

    /** Gera um pid de forma sequancial, para cada processo.
     * @return Integer - PID
     */
    public Integer geraPID()
    {
        gera_pid++;
        
        return gera_pid;
    }
    
    /**
     * Retorna o arraylist de todos os processos criados.
     * @return processos_criados - Arraylist de processos criados.
     */
    public ArrayList getListaCriados()
    {
        return processos_criados;
    }
    
    /**
     * Retorna o host 1
     * @return host1 - Host 1 do sistema.
     */
    public Host getHost1()
    {
        return host1;
    }
    
    /** 
     * Retorna o host 2
     * @return host2 - Host 2 do sistema
     */
    public Host getHost2()
    {
        return host2;
    }
    
    /** Metodo que verifica se o tempo de execução de cada processo em execução não excedeu seu tempo
     * 
     * @param h1core1 - core1 do host1
     * @param h1core2 - core2 do host1
     * @param h2core1 - core1 do host2
     * @param h2core2 - core2 do host2
     */
    
    
    public void verificaExecucaoThread(Processo processo_executando)
    {
        
        /** separa_string_tempo_atual receberá, os valores retornado de gettime(), de forma separada por ":". */
        //String separa_string_tempo_atual[] = So.getTime().split(":");
        //Integer tempo_atual[] = new Integer[3];

        /** tempo_atual recebe os valores de separa_string_tempo_atual para ser feita a verificação posteriormente */
        //tempo_atual[0] = Integer.parseInt(separa_string_tempo_atual[0]);
        //tempo_atual[1] = Integer.parseInt(separa_string_tempo_atual[1]);
        //tempo_atual[2] = Integer.parseInt(separa_string_tempo_atual[2]);    
        Calendar tempo_atual = Calendar.getInstance();
        Calendar tempo_saida = Calendar.getInstance();
        tempo_atual.setTime(So.getTempoAtual());
        tempo_saida.setTime(processo_executando.getTempo_saida());
        //Date tempo_atual = So.getTempoAtual();
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
        //System.out.println(tempo_atual.compareTo(tempo_saida));
        if ( tempo_atual.compareTo(tempo_saida) > 0 )
        {
            if ( ( processo_executando.getTempo_restante_execucao() == null) || ( processo_executando.getTempo_restante_execucao() <= 0 ) )
            {    
                processo_executando.setStatus("finalizado");
                /** Insere a informação no arquivo txt de que o processo foi finalizado. */
                So.insereDadosArquivo(formato.format(processo_executando.getTempo_saida())+": processo de nome:"+processo_executando.getNome()+" foi finalizado. %n");
                //processos_criados.remove(processo_executando.hashCode());
            }
            else
            {
                if ( processo_executando.getTempo_restante_execucao() < quantum )
                {
                    processo_executando.setTempo_execucao(Integer.toString(processo_executando.getTempo_restante_execucao()));
                }
                processo_executando.setTempo_restante_execucao(Integer.parseInt(processo_executando.getTempo_execucao()));
                
                fila_aptos.add(processo_executando);
            }
            
            if ( ! fila_aptos.isEmpty() )
            {
                fila_aptos.get(0).setTempo_entrada_execucao(formato.format(processo_executando.getTempo_saida()));
                fila_aptos.get(0).setTempo_saida(calculaTempoSaida(fila_aptos.get(0).getTempo_entrada_execucao(), fila_aptos.get(0).getTempo_execucao()));
            
            
                if ( host1.getCore1().hashCode() == processo_executando.hashCode() )
                {
                    host1.setCore1(null);
                    /** Seta o primeiro processo da fila de aptos no core1 do Host1. */
                    host1.setCore1(fila_aptos.get(0));
                    So.insereDadosArquivo(formato.format(tempo_atual.getTime())+": processo de nome:"+fila_aptos.get(0).getNome()+" entrou em execução no host1 core1.%n");
                }
                else if ( host1.getCore2().hashCode() == processo_executando.hashCode() )
                {
                    host1.setCore2(null);
                    /** Seta o primeiro processo da fila de aptos no core2 do Host1. */
                    host1.setCore2(fila_aptos.get(0));
                    So.insereDadosArquivo(formato.format(tempo_atual.getTime())+": processo de nome:"+fila_aptos.get(0).getNome()+" entrou em execução no host1 core2.%n");
                }
                else if ( host2.getCore1().hashCode() == processo_executando.hashCode() )
                {
                    host2.setCore1(null);
                    /** Seta o primeiro processo da fila de aptos no core1 do Host2. */
                    host2.setCore1(fila_aptos.get(0));
                    So.insereDadosArquivo(formato.format(tempo_atual.getTime())+": processo de nome:"+fila_aptos.get(0).getNome()+" entrou em execução no host2 core1.%n");
                }
                else if ( host2.getCore2().hashCode() == processo_executando.hashCode() )
                {
                    host2.setCore2(null);
                    /** Seta o primeiro processo da fila de aptos no core2 do Host2. */
                    host2.setCore2(fila_aptos.get(0));
                    So.insereDadosArquivo(formato.format(tempo_atual.getTime())+": processo de nome:"+fila_aptos.get(0).getNome()+" entrou em execução no host2 core2.%n");
                }
                /** remove o processo da fila de aptos, pois já está em execução. */
                fila_aptos.remove(0);
            }
        }
    }
    
    /**
     * Seta o tipo de escalonador.
     * @param escalonador - tipo de escalonador.
     */
    public void setEscalonador(String escalonador)
    {
        this.escalonador = escalonador;
    }
    
    /**
     * Retorna o tipo de escalonador.
     * @return escalonador - Tipo de escalonador.
     */
    public String getEscalonador()
    {
        return escalonador;
    }
    
    /**
     * Seta a quantidade (unidade de tempo) quantum.
     * @param quantum - quantum de tempo.
     */
    public void setQuantum(String quantum)
    {
        this.quantum = Integer.parseInt(quantum);
    }
    
    /**
     * Retorna o valor do quantum.
     * @return quantum - quantum de tempo.
     */
    public int getQuantum()
    {
        return quantum;
    }
    
    /** Método para criar processo e adicionar no arraylist de processos_criados.
     * 
     * @param nome - nome do processo.
     * @param tamanho - tamanho do processo (Bytes).
     * @param tempo_execucao - tempo de execução do processo.
     * @param tempo_criacao - tempo de criação do processo.
     */
    public void criarProcesso(String nome, String tamanho, String tempo_execucao, Date tempo_criacao)
    {       
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
        
        String entrada_execucao = null;
        Integer tempo_restante = null;
           
        if ( escalonador.equals("2") )
        {
            tempo_restante = Integer.parseInt(tempo_execucao);
            tempo_execucao = Integer.toString(quantum);
        }
        
        /** Início da verificação (para o host 1), se for 1 o processo irá para o core1 (do host1), se for 2 o processo vai para o core2 do host2). */
        if ( host1.getCore_Disp() == 1 )
        {                            
            entrada_execucao = formato.format(tempo_criacao);

            /** Cria o processo, adicionando ao arraylist de processos_criados. */
            processos_criados.add(new Processo( geraPID(), nome, tamanho, tempo_execucao, tempo_criacao, entrada_execucao,calculaTempoSaida(entrada_execucao,tempo_execucao), tempo_restante, getHost1(), "executando" ));
            /** core1 do host1 recebe o processo. */
            host1.setCore1(processos_criados.get(processos_criados.size() - 1));
            So.insereDadosArquivo(tempo_criacao+" Processo de nome "+nome+" criado no Host1 executando no Core1.%n");
        } 
        else if ( host1.getCore_Disp() == 2 )
        {               
            entrada_execucao = formato.format(tempo_criacao);
            
            
            /** Cria o processo, adicionando ao arraylist de processos_criados. */
            processos_criados.add(new Processo( geraPID(), nome, tamanho, tempo_execucao, tempo_criacao, entrada_execucao,calculaTempoSaida(entrada_execucao,tempo_execucao), tempo_restante, getHost1(), "executando" ));
            
              
            /** core2 do host1 recebe o processo. */
            host1.setCore2(processos_criados.get(processos_criados.size() - 1));
            So.insereDadosArquivo(tempo_criacao+" Processo de nome "+nome+" criado no Host1 executando no Core2.%n");
        } 
        /** fim da verificação para o host1 e início da verificação para o host2 caso não encontre um core disponível no host1. */
        else
        {
            /** Início da verificação para host2, se for 1 o processo irá para o core1 (do host2), se for 2 irá para o core2 (do host2). */
            if ( host2.getCore_Disp() == 1 )
            {                    
                entrada_execucao = formato.format(tempo_criacao);
                
                
                /** Cria o processo, adicionando ao arraylist de processos_criados. */
                processos_criados.add(new Processo( geraPID(), nome, tamanho, tempo_execucao, tempo_criacao, entrada_execucao,calculaTempoSaida(entrada_execucao,tempo_execucao), tempo_restante, getHost2(), "executando" ));
                /** core1 do host2 recebe o processo. */
                host2.setCore1(processos_criados.get(processos_criados.size() - 1));
                So.insereDadosArquivo(tempo_criacao+" Processo de nome "+nome+" criado no Host2 executando no Core1.%n");
            } 
            else if ( host2.getCore_Disp() == 2 )
            {
                entrada_execucao = formato.format(tempo_criacao);
                
                
                /** Cria o processo, adicionando ao arraylist de processos_criados. */
                processos_criados.add(new Processo( geraPID(), nome, tamanho, tempo_execucao, tempo_criacao, entrada_execucao,calculaTempoSaida(entrada_execucao,tempo_execucao), tempo_restante, getHost2(), "executando" ));

                /** core2 do host2 recebe o processo. */
                host2.setCore2(processos_criados.get(processos_criados.size() - 1));
                So.insereDadosArquivo(tempo_criacao+" Processo de nome "+nome+" criado no Host2 executando no Core2.%n");
            }
            /** Fim da verificação para host2. Caso não ache um core disponível em nenhum host, o processo é criado no arraylist de processos_criados e também é colocado no arraylist de fila_aptos. */
            else
            {           
                
                /** Cria o processo, adicionando ao arraylist de processos_criados. */
                processos_criados.add(new Processo( geraPID(), nome, tamanho, tempo_execucao, tempo_criacao, entrada_execucao, null, tempo_restante, null, "apto" ));
                

                /** O último processo criado é adicionado na fila de aptos. */
                fila_aptos.add(processos_criados.get(processos_criados.size() - 1));
                So.insereDadosArquivo(tempo_criacao+" Processo de nome "+nome+" criado e colocado na fila de aptos.%n");
            }                               
        }
        System.out.println("\t**Processo Criado aos "+formato.format(tempo_criacao)+"**");
    }

    /** Este método irá calcular o tempo de saída do processo criado.
     * 
     * @param entrada_execucao - tempo de criação do processo.
     * @param tempo_execucao - tempo de execução do processo.
     * @return tempo_saida - tempo de criação + tempo de execução.
     */
    public Date calculaTempoSaida(String entrada_execucao, String tempo_execucao)
    {
        /** separa_string_tempo_criacao receberá o valor do tempo_criacao, separado por ":". */
        String separa_string_entrada_execucao[] = entrada_execucao.split(":");
        Integer tempo_saida[] = new Integer[3];
        
        Calendar tempo_saida_exec = Calendar.getInstance();
        tempo_saida_exec.setTime(new Date());
        //SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
        //Date tempo_saida_exec = null; 
        
        
        /**
         * tempo_saida[0] - é refetente a hora hh.
         * tempo_saida[1] - é referente aos minutos mm.
         * tempo_saida[2] - é referente aos segundos ss.
         */       
        tempo_saida[2] = ( ( Integer.parseInt(separa_string_entrada_execucao[2]) ) + ( Integer.parseInt(tempo_execucao) ) );
        if ( tempo_saida[2] > 60 )
        {
            tempo_saida[1] = ( Integer.parseInt(separa_string_entrada_execucao[1] ) ) + 1;
            tempo_saida[2] -= 60;
            if ( tempo_saida[1] == 60 )
            {
                tempo_saida[0] = ( Integer.parseInt(separa_string_entrada_execucao[0] ) ) + 1;
                tempo_saida[1] = 0;
            }
            else
            {
                tempo_saida[0] = ( Integer.parseInt(separa_string_entrada_execucao[0] ) );
            }
        }
        else
        {
            tempo_saida[0] = Integer.parseInt(separa_string_entrada_execucao[0]);
            tempo_saida[1] = Integer.parseInt(separa_string_entrada_execucao[1]);
        }
        
        
        tempo_saida_exec.set(Calendar.HOUR_OF_DAY, tempo_saida[0]); //zerando as horas, minuots e segundos..
        tempo_saida_exec.set(Calendar.MINUTE, tempo_saida[1]);
        tempo_saida_exec.set(Calendar.SECOND, tempo_saida[2]);
        //formato.parse(tempo_saida[0].toString()+":"+tempo_saida[1].toString()+":"+tempo_saida[0].toString());

        return tempo_saida_exec.getTime();
        //return tempo_saida_exec;
    }
    
    /**
     * Método que mostrará a tabela de processos e suas informações/status.
     * Básicamente formado por printf's.
     */
    public void mostraTabelaPS()
    {
        int aux;
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
        
        if ( !processos_criados.isEmpty() )
        {
            So.insereDadosArquivo(formato.format(So.getTempoAtual())+" Mostra Tabela:%n");
            System.out.println("#########################################################################################################");
            System.out.println("#PID\t|NOME\t|TAMANHO\t|TEMPO CRIACAO\t|TEMPO EXEC.\t|TEMPO P/ SAÍDA\t|STATUS\t\t|HOST\t#");
            So.insereDadosArquivo("#########################################################################################################%n");
            So.insereDadosArquivo("#PID\t|NOME\t|TAMANHO\t|TEMPO CRIACAO\t|TEMPO EXEC.\t|TEMPO P/ SAÍDA\t|STATUS\t\t|HOST\t#%n");

            for ( aux = 0 ; aux < processos_criados.size() ; aux++ )
            {
                if ( processos_criados.get(aux).getStatus().equals("executando") )
                {
                    System.out.println("#"+processos_criados.get(aux).getPid()+"\t|"+processos_criados.get(aux).getNome()+"\t|"+processos_criados.get(aux).getTamanho()+"\t\t|"
                    +formato.format(processos_criados.get(aux).getTempo_criacao())+"\t|"+processos_criados.get(aux).getTempo_execucao()+"\t\t|"+formato.format(processos_criados.get(aux).getTempo_saida())+"\t|"
                    +processos_criados.get(aux).getStatus()+"\t|"+processos_criados.get(aux).getHost().getNome()+"\t#");                
                    if ( processos_criados.get(aux).getTempo_restante_execucao() != null )
                        System.out.println("resta: "+processos_criados.get(aux).getTempo_restante_execucao());
                    So.insereDadosArquivo("#"+processos_criados.get(aux).getPid()+"\t|"+processos_criados.get(aux).getNome()+"\t|"+processos_criados.get(aux).getTamanho()+"\t\t|"
                    +formato.format(processos_criados.get(aux).getTempo_criacao())+"\t|"+processos_criados.get(aux).getTempo_execucao()+"\t\t|"+formato.format(processos_criados.get(aux).getTempo_saida())
                    +"\t|"+processos_criados.get(aux).getStatus()+"\t|"+processos_criados.get(aux).getHost().getNome()+"\t#%n");                

                }
            }  
        }
        
        if ( !fila_aptos.isEmpty() )
        {
            for ( aux = 0 ; aux < fila_aptos.size() ; aux++ )
            {
                System.out.println("#"+processos_criados.get(aux).getPid()+"\t|"+processos_criados.get(aux).getNome()+"\t|"+processos_criados.get(aux).getTamanho()+"\t\t|"
                +formato.format(processos_criados.get(aux).getTempo_criacao())+"\t|"+processos_criados.get(aux).getTempo_execucao()+"\t\t|"+formato.format(processos_criados.get(aux).getTempo_saida())
                +"\t|"+processos_criados.get(aux).getStatus()+"\t|"+processos_criados.get(aux).getHost().getNome()+"\t#");                
                  
                So.insereDadosArquivo("#"+processos_criados.get(aux).getPid()+"\t|"+processos_criados.get(aux).getNome()+"\t|"+processos_criados.get(aux).getTamanho()+"\t\t|"
                +formato.format(processos_criados.get(aux).getTempo_criacao())+"\t|"+processos_criados.get(aux).getTempo_execucao()+"\t\t|"+formato.format(processos_criados.get(aux).getTempo_saida())+"\t|"
                +processos_criados.get(aux).getStatus()+"\t|"+processos_criados.get(aux).getHost()+"\t#%n");                

            }
        } 
        System.out.println("#########################################################################################################");
        System.out.println("#\tTempo Atual: "+formato.format(So.getTempoAtual())+"  #");
        System.out.println("################################");
          
        So.insereDadosArquivo("#########################################################################################################%n");
        So.insereDadosArquivo("#\tTempo Atual: "+formato.format(So.getTempoAtual())+"  #%n");
        So.insereDadosArquivo("################################%n");
        
        if ( ( processos_criados.isEmpty()) && ( fila_aptos.isEmpty()) )
        {
            System.out.println("Não há processo executando ou apto!");
            So.insereDadosArquivo(So.getTime()+" não foi criado nenhum processo.%n");
        }
        
    }
    
    /** Método que irá mostrar informações da memória de acordo com o parâmetro passado.
     * 
     * @param parametro - parâmetro informado para o método retorna o escopo da memória Física ou Virtual.
     */
    public void mostraMem(String parametro)
    {
        /** Se o parâmetro é "F" mostrará o escopo da memória FISICA. */
        if ( parametro.equals("F") )
        {
            memoria.escopo_f();
        } 
        /** Se o parâmetro é "V" mostrará o escopo da memória VIRTUAL. */
        else if ( parametro.equals("V") ) 
        {
            memoria.escopo_v();
        } 
        /** Se o parâmetro é "nd" mostrará somente a configuração da memória. */
        else if ( parametro.equals("nd") )
          {
                System.out.println("CONFIGURAÇÕES DE MEMORIA:");
                System.out.println("Pagina: "+memoria.getTam_pag()+" Bytes");
                System.out.println("Memória Física: "+memoria.getQtd_pag_f()+" Paginas.");
                System.out.println("Memória Virtual: "+memoria.getQtd_pag_v()+ "Paginas");
                
                So.insereDadosArquivo("CONFIGURAÇÕES DE MEMORIA:%n");
                So.insereDadosArquivo("Pagina: "+memoria.getTam_pag()+" Bytes.%n");
                So.insereDadosArquivo("Memória Física: "+memoria.getQtd_pag_f()+" Paginas.%n");
                So.insereDadosArquivo("Memória Virtual: "+memoria.getQtd_pag_v()+ "Paginas.%n");
          }
    }

    
    /**
     * Método que limpa os ArrayLists antes de usá-los (garantia).
     */
    public void limpaArraylist()
    {
        processos_criados.clear();
        fila_aptos.clear();
    }
}
