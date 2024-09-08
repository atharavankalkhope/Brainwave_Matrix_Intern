import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Patient {
    private int patientId;
    private String name;
    private int age;
    private String gender;
    private String contactInfo;
    private String address;

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}

class Appointment {
    private int appointmentId;
    private int patientId;
    private int doctorId;
    private LocalDateTime dateTime;
    private String status;

    public int getAppointmentId() { return appointmentId; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

class MedicalRecord {
    private int recordId;
    private int patientId;
    private String diagnoses;
    private String prescriptions;
    private String doctorNotes;

    public int getRecordId() { return recordId; }
    public void setRecordId(int recordId) { this.recordId = recordId; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public String getDiagnoses() { return diagnoses; }
    public void setDiagnoses(String diagnoses) { this.diagnoses = diagnoses; }
    public String getPrescriptions() { return prescriptions; }
    public void setPrescriptions(String prescriptions) { this.prescriptions = prescriptions; }
    public String getDoctorNotes() { return doctorNotes; }
    public void setDoctorNotes(String doctorNotes) { this.doctorNotes = doctorNotes; }
}

class Invoice {
    private int invoiceId;
    private int patientId;
    private double amount;
    private String paymentStatus;
    private LocalDateTime paymentDate;

    public int getInvoiceId() { return invoiceId; }
    public void setInvoiceId(int invoiceId) { this.invoiceId = invoiceId; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
}

class Staff {
    private int staffId;
    private String name;
    private String role;
    private String contactInfo;

    public int getStaffId() { return staffId; }
    public void setStaffId(int staffId) { this.staffId = staffId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
}

class InventoryItem {
    private int itemId;
    private String itemName;
    private int quantity;
    private String expiryDate;
    private String supplierInfo;

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }
    public String getSupplierInfo() { return supplierInfo; }
    public void setSupplierInfo(String supplierInfo) { this.supplierInfo = supplierInfo; }
}

class PatientDAO {
    private Connection connection;

    public PatientDAO(Connection connection) {
        this.connection = connection;
    }

    public void addPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (name, age, gender, contact_info, address) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, patient.getName());
            stmt.setInt(2, patient.getAge());
            stmt.setString(3, patient.getGender());
            stmt.setString(4, patient.getContactInfo());
            stmt.setString(5, patient.getAddress());
            stmt.executeUpdate();
        }
    }

    public List<Patient> getAllPatients() throws SQLException {
        String sql = "SELECT * FROM patients";
        List<Patient> patients = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Patient patient = new Patient();
                patient.setPatientId(rs.getInt("patient_id"));
                patient.setName(rs.getString("name"));
                patient.setAge(rs.getInt("age"));
                patient.setGender(rs.getString("gender"));
                patient.setContactInfo(rs.getString("contact_info"));
                patient.setAddress(rs.getString("address"));
                patients.add(patient);
            }
        }

        return patients;
    }

   
}

class AppointmentDAO {
    private Connection connection;

    public AppointmentDAO(Connection connection) {
        this.connection = connection;
    }

    public void addAppointment(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, date_time, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, appointment.getPatientId());
            stmt.setInt(2, appointment.getDoctorId());
            stmt.setTimestamp(3, Timestamp.valueOf(appointment.getDateTime()));
            stmt.setString(4, appointment.getStatus());
            stmt.executeUpdate();
        }
    }

    public List<Appointment> getAllAppointments() throws SQLException {
        String sql = "SELECT * FROM appointments";
        List<Appointment> appointments = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getInt("appointment_id"));
                appointment.setPatientId(rs.getInt("patient_id"));
                appointment.setDoctorId(rs.getInt("doctor_id"));
                appointment.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
                appointment.setStatus(rs.getString("status"));
                appointments.add(appointment);
            }
        }

        return appointments;
    }

 
}

class MedicalRecordDAO {
    private Connection connection;

    public MedicalRecordDAO(Connection connection) {
        this.connection = connection;
    }

