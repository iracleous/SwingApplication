package main.java;

import javax.swing.*;

public class Crypto {
    private String message;
    private String encryptedMessage;


    public void setMessage(String message){
        this.message = message;
    }

    public String getEncryptedMessage(){
        if (message == null){
            return null;
        }
        encryptedMessage="";
        for (int i = 0; i < message.length(); i++ ){
            char c = message.charAt(i);
            c = (char) ((int)c + 3);
           // System.out.println(c + "   "+ (int)c);
            encryptedMessage += c;
        }
        return encryptedMessage;
    }


    public String getDecryptedMessage(String cipher){
        if (cipher == null){
            return null;
        }
        encryptedMessage="";
        for (int i = 0; i < cipher.length(); i++ ){
            char c = cipher.charAt(i);
            c = (char) ((int)c - 3);
            // System.out.println(c + "   "+ (int)c);
            encryptedMessage += c;
        }
        return encryptedMessage;
    }



    public static void main(String[] args) {
        Crypto crypto = new Crypto();
        String message =   JOptionPane.showInputDialog("Give your message");
        crypto.setMessage(message);
        String ciphered = crypto.getEncryptedMessage();
        String deciphered = crypto.getDecryptedMessage(ciphered);


        JOptionPane.showMessageDialog(null,
                "cipher = "+ciphered+ " decipher = "+ deciphered);



    }
}
