import java.util.ArrayList;
import java.util.List;

public class ContactManagerBackend {

    // ===== Exception nomor invalid =====
    public static class InvalidNumberException extends Exception {
        public InvalidNumberException(String msg) {
            super(msg);
        }
    }

    // ===== Abstraksi Group + inheritance =====
    public static abstract class Group {
        protected String namaGroup;

        public Group(String namaGroup) {
            this.namaGroup = namaGroup;
        }

        public String getNamaGroup() {
            return namaGroup;
        }

        @Override
        public String toString() {
            return namaGroup;
        }
    }

    public static class KeluargaGroup extends Group {
        public KeluargaGroup() {
            super("Keluarga");
        }
    }

    public static class TemanGroup extends Group {
        public TemanGroup() {
            super("Teman");
        }
    }

    public static class KerjaGroup extends Group {
        public KerjaGroup() {
            super("Kerja");
        }
    }

    // ===== Contact + FavoriteContact =====
    public static class Contact {
        private String nama;
        private String nomor;
        private Group group;

        public Contact(String nama, String nomor, Group group) {
            this.nama = nama;
            this.nomor = nomor;
            this.group = group;
        }

        public String getNama() { return nama; }
        public void setNama(String nama) { this.nama = nama; }

        public String getNomor() { return nomor; }
        public void setNomor(String nomor) { this.nomor = nomor; }

        public Group getGroup() { return group; }
        public void setGroup(Group group) { this.group = group; }

        @Override
        public String toString() {
            return nama + " (" + nomor + ")";
        }
    }

    public static class FavoriteContact extends Contact {
        public FavoriteContact(String nama, String nomor, Group group) {
            super(nama, nomor, group);
        }

        @Override
        public String toString() {
            return "★ " + super.toString();
        }
    }

    // ===== Penyimpanan =====
    private final List<Contact> contacts = new ArrayList<>();
    private final List<Group> groups = new ArrayList<>();

    public ContactManagerBackend() {
        groups.add(new KeluargaGroup());
        groups.add(new TemanGroup());
        groups.add(new KerjaGroup());
    }

    public List<Group> getGroups() { return groups; }
    public List<Contact> getAllContacts() { return contacts; }

    // ===== Tambah kontak =====
    public Contact addContact(String nama, String nomor, Group group, boolean favorite)
            throws InvalidNumberException {

        validatePhone(nomor);

        Contact c = favorite
                ? new FavoriteContact(nama, nomor, group)
                : new Contact(nama, nomor, group);

        contacts.add(c);
        return c;
    }

    // ===== Update kontak =====
    public void updateContact(Contact c, String namaBaru, String nomorBaru, Group groupBaru)
            throws InvalidNumberException {

        validatePhone(nomorBaru);

        c.setNama(namaBaru);
        c.setNomor(nomorBaru);
        c.setGroup(groupBaru);
    }

    // ===== Replace contact (fix favorit dilepas tidak berubah) =====
    public void replaceContact(Contact oldC, Contact newC) {
        int index = contacts.indexOf(oldC);
        if (index != -1) {
            contacts.set(index, newC);
        }
    }

    // ===== Cari kontak =====
    public List<Contact> search(String keyword) {
        List<Contact> result = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            result.addAll(contacts);
            return result;
        }
        String lower = keyword.toLowerCase();
        for (Contact c : contacts) {
            if (c.getNama().toLowerCase().contains(lower) ||
                c.getNomor().toLowerCase().contains(lower)) {
                result.add(c);
            }
        }
        return result;
    }

    // ===== Filter group =====
    public List<Contact> filterByGroup(Group g) {
        List<Contact> result = new ArrayList<>();
        for (Contact c : contacts) {
            if (c.getGroup().getNamaGroup().equals(g.getNamaGroup())) {
                result.add(c);
            }
        }
        return result;
    }

    // ===== Validasi nomor =====
    private void validatePhone(String phone) throws InvalidNumberException {
        if (!phone.matches("\\d+"))
            throw new InvalidNumberException("Nomor hanya boleh angka.");
        if (phone.length() < 8)
            throw new InvalidNumberException("Nomor minimal 8 digit.");
    }
}