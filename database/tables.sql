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
    Ide INTEGER NOT NULL,
    Title VARCHAR(60) NOT NULL,
    Publisher VARCHAR(50) NOT NULL,
    NumOfPages INTEGER NOT NULL,
    Description VARCHAR(250) NOT NULL,
    Genre VARCHAR(15) NOT NULL,
    PublishedDate DATE NOT NULL,
    Language VARCHAR(20) NOT NULL,
    Format INTEGER NOT NULL,
    Image VARCHAR(200),
    CONSTRAINT editions_PK PRIMARY KEY (Ide),
    CONSTRAINT editions_FK FOREIGN KEY (Format) REFERENCES bookFormats (Idbf)
);

CREATE TABLE tags (
    Idt INTEGER NOT NULL,
    Tag VARCHAR(50) NOT NULL,
    CONSTRAINT tags_PK PRIMARY KEY (Idt),
    CONSTRAINT tags_UQ UNIQUE (Tag)
);

CREATE TABLE editionTags (
    Ide INTEGER NOT NULL,
    Idt INTEGER NOT NULL,
    CONSTRAINT editionTags_PK PRIMARY KEY (Idt, Ide)
);

CREATE TABLE editionContributors (
    Ide INTEGER NOT NULL,
    Idc INTEGER NOT NULL,
    Type VARCHAR(15) NOT NULL,
    CONSTRAINT editionContributors_PK PRIMARY KEY (Ide, Idc)
);

CREATE TABLE books (
    Idb INTEGER NOT NULL,
    Edition INTEGER NOT NULL,
    State VARCHAR(20) NOT NULL,
    IsRestricted INTEGER NOT NULL,
    CONSTRAINT books_PK PRIMARY KEY (Idb),
    CONSTRAINT books_FK FOREIGN KEY (Edition) REFERENCES editions (Ide),
    CONSTRAINT books_CHK CHECK (IsRestricted IN (0, 1))
);

COMMIT;
