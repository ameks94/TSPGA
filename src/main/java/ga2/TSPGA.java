package ga2;
import java.applet.*;
import java.awt.*;
import java.lang.*;

/**
 * This class includes the main class and handels the dialogs and frames.
 * 
 * coding:   h.sarg
 * modified: 01/98
 */
public class TSPGA extends Applet implements java.awt.event.ActionListener, java.awt.event.AdjustmentListener, java.awt.event.ItemListener, java.awt.event.WindowListener, java.awt.event.ComponentListener, Runnable,  ResultProcessor {
/**
 *	hold the actual step in a generation
 *  this means:	0	start position of the GA
 *				1	the selection is done
 *				2	the recombination is done
 *				3	the mutation is done
 *				4	the mutated individuals becoms evaluated
 */
	private int step = 0;
/**
 *	hold the actual generation
 */
	private int generation = 0;
/**
 *	Thread, when running the GA
 */
	Thread TspThread = null;
/**
 *	reserve Individual
 */
	private Individual helpindiv= null;
/**
 *	stop the running Thread
 */
	private boolean stop=false;
/**
 * only used to store the aktual individual number to show the recombination
 */
	private int aktindivnr = 0;
/**
 * only used to store the aktual step to show the recombination
 */
	private int aktstep = 0;
/**
 * array to hold result frames
 */
	private FinalFrame[] finalarray = new FinalFrame[20];
/**
 * result frame counter
 */
	private int finalcount = 0;

//--------------------ABOUT DIALOG--------------------------------------------
	private AboutDialog ivjAboutDialog = null;

//--------------------RUN DIALOG----------------------------------------------
	private RunDialog ivjRunDialog = null;

//--------------------Grid DIALOG-------------------------------------------
	private Dialog ivjGridDialog = null;
    private Panel ivjGridPanel2 = null;
    private Button ivjGridClose = null;
    private Label ivjGridGenLabel = null;
    private Button ivjGridOk = null;
    private Panel ivjGridPanel1 = null;
    private IntTextField ivjGridText = null;
    private Label ivjGridTextLabel = null;

//--------------------TOWN DIALOG---------------------------------------------
    private Dialog ivjTownDialog = null;
    private Panel ivjTownPanel2 = null;
    private Button ivjTownClose = null;
    private Label ivjTownGenLabel = null;
    private Button ivjTownOk = null;
    private Panel ivjTownPanel1 = null;
    private IntTextField ivjTownText = null;
    private Label ivjTownTextLabel = null;

//---------------------ERX FRAME----------------------------------------------
	private ErxCanvas ivjErxCanvas = null;
	private Button ivjErxClose = null;
	private Frame ivjErxFrame = null;
	private Panel ivjErxPanel = null;
	private Button ivjErxStep = null;
	private Button ivjErxResult = null;
	private Button ivjErxTrace = null;

//-------------------GA START-------------------------------------------------
	private Button ivjGaStart = null;

//-------------------OPTIONS DIALOG-------------------------------------------
	private Checkbox ivjOptionsCheckbox = null;
	private Button ivjOptionsClose = null;
	private Button ivjOptionsDefault = null;
	private Dialog ivjOptionsDialog = null;
	private Label ivjOptionsGroupLabel = null;
	private IntTextField ivjOptionsGroupText = null;
	private Choice ivjOptionsMutChoice = null;
	private Label ivjOptionsMutmetLabel = null;
	private Label ivjOptionsMutnumLabel = null;
	private Label ivjOptionsMutprobLabel = null;
	private Scrollbar ivjOptionsMutScroll = null;
	private Button ivjOptionsOk = null;
	private Label ivjOptionsPopLabel = null;
	private IntTextField ivjOptionsPopText = null;
	private Label ivjOptionsRecmetLabel = null;
	private Label ivjOptionsRecnumLabel = null;
	private Choice ivjOptionsRecombChoice = null;
	private Scrollbar ivjOptionsRecombScroll = null;
	private Label ivjOptionsRecprobLabel = null;


//--------------------PARENT FRAME--------------------------------------------
	private Label ivjParentFitLabel1 = null;
	private Label ivjParentFitLabel2 = null;
	private Label ivjParentFitLabel3 = null;
	private Frame ivjParentFrame = null;
	private GridLayout ivjParentFrameGridLayout = null;
	private Label ivjParentIndivLabel1 = null;
	private Label ivjParentIndivLabel2 = null;
	private Label ivjParentIndivLabel3 = null;
	private Panel ivjParentPanel1 = null;
	private Panel ivjParentPanel2 = null;
	private Panel ivjParentPanel3 = null;
	private TravelCanvas ivjParentTravelCanvas1 = null;
	private TravelCanvas ivjParentTravelCanvas2 = null;
	private TravelCanvas ivjParentTravelCanvas3 = null;

//--------------------POP FRAME-----------------------------------------------
	private PopCanvas ivjPopCanvas = null;
	private Frame ivjPopFrame = null;
	private ScrollPane ivjPopScrollPane = null;

//--------------------RECOMB FRAME--------------------------------------------
	private RecombCanvas ivjRecombCanvas = null;
	private Frame ivjRecombframe = null;
	private ScrollPane ivjRecombScrollPane = null;

//--------------------SET TOWNS DIALOG----------------------------------------
	private TownCanvas ivjSetTownCanvas = null;
	private Button ivjSetTownsClearAll = null;
	private Dialog ivjSetTownsDialog = null;
	private Button ivjSetTownsAdd = null;
	private Button ivjSetTownsSet = null;
	private Button ivjSetTownsOk = null;
	private Panel ivjSetTownsPanel = null;
	private Label ivjSetTownsLabel = null;

//-------------------STATISTIC FRAME------------------------------------------
	private CheckboxMenuItem ivjStartOptionsTowns = null;
	private MenuItem ivjStartSaveFinal = null;
	private Menu ivjStartOptionsMenu = null;
	private MenuItem ivjStartOptionsOptions = null;
	private MenuItem ivjStartOptionsProblem = null;
	private MenuItem ivjStartOptionsItem3 = null;
	private MenuItem ivjStartOptionsItem2 = null;
	private MenuItem ivjStartOptionsItem1 = null;
	private MenuItem ivjStartInfoAbout = null;
	private Menu ivjStartInfoMenu = null;
	private CheckboxMenuItem ivjStartOptionsFonts1 = null;
	private CheckboxMenuItem ivjStartOptionsFonts2 = null;
	private CheckboxMenuItem ivjStartOptionsFonts3 = null;
	private MenuItem ivjStartCalculateTSP = null;

    private MenuItem ivjStatisticGoItem3 = null;
    private MenuItem ivjStatisticGoItem2 = null;
    private MenuItem ivjStatisticGoItem1 = null;
	private Label ivjStatisticBestfitLabel = null;
	private Label ivjStatisticBestindivLabel = null;
	private Label ivjStatisticBestLabel = null;
	private StatisticCanvas ivjStatisticCanvas = null;
	private MenuItem ivjStatisticFileClose = null;
	private Menu ivjStatisticFileMenu = null;
	private Frame ivjStatisticFrame = null;
	private GridLayout ivjStatisticFrameGridLayout = null;
	private MenuBar ivjStatisticFrameMenuBar = null;
	private Label ivjStatisticGenLabel = null;
	private MenuItem ivjStatisticGoGen = null;
	private Menu ivjStatisticGoMenu = null;
	private MenuItem ivjStatisticGoRun = null;
	private MenuItem ivjStatisticGoStep = null;
	private Panel ivjStatisticLabelPanel = null;
	private Panel ivjStatisticLabelPanel1 = null;
	private FlowLayout ivjStatisticLabelPanelFlowLayout = null;
	private FlowLayout ivjStatisticLabelPanelFlowLayout1 = null;
	private Label ivjStatisticMeanfitLabel = null;
	private Label ivjStatisticMeanLabel = null;
	private ScrollPane ivjStatisticScrollPane = null;
	private Panel ivjStatisticStatisticPanel = null;
	private MenuItem ivjStatisticStopStop = null;
	private Panel ivjStatisticTravelPanel = null;
	private TravelCanvas ivjStatisticTravelCanvas = null;
	private Menu ivjStatisticWindowMenu = null;
	private MenuItem ivjStatisticWindowPop = null;
	private MenuItem ivjStatisticWindowRecomb = null;
	private MenuItem ivjStatisticSimulationSimulation = null;

//---------------------STRING FRAME-------------------------------------------
	private StringCanvas ivjStringCanvas = null;
	private Button ivjStringClose = null;
	private Frame ivjStringFrame = null;
	private Panel ivjStringPanel = null;
	private Button ivjStringStep = null;
	private Button ivjStringResult = null;
	private Button ivjStringTrace = null;

	private DialogData ivjDialogData = new DialogData();
	private TravelData ivjTravelData;

