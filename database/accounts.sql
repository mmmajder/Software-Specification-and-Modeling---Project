INSERT INTO accounts VALUES ('milovan99@gmail.com', 'Krakatau', 'Krka.44', 'LIBRARIAN', NULL);
COMMIT;

UPDATE accounts SET person = '1405999074365' WHERE person IS NULL;
COMMIT;
