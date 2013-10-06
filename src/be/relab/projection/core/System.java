package be.relab.projection.core;
import java.util.ArrayList;
import java.util.Iterator;
import processing.core.*;
/**
 * Created with IntelliJ IDEA.
 * User: MacBookPro17
 * Date: 29/09/13
 * Time: 14:04
 * To change this template use File | Settings | File Templates.
 */

public class System {
    ArrayList<Particle> parts;
    ArrayList<Repeller> repellers;
    ArrayList<Attractor> attractors;
    ArrayList<PVector> locs = new ArrayList<PVector>();
    Projection parent;



    System(Projection p) {
        parent=p;
        init();
    }

    void init() {
        parts = new ArrayList<Particle>();
        repellers = new ArrayList<Repeller>();
        attractors = new ArrayList<Attractor>();

    }
    void run() {
        // parts.add(new Particle(new PVector(width/2, height/2)));
        //if(parts.size() < 1)
        parts.add(new Particle(new PVector(parent.partX, parent.partY), parent.partSizeModifier, parent));

        int i =0;
        Iterator<Particle> it = parts.iterator();
        while (it.hasNext ()) {
            Particle p = it.next();
            // if(i==0) println(p.location);
            p.run();
            if (p.isDead()) {
                it.remove();
            }
            //println("part. qty :"+parts.size());
            i++;
        }
        /*
        for (Repeller r:repellers) {
            r.display();
        }
        //*/
    }
    void applyForce(PVector f) {
        for (Particle p:parts) {
            p.applyForce(f);
        }
    }
    void applyRepellers() {
        for (Repeller r:repellers) {
            for (Particle p:parts) {
                PVector force =  r.repel(p);
                p.applyForce(force);
            }
        }
    }

    void applyAttractors(){
        for(Attractor a:attractors){
            for (Particle p:parts){
                p.applyForce(a.attract(p));
            }
        }
    }

    void addRepeller(Repeller r) {
        repellers.add(r);
    }

    void addAttractor(Attractor a){
        attractors.add(a);
    }
    void addParticle(Particle p) {
        parts.add(p);
    }

    ArrayList<PVector> getLocations() {
        return locs;
    }
}

