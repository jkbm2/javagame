import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Ball extends Rectangle implements KeyListener{
    private int x, y, diameter;
    private Color color;
    private final int SPEED = 3;
    private boolean moving;
    private boolean inAir;
    private int deltaX, deltaY;

    public Ball(int x, int y, int diameter, Color color) {
        super(x - diameter / 2, y - diameter / 2, diameter, diameter);
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.color = color;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x - diameter / 2, y - diameter / 2, diameter, diameter);
        g.setColor(Color.magenta);
        g.drawRect(x - diameter / 2, y - diameter / 2, diameter, diameter);
    }

    public void jump() {
        
    }

    public int getXCoord() {
        return x;
    }

    public int getYCoord() {
        return y;
    }
    // Updates the position of the Bounds
    public Rectangle getBounds() {
        return new Rectangle(x - diameter / 2, y - diameter / 2, diameter, diameter);
    }

    public boolean onPlatform(Platform p) {
        if ((x-(diameter/2)) >= p.getXCoord() & (x+(diameter/2)) <= (p.getXCoord()+p.getW()) & (y+(diameter/2)) == p.getYCoord() ) {
            return true;
        } else {
            return false;
        }
    }

    public void updatePosition(ArrayList<Platform> platforms) {
        moving = false;
        int pointer = ((y + diameter) + GamePanel.HEADROOM) / (GamePanel.platformSpacing) - 1;

        if (canMoveHere(x + deltaX, y + deltaY, platforms.get(pointer))) {
            moving = true;
            moveX();
            
            y += deltaY; 
        }
        
        deltaX = 0;
        deltaY = 0;
        System.out.println(y);
        System.out.println("pointer: " + pointer);
    }
    // Collision detection handling
    public boolean canMoveHere(int x, int y, Platform p) {
        // Creates a virtual hitbox
        Rectangle nextPlace = new Rectangle(x - this.diameter / 2, y - this.diameter / 2, this.diameter, this.diameter);
        
        if (x <= ((diameter / 2)) || x >= (GamePanel.WIDTH - (diameter / 2)))
            return false;
        if (y <= (diameter / 2) || y >= (GamePanel.HEIGHT - (diameter / 2)))
            return false;
        return !nextPlace.intersects(p.getBounds());
    }

    public void moveX() {
        x +=deltaX;
        if (this.getBounds().x < 0) 
            x = x + diameter / 2;
        if (this.getBounds().y + diameter > GamePanel.WIDTH)
            x = GamePanel.WIDTH - diameter;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if ((keyCode == KeyEvent.VK_UP)) {
            deltaY -=SPEED;
        }

        if (keyCode == KeyEvent.VK_DOWN) {
            deltaY +=SPEED;
        }

        if (keyCode == KeyEvent.VK_LEFT) {
            deltaX -=SPEED;
        }
        
        if (keyCode == KeyEvent.VK_RIGHT) {
            deltaX +=SPEED;
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

}

