import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.util.*;

public class RaceTrack extends JPanel implements Runnable {

   Thread thread;
   public static ArrayList<Participant> participants = new ArrayList<Participant>();
   public static boolean start = false;
   public static double time = 0;
   public static int totalHeats;
   
   public static Random rand = new Random();
   
   public RaceTrack() {
      
      if(AthleticsRaceSimulator.raceDistance == 100) {
      
         this.setPreferredSize(new Dimension(1200,500));
         
      } else {
      
         this.setPreferredSize(new Dimension(1200,500));
      
      }
      createParticipants();
      startThread();
   
   }
   
   public void startThread() {
   
      thread = new Thread(this);
      thread.start();
   
   }
   
   @Override
   public void run() {
   
      while(thread != null) {
      
         try {
         
            TimeUnit.MILLISECONDS.sleep(20);
            
            if(start == true) {
            
               updateTime();
               updateScoreBoard();
            
            }
            repaint();
         
         } catch(InterruptedException e) {}
         
      
      }
   
   }
   
   public void updateScoreBoard() {
   
      for (int i = 0; i < participants.size(); i++) {
      
         Participant p = participants.get(i);
         double currDistance = p.distanceTravelled;
         
         if(AthleticsRaceSimulator.raceDistance == 100) {
         
            if(p.heat < Participant.currHeat) {
            
               participants.remove(p);
               continue;
               
            } else if(p.heat != Participant.currHeat) {
            
               continue;
            }
            
         }
         
         
            for (int j = 0; j < i; j++) {
            
               Participant p2 = participants.get(j);
               double distanceTwo = p2.distanceTravelled;
               
               if(p2.heat == Participant.currHeat || AthleticsRaceSimulator.raceDistance != 100) {
               
                  if(distanceTwo < currDistance) {
                  
                     Collections.swap(participants,i,j);
                  }
                  
               }
                  
            
            }
            
            for (int k = i + 1; k < participants.size(); k++) {
            
               Participant p2 = participants.get(k);
               double distanceTwo = p2.distanceTravelled;
               
               if(p2.heat == Participant.currHeat || AthleticsRaceSimulator.raceDistance != 100) {
               
                  if(distanceTwo > currDistance) {
                  
                     Collections.swap(participants,i,k);
                  }
                  
               }
               
            }
            
            
      }
      
   
   }
   
   public void updateTime() {
   
      time += AthleticsRaceSimulator.speedFactor*(0.02);
   
   }
   
   public void createParticipants() {
   
      for (int i = 0; i < AthleticsRaceSimulator.participantNames.size(); i++) {
      
         Participant participant;
         
         if(AthleticsRaceSimulator.raceDistance != 100) {
         
            participant = new Participant(AthleticsRaceSimulator.participantNames.get(i),AthleticsRaceSimulator.participantTimes.get(i),
         AthleticsRaceSimulator.participantKickScores.get(i),AthleticsRaceSimulator.participantExperience.get(i));
         
         } else {
         
            participant = new Participant(AthleticsRaceSimulator.participantNames.get(i),AthleticsRaceSimulator.participantTimes.get(i),
         AthleticsRaceSimulator.participantKickScores.get(i),3);
         
         }
         
         participants.add(participant);
         
      
      }

      
      if(AthleticsRaceSimulator.raceDistance == 100) {
      
         double heats = (0.125*participants.size() + 0.98);
   
         totalHeats = (int)heats;
         System.out.println(totalHeats);
         
         
      }
      
      start = true;
      
   }
   
   public void paintComponent(Graphics g) {
   
      super.paintComponent(g);

      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
      
      
      if(AthleticsRaceSimulator.raceDistance != 100) {
      
         drawBackground(g2);
         
      } else {

         drawBackground2(g2);
      
      }
      
      if(start == true && AthleticsRaceSimulator.raceDistance == 100) {
         
         startSprintRace(g2);
         drawScoreBoard(g2);
      
      } else if(start == true) {
      
         startRace(g2);
         drawScoreBoard(g2);
      
      }
      
   
   }
   
