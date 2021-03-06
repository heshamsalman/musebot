package Practice;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;

import javax.swing.*;

import Quizzes.*;
import SheetMusic.SheetMusic;

//THIS IS THE CLASS FOR PRACTICE
//@author MICHAEL WAYMAN

public class Practice extends JLayeredPane {
	private static final long serialVersionUID = 1L;
	
	//our globals to use for this class
	Question currentQuestion;
	
	JButton check;
	JButton next;
	JLabel bg;
	JLabel sideGradient;
	JLabel divider;
	JLayeredPane pane;
	JLayeredPane displayQ;
	JPanel buttons;
	QuizzesListener ql;

	
	public Practice() {
		//initialize and set the sizes of our classes
		setBounds(0, 0, 900, 720);
		pane = new JLayeredPane();
		displayQ = new JLayeredPane();
		buttons = new JPanel();
		bg = new JLabel(new ImageIcon(new File("Extras/Resources/Frame/Blue_Abstract_Home.png").toString()));
		check = new JButton("Check Answers");
		next = new JButton("Next Question");
		
		
		check.setBounds(380, 650, 120, 30);
		next.setBounds(500, 650, 120, 30);
		buttons.setBounds(0, 640, 900, 720);
		buttons.add(check);
		buttons.add(next);
		buttons.setBackground(Color.white);
		//add everything to our pane
		sideGradient = new JLabel(new ImageIcon(new File("Extras/Resources/Frame/practicePanel.png").toString()));
		divider = new JLabel(new ImageIcon(new File("Extras/Resources/Frame/divBar.png").toString()));
		divider.setLocation(900, 0);
		divider.setSize(20, 720);
		sideGradient.setLocation(920, 0);
		sideGradient.setSize(260, 720);
		pane.setBounds(0, 0, 1180, 720);
		pane.add(bg, new Integer(4));
		pane.add(divider, new Integer(5));
		pane.add(sideGradient, new Integer(6));
		//pane.add(check, new Integer(2));
		//pane.add(next, new Integer(2));
		pane.add(buttons, new Integer(2));

		//create an initial question and display it
		currentQuestion = newQuestion();
		DisplayQuestion dq = new DisplayQuestion(currentQuestion);
		dq.setBounds(0, 0, 900, 720);
		dq.setBackground(Color.WHITE);
		
		displayQ.setBounds(0, 0, 900, 720);
		displayQ.setBackground(Color.WHITE);
		displayQ.add(dq, new Integer(0));
		
		pane.setBackground(Color.white);
		
		pane.add(displayQ, new Integer(1));

		add(pane, new Integer(0));
	}
	
	public Question getCurrentQuestion() {
		return currentQuestion;
	}

	//show a new question
	public void showNewQuestion() {
		//remove the old question
		displayQ.remove(0);
		currentQuestion = newQuestion();
		DisplayQuestion dq = new DisplayQuestion(currentQuestion);
		dq.setBounds(0, 0, 900, 650);
		dq.setBackground(Color.WHITE);
		//add it to the question display
		
		displayQ.add(dq, new Integer(0));
		pane.setBackground(Color.WHITE);

	}
	//register the action listener for action events
	public void registerListeners(ActionListener l) {
		check.addActionListener(l);
		next.addActionListener(l);
	}
	
	//generate a new practice question
	public Question newQuestion() {
		Question q = new Question();
		//generate random time signature
		String[] foo = {"4/4", "3/4", "6/8", "3/2"};
		int bar = new Random().nextInt(4);
		SheetMusic sm = new SheetMusic(foo[bar]);
		sm.useDots(true);
		//generate random sheet music
		sm = sm.generateRandomSheetMusic(4);
		q.setSheetMusic(sm);
		//generate one of the two types of questions
		int qType = new Random().nextInt(2);
		if(qType == 0) {
			q.setQuestion("What are the 1st, 2nd, 3rd, and 4th notes(without accents) on the sheet music?");
			int w = 0;
			for(int j = 0; j < 4; j++) {
				for(int k = 0; k < sm.getMeasure(j).numberOfNotes(); k++) {
					if(w >= 4)
						break;
					w++;
					//add the answers
					q.addAnswer(sm.getMeasure(j).getNote(k).getNote().substring(0, 1).toUpperCase());
				}
			}
			//set the questions type
			q.setType("identify 4 notes");
		}
		if(qType == 1) {
			q.setQuestion("What are the 1st, 2nd, 3rd, and 4th note durations on the sheet music?");
			int w = 0;
			for(int j = 0; j < 4; j++) {
				for(int k = 0; k < sm.getMeasure(j).numberOfNotes(); k++) {
					if(w >= 4)
						break;
					w++;
					//add the answers
					q.addAnswer(sm.getMeasure(j).getNote(k).getDuration().toUpperCase());
				}
			}
			//set the question type
			q.setType("identify 4 durations");
		}

		return q;
	}
}
