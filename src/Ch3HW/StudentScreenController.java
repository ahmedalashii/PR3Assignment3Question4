package Ch3HW;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class StudentScreenController implements Initializable {

    // This Programm has been completed By Ahmed Hesham Alashi 120191156 ..
    // Text Fields :
    @FXML
    private TextField textFieldID;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldMajor;
    @FXML
    private TextField textFieldGrade;
    @FXML
    private ListView<String> listViewStudents;
    // Buttons :
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonClear;
    @FXML
    private Button buttonSort;
    @FXML
    private Button buttonAvg;
    @FXML
    private Button buttonGroup;
    @FXML
    private Button buttonMap;
    @FXML
    private Button buttonMapRange;
    // Labels :
    @FXML
    private Label resultLabel;
    // Array List :
    List<Student> students = new ArrayList(); // asn an instance

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // This is just an intitial data
        listViewStudents.getItems()
                .add(String.format("%-5s %-10s %-7s %8.2f", "111", "Ahmad", "CS", 93.2));
        listViewStudents.getItems()
                .add(String.format("%-5s %-10s %-7s %8.2f", "123", "Marwa", "EDUC", 87.9));
        // or : 
//        students.add(new Student(111,"Ahmed","CS",93.2));
//        students.add(new Student(123,"Marwa","EDUC",87.9));
//        listViewStudents.getItems()
//                .add(String.format("%-5s %-10s %-7s %8.2f", students.get(0).getId(), students.get(0).getName(), students.get(0).getMajor(), students.get(0).getGrade()));
//        listViewStudents.getItems()
//                .add(String.format("%-5s %-10s %-7s %8.2f", students.get(1).getId(), students.get(1).getName(), students.get(1).getMajor(), students.get(1).getGrade()));
    }

    @FXML
    private void buttonAddHandle(ActionEvent event) { // Question 4
        // I want to add just if textFields are not empty
        try { // to check if the entered input is not a number or not >> if it is show an error message , if it's not complete the adding process
            if ((!textFieldID.getText().isEmpty()) && (!textFieldName.getText().isEmpty()) && (!textFieldMajor.getText().isEmpty()) && (!textFieldGrade.getText().isEmpty())) {
                boolean idFound = false;
                for (int i = 0; i < students.size(); i++) {
                    if (Integer.valueOf(textFieldID.getText()) == students.get(i).getId()) {
                        idFound = true;
                        break;
                    }
                }
                if (idFound == false) {
                    // The properties of Student Class some of them are not String so we must get the string value and change it into Double or Integer and it depends ..
                    resultLabel.setText("");
                    Student tempStudent = new Student(Integer.valueOf(textFieldID.getText()), textFieldName.getText(), textFieldMajor.getText(), Double.valueOf(textFieldGrade.getText()));
                    students.add(tempStudent);
                    // I can use students.get(students.size()-1).getId() or tempStudent.getId() >> students.size()-1 is the last added element
                    listViewStudents.getItems().add(String.format("%-5s %-10s %-7s %8.2f", tempStudent.getId(), tempStudent.getName(), tempStudent.getMajor(), tempStudent.getGrade()));
                } else {
                    resultLabel.setText("The ID you Entered is Existed Before!");
                }
            } else { // if one of textFields hasn't been filled yet!
                resultLabel.setText("You Should Enter a valid value\nin all Text Fields!");
            }
        } catch (NumberFormatException e) {
            resultLabel.setText("You Should Enter a valid value in \nID TextField or Grade TextField");
//            System.err.println("Input String cannot be parsed to Integer.");
        }

        // or without checking id if it's existed before or not :
//        try { // to check if the entered input is not a number or not >> if it is show an error message , if it's not complete the adding process
//            if ((!textFieldID.getText().isEmpty()) && (!textFieldName.getText().isEmpty()) && (!textFieldMajor.getText().isEmpty()) && (!textFieldGrade.getText().isEmpty())) {
//                // The properties of Student Class some of them are not String so we must get the string value and change it into Double or Integer and it depends ..
//                resultLabel.setText("");
//                Student tempStudent = new Student(Integer.valueOf(textFieldID.getText()), textFieldName.getText(), textFieldMajor.getText(), Double.valueOf(textFieldGrade.getText()));
//                students.add(tempStudent);
//                // I can use students.get(students.size()-1).getId() or tempStudent.getId() >> students.size()-1 is the last added element
//                listViewStudents.getItems().add(String.format("%-5s %-10s %-7s %8.2f", tempStudent.getId(), tempStudent.getName(), tempStudent.getMajor(), tempStudent.getGrade()));
//            } else { // if one of textFields hasn't been filled yet!
//                resultLabel.setText("You Should Enter a valid value\nin all Text Fields!");
//            }
//        } catch (NumberFormatException e) {
//            resultLabel.setText("You Should Enter a valid value in \nID TextField or Grade TextField");
////            System.err.println("Input String cannot be parsed to Integer.");
//        }
    }

    @FXML
    private void buttonClearHandle(ActionEvent event) {
        textFieldID.clear();
        textFieldName.clear();
        textFieldMajor.clear();
        textFieldGrade.clear();
        resultLabel.setText("");
    }

    @FXML
    private void buttonSortHandle(ActionEvent event) { // Question 4-a
        if (!students.isEmpty()) {
            listViewStudents.getItems().clear(); // at first clear the list view then adding the sorted student objects
//        students.sort((student1, student2) -> student1.getName().compareTo(student2.getName())); // Here's Just by lambdas
            students // Here's By Lambda Expression and Streams and showing the results
                    .stream()
                    .sorted((student1, student2) -> student1.getName().compareTo(student2.getName()))
                    .forEach(std -> listViewStudents.getItems().add(String.format("%-5s %-10s %-7s %8.2f", std.getId(), std.getName(), std.getMajor(), std.getGrade())));
        } else {
            resultLabel.setText("There are no Students \nto Sort them By Name!");
        }
    }

    @FXML
    private void buttonMapHandle(ActionEvent event) { // Question 4-b         
        listViewStudents.getItems().clear();
        students
                .stream()
                // the student's name is the key and it shouldn't be duplicated like (mohammed twice)
                .collect(Collectors.toMap(student -> student.getName(), student -> student.getGrade())) // to map student object to its name and grade
                .entrySet() // to set
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) // sort by value which is (grade) >> descending
                .forEachOrdered(std -> listViewStudents.getItems().add(String.format("%-10s %-10s ", std.getKey(), std.getValue())));
        
        // or by storing into Map<String,Double> >> String(name) , Double(the grade)
