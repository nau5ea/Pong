import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball {
	public double x;
	public double y;
	public double dx;
	public double dy;//this isn't obvious of purpose
	
	private double angle;
	
	private final double SPEED = 0.7;
	private final int WIDTH = 5;
	private final int HEIGHT = 5;//these should be more descriptive
	
	Ball() {//not given a scope classifier or a return type?
		this.x = Game.WIDTH/2;
		this.y = 40;
		//from this line to the end of the method can be extracted to a new method
		angle = new Random().nextInt(120 - 60) + 60 + 1;
		
		while(angle<110 && angle>70) {
			angle = new Random().nextInt(120 - 60) + 61;
		}
		
		this.dx = Math.cos(Math.toRadians(angle));
		this.dy = Math.sin(Math.toRadians(angle));
	}
	
	public void tick() {
		
		x +=  dx * SPEED;
		y += dy * SPEED;
		
		// Ball Outside	
		if(y >= Game.HEIGHT)
		{
			//Enemy location.
			System.out.println("Enemy's location!");
			new Game().start();
			return;
		} else if (y <= 0) {
			//Player location.
			System.out.println("Player's location!");
			new Game().start();
			return;
		}
		
		if (x <= 0 || x >= Game.WIDTH - WIDTH)//this operator might be obscure. WIDTH should have a more descriptive name
			dx *= -1;//Negate dx, reversing x velocity
		
		// Collisions
		Rectangle bounds = new Rectangle((int)x,(int)y,WIDTH,HEIGHT);//what does this represent
		Rectangle boundsPlayer = new Rectangle(Game.player.x, Game.player.y, Game.player.WIDTH, Game.player.HEIGHT);//Game.player should be saved with a better name, and a better method can be written to replace Ractangle's constructor that just accepts the player object and pulls out this data
		Rectangle boundsEnemy = new Rectangle((int)Game.enemy.x, Game.enemy.y, Game.player.WIDTH, Game.player.HEIGHT);//same for this
		
		if (bounds.intersects(boundsPlayer)) {//what is bounds in this case?
			angle = new Random().nextInt(120 - 60) + 61;
			
			while(angle < 110 && angle > 70) {
				angle = new Random().nextInt(120 - 60) + 61;
			}
			
			this.dx = Math.sin(Math.toRadians(angle));	
			this.dy = Math.cos(Math.toRadians(angle));	//this section seems familiar (lines 59 to here)
			
			if (dy > 0)//what if it's already negative? why can't we negate it?
				dy *= -1; 
		} else if (bounds.intersects(boundsEnemy)) {//the only thing that differs after the branch is the condition for negating the y-velocity
			angle = new Random().nextInt(120 - 60) + 61;
			
			while (angle < 110 && angle > 70) {
				angle = new Random().nextInt(120 - 60) + 61;
			}
			
			this.dx = Math.sin(Math.toRadians(angle));	
			this.dy = Math.cos(Math.toRadians(angle));	
			
			if (dy < 0)
				dy *= -1;
		}
		
	}
	
	public void render(Graphics g){
		tick();
		g.setColor(new Color(255, 255, 255));//has to be a better way to specify this color lol
		g.fillRect((int) x, (int) y, WIDTH, HEIGHT);
	}
	
}
