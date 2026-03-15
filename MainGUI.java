import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import javax.imageio.*;
import java.awt.image.*;

public class MainGUI extends JPanel {

   JLabel title,question,createdBy;
   JButton start,instructions,confirm,back;
   JTextField answer;
   JPanel mainScreen,instructionScreen,infoEntryScreen,participantEntryScreen;
   JTable table;

   Font titleFont = new Font("Segoe UI", Font.BOLD, 36);
   Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
   Font buttonFont = new Font("Segoe UI", Font.BOLD, 18);

   BufferedImage backgroundImage;
   
   public static int stage = 1;
   public static boolean isOkk = true;
   public static int subStage = 1;
   public static int numParticipants = 0;
   public static int currParticipant = 1;
   public ButtonListener bL = new ButtonListener();

   public MainGUI() {
   
      try {
      
         backgroundImage = ImageIO.read(new File("trackfield_2024_106-1600x900.jpg"));
      
      } catch(IOException e) {
      
         e.printStackTrace();
      }
      
      this.setPreferredSize(new Dimension(1200,675));
      this.setBackground(Color.white);
      
      title = new JLabel("Athletics Race Simulator");
      title.setHorizontalAlignment(SwingConstants.CENTER);
      title.setPreferredSize(new Dimension(800,225));
      question = new JLabel("");

      createdBy = new JLabel("Created By Ethan James");
      createdBy.setHorizontalAlignment(SwingConstants.CENTER);
      createdBy.setFont(labelFont);
      createdBy.setAlignmentX(Component.CENTER_ALIGNMENT);
      
      answer = new JTextField(5);
      answer.setFont(new Font("Segoe UI", Font.PLAIN, 18));
      answer.setPreferredSize(new Dimension(200,35));
      
      start = createStyledButton("Start", new Color(46, 204, 113));
      start.setBackground(Color.getHSBColor(0.33f,0.45f,0.73f));
      start.setPreferredSize(new Dimension(600,675));
      start.setOpaque(true);
      start.addActionListener(bL);
      
      instructions = createStyledButton("Instructions", new Color(52, 152, 219));
      instructions.setBackground(Color.getHSBColor(0.15f,0.4f,0.95f));
      instructions.setPreferredSize(new Dimension(600,675));
      instructions.setOpaque(true);
      instructions.addActionListener(bL);
      
      back = createStyledButton("Back", new Color(231, 76, 60));
      back.setBackground(Color.red);
      back.setOpaque(true);
      back.addActionListener(bL);
      
      confirm = confirm = createStyledButton("Confirm", new Color(39, 174, 96));
      confirm.setBackground(Color.green);
      confirm.setOpaque(true);
      confirm.addActionListener(bL);

      title.setFont(titleFont);
      title.setAlignmentX(Component.CENTER_ALIGNMENT);
      question.setFont(labelFont);

      start.setFont(buttonFont);
      start.setAlignmentX(Component.CENTER_ALIGNMENT);
      instructions.setFont(buttonFont);
      instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
      confirm.setFont(buttonFont);
      back.setFont(buttonFont);
      
      mainScreen = new JPanel();
      mainScreen.setLayout(new BoxLayout(mainScreen, BoxLayout.Y_AXIS));
      
      instructionScreen = new JPanel();
      instructionScreen.setPreferredSize(new Dimension(800,500));
      instructionScreen.setLayout(new BorderLayout());
      
      JTextArea instructionsText = new JTextArea();
      instructionsText.setEditable(false);
      instructionsText.setLineWrap(true);
      instructionsText.setWrapStyleWord(true);
      instructionsText.setFont(new Font("Segoe UI", Font.PLAIN, 16));
      instructionsText.setOpaque(false);

      String instructionContent = setUpInstructions();

      instructionsText.setText(instructionContent);

      JScrollPane instructionScroll = new JScrollPane(instructionsText);
      instructionScroll.setBorder(null);
      instructionScroll.getViewport().setOpaque(false);
      instructionScroll.setOpaque(false);

      instructionScreen.add(instructionScroll, BorderLayout.CENTER);
      instructionScreen.add(back, BorderLayout.SOUTH);
      
      infoEntryScreen = new JPanel();
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(10,10,10,10);

      gbc.gridx = 0;
      gbc.gridy = 0;
      infoEntryScreen.add(question, gbc);

      gbc.gridy = 1;
      infoEntryScreen.add(answer, gbc);

      gbc.gridy = 2;
      infoEntryScreen.add(confirm, gbc);
      
      participantEntryScreen = new JPanel();
      participantEntryScreen.setLayout(new GridLayout(12,5));

      
      add(mainScreen);
      mainScreen.add(title);
      mainScreen.add(Box.createVerticalStrut(40));
      mainScreen.add(start);
      mainScreen.add(Box.createVerticalStrut(20));
      mainScreen.add(instructions);
      mainScreen.add(Box.createVerticalStrut(40));
      mainScreen.add(createdBy);

      mainScreen.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
      instructionScreen.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
      infoEntryScreen.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
      participantEntryScreen.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
      
      add(instructionScreen);
      add(infoEntryScreen);
      add(participantEntryScreen);
      
      infoEntryScreen.setVisible(false);
      participantEntryScreen.setVisible(false);
      
      manageStages();
      
   
   }

