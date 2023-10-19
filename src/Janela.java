import javax.swing.*;
import java.awt.*;

public class Janela extends JPanel{

    Grafo grafo;

    static final int altura = 100;

    static final int largura = 100;

    static final int escala = 5;

    public Janela(Grafo grafo){
        this.grafo = grafo;
        JFrame frame = new JFrame();
        frame.add(this);
        frame.setSize(largura*escala,altura*escala);
        frame.setTitle("Grafo");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g){
        grafo.desenhaAresta(g);
        grafo.desehnhaGrafo(g);
    }
}
