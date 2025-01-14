
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


INSERT INTO Hospitals (HospitalName, Address)
VALUES 
(N'Bệnh viện Đa khoa Đà Nẵng', N'Số 1, Đường Hải Phòng, Đà Nẵng');

-- Thêm các khoa
INSERT INTO Departments (DepartmentName)
VALUES 
   (N'Nội khoa'),
    (N'Ngoại khoa'),
    (N'Tim mạch'),
    (N'Da liễu'),
    (N'Nhi khoa');



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
(N'Lê Minh Tuấn', N'Nam', '1985-06-15', N'0901234567', N'Số 2, Đường Hải Phòng, Đà Nẵng', N'Nội khoa', 1),
(N'Nguyễn Thị Lan', N'Nữ', '1987-08-20', N'0912345678', N'Số 3, Đường Hải Phòng, Đà Nẵng', N'Ngoại khoa', 1),
(N'Phan Tiến Dũng', N'Nam', '1980-12-25', N'0923456789', N'Số 4, Đường Hải Phòng, Đà Nẵng', N'Tim mạch', 1),
(N'Vũ Minh Hải', N'Nam', '1990-04-10', N'0934567890', N'Số 5, Đường Hải Phòng, Đà Nẵng', N'Da liễu', 1),
(N'Lê Thanh Hà', N'Nữ', '1988-01-30', N'0945678901', N'Số 6, Đường Hải Phòng, Đà Nẵng', N'Nhi khoa', 1),
(N'Nguyễn Thị Mai', N'Nữ', '1992-05-17', N'0956789012', N'Số 7, Đường Hải Phòng, Đà Nẵng', N'Nội Khoa', 1),
(N'Phạm Thị Kim', N'Nữ', '1984-02-22', N'0967890123', N'Số 8, Đường Hải Phòng, Đà Nẵng', N'Nhi khoa', 1),
(N'Hoàng Minh Đức', N'Nam', '1982-09-12', N'0978901234', N'Số 9, Đường Hải Phòng, Đà Nẵng', N'Ngoại khoa', 1),
(N'Trần Thị Hồng', N'Nữ', '1995-11-05', N'0989012345', N'Số 10, Đường Hải Phòng, Đà Nẵng', N'Nhi khoa', 1),
(N'Nguyễn Thanh Vân', N'Nữ', '1991-07-23', N'0990123456', N'Số 11, Đường Hải Phòng, Đà Nẵng', N'Nội khoa', 1),
(N'Nguyễn Hữu Tô Hoàng', N'Nam', '1980-01-01', '0912000001', N'Hà Nội', N'Nội khoa', 1),
(N'Trần Thị Thu Hà', N'Nữ', '1985-02-15', '0912000002', N'Hải Phòng', N'Ngoại khoa', 1),
(N'Lê Văn Hùng', N'Nam', '1990-03-20', '0912000003', N'Đà Nẵng', N'Tim mạch',1),
(N'Phạm Thị Mai', N'Nữ', '1987-04-10', '0912000004', N'Hồ Chí Minh', N'Da liễu',1),
(N'Võ Văn An', N'Nam', '1983-05-05', '0912000005', N'Huế', N'Nhi khoa', 1),
(N'Nguyễn Văn Bình', N'Nam', '1978-07-22', '0912000006', N'Hà Nội', N'Nội khoa', 1),
    (N'Trần Thị Hồng', N'Nữ', '1982-08-30', '0912000007', N'Hải Phòng', N'Ngoại khoa', 1),
    (N'Lê Minh Sơn', N'Nam', '1991-09-15', '0912000008', N'Đà Nẵng', N'Tim mạch', 1),
    (N'Phạm Văn Quang', N'Nam', '1986-10-05', '0912000009', N'Huế', N'Da liễu', 1),
    (N'Hoàng Thị Lan', N'Nữ', '1993-11-12', '0912000010', N'Hồ Chí Minh', N'Nhi khoa', 1),
    (N'Nguyễn Văn Phong', N'Nam', '1975-04-18', '0912000011', N'Hà Nội', N'Nội khoa', 1),
    (N'Trần Minh Tú', N'Nam', '1984-05-24', '0912000012', N'Hải Phòng', N'Ngoại khoa', 1),
    (N'Lê Hồng Ngọc', N'Nữ', '1990-06-10', '0912000013', N'Đà Nẵng', N'Tim mạch', 1),
    (N'Phạm Minh Khoa', N'Nam', '1989-07-19', '0912000014', N'Huế', N'Da liễu', 1),
    (N'Hoàng Văn Tùng', N'Nam', '1981-08-03', '0912000015', N'Hồ Chí Minh', N'Nhi khoa', 1),
    (N'Nguyễn Thị Ánh', N'Nữ', '1977-09-27', '0912000016', N'Hà Nội', N'Nội khoa', 1),
    (N'Trần Văn Kiên', N'Nam', '1992-10-14', '0912000017', N'Hải Phòng', N'Ngoại khoa', 1),
    (N'Lê Thị Hương', N'Nữ', '1985-11-08', '0912000018', N'Đà Nẵng', N'Tim mạch', 1),
    (N'Phạm Văn Tuấn', N'Nam', '1983-12-01', '0912000019', N'Huế', N'Da liễu', 1),
    (N'Hoàng Thị Cúc', N'Nữ', '1976-01-15', '0912000020', N'Hồ Chí Minh', N'Nhi khoa', 1),
    (N'Nguyễn Văn Thắng', N'Nam', '1994-02-22', '0912000021', N'Hà Nội', N'Nội khoa', 1),
    (N'Trần Thị Hiền', N'Nữ', '1988-03-11', '0912000022', N'Hải Phòng', N'Ngoại khoa', 1),
    (N'Lê Thanh Tú', N'Nam', '1993-04-06', '0912000023', N'Đà Nẵng', N'Tim mạch', 1),
    (N'Phạm Văn Lộc', N'Nam', '1979-05-17', '0912000024', N'Huế', N'Da liễu', 1),
    (N'Hoàng Minh Trí', N'Nam', '1987-06-22', '0912000025', N'Hồ Chí Minh', N'Nhi khoa', 1);

	select * from Doctors;


