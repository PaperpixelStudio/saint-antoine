package be.relab.projection.core;

import be.relab.projection.constants.ProjectionConstants;

/**
 * Created with IntelliJ IDEA.
 * User: MacBookPro17
 * Date: 29/09/13
 * Time: 15:23
 * To change this template use File | Settings | File Templates.
 */
public class Grid {
    Projection parent;
    float width;
    Grid(){
        width = 100;
    }

    public void display(Projection p){
        p.canvas.strokeWeight(3);
        p.canvas.stroke(255);
        p.canvas.noFill();
        for(float i = 0; i < 4; i+=1){
            for(float y = 0; y < 4; y+=1){
                p.canvas.rect(p.marginLeft+(p.vitRectWidth*y)+p.colonne*y-10*y,(70+p.vitRectHeight)+(p.vitRectHeight*i)+5,p.vitRectWidth,p.vitRectHeight);

            }
        }
        p.canvas.fill(255);
    }
}
