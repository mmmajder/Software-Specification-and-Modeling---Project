package controller;

import model.*;
import model.enums.MemberType;
import utils.DateUtils;
import utils.StringUtils;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Reports {

    private final Library library;
    private final String extension;
    private final String path;
    private final String datePatternInFilename;
    private final String datePatternInFile;

    public Reports(Library library) {
        extension = ".txt";
        path = "reports/";
        datePatternInFilename = "dd_MM_yyyy";
        datePatternInFile = "dd.MM.yyyy.";
        this.library = library;
    }

    public void generateDailyReport() throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(generateTitle());
        generateMembershipsPart(lines);
        generateIssuesPart(lines);
        generateFile(lines, generateFilename());
    }

    private String generateTitle(){
        return "DAILY REPORT FOR THE DATE " + StringUtils.dateToString(LocalDate.now(), datePatternInFile) + "\n";
    }

    private void generateMembershipsPart(List<String> lines) {
        List<Payment> todaysPayments = getPayments(LocalDate.now());
        double totalEarnings = 0;

        for (MemberType memberType : MemberType.values()) {
            List<Payment> typePayments = getTypePayments(todaysPayments, memberType);
            totalEarnings += generateNumOfMonthsPaymentLine(lines, typePayments, memberType, 6);
            totalEarnings += generateNumOfMonthsPaymentLine(lines, typePayments, memberType, 12);
        }

        lines.add("Total earnings are: " + totalEarnings + ".");
    }

    private List<Payment> getPayments(LocalDate date) {
        return library.getPayments().stream()
                .filter(payment -> payment.getPaymentDate().isEqual(date))
                .collect(Collectors.toList());
    }

    private List<Payment> getTypePayments(List<Payment> payments, MemberType memberType) {
        return payments.stream()
                .filter(payment -> payment.getMember().getType() == memberType)
                .collect(Collectors.toList());
    }

    private double generateNumOfMonthsPaymentLine(List<String> lines, List<Payment> payments, MemberType memberType, int numOfMonths) {
        double price = numOfMonths == 6 ? library.getHalfAYearPrice(memberType) : library.getFullYearPrice(memberType);
        int numOfPayments = payments.size();
        double earnings = price * numOfPayments;
        lines.add(generateTypePaymentLine(memberType, numOfPayments, numOfMonths, earnings));

        return earnings;
    }

    private String generateTypePaymentLine(MemberType memberType, int numOfPayments, int numOfMonths, double earnings) {
        return numOfPayments + " memberships of type " + memberType + " for a period of " + numOfMonths + " were sold and made earnings of " + earnings + ".";
    }

    private void generateIssuesPart(List<String> lines) {
        List<IssuedBook> issuedBooks = getTodaysIssues();
        Set<Librarian> librariansThatIssued = getLibrariansThatIssued(issuedBooks);

        for (Librarian librarian : librariansThatIssued) {
            generateLibrarianLines(lines, issuedBooks, librarian);
        }
    }

    private void generateLibrarianLines(List<String> lines, List<IssuedBook> issuedBooks, Librarian librarian) {
        List<IssuedBook> librarianIssues = getLibrariansIssues(issuedBooks, librarian);
        int numOfIssues = librarianIssues.size();
        lines.add(generateNumOfIssuesLine(librarian, numOfIssues));

        for (IssuedBook issuedBook : librarianIssues) {
            lines.add(generateIssuedBookLine(issuedBook));
        }
    }

    private List<IssuedBook> getTodaysIssues() {
        LocalDate todayDate = LocalDate.now();
        return library.getCurrentlyIssued().stream()
                .filter(issuedBook -> issuedBook.getIssueDate().isEqual(todayDate))
                .collect(Collectors.toList());
    }

    private Set<Librarian> getLibrariansThatIssued(List<IssuedBook> issuedBooks) {
        return issuedBooks.stream()
                .map(IssuedBook::getLibrarian)
                .collect(Collectors.toSet());
    }

    private List<IssuedBook> getLibrariansIssues(List<IssuedBook> issuedBooks, Librarian librarian) {
        return issuedBooks.stream()
                .filter(issuedBook -> issuedBook.getLibrarian().equals(librarian))
                .collect(Collectors.toList());
    }

    private String generateNumOfIssuesLine(Librarian librarian, int numOfIssues) {
        return getLibrarianNamingInfo(librarian) + " issued " + numOfIssues + " books.";
    }

    private String getLibrarianNamingInfo(Librarian librarian) {
        return "Librarian " + librarian.getName() + " " + librarian.getSurname() + " (" + librarian.getAccount().getUsername() + ")";
    }

    private String generateIssuedBookLine(IssuedBook issuedBook) {
        return getBookNamingInfo(issuedBook) + " to member " + getMemberNamingInfo(issuedBook.getMember());
    }

    private String getBookNamingInfo(IssuedBook issuedBook) {
        return issuedBook.getBook().getEdition().getTitle();
    }

    private String getMemberNamingInfo(Member member) {
        String info = member.getName() + " " + member.getSurname();

        if (member.getAccount() != null) {
            info += " (" + member.getAccount().getUsername() + ")";
        }

        return info;
    }

    public void generateTopTenEditionsReport(LocalDate fromDate, LocalDate toDate) throws IOException {
        generateEditionIssuesReport(fromDate, toDate, 10);
    }

    public void generateEditionIssuesReport(LocalDate fromDate, LocalDate toDate, Integer n) throws IOException {
        List<EditionIssues> editionsIssues = getEditionsIssues(fromDate, toDate);
        if (n == null || n > editionsIssues.size()) {
            n = editionsIssues.size();
        }
        List<String> lines = generateLines(editionsIssues, fromDate, n);
        String filename = generateFilename(fromDate, toDate, n, n == editionsIssues.size());
        generateFile(lines, filename);
    }

    private List<EditionIssues> getEditionsIssues(LocalDate fromDate, LocalDate toDate) {
        Map<String, Integer> mappedEditionsIssues = new HashMap<>();

        for (Book book : library.getBooks()) {
            addNumOfBookIssues(mappedEditionsIssues, book, fromDate, toDate);
        }

        List<EditionIssues> editionsIssues = transformIntoEditionIssues(mappedEditionsIssues);
        sort(editionsIssues);

        return editionsIssues;
    }

    private void addNumOfBookIssues(Map<String, Integer> mappedEditionsIssues, Book book, LocalDate fromDate, LocalDate toDate) {
        String editionId = book.getEdition().getEditionId();
        int numOfIssues = getIssues(book, fromDate, toDate).size();

        if (mappedEditionsIssues.containsKey(editionId)) {
            numOfIssues += mappedEditionsIssues.get(editionId);
        }

        mappedEditionsIssues.put(editionId, numOfIssues);
    }

    private List<IssuedBook> getIssues(Book book, LocalDate fromDate, LocalDate toDate) {
        return book.getIssueHistory().stream()
                .filter(issuedBook -> DateUtils.isDateWithin(issuedBook.getIssueDate(), fromDate, toDate))
                .collect(Collectors.toList());
    }

    private List<EditionIssues> transformIntoEditionIssues(Map<String, Integer> mappedEditionsIssues) {
        List<EditionIssues> editionsIssues = new ArrayList<>();

        for (String editionId : mappedEditionsIssues.keySet()) {
            EditionIssues editionIssues = new EditionIssues(editionId, mappedEditionsIssues.get(editionId));
            editionsIssues.add(editionIssues);
        }

        return editionsIssues;
    }

    private void sort(List<EditionIssues> editionsIssues) {
        EditionIssuesComparator comparator = new EditionIssuesComparator();
        editionsIssues.sort(comparator);
    }

    private List<String> generateLines(List<EditionIssues> editionsIssues, LocalDate fromDate, Integer n) {
        List<String> lines = new ArrayList<>();
        lines.add(generateTitle(fromDate, n, n == editionsIssues.size()));

        for (int i = 0; i < n; i++) {
            lines.add(generateEditionIssuesLine(editionsIssues.get(i)));
        }

        lines.add(generateEditionsIssuesSumLine(editionsIssues));

        return lines;
    }

    private String generateTitle(LocalDate fromDate, Integer n, boolean allEditions) {
        String line = "NUMBER OF ";

        if (!allEditions) {
            line += "TOP " + n;
        }

        line += " EDITIONS' ISSUES FROM " + StringUtils.dateToString(fromDate, datePatternInFile)
                + " TO " + StringUtils.dateToString(fromDate, datePatternInFile) + "\n";

        return line;
    }

    private String generateEditionIssuesLine(EditionIssues editionIssues) {
        Edition edition = library.getEdition(editionIssues.getEditionId());

        return "Edition " + edition.getTitle() + " by " + edition.getAuthor()
                + " (id=" + edition.getEditionId() + ") was issued "
                + editionIssues.getNumOfIssues() + " times.";
    }

    private String generateEditionsIssuesSumLine(List<EditionIssues> editionsIssues) {
        int numOfEditions = editionsIssues.size();
        int numOfIssuesSum = 0;

        for (EditionIssues editionIssues : editionsIssues) {
            numOfIssuesSum += editionIssues.getNumOfIssues();
        }

        return "A total of " + numOfEditions + " editions were issued " + numOfIssuesSum + " times.";
    }

    private String generateFilename() {
        return "Daily_" + getTodaysDateStr();
    }

    private String generateFilename(LocalDate fromDate, LocalDate toDate, Integer n, boolean allEditions) {
        String name;

        if (allEditions) {
            name = "Issues_";
        } else {
            name = "Top" + n + "_Issues_";
        }
        name += getTodaysDateStr() + "_from_" + getDateStrForFilename(fromDate) + "_to_" + getDateStrForFilename(toDate);

        return name;
    }

    private String getTodaysDateStr() {
        return getDateStrForFilename(LocalDate.now());
    }

    private String getDateStrForFilename(LocalDate date) {
        return StringUtils.dateToString(date, datePatternInFilename);
    }

    public void generateFile(List<String> lines, String filename) throws IOException {
        FileWriter myWriter = new FileWriter(path + filename + extension);
        write(path + filename + extension, lines);
    }

    private void write(String fileName, List<String> lines) {
        PrintWriter pw = null;

        try {
            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"));

            for (String line : lines) {
                pw.println(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pw.close();
        }
    }
}