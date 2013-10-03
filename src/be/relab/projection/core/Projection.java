package be.relab.projection.core;

import processing.core.*;
import processing.net.*;
import processing.video.*;

// syphon
import codeanticode.syphon.*;
// audio

import ddf.minim.*;
import ddf.minim.analysis.*;
// utils
import java.net.ConnectException;
import java.util.*;


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

    PImage vitrail;
    PGraphics canvas;

    Minim minim;
    AudioInput input;
    FFT fft;

    public Grid grid;

    // control variables
    float soundThreshold = 2.0f;
    float partSizeModifier = 1.0f;
    // modifying origin coords after th -90° flip
    float oriX, partX = height/2;
    float oriY, partY = width;
    float gravMod = 0;
    // new coordinates
    // TODO attach these to controlP5

    float MARGIN_LEFT = 10;
    float COLONNE = 43;
    float RECT_WIDTH = 114;
    float RECT_HEIGHT = 157;
    float MARGIN_BOTTOM = 5;
    float VIT_POS_Y = 70;
    int LINES=5;
    PVector[] POSITIONS = new PVector[LINES*4];


    boolean displayParts = true;
    boolean displayMess = true;
    boolean animateWords=true;
    boolean animateLetters=true;
    boolean flip=false;

    boolean mustUpdateGrid=false;
    Client client;
    Word word;
    Word word2;


    Message message;
    LinkedList<Message> buffer;

    Movie movie;

    long lastUpdate;

    static public void main(String args[]){
        PApplet.main(new String[] {"be.relab.projection.core.Projection"});
    }

    public void setup(){
       size(1024, 576, OPENGL);

        // particles
        system = new System(this);

        // syphon (graphics+server)
        canvas = createGraphics(1024, 576, OPENGL);
        server = new SyphonServer(this, "Particles");

        // sound
        minim  = new Minim(this);
       // minim.debugOn();

        input = minim.getLineIn(minim.MONO, 512);
        fft = new FFT(512, 44100);
        fft.linAverages(30);

        // various & helpers
        vitrail = loadImage("masque.jpg");

        grid = new Grid();
        // controls
        cp5 = new ControlP5(this);
        cf = new ControlFrame(this, 400, 400, "Controls");

        client = new Client(this, "188.165.193.200",8080);

        mustUpdateGrid = false;
        // messages = new ArrayList<Message>();
        buffer  = new LinkedList<Message>();

         message = new Message(this, "Welcome to FAB W.");
        // font = createFont("ElectronicHighwaySign",190,true);
        font = createFont("Consolas- Bold",190,true);

        canvas.textMode(SHAPE);
    }

    public void draw(){
        canvas.textAlign(PConstants.CENTER);
        canvas.textFont(font);
        canvas.textSize(90);
        fft.forward(input.mix);

        blendMode(ADD);
        background(0);
        canvas.blendMode(ADD);
        canvas.beginDraw();
        canvas.background(0);
        // canvas.pushMatrix();
        canvas.rotate(-PI/2);
        canvas.translate(-height, 0);

        //*

        displayParticles(displayParts);
        // canvas.textSize(60);
        displayMessages();

        // grid.display(this);


        //canvas.scale(-1,1);
        canvas.endDraw();


        pushMatrix();
        rotate(-PI/2);
        translate(-height, 0);

        popMatrix();
        // image(vitrail, 0, 0);
        //tint(255, 200);
        if(movie != null && message == null){
            image(movie,0,0);
            server.sendImage(movie);
        }else{
            image(canvas, 0, 0);
            server.sendImage(canvas);
        }
        noTint();
        /*
        if(! client.active()){
            client = new Client(this, "188.165.193.200",14240);
       }
       */
    }
    public void mouseReleased(){
        // system.addRepeller(new Repeller(mouseX,mouseY,repSize));
        // repSize=0;

    }

    public void mousePressed(){
        println("mouse coord x : "+mouseX+" y: "+mouseY);
        println("translated coord x : "+map(mouseY,0,height,height,0)+" y: "+mouseX);

    }

    public void displayParticles(boolean dp){
        if(dp == true){
            for (int i=0;i<fft.avgSize();i++) {
                // le son modifie le vent
                float avg = fft.getAvg(i);
//                println(soundThreshold);
                if (avg < soundThreshold) {

                    wind.add(0, 2, 0);
                }
                else {
                    wind.add(0, fft.getAvg(i)*-.3f, 0);
                    //println(avg);
                }
            }
            gravity.y = gravMod;
            system.applyForce(wind);
            system.applyForce(gravity);
            system.applyRepellers();
            system.run();
            //*/
            wind.mult(0);
        }
    }

    public void movieEvent(Movie m) {
        m.read();

    }


    public void updatePositions(){
        mustUpdateGrid = true;

        /*
        POSITIONS = new PVector[LINES*4];
        for(int i = 0; i<POSITIONS.length; i++){
            // 4 = nombre de vitraux en x
            // why 10 ?!!!
            float x = MARGIN_LEFT+(RECT_WIDTH *4)+(COLONNE *4)-(10*4)+RECT_WIDTH /2;
            float y = VIT_POS_Y+(RECT_HEIGHT)+(RECT_HEIGHT *i)+5+RECT_HEIGHT-5;
            point(x,y);
            POSITIONS[i] = new PVector(x,y);
        }
        */
    }

    public void displayMessages(){
        if(! displayMess) return;
        if(message == null){
            message = buffer.pollFirst();
            if(message == null && movie==null){
                // message = new Message(this,"Fab - w.");
                int no = 1;//(int)random(1,4);
                movie = new Movie(this,"/Users/MacBookPro17/Documents/paperpixel/saint-antoine/src/data/ANTOINE"+no+".mov");
                movie.loop();

            }
        }else{
            movie = null;
            if(millis()-lastUpdate > 1000 )
            message.display();
            if(message.isDead()){

                message = null;
                lastUpdate = millis();
            }
        }

        if(mustUpdateGrid) mustUpdateGrid=false;
    }

    public void clientEvent(Client c){

        // le protocole: [type: 0(sms)-1(twitter)]$|>>[sender]$|>>[text]<<|$
        byte[] data = new byte[250];

        if(c.readBytesUntil(10,data) > 16){
            String str = new String(data);
            println(str);
            if(! str.contains("Welcome to RElab")){
                Message m = new Message(this, str);
                buffer.add(m);
            }else{
              //  println(str);
            }
        }
        data = null;
    }

    void disconnectEvent(Client c){
        // todo tester 5 fois la reconnexion toutes les 10 secondes
    }

    public void controlEvent(ControlEvent e){
        if(e.getId() == 0){
            updatePositions();
        }
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

}
