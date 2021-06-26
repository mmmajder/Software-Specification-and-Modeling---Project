INSERT INTO persons VALUES ('1405999074365', 'Milovan', 'Milanovic', '+3816545698', '27-jun-1999', NULL);
COMMIT;

UPDATE persons SET account = 'milovan99@gmail.com' WHERE jmbg = '1405999074365';
COMMIT;