   private JButton createStyledButton(String text, Color color) {

      JButton btn = new JButton(text);
      btn.setFocusPainted(false);
      btn.setBackground(color);
      btn.setForeground(Color.WHITE);
      btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
      btn.setBorder(BorderFactory.createEmptyBorder(15,30,15,30));

      return btn;
   }
   
   @Override
   public void paintComponent(Graphics g) {
         
       super.paintComponent(g);
       if (backgroundImage != null) {
            
          g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
       }
            
   }
   
   public String setUpInstructions() {
   
      StringBuilder text = new StringBuilder();

      try {
      
         Scanner instructionScanner = new Scanner(new File("instructions"));
         
         while(instructionScanner.hasNextLine()) {
         
            text.append(instructionScanner.nextLine()).append("\n\n");
         
         }

         return text.toString();
         
      } catch(FileNotFoundException fnfe) {

         return "";
      }
   
   }
   
   
   public void manageStages() {
   
      switch(stage) {
      
         case(1):
         
            if(subStage == 1) {
            
               mainScreen.setVisible(true);
               instructionScreen.setVisible(false);
               
            } else if(subStage == 2) {
            
               instructionScreen.setVisible(true);
               mainScreen.setVisible(false);
            
            }
            break;
            
          case(2):
          
            if(subStage == 1) {
            
               mainScreen.setVisible(false);
               infoEntryScreen.setVisible(true);
               question.setText("Race Distance (meters)");
            
            } else if(subStage == 2) {
            
               question.setText("How many participants will be racing?");
               
            } else if(subStage == 3) {
            
               question.setText("How much would you like the race sped up? (i.e 5 for 5x speed)");
               
            }
            break;
            
          case(3):
          
            infoEntryScreen.setVisible(false);
            participantEntryScreen.setVisible(true);

            participantEntryScreen.removeAll();

            JScrollPane scroll = new JScrollPane(table);
            participantEntryScreen.setLayout(new BorderLayout());

            participantEntryScreen.add(scroll, BorderLayout.CENTER);
            participantEntryScreen.add(confirm, BorderLayout.SOUTH);

            participantEntryScreen.revalidate();
            participantEntryScreen.repaint();

            break;
          
            
      
      }
   
   }
   
   private class ButtonListener implements ActionListener {
   
