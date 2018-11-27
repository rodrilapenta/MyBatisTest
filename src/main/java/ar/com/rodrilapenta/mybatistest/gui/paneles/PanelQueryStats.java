package ar.com.rodrilapenta.mybatistest.gui.paneles;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ar.com.rodrilapenta.mybatistest.buffer.HiloQueryHibernate;
import ar.com.rodrilapenta.mybatistest.db.dao.impl.GenericDAOImpl;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelQueryStats extends JPanel {
	private static final long serialVersionUID = 1390552473370182874L;
	private JTextField maxExecTime;
	private JTextField minExecTime;
	private JTextField avgExecTime;
	private JTextField query;
	
	public PanelQueryStats(final GenericDAOImpl dao) {
		setLayout(null);
		setSize(750, 300);
		maxExecTime = new JTextField(5);
		maxExecTime.setBounds(450, 74, 137, 28);
		add(maxExecTime);

		JLabel labelMaxExecTime = new JLabel("Max exec time");
		labelMaxExecTime.setBounds(162, 80, 126, 16);
		add(labelMaxExecTime);

		JLabel labelMinExecTime = new JLabel("Min exec time");
		labelMinExecTime.setBounds(160, 159, 126, 16);
		add(labelMinExecTime);

		minExecTime = new JTextField(5);
		minExecTime.setBounds(451, 153, 137, 28);
		add(minExecTime);

		JLabel labelAvgExecTime = new JLabel("Avg exec time");
		labelAvgExecTime.setBounds(160, 238, 126, 16);
		add(labelAvgExecTime);

		avgExecTime = new JTextField(5);
		avgExecTime.setBounds(451, 232, 137, 28);
		add(avgExecTime);
		
		/*maxExecTime.setText(queryStats.get("maxExecTime").toString());
		minExecTime.setText(queryStats.get("minExecTime").toString());
		avgExecTime.setText(queryStats.get("avgExecTime").toString());*/
		
		
		
		query = new JTextField(5);
		query.setFont(new Font("Consolas", Font.PLAIN, 11));
		query.setBounds(54, 11, 545, 28);
		add(query);
		
		JButton run = new JButton("Ejecutar");
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new HiloQueryHibernate(dao, minExecTime, maxExecTime, avgExecTime, query.getText()).start();
			}
		});
		run.setBounds(620, 10, 73, 28);
		add(run);
	}
}