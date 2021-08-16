package com.ldapjavafx;

import com.ldapjavafx.authentication.AuthenticationLDAP;
import com.ldapjavafx.authentication.AuthenticationLDAPService;
import com.ldapjavafx.enums.ProcessorProperty;
import com.ldapjavafx.util.PropertiesUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.naming.NamingException;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;


public class LogginScreen implements Initializable {

    @FXML
    public Button btnOK;

    @FXML
    public TextField username;

    @FXML
    public PasswordField password;

    @FXML
    public TextArea visor;

    @FXML
    public ImageView imageView;


    private AuthenticationLDAPService authenticationLDAPService = new AuthenticationLDAP();





    @FXML
    public void handleButtonAction(ActionEvent event) throws NamingException, FileNotFoundException {

        String name = username.getText();
        String pass = password.getText();
        String []result = authenticationLDAPService.authenticateLDAP(name,pass);
        if(!result[0].equals("ACESSO NEGADO")) {
            visor.setText(result[0]+"\n"+result[1]);
            String path = username.getText()+".jpg";
            showImage(path);

        }else{
            visor.setText("ACESSO NEGADO!\nVocê não é da banda!");
            String path = "ACESSO_NEGADO.jpg";
            showImage(path);
        }


    }

    public void showImage(String name){
        File file = new File("src/main/resources/img/"+name);
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


}
