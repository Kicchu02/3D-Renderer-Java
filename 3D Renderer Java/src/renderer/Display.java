package renderer;

import renderer.entity.EntityManager;
import renderer.input.UserInput;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Display extends Canvas implements Runnable {
    
    private Thread thread;
    private final JFrame frame;
    private static final String title = "3D Renderer";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private static boolean running = false;
    
    private final EntityManager entityManager;
    
    private final UserInput userInput;
    
    public Display() {
        this.frame = new JFrame();
        
        this.frame.setTitle(title);
        Dimension size = new Dimension(WIDTH, HEIGHT);
        this.setPreferredSize(size);
        
        this.userInput = new UserInput();
        
        this.entityManager = new EntityManager();
        
        this.addMouseListener(this.userInput.mouse);
        this.addMouseMotionListener(this.userInput.mouse);
        this.addMouseWheelListener(this.userInput.mouse);
        this.addKeyListener(this.userInput.keyboard);
    }
    
    public synchronized void start() {
        running = true;
        this.thread = new Thread(this, "renderer.Display");
        this.thread.start();
    }
    
    public static void main(String[] args) {
        Display display = new Display();
        display.frame.add(display);
        display.frame.pack();
        display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.frame.setLocationRelativeTo(null);
        display.frame.setResizable(false);
        display.frame.setAlwaysOnTop(true);
        display.frame.setVisible(true);
        display.start();
    }
    
    public synchronized void stop() {
        running = false;
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60;
        double delta = 0;
        int frames = 0;
        
        this.entityManager.init(this.userInput);
        
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                delta--;
                render();
                frames++;
            }
            
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                this.frame.setTitle(title + " | " + frames + " fps");
                frames = 0;
            }
        }
        
        stop();
    }
    
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        this.entityManager.render(g);
        
        g.dispose();
        bs.show();
    }
    
    private void update() {
        this.entityManager.update();
    }
}
