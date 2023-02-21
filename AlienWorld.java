// Ozan Çatalorman, 21COMP1032
// Emine Ekin, COMP1111.4
// Project - 3
/* This is an alien world simulator which creates an alien population and
allows user to control it.*/


package project3rd_winter202223;

import java.util.Scanner;
import java.lang.Math;

public class Project3rd_Winter202223 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        execution();
    }
    //This function generates an alien.
    public static char[] generateAlien(){
        char[] geneticCode = new char[128];
        for(int i = 0; i < 128; i++){
            int tcode = (int)(Math.random()*3);
            switch(tcode){
                case 0: geneticCode[i] = 'C';break;
                case 1: geneticCode[i] = 'S';break;
                default: geneticCode[i] = 'E';
            }
        } 
        return geneticCode;
    }
    //This function sets the ID number of every alien in the population.
    public static int[] setIDs(int size){
        int[] tempIDs = new int[size];
        int tempID = 1;
        for(int i = 0; i < size; i++){
            tempIDs[i] = tempID;
            tempID++;
        }
        return tempIDs;
    }
    //This function creates a population.
    public static char[][] setPopulation(int size){
        char[][] geneticCodes = new char[size][128];
        
        for(int i = 0; i < size; i++){
            char[] tempGC = generateAlien();  
            for(int k = 0; k < 128; k++){
                geneticCodes[i][k] = tempGC[k];
            }
        }
        return geneticCodes;
    }
    //This function shows the IDs, genders and health scores of each alien in the population.
    public static void showPopulation(int[] IDs, char[][] geneticCodes){
        for(int i = 0; i < IDs.length; i++){
            System.out.print("ID:"+IDs[i]+", "+findGender(geneticCodes, i+1)+", Health:"+healthScores(geneticCodes, i+1));
            System.out.println();
        }
    }
    //This function calculates the health score of an alien of the population.
    public static int healthScores(char[][] geneticCodes,int a){
        int healthScore = 0;
        for(int i = 0; i < 126; i++){
            if(geneticCodes[a-1][i] == 'C' && geneticCodes[a-1][i+1] == 'S' && geneticCodes[a-1][i+2] == 'E'){
                healthScore++;
                i+=2;
            }
        }
        return healthScore;
    }
    //This function finds the gender of an alien in the population.
    public static String findGender(char[][] geneticCodes,int a){
        if(geneticCodes[a-1][127] == 'S'){
            return "Male";
        }
        return "Female";
    }
    //This function calculates the gender percentage and the health scores of the population.
    public static void calculateStatistics(char [][] geneticCodes){
        Scanner scanner = new Scanner(System.in);
        
        int femalecount = 0;
        for(int i = 1; i <= geneticCodes.length; i++){
            if(findGender(geneticCodes, i).equals("Female")){
                femalecount++;
            }
        }
        System.out.println("Female Population : " + (int)((((double)femalecount)/geneticCodes.length)*100)+"%");
        System.out.println("Male Population : " + (int)(((geneticCodes.length-((double)femalecount))/geneticCodes.length)*100)+"%");
        System.out.print("Enter a health thresold(between 1 and 41):");
        int thresold = scanner.nextInt();
        
        int healthcount = 0;
        for(int i = 1; i <= geneticCodes.length; i++ ){
            if(healthScores(geneticCodes, i) >= thresold){
                healthcount++;
            }
        }
        System.out.println((int)((((double)healthcount)/geneticCodes.length)*100)+"% of aliens have a health of "+thresold+" or higher.");
        
    }
    //This function calculates the reproduction probability of two aliens.
    public static double reProdProbability(char[][] geneticCodes, int a1, int a2){
        if(findGender(geneticCodes, a1) == findGender(geneticCodes, a2)){
            System.out.println("Alien "+a1+"("+findGender(geneticCodes, a1)+") and alien "+a2+"("+findGender(geneticCodes, a2)+") are same gender.");
            return 0;
        }
        int h1 = healthScores(geneticCodes, a1);
        int h2 = healthScores(geneticCodes, a2);
        double reProdProb = (h1+h2)/84.0;
        
        return reProdProb;
    }
    //This function mates two aliens if they can.
    public static char[][] mateTwoAliens(char[][] geneticCodes, int a1, int a2){
        double probability = reProdProbability(geneticCodes, a1, a2);
        char[][] newGeneticCodes = new char[geneticCodes.length][128];
        
        if(probability == 0){
            return geneticCodes;
        }
        else if(probability < 0.10){
            System.out.println("Aliens cannot mate, their compatibility is too low.");
            return geneticCodes;
        }
        else{
            System.out.println("Aliens mate: Offspring chance is bigger than thresold. They have one offspring.");
            newGeneticCodes = new char[newGeneticCodes.length+1][128];
            for(int i = 0; i < geneticCodes.length; i++){
                for(int j = 0; j < 128; j++){
                    newGeneticCodes[i][j] = geneticCodes[i][j];
                }
            }
            char[] newAlien = generateAlien();
            for(int i = 0; i < 128; i++){
                newGeneticCodes[newGeneticCodes.length-1][i] = newAlien[i];
            }
            
            setIDs(newGeneticCodes.length+1);
        
            System.out.println("Population has enlarged. Population now has "+newGeneticCodes.length+" aliens.");
            return newGeneticCodes;
        }
           
    }
    //This function enlarges the population according to the desired pairs.
    public static char[][] enlargePopulation(char[][] geneticCodes){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of pairs to mate:");
        int pairs = scanner.nextInt();
        
        
        char[][] newGeneticCodes = geneticCodes;
        for(int i = pairs; i!= 0; i-=2){
            int a1 = (int)(Math.random()*(geneticCodes.length+1));
            int a2 = (int)(Math.random()*(geneticCodes.length+1));
            if(a1 == 0){
                a1 = (int)(Math.random()*(geneticCodes.length+1));
            }
            if(a2 == 0){
                a2 = (int)(Math.random()*(geneticCodes.length+1));
            }
            
            System.out.println("Random Pair : Alien "+a1+" and Alien "+a2);
            newGeneticCodes = mateTwoAliens(newGeneticCodes, a1, a2);
        }
        return newGeneticCodes;
    }
    // This function shows the genetic code of an alien of the population.
    public static void examineAnAlien(char[][] geneticCodes){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the alien ID to examine the genetic code.");
        int a = scanner.nextInt();
        
        for(int i = 0; i < 128; i++){
            System.out.print(geneticCodes[a-1][i]);
        }
    }
    //This function asks a surprise question to the user.
    public static void surpriseGame(char[][] geneticCodes){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter an alien ID:");
        int alien = scanner.nextInt();
        System.out.println("There is an enemy alien specie attack to your aliens' planet.");
        System.out.println("Only the ones that has 5 health score or more can survive.");
        System.out.println("Do you think alien number "+alien+" can survive?");
        System.out.print("Enter '1' for YES and '2' for NO:");
        int answer = scanner.nextInt();
        boolean canSurvive = false;
        
        if(healthScores(geneticCodes, alien) >= 5){
            canSurvive = true;
        }
        
        if((answer == 1 && canSurvive == true) || (answer == 2 && canSurvive == false)){
            System.out.println("Your answer is correct!!!");
        }
        else{
            System.out.println("Your answer is false!!!");
        }
    }
    //This function asks user to what to do next.
    public static int nextOperation(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose one of the options:");
        System.out.println("(1)Mate two aliens");
        System.out.println("(2)Enlarge the population");
        System.out.println("(3)Show statistics");
        System.out.println("(4)Examine the genetic code of an alien");
        System.out.println("(5)Surprise Game");
        System.out.println("!!!(6)Quit the simulator!!!");
        System.out.println("What is your option?");
        int operation = scanner.nextInt();
        System.out.println("-----------------------------------------");
        return operation;
    }
    //This last function executes the whole process.
    public static void execution(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the 'Alien World Simulator'");
        System.out.print("Enter the size of the population:");
        int populationSize = scanner.nextInt();
        System.out.println("Simulating alien species...");
        System.out.println("-----------------------------------------");
        int[] IDs = setIDs(populationSize);
        char[][] geneticCodes = setPopulation(populationSize);
        showPopulation(IDs, geneticCodes);
        System.out.println("-----------------------------------------");
        System.out.println("Alien World population is generated!");
        int nextOP = 0;
        
        while(nextOP != 6){
            nextOP = nextOperation();
            
            switch(nextOP){
                case 1: 
                    System.out.print("Enter the first alien:");
                    int a1 = scanner.nextInt();
                    System.out.print("Enter the second alien:");
                    int a2 = scanner.nextInt();
                    geneticCodes = mateTwoAliens(geneticCodes, a1, a2);
                    IDs = setIDs(geneticCodes.length);break;
                case 2:
                    geneticCodes = enlargePopulation(geneticCodes);
                    IDs = setIDs(geneticCodes.length);break;
                case 3:
                    calculateStatistics(geneticCodes);break;
                case 4:
                    examineAnAlien(geneticCodes);break;
                case 5:
                    surpriseGame(geneticCodes);break;
                case 6:
                    break;
                default:
                    System.out.println("There is no such an option.");
            }
            
        }
        System.out.println("Shutting down the simulator.");
        System.out.println("Goodbye...");
        
    }

}
    
    
    
    
    

