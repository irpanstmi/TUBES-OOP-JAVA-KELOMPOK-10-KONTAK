import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.tree.DefaultTreeCellRenderer;

public class ContactManagerGUI extends JFrame {

    private final ContactManagerBackend backend;

    private JList<ContactManagerBackend.Contact> listKontak;
    private DefaultListModel<ContactManagerBackend.Contact> listModel;
    private JButton btnPanggil;
private JButton btnHapus;
    private JTree treeGroup;
    private DefaultTreeModel treeModel;

    private JTextField txtCari;

    // Tambah kontak
    private JTextField txtNamaTambah;
    private JTextField txtNomorTambah;
    private JComboBox<ContactManagerBackend.Group> cbGroupTambah;
    private JCheckBox cbFavorite;

    // Edit kontak
    private JTextField txtNamaEdit;
    private JTextField txtNomorEdit;
    private JComboBox<ContactManagerBackend.Group> cbGroupEdit;
    private JCheckBox cbFavoriteEdit;
    private JButton btnSimpanEdit;

    private ContactManagerBackend.Contact selectedContact;

    public ContactManagerGUI(ContactManagerBackend backend) {
        this.backend = backend;
        initComponents();
        setTitle("Manajemen Kontak");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 560);
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        // ======= Warna Versi A – Abu-Abu Soft =======
        Color bgGray      = new Color(229, 229, 229);   // #E5E5E5
        Color panelGray   = new Color(242, 242, 242);   // #F2F2F2
        Color inputGray   = new Color(221, 221, 221);   // #DDDDDD
        Color borderCyan  = new Color(0, 188, 212);     // cyan versi A
        Color accentPink  = new Color(255, 64, 129);    // pink favorit
        Color textDark    = new Color(51, 51, 51);      // #333333

        setLayout(new BorderLayout());
        getContentPane().setBackground(bgGray);

        // ================== PANEL KIRI ==================
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setBackground(bgGray);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));

        // ==== LOGO EMOJI 👤 ====
        JLabel lblIcon = new JLabel("👤", SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 70));
        lblIcon.setPreferredSize(new Dimension(100, 100));

        JLabel lblDaftar = new JLabel("DAFTAR KONTAK", SwingConstants.CENTER);
        lblDaftar.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblDaftar.setForeground(borderCyan);

        JPanel headerLeft = new JPanel(new BorderLayout());
        headerLeft.setBackground(bgGray);
        headerLeft.add(lblIcon, BorderLayout.CENTER);
        headerLeft.add(lblDaftar, BorderLayout.SOUTH);

        // ===== LIST KONTAK =====
        listModel = new DefaultListModel<>();
        listKontak = new JList<>(listModel);
        listKontak.setBackground(panelGray);
        listKontak.setForeground(textDark);
        listKontak.setSelectionBackground(accentPink);
        listKontak.setSelectionForeground(Color.WHITE);
        listKontak.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 15)); // ★ terlihat

        JScrollPane scrollList = new JScrollPane(listKontak);
        scrollList.setBorder(BorderFactory.createLineBorder(borderCyan));

        // ===== TOMBOL PANGGIL =====
        btnPanggil = new JButton("\u260E  Panggil");
        btnPanggil.setBackground(accentPink);
        btnPanggil.setForeground(Color.WHITE);
        btnPanggil.setFocusPainted(false);
        btnPanggil.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));

        JPanel bottomLeft = new JPanel(new BorderLayout());
        bottomLeft.setBackground(bgGray);
        // ===== TOMBOL HAPUS =====
btnHapus = new JButton("Hapus");
btnHapus.setBackground(new Color(200, 0, 0));
btnHapus.setForeground(Color.WHITE);
btnHapus.setFocusPainted(false);
btnHapus.setFont(new Font("Segoe UI", Font.BOLD, 15));

// Panel tombol bawah (Panggil + Hapus)
JPanel panelButtons = new JPanel(new GridLayout(1, 2, 10, 0));
panelButtons.setBackground(bgGray);
panelButtons.add(btnPanggil);
panelButtons.add(btnHapus);

