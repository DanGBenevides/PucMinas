import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.File;

class Filme {
    private String Nome; 
    private String Titulo;
    private Date Data;
    private int Duracao;
    private String Genero;
    private String Idioma;
    private String Situacao;
    private float Orcamento;
    private String[] Chave;

    public Filme () {
      this.Nome = "";
      this.Titulo = "";
      this.Data = new Date();
      this.Duracao = 0;
      this.Genero = "";
      this.Idioma = "";
      this.Situacao = "";
      this.Orcamento = 0;
      this.Chave = new String[0];
    }

    //Set e Get nome
    private void setNome (String nome) {
        this.Nome = nome;
    }

    public String getNome () {
        return Nome;
    }

    //Set e Get Titulo original
    private void setTitulo (String titulo) {
        this.Titulo = titulo;
    }

    public String getTitulo() {
        return Titulo;
    }

    //Set e Get Data
    private void setData(String data) {
        Date date = null;

        if (data != null) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                date = format.parse(data);
            } 
            catch (ParseException e) {}
        }
        this.Data = date;
    }

    public static String formataData(Date date) {
        String resp = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            resp = sdf.format(date);
        } catch (Exception e) {

        }
        return resp;
    }

    public Date getData() {
        return Data;
    }

    //Set e Get Dura??o
    private void setDuracao(String duracao) {
        int tempo = 0, h = 0, m = 0;
        
        if (duracao.length() <= 2) {
            tempo = Character.getNumericValue(duracao.charAt(0));
        }
        else {
            for (int i = 0; i < duracao.length(); i++) {
                if (duracao.charAt(i) == 'h') {
                    h = Character.getNumericValue(duracao.charAt(i - 1));
                    h *= 60;
                }
                if (duracao.charAt(i) == 'm') {
                    m = Character.getNumericValue(duracao.charAt(i - 1));
                    if (duracao.charAt(i - 2) >= 48 && duracao.charAt(i - 2) <= 57) {
                        m = m + (Character.getNumericValue(duracao.charAt(i - 2)) * 10);
                    }
                }
            }
            tempo = h + m;
        }
        
        this.Duracao = tempo;
    }

    public int getDuracao () {
        return Duracao;
    }

    //Set e Get Genero
    private void setGenero (String genero) {
        this.Genero = genero;
    }

    public String getGenero () {
        return Genero;
    }

    //Set e Get Idioma
    public void setIdioma(String idioma) {
        Idioma = idioma;
    }

    public String getIdioma() {
        return Idioma;
    }

    //Set e Get Situa??o
    public void setSituacao(String situacao) {
        Situacao = situacao;
    }

    public String getSituacao() {
        return Situacao;
    }

    //Set e Get Or?amento
    public void setOrcamento(float orcamento) {
        Orcamento = orcamento;
    }

    public float getOrcamento() {
        return Orcamento;
    }

    //Set e Get Chave
    public void setChave(String[] chave) {
        this.Chave = chave;
    }

    public String[] getChave() {
        return Chave;
    }

    public static String formataChave(String[] chave) {
        String resp = "[";
        
        for (int i = 0; i < chave.length; i++) {
            resp += chave[i] + ", ";
        
        }
        resp = resp.replace(", null,", "");
        resp = resp.replace("null,", "");
        resp = resp.replace("null", "");
        resp = resp.trim();
        resp += "]";

        return resp;
    }



    public static String removeTags(String in) {
        String resp = "";
        for (int i = 0; i < in.length(); i++) {
            if(in.charAt(i) == '<') {
                i++;
                while(in.charAt(i) != '>') i++;
            }
            else {
                resp += in.charAt(i);
            }
        }
        return resp;
    }



    public void leitura (String arquivo) throws Exception{
        InputStreamReader arq = new InputStreamReader(new FileInputStream(arquivo));
        BufferedReader br = new BufferedReader(arq);
        String aux = "", resp = "";
        float orcamento = 0;
        String[] palavras_chave = new String[40];
        String[] chave_vazia = new String[0];
        int contador = 0;

        
        //Nome
        while (!aux.contains("h2 class")) { 
            aux = br.readLine();
        }
        aux = br.readLine();
        setNome(removeTags(aux).trim());


        //Data
        while (!aux.contains("span class=\"release\"")) {
            aux = br.readLine();
        }
        aux = br.readLine();
        aux = removeTags(aux).trim();
        
        for (int i = 0; i < 10; i++) {
            resp += aux.charAt(i);
        }
        setData(resp);


        //G?nero
        while (!aux.contains("genres")) { 
            aux = br.readLine();
        }
        br.readLine();
        aux = removeTags(br.readLine().trim());
        aux = aux.replace("&nbsp;", "");
        
        setGenero(aux.trim());


        //Dura??o
        while (!aux.contains("runtime")) {
            aux = br.readLine();
        }
        br.readLine();
        aux = br.readLine();
        setDuracao(aux.trim());


        //T?tulo Original
        resp = "";

        while (!aux.contains("Título original") && !aux.contains("bdi>Situação")) {
            aux = br.readLine();
        }
        
        if (aux.contains("Situação")) {
            setTitulo(getNome());
        }
        else {
            aux = removeTags(aux).trim();

            for (int i = 16; i < aux.length(); i++) {
                resp += aux.charAt(i);
            }

            setTitulo(resp.trim());
        }

        
        

        //Situa??o
        resp = "";

        while (!aux.contains("strong><bdi>Situa")) {
            aux = br.readLine();
        }
        aux = removeTags(aux.trim());

        for (int i = 8; i < aux.length(); i++) { 
            resp += aux.charAt(i);
        }
        setSituacao(resp.trim());
        

        //Idioma
        resp = "";

        while (!aux.contains("Idioma")) {
            aux = br.readLine();
        }
        aux = removeTags(aux.trim());

        for (int i = 15; i < aux.length(); i++) {
            resp += aux.charAt(i);
        }
        setIdioma (resp.trim());


        //Or?amento
        resp = "";

        while (!aux.contains("Orçamento")) {
            aux = br.readLine();
        }
        aux = removeTags(aux).trim();

        if (aux.contains("-")) {
            aux = aux.replace("-", "0.0");

            for (int i = 10; i < aux.length(); i++) {
                resp += aux.charAt(i);
            }
        }
        else {
            for (int i = 12; i < aux.length(); i++) {
                if (aux.charAt(i) == '.') {
                    i = aux.length();
                }
                else {
                    resp += aux.charAt(i);
                }
            }
        }
      
        resp = resp.replace("," , "");
        orcamento = Float.parseFloat(resp);
        setOrcamento(orcamento);


        //Palavras Chave
        resp = "";

        while (!aux.contains("Palavras-chave") && !aux.contains("Nenhuma")) {
            aux = br.readLine();
        }
        br.readLine();
        aux = br.readLine();
        if (aux.contains("Nenhuma")) {
            setChave(chave_vazia);
        }
        else {
            br.readLine();
            aux = br.readLine();

            while (!aux.contains("</ul>")) {
                if (aux.contains("li")) {
                    resp = removeTags(aux).trim();
                    palavras_chave[contador] = resp;
                
                    aux = br.readLine();

                    contador++;
                }
                else {
                    aux = br.readLine();
                }
            }

            setChave(palavras_chave);
        }
        

        arq.close();
        br.close();
    }

    public  void imprimir () {
        String resp = "";
        
        String Nome = getNome(); 
        String Titulo = getTitulo(); 
        String Data = formataData(getData()); 
        String Duracao = Integer.toString(getDuracao()); 
        String Genero = getGenero(); 
        String Idioma = getIdioma(); 
        String Situacao = getSituacao(); 
        String Orcamento = Float.toString(getOrcamento()); 
        String Chave = formataChave(getChave());
        
        resp += Nome + " " + Titulo + " " + Data + " " + Duracao + " " + Genero + " " + Idioma + " " + Situacao + " " + Orcamento + " " + Chave;
        

        MyIO.println(resp);
    }
}


