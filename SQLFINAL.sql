


	-- Bảng Hospitals (bệnh viện)
	CREATE TABLE Hospitals (
		HospitalID INT DEFAULT 1 PRIMARY KEY ,
		HospitalName NVARCHAR(255),
		Address NVARCHAR(255)
	);

	-- Bảng Departments (khoa bệnh)
	CREATE TABLE Departments (
		DepartmentID INT PRIMARY KEY IDENTITY,
		DepartmentName NVARCHAR(255)
	);
	-- Sửa bảng Departments để đảm bảo DepartmentName là duy nhất
	ALTER TABLE Departments
	ADD CONSTRAINT UQ_DepartmentName UNIQUE (DepartmentName);

	-- Bảng Doctors (Bác sĩ)
	CREATE TABLE Doctors (
		DoctorID INT PRIMARY KEY IDENTITY,
		FullName NVARCHAR(255),
		Gender NVARCHAR(10) CHECK (Gender IN (N'Nam', N'Nữ')),
		DateOfBirth DATE,
		PhoneNumber NVARCHAR(20),
		Address NVARCHAR(255),
		Specialization NVARCHAR(255),  -- Sử dụng tên khoa trực tiếp
		HospitalID INT DEFAULT 1,  -- Giá trị cố định là 1
		CustomID NVARCHAR(10),  -- Mã định danh tùy chỉnh
		FOREIGN KEY (HospitalID) REFERENCES Hospitals(HospitalID),
		FOREIGN KEY (Specialization) REFERENCES Departments(DepartmentName) -- Liên kết với tên khoa
	);

	select Specialization from Doctors where CustomID = 'D001';
	 
	CREATE TABLE Patients (
		PatientID INT PRIMARY KEY IDENTITY,
		FullName NVARCHAR(255),
		Gender NVARCHAR(10) CHECK (Gender IN (N'Nam', N'Nữ')),
		DateOfBirth DATE,
		PhoneNumber NVARCHAR(20),
		Address NVARCHAR(255),
		DiseaseName NVARCHAR(255),
		DoctorID INT,
		Specialization NVARCHAR(255),  -- Sử dụng tên khoa trực tiếp
		CustomID NVARCHAR(10),  -- Mã định danh tùy chỉnh
		FOREIGN KEY (DoctorID) REFERENCES Doctors(DoctorID),
		FOREIGN KEY (Specialization) REFERENCES Departments(DepartmentName)  -- Liên kết với tên khoa
	);



	-- Bảng Managers (Quản lý bệnh viện)
	CREATE TABLE Managers (
		ManagerID INT PRIMARY KEY IDENTITY,
		FullName NVARCHAR(255),
		Gender NVARCHAR(10) CHECK (Gender IN (N'Nam', N'Nữ')),
		DateOfBirth DATE,
		PhoneNumber NVARCHAR(20),
		Address NVARCHAR(255),
		HospitalID INT DEFAULT 1,
		CustomID NVARCHAR(10), -- Mã định danh tùy chỉnh
		FOREIGN KEY (HospitalID) REFERENCES Hospitals(HospitalID)
	);

	CREATE TRIGGER trg_InsertDoctor
	ON Doctors
	AFTER INSERT
	AS
	BEGIN
		DECLARE @MaxDoctorID INT;
		DECLARE @NewCustomID NVARCHAR(10);

		-- Lấy giá trị lớn nhất của mã bác sĩ hiện tại
		SELECT @MaxDoctorID = MAX(CAST(SUBSTRING(CustomID, 2, LEN(CustomID)) AS INT))
		FROM Doctors
		WHERE CustomID LIKE 'D%';

		-- Tạo mã bác sĩ mới
		IF @MaxDoctorID IS NULL
		BEGIN
			-- Nếu không có bác sĩ nào, bắt đầu từ D001
			SET @MaxDoctorID = 0;
		END

		-- Tạo mã định danh cho bác sĩ mới
		;WITH NewDoctorIDs AS (
			SELECT DoctorID, 'D' + RIGHT('000' + CAST(@MaxDoctorID + ROW_NUMBER() OVER (ORDER BY DoctorID) AS NVARCHAR(3)), 3) AS NewCustomID
			FROM INSERTED
		)
		UPDATE D
		SET D.CustomID = N.NewCustomID, D.HospitalID = 1  -- Giới hạn HospitalID là 1
		FROM Doctors D
		JOIN NewDoctorIDs N ON D.DoctorID = N.DoctorID;
	END;

	CREATE TRIGGER trg_InsertManager
	ON Managers
	AFTER INSERT
	AS
	BEGIN
		DECLARE @MaxManagerID INT;
		DECLARE @NewCustomID NVARCHAR(10);

		-- Lấy giá trị lớn nhất của mã quản lý hiện tại
		SELECT @MaxManagerID = MAX(CAST(SUBSTRING(CustomID, 2, LEN(CustomID)) AS INT))
		FROM Managers
		WHERE CustomID LIKE 'M%';

		-- Nếu không có quản lý nào, bắt đầu từ M001
		IF @MaxManagerID IS NULL
		BEGIN
			SET @MaxManagerID = 0;
		END

		-- Tạo mã định danh cho quản lý mới
		;WITH NewManagerIDs AS (
			SELECT ManagerID, 'M' + RIGHT('000' + CAST(@MaxManagerID + ROW_NUMBER() OVER (ORDER BY ManagerID) AS NVARCHAR(3)), 3) AS NewCustomID
			FROM INSERTED
		)
		UPDATE M
		SET M.CustomID = N.NewCustomID
		FROM Managers M
		JOIN NewManagerIDs N ON M.ManagerID = N.ManagerID;
	END;

	CREATE TRIGGER trg_InsertPatient
	ON Patients
	AFTER INSERT
	AS
	BEGIN
		DECLARE @MaxPatientID INT;
		DECLARE @NewCustomID NVARCHAR(10);

		-- Lấy giá trị lớn nhất của mã bệnh nhân hiện tại
		SELECT @MaxPatientID = MAX(CAST(SUBSTRING(CustomID, 2, LEN(CustomID)) AS INT))
		FROM Patients
		WHERE CustomID LIKE 'P%';

		-- Nếu không có bệnh nhân nào, bắt đầu từ P001
		IF @MaxPatientID IS NULL
		BEGIN
			SET @MaxPatientID = 0;
		END

		-- Tạo mã định danh cho bệnh nhân mới
		;WITH NewPatientIDs AS (
			SELECT PatientID, 'P' + RIGHT('000' + CAST(@MaxPatientID + ROW_NUMBER() OVER (ORDER BY PatientID) AS NVARCHAR(3)), 3) AS NewCustomID
			FROM INSERTED
		)
		UPDATE P
		SET P.CustomID = N.NewCustomID
		FROM Patients P
		JOIN NewPatientIDs N ON P.PatientID = N.PatientID;
	END;


	ALTER TABLE Managers ADD Password NVARCHAR(255);
	ALTER TABLE Doctors ADD Password NVARCHAR(255);
	ALTER TABLE Patients ADD Password NVARCHAR(255);