bottomLeft.add(panelButtons, BorderLayout.CENTER);
        bottomLeft.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        leftPanel.add(headerLeft, BorderLayout.NORTH);
        leftPanel.add(scrollList, BorderLayout.CENTER);
        leftPanel.add(bottomLeft, BorderLayout.SOUTH);

        // ================== PANEL KANAN ==================
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBackground(bgGray);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));

        // ---------- Panel Group ----------
        JPanel panelTree = new JPanel(new BorderLayout());
        panelTree.setBackground(panelGray);
        panelTree.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(borderCyan),
                "Group",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI Symbol", Font.BOLD, 14),
                borderCyan
        ));

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Group");
        for (ContactManagerBackend.Group g : backend.getGroups()) {
            root.add(new DefaultMutableTreeNode(g));
        }

        treeModel = new DefaultTreeModel(root);
        treeGroup = new JTree(treeModel);
        treeGroup.setBackground(panelGray);
        treeGroup.setForeground(textDark);
        treeGroup.setRowHeight(24);
        treeGroup.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
        // ===== Ganti ikon kertas menjadi emoji orang =====
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer() {
          @Override
        public Component getTreeCellRendererComponent(
            JTree tree, Object value, boolean sel, boolean expanded,
            boolean leaf, int row, boolean hasFocus) {

        JLabel label = (JLabel) super.getTreeCellRendererComponent(
                tree, value, sel, expanded, leaf, row, hasFocus);

        // Hilangkan ikon default (kertas/folder)
        label.setIcon(null);

        // Tambahkan emoji orang di depan teks
        label.setText("👤 " + label.getText());

        return label;
    }
};