//        Map<String, Double> studentsMap = students
//                .stream()
//                .collect(Collectors.toMap(Student::getName, student -> student.getGrade()));
//
//        studentsMap
//                .entrySet()
//                .stream()
//                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//                .forEachOrdered(std
//                        -> listViewStudents.getItems().add(String.format("%-10s %-10s ", std.getKey(), std.getValue())));
    }

    @FXML
    private void buttonMapRangeHandle(ActionEvent event) { // Question 4-c 
        listViewStudents.getItems().clear();
        students
                .stream()
                .filter(std -> std.getGrade() >= 80 && std.getGrade() <= 90)
                .collect(Collectors.toMap(student -> student.getName(), student -> student.getGrade()))
                // .collect(Collectors.toMap(Student::getName, student -> student.getGrade())) // or by member reference
                .entrySet() // to set
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) // sort by value which is (grade) >> descending
                .forEachOrdered(std
                        -> listViewStudents.getItems().add(String.format("%-10s %-10s ", std.getKey(), std.getValue())));
    }

    @FXML
    private void buttonAvgHandle(ActionEvent event) { // Question 4-d
        try { // calculate the total average grade just if students array list has elements ..
            double average = students
                    .stream()
                    .mapToDouble(std -> std.getGrade())
                    .average().getAsDouble();
            resultLabel.setText("The Total Average\nOf All Students is : " + average);
        } catch (NoSuchElementException e) {
            resultLabel.setText("There is no any Student \nto calculate the average!");
        }
    }

    @FXML
    private void buttonGroupHandle(ActionEvent event) { // Question 4-e
        if (!students.isEmpty()) { // grouping the total average grade just if students array list has elements ..
            listViewStudents.getItems().clear(); // clear listView Before Grouping
            students
                    .stream()
                    .collect(Collectors.groupingBy(student -> student.getMajor()))
                    .forEach((major, stdMajor) -> {
                        listViewStudents.getItems().add(major + " :\n-------");
                        stdMajor.forEach(std -> listViewStudents.getItems().add(std.getId() + "  " + std.getName() + "  " + std.getMajor() + "  " + std.getGrade()));
                    }
                    );
        } else {
            resultLabel.setText("There is no any Student \nfor Grouping By Major!");
        }
    }
}
