
import com.mysql.cj.jdbc.Blob;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author sarve
 */
public class Image_frame extends javax.swing.JFrame {
    
    public static String storedpartstring;
    private boolean[][] checkboxStates; 
    private static final int  GRID_SIZE = 3;
    private ImageIcon[][] imageParts;
    private ArrayList<ImageIcon> selectedParts;
    /**
     * Creates new form Image_frame
     */
    public Image_frame() {
        initComponents();
        selectedParts = new ArrayList<>();

    }
    public Image_frame(String user) {
        initComponents();
        jLabel1.setText(user);
        selectedParts = new ArrayList<>();

    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        imagelabel = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(125, 188, 189));

        jButton1.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(125, 188, 189));
        jButton1.setText("Display");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(125, 188, 189));
        jButton2.setText("Select ");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel2.setText("THIRD LEVEL AUTHENTICATION");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(174, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(68, 68, 68)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(20, 20, 20))
            .addComponent(imagelabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(imagelabel, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost/lockdb","root","sachin");
            PreparedStatement pst = con.prepareStatement("SELECT image,selectedpart FROM user_info WHERE username ='"+jLabel1.getText()+"'");

            ResultSet rs=pst.executeQuery();
            if(rs.next())
            {
                 Blob imageBlob = (Blob) rs.getBlob("image");
                 Image storedImage = new ImageIcon(imageBlob.getBytes(1, (int) imageBlob.length())).getImage();
                 displayImage(storedImage);
                 storedpartstring = rs.getString("selectedpart");
            }
        }
        catch(ClassNotFoundException|SQLException|HeadlessException ae)
        {
            System.out.println(ae);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        cutImage();
        promptUserForSelection();
        
    }//GEN-LAST:event_jButton2ActionPerformed
    private void cutImage() {
    ImageIcon originalIcon = (ImageIcon) imagelabel.getIcon();
    Image originalImage = originalIcon.getImage();

    // Convert Image to BufferedImage
    BufferedImage bufferedImage = new BufferedImage(originalImage.getWidth(null), originalImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
    Graphics g = bufferedImage.createGraphics();
    g.drawImage(originalImage, 0, 0, null);
    g.dispose();

    int originalWidth = bufferedImage.getWidth();
    int originalHeight = bufferedImage.getHeight();

    int partWidth = originalWidth / GRID_SIZE;
    int partHeight = originalHeight / GRID_SIZE;

    imageParts = new ImageIcon[GRID_SIZE][GRID_SIZE];

    for (int i = 0; i < GRID_SIZE; i++) {
        for (int j = 0; j < GRID_SIZE; j++) {
            int x = j * partWidth;
            int y = i * partHeight;

            // Use getSubimage on BufferedImage
            BufferedImage partImage = bufferedImage.getSubimage(x, y, partWidth, partHeight);
            
            // Convert BufferedImage to Image
            Image part = partImage.getScaledInstance(partWidth, partHeight, Image.SCALE_SMOOTH);
            imageParts[i][j] = new ImageIcon(part);
        }
    }
        checkboxStates = new boolean[GRID_SIZE][GRID_SIZE];

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                checkboxStates[i][j] = false; // Initialize all checkboxes to unchecked
            }
        }
}
    private void promptUserForSelection() {
    selectedParts.clear();

    JFrame selectionFrame = new JFrame("Select Image Parts");
    selectionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    selectionFrame.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));

    JCheckBox[][] checkBoxes = new JCheckBox[GRID_SIZE][GRID_SIZE];
    JLabel[][] imageLabels = new JLabel[GRID_SIZE][GRID_SIZE];

    for (int i = 0; i < GRID_SIZE; i++) {
        for (int j = 0; j < GRID_SIZE; j++) {
            final int row = i;
            final int col = j;
            imageLabels[i][j] = new JLabel(imageParts[i][j]);
            checkBoxes[i][j] = new JCheckBox();
            checkBoxes[i][j].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    checkBoxActionPerformed(row,col);
                }
            });

            JPanel panel = new JPanel();
            panel.add(checkBoxes[i][j]);
            panel.add(imageLabels[i][j]);
            selectionFrame.add(panel);
        }
    }

    JButton submitButton = new JButton("Submit");
    
    submitButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            String selectedPartsString = selectedParts.toString();
            String checkboxStateString = Arrays.deepToString(checkboxStates);
            System.out.println(checkboxStateString);
            System.out.println(storedpartstring);
            if(storedpartstring.equals(checkboxStateString)){
                JOptionPane.showMessageDialog(null,"Image selected Successfully");
                 selectionFrame.dispose();
                 Image_frame.this.setVisible(false);
                 new FinalFrame().setVisible(true);
               
            }
            else{
                int option = JOptionPane.showConfirmDialog(Image_frame.this,"Incorrect parts selected! Do you want to try again?", "Incorrect Selection", JOptionPane.YES_NO_OPTION);
                 if (option == JOptionPane.YES_OPTION) {
                     // Clear selected parts and prompt the user to select again
                    selectedParts.clear();
                    promptUserForSelection();
                    }
                }
           
            
        }
    });

    selectionFrame.add(submitButton);

    selectionFrame.setSize(400, 400);
    selectionFrame.setVisible(true);
}
    private void displayImage(Image image) {
        ImageIcon icon = new ImageIcon(image);
        Image scaledImage = icon.getImage().getScaledInstance(imagelabel.getWidth(), imagelabel.getHeight(), Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);
        imagelabel.setIcon(icon);
    }
    private void checkBoxActionPerformed(int row, int col) {
        checkboxStates[row][col] = !checkboxStates[row][col];
    }
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
            java.util.logging.Logger.getLogger(Image_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Image_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Image_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Image_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Image_frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel imagelabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
