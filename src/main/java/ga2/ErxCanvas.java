package ga2;import java.awt.*;

/**
 * This class visualizes the recombination method, if ERX or
 * ERX with heuristic is selected.
 *
 * coding:   h.sarg
 * modified: 01/98
 */
public class ErxCanvas extends java.awt.Canvas {
    private boolean fontSet= false;
    private Font f;
    private FontMetrics fm;
    private int fw, fh, fwh, fhh;
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
	private int[][] edges;
	private String erx1;
	private String erx2;
	private TravelData data;
    private int [][]coord;
	private boolean showtowns;
    private String helpstring="ABCDEFGHIJKLMNOPQRSTUVWXYZ";


/**
 * ErxCanvas constructor comment.
 */
public ErxCanvas() {
	super();
	erx1="Edge Recombination Crossover (ERX)";
	erx2="Edge Recombination Crossover with Heuristic (Grefenstette)";
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
 * sets the necessary data to draw this object
 * @param data TravelData contains info about the towns
 * @param selectsize number of selected individuals
 * @param a indicates if the towns should be labeled or not
 * @param b indicates the font size
 * @param c recombination method
 * @param d showtowns
 */
public void setData(TravelData data, int selectsize, int a, int b, 
									int c, boolean d) {
    sz = selectsize;
    towns = a;
    largefonts = b;
    fontSet = false;
	this.data = data;
    coord=data.getCoord();
    rm =c;
	showtowns=d;
}

/**
 * sets the parents and the child to be drawn
 * @param parent1: first parent
 * @param parent2: second parent
 * @param child: child
 */
public void setRecomb(Individual parent1, Individual parent2, Individual child) {
    this.child=child;
    this.parent1=parent1;
    this.parent2=parent2;
    step=0;
    if(child!=null) {
        vis=child.getVisRecomb();
		edges=vis.getEdges();
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
 * adapts the coordinates to a fixed formatted string
 * @param  dbl represents the coordinates
 * @return  String which represents the coordinates
 */
public String trimCoord(double dbl) {
    int help;
    int post=3;
    String s="";
    int length =0;
    int diff=0;

    s="" + dbl;
    length=s.length();
    help=s.indexOf(".");
    while(help<3) {
        s=" "+s;
        help++;
    }
    help++;
    diff=length-help-post;
    while(diff<1) {
        s=s+"0";
        diff++;
    }
    s=s.substring(0,help+post);
  
    return s;
}


/**
 * creates a representative string out of the edge list of one town
 * @param 	nr number of entry in the edge list
 * @param 	list the edge list of one town
 * @return 	string representation
 */
public String toEdgeString(int nr, int[] list) {
	String s="";
	int i;
	boolean double_edge=false;
	boolean any=false;
    String helpstring="ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	s="" + helpstring.substring(nr, nr+1) + ": ";
	if(vis.getMaxStep()==step) {
		s=s+"already visited";
		return s;
	}
	if(list[0] ==-1) s=s+"already visited";
	else {
		i=1;
		while(list[i] != -1) {
			if(list[i] == -2 && rm==4) double_edge=true;
			if(list[i] > -1) {
				if (!any) {
					any = true;
						if(double_edge) {
							s=s+"#"+helpstring.substring(list[i], list[i]+1);
						}
						else {
							s=s+helpstring.substring(list[i], list[i]+1);		
						}	
				}
				else {	
					if(double_edge) {
						s=s+", #"+helpstring.substring(list[i], list[i]+1);		
					}
					else {
						s=s+", "+helpstring.substring(list[i], list[i]+1);		
					}
				}	
			}
		i++;
		}
		if(!any) s= s + "no connection left";
	}
	return s;
}

/**
 * eliminates the parameter town in the edge list
 * @param number of town to eliminate
 */
public void eliminateTown(int town) {
	int j,k;

    //eliminate the town from the edge - list
    for(j=0;j<towns;j++) {
       k=0;
       while(edges[j][k] != -1) {
           k++;
           if(edges[j][k] ==town) {
               edges[j][k]=-3;
               edges[j][0]--;
           }
       }
   }

}

/**
 * asks if the parameter town can be the next town in the trip
 * @param 	index the edge list index of the actual town
 * @param 	town town to test
 * @return 	true if it can be, otherwise false
 */
public boolean isOption(int index, int town) {
	int i;

	if(vis.getMaxStep()==step) return false;
	i=1;
	while(edges[index][i] != -1) {
		if(edges[index][i] == town) return true;
		i++;
	}
	return false;
}	

/**
 * asks if there is a double connection to this town
 * @param   index edge list index of the actual town
 * @param   town town to test
 * @return  true for a double connection, otherwise false
 */
public boolean isDouble(int index, int town) {
    int i;
	boolean dbl=false;

    if(vis.getMaxStep()==step) return false;
    i=1;
    while(edges[index][i] != -1) {
		if(edges[index][i] == -2) dbl=true;
        if(edges[index][i] == town && dbl==true) return true;
        i++;
    }
    return false;
}


/**
 * asks if the town is already in the trip
 * @param   town town to test
 * @return  true if it is, otherwise false
 */
public boolean isUsed(int town) {

    int i;

	if(step<2) return false;
	for(i=0;i<(step-1);i++) {
		if(child.getGen(i)==town) return true;
	}
    return false;
}


/**
 * redefines the paint method, implements double buffering
 * @param g java.awt.Graphics
 */
public void paint(java.awt.Graphics g) {
    int i,j,k,l;
    String parent1l="";
    String parent2l="";
    String childl="";
	int length = 0;
	int height = 0;
	int x,y;
	int width, help;
	int fhhh=0;
	int fwhh=0;
	int akttown=0;
	boolean edgefault=false;

    setFont(g);
	y=this.getSize().height;
	x=this.getSize().width;
	help=(int) (x/2);

	if(help-2*fw>y-7*(fh+3) ) {
		width=  y-7*(fh+3);
		width= (int) (width/data.getGrid());
		fhhh= fhh+7*(fh+3);
		fwhh= fwh+fw;
	}
	else {
		width= help-2*fw;
		width= (int) (width/data.getGrid());
		fhhh= fhh+7*(fh+3);
		fwhh= fwh+fw;
	}

    buffered_image = this.createImage(this.getSize().width,
                                       this.getSize().height);
    Graphics gbuff = buffered_image.getGraphics();

    //set background
    gbuff.setColor(java.awt.Color.white);
    gbuff.fillRect(0,0,this.getSize().width,this.getSize().height);

    gbuff.setColor(java.awt.Color.black);
    setFont(gbuff);
    gbuff.setFont(f);

	if(child!=null) {

	if(rm==4) {
		gbuff.drawString(erx1,2*fw,fh+3);
	}
	else {
		gbuff.drawString(erx2,2*fw,fh+3);
	}

	if(step==0) akttown=-1;
	else akttown=child.getGen(step-1);

	// draw the trip of parent1
    	gbuff.setColor(java.awt.Color.black);
        for(i=0;i<towns;i++) {
            j=parent1.getGen(i);
            if(i<(towns-1)) k=parent1.getGen(i+1);
            else k=parent1.getGen(0);
			if(!isUsed(j) && !isUsed(k)) {
				if(akttown==j || akttown==k) {
    				gbuff.setColor(java.awt.Color.magenta);
            		gbuff.drawLine(coord[j][0]*width+fwhh,coord[j][1]*width+
						fhhh,coord[k][0]*width+fwhh,coord[k][1]*width+fhhh);
    				gbuff.setColor(java.awt.Color.black);
				}
				else {
            		gbuff.drawLine(coord[j][0]*width+fwhh,coord[j][1]*width+
						fhhh,coord[k][0]*width+fwhh,coord[k][1]*width+fhhh);
				}
			}
        }

	// draw the trip of parent2
    	gbuff.setColor(java.awt.Color.black);
        for(i=0;i<towns;i++) {
            j=parent2.getGen(i);
            if(i<(towns-1)) k=parent2.getGen(i+1);
            else k=parent2.getGen(0);
            if(!isUsed(j) && !isUsed(k)) {
                if(akttown==j || akttown==k) {
					if(isDouble(j,k) && rm==4) {
                    	gbuff.setColor(java.awt.Color.cyan);
                    	gbuff.drawLine(coord[j][0]*width+fwhh,coord[j][1]*width+
                        	fhhh,coord[k][0]*width+fwhh,coord[k][1]*width+fhhh);
                    	gbuff.setColor(java.awt.Color.black);
					}
					else {
                        gbuff.setColor(java.awt.Color.magenta);
                        gbuff.drawLine(coord[j][0]*width+fwhh,coord[j][1]*width+
                            fhhh,coord[k][0]*width+fwhh,coord[k][1]*width+fhhh);
                        gbuff.setColor(java.awt.Color.black);
					}
                }
                else {
					if(isDouble(j,k) && rm==4) {
    					gbuff.setColor(java.awt.Color.blue);
                    	gbuff.drawLine(coord[j][0]*width+fwhh,coord[j][1]*width+
                        	fhhh,coord[k][0]*width+fwhh,coord[k][1]*width+fhhh);
    					gbuff.setColor(java.awt.Color.black);
					}
					else {
                    	gbuff.drawLine(coord[j][0]*width+fwhh,coord[j][1]*width+
                        	fhhh,coord[k][0]*width+fwhh,coord[k][1]*width+fhhh);
					}	
                }
            }
        }

	// has an edgefault occured
		if(step>0) {
			if(vis.getInfo(step-1)==1) edgefault=true;
			else edgefault=false;
		}

	// mark the next town
    	gbuff.setColor(java.awt.Color.black);
		childl=child.toPopString(false);
		childl="   "+childl;
		if(step>0) {
			if(edgefault) vis.setStep(step-1,2);
			else vis.setStep(step-1,1);
			eliminateTown(child.getGen(step-1) );
		}
		if(vis.getMaxStep()==step) {
			for(i=0;i<towns;i++) {
				if(vis.getInfo(i)==1) vis.setStep(i,2);
				else vis.setStep(i,1);
			}
		}

	// draw the edge list
		length= (int) (x/2);	
		height=3*(fh+3);
		for(i=0;i<towns;i++) {
			if( step>0 && i== child.getGen(step-1) ) {
				gbuff.setColor(java.awt.Color.red);
				gbuff.drawString(toEdgeString(i, edges[i]),length,height);
        		gbuff.setColor(java.awt.Color.black);
			}
			else {
				if(step>0 && isOption(child.getGen(step-1), i) && rm==4) {
					gbuff.setColor(java.awt.Color.magenta);
                	gbuff.drawString(toEdgeString(i, edges[i]),length,height);
                	gbuff.setColor(java.awt.Color.black);
				}
				else gbuff.drawString(toEdgeString(i, edges[i]),length,height);
			}
			height+=fh+3;	
		}

    // draw the distance list for Erx with Heuristic
		if(rm==5) {
        	length= (int) (x/4);
			length=3*length;
        	height=3*(fh+3);
       		gbuff.setColor(java.awt.Color.black);
        	gbuff.drawString("Distance to town: ",length,height);
        	height+=fh+3;
       		gbuff.setColor(java.awt.Color.magenta);
           	if( step>0) {
        		for(i=0;i<towns;i++) {
               		if(isOption(child.getGen(step-1), i) ) {
                		gbuff.drawString(toEdgeString(i, edges[i]).substring(0,3),length,height);
                		gbuff.drawString(trimCoord(data.distance(child.getGen(step-1), i)) ,length+3*fw,height);
            			height+=fh+3;
					}
				}
        	}
       		gbuff.setColor(java.awt.Color.black);
		}


	// draw the parent1
        parent1l=parent1.toPopString(false);
        gbuff.setColor(java.awt.Color.black);
        length=2*fw;
        height=3*(fh+3);
        gbuff.drawString("Parent1: ",length,height);
        length=11*fw;
        gbuff.drawString(parent1l,length,height);

	// draw the parent2
        parent2l=parent2.toPopString(false);
        gbuff.setColor(java.awt.Color.black);
        length=2*fw;
        height=4*(fh+3);
        gbuff.drawString("Parent2: ",length,height);
        length=11*fw;
        gbuff.drawString(parent2l,length,height);

	// draw the string of the child
		length=2*fw;
		height=5*(fh+3);
        gbuff.drawString("Child: ",length,height);
        length=11*fw;
        gbuff.drawString(childl.substring(0,3),length,height);
        length+=fm.stringWidth(childl.substring(0,3));
		for(i=0;i<towns;i++) {
			if(vis.getStep(i) > 0) {
        		gbuff.drawString(childl.substring(i+3,i+4),length,height);
			}
			else {
                    gbuff.drawString("_",length,height);
			}
			length+=fw;
		}
		if(step>0) {
			edges[child.getGen(step-1)][0]=-1;
		}

	// draw the actual trip of the child
    	gbuff.setColor(java.awt.Color.red);
   		for(i=0;i<towns;i++) {
       		j=child.getGen(i);
        	if(i<(towns-1)) {
           		k=child.getGen(i+1);
           		l=i+1;
        	}
        	else {
           		k=child.getGen(0);
           		l=0;
        	}
        	if(vis.getStep(i)>0 && vis.getStep(l)==1) {
           		gbuff.drawLine(coord[j][0]*width+fwhh,coord[j][1]*width+fhhh,
           		coord[k][0]*width+fwhh,coord[k][1]*width+fhhh);
        	}
        	if(vis.getStep(i)>0 && vis.getStep(l)==2) {
    			gbuff.setColor(java.awt.Color.green);
           		gbuff.drawLine(coord[j][0]*width+fwhh,coord[j][1]*width+fhhh,
           		coord[k][0]*width+fwhh,coord[k][1]*width+fhhh);
    			gbuff.setColor(java.awt.Color.red);
        	}
		}

	// draw the towns
    k=1;
    for(i=0;i<towns;i++) {
        if(showtowns) {
            if(largefonts==0) {
            gbuff.drawOval(coord[i][0]*width+fwhh-fwh-3,coord[i][1]*width+fhhh-fhh-1, fw+4,fh+2);
            gbuff.setColor(java.awt.Color.white);
			if(akttown==i) gbuff.setColor(java.awt.Color.red);
            gbuff.fillOval(coord[i][0]*width+fwhh-fwh-3,coord[i][1]*width+fhhh-fhh-1, fw+4,fh+2);
            gbuff.setColor(java.awt.Color.black);
            gbuff.drawOval(coord[i][0]*width+fwhh-fwh-3,coord[i][1]*width+fhhh-fhh-1, fw+4,fh+2);
            }
            if(largefonts==1) {
            gbuff.drawOval(coord[i][0]*width+fwhh-fwh-3,coord[i][1]*width+fhhh-fhh-1, fw+4,fh+2);
            gbuff.setColor(java.awt.Color.white);
			if(akttown==i) gbuff.setColor(java.awt.Color.red);
            gbuff.fillOval(coord[i][0]*width+fwhh-fwh-3,coord[i][1]*width+fhhh-fhh-1, fw+4,fh+2);
            gbuff.setColor(java.awt.Color.black);
            gbuff.drawOval(coord[i][0]*width+fwhh-fwh-3,coord[i][1]*width+fhhh-fhh-1, fw+4,fh+2);
            }
            if(largefonts==2) {
            gbuff.drawOval(coord[i][0]*width+fwhh-fwh-2,coord[i][1]*width+fhhh-fhh-1, fw+4,fh+2);
            gbuff.setColor(java.awt.Color.white);
			if(akttown==i) gbuff.setColor(java.awt.Color.red);
            gbuff.fillOval(coord[i][0]*width+fwhh-fwh-2,coord[i][1]*width+fhhh-fhh-1, fw+4,fh+2);
            gbuff.setColor(java.awt.Color.black);
            gbuff.drawOval(coord[i][0]*width+fwhh-fwh-2,coord[i][1]*width+fhhh-fhh-1, fw+4,fh+2);
            }

            gbuff.drawString(helpstring.substring(k-1,k),
            coord[i][0]*width+fwhh-fwh,coord[i][1]*width+fhhh+fhh);
        k=k+1;
        }
        else {
        gbuff.drawOval(coord[i][0]*width+fwhh-2,coord[i][1]*width+fhhh-2,4,4);
		if(akttown==i) gbuff.setColor(java.awt.Color.red);
        gbuff.fillOval(coord[i][0]*width+fwhh-2,coord[i][1]*width+fhhh-2,4,4);
        gbuff.setColor(java.awt.Color.black);
        }
    }

    } //if child==null
    else {
        gbuff.drawString("Sorry, in this generation was no more recombination",2*fw,fh+3);
        gbuff.drawString("Close this panel and go on with the GA",2*fw,2*(fh+3));
    }

    g.drawImage(buffered_image,0,0,this);
    g.dispose();

    return;
}

}

