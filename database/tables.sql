CREATE TABLE persons (
    JMBG CHAR(13) NOT NULL,
    Name VARCHAR(50) NOT NULL,
    Surname VARCHAR(50) NOT NULL,
    PhoneNumber VARCHAR(15) NOT NULL,
    DateOfBirth DATE NOT NULL,
    Account VARCHAR(75),
    CONSTRAINT persons_PK PRIMARY KEY (JMBG),
    CONSTRAINT persons_UQ UNIQUE (PhoneNumber, Account)
);

CREATE TABLE accounts (
    Email VARCHAR(75) NOT NULL, 
    Username VARCHAR(50) NOT NULL, 
    Password VARCHAR(50) NOT NULL, 
    Type VARCHAR(10) NOT NULL, 
    Person CHAR(13) NOT NULL,
    CONSTRAINT accounts_PK PRIMARY KEY (Email),
    CONSTRAINT accounts_UQ UNIQUE (Email, Username, Person)
);

ALTER TABLE accounts ADD CONSTRAINT accounts_FK FOREIGN KEY (Person) REFERENCES persons (JMBG);
ALTER TABLE persons ADD CONSTRAINT persons_FK FOREIGN KEY (Account) REFERENCES accounts (Email);

CREATE TABLE bookFormats (
    Idbf INTEGER NOT NULL,
    Height NUMBER(5, 2) NOT NULL,
    Width NUMBER(5, 2) NOT NULL,
    Thickness NUMBER(5, 2) NOT NULL,
    CONSTRAINT bookFormats_PK PRIMARY KEY (Idbf)
);

CREATE TABLE contributors (
    Idc INTEGER NOT NULL,
    Name VARCHAR(50) NOT NULL,
    Surname VARCHAR(50) NOT NULL,
    Biography VARCHAR(250),
    CONSTRAINT contributors_PK PRIMARY KEY (Idc)
);

CREATE TABLE editions (
    Ide VARCHAR(50) NOT NULL,
    Title VARCHAR(60) NOT NULL,
    Publisher VARCHAR(50) NOT NULL,
    NumOfPages INTEGER NOT NULL,
    Description VARCHAR(250) NOT NULL,
    PublishedDate DATE NOT NULL,
    Language VARCHAR(20) NOT NULL,
    Format INTEGER NOT NULL,
    Image VARCHAR(200),
    CONSTRAINT editions_PK PRIMARY KEY (Ide),
    CONSTRAINT editions_FK FOREIGN KEY (Format) REFERENCES bookFormats (Idbf)
);

CREATE TABLE genres (
    Idg INTEGER NOT NULL,
    Name VARCHAR(35) NOT NULL,
    CONSTRAINT genres_PK PRIMARY KEY (Idg)
);

CREATE TABLE editionGenres (
    Edition VARCHAR(50) NOT NULL,
    Genre INTEGER NOT NULL,
    CONSTRAINT editionGenres_PK PRIMARY KEY (Edition, Genre),
    CONSTRAINT editionGenres_FK1 FOREIGN KEY (Edition) REFERENCES editions (Ide),
    CONSTRAINT editionGenres_FK2 FOREIGN KEY (Genre) REFERENCES genres (Idg)
);

CREATE TABLE tags (
    Tag VARCHAR(50) NOT NULL,
    CONSTRAINT tags_PK PRIMARY KEY (Tag)
);

CREATE TABLE editionTags (
    Ide VARCHAR(50) NOT NULL,
    Tag VARCHAR(50) NOT NULL,
    CONSTRAINT editionTags_PK PRIMARY KEY (Tag, Ide),
    CONSTRAINT editionTags_FK1 FOREIGN KEY (Ide) REFERENCES editions (Ide),
    CONSTRAINT editionTags_FK2 FOREIGN KEY (Tag) REFERENCES tags (Tag)
);

