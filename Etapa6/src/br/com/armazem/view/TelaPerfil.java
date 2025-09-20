package br.com.armazem.view;

import br.com.armazem.model.Funcionario;
import br.com.armazem.model.Usuario;
import br.com.armazem.service.FuncionarioService; // Importação da camada Service

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class TelaPerfil extends JFrame {
    private JTextField txtNome;
    private JTextField txtSobrenome;
    private JTextField txtCargo;
    private JTextField txtEmail;
    private JTextField txtTelefone;
    private Usuario usuario;
    private FuncionarioService funcionarioService; // Dependência para a camada Service

    public TelaPerfil(Usuario usuario) {
        this.usuario = usuario;
        this.funcionarioService = new FuncionarioService(); // Instância do Service

        setTitle("Perfil");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Perfil", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(34, 139, 34));
        lblTitulo.setForeground(Color.WHITE);
        panel.add(lblTitulo, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(5, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtNome = new JTextField();
        txtSobrenome = new JTextField();
        txtCargo = new JTextField();
        txtEmail = new JTextField();
        txtTelefone = new JTextField();

        infoPanel.add(new JLabel("Nome:"));
        infoPanel.add(txtNome);
        infoPanel.add(new JLabel("Sobrenome:"));
        infoPanel.add(txtSobrenome);
        infoPanel.add(new JLabel("Cargo:"));
        infoPanel.add(txtCargo);
        infoPanel.add(new JLabel("Email:"));
        infoPanel.add(txtEmail);
        infoPanel.add(new JLabel("Telefone:"));
        infoPanel.add(txtTelefone);

        panel.add(infoPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(); // Novo painel para os botões
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(new Color(34, 139, 34));
        btnSalvar.setForeground(Color.WHITE);
        JButton btnVoltar = new JButton("Voltar"); // Novo botão
        btnVoltar.setBackground(new Color(105, 105, 105));
        btnVoltar.setForeground(Color.WHITE);

        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnVoltar); // Adiciona o novo botão
        panel.add(buttonPanel, BorderLayout.SOUTH); // Adiciona o painel de botões

        carregarDadosFuncionario();

        btnSalvar.addActionListener(e -> {
            try {
                Funcionario funcionario = new Funcionario(
                    usuario.getFuncionarioId(),
                    txtNome.getText(),
                    txtSobrenome.getText(),
                    txtCargo.getText(),
                    txtEmail.getText(),
                    txtTelefone.getText()
                );
                // Chama o método da camada Service para atualizar o funcionário
                funcionarioService.atualizarFuncionario(funcionario);

                JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao atualizar dados do funcionário: " + ex.getMessage());
            }
        });

        // Ação do novo botão
        btnVoltar.addActionListener(e -> {
            new TelaDashboard(usuario).setVisible(true);
            dispose();
        });

        add(panel);
    }

    private void carregarDadosFuncionario() {
        try {
            // Chama o método da camada Service para buscar os dados do funcionário
            Funcionario funcionario = funcionarioService.buscarFuncionarioPorId(usuario.getFuncionarioId());
            if (funcionario != null) {
                txtNome.setText(funcionario.getNome());
                txtSobrenome.setText(funcionario.getSobrenome());
                txtCargo.setText(funcionario.getCargo());
                txtEmail.setText(funcionario.getEmail());
                txtTelefone.setText(funcionario.getTelefone());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados do funcionário: " + e.getMessage());
        }
    }
}