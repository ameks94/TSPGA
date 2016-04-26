package ga2;import java.awt.*;
import java.lang.*;

/**
 * This class visualizes the level of the actual generation.
 *
 * coding:   h.sarg
 * modified: 01/98
 */
public class RecombCanvas extends java.awt.Canvas implements java.awt.event.MouseListener {
	private final int NR=20;
    private boolean fontSet= false;
    private Font f;
    private FontMetrics fm;
    private int fw, fh;
    private int selectsize=0;
	private int popsize=0;
    private Individual[] indiv=null;
	private Population pop=null;
	private Population mut=null;
    private TripFrame[] trips=new TripFrame[NR];
    private int framecount=0;
    private TravelData data;
    private boolean showtowns;
    private int largefonts;
    private int grid;
	private int mm;
    private boolean drawnew = false;
    private Image buffered_image = null;
	private ScrollPane scroll=null;
    private String nrstring="Nr";
    private String parentsstring="Selection";
    private String childstring="Rekombination";
    private String mutstring="Mutation";
    private String fitstring="Fitness";


/**
 * RecombCanvas constructor comment.
 */
public RecombCanvas() {
	super();
	this.addMouseListener(this);
}

/**
 * sets the necessary data to drawing object
 * @param   s pointer to the scrollpane
 * @param   ps populationsize
 * @param   ss selectionsize
 * @param   a object which contains the values of the towns
 * @param   b indicates if the towns should be labeled or not
 * @param   c indicates the font size
 * @param   grid value for the call of the subroutine setData
 * @param   mm mutation method
 */
public void setRecombData(ScrollPane s, int ps, int ss, TravelData a, boolean b, int c, int grid, int mm) {
    this.data=a;
    this.showtowns=b;
    this.largefonts=c;
    this.grid=grid;
	fontSet = false;
	drawnew=false;
	pop=null;
	indiv=null;
	mut=null;
	this.mm=mm;
	if(2*ps > ss) {
		this.selectsize=ss;
		this.popsize=ps-1;
	}
	else {
		this.popsize=ps;
		this.selectsize=ss;
	}
	scroll = s;
    repaint();
}

/**
 * sets the recombinated population to be drawn
 * @param   pop population
 */
public void setPop(Population pop) {
		this.pop=pop;
		drawnew=true;
    	repaint();
}

/**
 * sets the mutated population to be drawn
 * @param   pop population
 */
public void setMut(Population pop) {
		this.mut=pop;
		drawnew=true;
    	repaint();
}

/**
 * sets the selected individuals to be drawn
 * @param   indiv	array of individuals
 */
public void setSelection(Individual[] indiv) {
		this.indiv=indiv;
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
 * @return true if there is a free place in the array
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
    //System.out.println("showing:" + framecount+ "index= "+index+"\n");
    return state;
}


/**
 * redefines the paint method, implements double buffering
 * @param a java.awt.Graphics
 */
public void paint(java.awt.Graphics g) {
    int i,j,x,y;
	int length=0;
	int oldlength=0;
	int mut1, mut2;
	String helpstr="";
	String drawstr="";
	//int help;

	if(drawnew) {
		setFont(g);

	// calculate the needed size of the Canvas
		if(indiv!=null) {
			x=fm.stringWidth(indiv[0].toPopString(false) )+3*fw;
			y=(int) ((selectsize)*(fh+4.5)+(fh+3)+fh+2);
			if(pop!=null) {
				x+=fm.stringWidth(pop.getIndiv(0).toPopString(false) )+2*fw; 
			}
			if(mut!=null) {
				x+=fm.stringWidth(mut.getIndiv(0).toPopString(true) )+2*fw; 
			}
			this.setSize(x, y);
			scroll.doLayout();
		}

    	buffered_image = this.createImage(this.getSize().width,
                                            this.getSize().height);

    	Graphics gbuff = buffered_image.getGraphics();

    	gbuff.setColor(java.awt.Color.magenta);

    	setFont(gbuff);
    	gbuff.setFont(f);

	if(indiv!=null) {
		gbuff.drawString(nrstring,2*fw,fh+3);
        gbuff.drawString(parentsstring,5*fw,fh+3);

  	gbuff.setColor(java.awt.Color.black);
	//draw the selected individuals
		j=0;
		for(i=0;i<selectsize;i+=2) {
       	 	gbuff.drawString( (indiv[i].toPopString(false)),2*fw,(i+2)*(fh+3)+2+j);
       	 	gbuff.drawString( (indiv[i+1].toPopString(false)),2*fw,(i+3)*(fh+3)+2+j);
			j+=3;
 	   	}

	// visualize the recombinated individuals
	if(pop!=null) {
		length=fm.stringWidth(indiv[0].toPopString(false) )+2*fw+2*fw;
		oldlength=length;
		//gbuff.drawString(nrstring,length,fh+3);
    	gbuff.setColor(java.awt.Color.magenta);
	    gbuff.drawString(childstring,length,fh+3);
    	gbuff.setColor(java.awt.Color.black);

		j=0;
		for(i=0;i<popsize;i++) {
			mut1=pop.getIndiv(i).getMut1();
			helpstr = pop.getIndiv(i).toPopString(false);
			length=oldlength;

			if(mut1==-1 || mut==null) {
       			gbuff.drawString( helpstr,length,(fh+3)+2*(i+1)*(fh+3)+2+j );
				length=length+fm.stringWidth(helpstr);
			}
			else {
				mut2=pop.getIndiv(i).getMut2();
				//help=helpstr.indexOf(" ");
				//help++;
				if(mm==3 && mut2!=-1) {
					drawstr=helpstr.substring(0,mut1);
       				gbuff.drawString(drawstr,length,(fh+3)+2*(i+1)*(fh+3)+2+j );

    				gbuff.setColor(java.awt.Color.red);
					length= length+fm.stringWidth(drawstr);
					drawstr=helpstr.substring(mut1,mut1+1);
       				gbuff.drawString(drawstr,length,(fh+3)+2*(i+1)*(fh+3)+2+j );

					if(mut1+1<=mut2 ) {
    					gbuff.setColor(java.awt.Color.black);
						length= length+fm.stringWidth(drawstr);
						drawstr=helpstr.substring(mut1+1,mut2);
       					gbuff.drawString(drawstr,length,(fh+3)+2*(i+1)*(fh+3)+2+j );
					}

    				gbuff.setColor(java.awt.Color.red);
					length= length+fm.stringWidth(drawstr);
					drawstr=helpstr.substring(mut2,mut2+1);
       				gbuff.drawString(drawstr,length,(fh+3)+2*(i+1)*(fh+3)+2+j );

    				gbuff.setColor(java.awt.Color.black);
					if(mut2+1<=helpstr.length() ) {
						length= length+fm.stringWidth(drawstr);
						drawstr=helpstr.substring(mut2+1,helpstr.length());
       					gbuff.drawString(drawstr,length,(fh+3)+2*(i+1)*(fh+3)+2+j );
					}	
				}
				else {
					if(mut2==-1)mut2=mut1;
					drawstr=helpstr.substring(0,mut1);
                    gbuff.drawString(drawstr,length,(fh+3)+2*(i+1)*(fh+3)+2+j );

                    gbuff.setColor(java.awt.Color.red);
                    length= length+fm.stringWidth(drawstr);
                    drawstr=helpstr.substring(mut1,mut2+1);
                    gbuff.drawString(drawstr,length,(fh+3)+2*(i+1)*(fh+3)+2+j );

                   	gbuff.setColor(java.awt.Color.black);
					if(mut2+1<=helpstr.length() ) {
                    	length= length+fm.stringWidth(drawstr);
                    	drawstr=helpstr.substring(mut2+1,helpstr.length());
                    	gbuff.drawString(drawstr,length,(fh+3)+2*(i+1)*(fh+3)+2+j );
					}
				}
			}
			j+=3;
    	}	
	}

	// visualize the mutated individuals
	if(mut!=null) {
		oldlength=oldlength + fm.stringWidth(helpstr)+2*fw;
		//gbuff.drawString(nrstring,oldlength,fh+3);
    	gbuff.setColor(java.awt.Color.magenta);
        gbuff.drawString(mutstring,oldlength,fh+3);
		gbuff.drawString(fitstring,oldlength-7*fw+fw*(mut.getIndiv(0).toPopString(true)).length(),fh+3);
    	gbuff.setColor(java.awt.Color.black);

		j=0;
        for(i=0;i<popsize;i++) {
            mut1=mut.getIndiv(i).getMut1();
            helpstr = mut.getIndiv(i).toPopString(true);
			length=oldlength;
            if(mut1==-1) {
                gbuff.drawString( helpstr,length,(fh+3)+2*(i+1)*(fh+3)+2+j );
				length=length+fm.stringWidth(helpstr);
            }
            else {
                mut2=mut.getIndiv(i).getMut2();
                //help=helpstr.indexOf(" ");
                //help++;
                if(mm==3 && mut2!=-1) {
                    drawstr=helpstr.substring(0,mut1);
                    gbuff.drawString(drawstr,length,(fh+3)+2*(i+1)*(fh+3)+2+j );

                    gbuff.setColor(java.awt.Color.red);
                    length= length+fm.stringWidth(drawstr);
                    drawstr=helpstr.substring(mut1,mut1+1);
                    gbuff.drawString(drawstr,length,(fh+3)+2*(i+1)*(fh+3)+2+j );

                    if(mut1+1<=mut2 ) {
                        gbuff.setColor(java.awt.Color.black);
                        length= length+fm.stringWidth(drawstr);
                        drawstr=helpstr.substring(mut1+1,mut2);
                        gbuff.drawString(drawstr,length,(fh+3)+2*(i+1)*(fh+3)+2+j );
                    }

                    gbuff.setColor(java.awt.Color.red);
                    length= length+fm.stringWidth(drawstr);
                    drawstr=helpstr.substring(mut2,mut2+1);
                    gbuff.drawString(drawstr,length,(fh+3)+2*(i+1)*(fh+3)+2+j );

                    gbuff.setColor(java.awt.Color.black);
                    if(mut2+1<=helpstr.length() ) {
                        length= length+fm.stringWidth(drawstr);
                        drawstr=helpstr.substring(mut2+1,helpstr.length());
                        gbuff.drawString(drawstr,length,(fh+3)+2*(i+1)*(fh+3)+2+j );
                    }
                }
                else {
					if(mut2==-1)mut2=mut1;
                    drawstr=helpstr.substring(0,mut1);
                    gbuff.drawString(drawstr,length,(fh+3)+2*(i+1)*(fh+3)+2+j );

                    gbuff.setColor(java.awt.Color.red);
                    length= length+fm.stringWidth(drawstr);
                    drawstr=helpstr.substring(mut1,mut2+1);
                    gbuff.drawString(drawstr,length,(fh+3)+2*(i+1)*(fh+3)+2+j );

                    gbuff.setColor(java.awt.Color.black);
                    if(mut2+1<=helpstr.length() ) {
                        length= length+fm.stringWidth(drawstr);
                        drawstr=helpstr.substring(mut2+1,helpstr.length());
                        gbuff.drawString(drawstr,length,(fh+3)+2*(i+1)*(fh+3)+2+j );
                    }
                }
            }
			j+=3;
        }
    }

	} //end if indiv

    g.drawImage(buffered_image,0,0,this);
    g.dispose();
	} // end if drawnew
	else {
		if(indiv!=null) g.drawImage(buffered_image,0,0,this);
	}
	drawnew=false;	
    return;
}



/**
 * Double clicking the string representation of an individual creates a 
 * TripFrame. This frame shows this individual in detail.
 * @param e: java.awt.event.MouseEvent
 */
public void mouseClicked(java.awt.event.MouseEvent e) {
    int a,b;
    int x,y;
	int ls=0;
	int lp=0;
	int lm=0;
	
    if(e.getClickCount() >= 2) {
    	x=e.getX();
    	y=e.getY();

		if(indiv!=null) {
			ls=fm.stringWidth(indiv[0].toPopString(false) )+2*fw;

    		if(x>(2*fw) && (x<ls) && y>=(fh+5)) {
				y-=fh+5;
        		a=(int) (y/(fh+3+1.5));
        		if(a<selectsize) {
            		if(framecount<NR || checkFrames()) {
                		trips[framecount]=new TripFrame();
                		trips[framecount].setData(indiv[a],data,showtowns,largefonts,grid);
                		trips[framecount].show();
                		framecount++;
            		}
        		}
			}
		}
		if(pop!=null) {
			lp=ls+fm.stringWidth(pop.getIndiv(0).toPopString(false) )+2*fw;

    		if(x>ls && (x<lp) && y>=(fh+5)) {
				y-=fh+5;
        		a=(int) (y/(2*(fh+3)+3));
        		if(a<popsize) {
            		if(framecount<NR || checkFrames()) {
                		trips[framecount]=new TripFrame();
                		trips[framecount].setData(pop.getIndiv(a),data,showtowns,largefonts,grid);
                		trips[framecount].show();
                		framecount++;
            		}
        		}
			}
		}
        if(mut!=null) {
            lm=lp+fm.stringWidth(mut.getIndiv(0).toPopString(true) )+2*fw;

            if(x>lp && (x<lm) && y>=(fh+5)) {
				y-=fh+5;
                a=(int) (y/(2*(fh+3)+3));
                if(a<popsize) {
                    if(framecount<NR || checkFrames()) {
                        trips[framecount]=new TripFrame();
                        trips[framecount].setData(mut.getIndiv(a),data,showtowns,largefonts,grid);
                        trips[framecount].show();
                        framecount++;
                    }
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

