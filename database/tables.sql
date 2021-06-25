CREATE TABLE accounts (
    Email VARCHAR(75) NOT NULL, 
    Username VARCHAR(50) NOT NULL, 
    Password VARCHAR(50) NOT NULL, 
    Type VARCHAR(10) NOT NULL, 
    Person CHAR(13),
    CONSTRAINT accounts_PK PRIMARY KEY (Email)
);

CREATE TABLE persons (
    JMBG CHAR(13) NOT NULL,
    Name VARCHAR(50) NOT NULL,
    Surname VARCHAR(50) NOT NULL,
    PhoneNumber VARCHAR(15) NOT NULL,
    DateOfBirth DATE NOT NULL,
    Account VARCHAR(75),
    CONSTRAINT persons_PK PRIMARY KEY (JMBG)
);

ALTER TABLE accounts ADD CONSTRAINT accounts_FK FOREIGN KEY (Person) REFERENCES persons (JMBG);
ALTER TABLE persons ADD CONSTRAINT persons_FK FOREIGN KEY (Account) REFERENCES accounts (Email);
COMMIT;
