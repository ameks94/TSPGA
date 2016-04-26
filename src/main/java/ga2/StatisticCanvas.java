package ga2;import java.awt.*;
import java.lang.*;
import java.util.*;

/**
 * This class shows the statistic of best and mean values through all
 * generations of the GA.
 *
 * coding:   h.sarg
 * modified: 01/98
 */
public class StatisticCanvas extends java.awt.Canvas {
    private boolean fontSet= false;
    private Font f;
    private FontMetrics fm;
    private int fw, fh;
/**
 * draw a new image
 */
    private boolean drawnew = false;
/**
 * image buffer
 */
    private Image buffered_image = null;
/**
 * list for the best fitness per generation
 */
	private Vector best = new Vector(100,200);
/**
 * list for mean fitness per generation
 */
	private Vector mean = new Vector(100,200);
/**
 * scale value for the Canvas height
 */
	private double maxval = -1.0;
/**
 * assosiated ScrollPane
 */
	private ScrollPane scroll=null;
/**
 * length of the actual canvas
 */
	private int length =0;
/**
 * actual canvas changed
 */
	private boolean changed=false;

/**
 * actual canvas dimension
 */
	private Dimension dim;

/**
 * StatisticCanvas constructor comment.
 */
public StatisticCanvas() {
	super();
	dim= new Dimension(10,10);
	drawnew=true;
}

/**
 * gets the statistic vector of the best individuals
 * @return statistic best vector
 */
public Vector getBest() {
	return best;
}

/**
 * gets the statistic vector of the mean individuals
 * @return statistic mean vector
 */
public Vector getMean() {
	return mean;
}

/**
 * sets the scrollpane data
 * @param	s the associated scrollpane
 */
public void setData(ScrollPane s) {
	scroll = s;
	dim=scroll.getViewportSize(); 
	this.setSize(length, dim.height-2);
	scroll.doLayout();
}

/**
 * stores the best and mean values of each generation in vectors
 * @param best best fitness
 * @param mv mean fitness
 */
public void setValues(double best, double mv) {
	if(maxval<mv) maxval = mv;

	Double b= new Double(best);
	Double m= new Double(mv);
	this.best.addElement(b);
	this.mean.addElement(m);
	changed=false;
	drawnew=true;
	repaint();
}

/**
 * sets the new size of the canvas
 */
public void setChanged() {
	if(scroll!=null) {
		if (scroll.getViewportSize().height!= dim.height) {
			changed=true;
			dim=scroll.getViewportSize();
			drawnew=true;
			repaint();
		}
	}
}

/**
 * clears all entries in the best and mean vectors, resets the length of
 * the canvas
 */
public void clear() {
	this.best.removeAllElements();
	this.mean.removeAllElements();
	this.maxval = -1.0;
	length=0;
    buffered_image = null;
	changed=false;
	drawnew=true;
	repaint();
}

/**
 * redefines the update method for faster double buffering
 * @param g java.awt.Graphics
 */
public void update(Graphics g) {
    paint(g);
}

/**
 * create the used font and measure his size
 * @param g java.awt.Graphics
 */
private void setFont(Graphics g) {
    if(fontSet) return;

    f = new Font("Monospaced",Font.PLAIN, 12);
    fm =g.getFontMetrics(f);
    fw =fm.stringWidth("A");
    fh =fm.getAscent();
    fontSet=true;
}


/**
 * redefines the paint method, implements double buffering
 * @param g java.awt.Graphics
 */
public void paint(java.awt.Graphics g) {
	int i,j,x,y;
	int vsize;
	double skal;
	Double help;
	int hval;

	if(drawnew) {
		vsize=best.size();
		if(vsize>0) {
			x=vsize*3;
			y=dim.height;

			if(changed==false) {
				if (scroll.getViewportSize().height!= dim.height) {
					dim=scroll.getViewportSize();
					y=dim.height;
					changed=true;
				}
			}
			while(x>(length-33)) {
				if(length==0) {
					length+=200;
				}
				else length+=100;
				changed=true;
			}
			if(changed) {
				this.setSize(length, y-2);
				scroll.doLayout();
				if (scroll.getViewportSize().height!= dim.height) {
					dim=scroll.getViewportSize();
					y=dim.height;
					this.setSize(length, y-2);
					scroll.doLayout();
				}
				scroll.setScrollPosition(length,0);
				changed=false;
			}

			buffered_image = this.createImage(this.getSize().width, 
						this.getSize().height); 
	
			Graphics gbuff = buffered_image.getGraphics();

		
	        setFont(gbuff);
   		    gbuff.setFont(f);

			y=y-2*fh;	
			skal=(y-2)/maxval;

			gbuff.setColor(java.awt.Color.black);
			gbuff.drawLine(30,2,30,y+1);
			gbuff.drawLine(30,y+1,length,y+1);
			
			i=0;
			j=0;
			while(j<length) {
				gbuff.drawLine(30+j,y+1,30+j,y+4);
				gbuff.drawString(""+i,30+j-fw,y+6+fh);
				i=i+50;
				if(j==0) j+=153;
				else j=j+150;
			}
	
			int delta=50;
			if(maxval<125)  delta=25;
			if(maxval>450) 	delta=200;
			if(maxval>1400) delta=500;
			i=0;
			j=0;
			while(j<maxval) {
				hval= (int) (j*skal);
				gbuff.drawLine(30,y-hval,30-5,y-hval);
				gbuff.drawString(""+i,30-3*fw-9,y-hval+4);
				i=i+delta;
				j=j+delta;
			}

	// draw the mean statistic
			gbuff.setColor(java.awt.Color.green);
			for(i=0; i<vsize; i++) {
				help= (Double) mean.elementAt(i);
				hval= (int) (help.doubleValue() *skal);
				gbuff.drawLine(31+i*3, y, 31+i*3, y-hval);
				gbuff.drawLine(31+i*3+1, y, 31+i*3+1, y-hval);
				gbuff.drawLine(31+i*3+2, y, 31+i*3+2, y-hval);
			}

	// draw the best statistic
			gbuff.setColor(java.awt.Color.red);
			for(i=0; i<vsize; i++) {
				help= (Double) best.elementAt(i);
				hval= (int) (help.doubleValue() *skal);
				gbuff.drawLine(31+i*3, y, 31+i*3, y-hval);
				gbuff.drawLine(31+i*3+1, y, 31+i*3+1, y-hval);
				gbuff.drawLine(31+i*3+2, y, 31+i*3+2, y-hval);
			}

			g.drawImage(buffered_image,0,0,this);
   	     	g.dispose();
		}
		if(buffered_image==null) {
            buffered_image = this.createImage(this.getSize().width,
                        this.getSize().height);

            Graphics gbuff = buffered_image.getGraphics();
			g.drawImage(buffered_image,0,0,this);
   	     	g.dispose();
		}
	}
	else {
		g.drawImage(buffered_image,0,0,this);
   	}
    drawnew=false;
    return;

}

}