treeGroup.setCellRenderer(renderer);

        JScrollPane treeScroll = new JScrollPane(treeGroup);
        treeScroll.setPreferredSize(new Dimension(240, 130));
        treeScroll.setBorder(BorderFactory.createLineBorder(borderCyan));
        panelTree.add(treeScroll, BorderLayout.CENTER);

        // ---------- Panel Form Tambah & Edit ----------
        JPanel panelFormRow = new JPanel(new GridLayout(1, 2, 15, 0));
        panelFormRow.setBackground(bgGray);

        // ====== Panel Tambah ======
        JPanel panelTambah = new JPanel(new GridBagLayout());
        panelTambah.setBackground(panelGray);
        panelTambah.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(borderCyan),
                "Tambah Kontak",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                borderCyan
        ));
        panelTambah.setPreferredSize(new Dimension(320, 240));

        GridBagConstraints gbcTambah = new GridBagConstraints();
        gbcTambah.insets = new Insets(6, 6, 6, 6);
        gbcTambah.fill = GridBagConstraints.HORIZONTAL;
        gbcTambah.weightx = 1.0;

        JLabel lblNamaTambah = new JLabel("Nama");
        lblNamaTambah.setForeground(textDark);
        lblNamaTambah.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JLabel lblNomorTambah = new JLabel("No. Telp");
        lblNomorTambah.setForeground(textDark);
        lblNomorTambah.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JLabel lblGroupTambah = new JLabel("Group");
        lblGroupTambah.setForeground(textDark);
        lblGroupTambah.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        txtNamaTambah = new JTextField();
        txtNamaTambah.setPreferredSize(new Dimension(200, 30));
        txtNamaTambah.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtNamaTambah.setBorder(BorderFactory.createLineBorder(borderCyan));
        txtNamaTambah.setBackground(inputGray);

        txtNomorTambah = new JTextField();
        txtNomorTambah.setPreferredSize(new Dimension(200, 30));
        txtNomorTambah.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtNomorTambah.setBorder(BorderFactory.createLineBorder(borderCyan));
        txtNomorTambah.setBackground(inputGray);

        cbGroupTambah = new JComboBox<>();
        for (ContactManagerBackend.Group g : backend.getGroups()) {
            cbGroupTambah.addItem(g);
        }
        cbGroupTambah.setPreferredSize(new Dimension(200, 30));
        cbGroupTambah.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        cbGroupTambah.setBackground(inputGray);

        cbFavorite = new JCheckBox("Favorit");
        cbFavorite.setBackground(panelGray);
        cbFavorite.setForeground(accentPink);
        cbFavorite.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JButton btnTambah = new JButton("Simpan");
        btnTambah.setBackground(accentPink);
        btnTambah.setForeground(Color.WHITE);
        btnTambah.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnTambah.setFocusPainted(false);

        gbcTambah.gridx = 0; gbcTambah.gridy = 0;
        panelTambah.add(lblNamaTambah, gbcTambah);
        gbcTambah.gridx = 1;
        panelTambah.add(txtNamaTambah, gbcTambah);

        gbcTambah.gridx = 0; gbcTambah.gridy = 1;
        panelTambah.add(lblNomorTambah, gbcTambah);
        gbcTambah.gridx = 1;
        panelTambah.add(txtNomorTambah, gbcTambah);

        gbcTambah.gridx = 0; gbcTambah.gridy = 2;
        panelTambah.add(lblGroupTambah, gbcTambah);
        gbcTambah.gridx = 1;
        panelTambah.add(cbGroupTambah, gbcTambah);

        gbcTambah.gridx = 1; gbcTambah.gridy = 3;
        panelTambah.add(cbFavorite, gbcTambah);

        gbcTambah.gridx = 1; gbcTambah.gridy = 4;
        panelTambah.add(btnTambah, gbcTambah);
        // ====== Panel Edit ======
        JPanel panelEdit = new JPanel(new GridBagLayout());
        panelEdit.setBackground(panelGray);
        panelEdit.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(borderCyan),
                "Edit Kontak",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                borderCyan
        ));
        panelEdit.setPreferredSize(new Dimension(320, 240));

        GridBagConstraints gbcEdit = new GridBagConstraints();
        gbcEdit.insets = new Insets(6, 6, 6, 6);
        gbcEdit.fill = GridBagConstraints.HORIZONTAL;
        gbcEdit.weightx = 1.0;

        JLabel lblNamaEdit = new JLabel("Nama");
        lblNamaEdit.setForeground(textDark);
        lblNamaEdit.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JLabel lblNomorEdit = new JLabel("No. Telp");
        lblNomorEdit.setForeground(textDark);
        lblNomorEdit.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JLabel lblGroupEdit = new JLabel("Group");
        lblGroupEdit.setForeground(textDark);
        lblGroupEdit.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        txtNamaEdit = new JTextField();
        txtNamaEdit.setPreferredSize(new Dimension(200, 30));
        txtNamaEdit.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtNamaEdit.setBorder(BorderFactory.createLineBorder(borderCyan));
        txtNamaEdit.setBackground(inputGray);

        txtNomorEdit = new JTextField();
        txtNomorEdit.setPreferredSize(new Dimension(200, 30));
        txtNomorEdit.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtNomorEdit.setBorder(BorderFactory.createLineBorder(borderCyan));
        txtNomorEdit.setBackground(inputGray);

        cbGroupEdit = new JComboBox<>();
        for (ContactManagerBackend.Group g : backend.getGroups()) {
            cbGroupEdit.addItem(g);
        }
        cbGroupEdit.setPreferredSize(new Dimension(200, 30));
        cbGroupEdit.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        cbGroupEdit.setBackground(inputGray);

        cbFavoriteEdit = new JCheckBox("Favorit");
        cbFavoriteEdit.setBackground(panelGray);
        cbFavoriteEdit.setForeground(accentPink);
        cbFavoriteEdit.setFont(new Font("Segoe UI", Font.BOLD, 15));

        btnSimpanEdit = new JButton("Simpan Perubahan");
        btnSimpanEdit.setBackground(borderCyan);
        btnSimpanEdit.setForeground(Color.WHITE);
        btnSimpanEdit.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnSimpanEdit.setFocusPainted(false);

        gbcEdit.gridx = 0; gbcEdit.gridy = 0;
        panelEdit.add(lblNamaEdit, gbcEdit);
        gbcEdit.gridx = 1;
        panelEdit.add(txtNamaEdit, gbcEdit);

        gbcEdit.gridx = 0; gbcEdit.gridy = 1;
        panelEdit.add(lblNomorEdit, gbcEdit);
        gbcEdit.gridx = 1;
        panelEdit.add(txtNomorEdit, gbcEdit);

        gbcEdit.gridx = 0; gbcEdit.gridy = 2;
        panelEdit.add(lblGroupEdit, gbcEdit);
        gbcEdit.gridx = 1;
        panelEdit.add(cbGroupEdit, gbcEdit);

        gbcEdit.gridx = 1; gbcEdit.gridy = 3;
        panelEdit.add(cbFavoriteEdit, gbcEdit);

        gbcEdit.gridx = 1; gbcEdit.gridy = 4;
        panelEdit.add(btnSimpanEdit, gbcEdit);

        panelFormRow.add(panelTambah);
        panelFormRow.add(panelEdit);

        // ---------- Panel Cari ----------
        JPanel panelCari = new JPanel(new BorderLayout(5, 5));
        panelCari.setBackground(panelGray);
        panelCari.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(borderCyan),
                "Cari Kontak",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                borderCyan
        ));

        txtCari = new JTextField();
        txtCari.setBackground(inputGray);
        txtCari.setForeground(textDark);
        txtCari.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtCari.setBorder(BorderFactory.createLineBorder(borderCyan));

        JButton btnCari = new JButton("Cari");
        btnCari.setBackground(borderCyan);
        btnCari.setForeground(Color.WHITE);
        btnCari.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnCari.setFocusPainted(false);

        panelCari.add(txtCari, BorderLayout.CENTER);
        panelCari.add(btnCari, BorderLayout.EAST);

        // Gabungkan ke kanan
        JPanel centerRight = new JPanel(new BorderLayout(10, 10));
        centerRight.setBackground(bgGray);
        centerRight.add(panelFormRow, BorderLayout.CENTER);
        centerRight.add(panelCari, BorderLayout.SOUTH);

        rightPanel.add(panelTree, BorderLayout.NORTH);
        rightPanel.add(centerRight, BorderLayout.CENTER);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        // ================= EVENT HANDLER =================
        initEventHandlers(btnCari, btnTambah);
        refreshList(backend.getAllContacts());
    }

    private void initEventHandlers(JButton btnCari, JButton btnTambah) {

        listKontak.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedContact = listKontak.getSelectedValue();
                if (selectedContact != null) {
                    txtNamaEdit.setText(selectedContact.getNama());
                    txtNomorEdit.setText(selectedContact.getNomor());
                    cbGroupEdit.setSelectedItem(selectedContact.getGroup());
                    cbFavoriteEdit.setSelected(
                            selectedContact instanceof ContactManagerBackend.FavoriteContact
                    );
                }
            }
        });

        btnPanggil.addActionListener(e -> {
            ContactManagerBackend.Contact c = listKontak.getSelectedValue();
            if (c == null) {
                JOptionPane.showMessageDialog(this,
                        "Pilih kontak terlebih dahulu.",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this,
                    "Memanggil " + c.getNama() + " (" + c.getNomor() + ")...",
                    "Simulasi Panggilan",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        btnCari.addActionListener(e -> {
            String keyword = txtCari.getText();
            refreshList(backend.search(keyword));
        });

        txtCari.addActionListener(e -> {
            String keyword = txtCari.getText();
            refreshList(backend.search(keyword));
        });

        treeGroup.addTreeSelectionListener(e -> {
            Object nodeObj = treeGroup.getLastSelectedPathComponent();
            if (nodeObj == null) return;

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) nodeObj;
            Object userObject = node.getUserObject();

            if (userObject instanceof ContactManagerBackend.Group) {
                refreshList(backend.filterByGroup((ContactManagerBackend.Group) userObject));
            } else {
                refreshList(backend.getAllContacts());
            }
        });

        btnTambah.addActionListener(e -> {
            String nama = txtNamaTambah.getText().trim();
            String nomor = txtNomorTambah.getText().trim();
            ContactManagerBackend.Group group =
                    (ContactManagerBackend.Group) cbGroupTambah.getSelectedItem();
            boolean favorite = cbFavorite.isSelected();

            if (nama.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                backend.addContact(nama, nomor, group, favorite);
                txtNamaTambah.setText("");
                txtNomorTambah.setText("");
                cbFavorite.setSelected(false);
                refreshList(backend.getAllContacts());
            } catch (ContactManagerBackend.InvalidNumberException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Nomor tidak valid", JOptionPane.ERROR_MESSAGE);
            }
        });
        // ===== EVENT HAPUS KONTAK =====
btnHapus.addActionListener(e -> {
    ContactManagerBackend.Contact c = listKontak.getSelectedValue();

    if (c == null) {
        JOptionPane.showMessageDialog(this,
                "Pilih kontak yang ingin dihapus.",
                "Info", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(
            this,
            "Yakin ingin menghapus kontak \"" + c.getNama() + "\"?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        backend.getAllContacts().remove(c);
        refreshList(backend.getAllContacts());
        selectedContact = null;

        // Kosongkan form edit
        txtNamaEdit.setText("");
        txtNomorEdit.setText("");
        cbFavoriteEdit.setSelected(false);
    }
});
        btnSimpanEdit.addActionListener(e -> {
            if (selectedContact == null) {
                JOptionPane.showMessageDialog(this,
                        "Pilih kontak yang akan diedit.",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String namaBaru = txtNamaEdit.getText().trim();
            String nomorBaru = txtNomorEdit.getText().trim();
            ContactManagerBackend.Group groupBaru =
                    (ContactManagerBackend.Group) cbGroupEdit.getSelectedItem();

            boolean favoritDipilih = cbFavoriteEdit.isSelected();

            if (namaBaru.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                ContactManagerBackend.Contact old = selectedContact;

                // Lepas favorit → ubah ke Contact biasa
                if (!favoritDipilih && selectedContact instanceof ContactManagerBackend.FavoriteContact) {
                    selectedContact = new ContactManagerBackend.Contact(
                            selectedContact.getNama(),
                            selectedContact.getNomor(),
                            selectedContact.getGroup()
                    );
                    backend.replaceContact(old, selectedContact);
                }

                // Tambah favorit → ubah ke FavoriteContact
                if (favoritDipilih && !(selectedContact instanceof ContactManagerBackend.FavoriteContact)) {
                    selectedContact = new ContactManagerBackend.FavoriteContact(
                            selectedContact.getNama(),
                            selectedContact.getNomor(),
                            selectedContact.getGroup()
                    );
                    backend.replaceContact(old, selectedContact);
                }

                backend.updateContact(selectedContact, namaBaru, nomorBaru, groupBaru);
                refreshList(backend.getAllContacts());

            } catch (ContactManagerBackend.InvalidNumberException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Nomor tidak valid", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void refreshList(List<ContactManagerBackend.Contact> data) {
        listModel.clear();
        for (ContactManagerBackend.Contact c : data) {
            listModel.addElement(c);
        }
    }
}

