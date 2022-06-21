import java.util.Date;
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

    //Set e Get Dura��o
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

    //Set e Get Situa��o
    public void setSituacao(String situacao) {
        Situacao = situacao;
    }

    public String getSituacao() {
        return Situacao;
    }

    //Set e Get Or�amento
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


        //G�nero
        while (!aux.contains("genres")) { 
            aux = br.readLine();
        }
        br.readLine();
        aux = removeTags(br.readLine().trim());
        aux = aux.replace("&nbsp;", "");
        
        setGenero(aux.trim());


        //Dura��o
        while (!aux.contains("runtime")) {
            aux = br.readLine();
        }
        br.readLine();
        aux = br.readLine();
        setDuracao(aux.trim());


        //T�tulo Original
        resp = "";

        while (!aux.contains("T�tulo original") && !aux.contains("bdi>Situa��o")) {
            aux = br.readLine();
        }
        
        if (aux.contains("Situa��o")) {
            setTitulo(getNome());
        }
        else {
            aux = removeTags(aux).trim();

            for (int i = 16; i < aux.length(); i++) {
                resp += aux.charAt(i);
            }

            setTitulo(resp.trim());
        }

        
        

        //Situa��o
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


        //Or�amento
        resp = "";

        while (!aux.contains("Or�amento")) {
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


class No {
    public Filme elemento;
    public No esq, dir;

    public No(Filme elemento) {
        this(elemento, null, null);
    }

    public No (Filme elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

class ArvoreBinaria {
    private int comparacoes;
    private No raiz;

    public ArvoreBinaria() {
        raiz = null;
    }

    public boolean pesquisar(String x) {
        MyIO.print("=>raiz ");
        return pesquisar(x, raiz);
    }

    private boolean pesquisar(String x, No i) {
        boolean resp;

        if (i == null) {
            resp = false;
        }
        else if (x.compareTo(i.elemento.getTitulo()) == 0) {
            resp = true;
        }
        else if (x.compareTo(i.elemento.getTitulo()) < 0) {
            MyIO.print("esq ");
            resp = pesquisar(x, i.esq);
        }
        else {
            MyIO.print("dir ");
            resp = pesquisar(x, i.dir);
        }
        return resp;
    }

    public void inserir(Filme x) throws Exception {
        raiz = inserir(x, raiz);
    }

    private No inserir(Filme x, No i) throws Exception {
		if (i == null) {
            i = new No(x);

        } else if (x.getTitulo().compareTo(i.elemento.getTitulo()) < 0) {
            i.esq = inserir(x, i.esq);
            comparacoes++;
        } else if (x.getTitulo().compareTo(i.elemento.getTitulo()) > 0) {
            i.dir = inserir(x, i.dir);
            comparacoes++;
        } else {
            throw new Exception("Erro ao inserir!");
        }

		return i;
	}

    public void remover(String x) throws Exception {
		raiz = remover(x, raiz);
	}

    private No remover(String x, No i) throws Exception {

		if (i == null) {
            throw new Exception("Erro ao remover!");

        } else if (x.compareTo(i.elemento.getTitulo()) < 0) {
            i.esq = remover(x, i.esq);
            comparacoes++;
        } else if (x.compareTo(i.elemento.getTitulo()) > 0) {
            i.dir = remover(x, i.dir);
            comparacoes++;
        // Sem no a direita.
        } else if (i.dir == null) {
            i = i.esq;

        // Sem no a esquerda.
        } else if (i.esq == null) {
            i = i.dir;

        // No a esquerda e no a direita.
        } else {
            i.esq = maiorEsq(i, i.esq);
		}

		return i;
	}

    private No maiorEsq(No i, No j) {
        // Encontrou o maximo da subarvore esquerda.
        if (j.dir == null) {
            i.elemento = j.elemento; // Substitui i por j.
            j = j.esq; // Substitui j por j.ESQ.
        // Existe no a direita.
        } else {
            // Caminha para direita.
            j.dir = maiorEsq(i, j.dir);
        }
        
        return j;
      }


    public void caminharCentral() {
		System.out.print("[ ");
		caminharCentral(raiz);
		System.out.println("]");
	}

    private void caminharCentral(No i) {
		if (i != null) {
			caminharCentral(i.esq); // Elementos da esquerda.
            i.elemento.imprimir();
			caminharCentral(i.dir); // Elementos da direita.
		}
	}


    
    public void matricula (long tempo) throws Exception {
        File file = new File("matricula_arvoreBinaria.txt");
        FileWriter fw = new FileWriter(file);

        fw.write("753511" + "\t" + tempo + "\t" + comparacoes);
        fw.close();
    }
}


public class TP04Q01 {
    public static void main(String[] args) throws Exception{
        MyIO.setCharset("UTF-8");
        Filme filme;
        ArvoreBinaria arvore = new ArvoreBinaria();
        String arquivo = "";
        String path = "/tmp/filmes/";
        String comando = "";
        String resp = "";
        String pesquisa = "";
        int n = 0;
        long tempoInicial = System.currentTimeMillis();

        while (isFim(arquivo) == false) {
            arquivo = MyIO.readLine();
            if (isFim(arquivo) == false) {
                arquivo = path + arquivo;

                filme = new Filme();
                filme.leitura(arquivo);
                arvore.inserir(filme);
            }
        }

        n = MyIO.readInt();

        for (int i = 0; i < n; i++) {
            comando = MyIO.readLine();

            if (comando.charAt(0) == 'I') {
                resp = comando.substring(2);

                filme = new Filme();
                resp = path + resp;
                filme.leitura(resp);
                arvore.inserir(filme);
            }

            else if (comando.charAt(0) == 'R') {
                resp = comando.substring(2);
                arvore.remover(resp);
            }
        }

        while (isFim(pesquisa) == false) {
            pesquisa = MyIO.readLine();
            if (isFim(pesquisa) == false) {
                MyIO.println(pesquisa);
                if (arvore.pesquisar(pesquisa) == true) {
                    MyIO.println("SIM");
                }
                else {
                    MyIO.println("NAO");
                }
            }
        }


        long tempoFinal = System.currentTimeMillis();
        long tempo = tempoFinal - tempoInicial;
        arvore.matricula(tempo);
    }
        

    public static boolean isFim(String fim) {
        return (fim.length() == 3 && fim.charAt(0) == 'F' && fim.charAt(1) == 'I' && fim.charAt(2) == 'M');
    }
}