CREATE TABLE editionContributors (
    Ide VARCHAR(50) NOT NULL,
    Idc INTEGER NOT NULL,
    Type VARCHAR(15) NOT NULL,
    CONSTRAINT editionContributors_PK PRIMARY KEY (Ide, Idc),
    CONSTRAINT editionContributors_FK1 FOREIGN KEY (Ide) REFERENCES editions (Ide),
    CONSTRAINT editionContributors_FK2 FOREIGN KEY (Idc) REFERENCES contributors (Idc)
);

CREATE TABLE books (
    Idb VARCHAR(50) NOT NULL,
    Edition VARCHAR(50) NOT NULL,
    State VARCHAR(20) NOT NULL,
    IsRestricted INTEGER NOT NULL,
    CONSTRAINT books_PK PRIMARY KEY (Idb),
    CONSTRAINT books_FK FOREIGN KEY (Edition) REFERENCES editions (Ide),
    CONSTRAINT books_CHK CHECK (IsRestricted IN (0, 1))
);

CREATE TABLE members (
    JMBG CHAR(13) NOT NULL,
    Type VARCHAR(12) NOT NULL,
    Debt NUMBER(5, 2),
    CONSTRAINT members_PK PRIMARY KEY (JMBG),
    CONSTRAINT members_FK FOREIGN KEY (JMBG) REFERENCES persons (JMBG)
);

CREATE TABLE librarians (
    JMBG CHAR(13) NOT NULL,
    CONSTRAINT librarians_PK PRIMARY KEY (JMBG),
    CONSTRAINT librarians_FK FOREIGN KEY (JMBG) REFERENCES persons (JMBG)
);

CREATE TABLE issuedBooks (
    Book VARCHAR(50) NOT NULL,
    IssueDate DATE NOT NULL,
    ReturnDate DATE,
    ProlongedIssue INTEGER NOT NULL,
    Librarian CHAR(13) NOT NULL,
    Member CHAR(13) NOT NULL,
    CONSTRAINT issuedBooks_PK PRIMARY KEY (Book, IssueDate),
    CONSTRAINT issuedBooks_FKB FOREIGN KEY (Book) REFERENCES books (Idb),
    CONSTRAINT issuedBooks_FKL FOREIGN KEY (Librarian) REFERENCES librarians (JMBG),
    CONSTRAINT issuedBooks_FKM FOREIGN KEY (Member) REFERENCES members (JMBG),
    CONSTRAINT issuedBooks_CHK CHECK (ProlongedIssue IN (0, 1))
);

CREATE TABLE librarianIssuedBooks (
    Librarian CHAR(13) NOT NULL,
    Book VARCHAR(50) NOT NULL,
    IssueDate DATE NOT NULL,
    CONSTRAINT librarianIssuedBooks_PK PRIMARY KEY (Librarian, Book, IssueDate),
    CONSTRAINT librarianIssuedBooks_FK1 FOREIGN KEY (Book, IssueDate) REFERENCES issuedBooks (Book, IssueDate),
    CONSTRAINT librarianIssuedBooks_FK2 FOREIGN KEY (Librarian) REFERENCES librarians (JMBG)
);

CREATE TABLE currentlyTakenBooks (
    Member CHAR(13) NOT NULL,
    Book VARCHAR(50) NOT NULL,
    IssueDate DATE NOT NULL,
    CONSTRAINT currentlyTakenBooks_PK PRIMARY KEY (Member, Book, IssueDate),
    CONSTRAINT currentlyTakenBook_FK1 FOREIGN KEY (Book, IssueDate) REFERENCES issuedBooks (Book, IssueDate),
    CONSTRAINT currentlyTakenBook_FK2 FOREIGN KEY (Member) REFERENCES members (JMBG)
);

CREATE TABLE returnedBooks (
    Member CHAR(13) NOT NULL,
    Book VARCHAR(50) NOT NULL,
    IssueDate DATE NOT NULL,
    CONSTRAINT returnedBooks_PK PRIMARY KEY (Member, Book, IssueDate),
    CONSTRAINT returnedBooks_FK1 FOREIGN KEY (Book, IssueDate) REFERENCES issuedBooks (Book, IssueDate),
    CONSTRAINT returnedBooks_FK2 FOREIGN KEY (Member) REFERENCES members (JMBG)
);

