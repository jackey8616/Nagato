package org.mocraft.Gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.mocraft.Nagato.LevyData;
import org.mocraft.Nagato.Nagato;

public class TeskGui extends Nagato implements Runnable, ActionListener {

	public static JFrame frame;
	public static JLabel lblTrap[] = new JLabel[4];
	public static JComboBox<?> target[] = new JComboBox[4];
	public static JButton confirm, cancel;
	
	public TeskGui() {
		GridBagConstraints lblTrapGrid[] = new GridBagConstraints[4];
		GridBagConstraints comboGrid[] = new GridBagConstraints[4];
		GridBagConstraints confirmGrid = new GridBagConstraints();
		GridBagConstraints cancelGrid = new GridBagConstraints();
		frame = new JFrame("Nagato - Tesk Manager");
		frame.setSize(300, 180);
		frame.setLayout(new GridBagLayout());
		
		for(int i = 0; i < 4; ++i) {
			lblTrapGrid[i] = new GridBagConstraints();
			lblTrap[i] = new JLabel("Trap 0" + (i + 1));
			lblTrapGrid[i].gridx = 0;
			lblTrapGrid[i].gridy = i;
			frame.add(lblTrap[i], lblTrapGrid[i]);
			
			comboGrid[i] = new GridBagConstraints();
			target[i] = new JComboBox<Object>(LevyData.name);
			comboGrid[i].gridx = 1;
			comboGrid[i].gridy = i;
			comboGrid[i].fill = GridBagConstraints.HORIZONTAL;
			comboGrid[i].anchor = GridBagConstraints.EAST;
			frame.add(target[i], comboGrid[i]);
		}
		confirm = new JButton("Confirm");
		confirm.setActionCommand("confirm");
		confirm.addActionListener(this);
		confirmGrid.gridx = 0;
		confirmGrid.gridy = 4;
		confirmGrid.fill = GridBagConstraints.HORIZONTAL;
		frame.add(confirm, confirmGrid);
		
		cancel = new JButton("Cancel");
		cancel.setActionCommand("cancel");
		cancel.addActionListener(this);
		cancelGrid.gridx = 1;
		cancelGrid.gridy = 4;
		cancelGrid.fill = GridBagConstraints.HORIZONTAL;
		frame.add(cancel, cancelGrid);
	}

	public void setVisible(boolean sw) {
		frame.setVisible(sw);
		return;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("confirm")) {
			for(int i = 0; i < 4; ++i) {
				trapTimers[i].setTarget(0, target[i].getSelectedIndex());
			}
		}
		frame.dispose();
	}

}