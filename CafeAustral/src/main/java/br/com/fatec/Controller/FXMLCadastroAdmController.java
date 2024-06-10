package br.com.fatec.Controller;

import br.com.fatec.DATABASE.DataBase;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 *
 * @author vitor
 */
public class FXMLCadastroAdmController implements Initializable {

    @FXML
    private JFXButton btn_backAdmForm;
    

    @FXML
    private JFXComboBox<String> cmb_genCadAdm;
    
    @FXML
    private JFXTextField txt_userCadAdm;
    
    @FXML
    private JFXTextField txt_telCadAdm;
    
    @FXML
    private JFXTextField txt_emailCadAdm;

    @FXML
    private JFXPasswordField txt_senhaCadAdm;
    
    @FXML
    private JFXButton btn_cadAdm;
    
    private int currentAdminId;
    
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet result;
    private ResultSet rs = null;
    
    
 
    public void menuAdmBack() throws IOException {
        btn_backAdmForm.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_admMenu.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Adiciona os valores pré-definidos ao ComboBox
        cmb_genCadAdm.getItems().addAll("Masculino", "Feminino", "Outro");
    }

    public void cadastrarAdministrador() {
        // Verifica se todos os campos estão preenchidos
        if (txt_emailCadAdm.getText().isEmpty() || cmb_genCadAdm.getItems().isEmpty() || txt_userCadAdm.getText().isEmpty()  || txt_telCadAdm.getText().isEmpty() || txt_senhaCadAdm.getText().isEmpty() ) {
            exibirAlertaErro("Preencha todos os campos!");
            return;
        }

        // Verifica se o email possui o formato de email válido
        if (!txt_emailCadAdm.getText().contains("@") || !txt_emailCadAdm.getText().contains(".")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Email deve conter um '@' e um '.'!");
            alert.showAndWait();
            return;
        }

        // Verifica se o email já está cadastrado
        if (emailExistente(txt_emailCadAdm.getText())) {
        exibirAlertaErro("Este email já está cadastrado!");
        return;
    }
        // Inserir novo administrador na tabela
        String sql = "INSERT INTO administradores (Nome, Sobrenome, Genero, Email, Senha) VALUES (?, ?, ?, ?, ?)";

        try {
            conn = DataBase.connectDb();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, txt_userCadAdm.getText());
            stmt.setString(2, txt_telCadAdm.getText());
            stmt.setString(3, cmb_genCadAdm.getValue());
            stmt.setString(4, txt_emailCadAdm.getText());
            stmt.setString(5, txt_senhaCadAdm.getText());
            stmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);

            Optional<ButtonType> result = alert.showAndWait();            
            
            exibirAlertaInformacao("Administrador cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            exibirAlertaErro("Erro ao cadastrar administrador!");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadAdminData(int adminId) {
        // Carregar os dados do administrador a partir do banco de dados
        String sql = "SELECT * FROM administradores WHERE ID = ?";

        try {
            conn = DataBase.connectDb();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, adminId);
            result = stmt.executeQuery();

            if (result.next()) {
                currentAdminId = adminId;  // Armazena o ID do administrador atual
                txt_userCadAdm.setText(result.getString("Nome"));
                txt_telCadAdm.setText(result.getString("Sobrenome"));
                cmb_genCadAdm.setValue(result.getString("Genero"));
                txt_emailCadAdm.setText(result.getString("Email"));
                txt_senhaCadAdm.setText(result.getString("Senha"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            exibirAlertaErro("Erro ao carregar dados do administrador!");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }    

private int getIdFromEmail(String email) {
    String sql = "SELECT ID FROM administradores WHERE Email = ?";

    try {
        conn = DataBase.connectDb();
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("ID");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    return -1; // Retorna -1 se o email não for encontrado
}
    
    public void excluirAdministrador() {
    // Verifica se o email está vazio
        if (txt_emailCadAdm.getText().isEmpty()) {
            exibirAlertaErro("Insira o email do administrador que deseja excluir!");
            return;
        }
            // Obtém o ID do administrador usando o email fornecido
        int adminId = getIdFromEmail(txt_emailCadAdm.getText());
        if (adminId == -1) {
            exibirAlertaErro("Email não encontrado!");
            return;
        }

        // Mostra uma caixa de diálogo de confirmação
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirmação");
        confirmDialog.setHeaderText(null);
        confirmDialog.setContentText("Você tem certeza que deseja deletar o email: " + txt_emailCadAdm.getText() + " e seu ID: " + adminId + "?");

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
             // Excluir administrador da tabela
             String sql = "DELETE FROM administradores WHERE ID = ?";

             try {
                 conn = DataBase.connectDb();
                 stmt = conn.prepareStatement(sql);
                 stmt.setInt(1, adminId);
                 stmt.executeUpdate();

                 exibirAlertaInformacao("Administrador excluído com sucesso!");
             } catch (SQLException e) {
                 e.printStackTrace();
                 exibirAlertaErro("Erro ao excluir administrador!");
             } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            }
                 
        }           
    }

    public void editarAdministrador()   {
        // Verifica se todos os campos estão preenchidos
        if (txt_emailCadAdm.getText().isEmpty() || cmb_genCadAdm.getValue().isEmpty() || txt_userCadAdm.getText().isEmpty()  || txt_telCadAdm.getText().isEmpty() || txt_senhaCadAdm.getText().isEmpty() ) {
            exibirAlertaErro("Preencha todos os campos!");
            return;
        }

        // Verifica se o email possui o formato de email válido
        if (!txt_emailCadAdm.getText().contains("@") || !txt_emailCadAdm.getText().contains(".")) {
            exibirAlertaErro("Email deve conter um '@' e um '.'!");
            return;
        }
        
        // Obtém o ID do administrador usando o email fornecido
        int adminId = getIdFromEmail(txt_emailCadAdm.getText());
        if (adminId == -1) {
            exibirAlertaErro("Email não encontrado!");
            return;
        }            
        
            // Atualizar administrador na tabela
        String sql = "UPDATE administradores SET Nome = ?, Sobrenome = ?, Genero = ?, Email = ?, Senha = ? WHERE ID = ?" ;

        try {
            conn = DataBase.connectDb();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, txt_userCadAdm.getText());
            stmt.setString(2, txt_telCadAdm.getText());
            stmt.setString(3, cmb_genCadAdm.getValue());
            stmt.setString(4, txt_emailCadAdm.getText()); // Adiciona o email como critério de atualização
            // Utiliza o ID do administrador atual
            stmt.setString(5, txt_senhaCadAdm.getText());
            stmt.setInt(6, adminId);
            stmt.executeUpdate();
            
            

            exibirAlertaInformacao("Administrador atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            exibirAlertaErro("Erro ao atualizar administrador!");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        
    }
    private boolean emailExistente(String email) {
        String sql = "SELECT * FROM administradores WHERE Email = ?";

        try {
            conn = DataBase.connectDb();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void exibirAlertaErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void exibirAlertaInformacao(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
