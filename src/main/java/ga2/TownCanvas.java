package ga2;import java.awt.*;

/**
 * This class visualizes the selection of the towns with the mouse.
 * 
 * coding:   h.sarg
 * modified: 01/98
 */
public class TownCanvas extends java.awt.Canvas implements java.awt.event.MouseListener {
/**
 * holds the towns
 */
	private int[][] townarray;
/**
 * distance from one town to its nearest impossible town width
 */
	private int stepwidth;
/**
 * distance from one town to its nearest impossible town height
 */
	private int stepheight;
/**
 * canvas width
 */
	private int canvaswidth=340;
/**
 * canvas height
 */
	private int canvasheight=340;
/**
 * grid of towns
 */
	private int grid=0;

/**
 * TownCanvas constructor comment.
 */
public TownCanvas() {
	super();
	this.addMouseListener(this);
}

/**
 * gets towns from the DialogData object and makes a copy of them
 * @param a array of towns
 * @param b grid
 */
public void setTownData(int[][] a, int b) {
    Dimension canvassize;
	this.grid = b;
	if(a == null) {
		this.townarray=new int[grid][grid];
        for(int i=0;i<grid;i++) {
            for(int j=0;j<grid;j++) {
                this.townarray[i][j]=0;
            }
        }
	}
	else {
		this.townarray=new int[grid][grid];
        for(int i=0;i<grid;i++) {
            for(int j=0;j<grid;j++) {
                this.townarray[i][j]=a[i][j];
            }
        }
    }

    stepwidth=(int)(canvaswidth/grid);
    stepheight=(int)(canvasheight/grid);

    //System.out.println("setTownData: x="+canvaswidth+" y="+canvasheight+"\n");
	repaint();
}
	
/**
 * adds towns randomly to the existing problem
 * @param a number of towns to add
 */
public void setTownsRandom(int a) {
	int x,y;
	int count=0;
	int number=getTowns();
	
	while((number+count<100) && (count<a)) {
		x=MyRandom.intRandom(0, grid-1);
		y=MyRandom.intRandom(0, grid-1);
		if(townarray[x][y]==0) {
			count++;
			townarray[x][y]=1;
		}
	}
	repaint();
}

/**
 * gets the actual townarray
 * @return a:	pointer to townarray
 */
public int [][]getTownArray() {
	return(this.townarray);
}

/**
 * sets the actual measurement of the canvas
 * @param a:	x value, scaled by grid
 * @param b:	y value, scaled by grid
 */
public void setTownCanvas(int a, int b) {
	if(grid!=0) {
		this.stepwidth=a;
		this.stepheight=b;
		this.canvaswidth=grid*a;
		this.canvasheight=grid*b;
		repaint();
	}
}

/**
 * clears all towns set till now
 */
public void clearTownCanvas() {
	for(int i=0;i<grid;i++) {
        for(int j=0;j<grid;j++) {
            townarray[i][j]=0;
        }
	}
	repaint();
}

/**
 * checks the townarray
 * @return	number of entries in the townarray
 */
public int getTowns() {
	int towns=0;
	for(int i=0;i<grid;i++) {
        for(int j=0;j<grid;j++) {
            if(townarray[i][j]==1) towns++;
        }
	}
	return (towns);
}

/**
 * redefines the update method for faster double buffering
 * @param g java.awt.Graphics
 */
public void update(Graphics g) {
	paint(g);
}

/**
 * redefines the paint method, implements double buffering
 * @param g java.awt.Graphics
 */
public void paint(java.awt.Graphics g) {
	int i,j;
	Image buffered_image = this.createImage(this.getSize().width,
											this.getSize().height);

	Graphics gbuff = buffered_image.getGraphics();
	
	gbuff.setColor(java.awt.Color.white);
	gbuff.fillRect(0,0,canvaswidth,canvasheight);
	gbuff.setColor(java.awt.Color.gray);

/*draw the grid
	for(i=0;i<=canvaswidth;i+=stepwidth) {
		gbuff.drawLine(i,0,i,canvasheight);
	};
	for(i=0;i<=canvasheight;i+=stepheight) {
		gbuff.drawLine(0,i,canvaswidth,i);
	}; */
		
//fill the grid with the actual towns
	for(i=0;i<grid;i++) {
		for(j=0;j<grid;j++) {
			if(townarray[i][j]==1) {
				gbuff.drawOval(i*stepwidth,j*stepheight,stepwidth,stepheight);
				gbuff.fillOval(i*stepwidth,j*stepheight,stepwidth,stepheight);
				if(stepheight-4 >= 0) {
					gbuff.setColor(java.awt.Color.blue);
					gbuff.drawOval(i*stepwidth+2,j*stepheight+2,stepwidth-4,stepheight-4);
					gbuff.fillOval(i*stepwidth+2,j*stepheight+2,stepwidth-4,stepheight-4);
					gbuff.setColor(java.awt.Color.gray);
				}
			}
		}
	}		
	g.drawImage(buffered_image,0,0,this);
	g.dispose();
	return;
}

/**
 * Clicking to a position in the canvas sets the corresponding town.
 * Is this town already set then the click removes it.
 * @param e java.awt.event.MouseEvent
 */
public void mouseClicked(java.awt.event.MouseEvent e) {
	int a,b;
    int x,y;

    x=e.getX();
    y=e.getY();

    if (x<canvaswidth && y<canvasheight){
        a=(int)x/stepwidth;
        b=(int)y/stepheight;
		if((a<grid) && (b<grid))
		{
        	if(townarray[a][b]==1) {
           		 townarray[a][b]=0;
        	}
        	else {
				if(getTowns()<100) {
           		 	townarray[a][b]=1;
				}
        	}
        	repaint();
		}
        //System.out.println("clicked: hallo"+a+" "+b+"Groesse: "+canvassize);
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