-- Thêm mật khẩu mặc định cho bảng Managers
UPDATE Managers
SET Password = '123';

-- Thêm mật khẩu mặc định cho bảng Doctors
UPDATE Doctors
SET Password = '123';

-- Thêm mật khẩu mặc định cho bảng Patients
UPDATE Patients
SET Password = '123';


-- Thêm bệnh viện
INSERT INTO Hospitals (HospitalName, Address)
VALUES 
(N'Bệnh viện Đa khoa Đà Nẵng', N'Số 1, Đường Hải Phòng, Đà Nẵng');

-- Thêm các khoa
INSERT INTO Departments (DepartmentName)
VALUES 
(N'Khoa Nội'),
(N'Khoa Ngoại'),
(N'Khoa Mắt'),
(N'Khoa Da liễu'),
(N'Khoa Nhi'),
(N'Khoa Răng hàm mặt'),
(N'Khoa Tim mạch'),
(N'Khoa Cấp cứu'),
(N'Khoa Sản'),
(N'Khoa Y học cổ truyền');
-- Dữ liệu mẫu cho bảng Managers
-- Dữ liệu mẫu cho bảng Managers
INSERT INTO Managers (FullName, Gender, DateOfBirth, PhoneNumber, Address, HospitalID)
VALUES 
(N'Trần Minh Tuấn', N'Nam', '1975-05-15', N'0901234567', N'Số 1, Đường Hải Phòng, Đà Nẵng', 1),
(N'Nguyễn Thị Hạnh', N'Nữ', '1980-11-10', N'0912345678', N'Số 2, Đường Hải Phòng, Đà Nẵng', 1),
(N'Phan Minh Sơn', N'Nam', '1983-03-22', N'0923456789', N'Số 3, Đường Hải Phòng, Đà Nẵng', 1),
(N'Lê Thanh Hương', N'Nữ', '1978-02-11', N'0934567890', N'Số 4, Đường Hải Phòng, Đà Nẵng', 1),
(N'Vũ Minh Tú', N'Nam', '1985-07-30', N'0945678901', N'Số 5, Đường Hải Phòng, Đà Nẵng', 1);


