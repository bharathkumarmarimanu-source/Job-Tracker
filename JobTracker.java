package bharath;

import java.util.*;

class JobApplication {
    private int id;
    private String company, role, status, dateApplied;

    public JobApplication(int id, String company, String role, String dateApplied) {
        this.id = id;
        this.company = company;
        this.role = role;
        this.dateApplied = dateApplied;
        this.status = "APPLIED";
    }

    public int getId() { return id; }
    public String getCompany() { return company; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String describe() {
        return "#" + id + " | " + company + " | " + role
                + " | " + status + " | Applied on: " + dateApplied;
    }
}

class ReferralApplication extends JobApplication {
    private String referredBy;

    public ReferralApplication(int id, String company, String role, String date, String referredBy) {
        super(id, company, role, date);
        this.referredBy = referredBy;
    }

    @Override
    public String describe() {
        return super.describe() + " | Type: Referral (by " + referredBy + ")";
    }
}

class OnlineApplication extends JobApplication {
    private String portal;

    public OnlineApplication(int id, String company, String role, String date, String portal) {
        super(id, company, role, date);
        this.portal = portal;
    }

    @Override
    public String describe() {
        return super.describe() + " | Type: Online (" + portal + ")";
    }
}

class TrackerService {
    private HashMap<Integer, JobApplication> apps = new HashMap<>();
    private int nextId = 1;
    private String[] validStatuses = {"APPLIED", "INTERVIEW", "OFFER", "REJECTED"};

    public int generateId() { return nextId++; }

    public void add(JobApplication app) {
        apps.put(app.getId(), app);
        System.out.println("Added successfully!");
    }

    public void showAll() {
        if (apps.isEmpty()) {
            System.out.println("No applications yet.");
            return;
        }
        for (JobApplication app : apps.values()) {
            System.out.println(app.describe());
        }
    }

    public boolean isValidStatus(String status) {
        for (String s : validStatuses) {
            if (s.equals(status)) return true;
        }
        return false;
    }

    public void updateStatus(int id, String newStatus) {
        JobApplication app = apps.get(id);
        if (app == null) {
            System.out.println("No application with ID " + id);
        } else if (!isValidStatus(newStatus)) {
            System.out.println("Invalid status. Use APPLIED, INTERVIEW, OFFER or REJECTED.");
        } else {
            app.setStatus(newStatus);
            System.out.println("Status updated!");
        }
    }

    public void delete(int id) {
        if (apps.remove(id) != null) System.out.println("Deleted.");
        else System.out.println("No application with ID " + id);
    }

    public void searchByCompany(String keyword) {
        boolean found = false;
        for (JobApplication app : apps.values()) {
            if (app.getCompany().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(app.describe());
                found = true;
            }
        }
        if (!found) System.out.println("No match found.");
    }

    public void showSummary() {
        HashMap<String, Integer> counts = new HashMap<>();
        HashSet<String> companies = new HashSet<>();

        for (JobApplication app : apps.values()) {
            counts.put(app.getStatus(), counts.getOrDefault(app.getStatus(), 0) + 1);
            companies.add(app.getCompany().toLowerCase());
        }

        System.out.println("Total applications: " + apps.size());
        System.out.println("Unique companies: " + companies.size());
        for (String status : validStatuses) {
            System.out.println(status + ": " + counts.getOrDefault(status, 0));
        }
    }
}

public class JobTracker {

    // Keeps asking until the user types a whole number (exception handling)
    static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Keeps asking until the user types something non-empty
    static String readText(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("This field cannot be empty.");
        }
    }

    // "software engineer" -> "Software Engineer" (rest of each word stays as typed)
    static String capitalize(String text) {
        String[] words = text.trim().split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String w : words) {
            if (w.isEmpty()) continue;
            if (result.length() > 0) result.append(" ");
            result.append(Character.toUpperCase(w.charAt(0))).append(w.substring(1));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TrackerService service = new TrackerService();
        String[] menu = {
            "1. Add application", "2. View all", "3. Update status",
            "4. Delete", "5. Search by company", "6. Summary", "7. Exit"
        };

        while (true) {
            System.out.println("\n===== JOB TRACKER =====");
            for (String item : menu) System.out.println(item);
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": {
                    String company = capitalize(readText(sc, "Company: "));
                    String role = capitalize(readText(sc, "Role: "));

                    String date = readText(sc, "Date applied (dd-mm-yyyy): ");
                    while (!date.matches("\\d{2}-\\d{2}-\\d{4}")) {
                        System.out.println("Use the format dd-mm-yyyy, for example 09-10-2026.");
                        date = readText(sc, "Date applied (dd-mm-yyyy): ");
                    }

                    String type = readText(sc, "Type (1=Online, 2=Referral): ");
                    while (!type.equals("1") && !type.equals("2")) {
                        System.out.println("Please enter 1 or 2.");
                        type = readText(sc, "Type (1=Online, 2=Referral): ");
                    }

                    if (type.equals("2")) {
                        String by = capitalize(readText(sc, "Referred by: "));
                        service.add(new ReferralApplication(service.generateId(), company, role, date, by));
                    } else {
                        String portal = capitalize(readText(sc, "Portal (LinkedIn/Naukri/Company site): "));
                        service.add(new OnlineApplication(service.generateId(), company, role, date, portal));
                    }
                    break;
                }
                case "2":
                    service.showAll();
                    break;
                case "3": {
                    int uid = readInt(sc, "Application ID: ");
                    String status = readText(sc, "New status (APPLIED/INTERVIEW/OFFER/REJECTED): ").toUpperCase();
                    service.updateStatus(uid, status);
                    break;
                }
                case "4":
                    service.delete(readInt(sc, "Application ID to delete: "));
                    break;
                case "5":
                    service.searchByCompany(readText(sc, "Company name to search: "));
                    break;
                case "6":
                    service.showSummary();
                    break;
                case "7":
                    System.out.println("Bye! All the best for your job hunt!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
}
