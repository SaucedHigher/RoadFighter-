package alert;

import javax.swing.*;

public class Alert {
    public static void show(String message, String title, String alertType){
        JOptionPane.showMessageDialog(null, message, title, getAlertType(alertType));
    }

    private static int getAlertType(String alertType) {
        return switch (alertType) {
            case "ERROR_MESSAGE" -> JOptionPane.ERROR_MESSAGE;
            case "INFORMATION_MESSAGE" -> JOptionPane.INFORMATION_MESSAGE;
            case "WARNING_MESSAGE" -> JOptionPane.WARNING_MESSAGE;
            case "QUESTION_MESSAGE" -> JOptionPane.QUESTION_MESSAGE;
            default -> JOptionPane.PLAIN_MESSAGE;
        };
    }
}