CREATE TABLE bookSections (
    Section VARCHAR(100) NOT NULL,
    CONSTRAINT bookSection_PK PRIMARY KEY (Section)
);

CREATE TABLE bookShelves (
    Shelf INTEGER NOT NULL,
    CONSTRAINT bookShelf_PK PRIMARY KEY (Shelf)
);

CREATE TABLE sectionShelf (
    Section VARCHAR(100) NOT NULL,
    Shelf INTEGER NOT NULL,
    CONSTRAINT sectionShelf_PK PRIMARY KEY (Section, Shelf),
    CONSTRAINT sectionShelf_FK1 FOREIGN KEY (Section) REFERENCES bookSections (Section),
    CONSTRAINT sectionShelf_FK2 FOREIGN KEY (Shelf) REFERENCES bookShelves (Shelf)
);

CREATE TABLE bookShelfRows (
    "ROW" INTEGER NOT NULL,
    CONSTRAINT bookShelfRow_PK PRIMARY KEY ("ROW")
);

CREATE TABLE shelfRow (
    Shelf INTEGER NOT NULL,
    "ROW" INTEGER NOT NULL,
    CONSTRAINT shelfRow_PK PRIMARY KEY (Shelf, "ROW"),
    CONSTRAINT shelfRow_FK1 FOREIGN KEY (Shelf) REFERENCES bookShelves (Shelf),
    CONSTRAINT shelfRow_FK2 FOREIGN KEY ("ROW") REFERENCES bookShelfRows ("ROW")
);

CREATE TABLE booksInRow (
    "ROW" INTEGER NOT NULL,
    Book VARCHAR(50) NOT NULL,
    CONSTRAINT booksInRow_PK PRIMARY KEY ("ROW", Book),
    CONSTRAINT booksInRow_FK1 FOREIGN KEY ("ROW") REFERENCES bookShelfRows ("ROW"),
    CONSTRAINT booksInRow_FK2 FOREIGN KEY (Book) REFERENCES books (Idb)
);

CREATE TABLE payments (
    Idp INTEGER NOT NULL,
    Member CHAR(13) NOT NULL,
    ToDate DATE NOT NULL,
    CONSTRAINT payments_PK PRIMARY KEY (Idp),
    CONSTRAINT payments_FK FOREIGN KEY (Member) REFERENCES members (JMBG)
);

CREATE TABLE memberPayments (
    Member CHAR(13) NOT NULL,
    Payment INTEGER NOT NULL,
    CONSTRAINT memberPayments_PK PRIMARY KEY (Member, Payment),
    CONSTRAINT memberPayments_FK1 FOREIGN KEY (Member) REFERENCES members (JMBG),
    CONSTRAINT memberPayments_FK2 FOREIGN KEY (Payment) REFERENCES payments (Idp)
);

CREATE TABLE priceCatalogs (
    CatalogId INTEGER NOT NULL,
    FromDate DATE NOT NULL,
    ToDate DATE,
    CONSTRAINT priceCatalogs_PK PRIMARY KEY (CatalogId)
);

CREATE TABLE prices (
    Type VARCHAR(12) NOT NULL,
    Price NUMBER(5, 2) NOT NULL,
    CONSTRAINT prices_PK PRIMARY KEY (Type)
);

CREATE TABLE catalogPrices (
    Catalog INTEGER NOT NULL,
    Type VARCHAR(12) NOT NULL,
    CONSTRAINT catalogPrices_PK PRIMARY KEY (Catalog, Type),
    CONSTRAINT catalogPrices_FK1 FOREIGN KEY (Catalog) REFERENCES priceCatalogs (CatalogId),
    CONSTRAINT catalogPrices_FK2 FOREIGN KEY (Type) REFERENCES prices (Type)
);

