CREATE TABLE IF NOT EXISTS Airport(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	name VARCHAR(30),
	fullName VARCHAR(30),
	cityName VARCHAR(30)
);
CREATE TABLE IF NOT EXISTS Flight(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	flightNo VARCHAR(30),
	depart VARCHAR(30),
	arrival VARCHAR(30),
	departTime DATETIME,
	arrivalTime DATETIME,
	price INT,
	FOREIGN KEY (depart)
        REFERENCES Airport (name,)
	FOREIGN KEY (arrival)
        REFERENCES Airport (name)
);
CREATE TABLE IF NOT EXISTS Seats(
	seatNo VARCHAR(30),
	flight INTEGER,
	isBooked INT DEFAULT 0 NOT NULL,
	FOREIGN KEY (flight)
        REFERENCES Flight (id)
);
CREATE TABLE IF NOT EXISTS Person(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    firstName VARCHAR(30) NOT NULL,
    lastName VARCHAR(30) NOT NULL,
    kennitala VARCHAR(10) NOT NULL,
    email VARCHAR(30) NOT NULL,
    phoneNumber VARCHAR(30) NOT NULL,
    role VARCHAR(30) DEFAULT 'user' NOT NULL,
    insurance INT DEFAULT FALSE NOT NULL,
    luggage INT DEFAULT FALSE NOT NULL,
    healthIssues VARCHAR(300) DEFAULT '' NOT NULL,
    wantsFood INT DEFAULT FALSE NOT NULL,
    extraLuggage VARCHAR(300) DEFAULT '' NOT NULL,
    allergies VARCHAR(300) DEFAULT '' NOT NULL,
    wheelchair INT DEFAULT FALSE NOT NULL,
    password VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS Bookings(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    passenger INTEGER,
    customer INTEGER,
    flight INTEGER,
    seatNo VARCHAR(30),
    price INTEGER,
    billingAddress VARCHAR(30),
    paymentMade INT DEFAULT FALSE NOT NULL,
    FOREIGN KEY (flight)
    	REFERENCES Flight (id),
    FOREIGN KEY (passenger)
    	REFERENCES Person (id),
    FOREIGN KEY (customer)
        	REFERENCES Person (id),
    FOREIGN KEY (seatNo)
        	REFERENCES Seats (seatNo)
);
