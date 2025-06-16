CREATE TABLE AnimeStore (
  Id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  Name VARCHAR(255),
  Manager VARCHAR(255),
  Location VARCHAR(255),
  Theme VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS AnimeEmployee (
  EmployeeId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  StoreName VARCHAR(255),
  Name VARCHAR(255),
  ShiftDate DATE,
  ShiftStartTime TIME,
  ShiftEndTime TIME,
  Role VARCHAR(255),
  HourlyRate DOUBLE
);

