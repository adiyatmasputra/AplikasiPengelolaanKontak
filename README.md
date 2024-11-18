# AplikasiPengelolaanKontak
 Latihan3 (Adiyatma saputra - 2210010115)
# Pengelolaankontak

**Pengelolaankontak** adalah aplikasi berbasis Java Swing untuk mengelola data kontak. Aplikasi ini mendukung penambahan, penyaringan, dan penghapusan data kontak dengan integrasi basis data.

## Fitur Utama
- Menampilkan semua kontak dari basis data.
- Menyaring kontak berdasarkan kategori.
- Menambahkan kontak baru.
- Menghapus kontak yang dipilih.

## Prasyarat
Sebelum menjalankan aplikasi, pastikan Anda memiliki:
1. **JDK** (Java Development Kit) terinstal pada sistem Anda.
2. **Database Management System** seperti MySQL yang terhubung ke aplikasi.
3. Konfigurasi koneksi database di kode sudah sesuai dengan pengaturan Anda.

## Cara Menjalankan
1. Buka proyek ini menggunakan **NetBeans** atau editor Java lainnya.
2. Perbarui informasi koneksi database di bagian kode yang relevan, seperti:
   ```java
   Connection conn = DriverManager.getConnection("jdbc:mysql://<host>:<port>/<database_name>", "<username>", "<password>");

## koding yang dipakai


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
public class Pengelolaankontak extends javax.swing.JFrame {

    /**
     * Creates new form DatabaseConnection
     */
    public Pengelolaankontak() {
        initComponents();
        
        comboKategori.addItemListener(evt -> {
    if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
        String kategoriDipilih = comboKategori.getSelectedItem().toString();
        if (kategoriDipilih.equals("Semua")) {
            tampilkanKontak(); // Menampilkan semua kontak jika "Semua" dipilih
        } else {
            filterKontakBerdasarkanKategori(kategoriDipilih);
        }
    }
});
btnTambah.addActionListener(evt -> {
    String nama = txtNama.getText();
    String nomorTelepon = txtNomorTelepon.getText();
    String kategori = comboKategori.getSelectedItem().toString();
    if (nama.isEmpty() || nomorTelepon.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Nama dan nomor telepon harus diisi!");
    } else {
        tambahKontak(nama, nomorTelepon, kategori);
        tampilkanKontak(); // Refresh tampilan kontak di JTable
        JOptionPane.showMessageDialog(this, "Kontak berhasil ditambahkan.");
    }
});
btnEdit.addActionListener(evt -> {
    int selectedRow = tabelKontak.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih kontak yang ingin diedit terlebih dahulu.");
    } else {
        int id = Integer.parseInt(tabelKontak.getValueAt(selectedRow, 0).toString());
        String nama = txtNama.getText();
        String nomorTelepon = txtNomorTelepon.getText();
        String kategori = comboKategori.getSelectedItem().toString();
        editKontak(id, nama, nomorTelepon, kategori);
        tampilkanKontak(); // Refresh tampilan kontak di JTable
        JOptionPane.showMessageDialog(this, "Kontak berhasil diperbarui.");
    }
});
btnHapus.addActionListener(evt -> {
    int selectedRow = tabelKontak.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih kontak yang ingin dihapus terlebih dahulu.");
    } else {
        int id = Integer.parseInt(tabelKontak.getValueAt(selectedRow, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus kontak ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            hapusKontak(id);
            tampilkanKontak(); // Refresh tampilan kontak di JTable
            JOptionPane.showMessageDialog(this, "Kontak berhasil dihapus.");
        }
    }
});
btnCari.addActionListener(evt -> {
    String keyword = txtCari.getText();
    if (keyword.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Masukkan nama atau nomor telepon untuk mencari.");
    } else {
        cariKontak(keyword); // Fungsi untuk mencari kontak berdasarkan nama atau nomor telepon
    }
});


    }
    private void filterKontakBerdasarkanKategori(String kategori) {
    String sql = "SELECT * FROM contacts WHERE kategori = ?";

    try (Connection conn = DatabaseConnection.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, kategori);
        ResultSet rs = pstmt.executeQuery();

        DefaultTableModel model = (DefaultTableModel) tabelKontak.getModel();
        model.setRowCount(0); // Reset tabel sebelum menampilkan data baru
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("id"),
                rs.getString("nama"),
                rs.getString("nomor_telepon"),
                rs.getString("kategori")
            });
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

private void tampilkanKontak() {
    String sql = "SELECT * FROM contacts";
    try (Connection conn = DatabaseConnection.connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        DefaultTableModel model = (DefaultTableModel) tabelKontak.getModel();
        model.setRowCount(0); // Reset tabel
        while (rs.next()) {
            model.addRow(new Object[]{rs.getInt("id"), rs.getString("nama"), rs.getString("nomor_telepon"), rs.getString("kategori")});
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        txtNomorTelepon = new javax.swing.JTextField();
        btnTambah = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelKontak = new javax.swing.JTable();
        comboKategori = new javax.swing.JComboBox<>();
        btnCari = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("Nama");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setText("Nomor Telepon");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel3.setText("Kategori");

        btnTambah.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnEdit.setText("Edit");

        btnHapus.setBackground(new java.awt.Color(255, 51, 51));
        btnHapus.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnHapus.setForeground(new java.awt.Color(255, 255, 255));
        btnHapus.setText("Hapus");

        tabelKontak.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabelKontak);

        comboKategori.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        comboKategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnCari.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnCari.setText("Cari");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnTambah)
                        .addGap(50, 50, 50)
                        .addComponent(btnCari)
                        .addGap(64, 64, 64)
                        .addComponent(btnEdit)
                        .addGap(74, 74, 74)
                        .addComponent(btnHapus))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(172, 172, 172)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNama)
                                    .addComponent(txtNomorTelepon, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                                    .addComponent(txtCari))
                                .addGap(60, 60, 60)
                                .addComponent(comboKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(127, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(58, 58, 58)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTambah)
                            .addComponent(btnEdit)
                            .addComponent(btnHapus)
                            .addComponent(btnCari))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNomorTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(45, 45, 45))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(149, 149, 149))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTambahActionPerformed

    private void tambahKontak(String nama, String nomorTelepon, String kategori) {
         String sql = "INSERT INTO contacts(nama, nomor_telepon, kategori) VALUES(?,?,?)";

    try (Connection conn = DatabaseConnection.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, nama);
        
        if (!nomorTelepon.matches("\\d+")) {
    JOptionPane.showMessageDialog(this, "Nomor telepon hanya boleh berisi angka.");
    return;
}

        pstmt.setString(2, nomorTelepon);
        pstmt.setString(3, kategori);
        pstmt.executeUpdate();
        System.out.println("Kontak berhasil ditambahkan.");
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    }
    private void editKontak(int id, String nama, String nomorTelepon, String kategori) {
    String sql = "UPDATE contacts SET nama = ?, nomor_telepon = ?, kategori = ? WHERE id = ?";

    try (Connection conn = DatabaseConnection.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, nama);
        pstmt.setString(2, nomorTelepon);
        pstmt.setString(3, kategori);
        pstmt.setInt(4, id);
        pstmt.executeUpdate();
        System.out.println("Kontak berhasil diperbarui.");
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

    private void cariKontak(String keyword) {
    String sql = "SELECT * FROM contacts WHERE nama LIKE ? OR nomor_telepon LIKE ?";

    try (Connection conn = DatabaseConnection.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, "%" + keyword + "%");
        pstmt.setString(2, "%" + keyword + "%");
        ResultSet rs = pstmt.executeQuery();

        DefaultTableModel model = (DefaultTableModel) tabelKontak.getModel();
        model.setRowCount(0); // Reset tabel
        while (rs.next()) {
            model.addRow(new Object[]{rs.getInt("id"), rs.getString("nama"), rs.getString("nomor_telepon"), rs.getString("kategori")});
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

    private void hapusKontak(int id) {
    String sql = "DELETE FROM contacts WHERE id = ?";

    try (Connection conn = DatabaseConnection.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        System.out.println("Kontak berhasil dihapus.");
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
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
            java.util.logging.Logger.getLogger(Pengelolaankontak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pengelolaankontak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pengelolaankontak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pengelolaankontak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
DatabaseConnection.connect();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pengelolaankontak().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> comboKategori;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelKontak;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNomorTelepon;
    // End of variables declaration//GEN-END:variables
}

