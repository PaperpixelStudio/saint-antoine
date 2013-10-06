package be.relab.projection.core;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created with IntelliJ IDEA.
 * User: MacBookPro17
 * Date: 4/10/13
 * Time: 11:18
 * To change this template use File | Settings | File Templates.
 */
public class Attractor {

    PVector pos;
    float mass;
    float G;
    Attractor(float x, float y, float _r, float grav){
        pos = new PVector(x,y);
        mass=_r;
        G =grav;
    }

    PVector attract(Particle p){
        PVector force = PVector.sub(pos,p.location);
        float distance =  force.mag();
        distance = PApplet.constrain(distance,5,25);
        force.normalize();
        float strength= (G *mass*p.mass) / distance*distance;
        force.mult(strength);
        return force;
    }
    void display(){

        // stroke(150);
        // fill(150, 100);
  //    textAlign(CENTER);
//    textSize(10);
//    text("I'm a repeller", location.x,location.y);
        // ellipse(pos.x, pos.y, mass * 2, mass * 2);

    }
}