    public void addMedicalRecord(MedicalRecord medicalRecord) throws SQLException {
        String sql = "INSERT INTO medical_records (patient_id, diagnoses, prescriptions, doctor_notes) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, medicalRecord.getPatientId());
            stmt.setString(2, medicalRecord.getDiagnoses());
            stmt.setString(3, medicalRecord.getPrescriptions());
            stmt.setString(4, medicalRecord.getDoctorNotes());
            stmt.executeUpdate();
        }
    }

    public List<MedicalRecord> getAllMedicalRecords() throws SQLException {
        String sql = "SELECT * FROM medical_records";
        List<MedicalRecord> records = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MedicalRecord record = new MedicalRecord();
                record.setRecordId(rs.getInt("record_id"));
                record.setPatientId(rs.getInt("patient_id"));
                record.setDiagnoses(rs.getString("diagnoses"));
                record.setPrescriptions(rs.getString("prescriptions"));
                record.setDoctorNotes(rs.getString("doctor_notes"));
                records.add(record);
            }
        }

        return records;
    }

   
}

class InvoiceDAO {
    private Connection connection;

    public InvoiceDAO(Connection connection) {
        this.connection = connection;
    }

    public void addInvoice(Invoice invoice) throws SQLException {
        String sql = "INSERT INTO invoices (patient_id, amount, payment_status, payment_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, invoice.getPatientId());
            stmt.setDouble(2, invoice.getAmount());
            stmt.setString(3, invoice.getPaymentStatus());
            stmt.setTimestamp(4, Timestamp.valueOf(invoice.getPaymentDate()));
            stmt.executeUpdate();
        }
    }

    public List<Invoice> getAllInvoices() throws SQLException {
        String sql = "SELECT * FROM invoices";
        List<Invoice> invoices = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceId(rs.getInt("invoice_id"));
                invoice.setPatientId(rs.getInt("patient_id"));
                invoice.setAmount(rs.getDouble("amount"));
                invoice.setPaymentStatus(rs.getString("payment_status"));
                invoice.setPaymentDate(rs.getTimestamp("payment_date").toLocalDateTime());
                invoices.add(invoice);
            }
        }

        return invoices;
    }

  
}

class StaffDAO {
    private Connection connection;

    public StaffDAO(Connection connection) {
        this.connection = connection;
    }

    public void addStaff(Staff staff) throws SQLException {
        String sql = "INSERT INTO staff (name, role, contact_info) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, staff.getName());
            stmt.setString(2, staff.getRole());
            stmt.setString(3, staff.getContactInfo());
            stmt.executeUpdate();
        }
    }

    public List<Staff> getAllStaff() throws SQLException {
        String sql = "SELECT * FROM staff";
        List<Staff> staffList = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffId(rs.getInt("staff_id"));
                staff.setName(rs.getString("name"));
                staff.setRole(rs.getString("role"));
                staff.setContactInfo(rs.getString("contact_info"));
                staffList.add(staff);
            }
        }

        return staffList;
    }

  
}

class InventoryDAO {
    private Connection connection;

    public InventoryDAO(Connection connection) {
        this.connection = connection;
    }

    public void addInventoryItem(InventoryItem item) throws SQLException {
        String sql = "INSERT INTO inventory (item_name, quantity, expiry_date, supplier_info) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, item.getItemName());
            stmt.setInt(2, item.getQuantity());
            stmt.setString(3, item.getExpiryDate());
            stmt.setString(4, item.getSupplierInfo());
            stmt.executeUpdate();
        }
    }

    public List<InventoryItem> getAllInventoryItems() throws SQLException {
        String sql = "SELECT * FROM inventory";
        List<InventoryItem> items = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                InventoryItem item = new InventoryItem();
                item.setItemId(rs.getInt("item_id"));
                item.setItemName(rs.getString("item_name"));
                item.setQuantity(rs.getInt("quantity"));
                item.setExpiryDate(rs.getString("expiry_date"));
                item.setSupplierInfo(rs.getString("supplier_info"));
                items.add(item);
            }
        }

        return items;
    }

 
}

