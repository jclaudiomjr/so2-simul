package so;

import java.util.Date;
import java.util.Calendar;

/**
 * 
 * @author JUNIOR
 */
public class Processo {
    
    private final Integer pid;      /** pid receberá o pid gerado pelo 'geraPID()'. */    
    private final String nome;      /** nome receberá o nome do processo informado pelo usuário. */
    private String tempo_execucao;        /** tempo_execução receberá o tempo de execução do processo, que é informado eplo usuário. */
    private final String tamanho;       /** tamanho receberá o tamanho do processo, que é informado pelo usuário, */    
    private final Date tempo_criacao;     /** tempo_criação receberá o tempo de criação, gerado pelo 'getTime()'. */
    private String tempo_entrada_execucao;      /** tempo_entrada_execucao receberá o tempo em que o processo entra em execução. */
    //private Integer tempo_saida[] = new Integer[3];     /** tempo_saida[] receberá o tempo em que o processo deve sair do processador */
    private Date tempo_saida;
    private Integer tempo_restante_execucao;        /** Controla o tempo restante que um processo tem para executar. Esta variável é utilizada apenas quando o escalonador usa quantum. */
    private Host host;        /** host receberá o host que o processo será executado. */
    private String status;      /** status receberá o status do processo. */

    /** Construtor da classe Processo.
     * @param pid - pid do processo.
     * @param nome - nome do processo.   
     * @param tamanho - tamanho do processo.
     * @param tempo_execucao - tempo de execução do processo.
     * @param tempo_criacao - tempo de criação do processo.
     * @param tempo_entrada_execucao - tempo de entrada em execução do processo.
     * @param tempo_saida - tempo para saída do processo.
     * @param tempo_restante_execucao - tempo restante que o processo tem para executar (caso houver quantum)
     * @param host - host do processo.
     * @param status - status do processo.
     */
    public Processo(Integer pid, String nome, String tamanho, String tempo_execucao, Date tempo_criacao, String tempo_entrada_execucao, Date tempo_saida, Integer tempo_restante_execucao,Host host, String status) 
    {
        this.pid = pid;
        this.nome = nome;
        this.tamanho = tamanho;
        this.tempo_execucao = tempo_execucao;
        this.tempo_criacao = tempo_criacao;
        this.tempo_entrada_execucao = tempo_entrada_execucao;
        this.tempo_saida = tempo_saida;
        this.tempo_restante_execucao = tempo_restante_execucao;
        this.host = host;
        this.status = status;
    }
   
    /**
     * setter para o atributo status.
     * @param status - status do processo.
     */
    public void setStatus(String status) 
    {
        this.status = status;
    }
    
    /**
     * getter para o pid do processo.
     * @return pid - pid do processo.
     */
    public Integer getPid() 
    {
        return pid;
    }
    
    /**
     * getter para o nome do processo.
     * @return nome - nome do processo.
     */
    public String getNome() 
    {
        return nome;
    }
    
    /**
     * Seta o tempo de execução do processo.
     * @param tempo_execucao - tempo de execução do processo.
     */
    public void setTempo_execucao(String tempo_execucao)
    {
        this.tempo_execucao = tempo_execucao;
    }
    
    /**
     * getter para o tempo de execução do processo.
     * @return tempo_execucao - tempo de execução do processo.
     */
    public String getTempo_execucao() 
    {
        return tempo_execucao;
    }
    
    /**
     * getter para o tamanho do processo.
     * @return tamanho - tamanho do processo (Bytes).
     */
    public String getTamanho() 
    {
        return tamanho;
    }
    
    /**
     * getter para o tempo de criação do processo.
     * @return tempo_criacao - tempo de criação do processo.
     */
    public Date getTempo_criacao() 
    {
        return tempo_criacao;
    }
    
    /**
     * getter para o tempo em que o processo deve sair do processador. Método usado para fazer comparações de quando ele deve sair do processador.
     * @param indice - indice para o array. Sendo indice 0(zero) para hora(hh), indice 1 para minutos(mm) e indice 2 para segundos(ss).
     * @return tempo_saida - tempo de saída que o processo deve desocupar o processador de acordo com o indice informado.
     */
    public Date getTempo_saida()
    {
       
       
        return tempo_saida;
    }
    
    public void setTempo_saida(Date tempo_saida)
    {
        this.tempo_saida = tempo_saida; // REVISAR
    }
    /**
     * getter para obter o host do processo.
     * @return host - host em qual o processo está executando.
     */
    public Host getHost() 
    {
        return host;
    }
    
    public void setHost(Host host)
    {
        this.host = host;
    }
    /**
     * getter para o status do processo (executando, finalizado, apto).
     * @return status - status atual do processo.
     */
    public String getStatus() 
    {
        return status;
    }
    
    /**
     * Setter para o tempo de entrada em execução do proceso.
     * @param entrada_execucao - tempo de entrada em execução do processo.
     */
    public void setTempo_entrada_execucao(String entrada_execucao)
    {
        this.tempo_entrada_execucao = entrada_execucao;
    }
    
    /**
     * Retorna o tempo de entrada em execução.
     * @return tempo_entrada_execucao - tempo de entrada em execução.
     */
    public String getTempo_entrada_execucao()
    {
        return tempo_entrada_execucao;
    }
    
    public void setTempo_restante_execucao(Integer tempo_restante)
    {
        this.tempo_restante_execucao -= tempo_restante;
    }
    
    public Integer getTempo_restante_execucao()
    {
        return tempo_restante_execucao;
    }
    
   
}
