package ga2;import java.awt.*;

/**
 * This class visualizes the recombination method if PMX, OX, CX or
 * UOBX is selected.
 *
 * coding:   h.sarg
 * modified: 01/98
 */
public class StringCanvas extends java.awt.Canvas {
    private boolean fontSet= false;
    private Font f;
    private FontMetrics fm;
    private int fw, fh;
    private Image buffered_image = null;
    private int largefonts;
	private int sz;
	private int towns;
	private Individual child;
	private Individual parent1;
	private Individual parent2;
	private int step;
	private VisRecomb vis;
	private int rm;
	private Individual indiv;
	private String[] rmstring;


/**
 * StringCanvas constructor comment.
 */
public StringCanvas() {
	super();
	rmstring= new String[4];
	rmstring[0]="Partially Matched Crossover (PMX)";
	rmstring[1]="Order Crossover (OX)";
	rmstring[2]="Cycle Crossover (CX)";
	rmstring[3]="Uniform Order Based Crossover (UOBX)";
}

/**
 * sets the next step of the crossover
 * @param step the next step
 */
public void setStep(int step) {
	this.step=step;

    repaint();
}

/**
 * sets the necessary data and the child to draw this object
 * @param selectsize number of selected individuals
 * @param a number of towns
 * @param b indicates the font size
 * @param c recombination method
 */
public void setData(int selectsize, int a, int b, int c) {
	sz = selectsize;
	towns = a;
	largefonts = b;
    fontSet = false;
	rm =c;
	indiv=new Individual(towns);
}

/**
 * sets the parents and the child to be drawn
 * @param parent1 first parent
 * @param parent2 second parent
 * @param child
 */
public void setRecomb(Individual parent1, Individual parent2, Individual child) {
    this.child=child;
    this.parent1=parent1;
    this.parent2=parent2;
	step=0;
	if(child!=null) {
		vis=child.getVisRecomb();
	}
	
	
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
    f = new Font("Monospaced",Font.PLAIN, 18);
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
	int i,j;
	String parent1l="";
	String parent2l="";
	String childl="";
	int length = 0;
	int height = 0;
	int costart=0;
	int coend=0;
	boolean found =false;

    setFont(g);
    buffered_image = this.createImage(this.getSize().width,
                                       this.getSize().height);
    Graphics gbuff = buffered_image.getGraphics();

	//set background
    gbuff.setColor(java.awt.Color.white);
	gbuff.fillRect(0,0,this.getSize().width,this.getSize().height);

    gbuff.setColor(java.awt.Color.black);
    setFont(gbuff);
	gbuff.setFont(f);

	length=2*fw;
	height=2*(fh+3);
	gbuff.drawString(rmstring[rm],length,height);
	height+=2*(fh+3);

	if(child!=null) {
		costart=vis.getCoStart();
		coend=vis.getCoEnd();
	
	// draw the string representation of parent1
		parent1l=parent1.toPopString(false);
    	gbuff.setColor(java.awt.Color.black);
		gbuff.drawString("Parent1: ",length,height);
    	gbuff.setColor(java.awt.Color.green);
		length=5*fw;
		height+=fh+3;
		gbuff.drawString(parent1l.substring(0,3),length,height);
		length+=fm.stringWidth(parent1l.substring(0,3));
		for(i=0;i<towns;i++) {
			length+=2*fw;
			if(costart>-1 && costart<=i && coend>=i) {
				gbuff.setColor(java.awt.Color.red);
				gbuff.drawString(parent1l.substring(i+3,i+4),length,height);
				gbuff.setColor(java.awt.Color.green);
			}
			else {
				gbuff.drawString(parent1l.substring(i+3,i+4),length,height);
			}
		}

	// draw the string representation of parent2
		parent2l=parent2.toPopString(false);
    	gbuff.setColor(java.awt.Color.black);
		length=2*fw;
		height+=fh+3;
		gbuff.drawString("Parent2: ",length,height);
    	gbuff.setColor(java.awt.Color.blue);
		length=5*fw;
		height+=fh+3;
		gbuff.drawString(parent2l.substring(0,3),length,height);
		length+=fm.stringWidth(parent2l.substring(0,3));
		for(i=0;i<towns;i++) {
			length+=2*fw;
			if(costart>-1 && costart<=i && coend>=i) {
				gbuff.setColor(java.awt.Color.magenta);
				gbuff.drawString(parent2l.substring(i+3,i+4),length,height);
				gbuff.setColor(java.awt.Color.blue);
			}
			else {
				gbuff.drawString(parent2l.substring(i+3,i+4),length,height);
			}
		}

	// draw the string representation of  the actual child
		childl=child.toPopString(false);
		childl="   "+childl;
		length=2*fw;
		height+=fh+3;
    	gbuff.setColor(java.awt.Color.black);
		gbuff.drawString("Child: ",length,height);
		length=5*fw;
		height+=fh+3;
		gbuff.drawString(childl.substring(0,3),length,height);
		length+=fm.stringWidth(childl.substring(0,3));

	// used for pmx, make each step the exchange of the gens
		if(rm==0) {
			if(step==0) {
				indiv.copyIndiv(parent2);
				indiv.setNr(child.getNr());
			}
			if(step==1) {
                j=costart+step-1;
                indiv.setGen(vis.getStep(j), indiv.getGen(j) );
                indiv.setGen(j, parent1.getGen(j));
				vis.setInfo(vis.getStep(j), 5);
                vis.setInfo(j,3);
			}
			if(step>1) {
				j=costart+step-1;
				indiv.setGen(vis.getStep(j), indiv.getGen(j) );
				if(vis.getInfo(vis.getStep(j-1))==5) {
					vis.setInfo(vis.getStep(j-1), 2);
				}
				vis.setInfo(vis.getStep(j), 5);
				indiv.setGen(j, parent1.getGen(j));	
				vis.setInfo(j,3);
			}
			if(vis.getMaxStep() == step) {
				for(i=0;i<towns;i++) {
					if(i>=costart && i<=coend) vis.setInfo(i,3);
					else vis.setInfo(i,2);
				}	
				j=costart+step-1;
				if(vis.getStep(j)<costart || vis.getStep(j)>coend) {
					vis.setInfo(vis.getStep(j), 5);
				}

				childl=child.toPopString(false);
				childl="   "+childl;
			}
			else {
				childl=indiv.toPopString(false);
				childl="   "+childl;
			}
		}
		
		for(i=0;i<towns;i++) {
			length+=2*fw;
			if(costart!=-1 && step<2 && rm!=0) {
				if(step==0) {
                    gbuff.setColor(java.awt.Color.blue);
                    gbuff.drawString(parent2l.substring(i+3,i+4),length,height);
				}
				else {
				found=false;
				for(j=costart;j<=coend;j++) {
					if(parent2.getGen(i)==parent1.getGen(j) ) found=true;
				}
				if(found) {
					gbuff.setColor(java.awt.Color.black);
					gbuff.drawString("_",length,height);
				}
				else {
					gbuff.setColor(java.awt.Color.blue);
					gbuff.drawString(parent2l.substring(i+3,i+4),length,height);
				}
				}
			}
			else {
				if(vis.getStep(i)<=step || rm==0 ) {
					if(vis.getInfo(i)==1) gbuff.setColor(java.awt.Color.green);
					if(vis.getInfo(i)==2) gbuff.setColor(java.awt.Color.blue);
					if(vis.getInfo(i)==3) gbuff.setColor(java.awt.Color.red);
					if(vis.getInfo(i)==4) gbuff.setColor(java.awt.Color.magenta);
					if(vis.getInfo(i)==5) gbuff.setColor(java.awt.Color.cyan);

					gbuff.drawString(childl.substring(i+3,i+4),length,height);
				}
				else {
					gbuff.setColor(java.awt.Color.black);
					gbuff.drawString("_",length,height);
				}
			}
		}


	}
	else {
		gbuff.drawString("Sorry, in this generation was no more recombination",length, height);
		height+=fh+3;
		gbuff.drawString("Close this panel and go on with the GA",length,height);
	}
    g.drawImage(buffered_image,0,0,this);
    g.dispose();

	return;
}

}

