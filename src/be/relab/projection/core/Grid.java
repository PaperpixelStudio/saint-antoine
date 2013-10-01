package be.relab.projection.core;

/**
 * Created with IntelliJ IDEA.
 * User: MacBookPro17
 * Date: 29/09/13
 * Time: 15:23
 * To change this template use File | Settings | File Templates.
 */
public class Grid {
    Projection parent;
    Grid(){
    }

    public void display(Projection p){
        p.canvas.strokeWeight(3);
        p.canvas.stroke(255);
        p.canvas.noFill();
        for(float i = 0; i < p.LINES; i+=1){
            for(float j = 0; j < 4; j+=1){
                float x = p.MARGIN_LEFT +(p.RECT_WIDTH *j)+p.COLONNE *j-10*j;
                float y = p.VIT_POS_Y+(p.RECT_HEIGHT)+(p.RECT_HEIGHT *i)+5;
                p.canvas.rect(x,y,p.RECT_WIDTH,p.RECT_HEIGHT);
                p.textSize(10);

               // p.canvas.text(p.parseInt(i*4+j),x+p.RECT_WIDTH/2,y+p.RECT_HEIGHT);


            }

        }
        p.canvas.fill(255);
    }
}