CREATE TABLE maxIssuedBooks (
    Type VARCHAR(12) NOT NULL,
    Limit INTEGER NOT NULL,
    CONSTRAINT maxIssuedBooks_PK PRIMARY KEY (Type)
);

CREATE TABLE maxIssueDays (
    Type VARCHAR(12) NOT NULL,
    Days INTEGER NOT NULL,
    CONSTRAINT maxIssueDays_PK PRIMARY KEY (Type)
);

ALTER TABLE contributors MODIFY Biography VARCHAR(2500);
ALTER TABLE editions MODIFY Description VARCHAR(2500);

ALTER TABLE accounts ADD Active INTEGER default 1 NOT NULL;
ALTER TABLE accounts ADD CONSTRAINT accounts_CHK CHECK ( Active IN (0, 1) );

CREATE TABLE pendingReservations (
    Idpr INTEGER NOT NULL,
    Member CHAR(13) NOT NULL,
    Edition VARCHAR(50),
    CONSTRAINT pendingReservations_PK PRIMARY KEY (Idpr),
    CONSTRAINT pendingReservations_FK1 FOREIGN KEY (Member) REFERENCES members (JMBG),
    CONSTRAINT pendingReservations_FK2 FOREIGN KEY (Edition) REFERENCES editions (Ide)
);

CREATE TABLE reservedBooks (
    Idrb INTEGER NOT NULL,
    Member CHAR(13) NOT NULL,
    Book VARCHAR(50) NOT NULL,
    CONSTRAINT reservedBooks_PK PRIMARY KEY (Idrb),
    CONSTRAINT reservedBooks_FK1 FOREIGN KEY (Member) REFERENCES members (JMBG),
    CONSTRAINT reservedBooks_FK2 FOREIGN KEY (Book) REFERENCES books (Idb)
);

ALTER TABLE members ADD pendingReservation INTEGER NOT NULL;
ALTER TABLE members ADD CONSTRAINT members_FK1 FOREIGN KEY (pendingReservation) REFERENCES pendingReservations (Idpr);
ALTER TABLE members ADD reservation INTEGER NOT NULL;
ALTER TABLE members ADD CONSTRAINT members_FK2 FOREIGN KEY (reservation) REFERENCES reservedBooks (Idrb);
ALTER TABLE members ADD membershipPaid INTEGER default 1 NOT NULL;
ALTER TABLE members ADD CONSTRAINT members_CHK1 CHECK ( membershipPaid IN (0, 1) );
ALTER TABLE members ADD Active INTEGER default 1 NOT NULL;
ALTER TABLE members ADD CONSTRAINT members_CHK2 CHECK ( Active IN (0, 1) );

DROP TABLE catalogPrices;

RENAME prices TO halfAYearPrices;

CREATE TABLE fullYearPrices (
    Type VARCHAR(12) NOT NULL,
    Price NUMBER(5, 2) NOT NULL,
    CONSTRAINT fullYearPrices_PK PRIMARY KEY (Type)
);

DROP TABLE halfAYearPrices;
DROP TABLE fullYearPrices;

CREATE TABLE catalogHalfAYearPrices (
    Catalog INTEGER NOT NULL,
    Type VARCHAR(12) NOT NULL,
    Price NUMBER(5, 2) NOT NULL,
    CONSTRAINT catalogHalfAYearPrices_PK PRIMARY KEY (Catalog, Type),
    CONSTRAINT catalogHalfAYearPrices_FK FOREIGN KEY (Catalog) REFERENCES priceCatalogs (CatalogId)
);

CREATE TABLE catalogFullYearPrices (
    Catalog INTEGER NOT NULL,
    Type VARCHAR(12) NOT NULL,
    Price NUMBER(5, 2) NOT NULL,
    CONSTRAINT catalogFullYearPrices_PK PRIMARY KEY (Catalog, Type),
    CONSTRAINT catalogFullYearPrices_FK FOREIGN KEY (Catalog) REFERENCES priceCatalogs (CatalogId)
);

