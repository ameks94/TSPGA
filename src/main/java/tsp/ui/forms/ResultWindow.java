package tsp.ui.forms;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import tsp.ui.CitiesPanel;
import tsp.ui.DrawerFactory;
import tsp.ui.DrawerFactory.DrawerType;
import tsp.utils.CurrentResultForShowing;

public class ResultWindow extends JFrame {

	private JPanel contentPane;
	private DefaultListModel<CurrentResultForShowing> listModel;
	private JList<String> list;
	private CitiesPanel outputPanel;
	private JLabel outputLabel;
	/**
	 * Create the frame.
	 */
	public ResultWindow(CitiesPanel outputPanel, JLabel outputLabel) {
		this.outputLabel = outputLabel;
		this.outputPanel = outputPanel;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 486, 398);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		listModel = new DefaultListModel<CurrentResultForShowing>();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		contentPane.add(scrollPane_1);
		list = new JList(listModel);
		scrollPane_1.setViewportView(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		list.addListSelectionListener(listSelectionListener);
	}
	
	private ListSelectionListener listSelectionListener = new ListSelectionListener() {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			CurrentResultForShowing selectedResult = (CurrentResultForShowing)listModel.get(list.getSelectedIndex());
			outputPanel.clearAll();
			outputPanel.setDrawerStrategy(DrawerFactory.getDrawerStrategy(DrawerType.CitiesPath));
			outputPanel.setCurrentTour(selectedResult.getTour());
			outputLabel.setText(String.valueOf(selectedResult.getDistance()));
			outputPanel.repaint();
		}
	};
	
	public DefaultListModel getResultModel() {
		return listModel;
	}

}
