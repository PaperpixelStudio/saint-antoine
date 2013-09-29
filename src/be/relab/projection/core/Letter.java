package be.relab.projection.core;

import be.relab.projection.animation.Animable;
import be.relab.projection.animation.Animation;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created with IntelliJ IDEA.
 * User: MacBookPro17
 * Date: 29/09/13
 * Time: 20:00
 * To change this template use File | Settings | File Templates.
 */
public class Letter implements Animable {

    PVector position;
    float angle=0;
    String content;
    Animation animation;
    float size=190;

    Letter(String l){
        content = l;
    }

    Letter(char c){
        content = String.valueOf(c);
    }

    public void display(Projection p){
        animate();

        p.canvas.pushMatrix();
        p.canvas.translate(position.x, position.y);
        p.canvas.rotate(PApplet.radians(angle));

        p.canvas.strokeWeight(14);
        p.canvas.textFont(p.font);
        p.canvas.textSize(size);
        p.canvas.text(content,0,0);
        p.canvas.popMatrix();


    }

    @Override
    public void animate() {
        if(animation != null){
            animation.animate(this);
        }
    }

    @Override
    public void setAnimation(Animation a) {
       animation = a;

    }

    public void setPosition(PVector p){
        position = p.get();
    }

    public void setAngle(float a){
        angle = a;
    }
    public void setSize(float s){
        size = s;
    }
    public float getSize(){
        return size;
    }
}
