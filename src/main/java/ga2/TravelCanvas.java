package ga2;import java.awt.*;

/**
 * This class visualizes the trip representated by one individual.
 *
 * coding:   h.sarg
 * modified: 01/98
 */
public class TravelCanvas extends java.awt.Canvas {

	private TravelData data;
    private int stepwidth;
	private int stepwh;
    private int stepheight;
	private int stephh;
    private int canvaswidth=150;
    private int canvasheight=150;
	private boolean fontSet= false;
	private Font f;
	private FontMetrics fm;
	private int fw, fh, fwh, fhh;
	private String helpstring="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private boolean showtowns;
	private int largefonts;
	private int grid=0;
	private int size;
	private int [][]coord;
	private Individual indiv;
	private Image buffered_image=null;
	private boolean drawnew;

/**
 * TravelCanvas constructor comment.
 */
public TravelCanvas() {
	super();
	drawnew=true;
}

/**
 * sets the necessary data to draw this object
 * @param a:    object including the travel data
 * @param b:    indicates if the towns should be labeled or not
 * @param c:    indicates the font size
 */
public void setTravelData(TravelData a, boolean b, int c){
	if(a==null) System.out.println("What is that");
	data=a;
	size=data.getTownSize();
	indiv=new Individual(size);
	coord=data.getCoord();
	grid=data.getGrid();
	stepwidth=(int)(canvaswidth/grid);
	stepheight=(int)(canvasheight/grid);
	stepwh=(int)(stepwidth/2);
	stephh=(int)(stepheight/2);
	//System.out.println("setTravelData: x="+canvaswidth+" y="+canvasheight+"\n");
	if(size>26) showtowns=false;
	else showtowns=b;
	largefonts=c;
	fontSet = false;
	drawnew=true;
	buffered_image=null;
    repaint();
}

/**
 * sets an individual for which the trip is shown
 * @param	indiv individual to visualize
 */
public void setTravelIndiv(Individual indiv) {
	if(indiv!=null) this.indiv.copyIndiv(indiv);
	else {
		buffered_image=null;
		this.indiv=null;
	}
	drawnew=true;
    repaint();
}

/**
 * sets the actual measurement of the canvas
 * @param a:    x value, scaled by grid
 * @param b:    y value, scaled by grid
 */
public void setTravelCanvas(int a, int b) {
	if(grid!=0) {
    	this.stepwidth=a;
    	this.stepheight=b;
		this.stepwh=(int)(a/2);
		this.stephh=(int)(b/2);
    	this.canvaswidth=grid*a;
    	this.canvasheight=grid*b;
		//System.out.println("setTravelDataB�se: x="+canvaswidth+" y="+canvasheight+"\n");
		drawnew=true;
    	repaint();
	}
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
	fwh = (int)(fw/2);
	fh =fm.getAscent();
	fhh = (int)(fh/2);
	fontSet=true;
}

/**
 * redefines the paint method, implements double buffering
 * @param a java.awt.Graphics
 */
public void paint(java.awt.Graphics g) {
    int i,j;
	int k=1;


	if(drawnew) {
    if(indiv!=null) {
   		buffered_image = this.createImage(this.getSize().width,
                                            this.getSize().height);

   		Graphics gbuff = buffered_image.getGraphics();
    	gbuff.setColor(java.awt.Color.white);
    	gbuff.fillRect(0,0,canvaswidth,canvasheight);
    	gbuff.setColor(java.awt.Color.black);

		/* for(i=0;i<=canvaswidth;i+=stepwidth) {
       		 gbuff.drawLine(i,0,i,canvasheight);
    	};
    	for(i=0;i<=canvasheight;i+=stepheight) {
        	gbuff.drawLine(0,i,canvaswidth,i);
    	}; */

	// draw the trip of the individual
        for(i=0;i<size;i++) {
            j=indiv.getGen(i);
            if(i<(size-1)) k=indiv.getGen(i+1);
            else k=indiv.getGen(0);
            gbuff.drawLine(coord[j][0]*stepwidth+stepwh,coord[j][1]*stepheight+stephh,
                coord[k][0]*stepwidth+stepwh,coord[k][1]*stepheight+stephh);
        }

		setFont(gbuff);
		gbuff.setFont(f);

	// draw the towns and draw their names or not
	k=1;
    for(i=0;i<size;i++) {
    	//gbuff.drawRect(coord[i][0]*stepwidth,coord[i][1]*stepheight, stepwidth,stepheight);
		if(showtowns) {

            if(largefonts==0) {
            gbuff.drawOval(coord[i][0]*stepwidth+stepwh-fwh-3,coord[i][1]*stepheight+stephh-fhh-1, fw+4,fh+2);
            gbuff.setColor(java.awt.Color.white);
            gbuff.fillOval(coord[i][0]*stepwidth+stepwh-fwh-3,coord[i][1]*stepheight+stephh-fhh-1, fw+4,fh+2);
            gbuff.setColor(java.awt.Color.black);
            gbuff.drawOval(coord[i][0]*stepwidth+stepwh-fwh-3,coord[i][1]*stepheight+stephh-fhh-1, fw+4,fh+2);
            }
            if(largefonts==1) {
            gbuff.drawOval(coord[i][0]*stepwidth+stepwh-fwh-3,coord[i][1]*stepheight+stephh-fhh-1, fw+4,fh+2);
            gbuff.setColor(java.awt.Color.white);
            gbuff.fillOval(coord[i][0]*stepwidth+stepwh-fwh-3,coord[i][1]*stepheight+stephh-fhh-1, fw+4,fh+2);
            gbuff.setColor(java.awt.Color.black);
            gbuff.drawOval(coord[i][0]*stepwidth+stepwh-fwh-3,coord[i][1]*stepheight+stephh-fhh-1, fw+4,fh+2);
            }
			if(largefonts==2) {
			gbuff.drawOval(coord[i][0]*stepwidth+stepwh-fwh-2,coord[i][1]*stepheight+stephh-fhh-1, fw+4,fh+2);
    		gbuff.setColor(java.awt.Color.white);
			gbuff.fillOval(coord[i][0]*stepwidth+stepwh-fwh-2,coord[i][1]*stepheight+stephh-fhh-1, fw+4,fh+2);
    		gbuff.setColor(java.awt.Color.black);
			gbuff.drawOval(coord[i][0]*stepwidth+stepwh-fwh-2,coord[i][1]*stepheight+stephh-fhh-1, fw+4,fh+2);
			}
			
			gbuff.drawString(helpstring.substring(k-1,k),
            coord[i][0]*stepwidth+stepwh-fwh,coord[i][1]*stepheight+stephh+fhh);
		k=k+1;
		}
		else {
		gbuff.drawOval(coord[i][0]*stepwidth+stepwh-2,coord[i][1]*stepheight+stephh-2,4,4);
		gbuff.fillOval(coord[i][0]*stepwidth+stepwh-2,coord[i][1]*stepheight+stephh-2,4,4);
		}
	} //end for
    g.drawImage(buffered_image,0,0,this);
    g.dispose();
    } //end indiv
       if(buffered_image==null) {
            buffered_image = this.createImage(this.getSize().width,
                        this.getSize().height);

            Graphics gbuff = buffered_image.getGraphics();

	    	gbuff.setColor(java.awt.Color.lightGray );
    		gbuff.fillRect(0,0,this.getSize().width, this.getSize().height);

            g.drawImage(buffered_image,0,0,this);
            g.dispose();
        }
	} //end drawnew
	else {
		if(buffered_image!=null) g.drawImage(buffered_image,0,0,this);
    }
	drawnew=false;
    return;
}


}