DROP TABLE booksInRow;
DROP TABLE shelfRow;
DROP TABLE bookShelfRows;
DROP TABLE sectionShelf;
DROP TABLE bookShelves;
DROP TABLE bookSections;

CREATE TABLE bookSections (
    Section CHAR(3) NOT NULL,
    Shelf INTEGER NOT NULL,
    "Row" INTEGER NOT NULL,
    CONSTRAINT bookSections_PK PRIMARY KEY (Section, Shelf, "Row")
);

CREATE TABLE booksInRow (
    Section CHAR(3) NOT NULL,
    Shelf INTEGER NOT NULL,
    "Row" INTEGER NOT NULL,
    Book VARCHAR(50) NOT NULL,
    CONSTRAINT booksInRow_PK PRIMARY KEY (Section, Shelf, "Row", Book),
    CONSTRAINT booksInRow_FK1 FOREIGN KEY (Section, Shelf, "Row") REFERENCES bookSections (Section, Shelf, "Row"),
    CONSTRAINT booksInRow_FK2 FOREIGN KEY (Book) REFERENCES books (Idb)
);

DROP TABLE LIBRARIANISSUEDBOOKS;
DROP TABLE booksInRow;
DROP TABLE bookSections;

CREATE TABLE bookSections (
  Section CHAR(3) NOT NULL,
  CONSTRAINT bookSection_PK PRIMARY KEY (Section)
);

CREATE TABLE booksPosition (
    Section CHAR(3) NOT NULL,
    Shelf INTEGER NOT NULL,
    "Row" INTEGER NOT NULL,
    Book VARCHAR(50) NOT NULL,
    CONSTRAINT booksPosition_FK1 FOREIGN KEY (Book) REFERENCES books (Idb),
    CONSTRAINT booksPosition_FK2 FOREIGN KEY (Section) REFERENCES bookSections (Section)
);

ALTER TABLE members DROP CONSTRAINT members_FK1;
ALTER TABLE members DROP CONSTRAINT members_FK2;
ALTER TABLE members DROP COLUMN pendingReservation;
ALTER TABLE members DROP COLUMN reservation;
ALTER TABLE members ADD pendingReservation INTEGER;
ALTER TABLE members ADD CONSTRAINT members_FK1 FOREIGN KEY (pendingReservation) REFERENCES pendingReservations (Idpr);
ALTER TABLE members ADD reservation INTEGER;
ALTER TABLE members ADD CONSTRAINT members_FK2 FOREIGN KEY (reservation) REFERENCES reservedBooks (Idrb);

CREATE TABLE notifications (
    Idn VARCHAR(50) NOT NULL,
    Message VARCHAR(250) NOT NULL,
    NotiDate DATE NOT NULL,
    Member CHAR(13) NOT NULL,
    CONSTRAINT notifications_PK PRIMARY KEY (Idn),
    CONSTRAINT notifications_FK FOREIGN KEY (Member) REFERENCES members (JMBG)
);

RENAME reservedBooks TO reservations;
ALTER TABLE members RENAME COLUMN reservedBook TO reservation;
DROP TABLE RETURNEDBOOKS;

ALTER TABLE issuedBooks ADD state VARCHAR(10) DEFAULT 'TAKEN';

ALTER TABLE notifications DROP CONSTRAINT notifications_FK;
ALTER TABLE notifications DROP COLUMN Member;
ALTER TABLE notifications ADD Account VARCHAR(75) NOT NULL;
ALTER TABLE notifications ADD CONSTRAINT notifications_FK FOREIGN KEY (Account) REFERENCES accounts (Email);

ALTER TABLE issuedBooks DROP COLUMN state;

ALTER TABLE payments ADD numOfMonths INTEGER NOT NULL;
ALTER TABLE payments ADD paymentDate DATE NOT NULL;
DROP TABLE memberPayments;
DROP TABLE currentlyTakenBooks;
RENAME editionContributors TO contributorRoles;
ALTER TABLE reservations RENAME COLUMN idrb TO idr;

COMMIT;