INSERT INTO Patients (FullName, Gender, DateOfBirth, PhoneNumber, Address, DiseaseName, DoctorID, Specialization)
VALUES 
(N'Nguyễn Thị Lan Anh', N'Nữ', '2000-01-01', N'0901234567', N'Số 12, Đường Hải Phòng, Đà Nẵng', N'Cảm cúm', 1, N'Nội khoa'),
(N'Phan Minh Quân', N'Nam', '1995-02-20', N'0912345678', N'Số 13, Đường Hải Phòng, Đà Nẵng', N'Viêm phổi', 2, N'Ngoại khoa'),
(N'Lê Thị Hồng Nhung', N'Nữ', '1998-03-25', N'0923456789', N'Số 14, Đường Hải Phòng, Đà Nẵng', N'Bệnh đường ruột', 7, N'Nhi khoa'),
(N'Nguyễn Thanh Hà', N'Nữ', '1992-04-10', N'0934567890', N'Số 15, Đường Hải Phòng, Đà Nẵng', N'Bệnh ngoài da', 4, N'Da liễu'),
(N'Vũ Minh Tú', N'Nam', '2001-05-15', N'0945678901', N'Số 16, Đường Hải Phòng, Đà Nẵng', N'Bệnh đường ruột', 5, N'Nhi khoa'),
(N'Hoàng Anh Tuấn', N'Nam', '1997-06-12', N'0956789012', N'Số 17, Đường Hải Phòng, Đà Nẵng', N'Viêm nhiễm', 6, N'Nội khoa'),
(N'Trần Thị Bích', N'Nữ', '1993-07-08', N'0967890123', N'Số 18, Đường Hải Phòng, Đà Nẵng', N'Rối loạn tiêu hóa', 8, N'Ngoại khoa'),
(N'Phạm Minh Ngọc', N'Nam', '2022-08-25', N'0978901234', N'Số 19, Đường Hải Phòng, Đà Nẵng', N'Tăng huyết áp', 9, N'Nhi khoa'),
(N'Lê Thanh Trúc', N'Nữ', '1996-09-18', N'0989012345', N'Số 20, Đường Hải Phòng, Đà Nẵng', N'Bệnh tim', 25, N'Nhi khoa'),
(N'Nguyễn Thị Hải Yến', N'Nữ', '1994-10-10', N'0990123456', N'Số 21, Đường Hải Phòng, Đà Nẵng', N'Hở van tim', 33, N'Tim mạch'),
(N'Hoàng Minh Tâm', N'Nam', '1998-11-12', N'0902345678', N'Số 22, Đường Hải Phòng, Đà Nẵng', N'Viêm nhiễm',24, N'Da liễu'),
(N'Vũ Thanh Bình', N'Nam', '1995-12-22', N'0913456789', N'Số 23, Đường Hải Phòng, Đà Nẵng', N'Bệnh về thần kinh', 2, N'Ngoại khoa'),
(N'Trần Minh Tâm', N'Nam', '1999-01-15', N'0924567890', N'Số 24, Đường Hải Phòng, Đà Nẵng', N'Viêm khớp', 11, N'Nội Khoa'),
(N'Nguyễn Thị Lan', N'Nữ', '1997-02-08', N'0935678901', N'Số 25, Đường Hải Phòng, Đà Nẵng', N'Bệnh ngoài da',14, N'Da liễu'),
(N'Phan Thanh Phúc', N'Nam', '1993-03-01', N'0946789012', N'Số 26, Đường Hải Phòng, Đà Nẵng', N'Thần kinh', 22, N'Ngoại khoa'),
(N'Lê Minh Long', N'Nam', '1996-04-14', N'0957890123', N'Số 27, Đường Hải Phòng, Đà Nẵng', N'Viêm đường tiết niệu', 6, N'Nội khoa'),
(N'Nguyễn Hồng Nhung', N'Nữ', '1995-05-25', N'0968901234', N'Số 28, Đường Hải Phòng, Đà Nẵng', N'Bệnh đường tiêu hóa', 9, N'Nội khoa'),
(N'Phan Thị Lan', N'Nữ', '1994-06-10', N'0979012345', N'Số 29, Đường Hải Phòng, Đà Nẵng', N'Loét dạ dày', 27, N'Ngoại khoa'),
(N'Vũ Minh Tú', N'Nam', '1995-07-18', N'0980123456', N'Số 30, Đường Hải Phòng, Đà Nẵng', N'Rối loạn tâm lý', 7, N'Nội khoa'),
    (N'Nguyễn Thị Hoa', N'Nữ', '1995-06-15', '0982000001', N'Hà Nội', N'Cảm cúm', 10, N'Nội khoa' ),
    (N'Trần Văn Minh', N'Nam', '1988-04-10', '0982000002', N'Hải Phòng', N'Đau dạ dày', 17, N'Ngoại khoa'),
    (N'Lê Thị Ngọc', N'Nữ', '1992-11-25', '0982000003', N'Đà Nẵng', N'Gãy xương', 16, N'Nội khoa' ),
    (N'Phạm Văn Tâm', N'Nam', '1979-01-30', '0982000004', N'Huế', N'Tim bẩm sinh', 19, N'Da liễu'),
    (N'Hoàng Thị Bích', N'Nữ', '1985-03-17', '0982000005', N'Hồ Chí Minh', N'Sởi', 15, N'Nhi khoa' ),
    (N'Nguyễn Văn Quý', N'Nam', '1996-07-14', '0982000006', N'Hà Nội', N'Suy nhược cơ thể', 1, N'Nội khoa'),
    (N'Trần Thị Mai', N'Nữ', '1993-09-19', '0982000007', N'Hải Phòng', N'Đau đầu', 2, N'Ngoại khoa' ),
    (N'Lê Văn Hòa', N'Nam', '1989-10-25', '0982000008', N'Đà Nẵng', N'Gãy tay', 3, N'Tim mạch'),
    (N'Phạm Thị Lệ', N'Nữ', '1985-11-02', '0982000009', N'Huế', N'Viêm phổi', 4, N'Da liễu'),
    (N'Hoàng Văn Bình', N'Nam', '1991-12-12', '0982000010', N'Hồ Chí Minh', N'Viêm họng', 5, N'Nhi khoa' ),
    (N'Nguyễn Văn Hậu', N'Nam', '1990-03-14', '0982000011', N'Hà Nội', N'Tiểu đường', 1, N'Nội khoa'),
    (N'Trần Thị Lan', N'Nữ', '1986-06-22', '0982000012', N'Hải Phòng', N'Ung thư', 16, N'Ngoại khoa' ),
    (N'Lê Thanh Hải', N'Nam', '1981-07-05', '0982000013', N'Đà Nẵng', N'Tim to', 13, N'Tim mạch'),
    (N'Phạm Văn Thắng', N'Nam', '1983-09-12', '0982000014', N'Huế', N'Viêm da', 14, N'Da liễu' ),
    (N'Hoàng Minh Châu', N'Nữ', '1995-01-08', '0982000015', N'Hồ Chí Minh', N'Viêm họng', 20, N'Nhi khoa' ),
    (N'Nguyễn Văn Hòa', N'Nam', '1987-02-18', '0982000016', N'Hà Nội', N'Suy giảm miễn dịch', 21, N'Nội khoa' ),
    (N'Trần Văn Nhân', N'Nam', '1992-10-20', '0982000017', N'Hải Phòng', N'Đau đầu kinh niên', 22, N'Ngoại khoa' ),
    (N'Lê Thị Xuân', N'Nữ', '1988-05-09', '0982000018', N'Đà Nẵng', N'Sốt xuất huyết', 28, N'Tim mạch'),
    (N'Phạm Hồng Quang', N'Nam', '1979-03-14', '0982000019', N'Huế', N'Viêm gan', 34, N'Da liễu'),
    (N'Hoàng Minh Nhật', N'Nam', '1993-04-25', '0982000020', N'Hồ Chí Minh', N'Suy hô hấp', 30, N'Nhi khoa' );





