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


            ContactManagerGUI gui = new ContactManagerGUI(backend);
            gui.setVisible(true);
        });
    }
}