   public void drawScoreBoard(Graphics2D g) {
   
      g.setFont(new Font("TimesRoman",Font.BOLD,25));
      g.setColor(Color.black);
      g.drawString("Scoreboard",1025,20);
      g.setFont(new Font("TimesRoman",Font.BOLD,15));
      
      if(AthleticsRaceSimulator.raceDistance != 100) {
      
         if(participants.size() < 10) {
         
            for (int i = 0; i < participants.size(); i++) {
            
               String currName = participants.get(i).name;
               String currDistance = Double.toString(participants.get(i).distanceTravelled);
               
               String milliseconds = currDistance.substring(currDistance.indexOf(".") + 1);
               
               if(milliseconds.length() > 2) {
               
                  currDistance = currDistance.substring(0,currDistance.indexOf(".") + 3);
               
               }
               
               int currY = (100 + (i*40));
               g.drawString((i + 1) + ". " + currName + "     " + currDistance + "m",1020,currY);
               
            
            }
         
         } else {
         
            for (int i = 0; i < 10; i++) {
            
               String currName = participants.get(i).name;
               String currDistance = Double.toString(participants.get(i).distanceTravelled);
               
               String milliseconds = currDistance.substring(currDistance.indexOf(".") + 1);
               
               if(milliseconds.length() > 2) {
               
                  currDistance = currDistance.substring(0,currDistance.indexOf(".") + 3);
               
               }
               
               int currY = (100 + (i*40));
               g.drawString((i + 1) + ". " + currName + "     " + currDistance + "m",1020,currY);
            
            }
         
         }
         
      } else {
      
         if(participants.size() < 9) {
         
            for (int i = 0; i < participants.size(); i++) {
            
               String currName = participants.get(i).name;
               String currDistance = Double.toString(participants.get(i).distanceTravelled);
               
               String milliseconds = currDistance.substring(currDistance.indexOf(".") + 1);
               
               if(milliseconds.length() > 2) {
               
                  currDistance = currDistance.substring(0,currDistance.indexOf(".") + 3);
               
               }
               
               int currY = (100 + (i*40));
               g.drawString((i + 1) + ". " + currName + "     " + currDistance + "m",1020,currY);
               
            
            }
         
         } else {
         
            for (int i = 0; i < 8; i++) {
            
               String currName = participants.get(i).name;
               String currDistance = Double.toString(participants.get(i).distanceTravelled);
               
               String milliseconds = currDistance.substring(currDistance.indexOf(".") + 1);
               
               if(milliseconds.length() > 2) {
               
                  currDistance = currDistance.substring(0,currDistance.indexOf(".") + 3);
               
               }
               
               int currY = (100 + (i*40));
               g.drawString((i + 1) + ". " + currName + "     " + currDistance + "m",1020,currY);
            
            }
         
         }
      }
      
   
   }
   
   public void startSprintRace(Graphics2D g) {
   
      if(participants.size() <= 8) {
      
          for (int i = 0; i < participants.size(); i++) {
            
             participants.get(i).sprint();
             drawParticipantForSprint(participants.get(i),g);
            
          }
      
      } else {
      
         if(Participant.totalFinished >= Participant.currHeat*8) {
         
            Participant.currHeat++;
            

            time = 0;
         
         }
      
         if(Participant.currHeat != totalHeats) {
         
            for (int i = 0; i < 8; i++) {
            
               participants.get(i).sprint();
               drawParticipantForSprint(participants.get(i),g);
            
            }
            
         } else {
         
            for (int i = 0; i < participants.size(); i++) {
            
                  participants.get(i).sprint();
                  drawParticipantForSprint(participants.get(i),g);
            
            }
         
         
         }
         
         
      
      }
      
   
   }
   
   public void startRace(Graphics2D g) {
   
      for (int i = 0; i < participants.size(); i++) {
           
        try {
         
            participants.get(i).race();
            drawParticipant(participants.get(i),g);
            
        } catch(IndexOutOfBoundsException ioob) {}
            
      }
   
   }
   
   
   public void drawParticipantForSprint(Participant p,Graphics2D g) {
   
      int R = rand.nextInt(1,256);
      int G = rand.nextInt(1,256);
      int B = rand.nextInt(1,256);
      g.setColor(new Color(R,G,B));
      
      g.drawString(p.name,p.xPos,p.yPos - 20);
      
      g.setColor(Color.black);
      
      // HEAD
      g.fillOval(p.xPos,p.yPos,20,20);
      
      // TORSO
      g.drawLine(p.xPos + 10,p.yPos + 20,p.xPos + 10,p.yPos + 40);
      
      // ARMS
      g.drawLine(p.xPos,p.yPos + 30,p.xPos + 20,p.yPos + 30);
      g.drawLine(p.xPos,p.yPos + 30,p.xPos,p.yPos + 35);
      g.drawLine(p.xPos + 20,p.yPos + 30,p.xPos + 20,p.yPos + 25);
      
      // RIGHT LEG
      g.drawLine(p.xPos + 10,p.yPos + 40,p.xPos + 15,p.yPos + 45);
      g.drawLine(p.xPos + 15,p.yPos + 45,p.xPos + 12,p.yPos + 50);
      
      // LEFT LEG
      g.drawLine(p.xPos + 10,p.yPos + 40,p.xPos + 5,p.yPos + 45);
      g.drawLine(p.xPos + 5,p.yPos + 45,p.xPos,p.yPos + 40);
      
   }
   
