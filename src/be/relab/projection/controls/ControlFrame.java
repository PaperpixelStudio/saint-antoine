package be.relab.projection.controls;
import be.relab.projection.constants.ProjectionConstants;
import be.relab.projection.core.Projection;
import processing.core.*;
import controlP5.*;
import java.awt.Frame;
import java.awt.BorderLayout;

/**
 * Created with IntelliJ IDEA.
 * User: MacBookPro17
 * Date: 29/09/13
 * Time: 14:01
 * To change this template use File | Settings | File Templates.
 */
public class ControlFrame extends PApplet {
    int w, h;
    Projection parent;
    ControlP5 cp5;

    public ControlFrame(Projection _parent, int _w, int _h, String _name) {
        parent = _parent;
        w = _w;
        h = _h;
        Frame f = new Frame(_name);
        f.add(this);
        this.init();
        f.setTitle(_name);
        f.setSize(this.w, this.h);
        f.setLocation(1025, 10);
        f.setResizable(false);
        f.setVisible(true);
    }

    public void setup() {
        size(w, h);
        frameRate(25);
        cp5 = new ControlP5(this);
        cp5.addSlider("Sound Threshold").plugTo(parent, "soundThreshold").setRange(0, 5).setValue(2).setPosition(10, 10);
        cp5.addSlider("Part size").plugTo(parent, "partSizeModifier").setRange(0.1f,2).setValue(1).setPosition(10, 30);

        cp5.addSlider("particles X").plugTo(parent, "partX").setRange(0, 578).setValue(289).setPosition(10, 50);
        cp5.addSlider("particles y").plugTo(parent, "partY").setRange(0,1024).setValue(1024).setPosition(10, 80);

        cp5.addSlider("gravity").plugTo(parent, "gravMod").setRange(-100, 100).setValue(0).setPosition(10, 100);
        cp5.addSlider("grid width").plugTo(parent.grid, "width").setRange(-100,100).setValue(0).setPosition(10, 120);
  /*
    cp5.addRadioButton("drawingType").plugTo(parent, "drawingType").setPosition(10,100)
       .addItem("H lines", 1).addItem("V Lines",2).addItem("Triangles",3).activate(0);
    // cp5.addButton("export").plugTo(parent,"initExport").setPosition(10,150);
    // cp5.addSlider("Epaisseur").plugTo(parent, "weight").setRange(1, 5).setPosition(10, 110);
    */

    }

    public void draw() {
    }
    public ControlP5 control() {
        return cp5;
    }
    private ControlFrame() {
    }
}