-- Thêm bác sĩ-- Dữ liệu mẫu cho bảng Doctors
INSERT INTO Doctors (FullName, Gender, DateOfBirth, PhoneNumber, Address, Specialization, HospitalID)
VALUES 
(N'Lê Minh Tuấn', N'Nam', '1985-06-15', N'0901234567', N'Số 2, Đường Hải Phòng, Đà Nẵng', N'Khoa Nội', 1),
(N'Nguyễn Thị Lan', N'Nữ', '1987-08-20', N'0912345678', N'Số 3, Đường Hải Phòng, Đà Nẵng', N'Khoa Ngoại', 1),
(N'Phan Tiến Dũng', N'Nam', '1980-12-25', N'0923456789', N'Số 4, Đường Hải Phòng, Đà Nẵng', N'Khoa Mắt', 1),
(N'Vũ Minh Hải', N'Nam', '1990-04-10', N'0934567890', N'Số 5, Đường Hải Phòng, Đà Nẵng', N'Khoa Da liễu', 1),
(N'Lê Thanh Hà', N'Nữ', '1988-01-30', N'0945678901', N'Số 6, Đường Hải Phòng, Đà Nẵng', N'Khoa Nhi', 1),
(N'Nguyễn Thị Mai', N'Nữ', '1992-05-17', N'0956789012', N'Số 7, Đường Hải Phòng, Đà Nẵng', N'Khoa Răng hàm mặt', 1),
(N'Phạm Thị Kim', N'Nữ', '1984-02-22', N'0967890123', N'Số 8, Đường Hải Phòng, Đà Nẵng', N'Khoa Tim mạch', 1),
(N'Hoàng Minh Đức', N'Nam', '1982-09-12', N'0978901234', N'Số 9, Đường Hải Phòng, Đà Nẵng', N'Khoa Cấp cứu', 1),
(N'Trần Thị Hồng', N'Nữ', '1995-11-05', N'0989012345', N'Số 10, Đường Hải Phòng, Đà Nẵng', N'Khoa Sản', 1),
(N'Nguyễn Thanh Vân', N'Nữ', '1991-07-23', N'0990123456', N'Số 11, Đường Hải Phòng, Đà Nẵng', N'Khoa Y học cổ truyền', 1);