class Hash {
    int comparacoes;
    int tabela[];
    int m1, m2, m, reserva;
    final int NULO = -1;
    //Set< String> itensInseridos = new HashSet<String>();

    public Hash() {
        this(21, 9);
    }

    public Hash(int m1, int m2) {
        this.m1 = m1;
        this.m2 = m2;
        this.m = m1 + m2;
        this.tabela = new int[this.m];
        for (int i = 0; i < m1; i++) {
           tabela[i] = NULO;
        }
        reserva = 0;
    }

    public int h(String elemento) {
        int resp = 0;
        for (int i = 0; i < elemento.length(); i++) {
            resp += (int) elemento.charAt(i);
        }
        return resp % m1;
    }

    public int h(int elemento) {
        return elemento % m1;
    }

    public boolean inserir(int elemento) {
        boolean resp = false;
        if (elemento != NULO) {
           int pos = h(elemento);
           if (tabela[pos] == NULO) {
                tabela[pos] = elemento;
                resp = true;
            } else if (reserva < m2) {
                tabela[m1 + reserva] = elemento;
                reserva++;
                resp = true;
                comparacoes++;
            }
        }
        return resp;
    }

    public boolean pesquisar(int elemento) {
        boolean resp = false;
        int pos = h(elemento);
        //MyIO.println("Posicao: " + pos);
        if (tabela[pos] == elemento) {
            resp = true;
            MyIO.println("Posicao: " + pos);
            comparacoes++;
        }  else if (tabela[pos] != NULO) {
            for (int i = 0; i < reserva; i++) {
                if (tabela[m1 + i] == elemento) {
                    resp = true;
                    i = reserva;
                }
            }
        }
        return resp;
    }

    public void matricula (long tempo) throws Exception {
        File file = new File("matricula_hashReserva.txt");
        FileWriter fw = new FileWriter(file);

        fw.write("753511" + "\t" + tempo + "\t" + comparacoes);
        fw.close();
    }
}


public class TP04Q05 {
    public static void main(String[] args) throws Exception{
        MyIO.setCharset("UTF-8");
        Filme filme;
        Hash hash = new Hash();
        String arquivo = "";
        String path = "/tmp/filmes/";
        String pesquisa = "";
        String titulo = "";
        int transformado = 0;
        long tempoInicial = System.currentTimeMillis();

        while (isFim(arquivo) == false) {
            arquivo = MyIO.readLine();
            if (isFim(arquivo) == false) {
                arquivo = path + arquivo;

                filme = new Filme();
                filme.leitura(arquivo);
                titulo = filme.getTitulo();
                transformado = hash.h(titulo);
                transformado = hash.h(transformado);
                hash.inserir(transformado);
                transformado = 0;
            }
        }

        while (isFim(pesquisa) == false) {
            pesquisa = MyIO.readLine();
            if (isFim(pesquisa) == false) {
                MyIO.println("=> " + pesquisa);
                transformado = hash.h(pesquisa);
                transformado = hash.h(transformado);
                if (hash.pesquisar(transformado) == false) {
                    MyIO.println("NAO");
                }
            }  
        }

        long tempoFinal = System.currentTimeMillis();
        long tempo = tempoFinal - tempoInicial;
        hash.matricula(tempo);
    }
        

    public static boolean isFim(String fim) {
        return (fim.length() == 3 && fim.charAt(0) == 'F' && fim.charAt(1) == 'I' && fim.charAt(2) == 'M');
    }
}
