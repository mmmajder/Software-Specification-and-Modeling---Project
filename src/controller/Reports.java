package controller;

import model.*;
import model.enums.MemberType;
import utils.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Reports {

    private final String path = "../../reports/";
    private final String extension = ".txt";
    private final String datePattern = "dd_mm_yyyy";
    private Library library;

    public Reports(Library library) {
        this.library = library;
    }


    public void generateDailyReport() throws IOException {
        List<String> lines = new ArrayList<>();
        generateMembershipsPart(lines);
        generateIssuesPart(lines);
        generateFile(lines, generateName());
    }

    private void generateMembershipsPart(List<String> lines){
        List<Payment> todaysPayments = getPayments(LocalDate.now());
        double totalEarnings = 0;

        for (MemberType memberType : MemberType.values()){
            List<Payment> typePayments = getTypePayments(todaysPayments, memberType);
            totalEarnings += generateNumOfMonthsPaymentLine(lines, typePayments, memberType, 6);
            totalEarnings += generateNumOfMonthsPaymentLine(lines, typePayments, memberType, 12);
        }

        lines.add("Total earnings are: " + totalEarnings + ".");
    }

    private List<Payment> getPayments(LocalDate date){
        return library.getPayments().stream()
                .filter(payment -> payment.getPaymentDate().isEqual(date))
                .collect(Collectors.toList());
    }

    private List<Payment> getTypePayments(List<Payment> payments, MemberType memberType){
        return payments.stream()
                .filter(payment -> payment.getMember().getType() == memberType)
                .collect(Collectors.toList());
    }

    private List<Payment> getNumOfMonthsPayments(List<Payment> payments, int numOfMonths){
        return payments.stream()
                .filter(payment -> payment.getNumOfMonths() == numOfMonths)
                .collect(Collectors.toList());
    }

    private double generateNumOfMonthsPaymentLine(List<String> lines, List<Payment> payments, MemberType memberType, int numOfMonths){
        List<Payment> numOfMonthsPayments = getNumOfMonthsPayments(payments, numOfMonths);
        double price = numOfMonths == 6 ? library.get6mothsPrice(memberType) : library.get12mothsPrice(memberType);
        int numOfPayments = payments.size();
        double earnings = price*numOfPayments;
        lines.add(generateTypePaymentLine(memberType, numOfPayments, numOfMonths, earnings));

        return earnings;
    }

    private String generateTypePaymentLine(MemberType memberType, int numOfPayments, int numOfMonths, double earnings){
        return numOfPayments + " memberships of type " + memberType + "for a period of " + numOfMonths + " were sold and made earnings of " + earnings + ".";
    }

    private void generateIssuesPart(List<String> lines){
        List<IssuedBook> issuedBooks = getTodaysIssues();
        Set<Librarian> librariansThatIssued = getLibrariansThatIssued(issuedBooks);

        for (Librarian librarian : librariansThatIssued){
            generateLibrarianLines(lines, issuedBooks, librarian);
        }
    }

    private void generateLibrarianLines(List<String> lines, List<IssuedBook> issuedBooks, Librarian librarian){
        List<IssuedBook> librarianIssues = getLibrariansIssues(issuedBooks, librarian);
        int numOfIssues = librarianIssues.size();
        lines.add(generateNumOfIssuesLine(librarian, numOfIssues));

        for (IssuedBook issuedBook : librarianIssues){
            lines.add(generateIssuedBookLine(issuedBook));
        }
    }

    private List<IssuedBook> getTodaysIssues(){
        LocalDate todayDate = LocalDate.now();
        return library.getCurrentlyIssued().stream()
                .filter(issuedBook -> issuedBook.getIssueDate().isEqual(todayDate))
                .collect(Collectors.toList());
    }

    private Set<Librarian> getLibrariansThatIssued(List<IssuedBook> issuedBooks){
        return issuedBooks.stream()
                .map(issuedBook -> issuedBook.getLibrarian())
                .collect(Collectors.toSet());
    }

    private List<IssuedBook> getLibrariansIssues(List<IssuedBook> issuedBooks, Librarian librarian){
        return issuedBooks.stream()
                .filter(issuedBook -> issuedBook.getLibrarian().equals(librarian))
                .collect(Collectors.toList());
    }

    private String generateNumOfIssuesLine(Librarian librarian, int numOfIssues){
        return getLibrarianNamingInfo(librarian) + " issued " + numOfIssues + " books.";
    }

    private String getLibrarianNamingInfo(Librarian librarian){
        return "Librarian " + librarian.getName() + " " + librarian.getSurname() + " (" + librarian.getAccount().getUsername() + ")";
    }

    private String generateIssuedBookLine(IssuedBook issuedBook){
        return getBookNamingInfo(issuedBook) + " to member " + getMemberNamingInfo(issuedBook.getMember());
    }

    private String getBookNamingInfo(IssuedBook issuedBook){
        return issuedBook.getBook().getEdition().getTitle();
    }

    private String getMemberNamingInfo(Member member){
        String info = member.getName() + " " + member.getSurname();

        if (member.getAccount() != null){
            info += " (" + member.getAccount().getUsername() + ")";
        }

        return info;
    }

    public void generateTopTenBooks(){

    }

    private String generateName(){
        return "Daily_" + getTodaysDateStr();
    }

    private String generateName(List<LocalDate> dates){
        String name = "Top10_" + getTodaysDateStr();
        name += "_from_" + getDateStr(dates.get(0));
        name += "_to_" + getDateStr(dates.get(1));

        return name;
    }

    private String getTodaysDateStr(){
        return getDateStr(LocalDate.now());
    }

    private String getDateStr(LocalDate date){
        return StringUtils.dateToString(date, datePattern);
    }

    private void generateFile(List<String> lines, String filename) throws IOException {
        Files.write(Paths.get(path + filename + extension), lines, StandardCharsets.UTF_8);
    }
}
