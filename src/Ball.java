import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Ball {
    private Vector2d center;
    private Vector2d drawPosition;
    
    private Vector2d velocity;
    private float mass;
    private float radius;
    public static int k = 10;
    public Vector2d initialPosition;
    public Vector2d initialVelocity;
    private float restitution = 1;
    private Color c = new Color(200,0,0);
    private static ArrayList<Color> colors = new ArrayList<>();
    
    public Ball(){
        colors.add(new Color(165, 14, 87));
        colors.add(new Color(219, 89, 152));
        colors.add(new Color(224, 112, 112));
        colors.add(new Color(206, 33, 33));
        colors.add(new Color(104, 175, 214));
        colors.add(new Color(134, 119, 214));
        colors.add(new Color(100, 219, 213)); 
        colors.add(new Color(237, 230, 104)); 
        
        velocity = new Vector2d(1,0);
    }
   
    
    public float getRadius(){
        return this.radius;
    }
   
    public Vector2d getCenter(){
        return this.center;
    }
   
    public Vector2d getVelocity(){
        return this.velocity;
    }
   
    public float getMass(){
        return this.mass;
    }
   
    public void setVelocity(Vector2d velocity){
        this.velocity = velocity;
    }
   
    public void setMass(float mass){
        this.mass = mass;
        this.radius = mass*k;
    }
   
    public void setCenter(Vector2d center){
    	drawPosition = new Vector2d(center.getX() - radius, center.getY() - radius);
        this.center = center;
    }
   
    public void setRadius(float radius){
        this.radius = radius;
    }
   
    public void setDrawPosition(Vector2d drawPosition){
    	this.drawPosition = drawPosition;
    	this.center = new Vector2d(drawPosition.getX() + radius, drawPosition.getY() + radius);
    }
    
    public void update(){
        this.setCenter(this.center.add(this.velocity));
    }
   
    public boolean detectCollision(Ball ball2){
    	Vector2d distanceVector = this.center.subtract(ball2.center);
        double distance = this.center.getDistance(ball2.center);
        if (distance <= this.radius + ball2.radius){
        	//SET NEW POSITION
        	distanceVector.subtract(this.center).subtract(ball2.center);
        	this.setCenter(distanceVector.add(ball2.center));
        	
            return true;
        }else{
            return false;
        }
    }
   
    public float getKineticEnergy(){
        float KE = (float) ((0.5)*this.mass*this.velocity.getLength()*this.velocity.getLength());
        return KE;
    }

    public float getMomentum(){
        float momentum = this.mass*this.velocity.getLength(); 
        return momentum;
    }
    
	public void draw(Graphics2D g) {
		g.setColor(c);
		
		g.fillOval(
				(int) (this.drawPosition.getX()), 
				(int) (this.drawPosition.getY()),
				(int) (2*this.radius), 
				(int) (2*this.radius)
				);
		
		
		//DRAW ARROW
		g.setColor(Color.BLACK);
		
		Vector2d pV = new Vector2d(velocity.getX()/velocity.getLength(),velocity.getY()/velocity.getLength()) ;
		Vector2d perpV = new Vector2d(-pV.getY(), pV.getX());
		g.drawLine((int) center.getX(), (int) center.getY(),  
				(int) (center.getX() + velocity.getX()*50), 
				(int) (center.getY() + velocity.getY()*50));
		g.fillPolygon(new int[] {(int) (center.getX() + ((velocity.getX())*50 + 5*pV.getX())),
								(int) (center.getX() + velocity.getX()*50 + 5*perpV.getX()), 
								(int) (center.getX() + velocity.getX()*50 - 5*perpV.getX())},
					new int[] {(int) (center.getY() +  ((velocity.getY())*50 + 5*pV.getY())), 
							(int) (center.getY() + velocity.getY()*50 + 5*perpV.getY()), 
							(int) (center.getY() + velocity.getY()*50 - 5*perpV.getY())},
					3);
		}


    public void performCollision(Ball ball)
    {


     Vector2d delta = (center.subtract(ball.center));
     float r = getRadius() + ball.getRadius();
     float dist2 = delta.dot(delta);

     if (dist2 > r*r) return;


     float d = delta.getLength();

     Vector2d mtd;
     if (d != 0.0f)
     {
      mtd = delta.multiply(((getRadius() + ball.getRadius())-d)/d); 

     }
     else 
     {
      d = ball.getRadius() + getRadius() - 1.0f;
      delta = new Vector2d(ball.getRadius() + getRadius(), 0.0f);

      mtd = delta.multiply(((getRadius() + ball.getRadius())-d)/d);
     }


     float im1 = 1 / getMass();
     float im2 = 1 / ball.getMass();


     center = center.add(mtd.multiply(im1 / (im1 + im2)));
     ball.center = ball.center.subtract(mtd.multiply(im2 / (im1 + im2)));

   
     Vector2d v = (this.velocity.subtract(ball.velocity));
     float vn = v.dot(mtd.normalize());

     
     if (vn > 0.0f) return;

    
     float i = (-(1.0f + restitution) * vn) / (im1 + im2);
     Vector2d impulse = mtd.multiply(i);

     this.velocity = this.velocity.add(impulse.multiply(im1));
     ball.velocity = ball.velocity.subtract(impulse.multiply(im2));

    }
    public boolean detectWallCollision(){
        float thisX = this.center.getX(); 
        float thisY = this.center.getY();
            if ((thisX <= this.radius)||(thisX >= 700-this.radius)||(thisY <= this.radius)||(thisY >= 400-this.radius)){
                return true; 
            }
        return false;
     }
    
    public static Color getColors(int x){
    	return colors.get(x);
    }
    
    public void setColor(Color c){
    	this.c = c;
    }
     
    public void performWallCollision(){
        float thisX = this.center.getX(); 
        float thisY = this.center.getY();
        if (thisX<this.radius){
           this.center.setX(this.radius);
           this.velocity.setX(-1*this.velocity.getX()); 
        }if (thisX>700-this.radius){
            this.center.setX(700-this.radius);
            this.velocity.setX(-1*this.velocity.getX());
        }
        if (thisY<this.radius){
            this.center.setY(this.radius);
            this.velocity.setY(-1*this.velocity.getY());
        }if (thisY>400-this.radius){
            this.center.setY(400-this.radius);
            this.velocity.setY(-1*this.velocity.getY());
        }
    }
    
    public boolean isInBall(int x, int y){
    	
    	boolean validX = false;
    	boolean validY = false;
    	//CHECKS X
    	if(x < (center.getX() + radius) && x > (center.getX() - radius)){
    		validX = true;
    	}
    	if(y < (center.getY() + radius) && y > (center.getY() - radius)){
    		validY = true;
    	}
    	
    	if(validX && validY){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
}