   public void drawParticipant(Participant p,Graphics2D g) {
   
      int portionOfLap = p.numRotations;
      
      while(portionOfLap > 360) {
      
          portionOfLap -= 360;
         
      }
      
      portionOfLap += 360;
      
      int R = rand.nextInt(1,256);
      int G = rand.nextInt(1,256);
      int B = rand.nextInt(1,256);
      g.setColor(new Color(R,G,B));
      
      g.drawString(p.name,p.xPos,p.yPos - 20);
      
      g.setColor(Color.black);
      
      if(portionOfLap > 225 || portionOfLap < 45) {
      
         // HEAD
         g.fillOval(p.xPos,p.yPos,10,10);
         
         // TORSO
         g.drawLine(p.xPos + 5,p.yPos + 10,p.xPos + 5,p.yPos + 20);
         
         // ARMS
         g.drawLine(p.xPos,p.yPos + 15,p.xPos + 10,p.yPos + 15);
         g.drawLine(p.xPos,p.yPos + 15,p.xPos,p.yPos + 18);
         g.drawLine(p.xPos + 10,p.yPos + 15,p.xPos + 10,p.yPos + 12);
         
         // RIGHT LEG
         g.drawLine(p.xPos + 5,p.yPos + 20,p.xPos + 8,p.yPos + 22);
         g.drawLine(p.xPos + 8,p.yPos + 22,p.xPos + 6,p.yPos + 25);
         
         // LEFT LEG
         g.drawLine(p.xPos + 5,p.yPos + 20,p.xPos + 3,p.yPos + 22);
         g.drawLine(p.xPos + 3,p.yPos + 22,p.xPos,p.yPos + 20);
         
      } else {
      
         // HEAD
         g.fillOval(p.xPos,p.yPos,10,10);
         
         // TORSO
         g.drawLine(p.xPos + 5,p.yPos + 10,p.xPos + 5,p.yPos + 20);
         
         // ARMS
         g.drawLine(p.xPos,p.yPos + 15,p.xPos + 10,p.yPos + 15);
         g.drawLine(p.xPos,p.yPos + 15,p.xPos,p.yPos + 12);
         g.drawLine(p.xPos + 10,p.yPos + 15,p.xPos + 10,p.yPos + 18);
         
         // RIGHT LEG
         g.drawLine(p.xPos + 5,p.yPos + 20,p.xPos + 7,p.yPos + 22);
         g.drawLine(p.xPos + 7,p.yPos + 22,p.xPos + 10,p.yPos + 20);
         
         // LEFT LEG
         g.drawLine(p.xPos + 5,p.yPos + 20,p.xPos + 2,p.yPos + 22);
         g.drawLine(p.xPos + 2,p.yPos + 22,p.xPos + 4,p.yPos + 25);
      
      }
      
   }
   
   public void drawBackground(Graphics2D g) {
   
      g.setColor(Color.black);
      g.fillRect(0,0,1000,500);
      
      g.setColor(Color.green);
      g.fillRect(100,0,800,500);
      
      g.setColor(Color.blue);
      g.fillOval(100,0,400,500);
      g.fillOval(500,0,400,500);
      g.fillRect(300,0,400,500);
      
      g.setColor(Color.green);
      g.fillOval(200,100,300,300);
      g.fillOval(500,100,300,300);
      g.fillRect(350,100,300,300);
      
      g.setColor(Color.white);
      
      String TIME = timeToString(time);
      g.drawString(TIME,450,20);
   
   }
   
   public String timeToString(double time) {
   
      int minutes = ((int)time/60);
      
      double seconds = time - (minutes*60);
      
      String MINS = Integer.toString(minutes);
      String SECONDS = "";
      
      if(seconds < 10) {
      
         SECONDS = "0" + Double.toString(seconds);
         
         try {
         
            SECONDS = SECONDS.substring(0,5);
            
         } catch(StringIndexOutOfBoundsException s) {}
         
      } else {
      
         SECONDS = Double.toString(seconds);
         try {
         
            SECONDS = SECONDS.substring(0,5);
            
         } catch(StringIndexOutOfBoundsException s) {}
      
      }
      
      return MINS + ":" + SECONDS;
   
   }
   
   public void drawBackground2(Graphics2D g) {
   
      g.setColor(Color.black);
      g.fillRect(0,0,1000,500);
      
      g.setColor(Color.green);
      g.fillRect(100,0,800,500);
      
      g.setColor(Color.blue);
      g.fillRect(100,100,800,300);
      
      g.setColor(Color.white);
      g.drawLine(100,137,900,137);
      g.drawLine(100,174,900,174);
      g.drawLine(100,211,900,211);
      g.drawLine(100,248,900,248);
      g.drawLine(100,288,900,288);
      g.drawLine(100,328,900,328);
      g.drawLine(100,368,900,368);
      
      g.setColor(Color.white);
      
      String SECONDS = Double.toString(time);
      
      try {
      
         SECONDS = SECONDS.substring(0,SECONDS.indexOf(".") + 3);
      
      } catch(StringIndexOutOfBoundsException s) {}
      
      g.setFont(new Font("TimesRoman",Font.PLAIN,22));
      g.drawString(SECONDS,450,20);
      g.drawString(("Heat: " + Participant.currHeat),250,20);
   
   }
   

}