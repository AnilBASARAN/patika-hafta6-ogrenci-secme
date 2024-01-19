import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.io.*;

public class StudentOperation  {
    String path1 = "/Users/anilbasaran/Documents/ogrencilerlistesi.txt";
    String path2= "/Users/anilbasaran/Documents/sozalanogrenciler.txt";

    Scanner scan = new Scanner(System.in);
    LinkedHashMap<Integer,String> studentList;
    LinkedHashMap<Integer,String > listOfSpeakingStudents;
    boolean writeAtTheEnd = true; // dosyaya yazma işleminde kullanılan parametre.

    public StudentOperation(){
        //createNewFile(path1);
        //createNewFile(path2);
        studentList = readFromFile(path1);
        listOfSpeakingStudents = readFromFile(path2);
    }

    public void createNewFile (String path){
        File file = new File(path);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void run(){
        Boolean isRunning = true;

        while (isRunning){
            System.out.println("\nBakalım Şans kimi bulacak :D \n");
            int randomStudentId = getStudentRandomId(studentList);
            System.out.println("Sıradaki şanslı öğrenci: "+ randomStudentId+ " "+ studentList.get(randomStudentId+"\n"));
            System.out.println("\tÖğrenci derste değilse -> 0'a \n" +
                    "\tDersteyse -> 1'e \n"+
                    "\tÇıkmak için 2'ye basın.");
            System.out.print("Seçiminiz :  ");
            int isStudentHere = scan.nextInt();

            if(isStudentHere == 1){
                // Öğrenci dersteyse dosyaya yazma işlemi yapılır.
                writeToFile(randomStudentId,studentList.get(randomStudentId),writeAtTheEnd);
            }
        }
    }
    public int randomNumber(){
        return (int) (Math.random()*3);
    }
    public int getStudentRandomId(LinkedHashMap<Integer,String>list){
        int randomStudentId;
        listOfSpeakingStudents = readFromFile("sozalanogrenciler.txt");
        if(listOfSpeakingStudents.size()<studentList.size()){
            //boolean if true, than data will be written to the end of the file rather than the beginning.
            //sayfa sonuna yazılacak
            writeAtTheEnd = true;
            do {
                randomStudentId = randomNumber();
                // Aynı ogrenci seçilmesi durumunda bilgi veriyor.
                if(listOfSpeakingStudents.containsKey(randomStudentId)){
                    System.out.println(listOfSpeakingStudents.containsKey(randomStudentId)+ " şanslısın tekrar seçildin ama söz almayanlara...");
                    System.out.println("Devam etmek için herhangi bir tuşa bas");
                    scan.next();
                }
            }while (listOfSpeakingStudents.containsKey(randomStudentId));
        }else {
            //Sayfa başına yazılacak
            writeAtTheEnd = false;
            System.out.println("Tüm öğrenciler konuştu, dosya sıfırlanacak");
            randomStudentId = randomNumber();
        }
        System.out.println("Şimdiye kadar soz alan öğrenci sayısı : "+ listOfSpeakingStudents.size()+"\n");
        return randomStudentId;
    }



    public LinkedHashMap<Integer,String> readFromFile(String fileName){
        LinkedHashMap<Integer,String> list = new LinkedHashMap<>();
        try {
            FileReader fileToRead = new FileReader(fileName);
            BufferedReader input = new BufferedReader(fileToRead);
            String line;
            while ((line = input.readLine()) != null){
                int id = Integer.parseInt(line);
                String name = input.readLine();
                list.put(id,name);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }

public void writeToFile(int id, String name,boolean writeAtTheEnd){
        try {
            FileWriter fileToWrite = new FileWriter("sozalanogrenciler.txt",writeAtTheEnd);
            BufferedWriter output = new BufferedWriter(fileToWrite);
            output.write(id+"\n");
            output.write(name+"\n");
            output.close();
            System.out.println("\n"+name+ " ogrencisi soz alan oğrenci listesine kaydedildi.\n");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}