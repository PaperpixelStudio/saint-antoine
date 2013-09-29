package be.relab.projection.core;
import processing.core.*;

import java.util.Random;
/**
 * Created with IntelliJ IDEA.
 * User: MacBookPro17
 * Date: 29/09/13
 * Time: 14:05
 * To change this template use File | Settings | File Templates.
 */
class Particle {
    float mass, lifespan;
    PVector acceleration, velocity, location;
    Random generator = new Random();
    String letter;
    Projection parent;
    char character;
    float sizeModifier = 1;
    Particle(PVector l) {
        location = l.get();
        // letter = letters[int(random(letters.length))];

        pInit();
    }
    Particle(PVector l, float modifier, Projection p) {
        this.sizeModifier = modifier;
        this.location = l.get();
        parent =p;
        pInit();
    }

    void pInit() {
        character = PApplet.parseChar((int) parent.random(97, 122));
        acceleration = new PVector();
        float vx = (float) generator.nextGaussian()*1.8f;
        float vy = (float) generator.nextGaussian()*.4f-1.0f;
        velocity = new PVector(vx, vy);

        mass=parent.random(40, 120);
        mass *= sizeModifier;
        lifespan = parent.random(270);
    }

    void applyForce(PVector force) {
        PVector f = PVector.div(force, mass);
        acceleration.add(f);
    }
    void run() {
        //checkEdges();
        update();
        //  display();
        render();
    }
    void update() {

        velocity.add(acceleration);
        location.add(velocity);
        acceleration.mult(0);
        lifespan -= 1;
    }
    /*
    void checkEdges() {
        if (location.x >= width) {
            location.x = width;
            velocity.x *= -1;
        }
        else if (location.x <=0) {
            location.x = 0;
            velocity.x *=-1;
        }
        if (location.y > height) {
            location.y=height;
            velocity.y *=-1;
        }
        else if (location.y < 0) {
            location.y = 0;
            velocity.y *=-1;
        }
    }
    */
    void display() {
        //stroke(0, lifespan);
        parent.noStroke();

        // fill(175, lifespan);
        parent.colorMode(PConstants.HSB);
        parent.fill(parent.frameCount%255, parent.frameCount%100, 150);
        parent.ellipse(location.x, location.y, (mass*.1f)*sizeModifier, (mass*.1f)*sizeModifier);
        parent.colorMode(PConstants.RGB);
    }

    void render() {
        parent.canvas.noStroke();
        parent.canvas.textAlign(PConstants.CENTER);
        parent.canvas.fill(255);
        parent.canvas.colorMode(PConstants.HSB);
        parent.canvas.textSize(mass);
        parent.canvas.pushMatrix();
        parent.canvas.translate(location.x,location.y);
        parent.canvas.rotate(parent.radians(lifespan));

        parent.canvas.text(character, 0, 0);
        parent.canvas.popMatrix();
        parent.canvas.colorMode(PConstants.RGB);
    }
    void addToCanvas(PGraphics c) {
        c.noStroke();
        c.fill(175, lifespan);
        c.ellipse(location.x, location.y, mass*.1f, mass*.1f);
    }
    boolean isDead() {
        return (lifespan < 0.0);
    }
}