-- Thêm bệnh nhân-- Dữ liệu mẫu cho bảng Patients
-- Thêm bệnh nhân
INSERT INTO Patients (FullName, Gender, DateOfBirth, PhoneNumber, Address, DiseaseName, DoctorID, Specialization)
VALUES 
(N'Nguyễn Thị Lan Anh', N'Nữ', '2000-01-01', N'0901234567', N'Số 12, Đường Hải Phòng, Đà Nẵng', N'Cảm cúm', 1, N'Khoa Nội'),
(N'Phan Minh Quân', N'Nam', '1995-02-20', N'0912345678', N'Số 13, Đường Hải Phòng, Đà Nẵng', N'Viêm phổi', 2, N'Khoa Ngoại'),
(N'Lê Thị Hồng Nhung', N'Nữ', '1998-03-25', N'0923456789', N'Số 14, Đường Hải Phòng, Đà Nẵng', N'Bệnh tim', 7, N'Khoa Tim mạch'),
(N'Nguyễn Thanh Hà', N'Nữ', '1992-04-10', N'0934567890', N'Số 15, Đường Hải Phòng, Đà Nẵng', N'Bệnh ngoài da', 4, N'Khoa Da liễu'),
(N'Vũ Minh Tú', N'Nam', '2001-05-15', N'0945678901', N'Số 16, Đường Hải Phòng, Đà Nẵng', N'Bệnh đường ruột', 5, N'Khoa Nhi'),
(N'Hoàng Anh Tuấn', N'Nam', '1997-06-12', N'0956789012', N'Số 17, Đường Hải Phòng, Đà Nẵng', N'Viêm nhiễm', 6, N'Khoa Răng hàm mặt'),
(N'Trần Thị Bích', N'Nữ', '1993-07-08', N'0967890123', N'Số 18, Đường Hải Phòng, Đà Nẵng', N'Rối loạn tiêu hóa', 8, N'Khoa Cấp cứu'),
(N'Phạm Minh Ngọc', N'Nam', '1994-08-25', N'0978901234', N'Số 19, Đường Hải Phòng, Đà Nẵng', N'Tăng huyết áp', 9, N'Khoa Sản'),
(N'Lê Thanh Trúc', N'Nữ', '1996-09-18', N'0989012345', N'Số 20, Đường Hải Phòng, Đà Nẵng', N'Bệnh tim', 7, N'Khoa Tim mạch'),
(N'Nguyễn Thị Hải Yến', N'Nữ', '1994-10-10', N'0990123456', N'Số 21, Đường Hải Phòng, Đà Nẵng', N'Loét dạ dày', 3, N'Khoa Mắt'),
(N'Hoàng Minh Tâm', N'Nam', '1998-11-12', N'0902345678', N'Số 22, Đường Hải Phòng, Đà Nẵng', N'Viêm nhiễm', 4, N'Khoa Da liễu'),
(N'Vũ Thanh Bình', N'Nam', '1995-12-22', N'0913456789', N'Số 23, Đường Hải Phòng, Đà Nẵng', N'Bệnh về thần kinh', 2, N'Khoa Ngoại'),
(N'Trần Minh Tâm', N'Nam', '1999-01-15', N'0924567890', N'Số 24, Đường Hải Phòng, Đà Nẵng', N'Viêm khớp', 5, N'Khoa Nhi'),
(N'Nguyễn Thị Lan', N'Nữ', '1997-02-08', N'0935678901', N'Số 25, Đường Hải Phòng, Đà Nẵng', N'Bệnh ngoài da', 4, N'Khoa Da liễu'),
(N'Phan Thanh Phúc', N'Nam', '1993-03-01', N'0946789012', N'Số 26, Đường Hải Phòng, Đà Nẵng', N'Thần kinh', 6, N'Khoa Răng hàm mặt'),
(N'Lê Minh Long', N'Nam', '1996-04-14', N'0957890123', N'Số 27, Đường Hải Phòng, Đà Nẵng', N'Viêm đường tiết niệu', 8, N'Khoa Cấp cứu'),
(N'Nguyễn Hồng Nhung', N'Nữ', '1995-05-25', N'0968901234', N'Số 28, Đường Hải Phòng, Đà Nẵng', N'Bệnh đường tiêu hóa', 9, N'Khoa Sản'),
(N'Phan Thị Lan', N'Nữ', '1994-06-10', N'0979012345', N'Số 29, Đường Hải Phòng, Đà Nẵng', N'Loét dạ dày', 3, N'Khoa Mắt'),
(N'Vũ Minh Tú', N'Nam', '1995-07-18', N'0980123456', N'Số 30, Đường Hải Phòng, Đà Nẵng', N'Rối loạn tâm lý', 7, N'Khoa Tim mạch');





