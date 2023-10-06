import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
public class Grafo {

    private int matGrafo[][];

    private int matCoords[][];

    private int vertices;

    private int arestas;

    private boolean direcionado = false;

    public int getVertices() {
        return vertices;
    }

    public void setVertices(int vertices) {
        this.vertices = vertices;
    }

    public int getArestas() {
        return arestas;
    }

    public void setArestas(int arestas) {
        this.arestas = arestas;
    }

    public boolean isDirecionado() {
        return direcionado;
    }

    public void setDirecionado(boolean direcionado) {
        this.direcionado = direcionado;
    }

    public void lerArquivo(String nomeArquivo){
        try {
            File arquivoGrafo = new File(nomeArquivo);
            Scanner e = new Scanner(arquivoGrafo);
            String direcionado = e.nextLine();
            if(direcionado.equals("nao")){
                this.direcionado = false;
                System.out.println("Não direcionado");
            }else{
                this.direcionado = true;
                System.out.println("Direcionado");
            }
            vertices = e.nextInt();
            matGrafo = new int[vertices][vertices];
            for(int i = 0; i<vertices;i++){
                for (int j = 0; j<vertices; j++){
                    matGrafo[i][j] = -1;
                }
            }
            matCoords = new int[2][vertices];
            for(int i = 0; i<vertices;i++){
                e.nextInt();
                matCoords[0][i] = e.nextInt();
                matCoords[1][i] = e.nextInt();
            }
            arestas = e.nextInt();
            for(int i = 0; i<arestas;i++){
                int origem = e.nextInt();
                int destino = e.nextInt();
                if(this.direcionado) {
                    matGrafo[origem][destino] = e.nextInt();

                }else{
                    int peso = e.nextInt();
                    matGrafo[origem][destino] = peso;
                    matGrafo[destino][origem] = peso;
                }
            }
            e.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERRO!");
            e.printStackTrace();
        }
    }

    public void exportaGrafo(){
        try {
            File arquivo = new File("src/grafo.txt");
            arquivo.createNewFile();
        } catch(IOException e) {
            System.out.println("Falha");
        }

        try {
            FileWriter arquivo = new FileWriter("src/grafo.txt");
            if(direcionado){
                arquivo.write("sim\n");
            }else{
                arquivo.write("nao\n");
            }
            arquivo.write(vertices + "\n");
            for(int i = 0; i < vertices; i++){
                arquivo.write(i+" "+matCoords[0][i]+" "+matCoords[1][i]+"\n");
            }
            arquivo.write(arestas+"\n");
            if(direcionado) {
                for (int i = 0; i < vertices; i++) {
                    for (int j = 0; j < vertices; j++) {
                        if (matGrafo[i][j] == -1) {
                            continue;
                        } else {
                            arquivo.write(i + " " + j + " " + matGrafo[i][j] + "\n");
                        }
                    }
                }
            }else{
                for (int i = 0; i < vertices; i++) {
                    for (int j = 0; j < vertices; j++) {
                        if (matGrafo[i][j] == -1 || j>i) {
                            continue;
                        } else {
                            arquivo.write(i + " " + j + " " + matGrafo[i][j] + "\n");
                        }
                    }
                }
            }
            arquivo.close();
        } catch(IOException e) {
            System.out.println("Falha");
        }
    }

    public void criaGrafoVazio(int tamanho, boolean direcionado){
        Scanner e = new Scanner(System.in);
        vertices = tamanho;
        matGrafo =  new int[vertices][vertices];
        matCoords = new int[2][vertices];
        arestas =  0;
        this.direcionado = direcionado;
        for(int i = 0; i < vertices; i++){
            System.out.println("Digite a coordenada x do vértice "+i+": ");
            matCoords[0][i] = e.nextInt();
            System.out.println("Digite a coordenada y do vértice "+i+": ");
            matCoords[1][i] = e.nextInt();
            for(int j = 0; j < vertices; j++){
                matGrafo[i][j] = -1;
            }
        }
    }

    public void imprimeGrafo(){
        for(int i = 0; i < vertices; i++){
            for (int j = 0; j < vertices; j++){
                if(matGrafo[i][j] == -1){
                    System.out.print("X ");
                }else{
                    System.out.print(matGrafo[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public int consultaAdj(int origem,int destino){
        return matGrafo[origem][destino];
    }

    public void removeAresta(int origem, int destino){
        if(origem >= vertices || destino >= vertices){
            System.out.println("Vértice inexistente!");
            return;
        }
        if(direcionado){
            matGrafo[origem][destino] = -1;
        }else{
            matGrafo[origem][destino] = -1;
            matGrafo[destino][origem] = -1;
        }
        arestas--;
    }

    public void insereAresta(int origem, int destino, int peso){
        if(origem >= vertices || destino >= vertices){
            System.out.println("Vértice inexistente!");
            return;
        }
        if(direcionado){
            matGrafo[origem][destino] = peso;
        }else{
            matGrafo[origem][destino] = peso;
            matGrafo[destino][origem] = peso;
        }
        arestas++;
    }

    public void alteraCoord(int vertice, int x, int y){
        if(vertice >= vertices){
            System.out.println("Vértice inexistente!");
            return;
        }
        matCoords[0][vertice] = x;
        matCoords[1][vertice] = y;
    }

    public int primeiroAdj(int vertice){
        if(vertice >= vertices){
            System.out.println("Vértice inexistente!");
            return -1;
        }
        for(int i = 0; i<vertices;i++){
            if(matGrafo[vertice][i] != -1){
                return i;
            }else{
                continue;
            }
        }
        return -1;
    }

    public int proxAdj(int origem,int destino){
        if(origem >= vertices || destino >= vertices){
            System.out.println("Vértice inexistente!");
            return -1;
        }
        for(int i = destino+1;i < vertices;i++){
            if(matGrafo[origem][i] != -1){
                return i;
            }else{
                continue;
            }
        }
        return -1;
    }

    public void listaAdj(int vertice){
        if(vertice >= vertices){
            System.out.println("Vértice inexistente!");
            return;
        }
        boolean achou = false;
        System.out.print("Adjacências: ");
        for(int i = 0; i < vertices ; i++){
            if(matGrafo[vertice][i] != -1){
                System.out.print(i + "\t");
                achou = true;
            }else{
                continue;
            }
        }
        if(!achou){
            System.out.print("Nenhuma adjacência encontrada!");
        }
        System.out.println();
    }
}