      public void actionPerformed(ActionEvent ae) {
      
         JButton b = (JButton)ae.getSource();
         
         
         if(b == confirm) {
         
            switch(stage) {
            
               case(2):
               
                  if(subStage == 1) {

                     try {
                     
                        AthleticsRaceSimulator.raceDistance = Integer.valueOf(answer.getText());
                        
                        if(AthleticsRaceSimulator.raceDistance > 0) {
                        
                           subStage++;
                           
                        } else {
                        
                           JOptionPane.showMessageDialog(null,"Please enter a positive number!");
                        }
                           
                     } catch(NoSuchElementException e) {
                     
                        JOptionPane.showMessageDialog(null,"Please fill out all fields!");
                        
                     } catch(NumberFormatException n) {
                     
                        JOptionPane.showMessageDialog(null,"Please enter an integer");
                     }
                     
                     answer.setText("");
                     //subStage++;
                     
                  }
                  
                  else if(subStage == 2) {
                  
                     try {
                     
                        numParticipants = Integer.valueOf(answer.getText());
                        
                        if(numParticipants > 0) {
                        
                                                
                           String[] columns = {"Name", "Time", "Kick", "Experience"};
                           Object[][] data = new Object[numParticipants][4];

                           table = new JTable(data, columns);
                           JScrollPane tableScroll = new JScrollPane(table);

                           participantEntryScreen.add(tableScroll);
                           subStage++;
                           
                        } else {
                        
                           JOptionPane.showMessageDialog(null,"Please enter a positive number!");
                        }
                        
                     } catch(NoSuchElementException e) {
                     
                        JOptionPane.showMessageDialog(null,"Please fill out all fields!");
                        
                     } catch(NumberFormatException n) {
                     
                        JOptionPane.showMessageDialog(null,"Please enter an integer");
                     }
                                         
                     answer.setText("");
                     
                     //subStage++;
                     
                  }
                  
                  else if(subStage == 3) {
                  
                     try {
                     
                        AthleticsRaceSimulator.speedFactor = Integer.valueOf(answer.getText());
                        
                        if(AthleticsRaceSimulator.speedFactor > 0) {
                        
                           stage = 3;
                           subStage = 1;
                           
                        } else {
                        
                           JOptionPane.showMessageDialog(null,"Please enter a positive number!");
                        }
                        
                     } catch(NoSuchElementException e) {
                     
                        JOptionPane.showMessageDialog(null,"Please fill out all fields!");
                        
                     } catch(NumberFormatException n) {
                     
                        JOptionPane.showMessageDialog(null,"Please enter an integer");
                     }
                     
                     answer.setText("");
                     
                     
                  
                  }
                  break;
                  
                case(3):
                
                  
                     if(numParticipants <= 0) {
                     
                           isOkk = checkInputs();
                           
                           if(isOkk == true) {
                           
                              AthleticsRaceSimulator.setUpRace();
                              
                           }
                        
                        
                     } else {
                     
                        isOkk = checkInputs();
                        
                        if(isOkk == true) {
                        
                           if(numParticipants <= 10) {
                           
      
                                 for (int i = 0; i < numParticipants; i++) {
                                 
                                    String name = String.valueOf(table.getValueAt(i,0));
                                    String time = String.valueOf(table.getValueAt(i,1));

                                    int kick = Integer.parseInt(String.valueOf(table.getValueAt(i,2)));

                                    AthleticsRaceSimulator.participantNames.add(name);
                                    AthleticsRaceSimulator.participantTimes.add(time);
                                    AthleticsRaceSimulator.participantKickScores.add(kick);

                                    if(AthleticsRaceSimulator.raceDistance != 100){
                                       int exp = Integer.parseInt(String.valueOf(table.getValueAt(i,3)));
                                       AthleticsRaceSimulator.participantExperience.add(exp);
                                    }
                                    
                                    
                                 
                                 }
                              
                           } else {
                           
                                 for (int i = 0; i < 10; i++) {
                                 
                                    AthleticsRaceSimulator.participantNames.add(String.valueOf(table.getValueAt(i,0)));
                                    
                                    AthleticsRaceSimulator.participantTimes.add(String.valueOf(table.getValueAt(i,1)));
                                    
                                    
                                    AthleticsRaceSimulator.participantKickScores.add(Integer.valueOf(String.valueOf(table.getValueAt(i,2))));
                                    
                                    if(AthleticsRaceSimulator.raceDistance != 100) {
                                    
                                       AthleticsRaceSimulator.participantExperience.add(Integer.valueOf(String.valueOf(table.getValueAt(i,3))));
                                       
                                       
                                    }
                                    
                                 
                                 }
                                 
                           
                           }
                           numParticipants -= 10;
                           
                           if(numParticipants <= 0) {
                        
                              AthleticsRaceSimulator.setUpRace();
                           
                           }  
                           
                        }
              
                     
                     }

            
            }
         
         } else if(b == instructions) {
         
            subStage = 2;
            
         
         } else if(b == start) {
         
            stage = 2;
            subStage = 1;
            
         
         } else if(b == back) {
         
            if(stage == 1) {
            
               subStage = 1;
               
            
            }
         
         }
         
         if(isOkk == true) {
         
            manageStages();
            
         }
      
      }
   
   }
   
