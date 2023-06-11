

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class hw3 {

    public static void main(String[] args) {
        inputData();
    }

    public static void inputData() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter by space next data: Surname, Name, Patronymic, birthdate(dd.mm.yyyy), phone number and gender('m' or 'f')");
        String data = sc.nextLine();

        String[] splitData = data.split(" ");

        /**
         * Обработка общего количества введенных данных
         */
        if (splitData.length < 6) {
            throw new TooLessDataException("Was entered too less data");
        }
        if (splitData.length > 6) {
            throw new TooMuchDataException("Was entered too much data");
        }


        /**
         * Обработка даты рождения
         */
        String birthDate = splitData[3];
        if (birthDate.length() != 10) {
            throw new RuntimeException("Exceeded the allowed number of characters birthdate");
        }
        if (birthDate.charAt(2) != '.' || birthDate.charAt(5) != '.') {
            throw new RuntimeException("Incorrect symbol. Use dots to separate birthdate");
        }

        char[] birthArray = birthDate.toCharArray();
        for (int i = 0; i < birthArray.length; i++) {
            if (birthArray[i] != '.'){
                if (Character.digit(birthArray[i], 10) == -1) {
                    throw new RuntimeException("Incorrect data format. Use numbers to enter birthdate");
                }
            }
        }

        /**
         * Обработка номера телефона
         */
        String phoneNumber = splitData[4];
        try {
            Long.parseLong(phoneNumber);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect data format. Use numbers to enter the phone number");
        }

        /**
         * Обработка гендера
         */
        String gender = splitData[5];
        if (gender.equals("m") || gender.equals("f")) {
        } else {
            throw new RuntimeException("Incorrect gender symbol. Use 'f' or 'm'");
        }

        /**
         * Создание файла и запись в него данных
         */
        String surname = splitData[0];
        try {
            File file = new File("C:/Users/maris/Desktop/учеба пайтон/python/ошибки/Homework3", surname);
            if (file.createNewFile()) {
                System.out.println("'" + file.getName() + "'" +  " file created");
                try (FileWriter writer = new FileWriter(file.getName(), true)){
                    for (String c : splitData) {
                        writer.write("<" + c + ">");
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                try (FileWriter writer = new FileWriter(file.getName(), true)){
                    writer.write("\n");
                    for (String s : splitData) {
                        writer.write("<" + s + ">");
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("'" + file.getName() + "'" + " file already exists. New data added");
            }
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }
}

/**
 * Собственные исключения для проверки количества вводимых данных
 */
class TooLessDataException extends RuntimeException {
    public TooLessDataException(String s) {
        super(s);
    }
}

class TooMuchDataException extends RuntimeException {
    public TooMuchDataException(String s) {
        super(s);
    }
}