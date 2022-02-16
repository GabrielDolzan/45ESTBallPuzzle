package resolucao;

import busca.BuscaLargura;
import busca.Nodo;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import resolucao.EstadoPuzzle.Coluna;

public class ViewPrincipal extends javax.swing.JFrame {

    private String[] colunas;

    public ViewPrincipal() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btCarregar = new javax.swing.JButton();
        btSobre = new javax.swing.JButton();
        btLargura = new javax.swing.JButton();
        btProfundidade = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taArquivo = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btCarregar.setText("Carregar");
        btCarregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCarregarActionPerformed(evt);
            }
        });

        btSobre.setText("Sobre");
        btSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSobreActionPerformed(evt);
            }
        });

        btLargura.setText("Largura");
        btLargura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLarguraActionPerformed(evt);
            }
        });

        btProfundidade.setText("Profundidade");

        taArquivo.setColumns(20);
        taArquivo.setRows(5);
        taArquivo.setEnabled(false);
        jScrollPane1.setViewportView(taArquivo);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(btCarregar)
                        .addGap(18, 18, 18)
                        .addComponent(btLargura)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btProfundidade)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addComponent(btSobre)))
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btCarregar)
                    .addComponent(btSobre)
                    .addComponent(btLargura)
                    .addComponent(btProfundidade))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSobreActionPerformed
        JOptionPane.showMessageDialog(null, "Alunos: "
            + "Gabriel Dolzan (gabriel.dolzan@hotmail.com) e "
            + "Igor Ochner ()", "Sobre", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btSobreActionPerformed

    private void btCarregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCarregarActionPerformed
        JFileChooser file = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        file.setFileSelectionMode(JFileChooser.FILES_ONLY);
        file.setAcceptAllFileFilterUsed(false);
        file.setDialogTitle("Selecione um arquivo .txt");
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Somente arquivos .txt", "txt");
        file.addChoosableFileFilter(restrict);

        if (file.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File arquivo = file.getSelectedFile();

            try {
                String dados = new String(Files.readAllBytes(arquivo.toPath()));
                colunas = dados.split(" ");
                taArquivo.setText(dados);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao abrir o arquivo.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btCarregarActionPerformed

    private void btLarguraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLarguraActionPerformed
        //Cria as colunas
        Coluna coluna = new Coluna();
        Coluna[] ColunasModelo = null;
        for (int i = 0; i < colunas.length; i++) {
            ColunasModelo[i] = new Coluna(colunas[i].split(","));
        }

        // Define o estado inicial
        EstadoPuzzle inicial = new EstadoPuzzle(ColunasModelo);

        // chama busca em largura
        BuscaLargura<EstadoPuzzle> bLarg = new BuscaLargura<EstadoPuzzle>();
        Nodo n = bLarg.busca(inicial);

        if (n == null) {
            System.out.println("sem solucao!");
        } else {
            System.out.println(n.getProfundidade());
            Nodo w = n;
            while (w != null) {
                EstadoPuzzle th = (EstadoPuzzle)w.getEstado();
                //System.out.println(th.pino1);
                w = w.getPai();
            }
            System.out.println("solucao:\n" + n.montaCaminho() + "\n\n");
        }
    }//GEN-LAST:event_btLarguraActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCarregar;
    private javax.swing.JButton btLargura;
    private javax.swing.JButton btProfundidade;
    private javax.swing.JButton btSobre;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea taArquivo;
    // End of variables declaration//GEN-END:variables
}
