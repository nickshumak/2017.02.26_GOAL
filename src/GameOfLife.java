//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameOfLife {
    final String NAME_OF_GAME = "Game of life";
    final int START_LOCATION = 200;
    final int LIFE_SIZE = 50;
    final int POINT_RADIUS = 10;
    final int FIELD_SIZE = 507;
    final int BTN_PANEL_HEIGHT = 58;
    volatile boolean goNextGeneration = false;
    boolean[][] lifeGeneration = new boolean[50][50];
    boolean[][] nextGeneration = new boolean[50][50];
    int showDelay = 200;
    Random random = new Random();
    JFrame frame;
    GameOfLife.Canvas canvasPanel;

    public GameOfLife() {
    }

    public static void main(String[] args) {
        (new GameOfLife()).go();
    }

    void go() {
        this.frame = new JFrame("Game of life");
        this.frame.setDefaultCloseOperation(3);
        this.frame.setSize(507, 565);
        this.frame.setLocation(200, 200);
        this.frame.setResizable(false);
        this.canvasPanel = new GameOfLife.Canvas();
        this.canvasPanel.setBackground(Color.WHITE);
        JButton fillButton = new JButton("Fill");
        fillButton.addActionListener(new GameOfLife.FillButtonListener());
        JButton stepButton = new JButton("Step");
        stepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameOfLife.this.processOsLife();
                GameOfLife.this.canvasPanel.repaint();
            }
        });
        final JButton goButton = new JButton("Play");
        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent z) {
                GameOfLife.this.goNextGeneration = !GameOfLife.this.goNextGeneration;
                goButton.setText(GameOfLife.this.goNextGeneration?"Stop":"Play");
            }
        });
        JButton faster = new JButton("Faster");
        faster.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameOfLife.this.showDelay -= 50;
                if(GameOfLife.this.showDelay <= 0) {
                    GameOfLife.this.showDelay = 0;
                }

            }
        });
        JButton slower = new JButton("Slower");
        slower.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameOfLife.this.showDelay += 50;
            }
        });
        JButton DefaultSpeed = new JButton("Default");
        DefaultSpeed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameOfLife.this.showDelay = 200;
            }
        });
        JPanel btnPAnel = new JPanel();
        btnPAnel.add(fillButton);
        btnPAnel.add(stepButton);
        btnPAnel.add(goButton);
        btnPAnel.add(faster);
        btnPAnel.add(slower);
        btnPAnel.add(DefaultSpeed);
        this.frame.getContentPane().add("Center", this.canvasPanel);
        this.frame.getContentPane().add("South", btnPAnel);
        this.frame.setVisible(true);

        while(true) {
            while(!this.goNextGeneration) {
                ;
            }

            this.processOsLife();
            this.canvasPanel.repaint();

            try {
                Thread.sleep((long)this.showDelay);
            } catch (InterruptedException var9) {
                var9.printStackTrace();
            }
        }
    }

    int countNeighbors(int i, int j) {
        int count = 0;

        for(int ai = -1; ai < 2; ++ai) {
            for(int aj = -1; aj < 2; ++aj) {
                int nI = i + ai;
                int nJ = j + aj;
                nI = nI < 0?49:nI;
                nJ = nJ < 0?49:nJ;
                nI = nI > 49?0:nI;
                nJ = nJ > 49?0:nJ;
                count += this.lifeGeneration[nI][nJ]?1:0;
            }
        }

        if(this.lifeGeneration[i][j]) {
            --count;
        }

        return count;
    }

    void processOsLife() {
        int i;
        for(i = 0; i < 50; ++i) {
            for(int j = 0; j < 50; ++j) {
                int count = this.countNeighbors(i, j);
                this.nextGeneration[i][j] = this.lifeGeneration[i][j];
                this.nextGeneration[i][j] = count == 3?true:this.nextGeneration[i][j];
                this.nextGeneration[i][j] = count >= 2 && count <= 3?this.nextGeneration[i][j]:false;
            }
        }

        for(i = 0; i < 50; ++i) {
            System.arraycopy(this.nextGeneration[i], 0, this.lifeGeneration[i], 0, 50);
        }

    }

    public class Canvas extends JPanel {
        public Canvas() {
        }

        public void paint(Graphics graphics) {
            super.paint(graphics);

            for(int i = 0; i < 50; ++i) {
                for(int j = 0; j < 50; ++j) {
                    if(GameOfLife.this.lifeGeneration[i][j]) {
                        graphics.fillOval(i * 10, j * 10, 10, 10);
                    }
                }
            }

        }
    }

    public class FillButtonListener implements ActionListener {
        public FillButtonListener() {
        }

        public void actionPerformed(ActionEvent ev) {
            for(int x = 0; x < 50; ++x) {
                for(int y = 0; y < 50; ++y) {
                    GameOfLife.this.lifeGeneration[x][y] = GameOfLife.this.random.nextBoolean();
                }
            }

            GameOfLife.this.canvasPanel.repaint();
        }
    }
}
