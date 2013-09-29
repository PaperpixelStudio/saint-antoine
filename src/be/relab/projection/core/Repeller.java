package be.relab.projection.core;
import processing.core.*;


/**
 * Created with IntelliJ IDEA.
 * User: MacBookPro17
 * Date: 29/09/13
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
 */
public class Repeller{
    PVector location;
    float r = 50, strength=30;
    Repeller(float x, float y){
        location  = new PVector(x,y);
    }
    Repeller(int x, int y, int _r){
        location = new PVector(x,y);
        r = (float)_r;
    }
    Repeller(float x, float y, float _r){
        location = new PVector(x,y);
        r=_r;
    }

    PVector repel(Particle p){
        PVector dir = PVector.sub(location, p.location);
        float d = dir.mag();
        d= PApplet.constrain(d,1,r*4 +p.mass);
        dir.normalize();
        float force = -1 * (r*p.mass) * strength / (d*d);
        dir.mult(force);
        return dir;
    }


    void display(){
        /*
        stroke(150);
        fill(150, 100);
  //    textAlign(CENTER);
//    textSize(10);
//    text("I'm a repeller", location.x,location.y);
        ellipse(location.x,location.y,r*2,r*2);
        */
    }
}