select * from Patients;
select * from Patients where Specialization like N'Nội khoa';
SELECT p.PatientID, p.FullName AS PatientName, p.Gender, p.DateOfBirth ,p.DiseaseName, p.DoctorID AS AssignedDoctorID, d.FullName AS AssignedDoctorName 
                   FROM Patients p 
                   JOIN Doctors d ON p.DoctorID = d.DoctorID 
                   WHERE p.Specialization = (SELECT Specialization FROM Doctors WHERE DoctorID = 1);


-- Xóa tất cả các khóa ngoại
DECLARE @sql NVARCHAR(MAX) = N'';
SELECT @sql += 'ALTER TABLE [' + OBJECT_NAME(parent_object_id) + '] DROP CONSTRAINT [' + name + '];' + CHAR(13)
FROM sys.foreign_keys;
EXEC sp_executesql @sql;
-- Xóa tất cả các trigger
DECLARE @sql NVARCHAR(MAX) = N'';
SELECT @sql += 'DROP TRIGGER [' + name + '];' + CHAR(13)
FROM sys.objects
WHERE type = 'TR'; -- Trigger
EXEC sp_executesql @sql;
-- Xóa tất cả các bảng
DECLARE @sql NVARCHAR(MAX) = N'';
SELECT @sql += 'DROP TABLE [' + name + '];' + CHAR(13)
FROM sys.tables;
EXEC sp_executesql @sql;



