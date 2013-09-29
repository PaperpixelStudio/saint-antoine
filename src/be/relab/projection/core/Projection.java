package be.relab.projection.core;

import processing.core.*;
import processing.net.*;
// syphon
import codeanticode.syphon.*;
// audio

import ddf.minim.*;
import ddf.minim.analysis.*;
// utils
import java.util.Iterator;
import java.util.Random;


// control
import controlP5.*;
import be.relab.projection.controls.*;





public final class Projection extends PApplet {
    ControlP5 cp5;
    ControlFrame cf;

    PFont font;
    System system;

    SyphonServer server;

    float repSize = 0;
    PVector gravity = new PVector(0, .1f);
    PVector wind = new PVector(0, 40);


    String[]  letters = {
            "@", "a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "l", "m", "n"
    };
    PImage vitrail;
    PGraphics canvas;

    Minim minim;
    AudioInput input;
    FFT fft;

    public Grid grid;

    float mediumheights=0;

    // control variables
    float soundThreshold = 2.0f;
    float partSizeModifier = 1.0f;
    // modifying origin coords after th -90° flip
    float oriX, partX = height/2;
    float oriY, partY = width;
    float gravMod = 0;

    float marginLeft = 10;
    float colonne = 43;
    float vitRectWidth = 114;
    float vitRectHeight = 157;


    Client client;
    Word word;

    static public void main(String args[]){
        PApplet.main(new String[] {"be.relab.projection.core.Projection"});
    }

    public void setup(){
        size(1024, 576, P3D);

        // particles
        system = new System(this);

        // syphon (graphics+server)
        canvas = createGraphics(1024, 576, P3D);
        server = new SyphonServer(this, "Particles");

        // sound
        minim  = new Minim(this);
        input = minim.getLineIn(minim.STEREO, 512);
        fft = new FFT(512, 44100);
        fft.linAverages(30);

        // various & helpers
        vitrail = loadImage("vitrail.png");

        grid = new Grid();
        // controls
        cp5 = new ControlP5(this);
        cf = new ControlFrame(this, 400, 400, "Controls");

        // client = new Client(this, "188.165.193.200",14240);

        word = new Word(this, "test",1);
        font = createFont("ElectronicHighwaySign",200,true);


    }

    public void draw(){
        canvas.textFont(font);
        fft.forward(input.mix);
        for (int i=0;i<fft.avgSize();i++) {
            // le son modifie le vent
            float avg = fft.getAvg(i);
            if (avg < soundThreshold) {
                wind.add(0, 2, 0);
            }
            else {
                wind.add(0, fft.getAvg(i)*-.3f, 0);
                //println(avg);
            }
        }
        //text(frameRate,10,10);

        // println("max : "+max+" min: "+min);
        blendMode(ADD);
        background(0);
        canvas.blendMode(ADD);
        canvas.beginDraw();
        canvas.background(0, 100);
        // canvas.pushMatrix();
        canvas.rotate(-PI/2);
        canvas.translate(-height, 0);
        //*

        gravity.y = gravMod;
        system.applyForce(wind);
        system.applyForce(gravity);
        system.applyRepellers();
        system.run();
        //*/
        if (mousePressed) repSize++;
        wind.mult(0);
        canvas.textSize(60);


        word.display();
        // grid.display(this);
        //canvas.popMatrix();
        //canvas.rotate(-PI/2);
        //canvas.translate(-height, 0);
        canvas.endDraw();
        server.sendImage(canvas);

        pushMatrix();
        rotate(-PI/2);
        translate(-height, 0);
        image(vitrail, 0, 0);
        popMatrix();
        //tint(255, 200);
        image(canvas, 0, 0);
        noTint();
    }


    public void keyPressed() {
        if(key == CODED){
            switch(keyCode){
                case UP:{
                    gravity.y += 1;
                    println(gravity.y);
                    break;
                }
                case DOWN:{
                    gravity.y -=1;
                    break;
                }
                case LEFT:{
                    wind.y+=1;
                    break;
                }
                case RIGHT:{
                    wind.y-=1;
                    break;
                }
            }
        }else{
            switch(key){
                case ' ':{
                    noLoop();
                    system = null;
                    system = new System(this);
                    loop();
                    break;
                }
                case 'p':{
                    noLoop();
                }
                case 'r':{
                    loop();
                }
            }
        }
    }

    public void mouseReleased(){
        // system.addRepeller(new Repeller(mouseX,mouseY,repSize));
        // repSize=0;

    }

    public void mousePressed(){
        println("mouse coord x : "+mouseX+" y: "+mouseY);
        println("translated coord x : "+map(mouseY,0,height,height,0)+" y: "+mouseX);

    }
    public Projection getContext(){
        return this;
    }

    public void clientEvent(Client c){
        String data;
        data = c.readStringUntil(new String("%").charAt(0));
        println(data);
    }

}