   public boolean checkTime(String time) {
   
      boolean valid = true;
      int colonCounter = 0;
      int pointCounter = 0;
      int startIndex = 0;
      int endIndex = 0;
      
      for (int i = 0; i < time.length(); i++) {
      
         char c = time.charAt(i);
         
         if(Character.isLetter(c)) {
         
            valid = false;
            
         }
         
         if(c == ':') {
         
            colonCounter++;
            startIndex = i;
         
         }
         
         if(c == '.') {
         
            endIndex = i;
            pointCounter++;
         
         }
      
      }
      
      if(colonCounter > 1 || pointCounter > 1) {
      
         valid = false;
      
      }
      
      if(endIndex != 0 && startIndex != 0) {
      
         String seconds = "";
         
         if(colonCounter == 1) {
         
           seconds = time.substring(startIndex + 1,endIndex);
            
         } else {
         
            seconds = time.substring(startIndex + 1);
         
         }
         
         if(seconds.length() != 2) {
         
            valid = false;
         
         }
      
      } else if(endIndex == 0) {
      
         valid = false;
      } else {
      
         String seconds = time.substring(endIndex + 1);
         
         for (int q = 0; q < seconds.length(); q++) {
         
            if(!Character.isDigit(seconds.charAt(q))) {
            
               valid = false;
            }
         
         }
      }
      
      return valid;
   
   }
   
   public boolean checkInputs() {

      for(int i = 0; i < numParticipants; i++) {

         Object nameObj = table.getValueAt(i,0);
         Object timeObj = table.getValueAt(i,1);
         Object kickObj = table.getValueAt(i,2);

         if(nameObj == null || timeObj == null || kickObj == null) {
            JOptionPane.showMessageDialog(null,"Please fill out all fields!");
            return false;
         }

         String time = timeObj.toString();

         if(!checkTime(time)) {
            JOptionPane.showMessageDialog(null,"Invalid time format!");
            return false;
         }

         try {

            int kick = Integer.parseInt(kickObj.toString());

            if(kick < 1 || kick > 10){
               JOptionPane.showMessageDialog(null,"Kick score must be 1-10");
               return false;
            }

            if(AthleticsRaceSimulator.raceDistance != 100){

               Object expObj = table.getValueAt(i,3);

               if(expObj == null){
                  JOptionPane.showMessageDialog(null,"Please fill experience score!");
                  return false;
               }

               int exp = Integer.parseInt(expObj.toString());

               if(exp < 1 || exp > 10){
                  JOptionPane.showMessageDialog(null,"Experience score must be 1-10");
                  return false;
               }

            }

         } catch(NumberFormatException e) {

            JOptionPane.showMessageDialog(null,"Scores must be integers!");
            return false;

         }

      }

      return true;
   }

}