public class ContactManagerTest {
    public static void main(String[] args) {
        // Look & Feel agar lebih modern
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        javax.swing.SwingUtilities.invokeLater(() -> {
            ContactManagerBackend backend = new ContactManagerBackend();

            // Data contoh
            try {
                backend.addContact("Evan", "0812345678", backend.getGroups().get(0), true);
                backend.addContact("Budi", "0898765432", backend.getGroups().get(1), false);
                backend.addContact("Sari", "0822334455", backend.getGroups().get(2), false);
                backend.addContact("irpan", "0822334455", backend.getGroups().get(0), false);
            } catch (ContactManagerBackend.InvalidNumberException e) {
                e.printStackTrace();
            }

            ContactManagerGUI gui = new ContactManagerGUI(backend);
            gui.setVisible(true);
        });
    }
}