class PatientService {
    private PatientDAO patientDAO;

    public PatientService(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    public void addPatient(Patient patient) {
        try {
            patientDAO.addPatient(patient);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Patient> getAllPatients() {
        try {
            return patientDAO.getAllPatients();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}

class AppointmentService {
    private AppointmentDAO appointmentDAO;

    public AppointmentService(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    public void addAppointment(Appointment appointment) {
        try {
            appointmentDAO.addAppointment(appointment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Appointment> getAllAppointments() {
        try {
            return appointmentDAO.getAllAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

   
}

class MedicalRecordService {
    private MedicalRecordDAO medicalRecordDAO;

    public MedicalRecordService(MedicalRecordDAO medicalRecordDAO) {
        this.medicalRecordDAO = medicalRecordDAO;
    }

    public void addMedicalRecord(MedicalRecord medicalRecord) {
        try {
            medicalRecordDAO.addMedicalRecord(medicalRecord);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        try {
            return medicalRecordDAO.getAllMedicalRecords();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

 
}

class BillingService {
    private InvoiceDAO invoiceDAO;

    public BillingService(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    public void addInvoice(Invoice invoice) {
        try {
            invoiceDAO.addInvoice(invoice);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Invoice> getAllInvoices() {
        try {
            return invoiceDAO.getAllInvoices();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

  
}

class StaffService {
    private StaffDAO staffDAO;

    public StaffService(StaffDAO staffDAO) {
        this.staffDAO = staffDAO;
    }

    public void addStaff(Staff staff) {
        try {
            staffDAO.addStaff(staff);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Staff> getAllStaff() {
        try {
            return staffDAO.getAllStaff();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

  
}

class InventoryService {
    private InventoryDAO inventoryDAO;

    public InventoryService(InventoryDAO inventoryDAO) {
        this.inventoryDAO = inventoryDAO;
    }

    public void addInventoryItem(InventoryItem item) {
        try {
            inventoryDAO.addInventoryItem(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<InventoryItem> getAllInventoryItems() {
        try {
            return inventoryDAO.getAllInventoryItems();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

  
}

class HospitalManagementSystemUI {
    private Scanner scanner = new Scanner(System.in);
    private PatientService patientService;
    private AppointmentService appointmentService;
    private MedicalRecordService medicalRecordService;
    private BillingService billingService;
    private StaffService staffService;
    private InventoryService inventoryService;

    public HospitalManagementSystemUI(PatientService patientService, AppointmentService appointmentService,
                                      MedicalRecordService medicalRecordService, BillingService billingService,
                                      StaffService staffService, InventoryService inventoryService) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.medicalRecordService = medicalRecordService;
        this.billingService = billingService;
        this.staffService = staffService;
        this.inventoryService = inventoryService;
    }

    public void displayMenu() {
        while (true) {
            System.out.println("Hospital Management System");
            System.out.println("1. Manage Patients");
            System.out.println("2. Manage Appointments");
            System.out.println("3. Manage Medical Records");
            System.out.println("4. Manage Billing");
            System.out.println("5. Manage Staff");
            System.out.println("6. Manage Inventory");
            System.out.println("7. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    managePatients();
                    break;
                case 2:
                    manageAppointments();
                    break;
                case 3:
                    manageMedicalRecords();
                    break;
                case 4:
                    manageBilling();
                    break;
                case 5:
                    manageStaff();
                    break;
                case 6:
                    manageInventory();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void managePatients() {
        System.out.println("1. Add Patient");
        System.out.println("2. View All Patients");
        int choice = scanner.nextInt();
        scanner.nextLine();  

        if (choice == 1) {
            System.out.print("Enter patient name: ");
            String name = scanner.nextLine();
            System.out.print("Enter age: ");
            int age = scanner.nextInt();
            scanner.nextLine();  
            System.out.print("Enter gender: ");
            String gender = scanner.nextLine();
            System.out.print("Enter contact info: ");
            String contactInfo = scanner.nextLine();
            System.out.print("Enter address: ");
            String address = scanner.nextLine();

            Patient patient = new Patient();
            patient.setName(name);
            patient.setAge(age);
            patient.setGender(gender);
            patient.setContactInfo(contactInfo);
            patient.setAddress(address);

            patientService.addPatient(patient);
        } else if (choice == 2) {
            List<Patient> patients = patientService.getAllPatients();
            for (Patient patient : patients) {
                System.out.println("ID: " + patient.getPatientId() + ", Name: " + patient.getName() +
                        ", Age: " + patient.getAge() + ", Gender: " + patient.getGender() +
                        ", Contact Info: " + patient.getContactInfo() + ", Address: " + patient.getAddress());
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void manageAppointments() {
        System.out.println("1. Add Appointment");
        System.out.println("2. View All Appointments");
        int choice = scanner.nextInt();
        scanner.nextLine();  

        if (choice == 1) {
            System.out.print("Enter patient ID: ");
            int patientId = scanner.nextInt();
            System.out.print("Enter doctor ID: ");
            int doctorId = scanner.nextInt();
            scanner.nextLine(); 
            System.out.print("Enter appointment date and time (yyyy-MM-ddTHH:mm): ");
            String dateTime = scanner.nextLine();
            System.out.print("Enter status: ");
            String status = scanner.nextLine();

            Appointment appointment = new Appointment();
            appointment.setPatientId(patientId);
            appointment.setDoctorId(doctorId);
            appointment.setDateTime(LocalDateTime.parse(dateTime));
            appointment.setStatus(status);

            appointmentService.addAppointment(appointment);
        } else if (choice == 2) {
            List<Appointment> appointments = appointmentService.getAllAppointments();
            for (Appointment appointment : appointments) {
                System.out.println("ID: " + appointment.getAppointmentId() + ", Patient ID: " + appointment.getPatientId() +
                        ", Doctor ID: " + appointment.getDoctorId() + ", Date & Time: " + appointment.getDateTime() +
                        ", Status: " + appointment.getStatus());
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void manageMedicalRecords() {
        System.out.println("1. Add Medical Record");
        System.out.println("2. View All Medical Records");
        int choice = scanner.nextInt();
        scanner.nextLine(); 

        if (choice == 1) {
            System.out.print("Enter patient ID: ");
            int patientId = scanner.nextInt();
            scanner.nextLine(); 
            System.out.print("Enter diagnoses: ");
            String diagnoses = scanner.nextLine();
            System.out.print("Enter prescriptions: ");
            String prescriptions = scanner.nextLine();
            System.out.print("Enter doctor notes: ");
            String doctorNotes = scanner.nextLine();

            MedicalRecord medicalRecord = new MedicalRecord();
            medicalRecord.setPatientId(patientId);
            medicalRecord.setDiagnoses(diagnoses);
            medicalRecord.setPrescriptions(prescriptions);
            medicalRecord.setDoctorNotes(doctorNotes);

            medicalRecordService.addMedicalRecord(medicalRecord);
        } else if (choice == 2) {
            List<MedicalRecord> records = medicalRecordService.getAllMedicalRecords();
            for (MedicalRecord record : records) {
                System.out.println("ID: " + record.getRecordId() + ", Patient ID: " + record.getPatientId() +
                        ", Diagnoses: " + record.getDiagnoses() + ", Prescriptions: " + record.getPrescriptions() +
                        ", Doctor Notes: " + record.getDoctorNotes());
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void manageBilling() {
        System.out.println("1. Add Invoice");
        System.out.println("2. View All Invoices");
        int choice = scanner.nextInt();
        scanner.nextLine();  

        if (choice == 1) {
            System.out.print("Enter patient ID: ");
            int patientId = scanner.nextInt();
            System.out.print("Enter amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); 
            System.out.print("Enter payment status: ");
            String paymentStatus = scanner.nextLine();
            System.out.print("Enter payment date (yyyy-MM-ddTHH:mm): ");
            String paymentDate = scanner.nextLine();

            Invoice invoice = new Invoice();
            invoice.setPatientId(patientId);
            invoice.setAmount(amount);
            invoice.setPaymentStatus(paymentStatus);
            invoice.setPaymentDate(LocalDateTime.parse(paymentDate));

            billingService.addInvoice(invoice);
        } else if (choice == 2) {
            List<Invoice> invoices = billingService.getAllInvoices();
            for (Invoice invoice : invoices) {
                System.out.println("ID: " + invoice.getInvoiceId() + ", Patient ID: " + invoice.getPatientId() +
                        ", Amount: " + invoice.getAmount() + ", Payment Status: " + invoice.getPaymentStatus() +
                        ", Payment Date: " + invoice.getPaymentDate());
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void manageStaff() {
        System.out.println("1. Add Staff");
        System.out.println("2. View All Staff");
        int choice = scanner.nextInt();
        scanner.nextLine();  

        if (choice == 1) {
            System.out.print("Enter staff name: ");
            String name = scanner.nextLine();
            System.out.print("Enter role: ");
            String role = scanner.nextLine();
            System.out.print("Enter contact info: ");
            String contactInfo = scanner.nextLine();

            Staff staff = new Staff();
            staff.setName(name);
            staff.setRole(role);
            staff.setContactInfo(contactInfo);

            staffService.addStaff(staff);
        } else if (choice == 2) {
            List<Staff> staffList = staffService.getAllStaff();
            for (Staff staff : staffList) {
                System.out.println("ID: " + staff.getStaffId() + ", Name: " + staff.getName() +
                        ", Role: " + staff.getRole() + ", Contact Info: " + staff.getContactInfo());
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void manageInventory() {
        System.out.println("1. Add Inventory Item");
        System.out.println("2. View All Inventory Items");
        int choice = scanner.nextInt();
        scanner.nextLine(); 

        if (choice == 1) {
            System.out.print("Enter item name: ");
            String itemName = scanner.nextLine();
            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.print("Enter expiry date (yyyy-MM-dd): ");
            String expiryDate = scanner.nextLine();
            System.out.print("Enter supplier info: ");
            String supplierInfo = scanner.nextLine();

            InventoryItem item = new InventoryItem();
            item.setItemName(itemName);
            item.setQuantity(quantity);
            item.setExpiryDate(expiryDate);
            item.setSupplierInfo(supplierInfo);

            inventoryService.addInventoryItem(item);
        } else if (choice == 2) {
            List<InventoryItem> items = inventoryService.getAllInventoryItems();
            for (InventoryItem item : items) {
                System.out.println("ID: " + item.getItemId() + ", Name: " + item.getItemName() +
                        ", Quantity: " + item.getQuantity() + ", Expiry Date: " + item.getExpiryDate() +
                        ", Supplier Info: " + item.getSupplierInfo());
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }
}

public class HospitalManagementSystem {
    public static void main(String[] args) {
        // Set up the database connection
        String url = "jdbc:mysql://localhost:3306/hospital_db";
        String user = "root";
        String password = "@Alwaysatharv05";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            
            PatientDAO patientDAO = new PatientDAO(connection);
            AppointmentDAO appointmentDAO = new AppointmentDAO(connection);
            MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO(connection);
            InvoiceDAO invoiceDAO = new InvoiceDAO(connection);
            StaffDAO staffDAO = new StaffDAO(connection);
            InventoryDAO inventoryDAO = new InventoryDAO(connection);

          
            PatientService patientService = new PatientService(patientDAO);
            AppointmentService appointmentService = new AppointmentService(appointmentDAO);
            MedicalRecordService medicalRecordService = new MedicalRecordService(medicalRecordDAO);
            BillingService billingService = new BillingService(invoiceDAO);
            StaffService staffService = new StaffService(staffDAO);
            InventoryService inventoryService = new InventoryService(inventoryDAO);

        
            HospitalManagementSystemUI ui = new HospitalManagementSystemUI(patientService, appointmentService,
                    medicalRecordService, billingService, staffService, inventoryService);

            
            ui.displayMenu();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
