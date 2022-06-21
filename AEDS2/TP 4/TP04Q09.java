import java.util.Scanner;

class No {
    public char elemento;
    public No esq, dir;

    public No(char elemento) {
        this(elemento, null, null);
    }

    public No(char elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

class ArvoreBinaria {
    private No raiz;

    public ArvoreBinaria() {
        raiz = null;
    }

    //Pesquisar
    public boolean pesquisar(char x) {
        return pesquisar(x, raiz);
    }

    private boolean pesquisar(char x, No i) {
        boolean resp;
        if (i == null) {
            resp = false;
        }
        else if (x == i.elemento) {
            resp = true;
        }
        else if (x < i.elemento) {
            resp = pesquisar(x, i.esq);
        }
        else {
            resp = pesquisar(x, i.dir);
        }
        return resp;
    }

    //In
    public void caminharCentral() {
        caminharCentral(raiz);
    }

    private void caminharCentral(No i) {
        if (i != null) {
            caminharCentral(i.esq);
            MyIO.print(i.elemento + " ");
            caminharCentral(i.dir);
        }
    }

    //Pre
    public void caminharPre() {
        caminharPre(raiz);
    }

    private void caminharPre(No i) {
        if (i != null) {
            MyIO.print(i.elemento + " ");
            caminharPre(i.esq);
            caminharPre(i.dir);
        }
    }

    //Pos
    public void caminharPos() {
        caminharPos(raiz);
    }
    
    private void caminharPos(No i) {
        if (i != null) {
            caminharPos(i.esq);
            caminharPos(i.dir);
            MyIO.print(i.elemento + " ");
        }
    }

    //Inserir
    public void inserir(char x) throws Exception {
        raiz = inserir(x, raiz);
    }

    private No inserir(char x, No i) throws Exception {
        if (i == null) {
            i = new No(x);
        }
        else if (x < i.elemento) {
            i.esq = inserir(x, i.esq);
        }
        else if (x > i.elemento) {
            i.dir = inserir(x, i.dir);
        }
        else {
            throw new Exception("Erro ao inserir!");
        }
        return i;
    }
}



class TP04Q09 {
    public static void main(String[] args) throws Exception{
        ArvoreBinaria arvore = new ArvoreBinaria();
        Scanner sc = new Scanner(System.in);
        String comando = "";
        char n;

        while (sc.hasNext()) {
            comando = sc.nextLine();

            if (comando.charAt(0) == 'I' && comando.length() < 4) {
                n = comando.charAt(2);
                arvore.inserir(n);
            }
            else if (comando.charAt(0) == 'P' && comando.length() < 4) {
                n = comando.charAt(2);
                if (arvore.pesquisar(n) == false) {
                    MyIO.println(n + " nao existe");
                }
                else {
                    MyIO.println(n + " existe");
                }
            }
            else if (comando.compareTo("INFIXA") == 0 ) {
                arvore.caminharCentral();
                MyIO.println("");
            }
            else if (comando.compareTo("PREFIXA") == 0 ) {
                arvore.caminharPre();
                MyIO.println("");
            }
            else if (comando.compareTo("POSFIXA") == 0) {
                arvore.caminharPos();
                MyIO.println("");
            }
        }
        sc.close();
    }
}