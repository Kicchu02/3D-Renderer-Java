package renderer.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
    
    private final boolean[] keys = new boolean[66568];
    private boolean left, right, up, down, forward, backward;
    
    public void update() {
        this.left = keys[KeyEvent.VK_LEFT] || this.keys[KeyEvent.VK_A];
        this.right = keys[KeyEvent.VK_RIGHT] || this.keys[KeyEvent.VK_D];
        this.forward = keys[KeyEvent.VK_UP] || this.keys[KeyEvent.VK_W];
        this.backward = keys[KeyEvent.VK_DOWN] || this.keys[KeyEvent.VK_S];
        this.up = this.keys[KeyEvent.VK_SPACE];
        this.down = this.keys[KeyEvent.VK_SHIFT];
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    
    }
    
    @Override
    public void keyPressed(KeyEvent event) {
        keys[event.getKeyCode()] = true;
    }
    
    @Override
    public void keyReleased(KeyEvent event) {
        keys[event.getKeyCode()] = false;
    }
    
    public boolean isLeft() {
        return left;
    }
    
    public boolean isRight() {
        return right;
    }
    
    public boolean isUp() {
        return up;
    }
    
    public boolean isDown() {
        return down;
    }
    
    public boolean isForward() {
        return forward;
    }
    
    public boolean isBackward() {
        return backward;
    }
}
