package ga2;import java.awt.*;

/**
 * This class visualizes the actual population of the GA.
 * 
 * coding:   h.sarg
 * modified: 01/98
 */
public class PopCanvas extends java.awt.Canvas implements java.awt.event.MouseListener {
	private final int NR=20;
	private boolean fontSet= false;
	private Font f;
    private FontMetrics fm;
    private int fw, fh;
	private int popsize;
	private Population pop=null;
	private TripFrame[] trips=new TripFrame[NR];
	private int framecount=0;
	private TravelData data;
	private boolean showtowns;
	private int largefonts;
	private int grid;
	private boolean drawnew = false;
	private Image buffered_image = null;
	private ScrollPane scroll=null;
	private String nrstring="Nr";
	private String tripstring="Trip";
	private String fitstring="Fitness";

/**
 * PopCanvas constructor comment.
 */
public PopCanvas() {
	super();
	this.addMouseListener(this);
}

/**
 * sets the necessary data to draw this object
 * @param	s pointer to the scrollpane
 * @param	popsize populationsize
 * @param	a object which contains the values of the towns
 * @param	b indicates if the towns should be labeled or not
 * @param	c indicates the font size
 * @param	grid size
 */
public void setPopData(ScrollPane s, int popsize, TravelData a, boolean b, int c, int grid) {
	this.popsize = popsize;
	this.data=a;
	this.showtowns=b;
	this.largefonts=c;
	this.grid=grid;
	fontSet = false;
	scroll = s;

	//repaint();
}

/**
 * sets the population to be drawn
 * @param	pop population
 */
public void setPop(Population pop) {
	this.pop=pop;
	drawnew = true;
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
    if(largefonts==1)  {
        f = new Font("Monospaced",Font.PLAIN, 12);
    }
    if(largefonts==0)  {
        f = new Font("Monospaced",Font.PLAIN, 11);
    }
    if(largefonts==2)  {
        f = new Font("Monospaced",Font.PLAIN, 13);
    }
    fm =g.getFontMetrics(f);
    fw =fm.stringWidth("A");
    fh =fm.getAscent();
    fontSet=true;
}

/**
 * disposes all TripFrames and is called before the parent window is closed
 */
public void disposeFrames() {
	for(int i=0;i<framecount;i++) trips[i].dispose();
	framecount=0;
}

/**
 * checks if there are invalid frames stored 
 * @return if there is a free place in the array
 */
public boolean checkFrames() {
	int index=-1;
	boolean state=false;

	for(int i=0;i<NR;i++) {
		if(trips[i].isShowing() ) {
			if(index>-1) {
				trips[index]=trips[i];
				index+=1;
			}
		}
		else {
			if(index==-1) index=i;
			framecount--;
			state=true;
		}
	}
	System.out.println("showing:" + framecount+ "index= "+index+"\n");
	return state;
}
	

/**
 * redefines the paint method, implements double buffering
 * @param a java.awt.Graphics
 */
public void paint(java.awt.Graphics g) {
    int i,j,x,y;

	if(drawnew) {
		if(pop!=null) {
			setFont(g);
			x=fm.stringWidth(pop.getIndiv(0).toPopString(true) )+3*fw;
			y=(1+popsize)*(fh+3)+fh+2;
			this.setSize(x, y);
			scroll.doLayout();

    		buffered_image = this.createImage(this.getSize().width,
                                            this.getSize().height);

    		Graphics gbuff = buffered_image.getGraphics();

    		gbuff.setColor(java.awt.Color.magenta);
    		setFont(gbuff);
    		gbuff.setFont(f);

			gbuff.drawString(nrstring,2*fw,fh+3);
			gbuff.drawString(tripstring,5*fw,fh+3);
			gbuff.drawString(fitstring,-5*fw+fw*(pop.getIndiv(0).toPopString(true)).length(),fh+3);
			
    		gbuff.setColor(java.awt.Color.black);
			for(i=0;i<popsize;i++) {
				if(pop.getIndiv(i).isBest() ) {
    				gbuff.setColor(java.awt.Color.blue);
					gbuff.drawString( (pop.getIndiv(i).toPopString(true)),2*fw,(i+2)*(fh+3)+2 );
    				gbuff.setColor(java.awt.Color.black);
				}
				else {
					gbuff.drawString( (pop.getIndiv(i).toPopString(true)),2*fw,(i+2)*(fh+3)+2 );
				}
			}
		//System.out.println("letztes Indiv: y= "+(i+1)*(fh+3));
		}
    	g.drawImage(buffered_image,0,0,this);
    	g.dispose();
	}
	else {
    	g.drawImage(buffered_image,0,0,this);
	}
	drawnew=false;
    return;
}



/**
 * Double clicking the string representation of an individual creates
 * a TripFrame. This frame shows this individual in detail.
 * @param e MouseEvent
 */
public void mouseClicked(java.awt.event.MouseEvent e) {
    int a,b;
    int x,y;

	if(e.getClickCount() >= 2) {
    x=e.getX();
    y=e.getY();

	if(x>(2*fw) && y>=(fh+5) ) {
		y-=fh+5;
		a=(int) (y/(fh+3));
		if(a<popsize) {
			if(framecount<NR || checkFrames() ) {
				trips[framecount]=new TripFrame();
				trips[framecount].setData(pop.getIndiv(a),data,showtowns,largefonts,grid);
				trips[framecount].show();
				framecount++;
			}
		}
	}
	}

}

/**
 * mouseEntered method comment.
 */
public void mouseEntered(java.awt.event.MouseEvent e) {
}

/**
 * mouseExited method comment.
 */
public void mouseExited(java.awt.event.MouseEvent e) {
}

/**
 * mousePressed method comment.
 */
public void mousePressed(java.awt.event.MouseEvent e) {
}

/**
 * mouseReleased method comment.
 */
public void mouseReleased(java.awt.event.MouseEvent e) {
}


}