	private Algorithm ivjAlgorithm;

/**
 * Method to handle events for the ActionListener interface.
 * @param e java.awt.event.ActionEvent
 */
public void actionPerformed(java.awt.event.ActionEvent e) {
	Object source = e.getSource();

	if ((source == getStartOptionsOptions()) ) {
	    Dialog ResDialog;

        ResDialog=getOptionsDialog();
        ivjOptionsRecombChoice.select(ivjDialogData.rm);
        ivjOptionsMutChoice.select(ivjDialogData.mm);
        ivjOptionsRecombScroll.setValue(ivjDialogData.rp);
        ivjOptionsRecnumLabel.setText(ivjDialogData.rp+"%");
        ivjOptionsMutScroll.setValue(ivjDialogData.mp);
        ivjOptionsMutnumLabel.setText(ivjDialogData.mp+"%");
        ivjOptionsGroupText.setText(""+ivjDialogData.group);
        ivjOptionsPopText.setText(""+ivjDialogData.pop);
        ivjOptionsCheckbox.setState(ivjDialogData.elitism);
        ResDialog.show();
	}

	if ((source == getGaStart()) ) {
		initConnections();
		if(ivjStatisticFrame.isShowing()) {
			if(TspThread != null) {
           		 getStatisticStopStop().setEnabled(false);
           		 getStatisticGoGen().setEnabled(true);
       		     getStatisticGoStep().setEnabled(true);
           		 getStatisticWindowMenu().setEnabled(true);
        	    getStartOptionsMenu().setEnabled(true);
           		 TspThread.stop();
           		 TspThread = null;
        	}
       		 ivjStatisticCanvas.clear();
        	toStart();
        	disposeFrames();
        	getStatisticFrame().dispose();
        	ivjStatisticFrameMenuBar=null;

			if(ivjGaStart.isShowing()) {
				ivjGaStart.setLabel("Start");
			}
			else {
        		this.destroy();
        		System.exit(0);
			}

		}
		else {
			getStatisticFrame();
			//ivjStatisticFrame.setSize(800,300);
			getStatisticFrame().show();
			ivjGaStart.setLabel("Stop");
		}
	}
	if ((source == getStartCalculateTSP()) ) {
		Font f=null;
		step=0;
		generation=0;
		Individual bestindiv = null;

		getStartOptionsTowns().setEnabled(false);
		getStartOptionsFonts1().setEnabled(false);
		getStartOptionsFonts().setEnabled(false);
		getStartOptionsFonts3().setEnabled(false);

    	ivjStatisticCanvas.clear();

	// initialize the GA
		ivjTravelData =new TravelData(ivjDialogData);
		ivjAlgorithm = new Algorithm(ivjDialogData, ivjTravelData);

	// initialize PopCanvas, RecombCanvas, StatisticCanvas
		ivjPopCanvas.setPopData(ivjStatisticScrollPane, ivjDialogData.pop,ivjTravelData,ivjDialogData.showtowns, ivjDialogData.largefonts,ivjDialogData.grid);
		ivjPopCanvas.setPop(ivjAlgorithm.getPop(1) );

		ivjRecombCanvas.setRecombData(ivjRecombScrollPane, ivjDialogData.pop,ivjAlgorithm.getSelectionSize(),ivjTravelData,ivjDialogData.showtowns, ivjDialogData.largefonts,ivjDialogData.grid, ivjDialogData.mm);

		ivjStatisticTravelCanvas.setTravelData(ivjTravelData,ivjDialogData.showtowns, ivjDialogData.largefonts);
		bestindiv=ivjAlgorithm.getPop(1).getBest();
		ivjStatisticTravelCanvas.setTravelIndiv(bestindiv);

	    if(ivjDialogData.largefonts==1)  {
        	f = new Font("Monospaced",Font.PLAIN, 12);
    	}
    	if(ivjDialogData.largefonts==0)  {
       		f = new Font("Monospaced",Font.PLAIN, 11);
    	}
    	if(ivjDialogData.largefonts==2)  {
        	f = new Font("Monospaced",Font.PLAIN, 13);
    	}

		ivjStatisticBestfitLabel.setFont(f);
		ivjStatisticBestindivLabel.setFont(f);
		ivjStatisticMeanfitLabel.setFont(f);
		ivjStatisticGenLabel.setFont(f);
		ivjStatisticMeanLabel.setFont(f);
		ivjStatisticBestLabel.setFont(f);

		ivjStatisticBestfitLabel.setText("Best Fitness: " + bestindiv.trimFit() );
    	ivjStatisticBestindivLabel.setText("The best trip for now: " + bestindiv.toLabelString());
		helpindiv= new Individual(ivjDialogData.towns);
		helpindiv.setFitness(ivjAlgorithm.getPop(1).getMeanFit() );
		ivjStatisticMeanfitLabel.setText("Mean Fitness: "+helpindiv.trimFit()); 
		ivjStatisticGenLabel.setText("Gen.: "+generation +"   ");
		ivjStatisticCanvas.setData(ivjStatisticScrollPane);
		ivjStatisticCanvas.setValues(ivjAlgorithm.getPop(1).getBestFit(), 
										ivjAlgorithm.getPop(1).getMeanFit() );
		
		ivjStatisticBestfitLabel.invalidate();
		ivjStatisticBestindivLabel.invalidate();
		ivjStatisticMeanfitLabel.invalidate();
		ivjStatisticGenLabel.invalidate();
		ivjStatisticMeanLabel.invalidate();
		ivjStatisticBestLabel.invalidate();
		getStatisticLabelPanel1().doLayout();
		getStatisticLabelPanel().doLayout();
		
		ivjStatisticGoGen.setEnabled(true);
		ivjStatisticGoRun.setEnabled(true);
		ivjStatisticWindowMenu.setEnabled(true);
    	ivjStartSaveFinal.setEnabled(true);

		if(ivjDialogData.towns>26) {
			getStatisticGoStep().setEnabled(false);
        	getStatisticWindowMenu().setEnabled(false);
		}
		else {
			getStatisticGoStep().setEnabled(true);
			getStatisticWindowPop().setEnabled(false);
			getPopFrame().show();
			getRecombframe();
			ivjRecombCanvas.setSelection(null);
			getStatisticWindowRecomb().setEnabled(false);
			getRecombframe().show();
		}

	}
	if ((source == getOptionsDefault()) ) {
		ivjOptionsRecombChoice.select(DialogData.DEF_RM);
		ivjOptionsMutChoice.select(DialogData.DEF_MM);
		ivjOptionsRecombScroll.setValue(DialogData.DEF_RP);
		ivjOptionsRecnumLabel.setText(DialogData.DEF_RP+"%");
		ivjOptionsMutScroll.setValue(DialogData.DEF_MP);
		ivjOptionsMutnumLabel.setText(DialogData.DEF_MP+"%");
		ivjOptionsGroupText.setText(""+DialogData.DEF_GROUP);
		ivjOptionsPopText.setText(""+DialogData.DEF_POP);
		ivjOptionsCheckbox.setState(DialogData.DEF_ELITISM);
	}
	if ((source == getOptionsOk()) ) {
		if(ivjOptionsPopText.isValid() && ivjOptionsGroupText.isValid() ) {
			toStart();
			ivjDialogData.setDialogData(ivjOptionsRecombChoice.getSelectedIndex(),
									ivjOptionsRecombScroll.getValue(),
									ivjOptionsMutChoice.getSelectedIndex(),
									ivjOptionsMutScroll.getValue(),
									ivjOptionsPopText.getValue(),
									ivjOptionsGroupText.getValue(),
									ivjOptionsCheckbox.getState());
						System.out.println("hallo Dialog:" + ivjDialogData);
									getOptionsDialog().dispose();
		}
		//disposeFrames();

	}
	if ((source == getOptionsClose()) ) {
		getOptionsDialog().dispose();
	}
	if ((source == getStatisticFileClose()) ) {
		if(TspThread != null) {
            getStatisticStopStop().setEnabled(false);
            getStatisticGoGen().setEnabled(true);
           	getStatisticGoStep().setEnabled(true);
           	getStatisticWindowMenu().setEnabled(true);
			getStartOptionsMenu().setEnabled(true);
			TspThread.stop();
			TspThread = null;
		}
    	ivjStatisticCanvas.clear();
		toStart();
		disposeFrames();
		getStatisticFrame().dispose();
		ivjStatisticFrameMenuBar=null;
		
		if(ivjGaStart.isShowing()) {
			 ivjGaStart.setLabel("Start");
		}
		else {
			this.destroy();
			System.exit(0);
		}
	}
	if ((source == getStatisticSimulationSimulation()) ) {
		Individual[] indivarray;
		int i;
		int selectsize;

	// store the information about the crossover
		ivjAlgorithm.setSim();
        ivjAlgorithm.crossover();
		step++;

	// show the childs
        ivjRecombCanvas.setPop(ivjAlgorithm.getPop(2));
        ivjStatisticSimulationSimulation.setEnabled(false);
        ivjStatisticGoMenu.setEnabled(false);
        ivjStatisticWindowMenu.setEnabled(false);
		ivjStartOptionsMenu.setEnabled(false);

	// crossover methods from 0 to 3
		if(ivjDialogData.rm < 4) {
			selectsize=ivjAlgorithm.getSelectionSize();
			getStringCanvas().setData(selectsize, ivjDialogData.towns, 
						ivjDialogData.largefonts, ivjDialogData.rm);
			indivarray=ivjAlgorithm.getSelection();
			i=0;
			while(i<selectsize && ivjAlgorithm.getPop(2).getIndiv(i/2).getVisRecomb()==null) {
				i+=2;
			}
			if(i<selectsize) {
				ivjStringCanvas.setRecomb(indivarray[i], indivarray[i+1],
									ivjAlgorithm.getPop(2).getIndiv(i/2) );
				aktstep=0;
				ivjStringCanvas.setStep(aktstep);
				aktindivnr=i/2;
				ivjStringTrace.setEnabled(true);
			}
			else {
				ivjStringCanvas.setRecomb(null,null,null);
				aktindivnr=-1;
			}
			getStringFrame();
			ivjStringResult.setEnabled(false);
			getStringFrame().show();
		}
	// crossovermethods 4 and 5 (ERX and ERX with Heuristic)
		else {
            selectsize=ivjAlgorithm.getSelectionSize();
            getErxCanvas().setData(ivjTravelData,selectsize,ivjDialogData.towns,
           ivjDialogData.largefonts, ivjDialogData.rm, ivjDialogData.showtowns);
            indivarray=ivjAlgorithm.getSelection();
            i=0;
            while(i<selectsize && ivjAlgorithm.getPop(2).getIndiv(i/2).getVisRecomb()==null) {
                i+=2;
            }
            if(i<selectsize) {
                ivjErxCanvas.setRecomb(indivarray[i], indivarray[i+1],
                                    ivjAlgorithm.getPop(2).getIndiv(i/2) );
                aktstep=0;
                ivjErxCanvas.setStep(aktstep);
                aktindivnr=i/2;
            	ivjErxTrace.setEnabled(true);
            }
            else {
                ivjErxCanvas.setRecomb(null,null,null);
                aktindivnr=-1;
            }

            getErxFrame();
            ivjErxResult.setEnabled(false);
			getErxFrame().show();
		}
	}
	if ((source == getStringResult()) ) {
        Individual[] indivarray;
        int selectsize;
        Individual aktindiv;

		selectsize=ivjAlgorithm.getSelectionSize();
		indivarray=ivjAlgorithm.getSelection();
		aktindiv=ivjAlgorithm.getPop(2).getIndiv(aktindivnr);

        getParentFrame();
        ivjParentTravelCanvas1.setTravelData(ivjTravelData,ivjDialogData.showtowns, ivjDialogData.largefonts);
        ivjParentTravelCanvas1.setTravelIndiv(indivarray[aktindivnr*2]);
        ivjParentFitLabel1.setText("Fitness: " + indivarray[aktindivnr*2].trimFit() );
        ivjParentIndivLabel1.setText(indivarray[aktindivnr*2].toPopString(false) );

        ivjParentTravelCanvas2.setTravelData(ivjTravelData,ivjDialogData.showtowns, ivjDialogData.largefonts);
        ivjParentTravelCanvas2.setTravelIndiv(indivarray[1+aktindivnr*2]);
        ivjParentFitLabel2.setText("Fitness: " + indivarray[1+aktindivnr*2].trimFit() );
        ivjParentIndivLabel2.setText(indivarray[1+aktindivnr*2].toPopString(false) );

        ivjParentTravelCanvas3.setTravelData(ivjTravelData,ivjDialogData.showtowns, ivjDialogData.largefonts);
        ivjParentTravelCanvas3.setTravelIndiv(aktindiv);
        ivjParentFitLabel3.setText("Fitness: " + aktindiv.trimFit() );
        ivjParentIndivLabel3.setText(aktindiv.toPopString(false) );

		ivjStringResult.setEnabled(false);
        getParentFrame().show();
	}	
	if ((source == getStringTrace()) ) {
        Individual[] indivarray;
        int i;
        int selectsize;
        Individual aktindiv;

        if(aktindivnr>-1) {
            selectsize=ivjAlgorithm.getSelectionSize();
            indivarray=ivjAlgorithm.getSelection();
            aktindiv=ivjAlgorithm.getPop(2).getIndiv(aktindivnr);

			
            aktstep=aktindiv.getVisRecomb().getMaxStep();
            ivjStringResult.setEnabled(true);
            ivjStringCanvas.setStep(aktstep);
			ivjStringTrace.setEnabled(false);
        }
	}
	if ((source == getStringStep()) ) {
		Individual[] indivarray;
		int i;
		int selectsize;
		Individual aktindiv;

		if(aktindivnr>-1) {
			selectsize=ivjAlgorithm.getSelectionSize();
			indivarray=ivjAlgorithm.getSelection();
			aktindiv=ivjAlgorithm.getPop(2).getIndiv(aktindivnr);
		
			aktstep++;
			if(aktstep<=aktindiv.getVisRecomb().getMaxStep()) {
       			if(aktstep==aktindiv.getVisRecomb().getMaxStep()) {
					ivjStringResult.setEnabled(true);
					ivjStringTrace.setEnabled(false);
				}
				ivjStringCanvas.setStep(aktstep);
			}
			else {
				i=2+aktindivnr*2;
				while(i<selectsize && ivjAlgorithm.getPop(2).getIndiv(i/2).getVisRecomb()==null) {
					i+=2;
				}
				getParentFrame().dispose();
				ivjStringResult.setEnabled(false);
				if(i<selectsize) {
					ivjStringCanvas.setRecomb(indivarray[i], indivarray[i+1],
									ivjAlgorithm.getPop(2).getIndiv(i/2) );
					aktstep=0;
					ivjStringCanvas.setStep(aktstep);
					aktindivnr=i/2;
					ivjStringTrace.setEnabled(true);
				}
				else {
					ivjStringCanvas.setRecomb(null,null,null);
					aktindivnr=-1;
				}
			}
		}
	}
	if ((source == getErxResult()) ) {
        Individual[] indivarray;
        int selectsize;
        Individual aktindiv;

        selectsize=ivjAlgorithm.getSelectionSize();
        indivarray=ivjAlgorithm.getSelection();
        aktindiv=ivjAlgorithm.getPop(2).getIndiv(aktindivnr);

        getParentFrame();
        ivjParentTravelCanvas1.setTravelData(ivjTravelData,ivjDialogData.showtowns, ivjDialogData.largefonts);
        ivjParentTravelCanvas1.setTravelIndiv(indivarray[aktindivnr*2]);
        ivjParentFitLabel1.setText("Fitness: " + indivarray[aktindivnr*2].trimFit() );
        ivjParentIndivLabel1.setText(indivarray[aktindivnr*2].toPopString(false)
 );

        ivjParentTravelCanvas2.setTravelData(ivjTravelData,ivjDialogData.showtowns, ivjDialogData.largefonts);
        ivjParentTravelCanvas2.setTravelIndiv(indivarray[1+aktindivnr*2]);
        ivjParentFitLabel2.setText("Fitness: " + indivarray[1+aktindivnr*2].trimFit() );
        ivjParentIndivLabel2.setText(indivarray[1+aktindivnr*2].toPopString(false) );

        ivjParentTravelCanvas3.setTravelData(ivjTravelData,ivjDialogData.showtowns, ivjDialogData.largefonts);
        ivjParentTravelCanvas3.setTravelIndiv(aktindiv);
        ivjParentFitLabel3.setText("Fitness: " + aktindiv.trimFit() );
        ivjParentIndivLabel3.setText(aktindiv.toPopString(false) );

        ivjErxResult.setEnabled(false);
        getParentFrame().show();
	}
	if ((source == getErxTrace()) ) {
        Individual[] indivarray;
        int i;
        int selectsize;
        Individual aktindiv;

        if(aktindivnr>-1) {
            selectsize=ivjAlgorithm.getSelectionSize();
            indivarray=ivjAlgorithm.getSelection();
            aktindiv=ivjAlgorithm.getPop(2).getIndiv(aktindivnr);


            aktstep=aktindiv.getVisRecomb().getMaxStep();
            ivjErxResult.setEnabled(true);
            ivjErxCanvas.setStep(aktstep);
            ivjErxTrace.setEnabled(false);
        }
	}
	if ((source == getErxStep()) ) {
		Individual[] indivarray;
		int i;
		int selectsize;
		Individual aktindiv;

		if(aktindivnr>-1) {
			selectsize=ivjAlgorithm.getSelectionSize();
			indivarray=ivjAlgorithm.getSelection();
			aktindiv=ivjAlgorithm.getPop(2).getIndiv(aktindivnr);
		
			aktstep++;
			if(aktstep<=aktindiv.getVisRecomb().getMaxStep()) {
				if(aktstep==aktindiv.getVisRecomb().getMaxStep()) {
                    ivjErxResult.setEnabled(true);
                    ivjErxTrace.setEnabled(false);
				}
				ivjErxCanvas.setStep(aktstep);
			}
			else {
				i=2+aktindivnr*2;
				while(i<selectsize && ivjAlgorithm.getPop(2).getIndiv(i/2).getVisRecomb()==null) {
					i+=2;
				}
				getParentFrame().dispose();
				ivjErxResult.setEnabled(false);
				if(i<selectsize) {
					ivjErxCanvas.setRecomb(indivarray[i], indivarray[i+1],
									ivjAlgorithm.getPop(2).getIndiv(i/2) );
					aktstep=0;
					ivjErxCanvas.setStep(aktstep);
					aktindivnr=i/2;
					ivjErxTrace.setEnabled(true);
				}
				else {
					ivjErxCanvas.setRecomb(null,null,null);
					aktindivnr=-1;
				}
			}
		}
	}
	if ((source == getStringClose()) ) {
		Population pop;
		pop=ivjAlgorithm.getPop(2);
		for(int i=0;i<(ivjAlgorithm.getSelectionSize()/2);i++) {
			pop.getIndiv(i).delVisRecomb();
		}
		ivjAlgorithm.unsetSim();
        ivjStatisticGoMenu.setEnabled(true);
		ivjStatisticSimulationSimulation.setEnabled(false);
		ivjStartOptionsMenu.setEnabled(true);
		if(ivjDialogData.towns<27) {
        	ivjStatisticWindowMenu.setEnabled(true);
		}

		getStringFrame().dispose();
	}
	if ((source == getStatisticGoStep() ) ) {
		getStartOptionsMenu().setEnabled(false);
        getStatisticGoStep().setEnabled(false);
		if(step==0) {
			ivjAlgorithm.selection();
			ivjRecombCanvas.setSelection(ivjAlgorithm.getSelection());
			ivjRecombCanvas.setPop(null);
			ivjRecombCanvas.setMut(null);
			if(ivjDialogData.towns<27 && ivjDialogData.rm!=6) {
				ivjStatisticSimulationSimulation.setEnabled(true);
			}
		}
		if(step==1) {
			ivjAlgorithm.crossover();
			ivjRecombCanvas.setPop(ivjAlgorithm.getPop(2));	
			ivjStatisticSimulationSimulation.setEnabled(false);
		}
		if(step==2) {
			ivjAlgorithm.mutation();
			ivjRecombCanvas.setPop(ivjAlgorithm.getPop(2));	
			ivjRecombCanvas.setMut(ivjAlgorithm.getPop(3));	
		}
		if(step==3) {
			ivjAlgorithm.evaluate();
			ivjRecombCanvas.setMut(ivjAlgorithm.getPop(3));	
		}
		if(step==4) {
			Individual bestindiv = null;
			ivjAlgorithm.populate();
			ivjPopCanvas.setPop(ivjAlgorithm.getPop(1) );
        	bestindiv=ivjAlgorithm.getPop(1).getBest();
        	ivjStatisticTravelCanvas.setTravelIndiv(bestindiv);
        	ivjStatisticBestfitLabel.setText("Best Fitness: " + bestindiv.trimFit() );
        	ivjStatisticBestindivLabel.setText("The best trip for now: " + bestindiv.toLabelString() );
			helpindiv.setFitness(ivjAlgorithm.getPop(1).getMeanFit() );
			ivjStatisticMeanfitLabel.setText("Mean Fitness: "+helpindiv.trimFit()); 
			ivjStatisticCanvas.setValues(ivjAlgorithm.getPop(1).getBestFit(), 
										ivjAlgorithm.getPop(1).getMeanFit() );

		}
		if(step==4) {
			step=0;
			generation++;
		ivjStatisticGenLabel.setText("Gen.: "+generation);
		}
		else step++;

        getStartOptionsMenu().setEnabled(true);
        getStatisticGoStep().setEnabled(true);
	}
	if ((source == getStatisticGoGen() ) ) {
        getStatisticGoGen().setEnabled(false);
        getStartOptionsMenu().setEnabled(false);
        if(step<1) {
            ivjAlgorithm.selection();
            ivjRecombCanvas.setSelection(ivjAlgorithm.getSelection());
            //ivjRecombCanvas.setPop(null);
            //ivjRecombCanvas.setMut(null);
        }
        if(step<2) {
            ivjAlgorithm.crossover();
			ivjStatisticSimulationSimulation.setEnabled(false);
            //ivjRecombCanvas.setPop(ivjAlgorithm.getPop(2));
        }
        if(step<3) {
            ivjAlgorithm.mutation();
			ivjRecombCanvas.setPop(ivjAlgorithm.getPop(2));	
            //ivjRecombCanvas.setMut(ivjAlgorithm.getPop(3));
        }
        if(step<4) {
            ivjAlgorithm.evaluate();
            ivjRecombCanvas.setMut(ivjAlgorithm.getPop(3));
        }
        if(step<5) {
			Individual bestindiv = null;
            ivjAlgorithm.populate();
            ivjPopCanvas.setPop(ivjAlgorithm.getPop(1) );
        	bestindiv=ivjAlgorithm.getPop(1).getBest();
			ivjStatisticTravelCanvas.setTravelIndiv(bestindiv);
            ivjStatisticBestfitLabel.setText("Best Fitness: " + bestindiv.trimFit() );
            ivjStatisticBestindivLabel.setText("The best trip for now: " + bestindiv.toLabelString() );
			helpindiv.setFitness(ivjAlgorithm.getPop(1).getMeanFit() );
			ivjStatisticMeanfitLabel.setText("Mean Fitness: "+helpindiv.trimFit()); 
			ivjStatisticCanvas.setValues(ivjAlgorithm.getPop(1).getBestFit(), 
										ivjAlgorithm.getPop(1).getMeanFit() );

        }
        step=0;
		generation++;
		ivjStatisticGenLabel.setText("Gen.: "+generation);

        getStatisticGoGen().setEnabled(true);
        getStartOptionsMenu().setEnabled(true);
	}
	if ((source == getStatisticGoRun()) ) {
		TspThread=null;
		ConnectInfo in=new ConnectInfo(ivjDialogData.update,ivjDialogData.genstep);
		getRunDialog(this,in).show();
	}
	if ((source == getStatisticStopStop()) ) {
		if(!stop) stop=true;
		if(ivjDialogData.towns<27) {
			getStatisticWindowMenu().setEnabled(true);
		}
	}
	if ((source == getStartInfoAbout()) ) {
		getAboutDialog().show();
	}
	if ((source == getSetTownsOk()) ) {
		if(ivjSetTownCanvas.getTowns()>3) {
			ivjDialogData.townarray=ivjSetTownCanvas.getTownArray();
			ivjDialogData.towns=ivjSetTownCanvas.getTowns();
			getSetTownsDialog().dispose();
		}
    	ivjStatisticCanvas.clear();
		toStart();
		disposeFrames();
	}
	if ((source == getSetTownsClearAll()) ) {
		ivjSetTownCanvas.clearTownCanvas();
		ivjDialogData.towns=0;
	}
	if ((source == getStatisticWindowPop()) ) {
		ivjPopCanvas.setPop(ivjAlgorithm.getPop(1) );
		ivjPopScrollPane.setScrollPosition(0,0);
		ivjPopScrollPane.doLayout();
		ivjStatisticWindowPop.setEnabled(false);
		getPopFrame().show();
	}
	if ((source == getStatisticWindowRecomb()) ) {
		if(step==0) ivjRecombCanvas.setSelection(null);
		if(step==1) {
			ivjRecombCanvas.setSelection(ivjAlgorithm.getSelection());
       		ivjRecombCanvas.setPop(null);
           	ivjRecombCanvas.setMut(null);
		}
		if(step==2) {
			ivjRecombCanvas.setSelection(ivjAlgorithm.getSelection());
			ivjRecombCanvas.setPop(ivjAlgorithm.getPop(2));
           	ivjRecombCanvas.setMut(null);
		}
		if(step==3) {
			ivjRecombCanvas.setSelection(ivjAlgorithm.getSelection());
			ivjRecombCanvas.setPop(ivjAlgorithm.getPop(2));
			ivjRecombCanvas.setMut(ivjAlgorithm.getPop(3));
		}
		if(step==4) {
			ivjRecombCanvas.setSelection(ivjAlgorithm.getSelection());
			ivjRecombCanvas.setPop(ivjAlgorithm.getPop(2));
			ivjRecombCanvas.setMut(ivjAlgorithm.getPop(3));
		}
		ivjRecombScrollPane.setScrollPosition(0,0);
		ivjRecombScrollPane.doLayout();
		ivjStatisticWindowRecomb.setEnabled(false);
		getRecombframe().show();
	}
	if ((source == getErxClose()) ) {
        Population pop;
        pop=ivjAlgorithm.getPop(2);
        for(int i=0;i<(ivjAlgorithm.getSelectionSize()/2);i++) {
            pop.getIndiv(i).delVisRecomb();
        }
        ivjAlgorithm.unsetSim();
        ivjStatisticGoMenu.setEnabled(true);
        ivjStatisticSimulationSimulation.setEnabled(false);
		ivjStartOptionsMenu.setEnabled(true);
        if(ivjDialogData.towns<27) {
            ivjStatisticWindowMenu.setEnabled(true);
        }

		getErxFrame().dispose();
	}
	if ((source == getStringClose()) ) {
		getParentFrame().dispose();
	}

	if ((source == getStartSaveFinal()) ) {
    if(ivjStatisticCanvas.getBest().size()>0) {
        if(finalcount<20||checkFrames()) {
            finalarray[finalcount]= new FinalFrame();

            finalarray[finalcount].setTitle(ivjDialogData.toTitle());
            finalarray[finalcount].setData(
                            ivjDialogData,
                            ivjTravelData,
                            ivjStatisticBestfitLabel.getText(),
                            ivjStatisticMeanfitLabel.getText(),
                            ivjStatisticGenLabel.getText(),
                            ivjStatisticBestindivLabel.getText(),
                            ivjStatisticCanvas.getBest(),
                            ivjStatisticCanvas.getMean(),
                            ivjAlgorithm.getPop(1).getBest());
            finalarray[finalcount].show();
            finalcount++;
        }
    }

	}

	if ((source == getErxClose()) ) {
		getParentFrame().dispose();
	}
    if ((source == getGridClose()) ) {
		getGridDialog().dispose();
    }
    if ((source == getGridOk()) ) {
        if(ivjGridText.isValid()) {
        	ivjDialogData.grid=ivjGridText.getValue();
			ivjDialogData.townarray=null;
			ivjDialogData.towns=0;
			getSetTownCanvas().setTownData(ivjDialogData.townarray, ivjDialogData.grid);
			getGridDialog().dispose();
		}
    }
    if ((source == getTownClose()) ) {
		getTownDialog().dispose();
    }
    if ((source == getTownOk()) ) {
        if(ivjTownText.isValid()) {
			int help;
			help=ivjTownText.getValue();

			ivjSetTownCanvas.setTownsRandom(help);
			ivjDialogData.towns=ivjSetTownCanvas.getTowns();

            getTownDialog().dispose();
        }
    }
    if ((source == getStartOptionsProblem()) ) {
		getSetTownCanvas().setTownData(ivjDialogData.townarray, ivjDialogData.grid);
		getSetTownsDialog().show();
    }
    if ((source == getSetTownsAdd()) ) {
		getTownDialog();
		ivjTownText.setText("5");
		getTownDialog().show();
    }
    if ((source == getSetTownsSet()) ) {
		getGridDialog();
		ivjGridText.setText("" + ivjDialogData.grid);
		getGridDialog().show();
    }

}

/**
 * initializes the application to start a new GA
 */
public void toStart() {
	getStatisticSimulationSimulation().setEnabled(false);
    getStatisticGoStep().setEnabled(false);
    getStatisticGoGen().setEnabled(false);
    getStatisticGoRun().setEnabled(false);
	getStatisticStopStop().setEnabled(false);
    getStatisticWindowMenu().setEnabled(false);
    getStartCalculateTSP().setEnabled(true);
    getStatisticGoMenu().setEnabled(true);
    ivjStatisticTravelCanvas.setTravelIndiv(null);
    getStartOptionsTowns().setEnabled(true);
    getStartOptionsFonts1().setEnabled(true);
    getStartOptionsFonts().setEnabled(true);
    getStartOptionsFonts3().setEnabled(true);
    getStartSaveFinal().setEnabled(false);


    ivjStatisticCanvas.clear();
    ivjStatisticBestfitLabel.setText("Best Fitness: 0.0");
    ivjStatisticBestindivLabel.setText("The best trip for now: ");
    ivjStatisticMeanfitLabel.setText("Mean Fitness: 0.0");
    ivjStatisticGenLabel.setText("Gen.: 0");

    getPopCanvas().disposeFrames();
    getRecombCanvas().disposeFrames();
    getPopFrame().dispose();
    getRecombframe().dispose();
    getErxFrame().dispose();
    getParentFrame().dispose();
    getStringFrame().dispose();
}
/**
 * after successfull dialog the run thread gets started
 * @param	source who initiates the dialog
 * @param	result information about the dialog
 */
public void processResult(Dialog source, Object result) {
	if (source instanceof RunDialog) {
		ConnectInfo info = (ConnectInfo)result;
		ivjDialogData.genstep=info.value;
		ivjDialogData.update=info.ok;
		System.out.println(ivjDialogData);
		getStatisticStopStop().setEnabled(true);
		getStatisticWindowMenu().setEnabled(false);
		getStatisticGoGen().setEnabled(false);
		getStatisticGoStep().setEnabled(false);
		getStatisticGoRun().setEnabled(false);
		getStartCalculateTSP().setEnabled(false);
        getStartOptionsMenu().setEnabled(false);
		
		if(TspThread == null) {
			TspThread=new Thread (this);
			TspThread.start();
			TspThread.setPriority(Thread.MIN_PRIORITY);
		}
	}
}
	
/**
 * checks if there are invalid frames stored
 * @return true if there is a free place in the array, otherwise false
 */
public boolean checkFrames() {
    int index=-1;
    boolean state=false;

    for(int i=0;i<20;i++) {
        if(finalarray[i].isShowing() ) {
            if(index>-1) {
                finalarray[index]=finalarray[i];
                index+=1;
            }
        }
        else {
            if(index==-1) index=i;
            finalcount--;
            state=true;
        }
    }
    System.out.println("showing:" + finalcount+ "index= "+index+"\n");
    return state;
}

/**
 * disposes all FinalFrames and is called before the parent window is
 * closed or a new TSP is set
 */
public void disposeFrames() {
    for(int i=0;i<finalcount;i++) finalarray[i].dispose();
	finalcount=0;
}


/**
 * starts a thread, each loop is one generation, only the population canvas, 
 * the statistic canvas and the besttrip canvas are updated
 */
public void run() {
	if(step>0) {
		if(step<2) {
			ivjAlgorithm.crossover();
			ivjStatisticSimulationSimulation.setEnabled(false);
			ivjRecombCanvas.setSelection(null);
			//ivjRecombCanvas.setPop(ivjAlgorithm.getPop(2));
		}
		if(step<3) {
			ivjAlgorithm.mutation();
			ivjRecombCanvas.setSelection(null);
			//ivjRecombCanvas.setMut(ivjAlgorithm.getPop(3));
		}
		if(step<4) {
			ivjAlgorithm.evaluate();
			ivjRecombCanvas.setSelection(null);
			//ivjRecombCanvas.setMut(ivjAlgorithm.getPop(3));
		}
		if(step<5) {
			Individual bestindiv = null;
			ivjAlgorithm.populate();
			ivjPopCanvas.setPop(ivjAlgorithm.getPop(1) );
			bestindiv=ivjAlgorithm.getPop(1).getBest();
			ivjStatisticTravelCanvas.setTravelIndiv(bestindiv);
			ivjStatisticBestfitLabel.setText("Best Fitness: " + bestindiv.trimFit() );
			ivjStatisticBestindivLabel.setText("The best trip for now: " + bestindiv.toLabelString() );
			ivjStatisticCanvas.setValues(ivjAlgorithm.getPop(1).getBestFit(), 
										ivjAlgorithm.getPop(1).getMeanFit() );
			helpindiv.setFitness(ivjAlgorithm.getPop(1).getMeanFit() );
			ivjStatisticMeanfitLabel.setText("Mean Fitness: "+helpindiv.trimFit()); 
		}
		step=0;
		generation++;
		ivjStatisticGenLabel.setText("Gen.: "+generation);
	}
	ivjRecombCanvas.setSelection(null);
	ivjRecombCanvas.setPop(null);
	ivjRecombCanvas.setMut(null);
		
	int i=0;
	Individual bestindiv = null;
	while(i<ivjDialogData.genstep && (!stop)) {
		ivjAlgorithm.selection();
		ivjAlgorithm.crossover();
        ivjAlgorithm.mutation();
		ivjAlgorithm.evaluate();
        ivjAlgorithm.populate();
	    if(ivjDialogData.update) {
			ivjPopCanvas.setPop(ivjAlgorithm.getPop(1) );
		}
       	bestindiv=ivjAlgorithm.getPop(1).getBest();
       	ivjStatisticTravelCanvas.setTravelIndiv(bestindiv);
       	ivjStatisticBestfitLabel.setText("Best Fitness: " + bestindiv.trimFit() );
       	ivjStatisticBestindivLabel.setText("The best trip for now: " + bestindiv.toLabelString() );	
		ivjStatisticCanvas.setValues(ivjAlgorithm.getPop(1).getBestFit(), 
									ivjAlgorithm.getPop(1).getMeanFit() );
		helpindiv.setFitness(ivjAlgorithm.getPop(1).getMeanFit() );
		ivjStatisticMeanfitLabel.setText("Mean Fitness: "+helpindiv.trimFit()); 
		i++;
		generation++;
		ivjStatisticGenLabel.setText("Gen.: "+generation);

		if(ivjDialogData.update) {
			try
			{	TspThread.sleep (750); }
			catch (InterruptedException e)
			{	System.out.println("InterruptedException"); }
		}

	}
    getStatisticStopStop().setEnabled(false);
	getStatisticGoGen().setEnabled(true);
	getStatisticGoRun().setEnabled(true);
	getStartCalculateTSP().setEnabled(true);
    getStartOptionsMenu().setEnabled(true);
	if(!ivjDialogData.update) {
		ivjPopCanvas.setPop(ivjAlgorithm.getPop(1) );
	}
	stop=false;

	if(ivjDialogData.towns<27) {
		getStatisticWindowMenu().setEnabled(true);
		getStatisticGoStep().setEnabled(true);
	}

}

/**
 * Method to handle events for the AdjustmentListener interface.
 * @param e java.awt.event.AdjustmentEvent
 */
public void adjustmentValueChanged(java.awt.event.AdjustmentEvent e) {
	Object source = e.getSource();
	if ((source == getOptionsMutScroll()) ) {
		getOptionsMutnumLabel().setText(ivjOptionsMutScroll.getValue() + "%");
	}
	if ((source == getOptionsRecombScroll()) ) {
		getOptionsRecnumLabel().setText(ivjOptionsRecombScroll.getValue() + "%");
	}
}

/**
 * Return the Panel1 property value.
 * @return java.awt.Panel
 */
private java.awt.Panel getTownPanel2() {
    if (ivjTownPanel2 == null) {
        try {
            ivjTownPanel2 = new java.awt.Panel();
            ivjTownPanel2.setName("Panel1");
            ivjTownPanel2.setLayout(new java.awt.FlowLayout());
            ivjTownPanel2.add(getTownGenLabel(), getTownGenLabel().getName());
            ivjTownPanel2.add(getTownText(), getTownText().getName());
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjTownPanel2;
}

/**
 * Return the TownClose property value.
 * @return java.awt.Button
 */
private java.awt.Button getTownClose() {
    if (ivjTownClose == null) {
        try {
            ivjTownClose = new java.awt.Button();
            ivjTownClose.setName("TownClose");
            ivjTownClose.setLabel("Cancel");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjTownClose;
}

/**
 * Return the TownGenLabel property value.
 * @return java.awt.Label
 */
private java.awt.Label getTownGenLabel() {
    if (ivjTownGenLabel == null) {
        try {
            ivjTownGenLabel = new java.awt.Label();
            ivjTownGenLabel.setName("TownGenLabel");
            ivjTownGenLabel.setText("Towns to add:");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjTownGenLabel;
}

/**
 * Return the TownTextLabel property value.
 * @return java.awt.Label
 */
private java.awt.Label getTownTextLabel() {
    if (ivjTownTextLabel == null) {
        try {
            ivjTownTextLabel = new java.awt.Label();
            ivjTownTextLabel.setName("TownGenLabel");
			ivjTownTextLabel.setAlignment(java.awt.Label.CENTER);
            ivjTownTextLabel.setText("Add Random Towns to the problem");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjTownTextLabel;
}

/**
 * Return the TownOk property value.
 * @return java.awt.Button
 */
private java.awt.Button getTownOk() {
    if (ivjTownOk == null) {
        try {
            ivjTownOk = new java.awt.Button();
            ivjTownOk.setName("TownOk");
            ivjTownOk.setLabel("OK");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjTownOk;
}

/**
 * Return the TownPanel1 property value.
 * @return java.awt.Panel
 */
private java.awt.Panel getTownPanel1() {
    if (ivjTownPanel1 == null) {
        try {
            ivjTownPanel1 = new java.awt.Panel();
            ivjTownPanel1.setName("TownPanel1");
            ivjTownPanel1.setLayout(new java.awt.FlowLayout());
            ivjTownPanel1.add(getTownOk(), getTownOk().getName());
            ivjTownPanel1.add(getTownClose(), getTownClose().getName());
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjTownPanel1;
}

/**
 * Return the TownText property value.
 * @return java.awt.TextField
 */
private java.awt.TextField getTownText() {
    if (ivjTownText == null) {
        try {
            ivjTownText = new IntTextField(10,1,100,4);
            ivjTownText.setName("TownText");
            //ivjTownText.setText("2000");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjTownText;
}

/**
 * Return the TownDialog property value.
 * @return java.awt.Dialog
 */
private Dialog getTownDialog() { 
    if (ivjTownDialog == null) {
        try {
            ivjTownDialog = new java.awt.Dialog(new java.awt.Frame());
            ivjTownDialog.setName("TownDialog");
            ivjTownDialog.setLayout(new java.awt.BorderLayout());
            ivjTownDialog.setSize(450,150);
            ivjTownDialog.add("North", getTownTextLabel());
            ivjTownDialog.add("South", getTownPanel1());
            ivjTownDialog.add("Center", getTownPanel2());
            ivjTownDialog.setTitle("Add Randomly");
            ivjTownDialog.setModal(true);
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjTownDialog;
}


/**
 * Return the GridPanel2 property value.
 * @return java.awt.Panel
 */
private java.awt.Panel getGridPanel2() {
    if (ivjGridPanel2 == null) {
        try {
            ivjGridPanel2 = new java.awt.Panel();
            ivjGridPanel2.setName("Panel1");
            ivjGridPanel2.setLayout(new java.awt.FlowLayout());
            ivjGridPanel2.add(getGridGenLabel(), getGridGenLabel().getName());
            ivjGridPanel2.add(getGridText(), getGridText().getName());
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjGridPanel2;
}

/**
 * Return the GridClose property value.
 * @return java.awt.Button
 */
private java.awt.Button getGridClose() {
    if (ivjGridClose == null) {
        try {
            ivjGridClose = new java.awt.Button();
            ivjGridClose.setName("GridClose");
            ivjGridClose.setLabel("Cancel");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjGridClose;
}

/**
 * Return the GridGenLabel property value.
 * @return java.awt.Label
 */
private java.awt.Label getGridGenLabel() {
    if (ivjGridGenLabel == null) {
        try {
            ivjGridGenLabel = new java.awt.Label();
            ivjGridGenLabel.setName("GridGenLabel");
            ivjGridGenLabel.setText("Grid size:");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjGridGenLabel;
}

/**
 * Return the GridOk property value.
 * @return java.awt.Button
 */
private java.awt.Button getGridOk() {
    if (ivjGridOk == null) {
        try {
            ivjGridOk = new java.awt.Button();
            ivjGridOk.setName("GridOk");
            ivjGridOk.setLabel("OK");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjGridOk;
}

/**
 * Return the GridPanel1 property value.
 * @return java.awt.Panel
 */
private java.awt.Panel getGridPanel1() {
    if (ivjGridPanel1 == null) {
        try {
            ivjGridPanel1 = new java.awt.Panel();
            ivjGridPanel1.setName("GridPanel1");
            ivjGridPanel1.setLayout(new java.awt.FlowLayout());
            ivjGridPanel1.add(getGridOk(), getGridOk().getName());
            ivjGridPanel1.add(getGridClose(), getGridClose().getName());
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjGridPanel1;
}

/**
 * Return the GridText property value.
 * @return java.awt.TextField
 */
private java.awt.TextField getGridText() {
    if (ivjGridText == null) {
        try {
            ivjGridText = new IntTextField(15,10,100,5);
            ivjGridText.setName("GridText");
            //ivjGridText.setText("2000");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjGridText;
}

/**
 * Return the GridTextLabel property value.
 * @return java.awt.Label
 */
private java.awt.Label getGridTextLabel() {
    if (ivjGridGenLabel == null) {
        try {
            ivjGridTextLabel = new java.awt.Label();
            ivjGridTextLabel.setName("GridTextLabel");
			ivjGridTextLabel.setAlignment(java.awt.Label.CENTER);
            ivjGridTextLabel.setText("Warning: Pressing OK deletes all your town entries!");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjGridTextLabel;
}

/**
 * Return the GridDialog property value.
 * @return java.awt.Dialog
 */
private Dialog getGridDialog() {
    if (ivjGridDialog == null) {
        try {
            ivjGridDialog = new java.awt.Dialog(new java.awt.Frame());
            ivjGridDialog.setName("GridDialog");
            ivjGridDialog.setLayout(new java.awt.BorderLayout());
            ivjGridDialog.setSize(450,150);
            ivjGridDialog.add("North", getGridTextLabel());
            ivjGridDialog.add("South", getGridPanel1());
            ivjGridDialog.add("Center", getGridPanel2());
            ivjGridDialog.setTitle("Set Grid");
            ivjGridDialog.setModal(true);
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjGridDialog;
}


/**
 * Return the AboutDialog property value.
 * @return AboutDialog
 */
private AboutDialog getAboutDialog() {
	if (ivjAboutDialog == null) {
		try {
			ivjAboutDialog = new AboutDialog(new java.awt.Frame());
			ivjAboutDialog.setName("AboutDialog");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjAboutDialog;
}

/**
 * Return the RunDialog property value.
 * @return RunDialog
 */
private RunDialog getRunDialog(Applet obj,ConnectInfo in) {
    if (ivjRunDialog == null) {
        try {
            ivjRunDialog = new RunDialog(obj, in);
            ivjRunDialog.setName("RunDialog");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjRunDialog;
}


/**
 * Return the ErxCanvas property value.
 * @return ErxCanvas
 */
private ErxCanvas getErxCanvas() {
	if (ivjErxCanvas == null) {
		try {
			ivjErxCanvas = new ErxCanvas();
			ivjErxCanvas.setName("ErxCanvas");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjErxCanvas;
}

/**
 * Return the ErxClose property value.
 * @return java.awt.Button
 */
private Button getErxClose() {
	if (ivjErxClose == null) {
		try {
			ivjErxClose = new java.awt.Button();
			ivjErxClose.setName("ErxClose");
			ivjErxClose.setLabel("Close");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjErxClose;
}

/**
 * Return the ErxFrame property value.
 * @return java.awt.Frame
 */
private Frame getErxFrame() {
	if (ivjErxFrame == null) {
		try {
			ivjErxFrame = new java.awt.Frame();
			ivjErxFrame.setName("ErxFrame");
			ivjErxFrame.setLayout(new java.awt.BorderLayout());
			ivjErxFrame.setBounds(0, 0, 600, 400);
			ivjErxFrame.setTitle("Recombination ERX");
			getErxFrame().add("South", getErxPanel());
			getErxFrame().add("Center", getErxCanvas());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjErxFrame;
}

/**
 * Return the ErxPanel property value.
 * @return java.awt.Panel
 */
private Panel getErxPanel() {
	if (ivjErxPanel == null) {
		try {
			ivjErxPanel = new java.awt.Panel();
			ivjErxPanel.setName("ErxPanel");
			ivjErxPanel.setLayout(new java.awt.FlowLayout());
			ivjErxPanel.add(getErxStep(), getErxStep().getName());
			ivjErxPanel.add(getErxTrace(), getErxTrace().getName());
			ivjErxPanel.add(getErxResult(), getErxResult().getName());
			ivjErxPanel.add(getErxClose(), getErxClose().getName());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjErxPanel;
}

/**
 * Return the ErxStep property value.
 * @return java.awt.Button
 */
private Button getErxStep() {
	if (ivjErxStep == null) {
		try {
			ivjErxStep = new java.awt.Button();
			ivjErxStep.setName("ErxStep");
			ivjErxStep.setLabel("Step");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjErxStep;
}

/**
 * Return the ErxResult property value.
 * @return java.awt.Button
 */
private Button getErxResult() {
    if (ivjErxResult == null) {
        try {
            ivjErxResult = new java.awt.Button();
            ivjErxResult.setName("ErxResult");
            ivjErxResult.setLabel("Result");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjErxResult;
}

/**
 * Return the ErxTrace property value.
 * @return java.awt.Button
 */
private Button getErxTrace() {
    if (ivjErxTrace == null) {
        try {
            ivjErxTrace = new java.awt.Button();
            ivjErxTrace.setName("ErxTrace");
            ivjErxTrace.setLabel("Final");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjErxTrace;
}


/**
 * Return the GaStart property value.
 * @return java.awt.Button
 */
private Button getGaStart() {
	if (ivjGaStart == null) {
		try {
			ivjGaStart = new java.awt.Button();
			ivjGaStart.setName("GaStart");
			//ivjGaStart.setBounds(7, 10, 125, 30);
			ivjGaStart.setLabel("Start");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjGaStart;
}


/**
 * Return the CheckboxMenuItem1 property value.
 * @return java.awt.CheckboxMenuItem
 */
 private CheckboxMenuItem getStartOptionsFonts1() {
   if (ivjStartOptionsFonts1 == null) {
       try {
           ivjStartOptionsFonts1 = new java.awt.CheckboxMenuItem();
           ivjStartOptionsFonts1.setLabel("Small Fonts");
			if(ivjDialogData.largefonts==0) {
           		ivjStartOptionsFonts1.setState(true);
			}
       } catch (java.lang.Throwable ivjExc) {
           handleException(ivjExc);
       }
   };
   return ivjStartOptionsFonts1;
 }

/**
 * Return the CheckboxMenuItem2 property value.
 * @return java.awt.CheckboxMenuItem
 */
private CheckboxMenuItem getStartOptionsFonts3() {
   if (ivjStartOptionsFonts3 == null) {
       try {
           ivjStartOptionsFonts3 = new java.awt.CheckboxMenuItem();
           ivjStartOptionsFonts3.setLabel("Medium Fonts");
			if(ivjDialogData.largefonts==1) {
           		ivjStartOptionsFonts3.setState(true);
			}
       } catch (java.lang.Throwable ivjExc) {
           handleException(ivjExc);
       }
   };
   return ivjStartOptionsFonts3;
 }

/**
 * Return the MenuItem1 property value.
 * @return java.awt.MenuItem
 */
private MenuItem getMenuItem1() {
   if (ivjStartOptionsItem2 == null) {
       try {
           ivjStartOptionsItem2 = new java.awt.MenuItem();
           ivjStartOptionsItem2.setLabel("-");
       } catch (java.lang.Throwable ivjExc) {
           handleException(ivjExc);
       }
   };
   return ivjStartOptionsItem2;
}

/**
 * Return the MenuItem5 property value.
 * @return java.awt.MenuItem
 */
private MenuItem getMenuItem5() {
   if (ivjStartOptionsItem3 == null) {
       try {
           ivjStartOptionsItem3 = new java.awt.MenuItem();
           ivjStartOptionsItem3.setLabel("-");
       } catch (java.lang.Throwable ivjExc) {
           handleException(ivjExc);
       }
   };
   return ivjStartOptionsItem3;
}


/**
 * Return the MenuItem3 property value.
 * @return java.awt.MenuItem
 */
private MenuItem getMenuItem3() {
   if (ivjStatisticGoItem2 == null) {
       try {
           ivjStatisticGoItem2 = new java.awt.MenuItem();
           ivjStatisticGoItem2.setLabel("-");
       } catch (java.lang.Throwable ivjExc) {
           handleException(ivjExc);
       }
   };
   return ivjStatisticGoItem2;
}

/**
 * Return the MenuItem4 property value.
 * @return java.awt.MenuItem
 */
private MenuItem getMenuItem4() {
   if (ivjStatisticGoItem1 == null) {
       try {
           ivjStatisticGoItem1 = new java.awt.MenuItem();
           ivjStatisticGoItem1.setLabel("-");
       } catch (java.lang.Throwable ivjExc) {
           handleException(ivjExc);
       }
   };
   return ivjStatisticGoItem1;
}

/**
 * Return the MenuItem4 property value.
 * @return java.awt.MenuItem
 */
private MenuItem getMenuItem6() {
   if (ivjStatisticGoItem3 == null) {
       try {
           ivjStatisticGoItem3 = new java.awt.MenuItem();
           ivjStatisticGoItem3.setLabel("-");
       } catch (java.lang.Throwable ivjExc) {
           handleException(ivjExc);
       }
   };
   return ivjStatisticGoItem3;
}


/**
 * Return the OptionsCheckbox property value.
 * @return java.awt.Checkbox
 */
private Checkbox getOptionsCheckbox() {
	if (ivjOptionsCheckbox == null) {
		try {
			ivjOptionsCheckbox = new java.awt.Checkbox();
			ivjOptionsCheckbox.setName("OptionsCheckbox");
			ivjOptionsCheckbox.setLabel("Elitism");
			ivjOptionsCheckbox.setState(true);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjOptionsCheckbox;
}

/**
 * Return the OptionsClose property value.
 * @return java.awt.Button
 */
private Button getOptionsClose() {
	if (ivjOptionsClose == null) {
		try {
			ivjOptionsClose = new java.awt.Button();
			ivjOptionsClose.setName("OptionsClose");
			ivjOptionsClose.setLabel("Cancel");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjOptionsClose;
}

/**
 * Return the OptionsDefault property value.
 * @return java.awt.Button
 */
private Button getOptionsDefault() {
	if (ivjOptionsDefault == null) {
		try {
			ivjOptionsDefault = new java.awt.Button();
			ivjOptionsDefault.setName("OptionsDefault");
			ivjOptionsDefault.setLabel("Default");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjOptionsDefault;
}

/**
 * Return the OptionsDialog property value.
 * @return java.awt.Dialog
 */
private Dialog getOptionsDialog() {
	java.awt.GridBagConstraints constraintsOptionsRecombScroll = new java.awt.GridBagConstraints();
	java.awt.GridBagConstraints constraintsOptionsMutScroll = new java.awt.GridBagConstraints();
	java.awt.GridBagConstraints constraintsOptionsRecombChoice = new java.awt.GridBagConstraints();
	java.awt.GridBagConstraints constraintsOptionsPopLabel = new java.awt.GridBagConstraints();
	java.awt.GridBagConstraints constraintsOptionsPopText = new java.awt.GridBagConstraints();
	java.awt.GridBagConstraints constraintsOptionsMutmetLabel = new java.awt.GridBagConstraints();
	java.awt.GridBagConstraints constraintsOptionsMutChoice = new java.awt.GridBagConstraints();
	java.awt.GridBagConstraints constraintsOptionsMutprobLabel = new java.awt.GridBagConstraints();
	java.awt.GridBagConstraints constraintsOptionsClose = new java.awt.GridBagConstraints();
	java.awt.GridBagConstraints constraintsOptionsOk = new java.awt.GridBagConstraints();
	java.awt.GridBagConstraints constraintsOptionsDefault = new java.awt.GridBagConstraints();
	java.awt.GridBagConstraints constraintsOptionsMutnumLabel = new java.awt.GridBagConstraints();
	java.awt.GridBagConstraints constraintsOptionsRecnumLabel = new java.awt.GridBagConstraints();
	java.awt.GridBagConstraints constraintsOptionsRecprobLabel = new java.awt.GridBagConstraints();
	java.awt.GridBagConstraints constraintsOptionsRecmetLabel = new java.awt.GridBagConstraints();
	java.awt.GridBagConstraints constraintsOptionsGroupLabel = new java.awt.GridBagConstraints();
	java.awt.GridBagConstraints constraintsOptionsGroupText = new java.awt.GridBagConstraints();
	java.awt.GridBagConstraints constraintsOptionsCheckbox = new java.awt.GridBagConstraints();

	if (ivjOptionsDialog == null) {
		try {
			ivjOptionsDialog = new java.awt.Dialog(new java.awt.Frame());
			ivjOptionsDialog.setName("OptionsDialog");
			ivjOptionsDialog.setLayout(new java.awt.GridBagLayout());
            ivjOptionsDialog.setBounds(50, 0, 500, 400);
			ivjOptionsDialog.setTitle("GA Parameters");
			ivjOptionsDialog.setModal(true);

			constraintsOptionsRecombScroll.gridx = 0; constraintsOptionsRecombScroll.gridy = 3;
			constraintsOptionsRecombScroll.gridwidth = 2; constraintsOptionsRecombScroll.gridheight = 1;
			constraintsOptionsRecombScroll.anchor = java.awt.GridBagConstraints.CENTER;
			constraintsOptionsRecombScroll.weightx = 100.0;
			constraintsOptionsRecombScroll.weighty = 100.0;
			constraintsOptionsRecombScroll.ipadx = 50; 			constraintsOptionsRecombScroll.ipady = 8;
			((java.awt.GridBagLayout) getOptionsDialog().getLayout()).setConstraints(getOptionsRecombScroll(), constraintsOptionsRecombScroll);
			getOptionsDialog().add(getOptionsRecombScroll());

			constraintsOptionsMutScroll.gridx = 0; constraintsOptionsMutScroll.gridy = 5;
			constraintsOptionsMutScroll.gridwidth = 2; constraintsOptionsMutScroll.gridheight = 1;
			constraintsOptionsMutScroll.anchor = java.awt.GridBagConstraints.CENTER;
			constraintsOptionsMutScroll.weightx = 100.0;
			constraintsOptionsMutScroll.weighty = 100.0;
			constraintsOptionsMutScroll.ipadx = 50; 			constraintsOptionsMutScroll.ipady = 8;
			((java.awt.GridBagLayout) getOptionsDialog().getLayout()).setConstraints(getOptionsMutScroll(), constraintsOptionsMutScroll);
			getOptionsDialog().add(getOptionsMutScroll());

			constraintsOptionsRecombChoice.gridx = 4; constraintsOptionsRecombChoice.gridy = 3;
			constraintsOptionsRecombChoice.gridwidth = 2; constraintsOptionsRecombChoice.gridheight = 1;
			constraintsOptionsRecombChoice.anchor = java.awt.GridBagConstraints.CENTER;
			constraintsOptionsRecombChoice.weightx = 100.0;
			constraintsOptionsRecombChoice.weighty = 100.0;
			((java.awt.GridBagLayout) getOptionsDialog().getLayout()).setConstraints(getOptionsRecombChoice(), constraintsOptionsRecombChoice);
			getOptionsDialog().add(getOptionsRecombChoice());

			constraintsOptionsPopLabel.gridx = 4; constraintsOptionsPopLabel.gridy = 6;
			constraintsOptionsPopLabel.gridwidth = 1; constraintsOptionsPopLabel.gridheight = 1;
			constraintsOptionsPopLabel.anchor = java.awt.GridBagConstraints.WEST;
			constraintsOptionsPopLabel.weightx = 100.0;
			constraintsOptionsPopLabel.weighty = 100.0;
			((java.awt.GridBagLayout) getOptionsDialog().getLayout()).setConstraints(getOptionsPopLabel(), constraintsOptionsPopLabel);
			getOptionsDialog().add(getOptionsPopLabel());

			constraintsOptionsPopText.gridx = 5; constraintsOptionsPopText.gridy = 6;
			constraintsOptionsPopText.gridwidth = 1; constraintsOptionsPopText.gridheight = 1;
			constraintsOptionsPopText.anchor = java.awt.GridBagConstraints.CENTER;
			constraintsOptionsPopText.weightx = 0.0;
			constraintsOptionsPopText.weighty = 0.0;
			((java.awt.GridBagLayout) getOptionsDialog().getLayout()).setConstraints(getOptionsPopText(), constraintsOptionsPopText);
			getOptionsDialog().add(getOptionsPopText());

			constraintsOptionsMutmetLabel.gridx = 4; constraintsOptionsMutmetLabel.gridy = 4;
			constraintsOptionsMutmetLabel.gridwidth = 2; constraintsOptionsMutmetLabel.gridheight = 1;
			constraintsOptionsMutmetLabel.anchor = java.awt.GridBagConstraints.CENTER;
			constraintsOptionsMutmetLabel.weightx = 0.0;
			constraintsOptionsMutmetLabel.weighty = 0.0;
			((java.awt.GridBagLayout) getOptionsDialog().getLayout()).setConstraints(getOptionsMutmetLabel(), constraintsOptionsMutmetLabel);
			getOptionsDialog().add(getOptionsMutmetLabel());

			constraintsOptionsMutChoice.gridx = 4; constraintsOptionsMutChoice.gridy = 5;
			constraintsOptionsMutChoice.gridwidth = 2; constraintsOptionsMutChoice.gridheight = 1;
			constraintsOptionsMutChoice.anchor = java.awt.GridBagConstraints.CENTER;
			constraintsOptionsMutChoice.weightx = 0.0;
			constraintsOptionsMutChoice.weighty = 0.0;
			((java.awt.GridBagLayout) getOptionsDialog().getLayout()).setConstraints(getOptionsMutChoice(), constraintsOptionsMutChoice);
			getOptionsDialog().add(getOptionsMutChoice());

			constraintsOptionsMutprobLabel.gridx = 0; constraintsOptionsMutprobLabel.gridy = 4;
			constraintsOptionsMutprobLabel.gridwidth = 2; constraintsOptionsMutprobLabel.gridheight = 1;
			constraintsOptionsMutprobLabel.anchor = java.awt.GridBagConstraints.WEST;
			constraintsOptionsMutprobLabel.weightx = 0.0;
			constraintsOptionsMutprobLabel.weighty = 0.0;
			((java.awt.GridBagLayout) getOptionsDialog().getLayout()).setConstraints(getOptionsMutprobLabel(), constraintsOptionsMutprobLabel);
			getOptionsDialog().add(getOptionsMutprobLabel());

			constraintsOptionsClose.gridx = 5; constraintsOptionsClose.gridy = 12;
			constraintsOptionsClose.gridwidth = 1; constraintsOptionsClose.gridheight = 1;
			constraintsOptionsClose.anchor = java.awt.GridBagConstraints.CENTER;
			constraintsOptionsClose.weightx = 100.0;
			constraintsOptionsClose.weighty = 100.0;
			((java.awt.GridBagLayout) getOptionsDialog().getLayout()).setConstraints(getOptionsClose(), constraintsOptionsClose);
			getOptionsDialog().add(getOptionsClose());

			constraintsOptionsOk.gridx = 4; constraintsOptionsOk.gridy = 12;
			constraintsOptionsOk.gridwidth = 1; constraintsOptionsOk.gridheight = 1;
			constraintsOptionsOk.anchor = java.awt.GridBagConstraints.CENTER;
			constraintsOptionsOk.weightx = 100.0;
			constraintsOptionsOk.weighty = 100.0;
			constraintsOptionsOk.ipadx = 20;
 			((java.awt.GridBagLayout) getOptionsDialog().getLayout()).setConstraints(getOptionsOk(), constraintsOptionsOk);
			getOptionsDialog().add(getOptionsOk());

			constraintsOptionsDefault.gridx = 3; constraintsOptionsDefault.gridy = 12;
			constraintsOptionsDefault.gridwidth = 1; constraintsOptionsDefault.gridheight = 1;
			constraintsOptionsDefault.anchor = java.awt.GridBagConstraints.CENTER;
			constraintsOptionsDefault.weightx = 100.0;
			constraintsOptionsDefault.weighty = 100.0;
			((java.awt.GridBagLayout) getOptionsDialog().getLayout()).setConstraints(getOptionsDefault(), constraintsOptionsDefault);
			getOptionsDialog().add(getOptionsDefault());

			constraintsOptionsMutnumLabel.gridx = 2; constraintsOptionsMutnumLabel.gridy = 4;
			constraintsOptionsMutnumLabel.gridwidth = 1; constraintsOptionsMutnumLabel.gridheight = 1;
			constraintsOptionsMutnumLabel.anchor = java.awt.GridBagConstraints.CENTER;
			constraintsOptionsMutnumLabel.weightx = 100.0;
			constraintsOptionsMutnumLabel.weighty = 100.0;
			constraintsOptionsMutnumLabel.ipadx = 4; 			((java.awt.GridBagLayout) getOptionsDialog().getLayout()).setConstraints(getOptionsMutnumLabel(), constraintsOptionsMutnumLabel);
			getOptionsDialog().add(getOptionsMutnumLabel());

			constraintsOptionsRecnumLabel.gridx = 2; constraintsOptionsRecnumLabel.gridy = 1;
			constraintsOptionsRecnumLabel.gridwidth = 1; constraintsOptionsRecnumLabel.gridheight = 1;
			constraintsOptionsRecnumLabel.anchor = java.awt.GridBagConstraints.CENTER;
			constraintsOptionsRecnumLabel.weightx = 100.0;
			constraintsOptionsRecnumLabel.weighty = 100.0;
			constraintsOptionsRecnumLabel.ipadx = 4; 			((java.awt.GridBagLayout) getOptionsDialog().getLayout()).setConstraints(getOptionsRecnumLabel(), constraintsOptionsRecnumLabel);
			getOptionsDialog().add(getOptionsRecnumLabel());

			constraintsOptionsRecprobLabel.gridx = 0; constraintsOptionsRecprobLabel.gridy = 1;
			constraintsOptionsRecprobLabel.gridwidth = 2; constraintsOptionsRecprobLabel.gridheight = 1;
			constraintsOptionsRecprobLabel.anchor = java.awt.GridBagConstraints.WEST;
			constraintsOptionsRecprobLabel.weightx = 0.0;
			constraintsOptionsRecprobLabel.weighty = 0.0;
			((java.awt.GridBagLayout) getOptionsDialog().getLayout()).setConstraints(getOptionsRecprobLabel(), constraintsOptionsRecprobLabel);
			getOptionsDialog().add(getOptionsRecprobLabel());

			constraintsOptionsRecmetLabel.gridx = 4; constraintsOptionsRecmetLabel.gridy = 1;
			constraintsOptionsRecmetLabel.gridwidth = 2; constraintsOptionsRecmetLabel.gridheight = 1;
			constraintsOptionsRecmetLabel.anchor = java.awt.GridBagConstraints.CENTER;
			constraintsOptionsRecmetLabel.weightx = 0.0;
			constraintsOptionsRecmetLabel.weighty = 0.0;
			((java.awt.GridBagLayout) getOptionsDialog().getLayout()).setConstraints(getOptionsRecmetLabel(), constraintsOptionsRecmetLabel);
			getOptionsDialog().add(getOptionsRecmetLabel());

			constraintsOptionsGroupLabel.gridx = 4; constraintsOptionsGroupLabel.gridy = 7;
			constraintsOptionsGroupLabel.gridwidth = 2; constraintsOptionsGroupLabel.gridheight = 1;
			constraintsOptionsGroupLabel.anchor = java.awt.GridBagConstraints.WEST;
			constraintsOptionsGroupLabel.weightx = 100.0;
			constraintsOptionsGroupLabel.weighty = 100.0;
			((java.awt.GridBagLayout) getOptionsDialog().getLayout()).setConstraints(getOptionsGroupLabel(), constraintsOptionsGroupLabel);
			getOptionsDialog().add(getOptionsGroupLabel());

			constraintsOptionsGroupText.gridx = 5; constraintsOptionsGroupText.gridy = 7;
			constraintsOptionsGroupText.gridwidth = 1; constraintsOptionsGroupText.gridheight = 1;
			constraintsOptionsGroupText.anchor = java.awt.GridBagConstraints.CENTER;
			constraintsOptionsGroupText.weightx = 0.0;
			constraintsOptionsGroupText.weighty = 0.0;
			((java.awt.GridBagLayout) getOptionsDialog().getLayout()).setConstraints(getOptionsGroupText(), constraintsOptionsGroupText);
			getOptionsDialog().add(getOptionsGroupText());

			constraintsOptionsCheckbox.gridx = 4; constraintsOptionsCheckbox.gridy = 9;
			constraintsOptionsCheckbox.gridwidth = 1; constraintsOptionsCheckbox.gridheight = 1;
			constraintsOptionsCheckbox.anchor = java.awt.GridBagConstraints.WEST;
			constraintsOptionsCheckbox.weightx = 100.0;
			constraintsOptionsCheckbox.weighty = 100.0;
			constraintsOptionsCheckbox.ipadx = 4; 			((java.awt.GridBagLayout) getOptionsDialog().getLayout()).setConstraints(getOptionsCheckbox(), constraintsOptionsCheckbox);
			getOptionsDialog().add(getOptionsCheckbox());

		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjOptionsDialog;
}

/**
 * Return the OptionsGroupLabel property value.
 * @return java.awt.Label
 */
private Label getOptionsGroupLabel() {
	if (ivjOptionsGroupLabel == null) {
		try {
			ivjOptionsGroupLabel = new java.awt.Label();
			ivjOptionsGroupLabel.setName("OptionsGroupLabel");
			ivjOptionsGroupLabel.setText("Group size");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjOptionsGroupLabel;
}


/**
 * Return the OptionsGroupText property value.
 * @return java.awt.TextField
 */
private TextField getOptionsGroupText() {
	if (ivjOptionsGroupText == null) {
		try {
			ivjOptionsGroupText = new IntTextField(3,1,10,3);
			ivjOptionsGroupText.setName("OptionsGroupText");
            ivjOptionsGroupText.setColumns(2);
			ivjOptionsGroupText.setText("2");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjOptionsGroupText;
}

/**
 * Return the OptionsMutChoice property value.
 * @return java.awt.Choice
 */
private Choice getOptionsMutChoice() {
	if (ivjOptionsMutChoice == null) {
		try {
			ivjOptionsMutChoice = new java.awt.Choice();
			ivjOptionsMutChoice.setName("OptionsMutChoice");
			ivjOptionsMutChoice.addItem("Inversion");
			ivjOptionsMutChoice.addItem("Insertion");
			ivjOptionsMutChoice.addItem("Displacement");
			ivjOptionsMutChoice.addItem("Reciprocal Exchange");
			ivjOptionsMutChoice.addItem("None");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjOptionsMutChoice;
}

/**
 * Return the OptionsMutmetLabel property value.
 * @return java.awt.Label
 */
private Label getOptionsMutmetLabel() {
	if (ivjOptionsMutmetLabel == null) {
		try {
			ivjOptionsMutmetLabel = new java.awt.Label();
			ivjOptionsMutmetLabel.setName("OptionsMutmetLabel");
			ivjOptionsMutmetLabel.setText("Mutation method");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjOptionsMutmetLabel;
}

/**
 * Return the OptionsMutnumLabel property value.
 * @return java.awt.Label
 */
private Label getOptionsMutnumLabel() {
	if (ivjOptionsMutnumLabel == null) {
		try {
			ivjOptionsMutnumLabel = new java.awt.Label();
			ivjOptionsMutnumLabel.setName("OptionsMutnumLabel");
			ivjOptionsMutnumLabel.setText("50%");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjOptionsMutnumLabel;
}

/**
 * Return the OptionsMutprobLabel property value.
 * @return java.awt.Label
 */
private Label getOptionsMutprobLabel() {
	if (ivjOptionsMutprobLabel == null) {
		try {
			ivjOptionsMutprobLabel = new java.awt.Label();
			ivjOptionsMutprobLabel.setName("OptionsMutprobLabel");
			ivjOptionsMutprobLabel.setText("Mutation probability");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjOptionsMutprobLabel;
}

/**
 * Return the OptionsMutScroll property value.
 * @return java.awt.Scrollbar
 */
private Scrollbar getOptionsMutScroll() {
	if (ivjOptionsMutScroll == null) {
		try {
			ivjOptionsMutScroll = new java.awt.Scrollbar();
			ivjOptionsMutScroll.setName("OptionsMutScroll");
			ivjOptionsMutScroll.setMaximum(110);
			ivjOptionsMutScroll.setValue(50);
			ivjOptionsMutScroll.setOrientation(0);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjOptionsMutScroll;
}

/**
 * Return the OptionsOk property value.
 * @return java.awt.Button
 */
private Button getOptionsOk() {
	if (ivjOptionsOk == null) {
		try {
			ivjOptionsOk = new java.awt.Button();
			ivjOptionsOk.setName("OptionsOk");
			ivjOptionsOk.setLabel("OK");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjOptionsOk;
}

/**
 * Return the OptionsPopLabel property value.
 * @return java.awt.Label
 */
private Label getOptionsPopLabel() {
	if (ivjOptionsPopLabel == null) {
		try {
			ivjOptionsPopLabel = new java.awt.Label();
			ivjOptionsPopLabel.setName("OptionsPopLabel");
			ivjOptionsPopLabel.setText("Population size");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjOptionsPopLabel;
}

/**
 * Return the OptionsPopText property value.
 * @return java.awt.TextField
 */
private TextField getOptionsPopText() {
	if (ivjOptionsPopText == null) {
		try {
			ivjOptionsPopText = new IntTextField(50,5,100,5);
			ivjOptionsPopText.setName("OptionsPopText");
            ivjOptionsPopText.setColumns(4);
			ivjOptionsPopText.setText("100");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjOptionsPopText;
}

/**
 * Return the OptionsRecmetLabel property value.
 * @return java.awt.Label
 */
private Label getOptionsRecmetLabel() {
	if (ivjOptionsRecmetLabel == null) {
		try {
			ivjOptionsRecmetLabel = new java.awt.Label();
			ivjOptionsRecmetLabel.setName("OptionsRecmetLabel");
			ivjOptionsRecmetLabel.setText("Recombination method");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjOptionsRecmetLabel;
}

/**
 * Return the OptionsRecnumLabel property value.
 * @return java.awt.Label
 */
private Label getOptionsRecnumLabel() {
	if (ivjOptionsRecnumLabel == null) {
		try {
			ivjOptionsRecnumLabel = new java.awt.Label();
			ivjOptionsRecnumLabel.setName("OptionsRecnumLabel");
			ivjOptionsRecnumLabel.setText("50%");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjOptionsRecnumLabel;
}

/**
 * Return the OptionsRecombChoice property value.
 * @return java.awt.Choice
 */
private Choice getOptionsRecombChoice() {
	if (ivjOptionsRecombChoice == null) {
		try {
			ivjOptionsRecombChoice = new java.awt.Choice();
			ivjOptionsRecombChoice.setName("OptionsRecombChoice");
			ivjOptionsRecombChoice.addItem("Partially Matched");
			ivjOptionsRecombChoice.addItem("Order");
			ivjOptionsRecombChoice.addItem("Cycle");
			ivjOptionsRecombChoice.addItem("Uniform Order Based");
			ivjOptionsRecombChoice.addItem("Edge Recombination");
			ivjOptionsRecombChoice.addItem("ERX with Heuristic");
			ivjOptionsRecombChoice.addItem("None");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjOptionsRecombChoice;
}

/**
 * Return the OptionsRecombScroll property value.
 * @return java.awt.Scrollbar
 */
private Scrollbar getOptionsRecombScroll() {
	if (ivjOptionsRecombScroll == null) {
		try {
			ivjOptionsRecombScroll = new java.awt.Scrollbar();
			ivjOptionsRecombScroll.setName("OptionsRecombScroll");
			ivjOptionsRecombScroll.setBlockIncrement(10);
			ivjOptionsRecombScroll.setValue(50);
			ivjOptionsRecombScroll.setMaximum(110);
			ivjOptionsRecombScroll.setOrientation(0);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjOptionsRecombScroll;
}

/**
 * Return the OptionsRecprobLabel property value.
 * @return java.awt.Label
 */
private Label getOptionsRecprobLabel() {
	if (ivjOptionsRecprobLabel == null) {
		try {
			ivjOptionsRecprobLabel = new java.awt.Label();
			ivjOptionsRecprobLabel.setName("OptionsRecprobLabel");
			ivjOptionsRecprobLabel.setText("Recombination probability");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjOptionsRecprobLabel;
}


/**
 * Return the ParentFitLabel1 property value.
 * @return java.awt.Label
 */
private Label getParentFitLabel1() {
	if (ivjParentFitLabel1 == null) {
		try {
			ivjParentFitLabel1 = new java.awt.Label();
			ivjParentFitLabel1.setName("ParentFitLabel1");
			ivjParentFitLabel1.setAlignment(java.awt.Label.CENTER);
			ivjParentFitLabel1.setText("Fitness=10099");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjParentFitLabel1;
}

/**
 * Return the ParentFitLabel2 property value.
 * @return java.awt.Label
 */
private Label getParentFitLabel2() {
	if (ivjParentFitLabel2 == null) {
		try {
			ivjParentFitLabel2 = new java.awt.Label();
			ivjParentFitLabel2.setName("ParentFitLabel2");
			ivjParentFitLabel2.setAlignment(java.awt.Label.CENTER);
			ivjParentFitLabel2.setText("Fitness=10099");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjParentFitLabel2;
}

/**
 * Return the ParentFitLabel3 property value.
 * @return java.awt.Label
 */
private Label getParentFitLabel3() {
	if (ivjParentFitLabel3 == null) {
		try {
			ivjParentFitLabel3 = new java.awt.Label();
			ivjParentFitLabel3.setName("ParentFitLabel3");
			ivjParentFitLabel3.setAlignment(java.awt.Label.CENTER);
			ivjParentFitLabel3.setText("Fitness=10099");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjParentFitLabel3;
}

/**
 * Return the ParentFrame property value.
 * @return java.awt.Frame
 */
private Frame getParentFrame() {
	if (ivjParentFrame == null) {
		try {
			ivjParentFrame = new java.awt.Frame();
			ivjParentFrame.setName("ParentFrame");
			ivjParentFrame.setLayout(getParentFrameGridLayout());
			ivjParentFrame.setBounds(0, 0, 570, 250);
			ivjParentFrame.setTitle("Result: Parent1 - Parent2 - Child");
			ivjParentFrame.add(getParentPanel1(), getParentPanel1().getName());
			ivjParentFrame.add(getParentPanel2(), getParentPanel2().getName());
			ivjParentFrame.add(getParentPanel3(), getParentPanel3().getName());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjParentFrame;
}

/**
 * Return the ParentFrameGridLayout property value.
 * @return java.awt.GridLayout
 */
private GridLayout getParentFrameGridLayout() {
	java.awt.GridLayout ivjParentFrameGridLayout = null;
	try {
		/* Create part */
		ivjParentFrameGridLayout = new java.awt.GridLayout(1, 3);
		ivjParentFrameGridLayout.setVgap(5);
		ivjParentFrameGridLayout.setHgap(5);
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	};
	return ivjParentFrameGridLayout;
}

/**
 * Return the ParentIndivLabel1 property value.
 * @return java.awt.Label
 */
private Label getParentIndivLabel1() {
	if (ivjParentIndivLabel1 == null) {
		try {
			ivjParentIndivLabel1 = new java.awt.Label();
			ivjParentIndivLabel1.setName("ParentIndivLabel1");
			ivjParentIndivLabel1.setAlignment(java.awt.Label.CENTER);
			ivjParentIndivLabel1.setText("ADFHJKIUFGH");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjParentIndivLabel1;
}

/**
 * Return the ParentIndivLabel2 property value.
 * @return java.awt.Label
 */
private Label getParentIndivLabel2() {
	if (ivjParentIndivLabel2 == null) {
		try {
			ivjParentIndivLabel2 = new java.awt.Label();
			ivjParentIndivLabel2.setName("ParentIndivLabel2");
			ivjParentIndivLabel2.setAlignment(java.awt.Label.CENTER);
			ivjParentIndivLabel2.setText("ADFHJKIUFGH");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjParentIndivLabel2;
}

/**
 * Return the ParentIndivLabel3 property value.
 * @return java.awt.Label
 */
private Label getParentIndivLabel3() {
	if (ivjParentIndivLabel3 == null) {
		try {
			ivjParentIndivLabel3 = new java.awt.Label();
			ivjParentIndivLabel3.setName("ParentIndivLabel3");
			ivjParentIndivLabel3.setAlignment(java.awt.Label.CENTER);
			ivjParentIndivLabel3.setText("ADFHJKIUFGH");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjParentIndivLabel3;
}

/**
 * Return the ParentPanel1 property value.
 * @return java.awt.Panel
 */
private Panel getParentPanel1() {
	if (ivjParentPanel1 == null) {
		try {
			ivjParentPanel1 = new java.awt.Panel();
			ivjParentPanel1.setName("ParentPanel1");
			ivjParentPanel1.setLayout(new java.awt.BorderLayout());
			getParentPanel1().add("North", getParentIndivLabel1());
			getParentPanel1().add("South", getParentFitLabel1());
			getParentPanel1().add("Center", getParentTravelCanvas1());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjParentPanel1;
}

/**
 * Return the ParentPanel2 property value.
 * @return java.awt.Panel
 */
private Panel getParentPanel2() {
	if (ivjParentPanel2 == null) {
		try {
			ivjParentPanel2 = new java.awt.Panel();
			ivjParentPanel2.setName("ParentPanel2");
			ivjParentPanel2.setLayout(new java.awt.BorderLayout());
			getParentPanel2().add("North", getParentIndivLabel2());
			getParentPanel2().add("South", getParentFitLabel2());
			getParentPanel2().add("Center", getParentTravelCanvas2());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjParentPanel2;
}

/**
 * Return the ParentPanel3 property value.
 * @return java.awt.Panel
 */
private Panel getParentPanel3() {
	if (ivjParentPanel3 == null) {
		try {
			ivjParentPanel3 = new java.awt.Panel();
			ivjParentPanel3.setName("ParentPanel3");
			ivjParentPanel3.setLayout(new java.awt.BorderLayout());
			//ivjParentPanel3.setBounds(461, 1046, 212, 239);
			getParentPanel3().add("North", getParentIndivLabel3());
			getParentPanel3().add("South", getParentFitLabel3());
			getParentPanel3().add("Center", getParentTravelCanvas3());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjParentPanel3;
}

/**
 * Return the ParentTravelCanvas1 property value.
 * @return TravelCanvas
 */
private TravelCanvas getParentTravelCanvas1() {
	if (ivjParentTravelCanvas1 == null) {
		try {
			ivjParentTravelCanvas1 = new TravelCanvas();
			ivjParentTravelCanvas1.setName("ParentTravelCanvas1");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjParentTravelCanvas1;
}

/**
 * Return the ParentTravelCanvas2 property value.
 * @return TravelCanvas
 */
private TravelCanvas getParentTravelCanvas2() {
	if (ivjParentTravelCanvas2 == null) {
		try {
			ivjParentTravelCanvas2 = new TravelCanvas();
			ivjParentTravelCanvas2.setName("ParentTravelCanvas2");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjParentTravelCanvas2;
}

/**
 * Return the ParentTravelCanvas3 property value.
 * @return TravelCanvas
 */
private TravelCanvas getParentTravelCanvas3() {
	if (ivjParentTravelCanvas3 == null) {
		try {
			ivjParentTravelCanvas3 = new TravelCanvas();
			ivjParentTravelCanvas3.setName("ParentTravelCanvas3");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjParentTravelCanvas3;
}

/**
 * Return the PopCanvas property value.
 * @return PopCanvas
 */
private PopCanvas getPopCanvas() {
	if (ivjPopCanvas == null) {
		try {
			ivjPopCanvas = new PopCanvas();
			ivjPopCanvas.setName("PopCanvas");
			ivjPopCanvas.setBounds(0, 0, 100, 500);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjPopCanvas;
}

/**
 * Return the PopFrame property value.
 * @return java.awt.Frame
 */
private Frame getPopFrame() {
	if (ivjPopFrame == null) {
		try {
			ivjPopFrame = new java.awt.Frame();
			ivjPopFrame.setName("PopFrame");
			ivjPopFrame.setLayout(new java.awt.BorderLayout());
			ivjPopFrame.setBounds(0, 255, 320, 200);
			ivjPopFrame.setTitle("Population");
			getPopFrame().add("Center", getPopScrollPane());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjPopFrame;
}

/**
 * Return the PopScrollPane property value.
 * @return java.awt.ScrollPane
 */
private ScrollPane getPopScrollPane() {
	if (ivjPopScrollPane == null) {
		try {
			ivjPopScrollPane = new java.awt.ScrollPane();
			ivjPopScrollPane.setName("PopScrollPane");
			ivjPopScrollPane.add(getPopCanvas(), getPopCanvas().getName());

        	Adjustable vadjust = ivjPopScrollPane.getVAdjustable();
        	Adjustable hadjust = ivjPopScrollPane.getHAdjustable();
        	hadjust.setUnitIncrement(10);
        	vadjust.setUnitIncrement(10);

			ivjPopScrollPane.setSize(100,100);
			//ivjPopScrollPane.doLayout();
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjPopScrollPane;
}

/**
 * Return the RecombCanvas property value.
 * @return RecombCanvas
 */
private RecombCanvas getRecombCanvas() {
	if (ivjRecombCanvas == null) {
		try {
			ivjRecombCanvas = new RecombCanvas();
			ivjRecombCanvas.setName("RecombCanvas");
			ivjRecombCanvas.setBounds(0, 0, 800, 500);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjRecombCanvas;
}

/**
 * Return the Recombframe property value.
 * @return java.awt.Frame
 */
private Frame getRecombframe() {
	if (ivjRecombframe == null) {
		try {
			ivjRecombframe = new java.awt.Frame();
			ivjRecombframe.setName("Recombframe");
			ivjRecombframe.setLayout(new java.awt.BorderLayout());
			ivjRecombframe.setBounds(320, 255, 320, 200);
			ivjRecombframe.setTitle("Generation");
			getRecombframe().add("Center", getRecombScrollPane());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjRecombframe;
}

/**
 * Return the RecombScrollPane property value.
 * @return java.awt.ScrollPane
 */
private ScrollPane getRecombScrollPane() {
	if (ivjRecombScrollPane == null) {
		try {
			ivjRecombScrollPane = new java.awt.ScrollPane();
			ivjRecombScrollPane.setName("RecombScrollPane");
			ivjRecombScrollPane.add(getRecombCanvas(), getRecombCanvas().getName());
	    	Adjustable vadjust = ivjRecombScrollPane.getVAdjustable();
    	    Adjustable hadjust = ivjRecombScrollPane.getHAdjustable();
        	hadjust.setUnitIncrement(10);
       	 	vadjust.setUnitIncrement(10);

        	ivjRecombScrollPane.setSize(500, 300);


		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjRecombScrollPane;
}

/**
 * Return the SetTownCanvas property value.
 * @return TownCanvas
 */
private TownCanvas getSetTownCanvas() {
	if (ivjSetTownCanvas == null) {
		try {
			ivjSetTownCanvas = new TownCanvas();
			ivjSetTownCanvas.setName("SetTownCanvas");
			//ivjSetTownCanvas.setSize(340,340);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjSetTownCanvas;
}

/**
 * Return the SetTownsClearAll property value.
 * @return java.awt.Button
 */
private Button getSetTownsClearAll() {
	if (ivjSetTownsClearAll == null) {
		try {
			ivjSetTownsClearAll = new java.awt.Button();
			ivjSetTownsClearAll.setName("SetTownsClearAll");
			ivjSetTownsClearAll.setLabel("Clear all");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjSetTownsClearAll;
}

/**
 * Return the SetTownsLabel property value.
 * @return java.awt.Label
 */
private Label getSetTownsLabel() {
    if (ivjSetTownsLabel == null) {
        try {
            ivjSetTownsLabel = new java.awt.Label();
            ivjSetTownsLabel.setName("SetTownsLabel");
            ivjSetTownsLabel.setAlignment(java.awt.Label.CENTER);
            ivjSetTownsLabel.setText("Set at least 4 towns!");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjSetTownsLabel;
}


/**
 * Return the SetTownsDialog property value.
 * @return java.awt.Dialog
 */
private Dialog getSetTownsDialog() {
	if (ivjSetTownsDialog == null) {
		try {
			ivjSetTownsDialog = new java.awt.Dialog(new java.awt.Frame());
			ivjSetTownsDialog.setName("SetTownsDialog");
			ivjSetTownsDialog.setLayout(new java.awt.BorderLayout());
			ivjSetTownsDialog.setBounds(0, 0, 350, 450);
			ivjSetTownsDialog.setTitle("Set Problem");
			ivjSetTownsDialog.setModal(true);
			getSetTownsDialog().add("Center", getSetTownCanvas());
			getSetTownsDialog().add("North", getSetTownsLabel());
			getSetTownsDialog().add("South", getSetTownsPanel());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjSetTownsDialog;
}

/**
 * Return the SetTownsOk property value.
 * @return java.awt.Button
 */
private Button getSetTownsOk() {
	if (ivjSetTownsOk == null) {
		try {
			ivjSetTownsOk = new java.awt.Button();
			ivjSetTownsOk.setName("SetTownsOk");
			ivjSetTownsOk.setLabel("OK");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjSetTownsOk;
}

/**
 * Return the SetTownsAdd property value.
 * @return java.awt.Button
 */
private Button getSetTownsAdd() {
    if (ivjSetTownsAdd == null) {
        try {
            ivjSetTownsAdd = new java.awt.Button();
            ivjSetTownsAdd.setName("SetTownsAdd");
            ivjSetTownsAdd.setLabel("Add Randomly");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjSetTownsAdd;
}

/**
 * Return the SetTownsSet property value.
 * @return java.awt.Button
 */
private Button getSetTownsSet() {
    if (ivjSetTownsSet == null) {
        try {
            ivjSetTownsSet = new java.awt.Button();
            ivjSetTownsSet.setName("SetTownsSet");
            ivjSetTownsSet.setLabel("Set Grid");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjSetTownsSet;
}


/**
 * Return the SetTownsPanel property value.
 * @return java.awt.Panel
 */
private Panel getSetTownsPanel() {
	if (ivjSetTownsPanel == null) {
		try {
			ivjSetTownsPanel = new java.awt.Panel();
			ivjSetTownsPanel.setName("SetTownsPanel");
			ivjSetTownsPanel.setLayout(new java.awt.FlowLayout());
			ivjSetTownsPanel.add(getSetTownsClearAll(), getSetTownsClearAll().getName());
			ivjSetTownsPanel.add(getSetTownsAdd(), getSetTownsAdd().getName());
			ivjSetTownsPanel.add(getSetTownsSet(), getSetTownsSet().getName());
			ivjSetTownsPanel.add(getSetTownsOk(), getSetTownsOk().getName());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjSetTownsPanel;
}


/**
 * Return the StatisticSimulationSimulation property value.
 * @return java.awt.MenuItem
 */
private MenuItem getStatisticSimulationSimulation() {
	if (ivjStatisticSimulationSimulation == null) {
		try {
			ivjStatisticSimulationSimulation = new java.awt.MenuItem();
			ivjStatisticSimulationSimulation.setLabel("Recombination...");
			ivjStatisticSimulationSimulation.setEnabled(false);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticSimulationSimulation;
}

/**
 * Return the StartCalculateTSP property value.
 * @return java.awt.MenuItem
 */
private MenuItem getStartCalculateTSP() {
	if (ivjStartCalculateTSP == null) {
		try {
			ivjStartCalculateTSP = new java.awt.MenuItem();
			ivjStartCalculateTSP.setLabel("Initialize...");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStartCalculateTSP;
}

/**
 * Return the StartInfoAbout property value.
 * @return java.awt.MenuItem
 */
private MenuItem getStartInfoAbout() {
	if (ivjStartInfoAbout == null) {
		try {
			ivjStartInfoAbout = new java.awt.MenuItem();
			ivjStartInfoAbout.setLabel("About...");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStartInfoAbout;
}

/**
 * Return the StartInfoMenu property value.
 * @return java.awt.Menu
 */
private Menu getStartInfoMenu() {
	if (ivjStartInfoMenu == null) {
		try {
			ivjStartInfoMenu = new java.awt.Menu();
			ivjStartInfoMenu.setLabel("Info");
			ivjStartInfoMenu.add(getStartInfoAbout());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStartInfoMenu;
}

/**
 * Return the StartOptionsItem property value.
 * @return java.awt.MenuItem
 */
private MenuItem getStartOptionsItem() {
	if (ivjStartOptionsItem1 == null) {
		try {
			ivjStartOptionsItem1 = new java.awt.MenuItem();
			ivjStartOptionsItem1.setLabel("-");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStartOptionsItem1;
}

/**
 * Return the StartOptionsMenu property value.
 * @return java.awt.Menu
 */
private Menu getStartOptionsMenu() {
	if (ivjStartOptionsMenu == null) {
		try {
			ivjStartOptionsMenu = new java.awt.Menu();
			ivjStartOptionsMenu.setLabel("Options");
			ivjStartOptionsMenu.add(getStartOptionsOptions());
			ivjStartOptionsMenu.add(getStartOptionsProblem());
			ivjStartOptionsMenu.add(getStartOptionsItem());
            ivjStartOptionsMenu.add(getStartOptionsFonts1());
            ivjStartOptionsMenu.add(getStartOptionsFonts3());
			ivjStartOptionsMenu.add(getStartOptionsFonts());
            ivjStartOptionsMenu.add(getMenuItem1());
			ivjStartOptionsMenu.add(getStartOptionsTowns());
            ivjStartOptionsMenu.add(getMenuItem5());
			ivjStartOptionsMenu.add(getStartSaveFinal());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStartOptionsMenu;
}

/**
 * Return the StartOptionsOptions property value.
 * @return java.awt.MenuItem
 */
private MenuItem getStartOptionsOptions() {
	if (ivjStartOptionsOptions == null) {
		try {
			ivjStartOptionsOptions = new java.awt.MenuItem();
			ivjStartOptionsOptions.setLabel("GA Parameters...");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStartOptionsOptions;
}

/**
 * Return the StartOptionsTowns property value.
 * @return java.awt.MenuItem
 */
private MenuItem getStartOptionsProblem() {
    if (ivjStartOptionsProblem == null) {
        try {
            ivjStartOptionsProblem = new java.awt.MenuItem();
            ivjStartOptionsProblem.setLabel("Set Problem...");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjStartOptionsProblem;
}


/**
 * Return the StartOptionsTowns property value.
 * @return java.awt.CheckboxMenuItem
 */
private CheckboxMenuItem getStartOptionsTowns() {
	if (ivjStartOptionsTowns == null) {
		try {
			ivjStartOptionsTowns = new java.awt.CheckboxMenuItem();
			ivjStartOptionsTowns.setLabel("Show Towns");
			ivjStartOptionsTowns.setState(ivjDialogData.showtowns);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStartOptionsTowns;
}


/**
 * Return the StartSaveFinal property value.
 * @return java.awt.MenuItem
 */
private MenuItem getStartSaveFinal() {
    if (ivjStartSaveFinal == null) {
        try {
            ivjStartSaveFinal = new java.awt.MenuItem();
            ivjStartSaveFinal.setLabel("Copy Statistics...");
            ivjStartSaveFinal.setEnabled(false);
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjStartSaveFinal;
}


/**
 * Return the StartOptionsFonts property value.
 * @return java.awt.CheckboxMenuItem
 */
private CheckboxMenuItem getStartOptionsFonts() {
	if (ivjStartOptionsFonts2 == null) {
		try {
			ivjStartOptionsFonts2 = new java.awt.CheckboxMenuItem();
			ivjStartOptionsFonts2.setLabel("Large Fonts");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStartOptionsFonts2;
}

/**
 * Return the StatisticBestfitLabel property value.
 * @return java.awt.Label
 */
private Label getStatisticBestfitLabel() {
	if (ivjStatisticBestfitLabel == null) {
		try {
			ivjStatisticBestfitLabel = new java.awt.Label();
			ivjStatisticBestfitLabel.setName("StatisticBestfitLabel");
			ivjStatisticBestfitLabel.setAlignment(java.awt.Label.LEFT);
			ivjStatisticBestfitLabel.setFont(new java.awt.Font("Monospaced", 0, 12));
			ivjStatisticBestfitLabel.setText("Best Fitness: 0.0");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticBestfitLabel;
}

/**
 * Return the StatisticBestindivLabel property value.
 * @return java.awt.Label
 */
private Label getStatisticBestindivLabel() {
	if (ivjStatisticBestindivLabel == null) {
		try {
			ivjStatisticBestindivLabel = new java.awt.Label();
			ivjStatisticBestindivLabel.setName("StatisticBestindivLabel");
			ivjStatisticBestindivLabel.setFont(new java.awt.Font("Monospaced", 0, 12));
			ivjStatisticBestindivLabel.setText("The best trip for now:  ");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticBestindivLabel;
}

/**
 * Return the StatisticBestLabel property value.
 * @return java.awt.Label
 */
private Label getStatisticBestLabel() {
	if (ivjStatisticBestLabel == null) {
		try {
			ivjStatisticBestLabel = new java.awt.Label();
			ivjStatisticBestLabel.setName("StatisticBestLabel");
			ivjStatisticBestLabel.setForeground(java.awt.Color.red);
			ivjStatisticBestLabel.setFont(new java.awt.Font("Monospaced", 0, 12));
			ivjStatisticBestLabel.setText("Best Fitness");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticBestLabel;
}

/**
 * Return the StatisticCanvas property value.
 * @return StatisticCanvas
 */
private StatisticCanvas getStatisticCanvas() {
	if (ivjStatisticCanvas == null) {
		try {
			ivjStatisticCanvas = new StatisticCanvas();
			ivjStatisticCanvas.setName("StatisticCanvas");
			ivjStatisticCanvas.setBounds(0, 0, 600, 200);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticCanvas;
}

/**
 * Return the StatisticFileClose property value.
 * @return java.awt.MenuItem
 */
private MenuItem getStatisticFileClose() {
	if (ivjStatisticFileClose == null) {
		try {
			ivjStatisticFileClose = new java.awt.MenuItem();
			ivjStatisticFileClose.setLabel("Close");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticFileClose;
}

/**
 * Return the StatisticFileMenu property value.
 * @return java.awt.Menu
 */
private Menu getStatisticFileMenu() {
	if (ivjStatisticFileMenu == null) {
		try {
			ivjStatisticFileMenu = new java.awt.Menu();
			ivjStatisticFileMenu.setLabel("File");
			ivjStatisticFileMenu.add(getStatisticFileClose());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticFileMenu;
}

/**
 * Return the StatisticFrame property value.
 * @return java.awt.Frame
 */
private Frame getStatisticFrame() {
	if (ivjStatisticFrame == null) {
		try {
			ivjStatisticFrame = new java.awt.Frame();
			ivjStatisticFrame.setName("StatisticFrame");
			ivjStatisticFrame.setMenuBar(getStatisticFrameMenuBar());
			ivjStatisticFrame.setLayout(getStatisticFrameGridLayout());
			ivjStatisticFrame.setBounds(0, 0, 640, 255);
			ivjStatisticFrame.setTitle("TSPGA");
			ivjStatisticFrame.add(getStatisticTravelPanel(), getStatisticTravelPanel().getName());
			ivjStatisticFrame.add(getStatisticStatisticPanel(), getStatisticStatisticPanel().getName());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	}
		else {
			ivjStatisticFrame.setMenuBar(getStatisticFrameMenuBar());
		}
	return ivjStatisticFrame;
}

/**
 * Return the StatisticFrameGridLayout property value.
 * @return java.awt.GridLayout
 */
private GridLayout getStatisticFrameGridLayout() {
	java.awt.GridLayout ivjStatisticFrameGridLayout = null;
	try {
		/* Create part */
		ivjStatisticFrameGridLayout = new java.awt.GridLayout();
		ivjStatisticFrameGridLayout.setColumns(2);
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	};
	return ivjStatisticFrameGridLayout;
}

/**
 * Return the StatisticFrameMenuBar property value.
 * @return java.awt.MenuBar
 */
private MenuBar getStatisticFrameMenuBar() {
	if (ivjStatisticFrameMenuBar == null) {
		try {
			ivjStatisticFrameMenuBar = new java.awt.MenuBar();
			ivjStatisticFrameMenuBar.setName("MenueStatistics");
			ivjStatisticFrameMenuBar.add(getStatisticFileMenu());
			ivjStatisticFrameMenuBar.add(getStatisticGoMenu());
			ivjStatisticFrameMenuBar.add(getStartOptionsMenu());
			ivjStatisticFrameMenuBar.add(getStatisticWindowMenu());
			ivjStatisticFrameMenuBar.add(getStartInfoMenu());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticFrameMenuBar;
}

/**
 * Return the StatisticGenLabel property value.
 * @return java.awt.Label
 */
private Label getStatisticGenLabel() {
	if (ivjStatisticGenLabel == null) {
		try {
			ivjStatisticGenLabel = new java.awt.Label();
			ivjStatisticGenLabel.setName("StatisticGenLabel");
			ivjStatisticGenLabel.setAlignment(java.awt.Label.RIGHT);
			ivjStatisticGenLabel.setFont(new java.awt.Font("Monospaced", 0, 12));
			ivjStatisticGenLabel.setText("Gen.: 0   ");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticGenLabel;
}

/**
 * Return the StatisticGoGen property value.
 * @return java.awt.MenuItem
 */
private MenuItem getStatisticGoGen() {
	if (ivjStatisticGoGen == null) {
		try {
			ivjStatisticGoGen = new java.awt.MenuItem();
			ivjStatisticGoGen.setShortcut(
		new java.awt.MenuShortcut(java.awt.event.KeyEvent.VK_G, true));

			ivjStatisticGoGen.setLabel("Generation");
			ivjStatisticGoGen.setEnabled(false);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticGoGen;
}

/**
 * Return the StatisticGoMenu property value.
 * @return java.awt.Menu
 */
private Menu getStatisticGoMenu() {
	if (ivjStatisticGoMenu == null) {
		try {
			ivjStatisticGoMenu = new java.awt.Menu();
			ivjStatisticGoMenu.setLabel("Simulation");
			ivjStatisticGoMenu.setEnabled(true);
			ivjStatisticGoMenu.add(getStartCalculateTSP());
			ivjStatisticGoMenu.add(getMenuItem3());
			ivjStatisticGoMenu.add(getStatisticGoStep());
			ivjStatisticGoMenu.add(getStatisticGoGen());
			ivjStatisticGoMenu.add(getStatisticGoRun());
			ivjStatisticGoMenu.add(getMenuItem4());
			ivjStatisticGoMenu.add(getStatisticSimulationSimulation());
			ivjStatisticGoMenu.add(getMenuItem6());
			ivjStatisticGoMenu.add(getStatisticStopStop());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticGoMenu;
}

/**
 * Return the StatisticGoRun property value.
 * @return java.awt.MenuItem
 */
private MenuItem getStatisticGoRun() {
	if (ivjStatisticGoRun == null) {
		try {
			ivjStatisticGoRun = new java.awt.MenuItem();
			ivjStatisticGoRun.setLabel("Run...");
			ivjStatisticGoRun.setEnabled(false);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticGoRun;
}

/**
 * Return the StatisticGoStep property value.
 * @return java.awt.MenuItem
 */
private MenuItem getStatisticGoStep() {
	if (ivjStatisticGoStep == null) {
		try {
			ivjStatisticGoStep = new java.awt.MenuItem();
			ivjStatisticGoStep.setShortcut(
		new java.awt.MenuShortcut(java.awt.event.KeyEvent.VK_S, true));
			ivjStatisticGoStep.setLabel("Step");
			ivjStatisticGoStep.setEnabled(false);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticGoStep;
}

/**
 * Return the StatisticLabelPanel property value.
 * @return java.awt.Panel
 */
private Panel getStatisticLabelPanel() {
	if (ivjStatisticLabelPanel == null) {
		try {
			ivjStatisticLabelPanel = new java.awt.Panel();
			ivjStatisticLabelPanel.setName("StatisticLabelPanel");
			ivjStatisticLabelPanel.setLayout(getStatisticLabelPanelFlowLayout());
			ivjStatisticLabelPanel.add(getStatisticMeanfitLabel(), getStatisticMeanfitLabel().getName());
			ivjStatisticLabelPanel.add(getStatisticGenLabel(), getStatisticGenLabel().getName());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticLabelPanel;
}

/**
 * Return the StatisticLabelPanel1 property value.
 * @return java.awt.Panel
 */
private Panel getStatisticLabelPanel1() {
    if (ivjStatisticLabelPanel1 == null) {
        try {
            ivjStatisticLabelPanel1 = new java.awt.Panel();
            ivjStatisticLabelPanel1.setName("StatisticLabelPanel");
            ivjStatisticLabelPanel1.setLayout(getStatisticLabelPanelFlowLayout1());
            ivjStatisticLabelPanel1.add(getStatisticMeanLabel(), getStatisticMeanLabel().getName());
            ivjStatisticLabelPanel1.add(getStatisticBestLabel(), getStatisticBestLabel().getName());
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjStatisticLabelPanel1;
}


/**
 * Return the StatisticLabelPanelFlowLayout property value.
 * @return java.awt.FlowLayout
 */
private FlowLayout getStatisticLabelPanelFlowLayout() {
	java.awt.FlowLayout ivjStatisticLabelPanelFlowLayout = null;
	try {
		/* Create part */
		ivjStatisticLabelPanelFlowLayout = new java.awt.FlowLayout();
		ivjStatisticLabelPanelFlowLayout.setVgap(5);
		ivjStatisticLabelPanelFlowLayout.setHgap(5);
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	};
	return ivjStatisticLabelPanelFlowLayout;
}

/**
 * Return the StatisticLabelPanelFlowLayout1 property value.
 * @return java.awt.FlowLayout
 */
private FlowLayout getStatisticLabelPanelFlowLayout1() {
    java.awt.FlowLayout ivjStatisticLabelPanelFlowLayout1 = null;
    try {
        /* Create part */
        ivjStatisticLabelPanelFlowLayout1 = new java.awt.FlowLayout();
        ivjStatisticLabelPanelFlowLayout1.setVgap(5);
        ivjStatisticLabelPanelFlowLayout1.setHgap(5);
    } catch (java.lang.Throwable ivjExc) {
        handleException(ivjExc);
    };
    return ivjStatisticLabelPanelFlowLayout1;
}


/**
 * Return the StatisticMeanfitLabel property value.
 * @return java.awt.Label
 */
private Label getStatisticMeanfitLabel() {
	if (ivjStatisticMeanfitLabel == null) {
		try {
			ivjStatisticMeanfitLabel = new java.awt.Label();
			ivjStatisticMeanfitLabel.setName("StatisticMeanfitLabel");
			ivjStatisticMeanfitLabel.setAlignment(java.awt.Label.LEFT);
			ivjStatisticMeanfitLabel.setFont(new java.awt.Font("Monospaced", 0, 12));
			ivjStatisticMeanfitLabel.setText("Mean Fitness: 0.0     ");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticMeanfitLabel;
}

/**
 * Return the StatisticMeanLabel property value.
 * @return java.awt.Label
 */
private Label getStatisticMeanLabel() {
	if (ivjStatisticMeanLabel == null) {
		try {
			ivjStatisticMeanLabel = new java.awt.Label();
			ivjStatisticMeanLabel.setName("StatisticMeanLabel");
			ivjStatisticMeanLabel.setFont(new java.awt.Font("Monospaced", 0, 12));
			ivjStatisticMeanLabel.setForeground(java.awt.Color.green);
			ivjStatisticMeanLabel.setText("Mean Fitness");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticMeanLabel;
}

/**
 * Return the StatisticScrollPane property value.
 * @return java.awt.ScrollPane
 */
private ScrollPane getStatisticScrollPane() {
	if (ivjStatisticScrollPane == null) {
		try {
			ivjStatisticScrollPane = new java.awt.ScrollPane();
			ivjStatisticScrollPane.setName("StatisticScrollPane");
			ivjStatisticScrollPane.add(getStatisticCanvas(), getStatisticCanvas().getName());
      		Adjustable vadjust = ivjStatisticScrollPane.getVAdjustable();
        	Adjustable hadjust = ivjStatisticScrollPane.getHAdjustable();
        	hadjust.setUnitIncrement(10);
        	vadjust.setUnitIncrement(10);

        	ivjStatisticScrollPane.setSize(300, 300);

		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticScrollPane;
}

/**
 * Return the StatisticStatisticPanel property value.
 * @return java.awt.Panel
 */
private Panel getStatisticStatisticPanel() {
	if (ivjStatisticStatisticPanel == null) {
		try {
			ivjStatisticStatisticPanel = new java.awt.Panel();
			ivjStatisticStatisticPanel.setName("StatisticStatisticPanel");
			ivjStatisticStatisticPanel.setLayout(new java.awt.BorderLayout());
			getStatisticStatisticPanel().add("North", getStatisticLabelPanel1());
			getStatisticStatisticPanel().add("South", getStatisticLabelPanel());
			getStatisticStatisticPanel().add("Center", getStatisticScrollPane());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticStatisticPanel;
}


/**
 * Return the StatisticStopStop property value.
 * @return java.awt.MenuItem
 */
private MenuItem getStatisticStopStop() {
	if (ivjStatisticStopStop == null) {
		try {
			ivjStatisticStopStop = new java.awt.MenuItem();
			ivjStatisticStopStop.setLabel("Stop");
			ivjStatisticStopStop.setEnabled(false);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticStopStop;
}

/**
 * Return the StatisticTravelPanel property value.
 * @return java.awt.Panel
 */
private Panel getStatisticTravelPanel() {
	if (ivjStatisticTravelPanel == null) {
		try {
			ivjStatisticTravelPanel = new java.awt.Panel();
			ivjStatisticTravelPanel.setName("StatisticTravelPanel");
			ivjStatisticTravelPanel.setLayout(new java.awt.BorderLayout());
			getStatisticTravelPanel().add("South", getStatisticBestfitLabel());
			getStatisticTravelPanel().add("North", getStatisticBestindivLabel());
			getStatisticTravelPanel().add("Center", getStatisticTravelCanvas());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticTravelPanel;
}

/**
 * Return the StatisticTravelCanvas property value.
 * @return TravelCanvas
 */
private TravelCanvas getStatisticTravelCanvas() {
	if (ivjStatisticTravelCanvas == null) {
		try {
			ivjStatisticTravelCanvas = new TravelCanvas();
			ivjStatisticTravelCanvas.setName("StatisticTravelCanvas");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticTravelCanvas;
}

/**
 * Return the StatisticWindowMenu property value.
 * @return java.awt.Menu
 */
private Menu getStatisticWindowMenu() {
	if (ivjStatisticWindowMenu == null) {
		try {
			ivjStatisticWindowMenu = new java.awt.Menu();
			ivjStatisticWindowMenu.setLabel("Window");
			ivjStatisticWindowMenu.setEnabled(false);
			ivjStatisticWindowMenu.add(getStatisticWindowPop());
			ivjStatisticWindowMenu.add(getStatisticWindowRecomb());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticWindowMenu;
}

/**
 * Return the StatisticWindowPop property value.
 * @return java.awt.MenuItem
 */
private MenuItem getStatisticWindowPop() {
	if (ivjStatisticWindowPop == null) {
		try {
			ivjStatisticWindowPop = new java.awt.MenuItem();
			ivjStatisticWindowPop.setLabel("Population");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticWindowPop;
}

/**
 * Return the StatisticWindowRecomb property value.
 * @return java.awt.MenuItem
 */
private MenuItem getStatisticWindowRecomb() {
	if (ivjStatisticWindowRecomb == null) {
		try {
			ivjStatisticWindowRecomb = new java.awt.MenuItem();
			ivjStatisticWindowRecomb.setLabel("Generation");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStatisticWindowRecomb;
}

/**
 * Return the StringCanvas property value.
 * @return StringCanvas
 */
private StringCanvas getStringCanvas() {
	if (ivjStringCanvas == null) {
		try {
			ivjStringCanvas = new StringCanvas();
			ivjStringCanvas.setName("StringCanvas");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStringCanvas;
}

/**
 * Return the StringClose property value.
 * @return java.awt.Button
 */
private Button getStringClose() {
	if (ivjStringClose == null) {
		try {
			ivjStringClose = new java.awt.Button();
			ivjStringClose.setName("StringClose");
			ivjStringClose.setLabel("Close");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStringClose;
}

/**
 * Return the StringFrame property value.
 * @return java.awt.Frame
 */
private Frame getStringFrame() {
	if (ivjStringFrame == null) {
		try {
			ivjStringFrame = new java.awt.Frame();
			ivjStringFrame.setName("StringFrame");
			ivjStringFrame.setLayout(new java.awt.BorderLayout());
			ivjStringFrame.setBounds(0, 0, 780, 350);
			ivjStringFrame.setTitle("Recombination String");
			getStringFrame().add("South", getStringPanel());
			getStringFrame().add("Center", getStringCanvas());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStringFrame;
}

/**
 * Return the StringPanel property value.
 * @return java.awt.Panel
 */
private Panel getStringPanel() {
	if (ivjStringPanel == null) {
		try {
			ivjStringPanel = new java.awt.Panel();
			ivjStringPanel.setName("StringPanel");
			ivjStringPanel.setLayout(new java.awt.FlowLayout());
			ivjStringPanel.add(getStringStep(), getStringStep().getName());
			ivjStringPanel.add(getStringTrace(), getStringTrace().getName());
			ivjStringPanel.add(getStringResult(), getStringResult().getName());
			ivjStringPanel.add(getStringClose(), getStringClose().getName());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStringPanel;
}

/**
 * Return the StringStep property value.
 * @return java.awt.Button
 */
private Button getStringStep() {
	if (ivjStringStep == null) {
		try {
			ivjStringStep = new java.awt.Button();
			ivjStringStep.setName("StringStep");
			ivjStringStep.setLabel("Step");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjStringStep;
}

/**
 * Return the StringStep property value.
 * @return java.awt.Button
 */
private Button getStringResult() {
    if (ivjStringResult == null) {
        try {
            ivjStringResult = new java.awt.Button();
            ivjStringResult.setName("StringResult");
            ivjStringResult.setLabel("Result");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjStringResult;
}

/**
 * Return the StringStep property value.
 * @return java.awt.Button
 */
private Button getStringTrace() {
    if (ivjStringTrace == null) {
        try {
            ivjStringTrace = new java.awt.Button();
            ivjStringTrace.setName("StringTrace");
            ivjStringTrace.setLabel("Final");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjStringTrace;
}


/**
 * Called whenever the part throws an exception.
 * @param exception java.lang.Throwable
 */
private void handleException(Throwable exception) {

	/* Uncomment the following lines to print uncaught exceptions to stdout */
	// System.out.println("--------- UNCAUGHT EXCEPTION ---------");
	// exception.printStackTrace(System.out);
}

/**
 * handles the Applet init method
 */
public void init() {
	super.init();
	try {
		//setName("TSPGA");
		setFont(new java.awt.Font("Monospaced", 0, 12));
		setLayout(new BorderLayout());
		add("Center",getGaStart());
		//initConnections();
		resize(150, 50);
		getGaStart().addActionListener(this);
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
}

/**
 * Initializes connections
 */
private void initConnections() {
	getStartOptionsOptions().addActionListener(this);
	getStartCalculateTSP().addActionListener(this);
	getOptionsClose().addActionListener(this);
	getOptionsOk().addActionListener(this);
	getOptionsDefault().addActionListener(this);
	getOptionsMutScroll().addAdjustmentListener(this);
	getOptionsRecombScroll().addAdjustmentListener(this);
	getOptionsDialog().addWindowListener(this);
	getStatisticFileClose().addActionListener(this);
	getStringClose().addActionListener(this);
	getStatisticSimulationSimulation().addActionListener(this);
	getStatisticGoRun().addActionListener(this);
	getStatisticStopStop().addActionListener(this);
	getStartInfoAbout().addActionListener(this);
	getSetTownsOk().addActionListener(this);
	getPopFrame().addWindowListener(this);
	getStatisticWindowPop().addActionListener(this);
	getRecombframe().addWindowListener(this);
	getStatisticWindowRecomb().addActionListener(this);
	getErxClose().addActionListener(this);
	getStartOptionsTowns().addItemListener(this);
	getStartOptionsFonts1().addItemListener(this);
    getStartOptionsFonts().addItemListener(this);
	getStartOptionsFonts3().addItemListener(this);
	getSetTownsClearAll().addActionListener(this);
	//getStatisticFrame().addWindowListener(this);
	getParentFrame().addWindowListener(this);
	getStatisticGoStep().addActionListener(this);
	getStatisticGoGen().addActionListener(this);
	getStringStep().addActionListener(this);
	getStringResult().addActionListener(this);
	getStringTrace().addActionListener(this);
	getErxStep().addActionListener(this);
	getErxResult().addActionListener(this);
	getErxTrace().addActionListener(this);
	getGridClose().addActionListener(this);
    getGridOk().addActionListener(this);
    getTownClose().addActionListener(this);
    getTownOk().addActionListener(this);
	getStartOptionsProblem().addActionListener(this);
	getSetTownsAdd().addActionListener(this);
	getSetTownsSet().addActionListener(this);
	getStartSaveFinal().addActionListener(this);
    getStatisticFrame().addComponentListener(this);
    getSetTownsDialog().addComponentListener(this);
	getParentFrame().addComponentListener(this);

}

/**
 * Method to handle events for the ItemListener interface.
 * @param e java.awt.event.ItemEvent
 */
public void itemStateChanged(java.awt.event.ItemEvent e) {
	Object source = e.getSource();
	if ((source == getStartOptionsFonts1()) ) {
		ivjStartOptionsFonts1.setState(true);
		ivjDialogData.largefonts=0;
		ivjStartOptionsFonts2.setState(false);
		ivjStartOptionsFonts3.setState(false);
	}
	if ((source == getStartOptionsFonts()) ) {
		ivjStartOptionsFonts2.setState(true);
		ivjDialogData.largefonts=2;
		ivjStartOptionsFonts3.setState(false);
		ivjStartOptionsFonts1.setState(false);
	}
	if ((source == getStartOptionsFonts3()) ) {
		ivjStartOptionsFonts3.setState(true);
		ivjDialogData.largefonts=1;
		ivjStartOptionsFonts2.setState(false);
		ivjStartOptionsFonts1.setState(false);
	}
	if ((source == getStartOptionsTowns()) ) {
		ivjDialogData.showtowns=ivjStartOptionsTowns.getState();
	}
}

/**
 * main entrypoint - starts here when it is run as an application
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		java.awt.Frame frame;
		try {
			Class aFrameClass = Class.forName("uvm.abt.edit.TestFrame");
			frame = (java.awt.Frame)aFrameClass.newInstance();
		} catch (java.lang.Throwable ivjExc) {
			frame = new java.awt.Frame();
		}
		TSPGA aTSPGA = new TSPGA();
		aTSPGA.initConnections();
		aTSPGA.getStatisticFrame().show();
		//frame.add("Center", aTSPGA);
		//frame.setSize(aTSPGA.getSize());
		//frame.setSize(150,120);
		//aTSPGA.init();
		//aTSPGA.start();
		//frame.setVisible(true);

		aTSPGA.destroy();
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of java.applet.Applet");
	}
}

/**
 * Method to handle events for the WindowListener interface.
 * @param e java.awt.event.WindowEvent
 */
public void windowActivated(java.awt.event.WindowEvent e) {
}

/**
 * Method to handle events for the WindowListener interface.
 * @param e java.awt.event.WindowEvent
 */
public void windowClosed(java.awt.event.WindowEvent e) {
}

/**
 * Method to handle events for the WindowListener interface.
 * @param e java.awt.event.WindowEvent
 */
public void windowClosing(java.awt.event.WindowEvent e) {
	Object source = e.getSource();
	if ((source == getOptionsDialog()) ) {
		getOptionsDialog().dispose();
	}
	if ((source == getParentFrame()) ) {
		getParentFrame().dispose();
	}
	if ((source == getPopFrame()) ) {
		getPopCanvas().disposeFrames();
		getPopFrame().dispose();
		ivjStatisticWindowPop.setEnabled(true);
	}
	if ((source == getRecombframe()) ) {
		getRecombCanvas().disposeFrames();
		getRecombframe().dispose();
		ivjStatisticWindowRecomb.setEnabled(true);
	}
}

/**
 * Method to handle events for the WindowListener interface.
 * @param e java.awt.event.WindowEvent
 */
public void windowDeactivated(java.awt.event.WindowEvent e) {
}

/**
 * Method to handle events for the WindowListener interface.
 * @param e java.awt.event.WindowEvent
 */
public void windowDeiconified(java.awt.event.WindowEvent e) {
}

/**
 * Method to handle events for the WindowListener interface.
 * @param e java.awt.event.WindowEvent
 */
public void windowIconified(java.awt.event.WindowEvent e) {
}

/**
 * Method to handle events for the WindowListener interface.
 * @param e java.awt.event.WindowEvent
 */
public void windowOpened(java.awt.event.WindowEvent e) {
}

/**
 * Method to handle events for the ComponentListener interface.
 * @param e java.awt.event.ComponentEvent
 */
public void componentHidden(java.awt.event.ComponentEvent e) {
}
/**
 * Method to handle events for the ComponentListener interface.
 * @param e java.awt.event.ComponentEvent
 */
public void componentMoved(java.awt.event.ComponentEvent e) {
}
/**
 * Method to handle events for the ComponentListener interface.
 * @param e java.awt.event.ComponentEvent
 */
public void componentResized(java.awt.event.ComponentEvent e) {
    Object source = e.getSource();

    if ((source == getSetTownsDialog()) ) {

        int stepwidth;
        int stepheight;
        Dimension canvassize;

		//System.out.println("Resize SetTownsDialog \n");
        canvassize=ivjSetTownCanvas.getSize();
        stepwidth=(int)(canvassize.width/ivjDialogData.grid);
        stepheight=(int)(canvassize.height/ivjDialogData.grid);

        if(stepwidth>stepheight) stepwidth=stepheight;
        else stepheight= stepwidth;
        ivjSetTownCanvas.setTownCanvas(stepwidth,stepheight);
    }

    if ((source == getStatisticFrame()) ) {
        int stepwidth;
        int stepheight;
        Dimension canvassize;

		//System.out.println("Resize StatisticFrame \n");
        canvassize=ivjStatisticTravelCanvas.getSize();
        stepwidth=(int)(canvassize.width/ivjDialogData.grid);
        stepheight=(int)(canvassize.height/ivjDialogData.grid);

        if(stepwidth>stepheight) stepwidth=stepheight;
        else stepheight= stepwidth;
        ivjStatisticTravelCanvas.setTravelCanvas(stepwidth,stepheight);
        ivjStatisticCanvas.setChanged();

    }

    if ((source == getParentFrame()) ) {
        int stepwidth;
        int stepheight;
        Dimension canvassize;

		//System.out.println("Resize ParentFrame \n");
        canvassize=ivjParentTravelCanvas1.getSize();
        stepwidth=(int)(canvassize.width/ivjDialogData.grid);
        stepheight=(int)(canvassize.height/ivjDialogData.grid);

        if(stepwidth>stepheight) stepwidth=stepheight;
        else stepheight= stepwidth;
        ivjParentTravelCanvas1.setTravelCanvas(stepwidth,stepheight);

        canvassize=ivjParentTravelCanvas2.getSize();
        stepwidth=(int)(canvassize.width/ivjDialogData.grid);
        stepheight=(int)(canvassize.height/ivjDialogData.grid);

        if(stepwidth>stepheight) stepwidth=stepheight;
        else stepheight= stepwidth;
        ivjParentTravelCanvas2.setTravelCanvas(stepwidth,stepheight);

        canvassize=ivjParentTravelCanvas3.getSize();
        stepwidth=(int)(canvassize.width/ivjDialogData.grid);
        stepheight=(int)(canvassize.height/ivjDialogData.grid);

        if(stepwidth>stepheight) stepwidth=stepheight;
        else stepheight= stepwidth;
        ivjParentTravelCanvas3.setTravelCanvas(stepwidth,stepheight);

    }
}

/**
 * Method to handle events for the ComponentListener interface.
 * @param e java.awt.event.ComponentEvent
 */
public void componentShown(java.awt.event.ComponentEvent e) {
}


}

