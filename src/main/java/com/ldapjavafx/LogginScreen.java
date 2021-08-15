package com.ldapjavafx;

import com.ldapjavafx.authentication.AuthenticationLDAP;
import com.ldapjavafx.authentication.AuthenticationLDAPService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.naming.NamingException;


public class LogginScreen    {

    @FXML
    public Button btnOK;

    @FXML
    public TextField username;

    @FXML
    public PasswordField password;

    @FXML
    public TextArea visor;


    private AuthenticationLDAPService authenticationLDAPService = new AuthenticationLDAP();



    @FXML
    public void handleButtonAction(ActionEvent event) throws NamingException {

        String name = username.getText();
        String pass = password.getText();
        String []result = authenticationLDAPService.authenticateLDAP(name,pass);
        if(!result[0].equals("ACESSO NEGADO")) {
            visor.setText(result[0]+"\n"+result[1]);
        }else{
            visor.setText("ACESSO NEGADO,você não é da banda!");
        }


    }
}
